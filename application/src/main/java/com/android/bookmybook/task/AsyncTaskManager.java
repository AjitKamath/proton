package com.android.bookmybook.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.bookmybook.activity.HomeActivity;
import com.android.bookmybook.model.BooksList;
import com.android.bookmybook.model.Category;
import com.android.bookmybook.model.Tenure;
import com.android.bookmybook.util.AsyncTaskUtility;
import com.android.bookmybook.util.CommonActivity;

import java.util.List;

import static com.android.bookmybook.util.Constants.ASYNC_TASK_GET_BOOKS_ALL;
import static com.android.bookmybook.util.Constants.ASYNC_TASK_GET_CATEGORIES_ALL;
import static com.android.bookmybook.util.Constants.ASYNC_TASK_GET_TENURES_ALL;
import static com.android.bookmybook.util.Constants.ASYNC_TASK_REGISTER_USER;

/**
 * Created by ajit on 4/1/17.
 */

public class AsyncTaskManager extends AsyncTask<String, Void, Object> {
    private static final String CLASS_NAME = AsyncTaskManager.class.getName();

    private String PURPOSE;

    @Override
    protected Object doInBackground(String... purposeStrArr) {
        if(purposeStrArr == null || purposeStrArr.length == 0){
            Log.e(CLASS_NAME, "Purpose is not passed into Async Task");
            return -1;
        }

        PURPOSE = purposeStrArr[0];

        if(ASYNC_TASK_REGISTER_USER.equalsIgnoreCase(PURPOSE)){
            return AsyncTaskUtility.registerUser();
        }
        else if(ASYNC_TASK_GET_BOOKS_ALL.equalsIgnoreCase(PURPOSE)){
            return AsyncTaskUtility.fetchAllBooks();
        }
        else if(ASYNC_TASK_GET_CATEGORIES_ALL.equalsIgnoreCase(PURPOSE)){
            return AsyncTaskUtility.fetchAllCategories();
        }
        else if(ASYNC_TASK_GET_TENURES_ALL.equalsIgnoreCase(PURPOSE)){
            return AsyncTaskUtility.fetchAllTenures();
        }
        else{
            Log.e(CLASS_NAME, "The task("+PURPOSE+")");
        }

        return -1;
    }

    @Override
    protected void onPostExecute(Object result) {
        if(result == null){
            Log.i(CLASS_NAME, PURPOSE+" returned null");
            return;
        }

        if(PURPOSE.equalsIgnoreCase(ASYNC_TASK_GET_BOOKS_ALL)){
            if(activity != null){
                ((HomeActivity)activity).setupBooks((List<BooksList>)result);
            }
        }
        else if(PURPOSE.equalsIgnoreCase(ASYNC_TASK_GET_CATEGORIES_ALL)){
            if(activity != null){
                ((CommonActivity)activity).setupCategories((List<Category>)result);
            }
        }
        else if(PURPOSE.equalsIgnoreCase(ASYNC_TASK_GET_TENURES_ALL)){
            if(activity != null){
                ((CommonActivity)activity).setupTenures((List<Tenure>)result);
            }
        }
        else{
            Log.i(CLASS_NAME, "There's no logic defined to do post Async Task execute for the purpose("+PURPOSE+")");
        }
    }

    private Activity activity;
    private Fragment fragment;


    public AsyncTaskManager(Activity act){
        this.activity = act;
    }

    public AsyncTaskManager(Fragment frag){
        this.fragment = frag;
    }
}
