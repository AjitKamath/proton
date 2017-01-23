package com.android.bookmybook.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.bookmybook.R;
import com.android.bookmybook.model.Response;
import com.android.bookmybook.util.AsyncTaskUtility;
import com.android.bookmybook.util.Utility;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class RegistrationFragment extends DialogFragment {
    private final String CLASS_NAME = this.getClass().getName();
    private Context mContext;

    /*components*/
    @InjectView(R.id.registration_act)
    LinearLayout act;

    @InjectView(R.id.email)
    EditText email;

    @InjectView(R.id.password)
    EditText password;

    @InjectView(R.id.sign_up_button)
    Button sign_in;

    /*components*/

    private Integer choice = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container);
        ButterKnife.inject(this, view);

        Dialog d = getDialog();
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);

        return view;
    }

    public RegistrationFragment() {
    }

    @OnClick(R.id.fragment_registration_signin_tv)
    public void onSignin(View view) {
        FragmentManager fragMan = getFragmentManager();
        Utility.showLoginFragment(fragMan);

        dismiss();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog d = getDialog();
        if (d != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            ;
            d.getWindow().setLayout(width, height);
            d.setCancelable(false);

        }
    }

    @OnClick(R.id.sign_up_button)
    public void onSignup(View view) {
        if (validate_inputs()) {
            register();
        } else {
            Utility.showSnacks(act, "Registration Failed..!!", "OK", Snackbar.LENGTH_INDEFINITE);
        }
    }

    String mble;
    String mail;
    String pwd;

    public boolean validate_inputs() {
        mail = String.valueOf(email.getText());
        pwd = String.valueOf(password.getText());

        if (mble.isEmpty()) {
            Utility.showSnacks(act, "PLEASE ENTER MOBILE", "OK", Snackbar.LENGTH_INDEFINITE);
        } else if (mble.length() < 10) {
            Utility.showSnacks(act, "INVALID MOBILE NUMBER", "OK", Snackbar.LENGTH_INDEFINITE);
        } else if (!isValidMobile(mble)) {
            Utility.showSnacks(act, "INVALID MOBILE NUMBER", "OK", Snackbar.LENGTH_INDEFINITE);
        } else if (mail.isEmpty()) {
            Utility.showSnacks(act, "PLEASE ENTER EMAIL", "OK", Snackbar.LENGTH_INDEFINITE);
        } else if (!isValidMail(mail)) {
            Utility.showSnacks(act, "INVALID EMAIL", "OK", Snackbar.LENGTH_INDEFINITE);
        } else if (pwd.isEmpty()) {
            Utility.showSnacks(act, "PLEASE ENTER PASSWORD", "OK", Snackbar.LENGTH_INDEFINITE);
        }
        return true;
    }

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void register() {
        new RegistrationFragment.BookTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "k");
    }


    private class BookTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            return AsyncTaskUtility.addNewUser(mble, mail, pwd);
        }

        @Override
        protected void onPostExecute(String result) {
            Response data = (Response) Utility.jsonToObject(result, Response.class);
            if (data.IS_ERROR()) {
                Utility.showSnacks(act, "Something went wrong..!!", "OK", Snackbar.LENGTH_INDEFINITE);
            } else {
                Utility.showSnacks(act, data.getUSER_MESSAGE(), "OK", Snackbar.LENGTH_INDEFINITE);
            }

        }

    }

}
