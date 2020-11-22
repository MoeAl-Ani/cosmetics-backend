package com.infotamia.cosmetics.services.payment;

import com.infotamia.cosmetics.daos.OrderDao;
import com.infotamia.exception.BaseErrorCode;
import com.infotamia.exception.IncorrectParameterException;
import com.infotamia.exception.ItemNotFoundException;
import com.infotamia.pojos.common.paytrail.PaymentEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

/**
 * @author Mohammed Al-Ani
 **/

@RequestScoped
public class PaymentValidator {

    @Inject
    private OrderDao foodOrderDao;

    public void validateInsert(@NotNull(message = "payment entity was null") PaymentEntity paymentEntity)
            throws ItemNotFoundException, IncorrectParameterException {
        if (paymentEntity.getPaymentStatusEntity() != null && paymentEntity.getPaymentStatusEntity().size() != 1) {
            throw new IncorrectParameterException(
                    "Payment entity has to contain at least one status!", BaseErrorCode.ORDER_MISSING_PAYMENT_STATUS);
        }
        foodOrderDao.fetchOneByOrderId(paymentEntity.getOrderId()).orElseThrow(
                () -> new ItemNotFoundException("Order referred in Payment not found!", BaseErrorCode.ORDER_NOT_FOUND));
        if (paymentEntity.getPaymentStatusEntity().stream().anyMatch(paymentStatus -> paymentStatus.getId() != null)) {
            throw new IncorrectParameterException(
                    "Not allowed to have an id in payment statuses while inserting!", BaseErrorCode.ORDER_ID_NOT_ALLOWED_IN_INSERT);
        }
    }
}

