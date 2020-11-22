package com.infotamia.config;

/**
 * @author Mohammed Al-Ani
 **/
public class PaymentConfiguration {
    public PaymentConfiguration() {
        //
    }

    private PayTrailConfiguration paytrail;

    public PayTrailConfiguration getPaytrail() {
        return paytrail;
    }

    public void setPaytrail(PayTrailConfiguration paytrail) {
        this.paytrail = paytrail;
    }

    public String toString() {
        return super.toString();
    }
}
