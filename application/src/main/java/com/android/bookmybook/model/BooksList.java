package com.android.bookmybook.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ajit on 3/1/17.
 */

public class BooksList implements Serializable{
    private String category;
    private List<Book> booksList;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Book> getBooksList() {
        return booksList;
    }

    public void setBooksList(List<Book> booksList) {
        this.booksList = booksList;
    }
}
