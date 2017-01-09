package com.android.bookmybook.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.bookmybook.R;
import com.android.bookmybook.adapter.ViewPagerAdapterShareBook;
import com.android.bookmybook.component.ViewPagerCustom;
import com.android.bookmybook.model.Book;
import com.android.bookmybook.model.User;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.android.bookmybook.util.Constants.BOOK;
import static com.android.bookmybook.util.Constants.CAMERA_CHOICE;
import static com.android.bookmybook.util.Constants.FRAGMENT_PICK_IMAGE;
import static com.android.bookmybook.util.Constants.FRAGMENT_SHARE_BOOK;
import static com.android.bookmybook.util.Constants.GALLERY_CHOICE;
import static com.android.bookmybook.util.Constants.LOGGED_IN_USER;
import static com.android.bookmybook.util.Constants.UI_FONT;


/**
 * Created by ajit on 21/3/16.
 */
public class ShareFragment extends DialogFragment {
    private final String CLASS_NAME = this.getClass().getName();
    private Context mContext;

    /*components*/
    @InjectView(R.id.common_fragment_header_back_tv)
    TextView common_fragment_header_back_tv;

    @InjectView(R.id.common_fragment_header_next_tv)
    TextView common_fragment_header_next_tv;

    @InjectView(R.id.common_pick_book_iv)
    ImageView common_pick_book_iv;

    @InjectView(R.id.common_pick_no_book_ll)
    LinearLayout common_pick_no_book_ll;

    @InjectView(R.id.share_book_vp)
    ViewPagerCustom share_book_vp;
    /*components*/

    /*data from activity/fragment*/
    private User user;
    private Book book;
    /*data from activity/fragment*/


    static final int REQUEST_IMAGE_CAPTURE = 1;
    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container);
        ButterKnife.inject(this, view);

        Dialog d = getDialog();
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getDataFromBundle();
        initComps();
        setupPage();

        return view;
    }

    @OnClick({R.id.common_pick_no_book_ll,R.id.common_pick_book_iv})
    public void onBookCoverPick(View view){
        String fragmentNameStr = FRAGMENT_PICK_IMAGE;
        String parentFragmentNameStr = FRAGMENT_SHARE_BOOK;

        FragmentManager manager = getFragmentManager();
        Fragment frag = manager.findFragmentByTag(fragmentNameStr);

        if (frag != null) {
            manager.beginTransaction().remove(frag).commit();
        }

        Fragment parentFragment = null;
        if(parentFragmentNameStr != null && !parentFragmentNameStr.trim().isEmpty()){
            parentFragment = manager.findFragmentByTag(parentFragmentNameStr);
        }

        ImagePickerFragment fragment = new ImagePickerFragment();
        fragment.setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.fragment_theme);

        if (parentFragment != null) {
            fragment.setTargetFragment(parentFragment, 0);
        }

        fragment.show(manager, fragmentNameStr);
    }

    private void setBookCover(Bitmap bitmap){
        if(bitmap != null){
            common_pick_no_book_ll.setVisibility(View.GONE);
            common_pick_book_iv.setVisibility(View.VISIBLE);
            common_pick_book_iv.setImageBitmap(bitmap);
        }
        else{
            common_pick_no_book_ll.setVisibility(View.VISIBLE);
            common_pick_book_iv.setVisibility(View.GONE);
        }
    }

    private void showPickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_GALLERY_PHOTO);
    }

    private void showPickImageFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.example.android.fileprovider", photoFile);

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
            catch (IOException ex) {
                Log.e(CLASS_NAME, "An error occurred when getting image file from camera: ");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, options);
            setBookCover(bitmap);
        }

        else if (requestCode == REQUEST_GALLERY_PHOTO && resultCode == RESULT_OK) {
            try {
                InputStream inputStream = mContext.getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                setBookCover(bitmap);
            }
            catch (Exception e){
                Log.e(CLASS_NAME, "Error while getting the image from the user choice");
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,  ".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void getInputs(){

    }

    private void getDataFromBundle() {
        book = (Book) getArguments().get(BOOK);
        user = (User) getArguments().get(LOGGED_IN_USER);
    }

    private void setupPage() {
        if(book == null){
            //TODO: adding a new book
            setupSliders();
        }
        else{
            //TODO: adding an existing book
        }
    }

    @OnClick({R.id.common_fragment_header_next_tv,R.id.common_fragment_header_back_tv})
    public void onBackNext(View view){
        if("DONE".equalsIgnoreCase(String.valueOf(((TextView)view).getText()))){
            //TODO: POST THE BOOK
        }
        else if(R.id.common_fragment_header_next_tv == view.getId()){
            share_book_vp.setCurrentItem(share_book_vp.getCurrentItem()+1);
        }
        else if(R.id.common_fragment_header_back_tv == view.getId()){
            share_book_vp.setCurrentItem(share_book_vp.getCurrentItem()-1);
        }
    }

    private void setupSliders() {
        final List<Integer> viewPagerTabsList = new ArrayList<>();
        viewPagerTabsList.add(R.layout.view_pager_share_book_1);
        viewPagerTabsList.add(R.layout.view_pager_share_book_2);
        viewPagerTabsList.add(R.layout.view_pager_share_book_3);

        ViewPagerAdapterShareBook viewPagerAdapter = new ViewPagerAdapterShareBook(mContext, viewPagerTabsList);

        int activePageIndex = 0;
        if(share_book_vp != null && share_book_vp.getAdapter() != null){
            activePageIndex = share_book_vp.getCurrentItem();
        }

        share_book_vp.setAdapter(viewPagerAdapter);
        share_book_vp.setCurrentItem(activePageIndex);
        share_book_vp.setPagingEnabled(false);

        share_book_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    common_fragment_header_back_tv.setVisibility(View.GONE);
                    common_fragment_header_next_tv.setVisibility(View.VISIBLE);
                    common_fragment_header_next_tv.setText("NEXT");
                }
                else if(position == viewPagerTabsList.size()-1){
                    common_fragment_header_back_tv.setVisibility(View.VISIBLE);
                    common_fragment_header_next_tv.setVisibility(View.VISIBLE);
                    common_fragment_header_next_tv.setText("DONE");
                }
                else{
                    common_fragment_header_back_tv.setVisibility(View.VISIBLE);
                    common_fragment_header_next_tv.setVisibility(View.VISIBLE);
                    common_fragment_header_next_tv.setText("NEXT");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void closeFragment(String messageStr){
        dismiss();
    }

    private void getMasterData() {
        /*categoriesList = calendarDbService.getAllCategories(loggedInUserObj.getUSER_ID());
        accountList = calendarDbService.getAllAccounts(loggedInUserObj.getUSER_ID());
        spentOnList = calendarDbService.getAllSpentOns(loggedInUserObj.getUSER_ID());

        repeatsList = calendarDbService.getAllRepeats();*/
    }

    private void initComps(){


       /* setFont(addUpdateTransactionRL);*/
    }

    // Empty constructor required for DialogFragment
    public ShareFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
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

    public interface DialogResultListener {
        void onFinishUserDialog(String str);
    }

    public void onFinishDialog(Integer choice) {
        if(GALLERY_CHOICE == choice){
            showPickImageFromGallery();
        }
        else if(CAMERA_CHOICE == choice){
            showPickImageFromCamera();
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