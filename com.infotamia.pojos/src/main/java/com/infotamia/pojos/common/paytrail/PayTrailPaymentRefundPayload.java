package com.infotamia.pojos.common.paytrail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammed Al-Ani
 **/
public class PayTrailPaymentRefundPayload implements Serializable {

    private String email;
    private List<PaymentRow> rows = new ArrayList<>();
    private String notifyUrl;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public List<PaymentRow> getRows() {
        return rows;
    }

    public void setRows(List<PaymentRow> rows) {
        this.rows = rows;
    }
}

