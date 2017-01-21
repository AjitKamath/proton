package com.android.bookmybook.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.bookmybook.R;
import com.android.bookmybook.adapter.HorizontalListAdapter;
import com.android.bookmybook.model.Book;
import com.android.bookmybook.model.Condition;
import com.android.bookmybook.model.Response;
import com.android.bookmybook.util.AsyncTaskUtility;
import com.android.bookmybook.util.Utility;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.android.bookmybook.util.Constants.BOOK;
import static com.android.bookmybook.util.Constants.CAMERA_CHOICE;
import static com.android.bookmybook.util.Constants.CHECK_MASTER_FOR_ALL;
import static com.android.bookmybook.util.Constants.FRAGMENT_SHARE_BOOK;
import static com.android.bookmybook.util.Constants.FRAGMENT_SHARE_BOOK_DETAILS;
import static com.android.bookmybook.util.Constants.FRAGMENT_SHARE_MESSAGE;
import static com.android.bookmybook.util.Constants.GALLERY_CHOICE;
import static com.android.bookmybook.util.Constants.LOGGED_IN_USER;
import static com.android.bookmybook.util.Constants.MASTER;
import static com.android.bookmybook.util.Constants.RESPONSE;
import static com.android.bookmybook.util.Constants.UI_FONT;
import static com.android.bookmybook.util.Constants.UN_IDENTIFIED_PARENT_FRAGMENT;

/**
 * Created by ajit on 21/3/16.
 */
public class ShareDetailsFragment extends DialogFragment{
    private final String CLASS_NAME = this.getClass().getName();
    private Context mContext;

    /*components*/
    @InjectView(R.id.common_fragment_header_tv)
    TextView common_fragment_header_tv;

    @InjectView(R.id.common_fragment_header_back_tv)
    TextView common_fragment_header_back_tv;

    @InjectView(R.id.common_fragment_header_next_tv)
    TextView common_fragment_header_next_tv;

    @InjectView(R.id.common_pick_no_book_ll)
    LinearLayout common_pick_no_book_ll;

    @InjectView(R.id.common_pick_book_iv)
    ImageView common_pick_book_iv;

    @InjectView(R.id.share_details_ll)
    LinearLayout share_details_ll;

    @InjectView(R.id.fragment_share_title_tv)
    TextView fragment_share_title_tv;

    @InjectView(R.id.fragment_share_author_tv)
    TextView fragment_share_author_tv;

    @InjectView(R.id.fragment_share_publication_tv)
    TextView fragment_share_publication_tv;

    @InjectView(R.id.fragment_share_description_tv)
    TextView fragment_share_description_tv;

    @InjectView(R.id.fragment_share_category_tv)
    TextView fragment_share_category_tv;

    @InjectView(R.id.fragment_share_max_rent__ll)
    LinearLayout fragment_share_max_rent__ll;

    @InjectView(R.id.fragment_share_min_rent_tv)
    TextView fragment_share_min_rent_tv;

    @InjectView(R.id.fragment_share_min_rent_duration_tv)
    TextView fragment_share_min_rent_duration_tv;

    @InjectView(R.id.fragment_share_max_rent_tv)
    TextView fragment_share_max_rent_tv;

    @InjectView(R.id.fragment_share_max_rent_duration_tv)
    TextView fragment_share_max_rent_duration_tv;

    @InjectView(R.id.fragment_share_min_duration_tv)
    TextView fragment_share_min_duration_tv;

    @InjectView(R.id.fragment_share_max_duration_tv)
    TextView fragment_share_max_duration_tv;

    @InjectView(R.id.horizontal_recycler)
    RecyclerView horizontalList;
    /*components*/

    private Book book;

