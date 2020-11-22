package com.infotamia.pojos.common.paytrail;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

import java.io.Serializable;

/**
 * @author Mohammed Al-Ani
 **/
@Generated("com.robohorse.robopojogenerator")
public class ContactPayload implements Serializable {

    @JsonProperty("telephone")
    private String telephone;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("email")
    private String email;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("address")
    private AddressPayload address;

    public void setTelephone(String telephone){
        this.telephone = telephone;
    }

    public String getTelephone(){
        return telephone;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }

    public String getMobile(){
        return mobile;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    public String getCompanyName(){
        return companyName;
    }

    public void setAddress(AddressPayload address){
        this.address = address;
    }

    public AddressPayload getAddress(){
        return address;
    }

    @Override
    public String toString(){
        return
                "ContactPayload{" +
                        "telephone = '" + telephone + '\'' +
                        ",mobile = '" + mobile + '\'' +
                        ",email = '" + email + '\'' +
                        ",firstName = '" + firstName + '\'' +
                        ",lastName = '" + lastName + '\'' +
                        ",companyName = '" + companyName + '\'' +
                        ",address = '" + address + '\'' +
                        "}";
    }
}

