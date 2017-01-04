package com.android.bookmybook.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.bookmybook.R;
import com.android.bookmybook.adapter.CategorizedBooksListViewAdapter;
import com.android.bookmybook.model.BooksList;
import com.android.bookmybook.task.AsyncTaskManager;
import com.android.bookmybook.util.AsyncTaskUtility;
import com.android.bookmybook.util.Utility;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.android.bookmybook.util.Constants.ASYNC_TASK_GET_BOOKS_ALL;
import static com.android.bookmybook.util.Constants.OK;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String CLASS_NAME = HomeActivity.class.getName();
    private Context mContext = this;

    /*components*/
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.nav_view)
    NavigationView nav_view;

    @InjectView(R.id.categorizedBooksLV)
    ListView categorizedBooksLV;
    /*components*/

    /*Async Task Manager*/
    private AsyncTaskManager asyncTaskManager = new AsyncTaskManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);

        initComponents();
        
        fetchBooks();
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
        //fab
        ImageButton fab = (ImageButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.showSnacks(drawer_layout, "Replace with your own action", OK, Snackbar.LENGTH_LONG);
            }
        });
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

        asyncTaskManager.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "REGISTER_USER");

        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    private class BookTask extends AsyncTask<String, Void, List<BooksList>> {
        @Override
        protected List<BooksList> doInBackground(String... urls) {
            return AsyncTaskUtility.fetchAllBooks();
        }

        @Override
        protected void onPostExecute(List<BooksList> result) {
            CategorizedBooksListViewAdapter adapter = new CategorizedBooksListViewAdapter(mContext, result);
            categorizedBooksLV.setAdapter(adapter);
        }
    }
}
