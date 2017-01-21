package com.android.bookmybook.util;

/**
 * Created by ajit on 10/1/17.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;

import static com.android.bookmybook.util.Constants.SHARED_PREF_FILE_NAME;

public class SharedPrefUtility {
    private final String CLASS_NAME = this.getClass().getName();
    private Context context;

    public SharedPrefUtility(Context context) {
        super();
        this.context = context;
    }

    public void save(String key, Object object) {
        if(context == null){
            Log.e(CLASS_NAME, "Could not store the Shared Preference as the context passed is null");
            return;
        }
        else if(key == null || key.trim().isEmpty()){
            Log.e(CLASS_NAME, "Could not store the Shared Preference as the Key passed is null/empty");
            return;
        }
        else if(object == null){
            Log.e(CLASS_NAME, "Could not store the Shared Preference as the value passed is null");
            return;
        }

        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        editor = settings.edit(); //2

        if(object instanceof String){
            editor.putString(key, (String)object);
        }
        else if(object instanceof HashSet){
            editor.putStringSet(key, (HashSet)object);
        }
        else{
            Log.e(CLASS_NAME, "Could not identify the object passed into the key("+key+")");
        }
        editor.commit(); //4
    }

    public Object getValue(String key) {
        SharedPreferences settings;
        String text;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        text = settings.getString(key, null);
        return text;
    }

    public void clearSharedPreference() {
        SharedPreferences settings;
        Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.clear();
        editor.commit();
    }

    public void removeValue(String key) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.remove(key);
        editor.commit();
    }
}