    private ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_details, container);
        ButterKnife.inject(this, view);

        Dialog d = getDialog();
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);

        fetchDataFromBundle();
        setupPage();

        return view;
    }

    @OnClick(R.id.common_fragment_header_next_tv)
    public void onShare(View view){
        //check if the user is connected to the internet
        if(!Utility.isNetworkAvailable(mContext)){
            FragmentManager fragment = getFragmentManager();
            Utility.showNoInternetFragment(fragment);
            return;
        }

        progress = Utility.getProgressDialog(getContext(), "Sharing your book ..");
        Utility.showProgress(progress);

        new BookUploadTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void setupPage() {
        //header
        common_fragment_header_tv.setText("CONFIRM YOUR BOOK");
        common_fragment_header_back_tv.setVisibility(View.INVISIBLE);
        common_fragment_header_next_tv.setText("SHARE");

        setBookCover(book.getIMAGE());

        fragment_share_title_tv.setText(book.getTITLE());
        fragment_share_author_tv.setText(book.getAUTHOR());

        fragment_share_category_tv.setText(book.getCategory().getCTGRY_NAME());
        if(book.getPUBLICATION() != null && !book.getPUBLICATION().trim().isEmpty()){
            fragment_share_publication_tv.setText(book.getPUBLICATION());
        }
        if(book.getDESCRIPTION() != null && !book.getDESCRIPTION().trim().isEmpty()){
            fragment_share_description_tv.setText(book.getDESCRIPTION());
        }

        fragment_share_min_rent_tv.setText(String.valueOf(book.getRENT()));
        fragment_share_min_rent_duration_tv.setText(book.getMinDuration().getTENURE_NAME());

        //if min and max duration are same, no need to show the projected rent/max duration
        if(book.getMinDuration().getTENURE_ID().equalsIgnoreCase(book.getMaxDuration().getTENURE_ID())){
            fragment_share_max_rent__ll.setVisibility(View.INVISIBLE);
        }
        else{
            fragment_share_max_rent__ll.setVisibility(View.VISIBLE);
            fragment_share_max_rent_tv.setText(String.valueOf(Utility.calculateMaxRent(book.getRENT(), book.getMinDuration().getNO_OF_DAYS(), book.getMaxDuration().getNO_OF_DAYS())));
            fragment_share_max_rent_duration_tv.setText(book.getMaxDuration().getTENURE_NAME());
        }

        fragment_share_min_duration_tv.setText(book.getMinDuration().getTENURE_NAME());
        fragment_share_max_duration_tv.setText(book.getMaxDuration().getTENURE_NAME());

        //set recycler
        HorizontalListAdapter horizontalAdapter;

        horizontalList.setHasFixedSize(true);
        //set horizontal LinearLayout as layout manager to creating horizontal list view
        LinearLayoutManager horizontalManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        horizontalList.setLayoutManager(horizontalManager);
        horizontalAdapter = new HorizontalListAdapter(getActivity(), new ArrayList<Condition>());
        horizontalList.setAdapter(horizontalAdapter);

        setFont(share_details_ll);
    }

    @OnClick({R.id.fragment_share_max_rent_info_tv, R.id.fragment_share_condition_info_tv, R.id.fragment_share_min_duration_info_tv, R.id.fragment_share_max_duration_info_tv})
    public void showRentInfo(View view){
        String primaryMessageStr = "Error";
        String secondaryMessageStr = "Error";
        if(R.id.fragment_share_max_rent_info_tv == view.getId()){
            primaryMessageStr = "To promote seekers to rent books for longer duration, the maximum duration rent is calculated by discounting the minimum duration rent.";
            secondaryMessageStr = "*the discount factor may vary over time  based on the availability";
        }
        else if(R.id.fragment_share_condition_info_tv == view.getId()){
            primaryMessageStr = "A Book like any other thing can wear & tear over time. Add some pics to let the seeker know the condition of your book. Be it missing pages or ink marks, add any number of pics & describe it briefly.";
            secondaryMessageStr = "*you can add conditions later from 'YOUR BOOKS' section";
        }
        else if(R.id.fragment_share_min_duration_info_tv == view.getId()){
            primaryMessageStr = "A seeker may rent your book for at least a "+book.getMinDuration().getTENURE_NAME()+" for "+getResources().getString(R.string.rupee)+" "+book.getRENT();
            secondaryMessageStr = "*you can change this anytime from 'YOUR BOOKS' section.";
        }
        else if(R.id.fragment_share_max_duration_info_tv == view.getId()){
            primaryMessageStr = "A seeker may rent your book for at most a "+book.getMinDuration().getTENURE_NAME()+" for "+getResources().getString(R.string.rupee)+" "+Utility.calculateMaxRent(book.getRENT(), book.getMinDuration().getNO_OF_DAYS(), book.getMaxDuration().getNO_OF_DAYS());
            secondaryMessageStr = "*you can change this anytime from 'YOUR BOOKS' section.";
        }

        FragmentManager manager = getFragmentManager();
        Utility.showInfoFragment(manager, primaryMessageStr, secondaryMessageStr);
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

    private void fetchDataFromBundle() {
        book = (Book) getArguments().get(BOOK);
    }

    // Empty constructor required for DialogFragment
    public ShareDetailsFragment() {}

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
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;;
            d.getWindow().setLayout(width, height);
            d.setCanceledOnTouchOutside(false);
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

    private class BookUploadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return AsyncTaskUtility.shareBook(book);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(CLASS_NAME, result);

            Response response = (Response) Utility.jsonToObject(result, Response.class);

            //book shared fail
            if(response == null || response.IS_ERROR()){
                Utility.closeProgress(progress);
                showShareMessageFragment(response);
                return;
            }

            //if success
            Utility.closeProgress(progress);

            //dismiss share details fragment
            dismiss();

            //dismiss parent share fragment
            ((ShareFragment)getTargetFragment()).dismiss();

            showShareMessageFragment(response);
        }
    }

    private void showShareMessageFragment(Response response) {
        String fragmentNameStr = FRAGMENT_SHARE_MESSAGE;
        String parentFragmentNameStr = null;

        Bundle bundle = new Bundle();
        bundle.putSerializable(BOOK, book);
        bundle.putSerializable(RESPONSE, response);

        FragmentManager manager = getFragmentManager();
        Fragment frag = manager.findFragmentByTag(fragmentNameStr);

        if (frag != null) {
            manager.beginTransaction().remove(frag).commit();
        }

        Fragment parentFragment = null;
        if(parentFragmentNameStr != null && !parentFragmentNameStr.trim().isEmpty()){
            parentFragment = manager.findFragmentByTag(parentFragmentNameStr);
        }

        ShareMessageFragment fragment = new ShareMessageFragment();
        fragment.setArguments(bundle);
        fragment.setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.fragment_theme);

        if (parentFragment != null) {
            fragment.setTargetFragment(parentFragment, 0);
        }

        fragment.show(manager, fragmentNameStr);
    }
}
