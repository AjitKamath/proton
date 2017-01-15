package com.android.bookmybook.util;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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

    private ProgressDialog progress;

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
            FragmentManager fragManager = getFragmentManager();
            Utility.showNoInternetFragment(fragManager);
            Log.e(CLASS_NAME, "Internet connection unavailable.");
            return;
        }

        //show progress bar when loading master data
        progress = Utility.getProgressDiealog(mContext, "loading app data ..");
        showProgress(progress);

        //monitor master
        monitorMasterData();

        //fetch book categories
        new AsyncTaskManager(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ASYNC_TASK_GET_CATEGORIES_ALL);

        //fetch tenures
        new AsyncTaskManager(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ASYNC_TASK_GET_TENURES_ALL);
    }

    private void monitorMasterData() {
        final Handler h = new Handler();
        final int delay = 1000; //milliseconds

        h.postDelayed(new Runnable(){
            public void run(){
                if(master != null && master.getTenuresList() != null && !master.getTenuresList().isEmpty() && master.getCategoriesList() != null && !master.getCategoriesList().isEmpty()){
                    h.removeCallbacks(this);
                    closeProgress(progress);
                    return;
                }
                h.postDelayed(this, delay);
            }
        }, delay);
    }

    public void showProgress(ProgressDialog progress) {
        if(progress != null && !progress.isShowing()){
            progress.show();
        }
    }

    public void closeProgress(ProgressDialog progress){
        if(progress != null && progress.isShowing()){
            progress.dismiss();
        }
    }

    public void setupCategories(List<Category> categoriesList){
        //TODO: not for production
        if(categoriesList == null || categoriesList.isEmpty()){
            master.setCategoriesList(TestData.getCategories());
            return;
        }
        //TODO: not for production

        master.setCategoriesList(categoriesList);
    }

    public void setupTenures(List<Tenure> tenuresList){
        //TODO: not for production
        if(tenuresList == null || tenuresList.isEmpty()){
            master.setTenuresList(TestData.getTenures());
            return;
        }
        //TODO: not for production

        master.setTenuresList(tenuresList);
    }
}
