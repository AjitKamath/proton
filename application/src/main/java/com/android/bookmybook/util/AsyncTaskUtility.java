package com.android.bookmybook.util;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.android.bookmybook.model.Book;
import com.android.bookmybook.model.BooksList;

import java.io.DataOutputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.android.bookmybook.util.Constants.OK;
import static com.android.bookmybook.util.Constants.SERVER_ADDRESS;
import static com.android.bookmybook.util.Constants.SERVER_CHARSET;
import static com.android.bookmybook.util.Constants.SERVER_PROJECT_DIRECTORY;

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

    public static boolean uploadFiles(List<File> filesList, String userIdStr, String purposeStr){
        if (filesList == null || filesList.isEmpty()) {
            return false;
        }

        if (userIdStr == null || userIdStr.trim().isEmpty()) {
            return false;
        }

        if (purposeStr == null || purposeStr.trim().isEmpty()) {
            return false;
        }

        boolean uploadFail = false;
        try {
            MultipartUtility multipart = new MultipartUtility(SERVER_ADDRESS, SERVER_CHARSET);

            for(File iterFiles: filesList){
                multipart.addFilePart("file", iterFiles);
                multipart.addFormField("user_id", userIdStr);
                multipart.addFormField("purpose", purposeStr);

                String response = multipart.finish(); // response from server.

                if(response.equalsIgnoreCase("-1")){
                    uploadFail = true;
                }
            }
        }
        catch(Exception e){
            Log.e(CLASS_NAME, e.getMessage());
        }

        return uploadFail;
    }

    public static boolean test(){
        File file = new File("/storage/emulated/0/DCIM/Flipkart/IMG_1419894120735.jpeg");
        boolean uploadFail = false;
        try {
            MultipartUtility multipart = new MultipartUtility(SERVER_ADDRESS+"imupload.php", SERVER_CHARSET);

            multipart.addFilePart("image", file);
            multipart.addFormField("book_id", "7");
            multipart.addFormField("title", "SHERLOCK HOMES");
            multipart.addFormField("category_id", "1");
            multipart.addFormField("author", "DANNY BOYLE");
            multipart.addFormField("publication", "DEEPA PUBLICATIONS");
            multipart.addFormField("description", "WORST BOOK EVER");
            multipart.addFormField("user_id", "Ajit");
            multipart.addFormField("rent", "200");
            multipart.addFormField("min_duration", "1");
            multipart.addFormField("max_duration", "1");

            String response = multipart.finish(); // response from server.
        }
        catch(Exception e){
            Log.e(CLASS_NAME, e.getMessage());
        }

        return uploadFail;
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
