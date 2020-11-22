package com.infotamia.cosmetics.services.payment;

import com.infotamia.cosmetics.daos.PaymentDao;
import com.infotamia.exception.BaseErrorCode;
import com.infotamia.exception.IncorrectParameterException;
import com.infotamia.exception.ItemNotFoundException;
import com.infotamia.exception.OperationNotAllowedException;
import com.infotamia.logger.LoggerFactory;
import com.infotamia.pojos.common.paytrail.PaymentEntity;
import com.infotamia.pojos.common.paytrail.PaymentRefundEntity;
import com.infotamia.pojos.common.paytrail.PaymentStatus;
import com.infotamia.pojos.common.paytrail.PaymentStatusEntity;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @author Mohammed Al-Ani
 **/
@RequestScoped
public class PaymentService {
    private final static Logger logger = LoggerFactory.getLogger(PaymentService.class);
    @Inject
    private PaymentDao paymentDao;
    @Inject
    private PaymentValidator validator;

    public PaymentEntity getPaymentByPaymentId(Long paymentId) throws ItemNotFoundException {
        return paymentDao.fetchPaymentByPaymentId(paymentId)
                .orElseThrow(() -> new ItemNotFoundException("payment not found", BaseErrorCode.ORDER_PAYMENT_NOT_FOUND));
    }

    public PaymentEntity getPaymentByOrderId(Long orderId) throws ItemNotFoundException {
        return paymentDao.fetchPaymentByOrderId(orderId)
                .orElseThrow(() -> new ItemNotFoundException("payment not found", BaseErrorCode.ORDER_PAYMENT_NOT_FOUND));
    }

    public PaymentEntity addPayment(PaymentEntity payment)
            throws ItemNotFoundException, IncorrectParameterException {
        PaymentStatusEntity paymentStatusEntity = addInProgressCashPayment(payment);
        logger.debug("VALIDATING ORDER PAYMENT FOR ORDER {}", payment.getOrderId());
        validator.validateInsert(payment);
        logger.debug("ADDING ORDER PAYMENT FOR ORDER {}", payment.getOrderId());
        PaymentEntity createdPayment = paymentDao.insert(payment);
        logger.debug("ORDER PAYMENT CREATED ORDER {}", payment.getOrderId());
        paymentStatusEntity.setPaymentId(createdPayment.getId());

        List<PaymentStatusEntity> createdStatus =
                paymentDao.insertPaymentStatus(Collections.singletonList(paymentStatusEntity));
        createdPayment.setPaymentStatusEntity(createdStatus);
        logger.debug("ORDER PAYMENT STATUS CREATED FOR ORDER {}", payment.getOrderId());
        return createdPayment;
    }

    public void addPaymentStatus(Long paymentId, PaymentStatus status) throws OperationNotAllowedException, ItemNotFoundException {
        PaymentEntity payment = getPaymentByPaymentId(paymentId);
        PaymentStatusEntity latestStatus = payment.getPaymentStatusEntity().stream().sorted().limit(1).findFirst().orElse(null);
        if (
                latestStatus != null
                        && !(latestStatus.getStatus().equals(PaymentStatus.PAID)
                        || latestStatus.getStatus().equals(PaymentStatus.CANCELED))) {
            if (status.equals(PaymentStatus.INPROGRESS)) {
                throw new OperationNotAllowedException("not allowed to add inprogress status", BaseErrorCode.PAYMENT_STATUS_NOT_ALLOWED);
            }

            PaymentStatusEntity paymentStatusEntity = new PaymentStatusEntity();
            paymentStatusEntity.setStatus(status);
            paymentStatusEntity.setCreatedAt(LocalDateTime.now());
            paymentDao.insertPaymentStatus(Collections.singletonList(paymentStatusEntity));
        }
    }

    private PaymentStatusEntity addInProgressCashPayment(PaymentEntity payment) {
        PaymentStatusEntity paymentStatusEntity = new PaymentStatusEntity();
        paymentStatusEntity.setStatus(PaymentStatus.INPROGRESS);
        paymentStatusEntity.setCreatedAt(LocalDateTime.now());
        payment.getPaymentStatusEntity().add(paymentStatusEntity);
        return paymentStatusEntity;
    }

    public void insertPaymentStatuses(List<PaymentStatusEntity> statuses) {
        paymentDao.insertPaymentStatus(statuses);
    }

    public void updatePayment(PaymentEntity payment) {
        paymentDao.updatePaymentEntity(payment);
    }

    public PaymentStatusEntity updatePaymentStatus(PaymentStatusEntity ps) {
        return paymentDao.updatePaymentStatus(ps);
    }

    public PaymentRefundEntity fetchPaymentRefund(String refundToken) throws ItemNotFoundException {
        return paymentDao.fetchRefundEntity(refundToken)
                .orElseThrow(() -> new ItemNotFoundException("payment refund not found", BaseErrorCode.ORDER_PAYMENT_REFUND_NOT_FOUND));
    }

    public PaymentRefundEntity createRefundEntity(PaymentRefundEntity entity) {
        return paymentDao.insertRefundEntity(entity);
    }

    public PaymentRefundEntity updatePaymentRefund(PaymentRefundEntity refundEntity) {
        return paymentDao.updatePaymentRefund(refundEntity);
    }
}

