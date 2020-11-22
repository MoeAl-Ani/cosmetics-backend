package com.infotamia.config;

/**
 * Paytrail payment configuration bean.
 * @author Mohammed Al-Ani
 **/
public class PayTrailConfiguration {
    private String merchantId;
    private String merchantSecret;
    private String successUrl;
    private String cancelUrl;
    private String notifyUrl;
    private String createPaymentUrl;
    private String clientRedirectUrl;
    private String merchantBaseUrl;
    private String createRefundUrl;
    private String cancelRefundUrl;
    private String notifyRefundUrl;
    private String currency;
    private Integer apiVersion;

    public PayTrailConfiguration() {
        //
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantSecret() {
        return merchantSecret;
    }

    public void setMerchantSecret(String merchantSecret) {
        this.merchantSecret = merchantSecret;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getCreatePaymentUrl() {
        return createPaymentUrl;
    }

    public void setCreatePaymentUrl(String createPaymentUrl) {
        this.createPaymentUrl = createPaymentUrl;
    }

    public String getClientRedirectUrl() {
        return clientRedirectUrl;
    }

    public void setClientRedirectUrl(String clientRedirectUrl) {
        this.clientRedirectUrl = clientRedirectUrl;
    }

    public String getMerchantBaseUrl() {
        return merchantBaseUrl;
    }

    public void setMerchantBaseUrl(String merchantBaseUrl) {
        this.merchantBaseUrl = merchantBaseUrl;
    }

    public String getCreateRefundUrl() {
        return createRefundUrl;
    }

    public void setCreateRefundUrl(String createRefundUrl) {
        this.createRefundUrl = createRefundUrl;
    }

    public String getCancelRefundUrl() {
        return cancelRefundUrl;
    }

    public void setCancelRefundUrl(String cancelRefundUrl) {
        this.cancelRefundUrl = cancelRefundUrl;
    }

    public String getNotifyRefundUrl() {
        return notifyRefundUrl;
    }

    public void setNotifyRefundUrl(String notifyRefundUrl) {
        this.notifyRefundUrl = notifyRefundUrl;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(Integer apiVersion) {
        this.apiVersion = apiVersion;
    }
}
