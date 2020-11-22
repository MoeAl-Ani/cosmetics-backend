package com.infotamia.pojos.common.paytrail;

/**
 * @author Mohammed Al-Ani
 **/
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class ProductsPayload implements Serializable {

    @JsonProperty("title")
    private String title;

    @JsonProperty("code")
    private String code;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("price")
    private String price;

    @JsonProperty("vat")
    private String vat;

    @JsonProperty("discount")
    private String discount;

    @JsonProperty("type")
    private String type;

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public void setAmount(String amount){
        this.amount = amount;
    }

    public String getAmount(){
        return amount;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public String getPrice(){
        return price;
    }

    public void setVat(String vat){
        this.vat = vat;
    }

    public String getVat(){
        return vat;
    }

    public void setDiscount(String discount){
        this.discount = discount;
    }

    public String getDiscount(){
        return discount;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    @Override
    public String toString(){
        return
                "ProductsPayload{" +
                        "title = '" + title + '\'' +
                        ",code = '" + code + '\'' +
                        ",amount = '" + amount + '\'' +
                        ",price = '" + price + '\'' +
                        ",vat = '" + vat + '\'' +
                        ",discount = '" + discount + '\'' +
                        ",type = '" + type + '\'' +
                        "}";
    }
}

