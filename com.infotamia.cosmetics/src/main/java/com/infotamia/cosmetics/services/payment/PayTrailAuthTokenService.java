package com.infotamia.cosmetics.services.payment;

import com.infotamia.config.ConfigEnum;
import com.infotamia.config.ConfigServiceQualifier;
import com.infotamia.config.PayTrailConfiguration;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Mohammed Al-Ani
 **/
@Dependent
public class PayTrailAuthTokenService {

    @Inject
    @ConfigServiceQualifier(ConfigEnum.PAY_TRAIL)
    private PayTrailConfiguration payTrailConfiguration;

    public String generatePaymentAuthCode(String orderNumber, String transactionTimestamp, String transactionId, String paymentMethod) {

        // create the token
        StringBuilder builder = new StringBuilder();
        String merchantSecret = payTrailConfiguration.getMerchantSecret();
        if (!StringUtils.isBlank(orderNumber)) {
            builder.append(orderNumber).append("|");
        }
        if (!StringUtils.isBlank(transactionTimestamp)) {
            builder.append(transactionTimestamp).append("|");
        }
        if (!StringUtils.isBlank(transactionId)) {
            builder.append(transactionId).append("|");
        }
        if (!StringUtils.isBlank(paymentMethod)) {
            builder.append(paymentMethod).append("|");
        }
        builder.append(merchantSecret);
        String code = builder.toString();
        return DigestUtils.md5Hex(code).toUpperCase();
    }
}

