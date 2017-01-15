package com.android.bookmybook.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.bookmybook.R;
import com.android.bookmybook.model.Category;
import com.android.bookmybook.model.Master;
import com.android.bookmybook.model.Tenure;
import com.android.bookmybook.util.Utility;

import java.util.List;

import static com.android.bookmybook.util.Constants.MAX_DURATION;
import static com.android.bookmybook.util.Constants.MIN_DURATION;
import static com.android.bookmybook.util.Constants.UI_FONT;

/**
 * Created by ajit on 30/9/15.
 */
public class ViewPagerAdapterShareBook extends PagerAdapter {
    private final String CLASS_NAME = this.getClass().getName();

    /*components*/
    //page 1
    private EditText share_title_et;
    private EditText share_author_et;

    //page 2
    private LinearLayout common_spinner_ll_category;
    private EditText share_publisher_et;
    private EditText share_description_et;

    //page 3
    private EditText common_amount_et;
    private LinearLayout common_spinner_ll_min_duration;
    private LinearLayout common_spinner_ll_max_duration;
    /*components*/

    private Context mContext;
    private List<Integer> layoutsList;
    private Master master;
    public int activePageIndex = 0;


    public ViewPagerAdapterShareBook(Activity activity, Context context, List<Integer> layoutsList, Master master) {
        this.mContext = context;
        this.layoutsList = layoutsList;
        this.master = master;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(layoutsList.get(position), collection, false);

        activePageIndex = position;

        switch(position){
            case 0: setupShareBook1(layout); break;
            case 1: setupShareBook2(layout); break;
            case 2: setupShareBook3(layout); break;
        }

        setFont(layout);

        collection.addView(layout);

        return layout;
    }

    private void setupShareBook1(ViewGroup layout) {
        share_title_et = (EditText) layout.findViewById(R.id.share_title_et);
        share_author_et = (EditText) layout.findViewById(R.id.share_author_et);
    }

    private void setupShareBook2(ViewGroup layout) {
        //set up default selected category
        common_spinner_ll_category = (LinearLayout) layout.findViewById(R.id.common_spinner_category);
        setupCategory((Category) Utility.fetchDefault(master.getCategoriesList()));

        share_publisher_et = (EditText) layout.findViewById(R.id.share_publisher_et);
        share_description_et = (EditText) layout.findViewById(R.id.share_description_et);
    }

    private void setupShareBook3(ViewGroup layout) {
        //set up min duration
        common_spinner_ll_min_duration = (LinearLayout) layout.findViewById(R.id.common_spinner_min_duration);
        setupMinDuration(((Tenure)Utility.fetchDefault(master.getTenuresList())));

        //set up max duration
        common_spinner_ll_max_duration = (LinearLayout) layout.findViewById(R.id.common_spinner_max_duration);
        setupMaxDuration(((Tenure)Utility.fetchDefault(master.getTenuresList())));

        common_amount_et = (EditText) layout.findViewById(R.id.common_amount_et);
    }

    public void setupCategory(Category category){
        common_spinner_ll_category.setTag(category);
        ((TextView)common_spinner_ll_category.findViewById(R.id.common_spinner_tv)).setText(category.getCTGRY_NAME());
    }

    public void setupMinDuration(Tenure tenure){
        common_spinner_ll_min_duration.setTag(tenure);
        ((TextView)common_spinner_ll_min_duration.findViewById(R.id.common_spinner_tv)).setText(tenure.getTENURE_NAME());

        adjustMinMaxTenure(MAX_DURATION);
    }

    public void setupMaxDuration(Tenure tenure){
        common_spinner_ll_max_duration.setTag(tenure);
        ((TextView)common_spinner_ll_max_duration.findViewById(R.id.common_spinner_tv)).setText(tenure.getTENURE_NAME());

        adjustMinMaxTenure(MIN_DURATION);
    }

    private void adjustMinMaxTenure(String whichToAdjustStr){
        if(common_spinner_ll_min_duration == null || common_spinner_ll_min_duration.getTag() == null || common_spinner_ll_max_duration == null || common_spinner_ll_max_duration.getTag() == null){
            return;
        }

        Tenure minTenure = (Tenure) common_spinner_ll_min_duration.getTag();
        Tenure maxTenure = (Tenure) common_spinner_ll_max_duration.getTag();

        if(minTenure.getNO_OF_DAYS() > maxTenure.getNO_OF_DAYS()){
            if(MIN_DURATION.equalsIgnoreCase(whichToAdjustStr)){
                for(Tenure iterList : master.getTenuresList()){
                    if(iterList.getNO_OF_DAYS() == maxTenure.getNO_OF_DAYS()){
                        setupMinDuration(iterList);
                        break;
                    }
                }
            }
            else if(MAX_DURATION.equalsIgnoreCase(whichToAdjustStr)){
                for(Tenure iterList : master.getTenuresList()){
                    if(iterList.getNO_OF_DAYS() == minTenure.getNO_OF_DAYS()){
                        setupMaxDuration(iterList);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return layoutsList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getString(layoutsList.get(position));
    }

    public LinearLayout getCommon_spinner_ll_category() {
        return common_spinner_ll_category;
    }

    public LinearLayout getCommon_spinner_ll_min_duration() {
        return common_spinner_ll_min_duration;
    }

    public LinearLayout getCommon_spinner_ll_max_duration() {
        return common_spinner_ll_max_duration;
    }

    public EditText getShare_title_et() {
        return share_title_et;
    }

    public EditText getShare_author_et() {
        return share_author_et;
    }

    public EditText getShare_publisher_et() {
        return share_publisher_et;
    }

    public EditText getShare_description_et() {
        return share_description_et;
    }

    public EditText getCommon_amount_et() {
        return common_amount_et;
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