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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
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
import com.android.bookmybook.model.Category;
import com.android.bookmybook.model.Master;
import com.android.bookmybook.model.Tenure;
import com.android.bookmybook.model.User;
import com.android.bookmybook.util.AsyncTaskUtility;
import com.android.bookmybook.util.Utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
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
import static com.android.bookmybook.util.Constants.CATEGORY;
import static com.android.bookmybook.util.Constants.DURATION_TYPE;
import static com.android.bookmybook.util.Constants.FRAGMENT_COMMON_SPINNER;
import static com.android.bookmybook.util.Constants.FRAGMENT_PICK_IMAGE;
import static com.android.bookmybook.util.Constants.FRAGMENT_SHARE_BOOK;
import static com.android.bookmybook.util.Constants.GALLERY_CHOICE;
import static com.android.bookmybook.util.Constants.LIST_DATA;
import static com.android.bookmybook.util.Constants.LOGGED_IN_USER;
import static com.android.bookmybook.util.Constants.MASTER;
import static com.android.bookmybook.util.Constants.MAX_DURATION;
import static com.android.bookmybook.util.Constants.MIN_DURATION;
import static com.android.bookmybook.util.Constants.OK;
import static com.android.bookmybook.util.Constants.REQUEST_GALLERY_PHOTO;
import static com.android.bookmybook.util.Constants.REQUEST_TAKE_PHOTO;
import static com.android.bookmybook.util.Constants.SELECTED_ITEM;
import static com.android.bookmybook.util.Constants.UI_FONT;


/**
 * Created by ajit on 21/3/16.
 */
public class ShareFragment extends DialogFragment {
    private final String CLASS_NAME = this.getClass().getName();
    private Context mContext;

    /*components*/
    @InjectView(R.id.share_ll)
    LinearLayout share_ll;

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
    private Book book = new Book();
    private Master master;
    /*data from activity/fragment*/

