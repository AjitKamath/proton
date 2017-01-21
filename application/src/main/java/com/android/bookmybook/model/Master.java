package com.android.bookmybook.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajit on 10/1/17.
 */

public class Master implements Serializable {
    private User user;
    private List<Category> categoriesList = new ArrayList<>();
    private List<Tenure> tenuresList = new ArrayList<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tenure> getTenuresList() {
        return tenuresList;
    }

    public void setTenuresList(List<Tenure> tenuresList) {
        this.tenuresList = tenuresList;
    }

    public List<Category> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<Category> categoriesList) {
        this.categoriesList = categoriesList;
    }
}
