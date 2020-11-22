package com.infotamia.pojos.common.paytrail;

/**
 * @author Mohammed Al-Ani
 **/
public class PaymentRow {

    private Integer amount;
    private String description = "";
    private Integer vatPercent;

    public PaymentRow() {
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVatPercent() {
        return vatPercent;
    }

    public void setVatPercent(Integer vatPercent) {
        this.vatPercent = vatPercent;
    }
}

