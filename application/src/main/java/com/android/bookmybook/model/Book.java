package com.android.bookmybook.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by ajit on 3/1/17.
 */

public class Book implements Serializable {
    private String TITLE;
    private String AUTHOR;
    private Double RENT;
    private Bitmap IMAGE;

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

    public Bitmap getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(Bitmap IMAGE) {
        this.IMAGE = IMAGE;
    }
}
