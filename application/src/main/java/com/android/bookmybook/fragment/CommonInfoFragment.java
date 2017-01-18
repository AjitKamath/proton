package com.android.bookmybook.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.android.bookmybook.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.android.bookmybook.util.Constants.CAMERA_CHOICE;
import static com.android.bookmybook.util.Constants.GALLERY_CHOICE;
import static com.android.bookmybook.util.Constants.INFO_MESSAGE_PRIMARY;
import static com.android.bookmybook.util.Constants.INFO_MESSAGE_SECONDARY;
import static com.android.bookmybook.util.Constants.UI_FONT;
import static com.android.bookmybook.util.Constants.UN_IDENTIFIED_PARENT_FRAGMENT;

/**
 * Created by ajit on 21/3/16.
 */
public class CommonInfoFragment extends DialogFragment{
    private final String CLASS_NAME = this.getClass().getName();
    private Context mContext;

    /*components*/
    @InjectView(R.id.common_fragment_info_primary_tv)
    TextView common_fragment_info_primary_tv;

    @InjectView(R.id.common_fragment_info_secondary_tv)
    TextView common_fragment_info_secondary;
    /*components*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_fragment_info, container);
        ButterKnife.inject(this, view);

        Dialog d = getDialog();
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setupPage();

        return view;
    }

    private void setupPage() {
        if(getArguments().get(INFO_MESSAGE_PRIMARY) != null){
            common_fragment_info_primary_tv.setText(String.valueOf(getArguments().get(INFO_MESSAGE_PRIMARY)));
        }

        if(getArguments().get(INFO_MESSAGE_SECONDARY) != null){
            common_fragment_info_secondary.setText(String.valueOf(getArguments().get(INFO_MESSAGE_SECONDARY)));
        }
    }

    private void getDataFromBundle() {
    }

    // Empty constructor required for DialogFragment
    public CommonInfoFragment() {}

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
        }
    }

    //method iterates over each component in the activity and when it finds a text view..sets its font
    public void setFont(ViewGroup group) {
        //set font for all the text view
        final Typeface robotoCondensedLightFont = Typeface.createFromAsset(mContext.getAssets(), UI_FONT);

        int count = group.getChildCount();
        View v;

        for(int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if(v instanceof TextView) {
                ((TextView) v).setTypeface(robotoCondensedLightFont);
            }
            else if(v instanceof ViewGroup) {
                setFont((ViewGroup) v);
            }
        }
    }
}
