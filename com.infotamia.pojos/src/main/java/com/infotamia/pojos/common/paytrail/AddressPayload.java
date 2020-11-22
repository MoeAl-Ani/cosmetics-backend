package com.infotamia.pojos.common.paytrail;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

import java.io.Serializable;

/**
 * @author Mohammed Al-Ani
 **/
@Generated("com.robohorse.robopojogenerator")
public class AddressPayload implements Serializable {

    @JsonProperty("street")
    private String street;

    @JsonProperty("postalCode")
    private String postalCode;

    @JsonProperty("postalOffice")
    private String postalOffice;

    @JsonProperty("country")
    private String country;

    public void setStreet(String street){
        this.street = street;
    }

    public String getStreet(){
        return street;
    }

    public void setPostalCode(String postalCode){
        this.postalCode = postalCode;
    }

    public String getPostalCode(){
        return postalCode;
    }

    public void setPostalOffice(String postalOffice){
        this.postalOffice = postalOffice;
    }

    public String getPostalOffice(){
        return postalOffice;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public String getCountry(){
        return country;
    }

    @Override
    public String toString(){
        return
                "AddressPayload{" +
                        "street = '" + street + '\'' +
                        ",postalCode = '" + postalCode + '\'' +
                        ",postalOffice = '" + postalOffice + '\'' +
                        ",country = '" + country + '\'' +
                        "}";
    }
}

