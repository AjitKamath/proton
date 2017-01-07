package com.android.bookmybook.util;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.android.bookmybook.model.Book;
import com.android.bookmybook.model.BooksList;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.android.bookmybook.util.Constants.OK;

public class AsyncTaskUtility extends Activity{

    private static final String CLASS_NAME = AsyncTaskUtility.class.getName();

	public static int registerUser(){
        String url = "http://192.168.43.253/bmb/register.php";
        String params = "code=3";

        try {
            URL test = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) test.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
            urlConnection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");


            urlConnection.setDoOutput(true);
            DataOutputStream ds = new DataOutputStream(urlConnection.getOutputStream());
            ds.writeBytes(params);
            ds.flush();
            ds.close();

            return urlConnection.getResponseCode();
        }
        catch(Exception e){
            Log.e(CLASS_NAME, e.getMessage());
        }

        return -1;
    }

    public static List<BooksList> fetchAllBooks(){
        String url = "http://192.168.43.253/bmb/register.php";
        String params = "code=3";

        try {
            /*URL test = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) test.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
            urlConnection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");

            urlConnection.setDoOutput(true);
            DataOutputStream ds = new DataOutputStream(urlConnection.getOutputStream());
            ds.writeBytes(params);
            ds.flush();
            ds.close();

            urlConnection.getResponseCode();*/


            List<BooksList> categorizedBooksList = new ArrayList<>();
            List<Book> booksList = null;
            BooksList categoryBooksList = null;
            Book book = null;

            //-----------------------------------------------------------------
            categoryBooksList = new BooksList();
            booksList = new ArrayList<>();
            categoryBooksList.setCategory("FICTION");

            book = new Book();
            book.setTITLE("MELUHA");
            book.setAUTHOR("AMISH");
            book.setRENT(45.0);
            booksList.add(book);

            book = new Book();
            book.setTITLE("THREE MISTAKES OF MY LIFE");
            book.setAUTHOR("CHETAN BHAGAT");
            book.setRENT(25.0);
            booksList.add(book);

            book = new Book();
            book.setTITLE("BRIEF HISTORY OF TIME");
            book.setAUTHOR("STEPHEN HAWKINGS");
            book.setRENT(80.0);
            booksList.add(book);

            book = new Book();
            book.setTITLE("ALICE IN WONDERLAND");
            book.setAUTHOR("LEWIS CARL");
            book.setRENT(12.0);
            booksList.add(book);
            categoryBooksList.setBooksList(booksList);
            categorizedBooksList.add(categoryBooksList);
            //-----------------------------------------------------------------

            //-----------------------------------------------------------------
            categoryBooksList = new BooksList();
            booksList = new ArrayList<>();
            categoryBooksList.setCategory("THRILLER");

            book = new Book();
            book.setTITLE("ARE YOU AFRAID OF THE DARK");
            book.setAUTHOR("SYDNEY SHELDON");
            book.setRENT(120.0);
            booksList.add(book);

            book = new Book();
            book.setTITLE("SYMBOLS");
            book.setAUTHOR("DAN BROWN");
            book.setRENT(25.0);
            booksList.add(book);

            book = new Book();
            book.setTITLE("KITE RUNNER");
            book.setAUTHOR("PAULO COELHO");
            book.setRENT(75.0);
            booksList.add(book);

            book = new Book();
            book.setTITLE("ANGELS & DEMONS");
            book.setAUTHOR("DAN BROWN");
            book.setRENT(200.0);
            booksList.add(book);

            categoryBooksList.setBooksList(booksList);
            categorizedBooksList.add(categoryBooksList);
            //-----------------------------------------------------------------

            return categorizedBooksList;
        }
        catch(Exception e){
            Log.e(CLASS_NAME, e.getMessage());
        }

        return null;
    }
}
