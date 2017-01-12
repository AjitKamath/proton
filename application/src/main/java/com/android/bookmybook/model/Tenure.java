package com.android.bookmybook.model;

import java.io.Serializable;

/**
 * Created by ajit on 10/1/17.
 */

public class Tenure implements Serializable{
    private String TENURE_ID;
    private String TENURE_NAME;
    private Integer NO_OF_DAYS;
    private String IS_DEF;

    public String getIS_DEF() {
        return IS_DEF;
    }

    public void setIS_DEF(String IS_DEF) {
        this.IS_DEF = IS_DEF;
    }

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
