package com.android.bookmybook.model;

import android.graphics.Bitmap;

import com.android.bookmybook.util.Rent;

import java.io.Serializable;

/**
 * Created by ajit on 3/1/17.
 */

public class Book extends Rent implements Serializable{
    private String TITLE;
    private String AUTHOR;
    private String CTGRY_ID;
    private String PUBLICATION;
    private String DESCRIPTION;
    private Bitmap IMAGE;

    private String imagePath;
    private Category category;
    private Tenure minDuration;
    private Tenure maxDuration;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

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

    public Bitmap getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(Bitmap IMAGE) {
        this.IMAGE = IMAGE;
    }

    public String getCTGRY_ID() {
        return CTGRY_ID;
    }

    public void setCTGRY_ID(String CTGRY_ID) {
        this.CTGRY_ID = CTGRY_ID;
    }

    public String getPUBLICATION() {
        return PUBLICATION;
    }

    public void setPUBLICATION(String PUBLICATION) {
        this.PUBLICATION = PUBLICATION;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public Tenure getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(Tenure minDuration) {
        this.minDuration = minDuration;
    }

    public Tenure getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(Tenure maxDuration) {
        this.maxDuration = maxDuration;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
