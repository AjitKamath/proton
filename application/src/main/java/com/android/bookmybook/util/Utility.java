package com.android.bookmybook.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.android.bookmybook.R;
import com.android.bookmybook.fragment.NoInternetFragment;
import com.android.bookmybook.fragment.ShareFragment;
import com.android.bookmybook.model.Category;
import com.android.bookmybook.model.Master;
import com.android.bookmybook.model.Tenure;
import com.android.bookmybook.task.AsyncTaskManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static com.android.bookmybook.util.Constants.AFFIRMATIVE;
import static com.android.bookmybook.util.Constants.ASYNC_TASK_GET_CATEGORIES_ALL;
import static com.android.bookmybook.util.Constants.ASYNC_TASK_GET_TENURES_ALL;
import static com.android.bookmybook.util.Constants.BOOK;
import static com.android.bookmybook.util.Constants.CHECK_MASTER_FOR_ALL;
import static com.android.bookmybook.util.Constants.CHECK_MASTER_FOR_CATEGORIES;
import static com.android.bookmybook.util.Constants.CHECK_MASTER_FOR_TENURES;
import static com.android.bookmybook.util.Constants.FRAGMENT_NO_INTERNET;
import static com.android.bookmybook.util.Constants.FRAGMENT_SHARE_BOOK;
import static com.android.bookmybook.util.Constants.LOGGED_IN_USER;
import static com.android.bookmybook.util.Constants.OK;

public class Utility extends Activity{

    private static final String CLASS_NAME = Utility.class.getName();

	public static void showSnacks(ViewGroup viewGroup, String messageStr, final String doWhatStr, int duration){
        Snackbar snackbar = Snackbar.make(viewGroup, messageStr, duration).setAction(doWhatStr, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //OK
                if(OK.equalsIgnoreCase(doWhatStr)){

                }
                else{
                    Log.e(CLASS_NAME, "Could not identify the action of the snacks");
                }
            }
        });

        snackbar.show();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean hasInternet = activeNetworkInfo != null && activeNetworkInfo.isConnected();

        if(!hasInternet){
            Log.i(CLASS_NAME, "Internet connection is available");
        }
        else{
            Log.i(CLASS_NAME, "No Internet connection available");
        }

        return hasInternet;
    }

    public static HttpURLConnection getHttpConnection(URL url, String method) throws Exception{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod(method);
        urlConnection.setRequestProperty("Content-length", "0");
        urlConnection.setUseCaches(false);
        urlConnection.setAllowUserInteraction(false);
        urlConnection.connect();
        return urlConnection;
    }

    public static String getResponseFromBMB(HttpURLConnection connection) throws Exception{
        int status = connection.getResponseCode();

        switch (status) {
            case 200:
            case 201:
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                br.close();
                return String.valueOf(sb);
        }

        return null;
    }

    public static Object jsonToObject(String jsonStr, Class mappingClass){
        if(jsonStr == null || jsonStr.isEmpty()){
            Log.e(CLASS_NAME, "JSON is null");
            return null;
        }
        else if (mappingClass == null){
            Log.e(CLASS_NAME, "No mapping class has been passed to map json into object");
            return null;
        }

        try{
            if(mappingClass.equals(Category.class)){
                Gson gson = new Gson();
                List<Category> list = gson.fromJson(jsonStr, new TypeToken<List<Category>>(){}.getType());
                return list;
            }
            else  if(mappingClass.equals(Tenure.class)){
                Gson gson = new Gson();
                List<Tenure> list = gson.fromJson(jsonStr, new TypeToken<List<Tenure>>(){}.getType());
                return list;
            }
            else{
                Log.e(CLASS_NAME, mappingClass+" is not identified for parsing JSON");
            }
        }
        catch (Exception e){
            Log.e(CLASS_NAME, "Error in parsing the json("+jsonStr+") into the mapping class("+mappingClass+")");
        }

        return null;
    }

    public static String escapePath(String str){
        return str.replace("\\", "\\\\").replaceAll("/", Matcher.quoteReplacement("\\/"));
    }

    public static Bitmap fetchThumbnailFromImage(Bitmap bitmap, int width, int height){
        return ThumbnailUtils.extractThumbnail(bitmap, width, height);
    }

    public String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static boolean hasMasterData(Master master, String purposeStr){
        if(master == null){
            Log.i(CLASS_NAME, "Master Data is null");
            return false;
        }

        boolean hasCategories = false;
        boolean hasTenures = false;

        if(master.getCategoriesList() != null && !master.getCategoriesList().isEmpty()){
            hasCategories = true;
        }

        if(master.getTenuresList() != null && !master.getTenuresList().isEmpty()){
            hasTenures = true;
        }

        Log.i(CLASS_NAME, "Master Data contains : ");
        Log.i(CLASS_NAME, "Categories("+hasCategories+")");
        Log.i(CLASS_NAME, "Tenures("+hasTenures+")");

        if(CHECK_MASTER_FOR_ALL.equalsIgnoreCase(purposeStr)){
            return hasCategories && hasTenures;
        }
        else if(CHECK_MASTER_FOR_CATEGORIES.equalsIgnoreCase(purposeStr)){
            return hasCategories;
        }
        else if(CHECK_MASTER_FOR_TENURES.equalsIgnoreCase(purposeStr)){
            return hasTenures;
        }
        else{
            Log.e(CLASS_NAME, "Invalid Purpose("+purposeStr+") passed to check master data");
        }
        return false;
    }

    public static void showNoInternetFragment(FragmentManager manager){
        String fragmentNameStr = FRAGMENT_NO_INTERNET;

        Fragment frag = manager.findFragmentByTag(fragmentNameStr);

        if (frag != null) {
            manager.beginTransaction().remove(frag).commit();
        }

        NoInternetFragment fragment = new NoInternetFragment();
        fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.fragment_theme);
        fragment.show(manager, fragmentNameStr);
    }

    public static Object fetchDefault(List<? extends Object> list){
        if(list == null || !(list instanceof ArrayList)){
            Log.e(CLASS_NAME, "List is null or the passed object is not of type arraylist");
            return null;
        }

        for(Object iterList : list){
            if(iterList instanceof Category){
                if(AFFIRMATIVE.equalsIgnoreCase(((Category)iterList).getIS_DEF())){
                    return iterList;
                }
            }
            else if(iterList instanceof Tenure){
                if(AFFIRMATIVE.equalsIgnoreCase(((Tenure)iterList).getIS_DEF())){
                    return iterList;
                }
            }
            else {
                Log.e(CLASS_NAME, "Could not identify the object type of the list");
                break;
            }
        }

        return null;
    }
}
