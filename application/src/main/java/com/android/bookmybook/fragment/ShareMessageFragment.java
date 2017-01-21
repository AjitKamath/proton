package com.android.bookmybook.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.bookmybook.R;
import com.android.bookmybook.model.Book;
import com.android.bookmybook.model.Response;
import com.android.bookmybook.util.Utility;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.android.bookmybook.util.Constants.BOOK;
import static com.android.bookmybook.util.Constants.CAMERA_CHOICE;
import static com.android.bookmybook.util.Constants.GALLERY_CHOICE;
import static com.android.bookmybook.util.Constants.HEADER;
import static com.android.bookmybook.util.Constants.OK;
import static com.android.bookmybook.util.Constants.RESPONSE;
import static com.android.bookmybook.util.Constants.UI_FONT;
import static com.android.bookmybook.util.Constants.UN_IDENTIFIED_PARENT_FRAGMENT;

/**
 * Created by ajit on 21/3/16.
 */
public class ShareMessageFragment extends DialogFragment{
    private final String CLASS_NAME = this.getClass().getName();
    private Context mContext;

    /*components*/
    @InjectView(R.id.common_fragment_header_tv)
    TextView common_fragment_header_tv;

    @InjectView(R.id.fragment_share_message_ll)
    LinearLayout fragment_share_message_ll;

    @InjectView(R.id.common_fragment_header_back_tv)
    TextView common_fragment_header_back_tv;

    @InjectView(R.id.common_fragment_header_next_tv)
    TextView common_fragment_header_next_tv;

    @InjectView(R.id.common_pick_no_book_ll)
    LinearLayout common_pick_no_book_ll;

    @InjectView(R.id.common_pick_book_iv)
    ImageView common_pick_book_iv;

    @InjectView(R.id.fragment_share_message_iv)
    ImageView fragment_share_message_iv;

    @InjectView(R.id.fragment_share_message_info_tv)
    TextView fragment_share_message_info_tv;

    @InjectView(R.id.fragment_share_message_primary_tv)
    TextView fragment_share_message_primary_tv;

    @InjectView(R.id.fragment_share_message_secondary_tv)
    TextView fragment_share_message_secondary_tv;
    /*components*/

    private Integer choice = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_message, container);
        ButterKnife.inject(this, view);

        Dialog d = getDialog();
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setupPage();

        return view;
    }

    private void setupPage() {
        common_fragment_header_tv.setVisibility(View.INVISIBLE);
        common_fragment_header_back_tv.setVisibility(View.INVISIBLE);
        common_fragment_header_next_tv.setText(OK);

        setBookCover(((Book)getArguments().get(BOOK)).getIMAGE());
        setMessage(((Response)getArguments().get(RESPONSE)));

        setFont(fragment_share_message_ll);
    }

    @OnClick(R.id.common_fragment_header_next_tv)
    public void onOK(View view){
        dismiss();
    }

    private void setMessage(Response response) {
        String primaryMsgStr;
        String secondaryMsgStr;
        int image;
        if(response.IS_ERROR()){
            primaryMsgStr = "OOPS! YOUR BOOK COULD NOT BE SHARED";
            secondaryMsgStr = "CHECK YOUR INTERNET & TRY AGAIN.";
            fragment_share_message_info_tv.setVisibility(View.GONE);
            image = R.drawable.cross;
        }
        else{
            primaryMsgStr = "YAY! YOUR BOOK HAS BEEN SHARED";
            secondaryMsgStr = "SIT BACK AND RELAX UNTIL SOMEONE SEEKS IT";
            fragment_share_message_info_tv.setVisibility(View.VISIBLE);
            image = R.drawable.tick;
        }

        fragment_share_message_iv.setBackgroundResource(image);
        fragment_share_message_primary_tv.setText(primaryMsgStr);
        fragment_share_message_secondary_tv.setText(secondaryMsgStr);
    }

    private void setBookCover(Bitmap bitmap){
        if(bitmap != null){
            common_pick_no_book_ll.setVisibility(View.GONE);
            common_pick_book_iv.setVisibility(View.VISIBLE);

            //set thumbnail for the image view for performance
            common_pick_book_iv.setImageBitmap(Utility.fetchThumbnailFromImage(bitmap, getResources().getDimensionPixelSize(R.dimen.pick_book_image_width), getResources().getDimensionPixelSize(R.dimen.pick_book_image_height)));
        }
        else{
            common_pick_no_book_ll.setVisibility(View.VISIBLE);
            common_pick_book_iv.setVisibility(View.GONE);
        }
    }

    // Empty constructor required for DialogFragment
    public ShareMessageFragment() {}

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
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
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
