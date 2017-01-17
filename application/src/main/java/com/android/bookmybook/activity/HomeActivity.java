package com.android.bookmybook.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.bookmybook.R;
import com.android.bookmybook.adapter.ListViewAdapterCategorizedBooks;
import com.android.bookmybook.fragment.ShareFragment;
import com.android.bookmybook.model.Book;
import com.android.bookmybook.model.BooksList;
import com.android.bookmybook.task.AsyncTaskManager;
import com.android.bookmybook.util.CommonActivity;
import com.android.bookmybook.util.Utility;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.android.bookmybook.util.Constants.ASYNC_TASK_GET_BOOKS_ALL;
import static com.android.bookmybook.util.Constants.BOOK;
import static com.android.bookmybook.util.Constants.CHECK_MASTER_FOR_ALL;
import static com.android.bookmybook.util.Constants.FRAGMENT_SHARE_BOOK;
import static com.android.bookmybook.util.Constants.LOGGED_IN_USER;
import static com.android.bookmybook.util.Constants.MASTER;
import static com.android.bookmybook.util.Constants.OK;

public class HomeActivity extends CommonActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private static final String CLASS_NAME = HomeActivity.class.getName();
    private Context mContext = this;

    /*components*/
    @InjectView(R.id.wrapper_home_cl)
    CoordinatorLayout wrapper_home_cl;

    @InjectView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.nav_view)
    NavigationView nav_view;

    @InjectView(R.id.categorizedBooksLV)
    ListView categorizedBooksLV;

    @InjectView(R.id.fabtoolbar)
    FABToolbarLayout layout;

    @InjectView(R.id.fabtoolbar_fab)
    FloatingActionButton fabtoolbar_fab;

    @InjectView(R.id.fab_seek_ll)
    LinearLayout fab_seek_ll;

    @InjectView(R.id.fab_share_ll)
    LinearLayout fab_share_ll;
    /*components*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);

        initComponents();

        setupPage();
    }

    private void initComponents(){
        setupToolbar();

        setupNavigator();

        setupFab();
    }

    private void setupFab() {
        fabtoolbar_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFabToolbar(true);
            }
        });

        fab_seek_ll.setOnClickListener(this);

        fab_share_ll.setOnClickListener(this);

    }

    private void showFabToolbar(boolean show){
        if(show){
            layout.show();
        }
        else{
            layout.hide();
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
       /* else if(R.id.loginItem == view.getId()){
            showLoginFragment(null);
        }*/
        else{
            Log.e(CLASS_NAME, "Could not identify the view");
            Utility.showSnacks(wrapper_home_cl, "Could not identify the view", OK, Snackbar.LENGTH_INDEFINITE);
        }
    }

    private void showRegistrationFragment(Book book) {
        //check for internet
        if (!Utility.isNetworkAvailable(this)) {
            FragmentManager fragMan = getFragmentManager();
            Utility.showNoInternetFragment(fragMan);
            return;
        } else {
            FragmentManager fragMan = getFragmentManager();
            Utility.showRegistrationFragment(fragMan);
            return;
        }
    }

    private void showLoginFragment(Book book) {
        //check for internet
        if (!Utility.isNetworkAvailable(this)) {
            FragmentManager fragMan = getFragmentManager();
            Utility.showNoInternetFragment(fragMan);
            return;
        } else {
            FragmentManager fragMan = getFragmentManager();
            Utility.showLoginFragment(fragMan);
            return;
        }
    }

    private void showShareFragment(Book book) {
        //check for internet
        if(!Utility.isNetworkAvailable(this)){
            FragmentManager fragMan = getFragmentManager();
            Utility.showNoInternetFragment(fragMan);
            return;
        }else{
            fetchMasterData();
        }

        //check for master data
        if(!Utility.hasMasterData(master, CHECK_MASTER_FOR_ALL)){
            FragmentManager manager = getFragmentManager();
            Utility.showNoInternetFragment(manager);
            return;
        }

        String fragmentNameStr = FRAGMENT_SHARE_BOOK;
        String parentFragmentNameStr = null;

        Bundle bundle = new Bundle();
        bundle.putSerializable(LOGGED_IN_USER, user);
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

    private void setupNavigator() {
        //drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.setDrawerListener(toggle);
        toggle.syncState();

        //navigation
        nav_view.setItemIconTintList(null);
        nav_view.getMenu().getItem(1).setActionView(R.layout.menu_dot);
        nav_view.setNavigationItemSelectedListener(this);
    }

    private void setupToolbar() {
        //toolbar
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setupPage(){
        if(!Utility.isNetworkAvailable(this)){
            return;
        }

        //fetch books
        new AsyncTaskManager(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ASYNC_TASK_GET_BOOKS_ALL);
    }

    public void setupBooks(List<BooksList> booksList) {
        if(booksList == null || (booksList != null && booksList.isEmpty())){
            return;
        }

        ListViewAdapterCategorizedBooks adapter = new ListViewAdapterCategorizedBooks(mContext, booksList);
        categorizedBooksLV.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }

        if(layout.isToolbar()){
            layout.hide();
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.accountItem)
        {
            showRegistrationFragment(null);
            //Intent i = new Intent(HomeActivity.this, RegistrationActivity.class);
            //startActivity(i);
        }
       else if(id == R.id.loginItem)
        {
            showLoginFragment(null);
            //Intent i = new Intent(HomeActivity.this, LoginActivity.class);
            //startActivity(i);
        }

        /*asyncTaskManager.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "REGISTER_USER");*/

        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }
}
