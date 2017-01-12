package com.android.bookmybook.activity;

import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import static com.android.bookmybook.R.id.login;
import static com.android.bookmybook.R.id.mobile;
import static com.android.bookmybook.R.id.password;
import static com.android.bookmybook.R.id.snackbar_action;
import static com.android.bookmybook.util.Constants.ASYNC_TASK_GET_BOOKS_ALL;
import static com.android.bookmybook.util.Constants.SERVER_ADDRESS;
import static com.android.bookmybook.util.Constants.SERVER_CHARSET;

public class LoginActivity extends AppCompatActivity {
    private static final String CLASS_NAME = LoginActivity.class.getName();
    private Context mContext = this;

    /*components*/

    @InjectView(R.id.login_act)
    LinearLayout act;

    @InjectView(R.id.email)
    EditText email;

    @InjectView(R.id.password)
    EditText password;

    @InjectView(R.id.sign_in_button)
    Button sign_in;

    /*components*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

    }

    @OnClick(R.id.sign_in_button)
    public void onSignup(View view) {
        if (validate_user()) {
            login();
        } else {
            Toast.makeText(getApplicationContext(), "Login Failed..!!", Toast.LENGTH_SHORT).show();
        }

    }

    String mail;
    String pwd;

    public boolean validate_user() {

        mail = String.valueOf(email.getText());
        pwd = String.valueOf(password.getText());


        if (mail.isEmpty()) {
            Utility.showSnacks(act,"PLEASE ENTER EMAIL","OK", Snackbar.LENGTH_INDEFINITE);
        } else if (!isValidMail(mail)) {
            Utility.showSnacks(act,"INVALID EMAIL","OK", Snackbar.LENGTH_INDEFINITE);
        } else if (pwd.isEmpty()) {
            Utility.showSnacks(act,"PLEASE ENTER PASSWORD","OK", Snackbar.LENGTH_INDEFINITE);
        }
        return true;
    }


    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void login()
    {
        new BookTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "k");
    }


    private class BookTask extends AsyncTask<String, Void, List<BooksList>> {
        @Override
        protected List<BooksList> doInBackground(String... urls) {
            if (ASYNC_TASK_GET_BOOKS_ALL.equalsIgnoreCase(urls[0])) {
                return AsyncTaskUtility.fetchAllBooks();
            } else {
                AsyncTaskUtility.login_authentication(mail, pwd);
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<BooksList> result) {
            Toast.makeText(getApplicationContext(), "Login Successfull..!!", Toast.LENGTH_SHORT).show();
        }

    }
}
