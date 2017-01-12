package com.android.bookmybook.model;

import android.graphics.Bitmap;

/**
 * Created by ajit on 10/1/17.
 */

public class Category {
    private String CTGRY_ID;
    private String CTGRY_NAME;
    private String CTGRY_IMGE;

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
}
