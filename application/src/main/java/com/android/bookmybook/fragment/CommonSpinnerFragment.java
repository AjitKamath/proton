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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.bookmybook.R;
import com.android.bookmybook.adapter.CommonSpinnerListViewAdapter;
import com.android.bookmybook.model.Category;
import com.android.bookmybook.model.Tenure;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.android.bookmybook.util.Constants.DURATION_TYPE;
import static com.android.bookmybook.util.Constants.LIST_DATA;
import static com.android.bookmybook.util.Constants.MAX_DURATION;
import static com.android.bookmybook.util.Constants.MIN_DURATION;
import static com.android.bookmybook.util.Constants.SELECTED_ITEM;
import static com.android.bookmybook.util.Constants.UI_FONT;
import static com.android.bookmybook.util.Constants.UN_IDENTIFIED_OBJECT_TYPE;
import static com.android.bookmybook.util.Constants.UN_IDENTIFIED_PARENT_FRAGMENT;

/**
 * Created by ajit on 21/3/16.
 */
public class CommonSpinnerFragment extends DialogFragment{
    private final String CLASS_NAME = this.getClass().getName();
    private Context mContext;

    /*components*/
    @InjectView(R.id.common_spinner_fragment_lv)
    ListView common_spinner_fragment_lv;

    @InjectView(R.id.common_fragment_header_tv)
    TextView common_fragment_header_tv;

    @InjectView(R.id.common_fragment_header_navigation_ll)
    LinearLayout common_fragment_header_navigation_ll;
    /*components*/

    private Integer choice = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_spinner_fragment, container);
        ButterKnife.inject(this, view);

        Dialog d = getDialog();
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setupPage();

        return view;
    }

    private void setupPage() {
        //hide navigation buttons
        common_fragment_header_navigation_ll.setVisibility(View.INVISIBLE);

        //set header
        setupHeader();

        CommonSpinnerListViewAdapter adapter = new CommonSpinnerListViewAdapter(mContext, (List<Object>)getArguments().get(LIST_DATA), (String)getArguments().get(SELECTED_ITEM));
        common_spinner_fragment_lv.setAdapter(adapter);

        common_spinner_fragment_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //share fragment
                if(getTargetFragment() instanceof ShareFragment){
                    ShareFragment frag = (ShareFragment) getTargetFragment();

                    //categories
                    if(view.getTag() instanceof Category){
                        frag.setupCategory((Category) view.getTag());
                    }
                    //durations
                    else if(view.getTag() instanceof Tenure){
                        if(MAX_DURATION.equalsIgnoreCase((String)getArguments().get(DURATION_TYPE))){
                            frag.setupMaxDuration((Tenure) view.getTag());
                        }
                        else if(MIN_DURATION.equalsIgnoreCase((String)getArguments().get(DURATION_TYPE))){
                            frag.setupMinDuration((Tenure) view.getTag());
                        }
                        else{
                            Log.e(CLASS_NAME, UN_IDENTIFIED_OBJECT_TYPE+view.getTag());
                        }
                    }

                    dismiss();
                }
            }
        });
    }

    private void setupHeader() {
        //category
        if(((List<Object>)getArguments().get(LIST_DATA)).get(0) instanceof Category){
            common_fragment_header_tv.setText("CHOOSE YOUR BOOK CATEGORY");
        }
        else if(MAX_DURATION.equalsIgnoreCase((String)getArguments().get(DURATION_TYPE))){
            common_fragment_header_tv.setText("MAXIMUM RENTAL DURATION");
        }
        else if(MIN_DURATION.equalsIgnoreCase((String)getArguments().get(DURATION_TYPE))){
            common_fragment_header_tv.setText("MINIMUM RENTAL DURATION");
        }
    }

    // Empty constructor required for DialogFragment
    public CommonSpinnerFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
    }

    @Override
    public void onStop(){
        super.onStop();

        if(getTargetFragment() instanceof ShareFragment){
            ShareFragment fragment = (ShareFragment) getTargetFragment();
            fragment.onFinishDialog(choice);
        }
        else{
            Log.e(CLASS_NAME, UN_IDENTIFIED_PARENT_FRAGMENT+":"+getParentFragment());
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog d = getDialog();
        if (d!=null) {
            int width = ViewGroup.LayoutParams.WRAP_CONTENT;
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
