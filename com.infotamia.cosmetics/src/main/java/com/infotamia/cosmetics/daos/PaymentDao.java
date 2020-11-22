package com.infotamia.cosmetics.daos;

import com.infotamia.pojos.common.paytrail.PaymentEntity;
import com.infotamia.pojos.common.paytrail.PaymentRefundEntity;
import com.infotamia.pojos.common.paytrail.PaymentStatusEntity;
import com.infotamia.pojos.entities.ProductEntity;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Mohammed Al-Ani
 **/
@RequestScoped
public class PaymentDao {
    public PaymentDao() {
        //
    }

    @Inject
    private Session session;


    public Optional<PaymentEntity> fetchPaymentByOrderId(Long orderId) {
        if (orderId == null || orderId <= 0) return Optional.empty();
        List<Object[]> result = session.createSQLQuery("select {p.*}, {ps.*} from payment p " +
                "join payment_status ps on ps.payment_id = p.id " +
                "where p.order_id = :orderId")
                .addEntity("p", PaymentEntity.class)
                .addEntity("ps", PaymentStatusEntity.class)
                .setParameter("orderId", orderId)
                .getResultList();

        return getPaymentEntity(result);
    }

    public Optional<PaymentEntity> fetchPaymentByPaymentId(Long paymentId) {
        if (paymentId == null || paymentId <= 0) return Optional.empty();
        List<Object[]> result = session.createSQLQuery("select {p.*}, {ps.*} from payment p " +
                "join payment_status ps on ps.payment_id = p.id " +
                "where p.id = :paymentId")
                .addEntity("p", PaymentEntity.class)
                .addEntity("ps", PaymentStatusEntity.class)
                .setParameter("paymentId", paymentId)
                .getResultList();

        return getPaymentEntity(result);
    }

    private Optional<PaymentEntity> getPaymentEntity(List<Object[]> result) {
        if (result.isEmpty()) return Optional.empty();
        PaymentEntity paymentEntity = null;
        for (Object[] objects : result) {
            PaymentStatusEntity paymentStatusEntity = null;

            for (Object o : objects) {
                if (o instanceof PaymentEntity) {
                    paymentEntity = (PaymentEntity) o;
                } else paymentStatusEntity = (PaymentStatusEntity) o;
            }
            if (paymentEntity != null) {
                if (paymentStatusEntity != null) {
                    paymentEntity.getPaymentStatusEntity().add(paymentStatusEntity);
                    Collections.sort(paymentEntity.getPaymentStatusEntity());
                }
            }
        }
        return Optional.ofNullable(paymentEntity);
    }

    public PaymentEntity insert(PaymentEntity payment) {
        session.persist(payment);
        return payment;
    }

    public List<PaymentStatusEntity> insertPaymentStatus(List<PaymentStatusEntity> statuses) {
        statuses.forEach(session::persist);
        return statuses;
    }

    public PaymentEntity updatePaymentEntity(PaymentEntity payment) {
        return (PaymentEntity) session.merge(payment);
    }

    public PaymentStatusEntity updatePaymentStatus(PaymentStatusEntity ps) {
        return (PaymentStatusEntity) session.merge(ps);
    }

    public Optional<PaymentRefundEntity> fetchRefundEntity(String refundToken) {
        if (StringUtils.isBlank(refundToken)) return Optional.empty();
        try {
            return Optional.of((PaymentRefundEntity) session.createSQLQuery("select {pr.*} from payment_refund pr " +
                    "where pr.refund_token = :refundToken")
                    .addEntity("pr", PaymentRefundEntity.class)
                    .setParameter("refundToken", refundToken)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public PaymentRefundEntity insertRefundEntity(PaymentRefundEntity paymentRefundEntity) {
        session.persist(paymentRefundEntity);
        return paymentRefundEntity;
    }

    public PaymentRefundEntity updatePaymentRefund(PaymentRefundEntity refundEntity) {
        return (PaymentRefundEntity) session.merge(refundEntity);
    }
}
