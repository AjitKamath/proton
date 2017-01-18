package com.android.bookmybook.util;

import com.android.bookmybook.model.Category;
import com.android.bookmybook.model.Tenure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajit on 6/1/15.
 */
public class TestData {
    private static List<Category> categoryList;
    private static List<Tenure> tenureList;

    static {
        categoryList = new ArrayList<>();
        Category category = null;
        int count = 5;
        int indexOfDefault = 0;
        String baseValueStr = "category";

        for(int i=0; i<count; i++){
            category = new Category();

            if(i==indexOfDefault){
                category.setIS_DEF("Y");
            }

            category.setCTGRY_ID(String.valueOf(i));
            category.setCTGRY_NAME(baseValueStr+i);
            categoryList.add(category);
        }
    }

    static {
        tenureList = new ArrayList<>();
        Tenure tenure = null;
        int count = 5;
        int indexOfDefault = 0;
        String baseValueStr = "tenure";

        for(int i=0; i<count; i++){
            tenure = new Tenure();

            if(i==indexOfDefault){
                tenure.setIS_DEF("Y");
            }

            tenure.setTENURE_ID(String.valueOf(i));
            tenure.setNO_OF_DAYS(i+1);
            tenure.setTENURE_NAME(baseValueStr+i);
            tenureList.add(tenure);
        }
    }

    public static List<Category> getCategories(){
        return categoryList;
    }

    public static List<Tenure> getTenures(){
        return tenureList;
    }
}
