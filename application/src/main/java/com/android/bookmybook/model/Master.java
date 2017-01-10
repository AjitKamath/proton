package com.android.bookmybook.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajit on 10/1/17.
 */

public class Master {
    private List<Category> categoriesList = new ArrayList<>();
    private List<Tenure> tenuresList = new ArrayList<>();

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
