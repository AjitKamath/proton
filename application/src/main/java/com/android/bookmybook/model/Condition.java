package com.android.bookmybook.model;

/**
 * Created by ajit on 17/1/17.
 */

public class Condition {
    private String CNDTN_ID;
    private String CNDTN_CTGRY;

    private String conditionDesc;

    public String getCNDTN_ID() {
        return CNDTN_ID;
    }

    public void setCNDTN_ID(String CNDTN_ID) {
        this.CNDTN_ID = CNDTN_ID;
    }

    public String getCNDTN_CTGRY() {
        return CNDTN_CTGRY;
    }

    public void setCNDTN_CTGRY(String CNDTN_CTGRY) {
        this.CNDTN_CTGRY = CNDTN_CTGRY;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    public void setConditionDesc(String conditionDesc) {
        this.conditionDesc = conditionDesc;
    }
}
