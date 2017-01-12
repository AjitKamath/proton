package com.android.bookmybook.model;

/**
 * Created by ajit on 10/1/17.
 */

public class Tenure {
    private String TENURE_ID;
    private String TENURE_NAME;
    private Integer NO_OF_DAYS;

    public String getTENURE_ID() {
        return TENURE_ID;
    }

    public void setTENURE_ID(String TENURE_ID) {
        this.TENURE_ID = TENURE_ID;
    }

    public String getTENURE_NAME() {
        return TENURE_NAME;
    }

    public void setTENURE_NAME(String TENURE_NAME) {
        this.TENURE_NAME = TENURE_NAME;
    }

    public Integer getNO_OF_DAYS() {
        return NO_OF_DAYS;
    }

    public void setNO_OF_DAYS(Integer NO_OF_DAYS) {
        this.NO_OF_DAYS = NO_OF_DAYS;
    }
}
