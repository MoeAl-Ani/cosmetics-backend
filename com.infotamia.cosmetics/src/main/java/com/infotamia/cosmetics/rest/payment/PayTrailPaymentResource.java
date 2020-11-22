package com.infotamia.cosmetics.rest.payment;

import com.infotamia.base.rest.AbstractResourceHandler;
import com.infotamia.cosmetics.factory.AbstractCDIFactory;
import com.infotamia.cosmetics.services.email.EmailService;
import com.infotamia.cosmetics.services.order.OrderService;
import com.infotamia.cosmetics.services.payment.PayTrailAuthTokenService;
import com.infotamia.cosmetics.services.payment.PaymentService;
import com.infotamia.cosmetics.services.user.UserService;
import com.infotamia.cosmetics.transaction.CosmeticsTransactional;
import com.infotamia.config.ConfigEnum;
import com.infotamia.config.ConfigServiceQualifier;
import com.infotamia.config.PayTrailConfiguration;
import com.infotamia.cosmetics.utils.OrderHtmlUtils;
import com.infotamia.exception.*;
import com.infotamia.jackson.ObjectMapperProducer;
import com.infotamia.logger.LoggerFactory;
import com.infotamia.pojos.common.paytrail.*;
import com.infotamia.pojos.entities.OrderDetailsEntity;
import com.infotamia.pojos.entities.UserEntity;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author Mohammed Al-Ani
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
@Path("/payment/paytrail/")
public class PayTrailPaymentResource implements AbstractResourceHandler {

    @Inject
    @ConfigServiceQualifier(value = ConfigEnum.PAY_TRAIL)
    private PayTrailConfiguration payTrailConfiguration;

    @Inject
    private OrderService orderService;
    @Inject
    private PaymentService paymentService;
    @Inject
    private EmailService emailService;
    @Inject
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(PayTrailPaymentResource.class);

    @POST
    @Path("orders/{orderId}")
    @CosmeticsTransactional
    public Response postPaymentStep1(@PathParam("orderId") Long orderId) {
        try {
            OrderDetailsEntity order = orderService.fetchOrder(orderId);
            try {
                PaymentEntity payment = paymentService.getPaymentByOrderId(orderId);
                Optional<PaymentStatusEntity> statusOpt = payment.getPaymentStatusEntity().stream().sorted().limit(1).findFirst();
                if (statusOpt.isPresent()) {
                    if (!statusOpt.get().getStatus().equals(PaymentStatus.INPROGRESS)) {
                        throw new RestCoreException(
                                403, BaseErrorCode.PAYMENT_NOT_ALLOWED_TO_MODIFIED, "payment is not allowed to be modified");
                    } else {
                        PaymentStatusEntity paymentStatusEntity = statusOpt.get();
                        return Response.status(Response.Status.CREATED)
                                .location(URI.create(payTrailConfiguration.getClientRedirectUrl() + "/" + payment.getTransactionId()))
                                .entity(paymentStatusEntity).build();
                    }
                } else {
                    throw new RestCoreException(400, BaseErrorCode.ORDER_HAS_ACTIVE_PAYMENT, "order has an active payment");
                }

            } catch (ItemNotFoundException ignore) {
            }
            PayTrailPaymentRestPayload payTrailPaymentRestPayload = new PayTrailPaymentRestPayload();
            payTrailPaymentRestPayload.setCurrency(payTrailConfiguration.getCurrency());
            payTrailPaymentRestPayload.setLocale("en_US");
            payTrailPaymentRestPayload.setOrderNumber(order.getOrderReference());
            UrlSetPayload urlSetPayload = new UrlSetPayload();
            urlSetPayload.setSuccess(payTrailConfiguration.getSuccessUrl());
            urlSetPayload.setFailure(payTrailConfiguration.getCancelUrl());
            urlSetPayload.setNotification(payTrailConfiguration.getNotifyUrl());
            payTrailPaymentRestPayload.setUrlSet(urlSetPayload);
            payTrailPaymentRestPayload.setPrice(order.getNetAmount());
            HttpAuthenticationFeature basicFeature =
                    HttpAuthenticationFeature.basic(
                            payTrailConfiguration.getMerchantId(),
                            payTrailConfiguration.getMerchantSecret());
            ClientConfig clientConfig = new ClientConfig();
            clientConfig.register(JacksonFeature.class);  // usually auto-discovered
            clientConfig.register(CDI.current().select(ObjectMapperProducer.class).get());
            Client client = ClientBuilder.newClient(clientConfig);
            client.register(basicFeature);
            Response response = client.target(payTrailConfiguration.getCreatePaymentUrl())
                    .request()
                    .header("X-Verkkomaksut-Api-Version", payTrailConfiguration.getApiVersion())
                    .header("Content-Type", MediaType.APPLICATION_JSON)
                    .header("Accept", MediaType.APPLICATION_JSON)
                    .post(Entity.entity(payTrailPaymentRestPayload, MediaType.APPLICATION_JSON));
            if (response.getStatus() != 201) {
                PayTrailErrorRestResult payTrailErrorRestResult = response.readEntity(PayTrailErrorRestResult.class);
                throw new RuntimeException("ERROR" + payTrailErrorRestResult.toString());
            }
            PayTrailStep1Result payTrailStep1Result = response.readEntity(PayTrailStep1Result.class);
            String clientRedirectUrl = payTrailStep1Result.getUrl();
            PaymentEntity paymentEntity = new PaymentEntity();
            paymentEntity.setOrderId(orderId);
            paymentEntity.setTransactionId(payTrailStep1Result.getToken());
            paymentEntity.setType(PaymentType.PAYTRAIL);
            paymentEntity.setCreatedAt(LocalDateTime.now());
            PaymentEntity createdPayment = paymentService.addPayment(paymentEntity);
            return Response.status(Response.Status.CREATED).location(URI.create(clientRedirectUrl)).entity(createdPayment).build();
        } catch (ItemNotFoundException | IncorrectParameterException e) {
            throw new RestCoreException(400, e.getErrorCode(), e.getMessage());
        }
    }

