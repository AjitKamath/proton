package com.android.bookmybook.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.bookmybook.R;
import com.android.bookmybook.fragment.ShareFragment;
import com.android.bookmybook.model.Book;
import com.android.bookmybook.model.Category;
import com.android.bookmybook.model.Master;
import com.android.bookmybook.model.Tenure;
import com.android.bookmybook.model.User;
import com.android.bookmybook.task.AsyncTaskManager;
import com.android.bookmybook.util.SharedPrefUtility;
import com.android.bookmybook.util.TestData;
import com.android.bookmybook.util.Utility;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

import java.util.List;

import static com.android.bookmybook.util.Constants.ASYNC_TASK_GET_CATEGORIES_ALL;
import static com.android.bookmybook.util.Constants.ASYNC_TASK_GET_TENURES_ALL;
import static com.android.bookmybook.util.Constants.ASYNC_TASK_GET_USER;
import static com.android.bookmybook.util.Constants.BOOK;
import static com.android.bookmybook.util.Constants.CHECK_MASTER_FOR_ALL;
import static com.android.bookmybook.util.Constants.FRAGMENT_SHARE_BOOK;
import static com.android.bookmybook.util.Constants.LOGGED_IN_USER;
import static com.android.bookmybook.util.Constants.MASTER;
import static com.android.bookmybook.util.Constants.OK;
import static com.android.bookmybook.util.Constants.SERVER_TIMEOUT;
import static com.android.bookmybook.util.Constants.SSID;

