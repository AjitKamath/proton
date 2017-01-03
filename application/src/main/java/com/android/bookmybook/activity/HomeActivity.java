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

import com.android.bookmybook.R;
import com.android.bookmybook.util.Utility;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.android.bookmybook.util.Constants.OK;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String CLASS_NAME = Utility.class.getName();
    private Context mContext = this;

    /*components*/
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.nav_view)
    NavigationView nav_view;
    /*components*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);

        initComponents();
        
        loadBooks();
    }

    private void loadBooks() {
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

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String url = "http://192.168.43.253/bmb/register.php";
                String params = "code=3";

                URL test = null;
                try {
                    test = new URL(url);
                    HttpURLConnection urlConnection = (HttpURLConnection) test.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                    urlConnection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");

                    urlConnection.setDoOutput(true);
                    DataOutputStream ds = new DataOutputStream(urlConnection.getOutputStream());
                    ds.writeBytes(params);
                    ds.flush();
                    ds.close();

                    int resp = urlConnection.getResponseCode();


                    //urlConnection.setChunkedStreamingMode(0);

                }
                catch(Exception e){
                    String s ;
                }
                finally {
                    //test.disconnect();
                }

                return "";
            }


            @Override
            protected void onPostExecute(String html) {

            }
        }.execute();






        // ViewFlipper vf = (ViewFlipper) findViewById(R.id.vf);

        /*if (id == R.id.nav_camera) {
            Intent i = new Intent(this, BooksActivity.class);
            startActivity(i);
            finish();

            //vf.setDisplayedChild(0);
        } else if (id == R.id.nav_gallery) {
            //vf.setDisplayedChild(1);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }
}
