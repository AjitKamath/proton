package com.android.bookmybook.activity;

import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.bookmybook.R;
import com.android.bookmybook.adapter.ListViewAdapterCategorizedBooks;
import com.android.bookmybook.model.BooksList;
import com.android.bookmybook.util.AsyncTaskUtility;
import com.android.bookmybook.util.MultipartUtility;
import com.android.bookmybook.util.Utility;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.android.bookmybook.R.id.gender;
import static com.android.bookmybook.R.id.mobile;
import static com.android.bookmybook.R.id.password;
import static com.android.bookmybook.util.Constants.ASYNC_TASK_GET_BOOKS_ALL;
import static com.android.bookmybook.util.Constants.SERVER_ADDRESS;
import static com.android.bookmybook.util.Constants.SERVER_CHARSET;

public class RegistrationActivity extends AppCompatActivity {
    private static final String CLASS_NAME = RegistrationActivity.class.getName();
    private Context mContext = this;

    /*components*/
    @InjectView(R.id.user_id)
    EditText user_id;

    @InjectView(R.id.mobile)
    EditText mobile;

    @InjectView(R.id.email)
    EditText email;

    @InjectView(R.id.password)
    EditText password;

    @InjectView(R.id.name)
    EditText name;

    @InjectView(R.id.gender)
    RadioGroup gender;

    @InjectView(R.id.city)
    EditText city;

    @InjectView(R.id.sign_up_button)
    Button sign_up;

    /*components*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.inject(this);

    }

    @OnClick(R.id.sign_up_button)
    public void onSignup(View view) {
        if (validate_inputs()) {
            register();
        } else {
            Toast.makeText(getApplicationContext(), "Registration Failed..!!", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean validate_inputs() {
        if (user_id.equals(null)) {
            Toast.makeText(getApplicationContext(), "ENTER USER NAME", Toast.LENGTH_SHORT).show();
        } else if (mobile.equals(null)) {
            Toast.makeText(getApplicationContext(), "ENTER MOBILE NUMBER", Toast.LENGTH_SHORT).show();
        } else if (email.equals(null)) {
            Toast.makeText(getApplicationContext(), "ENTER EMAIL ID", Toast.LENGTH_SHORT).show();
        } else if (password.equals(null)) {
            Toast.makeText(getApplicationContext(), "ENTER PASSWORD", Toast.LENGTH_SHORT).show();
        } else if (name.equals(null)) {
            Toast.makeText(getApplicationContext(), "ENTER NAME", Toast.LENGTH_SHORT).show();
        } else if (gender.equals(null)) {
            Toast.makeText(getApplicationContext(), "CHOOSE gender", Toast.LENGTH_SHORT).show();
        } else if (city.equals(null)) {
            Toast.makeText(getApplicationContext(), "ENTER CITY", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    public void register() {

        userid = String.valueOf(user_id.getText());
        mble = String.valueOf(mobile.getText());
        mail = String.valueOf(email.getText());
        pwd = String.valueOf(password.getText());
        nm = String.valueOf(name.getText());
        gnd = String.valueOf(gender.getCheckedRadioButtonId());
        cty = String.valueOf(city.getText());

        new BookTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "k");

    }

    String userid;
    String mble;
    String mail;
    String pwd;
    String nm;
    String gnd;
    String cty;

    private class BookTask extends AsyncTask<String, Void, List<BooksList>> {
        @Override
        protected List<BooksList> doInBackground(String... urls) {
            if (ASYNC_TASK_GET_BOOKS_ALL.equalsIgnoreCase(urls[0])) {
                return AsyncTaskUtility.fetchAllBooks();
            } else {
                AsyncTaskUtility.addNewUser(userid, mble, mail, pwd, nm, gnd, cty);
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<BooksList> result) {
            Toast.makeText(getApplicationContext(), "Registration Successfull..!!", Toast.LENGTH_SHORT).show();
        }

    }
}