    private String imagePathStr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container);
        ButterKnife.inject(this, view);

        Dialog d = getDialog();
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);

        getDataFromBundle();

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

            //set thumbnail for the image view for performance
            common_pick_book_iv.setImageBitmap(Utility.fetchThumbnailFromImage(bitmap, getResources().getDimensionPixelSize(R.dimen.pick_book_image_width), getResources().getDimensionPixelSize(R.dimen.pick_book_image_height)));

            //set real image in book object for real use
            book.setIMAGE(bitmap);
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
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePathStr);
            setBookCover(bitmap);
        }

        else if (requestCode == REQUEST_GALLERY_PHOTO && resultCode == RESULT_OK) {
            try {
                InputStream inputStream = mContext.getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                setBookCover(bitmap);

                File photoFile = createImageFile();

                OutputStream outputStream =  new FileOutputStream(photoFile);

                int read = 0;
                byte[] bytes = new byte[1024];

                InputStream is = mContext.getContentResolver().openInputStream(data.getData());

                while ((read = is.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }

                imagePathStr = photoFile.getAbsolutePath();
            }
            catch (Exception e){
                Log.e(CLASS_NAME, "Error while getting the image from the user choice");
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + "_";
        File storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,  "", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        imagePathStr = image.getAbsolutePath();
        return image;
    }

    public void getInputs(){

    }

    private void getDataFromBundle() {
        if(getArguments().get(BOOK) != null){
            book = (Book) getArguments().get(BOOK);
        }

        master = (Master) getArguments().get(MASTER);
        user = (User) getArguments().get(LOGGED_IN_USER);
    }

    private void setupPage() {
        if(book == null || (book != null && book.getTITLE() == null)){
            //TODO: adding a new book
            setupSliders();
        }
        else{
            //TODO: adding an existing book
        }
    }

    public void setupCategory(Category category){
        ((ViewPagerAdapterShareBook)share_book_vp.getAdapter()).setupCategory(category);
    }

    public void setupMinDuration(Tenure tenure){
        ((ViewPagerAdapterShareBook)share_book_vp.getAdapter()).setupMinDuration(tenure);
    }

    public void setupMaxDuration(Tenure tenure){
        ((ViewPagerAdapterShareBook)share_book_vp.getAdapter()).setupMaxDuration(tenure);
    }

    @OnClick({R.id.common_fragment_header_next_tv,R.id.common_fragment_header_back_tv})
    public void onBackNext(View view){
        //validations
        if(R.id.common_fragment_header_next_tv == view.getId()){
            if(share_book_vp.getCurrentItem() == 0){
                String bookTitleStr = Utility.fetchTextFromView(((ViewPagerAdapterShareBook)share_book_vp.getAdapter()).getShare_title_et());
                String bookAuthorStr = Utility.fetchTextFromView(((ViewPagerAdapterShareBook)share_book_vp.getAdapter()).getShare_author_et());

                if(bookTitleStr == null || bookTitleStr.trim().isEmpty()){
                    Utility.showSnacks(share_ll, "BOOK TITLE IS EMPTY", OK, Snackbar.LENGTH_LONG);
                    return;
                }
                else if(bookAuthorStr == null || bookAuthorStr.trim().isEmpty()){
                    Utility.showSnacks(share_ll, "AUTHOR OF THE BOOK IS EMPTY", OK, Snackbar.LENGTH_LONG);
                    return;
                }

                book.setTITLE(bookTitleStr);
                book.setAUTHOR(bookAuthorStr);
            }
            else if(share_book_vp.getCurrentItem() == 1){
                String bookCategoryIdStr = ((Category)((ViewPagerAdapterShareBook)share_book_vp.getAdapter()).getCommon_spinner_ll_category().getTag()).getCTGRY_ID();
                String bookPublisherStr = Utility.fetchTextFromView(((ViewPagerAdapterShareBook)share_book_vp.getAdapter()).getShare_publisher_et());
                String bookDescStr = Utility.fetchTextFromView(((ViewPagerAdapterShareBook)share_book_vp.getAdapter()).getShare_description_et());

                book.setCTGRY_ID(bookCategoryIdStr);
                book.setPUBLICATION(bookPublisherStr);
                book.setDESCRIPTION(bookDescStr);
            }
        }

        if("DONE".equalsIgnoreCase(String.valueOf(((TextView)view).getText()))){
            if(share_book_vp.getCurrentItem() == 2){
                String bookRentStr = Utility.fetchTextFromView(((ViewPagerAdapterShareBook)share_book_vp.getAdapter()).getCommon_amount_et());
                String bookMinDurationIdStr = ((Tenure)((ViewPagerAdapterShareBook)share_book_vp.getAdapter()).getCommon_spinner_ll_min_duration().getTag()).getTENURE_ID();
                String bookMaxDurationIdStr = ((Tenure)((ViewPagerAdapterShareBook)share_book_vp.getAdapter()).getCommon_spinner_ll_max_duration().getTag()).getTENURE_ID();

                //validation
                Integer rent = 0;
                try{
                    rent = Integer.parseInt(bookRentStr);
                }
                catch (Exception e){
                    Log.e(CLASS_NAME, "Could not parse("+bookRentStr+") into Integer type");
                    Utility.showSnacks(share_ll, "INVALID RENT AMOUNT", OK, Snackbar.LENGTH_LONG);
                    return;
                }

                if(rent == 0){
                    Utility.showSnacks(share_ll, "RENT AMOUNT CANNOT BE ZERO", OK, Snackbar.LENGTH_LONG);
                    return;
                }

                //validate image
                if(!common_pick_book_iv.isShown()){
                    Utility.showSnacks(share_ll, "BOOK COVER NOT FOUND", OK, Snackbar.LENGTH_LONG);
                    return;
                }

                book.setRENT(rent);
                book.setMIN_DURATION(bookMinDurationIdStr);
                book.setMAX_DURATION(bookMaxDurationIdStr);
                book.setImagePath(imagePathStr);

                new BookUploadTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                //TODO:POST THE BOOK HERE
            }
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

        final ViewPagerAdapterShareBook viewPagerAdapter = new ViewPagerAdapterShareBook(getActivity(), mContext, viewPagerTabsList, master);

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

                //listeners
                if(position == 1){
                    viewPagerAdapter.getCommon_spinner_ll_category().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showSpinner(view, CATEGORY);
                        }
                    });
                }
                else if(position == 2){
                    //min duration
                    viewPagerAdapter.getCommon_spinner_ll_min_duration().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showSpinner(view, MIN_DURATION);
                        }
                    });

                    //max duration
                    viewPagerAdapter.getCommon_spinner_ll_max_duration().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showSpinner(view, MAX_DURATION);
                        }
                    });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void showSpinner(View view, String whichSpinnerStr){
        FragmentManager manager = getActivity().getFragmentManager();
        Fragment frag = manager.findFragmentByTag(FRAGMENT_COMMON_SPINNER);
        if (frag != null) {
            manager.beginTransaction().remove(frag).commit();
        }

        Bundle bundle = new Bundle();

        if(CATEGORY.equalsIgnoreCase(whichSpinnerStr)){
            bundle.putSerializable(LIST_DATA, (Serializable) master.getCategoriesList());
            bundle.putSerializable(SELECTED_ITEM, ((Category)view.getTag()).getCTGRY_ID());
        }
        else if((MIN_DURATION+MAX_DURATION).contains(whichSpinnerStr)){
            bundle.putSerializable(LIST_DATA, (Serializable) master.getTenuresList());
            bundle.putSerializable(SELECTED_ITEM, ((Tenure)view.getTag()).getTENURE_ID());
            bundle.putSerializable(DURATION_TYPE, whichSpinnerStr);
        }

        Fragment currentFrag = manager.findFragmentByTag(FRAGMENT_SHARE_BOOK);

        CommonSpinnerFragment fragment = new CommonSpinnerFragment();
        fragment.setArguments(bundle);
        fragment.setTargetFragment(currentFrag, 0);
        fragment.setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.fragment_theme);
        fragment.show(manager, FRAGMENT_COMMON_SPINNER);
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
            d.setCancelable(false);
        }
    }

    public void onFinishDialog(Integer choice) {
        if(choice == null){
            return;
        }
        else if(GALLERY_CHOICE == choice){
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

    private class BookUploadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return AsyncTaskUtility.shareBook(book);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(CLASS_NAME, result);
        }
    }
}