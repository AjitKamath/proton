package com.android.bookmybook.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ajit on 3/1/17.
 */

public class BooksList implements Serializable{
    private Category category;
    private int booksCount;
    private List<Book> booksList;

    public int getBooksCount() {
        return booksCount;
    }

    public void setBooksCount(int booksCount) {
        this.booksCount = booksCount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Book> getBooksList() {
        return booksList;
    }

    public void setBooksList(List<Book> booksList) {
        this.booksList = booksList;
    }
}
