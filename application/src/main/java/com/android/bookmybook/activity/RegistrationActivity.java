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
import static com.android.bookmybook.R.id.mobile;
import static com.android.bookmybook.R.id.password;
import static com.android.bookmybook.R.id.snackbar_action;
import static com.android.bookmybook.util.Constants.ASYNC_TASK_GET_BOOKS_ALL;
import static com.android.bookmybook.util.Constants.SERVER_ADDRESS;
import static com.android.bookmybook.util.Constants.SERVER_CHARSET;

public class RegistrationActivity extends AppCompatActivity {
    private static final String CLASS_NAME = RegistrationActivity.class.getName();
    private Context mContext = this;

    /*components*/

    @InjectView(R.id.act_full)
    LinearLayout act;

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

    String mble;
    String mail;
    String pwd;
    String nm;
    String gnd;
    String cty;

    public boolean validate_inputs() {

        mble = String.valueOf(mobile.getText());
        mail = String.valueOf(email.getText());
        pwd = String.valueOf(password.getText());
        nm = String.valueOf(name.getText());
        int gnd_id = gender.getCheckedRadioButtonId();
        RadioButton rd = (RadioButton)findViewById(gnd_id);
        gnd = String.valueOf(rd.getText());
        cty = String.valueOf(city.getText());


        if (mble.isEmpty()) {
            Utility.showSnacks(act,"PLEASE ENTER MOBILE","OK", Snackbar.LENGTH_INDEFINITE);
        } else if (mble.length() < 10) {
            Utility.showSnacks(act,"INVALID MOBILE NUMBER","OK", Snackbar.LENGTH_INDEFINITE);
        } else if (!isValidMobile(mble)) {
            Utility.showSnacks(act,"INVALID MOBILE NUMBER","OK", Snackbar.LENGTH_INDEFINITE);
        } else if (mail.isEmpty()) {
            Utility.showSnacks(act,"PLEASE ENTER EMAIL","OK", Snackbar.LENGTH_INDEFINITE);
        } else if (!isValidMail(mail)) {
            Utility.showSnacks(act,"INVALID EMAIL","OK", Snackbar.LENGTH_INDEFINITE);
        } else if (pwd.isEmpty()) {
            Utility.showSnacks(act,"PLEASE ENTER PASSWORD","OK", Snackbar.LENGTH_INDEFINITE);
        } else if (nm.isEmpty()) {
            Utility.showSnacks(act,"PLEASE ENTER NAME","OK", Snackbar.LENGTH_INDEFINITE);
        } else if (gnd.isEmpty()) {
            Utility.showSnacks(act,"PLEASE SELECT GENDER","OK", Snackbar.LENGTH_INDEFINITE);
        } else if (cty.isEmpty()) {
            Utility.showSnacks(act,"PLEASE ENTER CITY","OK", Snackbar.LENGTH_INDEFINITE);
        }

        return true;
    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void register()
    {
        new BookTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "k");
    }


    private class BookTask extends AsyncTask<String, Void, List<BooksList>> {
        @Override
        protected List<BooksList> doInBackground(String... urls) {
            if (ASYNC_TASK_GET_BOOKS_ALL.equalsIgnoreCase(urls[0])) {
                return AsyncTaskUtility.fetchAllBooks();
            } else {
                AsyncTaskUtility.addNewUser(mble, mail, pwd, nm, gnd, cty);
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<BooksList> result) {
            Toast.makeText(getApplicationContext(), "Registration Successfull..!!", Toast.LENGTH_SHORT).show();
        }

    }
}
