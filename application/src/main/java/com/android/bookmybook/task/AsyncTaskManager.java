package com.android.bookmybook.task;

import android.os.AsyncTask;
import android.util.Log;

import com.android.bookmybook.util.AsyncTaskUtility;

/**
 * Created by ajit on 4/1/17.
 */

public class AsyncTaskManager extends AsyncTask<String, Void, Object> {
    private static final String CLASS_NAME = AsyncTaskManager.class.getName();

    @Override
    protected Object doInBackground(String... purposeStrArr) {
        String purposeStr = purposeStrArr[0];

        if("REGISTER_USER".equalsIgnoreCase(purposeStr)){
            return AsyncTaskUtility.registerUser();
        }

        return -1;
    }

    @Override
    protected void onPostExecute(Object result) {
        Log.e(CLASS_NAME, result.toString());
    }
}
