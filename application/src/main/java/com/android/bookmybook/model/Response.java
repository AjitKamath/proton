package com.android.bookmybook.model;

import java.io.Serializable;

/**
 * Created by vishal on 15/1/17.
 */

public class Response implements Serializable {

    private String errorCode;
    private String errorMessage;
    private String SSID;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }
}
