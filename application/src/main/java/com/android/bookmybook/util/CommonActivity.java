package com.android.bookmybook.util;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.bookmybook.model.Category;
import com.android.bookmybook.model.Master;
import com.android.bookmybook.model.Tenure;
import com.android.bookmybook.model.User;

import java.util.List;

public class CommonActivity extends AppCompatActivity{
    private static final String CLASS_NAME = CommonActivity.class.getName();
    private Context mContext = this;

    protected Master master = new Master();
    protected User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!Utility.isNetworkAvailable(mContext)){
            return;
        }

        fetchMasterData();
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
    }

    private boolean fetchUser() {
        user = new User();
        user.setUSER_ID("Ajit");

        return true;
    }

    private void fetchMasterData() {
        Utility.fetchMasterData(this);
    }

    public void setupCategories(List<Category> categoriesList){
        master.setCategoriesList(categoriesList);
    }

    public void setupTenures(List<Tenure> tenuresList){
        master.setTenuresList(tenuresList);
    }
}