    @GET
    @Path("success")
    @CosmeticsTransactional
    public Response getSuccessCallback(
            @QueryParam("ORDER_NUMBER") String orderNumber,
            @QueryParam("TIMESTAMP") String transactionTimestamp,
            @QueryParam("PAID") String transactionId,
            @QueryParam("METHOD") String paymentMethod,
            @QueryParam("RETURN_AUTHCODE") String authCode) throws ItemNotFoundException {

        PayTrailAuthTokenService payTrailService = AbstractCDIFactory.select(PayTrailAuthTokenService.class);
        String calculatedHash = payTrailService.generatePaymentAuthCode(orderNumber, transactionTimestamp, transactionId, paymentMethod);
        if (!calculatedHash.equals(authCode)) {
            throw new RuntimeException("payment is not successful");
        }
        OrderDetailsEntity orderDetails = orderService.fetchOrder(orderNumber);
        UserEntity user = userService.findUserById(orderDetails.getUserId());
        orderDetails.setEmail(user.getEmail());
        orderDetails.setPhoneNumber(user.getPhoneNumber());

        PaymentEntity payment = paymentService.getPaymentByOrderId(orderDetails.getId());
        if (payment == null || payment.getId() == null) {
            // should never happen
            throw new RestCoreException(400,BaseErrorCode.CONTACT_SUPPLIER_FOR_REFUND, "something went wrong please contact supplier for refund");
        }
        PaymentStatusEntity entity = new PaymentStatusEntity();
        entity.setCreatedAt(LocalDateTime.now());
        entity.setPaymentId(payment.getId());
        entity.setStatus(PaymentStatus.PAID);
        paymentService.insertPaymentStatuses(List.of(entity));
        payment.setTransactionId(transactionId);
        payment.setSelectedMethod(Integer.valueOf(paymentMethod));
        paymentService.updatePayment(payment);
        try {
            String customerMessage = OrderHtmlUtils.convertOrderToCustomerConfirmationMessage(orderDetails);
            emailService.send(orderDetails.getEmail(), "cosmetics.com", customerMessage);
        } catch (Exception e) {
            logger.error(e);
            // TODO check manually daily
        }
        return Response.ok().build();
    }

