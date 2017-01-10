package com.android.bookmybook.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.android.bookmybook.model.Category;
import com.android.bookmybook.model.Tenure;
import com.android.bookmybook.task.AsyncTaskManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;

import static com.android.bookmybook.util.Constants.ASYNC_TASK_GET_CATEGORIES_ALL;
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
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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

    public static void fetchMasterData(Activity activity) {
        //fetch book categories
        new AsyncTaskManager(activity).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ASYNC_TASK_GET_CATEGORIES_ALL);

        //fetch tenures
        //new AsyncTaskManager(activity).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ASYNC_TASK_GET_TENURES_ALL);
    }

    public static String escapePath(String str){
        return str.replace("\\", "\\\\").replaceAll("/", Matcher.quoteReplacement("\\/"));
    }
}
