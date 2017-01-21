package com.android.bookmybook.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.android.bookmybook.util.Utility;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.android.bookmybook.util.Constants.ASYNC_TASK_GET_BOOKS_ALL;
import static com.android.bookmybook.util.Constants.ASYNC_TASK_GET_USER;
import static com.android.bookmybook.util.Constants.BOOK;
import static com.android.bookmybook.util.Constants.CHECK_MASTER_FOR_ALL;
import static com.android.bookmybook.util.Constants.FRAGMENT_SHARE_BOOK;
import static com.android.bookmybook.util.Constants.LOGGED_IN_USER;
import static com.android.bookmybook.util.Constants.MASTER;
import static com.android.bookmybook.util.Constants.OK;

public class HomeActivity extends CommonActivity {
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

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);

        initComponents();

        setupPage();
    }

    private void initComponents(){

    }

    private void setupPage(){
        if(!Utility.isNetworkAvailable(this)){
            FragmentManager fragManager = getFragmentManager();
            Utility.showNoInternetFragment(fragManager);
            return;
        }

        //show progress dialog untill page loads
        progress = Utility.getProgressDialog(mContext, "loading books ..");
        Utility.showProgress(progress);

        //fetch books
        new AsyncTaskManager(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ASYNC_TASK_GET_BOOKS_ALL);
    }

    public void setupBooks(List<BooksList> booksList) {
        if(booksList == null || (booksList != null && booksList.isEmpty())){
            return;
        }

        ListViewAdapterCategorizedBooks adapter = new ListViewAdapterCategorizedBooks(mContext, booksList);
        categorizedBooksLV.setAdapter(adapter);

        Utility.closeProgress(progress);
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

    @Override
    protected DrawerLayout getDrawer_layout() {
        return drawer_layout;
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected NavigationView getNav_view() {
        return nav_view;
    }

    @Override
    protected FABToolbarLayout getLayout() {
        return layout;
    }

    @Override
    protected FloatingActionButton getFabtoolbar_fab() {
        return fabtoolbar_fab;
    }

    @Override
    protected LinearLayout getFab_seek_ll() {
        return fab_seek_ll;
    }

    @Override
    protected LinearLayout getFab_share_ll() {
        return fab_share_ll;
    }

    @Override
    protected CoordinatorLayout getWrapper_home_cl() {
        return wrapper_home_cl;
    }
}
