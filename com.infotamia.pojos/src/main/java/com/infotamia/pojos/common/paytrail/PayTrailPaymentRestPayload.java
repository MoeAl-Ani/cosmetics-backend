package com.infotamia.pojos.common.paytrail;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

import java.io.Serializable;

/**
 * @author Mohammed Al-Ani
 **/
@Generated("com.robohorse.robopojogenerator")
public class PayTrailPaymentRestPayload implements Serializable {

    @JsonProperty("orderNumber")
    private String orderNumber;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("locale")
    private String locale;

    @JsonProperty("urlSet")
    private UrlSetPayload urlSet;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("orderDetails")
    private OrderDetailsPayload orderDetails;

    public void setOrderNumber(String orderNumber){
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber(){
        return orderNumber;
    }

    public void setCurrency(String currency){
        this.currency = currency;
    }

    public String getCurrency(){
        return currency;
    }

    public void setLocale(String locale){
        this.locale = locale;
    }

    public String getLocale(){
        return locale;
    }

    public void setUrlSet(UrlSetPayload urlSet){
        this.urlSet = urlSet;
    }

    public UrlSetPayload getUrlSet(){
        return urlSet;
    }

    public void setPrice(Double price){
        this.price = price;
    }

    public Double getPrice(){
        return price;
    }

    public void setOrderDetails(OrderDetailsPayload orderDetails){
        this.orderDetails = orderDetails;
    }

    public OrderDetailsPayload getOrderDetails(){
        return orderDetails;
    }

    @Override
    public String toString(){
        return
                "PayTrailPaymentPayload{" +
                        "orderNumber = '" + orderNumber + '\'' +
                        ",currency = '" + currency + '\'' +
                        ",locale = '" + locale + '\'' +
                        ",urlSet = '" + urlSet + '\'' +
                        ",price = '" + price + '\'' +
                        ",orderDetails = '" + orderDetails + '\'' +
                        "}";
    }
}

