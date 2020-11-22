package com.infotamia.pojos.common.paytrail;

/**
 * @author Mohammed Al-Ani
 **/

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class UrlSetPayload implements Serializable {

    @JsonProperty("success")
    private String success;

    @JsonProperty("failure")
    private String failure;

    @JsonProperty("pending")
    private String pending;

    @JsonProperty("notification")
    private String notification;

    public void setSuccess(String success){
        this.success = success;
    }

    public String getSuccess(){
        return success;
    }

    public void setFailure(String failure){
        this.failure = failure;
    }

    public String getFailure(){
        return failure;
    }

    public void setPending(String pending){
        this.pending = pending;
    }

    public String getPending(){
        return pending;
    }

    public void setNotification(String notification){
        this.notification = notification;
    }

    public String getNotification(){
        return notification;
    }

    @Override
    public String toString(){
        return
                "UrlSetPayload{" +
                        "success = '" + success + '\'' +
                        ",failure = '" + failure + '\'' +
                        ",pending = '" + pending + '\'' +
                        ",notification = '" + notification + '\'' +
                        "}";
    }
}

