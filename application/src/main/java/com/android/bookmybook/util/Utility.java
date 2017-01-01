package com.android.bookmybook.util;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import static com.android.bookmybook.util.Constants.OK;

public class Utility extends Activity{

    private static final String CLASS_NAME = Utility.class.getName();

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
}
