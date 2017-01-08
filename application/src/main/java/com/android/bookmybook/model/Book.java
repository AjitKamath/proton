package com.android.bookmybook.model;

import java.io.Serializable;

/**
 * Created by ajit on 3/1/17.
 */

public class Book implements Serializable {
    private String TITLE;
    private String AUTHOR;
    private Double RENT;

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getAUTHOR() {
        return AUTHOR;
    }

    public void setAUTHOR(String AUTHOR) {
        this.AUTHOR = AUTHOR;
    }

    public Double getRENT() {
        return RENT;
    }

    public void setRENT(Double RENT) {
        this.RENT = RENT;
    }
}
