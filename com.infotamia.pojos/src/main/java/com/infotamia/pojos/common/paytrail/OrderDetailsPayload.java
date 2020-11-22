package com.infotamia.pojos.common.paytrail;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mohammed Al-Ani
 **/
@Generated("com.robohorse.robopojogenerator")
public class OrderDetailsPayload implements Serializable {

    @JsonProperty("includeVat")
    private String includeVat;

    @JsonProperty("contact")
    private ContactPayload contact;

    @JsonProperty("products")
    private List<ProductsPayload> products;

    public void setIncludeVat(String includeVat){
        this.includeVat = includeVat;
    }

    public String getIncludeVat(){
        return includeVat;
    }

    public void setContact(ContactPayload contact){
        this.contact = contact;
    }

    public ContactPayload getContact(){
        return contact;
    }

    public void setProducts(List<ProductsPayload> products){
        this.products = products;
    }

    public List<ProductsPayload> getProducts(){
        return products;
    }

    @Override
    public String toString(){
        return
                "OrderDetailsPayload{" +
                        "includeVat = '" + includeVat + '\'' +
                        ",contact = '" + contact + '\'' +
                        ",products = '" + products + '\'' +
                        "}";
    }
}

