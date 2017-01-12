package com.android.bookmybook.util;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.bookmybook.model.Category;
import com.android.bookmybook.model.Master;
import com.android.bookmybook.model.Tenure;
import com.android.bookmybook.model.User;
import com.android.bookmybook.task.AsyncTaskManager;

import java.util.List;

import static com.android.bookmybook.util.Constants.ASYNC_TASK_GET_CATEGORIES_ALL;
import static com.android.bookmybook.util.Constants.ASYNC_TASK_GET_TENURES_ALL;

public class CommonActivity extends AppCompatActivity{
    private static final String CLASS_NAME = CommonActivity.class.getName();
    private Context mContext = this;

    protected Master master = new Master();
    protected User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume(){
        super.onResume();

        //check if the user is connected to the internet
        if(!Utility.isNetworkAvailable(mContext)){
            FragmentManager fragment = getFragmentManager();
            Utility.showNoInternetFragment(fragment);

            return;
        }

        fetchMasterData();
    }

    private boolean fetchUser() {
        user = new User();
        user.setUSER_ID("Ajit");

        return true;
    }

    public void fetchMasterData() {
        if(!Utility.isNetworkAvailable(this)){
            Log.e(CLASS_NAME, "Internet connection unavailable.");
            return;
        }

        //fetch book categories
        new AsyncTaskManager(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ASYNC_TASK_GET_CATEGORIES_ALL);

        //fetch tenures
        new AsyncTaskManager(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ASYNC_TASK_GET_TENURES_ALL);
    }

    public void setupCategories(List<Category> categoriesList){
        master.setCategoriesList(categoriesList);
    }

    public void setupTenures(List<Tenure> tenuresList){
        master.setTenuresList(tenuresList);
    }
}