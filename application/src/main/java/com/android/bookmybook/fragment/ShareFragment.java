package com.android.bookmybook.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
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

import static android.app.Activity.RESULT_OK;
import static com.android.bookmybook.util.Constants.UI_FONT;


/**
 * Created by ajit on 21/3/16.
 */
public class ShareFragment extends DialogFragment {
    private final String CLASS_NAME = this.getClass().getName();
    private Context mContext;

    /*components*/
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

        //showPickImageDialog();

        return view;
    }

    private void showPickGalleryImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_GALLERY_PHOTO);

    }

    private void showPickImageDialog() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.android.fileprovider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, options);
            //share_book_iv.setImageBitmap(bitmap);

           // mImageView.setImageBitmap(imageBitmap);
        }

        else if (requestCode == REQUEST_GALLERY_PHOTO && resultCode == RESULT_OK) {
            try {
                InputStream inputStream = mContext.getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                //share_book_iv.setImageBitmap(bitmap);
            }
            catch (Exception e){
                Log.e(CLASS_NAME, "Error while getting the image from the user choice");
            }

            // mImageView.setImageBitmap(imageBitmap);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }




    public void getInputs(){
        /*try{
            transactionModelObj.setTRAN_DATE(UI_DATE_FORMAT_SDF.parse(String.valueOf(addUpdateDateTV.getText())));
        }
        catch (ParseException pe){
            Log.e(CLASS_NAME, "Parse Exception "+pe);
            return ;
        }

        transactionModelObj.setTRAN_AMT(Double.parseDouble(String.valueOf(transactionContentAmountTV.getText()).replaceAll(",", "")));
        transactionModelObj.setTRAN_NAME(String.valueOf(addUpdateTranNameET.getText()));

        transactionModelObj.setCAT_ID(((CategoryMO)transactionContentCategoryLL.getTag()).getCAT_ID());
        transactionModelObj.setACC_ID(((AccountMO)transactionContentAccountLL.getTag()).getACC_ID());
        transactionModelObj.setSPNT_ON_ID(((SpentOnMO)transactionContentSpentonLL.getTag()).getSPNT_ON_ID());

        transactionModelObj.setTRAN_TYPE(String.valueOf(getView().findViewById(addUpdateTranExpIncRadioGrp.getCheckedRadioButtonId()).getTag()));
        transactionModelObj.setTRAN_NOTE(String.valueOf(addUpdateNoteET.getText()));
        transactionModelObj.setUSER_ID(loggedInUserObj.getUSER_ID());

        if(transactionContentRepeatSwitch.isChecked()){
            transactionModelObj.setREPEAT_ID(((RepeatMO)transactionContentRepeatLL.getTag()).getREPEAT_ID());
            transactionModelObj.setNOTIFY(String.valueOf(getView().findViewById(transactionContentNotifyRG.getCheckedRadioButtonId()).getTag()));

            if(R.id.transactionContentNotifyAddRBId == transactionContentNotifyRG.getCheckedRadioButtonId()){
                transactionModelObj.setNOTIFY_TIME(String.valueOf(transactionContentNotifyAddTimeTV.getText()));
            }
            else if(R.id.transactionContentAutoAddRBId == transactionContentNotifyRG.getCheckedRadioButtonId()){
                transactionModelObj.setNOTIFY_TIME(String.valueOf(transactionContentAutoAddTimeTV.getText()));
            }

            transactionModelObj.setSCHD_UPTO_DATE(String.valueOf(transactionContentScheduleUptoDateTV.getText()));
        }*/
    }

    private void getDataFromBundle() {
        /*transactionModelObj = (TransactionMO) getArguments().get(TRANSACTION_OBJECT);
        loggedInUserObj = (UserMO) getArguments().get(LOGGED_IN_OBJECT);*/
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

    private void setupSliders() {
        List<Integer> viewPagerTabsList = new ArrayList<>();
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

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //setUploginWithFacebook();
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
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);

        }
    }

    public interface DialogResultListener {
        void onFinishUserDialog(String str);
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