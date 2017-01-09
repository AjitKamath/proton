package com.android.bookmybook.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.bookmybook.R;
import com.android.bookmybook.adapter.ListViewAdapterCategorizedBooks;
import com.android.bookmybook.fragment.ShareFragment;
import com.android.bookmybook.model.Book;
import com.android.bookmybook.model.BooksList;
import com.android.bookmybook.model.User;
import com.android.bookmybook.task.AsyncTaskImageLoader;
import com.android.bookmybook.task.AsyncTaskManager;
import com.android.bookmybook.util.AsyncTaskUtility;
import com.android.bookmybook.util.Utility;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.R.attr.fragment;
import static com.android.bookmybook.util.Constants.ASYNC_TASK_GET_BOOKS_ALL;
import static com.android.bookmybook.util.Constants.BOOK;
import static com.android.bookmybook.util.Constants.FRAGMENT_SHARE_BOOK;
import static com.android.bookmybook.util.Constants.LOGGED_IN_USER;
import static com.android.bookmybook.util.Constants.OK;
import static com.android.bookmybook.util.Constants.SERVER_ADDRESS;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener,AsyncTaskImageLoader.Listener {
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

    /*Async Task Manager*/
    private AsyncTaskManager asyncTaskManager = new AsyncTaskManager(); //TODO: move this to a class

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);

        initComponents();

        if(!fetchUser()){
            //TODO: force login
            return;
        }
        
        fetchBooks();
    }

    private boolean fetchUser() {
        user = new User();
        user.setUSER_ID("Ajit");

        return true;
    }

    private void fetchBooks() {
        BookTask bookTask = new BookTask();
        bookTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ASYNC_TASK_GET_BOOKS_ALL);
    }

    private void initComponents(){
        initToolbar();

        initNavigator();

        initFab();
    }

    private void initFab() {
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
            new AsyncTaskImageLoader(this).execute(SERVER_ADDRESS+"bookim/SHERLOCK%20HOMES.jpeg");
        }
        else if(R.id.fab_share_ll == view.getId()){
            showFabToolbar(false);
            showShareFragment(null);
        }
        else{
            Log.e(CLASS_NAME, "Could not identify the view");
            Utility.showSnacks(wrapper_home_cl, "Could not identify the view", OK, Snackbar.LENGTH_INDEFINITE);
        }
    }

    private void showShareFragment(Book book) {
        String fragmentNameStr = FRAGMENT_SHARE_BOOK;
        String parentFragmentNameStr = null;

        Bundle bundle = new Bundle();
        bundle.putSerializable(LOGGED_IN_USER, user);

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
    public void onImageLoaded(Bitmap bitmap) {
        fabtoolbar_fab.setImageBitmap(bitmap);
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Error Loading Image !", Toast.LENGTH_SHORT).show();
    }

    private void initNavigator() {
        //drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.setDrawerListener(toggle);
        toggle.syncState();

        //navigation
        nav_view.setItemIconTintList(null);
        nav_view.getMenu().getItem(1).setActionView(R.layout.menu_dot);
        nav_view.setNavigationItemSelectedListener(this);
    }

    private void initToolbar() {
        //toolbar
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }

        if(layout.isShown()){
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
            Intent i = new Intent(HomeActivity.this, RegistrationActivity.class);
            startActivity(i);
        }

        /*asyncTaskManager.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "REGISTER_USER");*/

        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    private class BookTask extends AsyncTask<String, Void, List<BooksList>> {
        @Override
        protected List<BooksList> doInBackground(String... urls) {
            if(ASYNC_TASK_GET_BOOKS_ALL.equalsIgnoreCase(urls[0])){
                return AsyncTaskUtility.fetchAllBooks();
            }
            else{
                //AsyncTaskUtility.getBook();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<BooksList> result) {
            ListViewAdapterCategorizedBooks adapter = new ListViewAdapterCategorizedBooks(mContext, result);
            categorizedBooksLV.setAdapter(adapter);
        }
    }
}
