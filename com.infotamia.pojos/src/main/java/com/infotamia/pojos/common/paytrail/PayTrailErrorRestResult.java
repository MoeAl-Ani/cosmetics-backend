package com.infotamia.pojos.common.paytrail;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

import java.io.Serializable;

/**
 * @author Mohammed Al-Ani
 **/
@Generated("com.robohorse.robopojogenerator")
public class PayTrailErrorRestResult implements Serializable {

    @JsonProperty("errorCode")
    private String errorCode;

    @JsonProperty("errorMessage")
    private String errorMessage;

    public void setErrorCode(String errorCode){
        this.errorCode = errorCode;
    }

    public String getErrorCode(){
        return errorCode;
    }

    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    @Override
    public String toString(){
        return
                "PayTrailErrorResult{" +
                        "errorCode = '" + errorCode + '\'' +
                        ",errorMessage = '" + errorMessage + '\'' +
                        "}";
    }
}