    @GET
    @Path("cancel")
    @CosmeticsTransactional
    public Response getCancelCallback(@QueryParam("ORDER_NUMBER") String orderNumber,
                                      @QueryParam("TIMESTAMP") String transactionTimestamp,
                                      @QueryParam("RETURN_AUTHCODE") String authCode) {

        PayTrailAuthTokenService payTrailService = AbstractCDIFactory.select(PayTrailAuthTokenService.class);
        String calculatedHash = payTrailService.generatePaymentAuthCode(orderNumber, transactionTimestamp, null, null);
        if (!calculatedHash.equals(authCode)) {
            throw new RuntimeException("cancel is not successful");
        }

        OrderDetailsEntity orderDetails = null;
        try {
            orderDetails = orderService.fetchOrder(orderNumber);
        } catch (ItemNotFoundException e) {
            throw new RuntimeException("something went wrong please contact restaurant for refund");
        }
        PaymentEntity payment = null;
        try {
            payment = paymentService.getPaymentByOrderId(orderDetails.getId());
        } catch (ItemNotFoundException e) {
            throw new RuntimeException("something went wrong please contact restaurant for refund");
        }

        PaymentStatusEntity latestStatus = payment.getPaymentStatusEntity().stream().sorted().limit(1).findFirst().get();
        if (!latestStatus.getStatus().equals(PaymentStatus.INPROGRESS)) {
            throw new RestCoreException(403, BaseErrorCode.PAYMENT_NOT_ALLOWED_TO_MODIFIED, "payment is not allowed to be modified");
        }
        return Response.ok().build();
    }

    @GET
    @Path("notify")
    @CosmeticsTransactional
    public Response getNotifyCallback() {
        logger.info("PAYMENT SUCCEEDED");
        // TODO figure out in future.
        return Response.ok().build();
    }

    @GET
    @Path("/refund/notify")
    @CosmeticsTransactional
    public Response getRefundNotifyCallback(
            @QueryParam("refundToken") String refundToken,
            @QueryParam("oldStatus") String oldStatus,
            @QueryParam("newStatus") String newStatus,
            @QueryParam("signature") String signature
    ) {

        logger.info("REFUND TOKEN = {}", refundToken);
        logger.info("oldStatus = {}", oldStatus);
        logger.info("newStatus = {}", newStatus);
        logger.info("signature = {}", signature);

        RefundStatus newStatusType = RefundStatus.valueOf(newStatus.toUpperCase().trim());
        RefundStatus oldStatusType = RefundStatus.valueOf(oldStatus.toUpperCase().trim());

        try {
            PaymentRefundEntity refundEntity = paymentService.fetchPaymentRefund(refundToken);
            PaymentEntity payment = paymentService.getPaymentByPaymentId(refundEntity.getPaymentId());
            OrderDetailsEntity orderDetails = orderService.fetchOrder(payment.getOrderId());

            String signatureCheck =
                    DigestUtils.sha256Hex(
                            refundToken + "|" +
                                    oldStatus + "|" +
                                    newStatus + "|" +
                                    payTrailConfiguration.getMerchantSecret());
            logger.info("calculated signature = {}", signatureCheck);
            if (!signature.equals(signatureCheck)) {
                throw new RuntimeException("ei ei");
            }
            RefundStatus refundDbStatus = refundEntity.getRefundStatus();
            if (refundDbStatus.equals(RefundStatus.CREATED)) {
                refundEntity.setRefundStatus(newStatusType);
                paymentService.updatePaymentRefund(refundEntity);
                List<PaymentStatusEntity> paymentStatuses = payment.getPaymentStatusEntity();

                paymentStatuses.stream().max(Comparator.comparing(PaymentStatusEntity::getId))
                        .ifPresentOrElse(ps -> {
                            if (ps.getStatus().equals(PaymentStatus.PAID)) {
                                if (refundEntity.getRefundStatus().equals(RefundStatus.COMPLETED)) {
                                    ps.setStatus(PaymentStatus.CANCELED);
                                    ps.setId(null);
                                    ps.setCreatedAt(LocalDateTime.now());
                                    paymentService.insertPaymentStatuses(List.of(ps));
                                }
                            }
                        },() -> {
                            //TODO THIS CASE IS RARE AND SHOULD NEVER HAPPEN!!.
                            logger.warn("NO STATUSES FOUND");
                            logger.warn("PAYMENT CANT BE REFUNDED");
                        });
            }
        } catch (ItemNotFoundException ignore) {
        }

        return Response.ok().build();
    }

}

