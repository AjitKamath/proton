package com.android.bookmybook.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by ajit on 10/1/17.
 */

public class Category implements Serializable{
    private String CTGRY_ID;
    private String CTGRY_NAME;
    private String CTGRY_IMGE;
    private String IS_DEF;

    public String getCTGRY_ID() {
        return CTGRY_ID;
    }

    public void setCTGRY_ID(String CTGRY_ID) {
        this.CTGRY_ID = CTGRY_ID;
    }

    public String getCTGRY_NAME() {
        return CTGRY_NAME;
    }

    public void setCTGRY_NAME(String CTGRY_NAME) {
        this.CTGRY_NAME = CTGRY_NAME;
    }

    public String getCTGRY_IMGE() {
        return CTGRY_IMGE;
    }

    public void setCTGRY_IMGE(String CTGRY_IMGE) {
        this.CTGRY_IMGE = CTGRY_IMGE;
    }

    public String getIS_DEF() {
        return IS_DEF;
    }

    public void setIS_DEF(String IS_DEF) {
        this.IS_DEF = IS_DEF;
    }
}
