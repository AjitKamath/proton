package com.android.bookmybook.util;

import java.io.Serializable;

/**
 * Created by ajit on 15/1/17.
 */

public class Rent implements Serializable{
    private String USER_ID;
    private String DURATION;
    private Integer RENT;
    private Integer AMOUNT;
    private String MIN_DURATION;
    private String MAX_DURATION;
    private String ACTIVE;

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getDURATION() {
        return DURATION;
    }

    public void setDURATION(String DURATION) {
        this.DURATION = DURATION;
    }

    public Integer getRENT() {
        return RENT;
    }

    public void setRENT(Integer RENT) {
        this.RENT = RENT;
    }

    public Integer getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(Integer AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getMIN_DURATION() {
        return MIN_DURATION;
    }

    public void setMIN_DURATION(String MIN_DURATION) {
        this.MIN_DURATION = MIN_DURATION;
    }

    public String getMAX_DURATION() {
        return MAX_DURATION;
    }

    public void setMAX_DURATION(String MAX_DURATION) {
        this.MAX_DURATION = MAX_DURATION;
    }

    public String getACTIVE() {
        return ACTIVE;
    }

    public void setACTIVE(String ACTIVE) {
        this.ACTIVE = ACTIVE;
    }
}
