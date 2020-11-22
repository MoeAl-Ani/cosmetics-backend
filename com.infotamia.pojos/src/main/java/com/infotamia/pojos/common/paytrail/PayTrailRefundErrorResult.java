package com.infotamia.pojos.common.paytrail;

/**
 * @author Mohammed Al-Ani
 **/
public class PayTrailRefundErrorResult {

    private String title;
    private String description;
    private String workaround;

    public PayTrailRefundErrorResult() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWorkaround() {
        return workaround;
    }

    public void setWorkaround(String workaround) {
        this.workaround = workaround;
    }

    @Override
    public String toString() {
        return "PayTrailRefundErrorResult{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", workaround='" + workaround + '\'' +
                '}';
    }
}

