package com.android.bookmybook.util;

import com.android.bookmybook.R;
import com.android.bookmybook.model.Book;
import com.android.bookmybook.model.BooksList;
import com.android.bookmybook.model.Category;
import com.android.bookmybook.model.Tenure;
import com.android.bookmybook.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajit on 6/1/15.
 */
public class TestData {
    private static List<Category> categoryList;
    private static List<Tenure> tenureList;
    private static List<BooksList> categorizedBooksList;
    private static User user;

    static {
        user = new User();
        user.setUSER_ID("8");
        user.setNAME("AJIT");
        user.setSSID("123456");
        user.setBag(3);
        user.setBalance(330);
        user.setSeeks(2);
        user.setShares(4);
        user.setWishes(6);
    }

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

    static {
        categorizedBooksList = new ArrayList<>();
        List<Book> booksList = null;
        BooksList categoryBooksList = null;
        Book book = null;
        Category category = null;

        //-----------------------------------------------------------------
        categoryBooksList = new BooksList();
        booksList = new ArrayList<>();
        category = new Category();

        category.setCTGRY_NAME("SCI-FI");
        category.setCTGRY_IMGE(String.valueOf(R.drawable.science_fiction));

        book = new Book();
        book.setTITLE("ANGELS & DEMONS");
        book.setAUTHOR("DAN BROWN");
        book.setRENT(45);
        booksList.add(book);

        book = new Book();
        book.setTITLE("BRIEF HISTORY OF TIME");
        book.setAUTHOR("STEPHEN HAWKINGS");
        book.setRENT(80);
        booksList.add(book);

        book = new Book();
        book.setTITLE("ALICE IN WONDERLAND");
        book.setAUTHOR("LEWIS CAROL");
        book.setRENT(12);
        booksList.add(book);

        categoryBooksList.setCategory(category);
        categoryBooksList.setBooksCount(125);
        categoryBooksList.setBooksList(booksList);
        categorizedBooksList.add(categoryBooksList);
        //-----------------------------------------------------------------

        //-----------------------------------------------------------------
        categoryBooksList = new BooksList();
        booksList = new ArrayList<>();
        category = new Category();

        category.setCTGRY_NAME("ROMANCE");
        category.setCTGRY_IMGE(String.valueOf(R.drawable.romance));

        book = new Book();
        book.setTITLE("I TOO HAD A LOVE STORY");
        book.setAUTHOR("Ravinder Singh");
        book.setRENT(18);
        booksList.add(book);

        book = new Book();
        book.setTITLE("PORTRAIT OF A ROMANTIC");
        book.setAUTHOR("ISAAC ALBENIZ");
        book.setRENT(270);
        booksList.add(book);

        book = new Book();
        book.setTITLE("DYNAMICS OF LOVE");
        book.setAUTHOR("MARIO MIKULINCER");
        book.setRENT(96);
        booksList.add(book);

        book = new Book();
        book.setTITLE("LOVE, DRUGS & WAR");
        book.setAUTHOR("CHRIS WALLY");
        book.setRENT(412);
        booksList.add(book);

        categoryBooksList.setCategory(category);
        categoryBooksList.setBooksCount(48);
        categoryBooksList.setBooksList(booksList);
        categorizedBooksList.add(categoryBooksList);
        //-----------------------------------------------------------------

        //-----------------------------------------------------------------
        categoryBooksList = new BooksList();
        booksList = new ArrayList<>();
        category = new Category();

        category.setCTGRY_NAME("MYTHOLOGY");
        category.setCTGRY_IMGE(String.valueOf(R.drawable.mythology));

        book = new Book();
        book.setTITLE("RAMAYAN - AN EPIC MYTHOLOGY");
        book.setAUTHOR("HARISHANKAR BHAT");
        book.setRENT(110);
        booksList.add(book);

        book = new Book();
        book.setTITLE("COMMANDMENTS OF THE GOD");
        book.setAUTHOR("GEORGE IGNATIUS");
        book.setRENT(270);
        booksList.add(book);

        book = new Book();
        book.setTITLE("FROM THE EYES OF KUNTI");
        book.setAUTHOR("SHWETA ADHYAPAK");
        book.setRENT(80);
        booksList.add(book);

        book = new Book();
        book.setTITLE("HAND OF THE GOD");
        book.setAUTHOR("GURU RAM RAHIM SINGH");
        book.setRENT(100);
        booksList.add(book);

        categoryBooksList.setCategory(category);
        categoryBooksList.setBooksCount(89);
        categoryBooksList.setBooksList(booksList);
        categorizedBooksList.add(categoryBooksList);
        //-----------------------------------------------------------------


    }

    public static List<Category> getCategories(){
        return categoryList;
    }

    public static List<Tenure> getTenures(){
        return tenureList;
    }

    public static List<BooksList> getCategorizedBooksList() {
        return categorizedBooksList;
    }

    public static User getUser() {
        return user;
    }
}
