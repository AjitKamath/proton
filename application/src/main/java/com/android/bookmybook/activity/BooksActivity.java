package com.android.bookmybook.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.android.bookmybook.R;
import com.android.bookmybook.model.BooksList;
import com.android.bookmybook.util.AsyncTaskUtility;
import com.android.bookmybook.util.Utility;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BooksActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String CLASS_NAME = Utility.class.getName();
    private Context mContext = this;

    /*components*/
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

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
        setContentView(R.layout.activity_books);
        ButterKnife.inject(this);

        initComponents();
    }

    private void initComponents(){
        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //navigation
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //fab
        /*ImageButton fab = (ImageButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.showSnacks(drawer_layout, "Replace with your own action", OK, Snackbar.LENGTH_LONG);
            }
        });*/
    }

    private void initFab() {
        fabtoolbar_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.show();
            }
        });

        fab_seek_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = Environment.getExternalStorageDirectory();

                //new AsyncTaskUtility().uploadFiles()
            }
        });

        fab_seek_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new BookTask().execute("");


            }
        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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

        return super.onOptionsItemSelected(item);
    }

    private class BookTask extends AsyncTask<String, Void, List<BooksList>> {
        @Override
        protected List<BooksList> doInBackground(String... urls) {
             //AsyncTaskUtility.test();

            return null;
        }

        @Override
        protected void onPostExecute(List<BooksList> result) {
            //ListViewAdapterCategorizedBooks adapter = new ListViewAdapterCategorizedBooks(mContext, result);
            //categorizedBooksLV.setAdapter(adapter);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        new BookTask().execute("");

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        //ViewFlipper vf = (ViewFlipper) findViewById(R.id.vf);

        /*if (id == R.id.nav_camera) {
            //vf.setDisplayedChild(0);
        } else if (id == R.id.nav_gallery) {
            //vf.setDisplayedChild(1);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
