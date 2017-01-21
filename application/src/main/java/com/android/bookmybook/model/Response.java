package com.android.bookmybook.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vishal on 15/1/17.
 */

public class Response implements Serializable {

    private String PHP;
    private boolean IS_ERROR;
    private String ERROR_MESSAGE;
    private String EXCEPTION;
    private String USER_MESSAGE;

    private List<Book> BOOKS_LIST;
    private User user;

    public List<Book> getBOOKS_LIST() {
        return BOOKS_LIST;
    }

    public void setBOOKS_LIST(List<Book> BOOKS_LIST) {
        this.BOOKS_LIST = BOOKS_LIST;
    }

    public String getPHP() {
        return PHP;
    }

    public void setPHP(String PHP) {
        this.PHP = PHP;
    }

    public boolean IS_ERROR() {
        return IS_ERROR;
    }

    public void setIS_ERROR(boolean IS_ERROR) {
        this.IS_ERROR = IS_ERROR;
    }

    public String getERROR_MESSAGE() {
        return ERROR_MESSAGE;
    }

    public void setERROR_MESSAGE(String ERROR_MESSAGE) {
        this.ERROR_MESSAGE = ERROR_MESSAGE;
    }

    public String getEXCEPTION() {
        return EXCEPTION;
    }

    public void setEXCEPTION(String EXCEPTION) {
        this.EXCEPTION = EXCEPTION;
    }

    public String getUSER_MESSAGE() {
        return USER_MESSAGE;
    }

    public void setUSER_MESSAGE(String USER_MESSAGE) {
        this.USER_MESSAGE = USER_MESSAGE;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
