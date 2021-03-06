package com.android.bookmybook.util;

import android.app.Activity;
import android.util.Log;

import com.android.bookmybook.model.Book;
import com.android.bookmybook.model.BooksList;
import com.android.bookmybook.model.Category;
import com.android.bookmybook.model.Tenure;
import com.android.bookmybook.model.User;

import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.android.bookmybook.util.Constants.PHP_FETCH_ALL_CATEGORIES;
import static com.android.bookmybook.util.Constants.PHP_FETCH_ALL_TENURES;
import static com.android.bookmybook.util.Constants.PHP_FETCH_USER;
import static com.android.bookmybook.util.Constants.PHP_POST_BOOK;
import static com.android.bookmybook.util.Constants.SERVER_ADDRESS;
import static com.android.bookmybook.util.Constants.SERVER_CHARSET;
import static com.android.bookmybook.util.Constants.SERVER_TIMEOUT;
import static com.android.bookmybook.util.Constants.SLASH;
import static com.android.bookmybook.util.Constants.SSID;

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
            urlConnection.setConnectTimeout(SERVER_TIMEOUT);

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

    public static String shareBook(Book book){
        try {
            File file = new File(book.getImagePath());

            MultipartUtility multipart = new MultipartUtility(SERVER_ADDRESS+SLASH+PHP_POST_BOOK, SERVER_CHARSET);
            multipart.addFilePart("image", file);
            multipart.addFormField("title", book.getTITLE());
            multipart.addFormField("category_id", book.getCTGRY_ID());
            multipart.addFormField("author", book.getAUTHOR());
            multipart.addFormField("publication", book.getPUBLICATION());
            multipart.addFormField("description", book.getDESCRIPTION());
            multipart.addFormField("user_id", book.getUSER_ID());
            multipart.addFormField("rent", String.valueOf(book.getRENT()));
            multipart.addFormField("min_duration", book.getMIN_DURATION());
            multipart.addFormField("max_duration", book.getMAX_DURATION());

            return multipart.finish(); // response from server.
        }
        catch(EOFException e){
            Log.e(CLASS_NAME, e.getMessage()+" .. retrying");
            //shareBook(book);
        }
        catch(Exception e){
            Log.e(CLASS_NAME, e.getMessage());
        }

        return "";
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




            return TestData.getCategorizedBooksList();
        }
        catch(Exception e){
            Log.e(CLASS_NAME, e.getMessage());
        }

        return null;
    }

    public static String login_authentication(String email,String password)
    {
        try {
            MultipartUtility multipart = new MultipartUtility(SERVER_ADDRESS+SLASH+"login.php", SERVER_CHARSET);

            multipart.addFormField("email", email);
            multipart.addFormField("password", password);

            String response = multipart.finish();// response from server.
            System.out.println(response);
            return response;
        }
        catch(Exception e) {
            Log.e(CLASS_NAME, e.getMessage());
        }
        return "";
    }

    public static String addNewUser(String mobile,String email,String password)
    {
        try {
            MultipartUtility multipart = new MultipartUtility(SERVER_ADDRESS+SLASH+"register.php", SERVER_CHARSET);

            multipart.addFormField("code", "1");
            multipart.addFormField("email", email);
            multipart.addFormField("mobile", mobile);
            multipart.addFormField("password", password);

            String response = multipart.finish();// response from server.
            System.out.println(response);
            return  response;
        }
        catch(Exception e) {
            Log.e(CLASS_NAME, e.getMessage());
        }
        return null;
    }

    public static Object fetchAllCategories() {
        try {
            URL url = new URL(SERVER_ADDRESS+SLASH+PHP_FETCH_ALL_CATEGORIES);
            HttpURLConnection connection = Utility.getHttpConnection(url, "GET");
            String jsonStr = Utility.getResponseFromBMB(connection);
            return Utility.jsonToObject(jsonStr, Category.class);
        }
        catch (Exception e){
            Log.e(CLASS_NAME, "Could not fetch Categories from the server : "+e);
        }

        return null;
    }

    public static Object fetchAllTenures() {
        try {
            URL url = new URL(SERVER_ADDRESS+SLASH+PHP_FETCH_ALL_TENURES);
            HttpURLConnection connection = Utility.getHttpConnection(url, "GET");
            String jsonStr = Utility.getResponseFromBMB(connection);
            return Utility.jsonToObject(jsonStr, Tenure.class);
        }
        catch (Exception e){
            Log.e(CLASS_NAME, "Could not fetch Tenures from the server : "+e);
        }

        return null;
    }

    public static Object fetchUser(String ssid) {
        try {
            MultipartUtility multipart = new MultipartUtility(SERVER_ADDRESS+SLASH+PHP_FETCH_USER, SERVER_CHARSET);
            multipart.addFormField("ssid", ssid);

            String response = multipart.finish();
            return  response;
        }
        catch (Exception e){
            Log.e(CLASS_NAME, "Could not fetch User from the server : "+e);
        }

        return null;
    }
}
