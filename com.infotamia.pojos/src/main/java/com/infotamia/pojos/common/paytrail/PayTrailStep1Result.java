package com.infotamia.pojos.common.paytrail;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

import java.io.Serializable;

/**
 * @author Mohammed Al-Ani
 **/
@Generated("com.robohorse.robopojogenerator")
public class PayTrailStep1Result implements Serializable {

    @JsonProperty("orderNumber")
    private String orderNumber;

    @JsonProperty("token")
    private String token;

    @JsonProperty("url")
    private String url;

    public void setOrderNumber(String orderNumber){
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber(){
        return orderNumber;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }

    @Override
    public String toString(){
        return
                "PayTrailStep1Result{" +
                        "orderNumber = '" + orderNumber + '\'' +
                        ",token = '" + token + '\'' +
                        ",url = '" + url + '\'' +
                        "}";
    }
}