public abstract class CommonActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
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

        //fetch user, tenures, categories
        fetchMasterData();

        setupToolbar();

        setupNavigator();

        setupFab();
    }

    public void fetchMasterData() {
        if(!Utility.isNetworkAvailable(this)){
            FragmentManager fragManager = getFragmentManager();
            Utility.showNoInternetFragment(fragManager);
            Log.e(CLASS_NAME, "Internet connection unavailable.");
            return;
        }

        //fetch user
        fetchUser();

        //monitor master
        monitorMasterData();
    }

    private void clearMasterData(){
        if(master != null){
            master = new Master();
        }
    }

    private void fetchUser(){
        /*fetch user*/
        Object object = new SharedPrefUtility(mContext).getValue(SSID);
        String request[] = new String[2];

        if(object == null){
            FragmentManager fragman = getFragmentManager();
            Utility.showLoginFragment(fragman);
            return;
        }
        else{
            request[1] = String.valueOf(object);
            request[0] = ASYNC_TASK_GET_USER;
            new AsyncTaskManager(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
        }
        /*fetch user*/
    }

    private void fetchCategories(){
        //fetch book categories
        new AsyncTaskManager(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ASYNC_TASK_GET_CATEGORIES_ALL);
    }

    private void fetchTenures(){
        //fetch tenures
        new AsyncTaskManager(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ASYNC_TASK_GET_TENURES_ALL);
    }

    private void monitorMasterData() {
        //show progress bar when loading master data
        progress = Utility.getProgressDialog(mContext, "loading app data ..");
        Utility.showProgress(progress);

        final Handler h = new Handler();

        h.postDelayed(new Runnable(){
            public void run(){
                if(master != null
                        && master.getTenuresList() != null && !master.getTenuresList().isEmpty()
                        && master.getCategoriesList() != null && !master.getCategoriesList().isEmpty()){
                    Utility.closeProgress(progress);
                    h.removeCallbacks(this);
                }
                //check what is missing in the master data and call its respective service
                else{
                    if(master == null || master.getCategoriesList() == null || master.getCategoriesList().isEmpty()){
                        fetchCategories();
                    }

                    if(master == null || master.getTenuresList() == null || master.getTenuresList().isEmpty()){
                        fetchTenures();
                    }

                    h.postDelayed(this, SERVER_TIMEOUT);
                }
            }
        }, SERVER_TIMEOUT);
    }

    public void setupCategories(List<Category> categoriesList){
        //TODO: not for production
        if(categoriesList == null || categoriesList.isEmpty()){
            master.setCategoriesList(TestData.getCategories());
            Utility.showToast(this, "WARNING : HARD CODED MASTER DATA");
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

    public void setupUser(User user) {
        //TODO: not for production
        if(user == null){
            master.setUser(TestData.getUser());
        }
        //TODO: not for production
        else{
            master.setUser(user);
        }

        //save ssid
        new SharedPrefUtility(mContext).save(SSID, master.getUser().getSSID());

        //setup account summary
        setupAccountSummary();

        //setup navigation drawer
        setupNavigationDrawer();
    }

    private void setupNavigationDrawer() {
        //set navigation drawer header
        if(master != null && master.getUser() != null){
            ((TextView)getNav_view().findViewById(R.id.common_nav_header_name_tv)).setText(master.getUser().getNAME());
            ((TextView)getNav_view().findViewById(R.id.common_nav_header_city_tv)).setText(master.getUser().getCITY());
        }
    }

    private void setupToolbar() {
        //toolbar
        //getToolbar().setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(getToolbar());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setupNavigator() {
        //drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, getDrawer_layout(), getToolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        getDrawer_layout().addDrawerListener(toggle);
        toggle.syncState();

        //navigation
        getNav_view().setItemIconTintList(null);
        getNav_view().getMenu().getItem(1).setActionView(R.layout.menu_dot);
        getNav_view().setNavigationItemSelectedListener(this);
    }

    private void showFabToolbar(boolean show){
        if(show){
            getLayout().show();
        }
        else{
            getLayout().hide();
        }
    }

    @Override
    public void onClick(View view) {
        if(R.id.fab_seek_ll == view.getId()){
        }
        else if(R.id.fab_share_ll == view.getId()){
            showFabToolbar(false);
            showShareFragment(null);
        }
        else{
            Log.e(CLASS_NAME, "Could not identify the view");
            Utility.showSnacks(getWrapper_home_cl(), "Could not identify the view", OK, Snackbar.LENGTH_INDEFINITE);
        }
    }

    private void setupFab() {
        getFabtoolbar_fab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFabToolbar(true);
            }
        });
        getFab_seek_ll().setOnClickListener(this);
        getFab_share_ll().setOnClickListener(this);
    }

    private void showShareFragment(Book book) {
        //check for master data
        if(!Utility.hasMasterData(master, CHECK_MASTER_FOR_ALL)){
            FragmentManager manager = getFragmentManager();
            Utility.showNoInternetFragment(manager);
            fetchMasterData();
            return;
        }

        String fragmentNameStr = FRAGMENT_SHARE_BOOK;
        String parentFragmentNameStr = null;

        Bundle bundle = new Bundle();
        bundle.putSerializable(MASTER, master);

        if(book != null){
            bundle.putSerializable(BOOK, book);
        }

        FragmentManager manager = getFragmentManager();
        Fragment frag = manager.findFragmentByTag(fragmentNameStr);

        if (frag != null) {
            manager.beginTransaction().remove(frag).commit();
        }

        Fragment parentFragment = null;
        if(parentFragmentNameStr != null && !parentFragmentNameStr.trim().isEmpty()){
            parentFragment = manager.findFragmentByTag(parentFragmentNameStr);
        }

        ShareFragment fragment = new ShareFragment();
        fragment.setArguments(bundle);
        fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.fragment_theme);

        if (parentFragment != null) {
            fragment.setTargetFragment(parentFragment, 0);
        }

        fragment.show(manager, fragmentNameStr);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if(R.id.navigation_drawer_logout == item.getItemId()){
            new SharedPrefUtility(mContext).clearSharedPreference();
            clearMasterData();
            setupAccountSummary();
        }
        else{
            Utility.showSnacks(getDrawer_layout(), "NOT IMPLEMENTED YET", OK, Snackbar.LENGTH_INDEFINITE);
            return true;
        }

        getDrawer_layout().closeDrawer(GravityCompat.START);
        onResume();
        return true;
    }

    protected abstract void setupAccountSummary();

    protected abstract DrawerLayout getDrawer_layout();

    protected abstract Toolbar getToolbar();

    protected abstract NavigationView getNav_view();

    protected abstract FABToolbarLayout getLayout();

    protected abstract FloatingActionButton getFabtoolbar_fab();

    protected abstract LinearLayout getFab_seek_ll();

    protected abstract LinearLayout getFab_share_ll();

    protected abstract CoordinatorLayout getWrapper_home_cl();
}
