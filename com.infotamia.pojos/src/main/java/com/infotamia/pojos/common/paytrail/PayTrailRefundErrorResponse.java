package com.infotamia.pojos.common.paytrail;

/**
 * @author Mohammed Al-Ani
 **/
public class PayTrailRefundErrorResponse {

    private PayTrailRefundErrorResult error;

    public PayTrailRefundErrorResponse() {
    }

    public PayTrailRefundErrorResult getError() {
        return error;
    }

    public void setError(PayTrailRefundErrorResult error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "PayTrailRefundErrorResponse{" +
                "error=" + error +
                '}';
    }
}

