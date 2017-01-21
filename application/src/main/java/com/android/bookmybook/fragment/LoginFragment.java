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
import android.widget.TextView;

import com.android.bookmybook.R;
import com.android.bookmybook.activity.CommonActivity;
import com.android.bookmybook.model.Response;
import com.android.bookmybook.model.User;
import com.android.bookmybook.util.AsyncTaskUtility;
import com.android.bookmybook.util.SharedPrefUtility;
import com.android.bookmybook.util.TestData;
import com.android.bookmybook.util.Utility;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.android.bookmybook.util.Constants.SSID;


public class LoginFragment extends DialogFragment{
    private final String CLASS_NAME = this.getClass().getName();
    private Context mContext;

    /*components*/
    @InjectView(R.id.login_act)
    LinearLayout act;

    @InjectView(R.id.login_form)
    LinearLayout lo_form;

    @InjectView(R.id.email)
    EditText email;

    @InjectView(R.id.password)
    EditText password;

    @InjectView(R.id.sign_in_button)
    Button sign_in;

    @InjectView(R.id.registration_from_login)
    TextView sign_up_from_login;

    /*components*/

    private Integer choice = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container);
        ButterKnife.inject(this, view);

        Dialog d = getDialog();
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);

        return view;
    }

    public LoginFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog d = getDialog();
        if (d!=null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;;
            d.getWindow().setLayout(width, height);
            d.setCancelable(false);

        }
    }

    @OnClick(R.id.registration_from_login)
    public void onSignupFromLogin(View view) {

            FragmentManager fragMan = getFragmentManager();
            Utility.showRegistrationFragment(fragMan);
            return;

    }

    @OnClick(R.id.sign_in_button)
    public void onSignup(View view) {
        if (validate_user()) {
            login();
        } else {
            Utility.showSnacks(act,"Response Failed","OK", Snackbar.LENGTH_INDEFINITE);
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
        new LoginFragment.BookTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "k");
    }


    private class BookTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            return AsyncTaskUtility.login_authentication(mail, pwd);
        }

        @Override
        protected void onPostExecute(String result) {
            Response data = (Response) Utility.jsonToObject(result, Response.class);

            /*not for prod*/
            data = new Response();
            data.setUser(TestData.getUser());
            data.setIS_ERROR(false);
            /*not for prod*/

            if(data == null || data.IS_ERROR()){
                Utility.showSnacks(act,"Something went wrong..!!","OK", Snackbar.LENGTH_INDEFINITE);
            }
            else{
                dismiss();

                //save ssid in shared prefs
                new SharedPrefUtility(mContext).save(SSID, data.getUser().getSSID());

                if(getActivity() instanceof CommonActivity){
                    ((CommonActivity) getActivity()).fetchMasterData();
                }
            }
        }

    }

}
