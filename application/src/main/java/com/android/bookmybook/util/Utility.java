package com.android.bookmybook.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.android.bookmybook.R;
import com.android.bookmybook.fragment.ShareFragment;

import static com.android.bookmybook.util.Constants.OK;

public class Utility extends Activity{

    private static final String CLASS_NAME = Utility.class.getName();
    private Context mContext;

	public static void showSnacks(ViewGroup viewGroup, String messageStr, final String doWhatStr, int duration){
        Snackbar snackbar = Snackbar.make(viewGroup, messageStr, duration).setAction(doWhatStr, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //OK
                if(OK.equalsIgnoreCase(doWhatStr)){

                }
                else{
                    Log.e(CLASS_NAME, "Could not identify the action of the snacks");
                }
            }
        });

        snackbar.show();
    }

    public Utility(Context mContext){
        this.mContext = mContext;
    }
}
