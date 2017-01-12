package com.android.bookmybook.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.bookmybook.R;
import com.android.bookmybook.model.Category;
import com.android.bookmybook.model.Master;
import com.android.bookmybook.model.Tenure;
import com.android.bookmybook.util.Utility;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.android.bookmybook.util.Constants.UI_FONT;

/**
 * Created by ajit on 30/9/15.
 */
public class ViewPagerAdapterShareBook extends PagerAdapter {
    private final String CLASS_NAME = this.getClass().getName();
    private Context mContext;

    /*components*/
    private LinearLayout common_spinner_ll_category;
    private LinearLayout common_spinner_ll_min_duration;
    private LinearLayout common_spinner_ll_max_duration;
    /*components*/

    private List<Integer> layoutsList;
    private Master master;
    public int activePageIndex = 0;


    public ViewPagerAdapterShareBook(Context context, List<Integer> layoutsList, Master master) {
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

        //ButterKnife.bind(this, layout);

        return layout;
    }

    private void setupShareBook1(ViewGroup layout) {
    }

    private void setupShareBook2(ViewGroup layout) {
        //set up default selected category
        common_spinner_ll_category = (LinearLayout) layout.findViewById(R.id.common_spinner_category);
        setupCategory((Category) Utility.fetchDefault(master.getCategoriesList()));

    }

    private void setupShareBook3(ViewGroup layout) {
        //set up min duration
        common_spinner_ll_min_duration = (LinearLayout) layout.findViewById(R.id.common_spinner_min_duration);
        setupMinDuration(((Tenure)Utility.fetchDefault(master.getTenuresList())));

        //set up max duration
        common_spinner_ll_max_duration = (LinearLayout) layout.findViewById(R.id.common_spinner_max_duration);
        setupMaxDuration(((Tenure)Utility.fetchDefault(master.getTenuresList())));
    }

    public void setupCategory(Category category){
        common_spinner_ll_category.setTag(category);
        ((TextView)common_spinner_ll_category.findViewById(R.id.common_spinner_tv)).setText(category.getCTGRY_NAME());
    }

    public void setupMinDuration(Tenure tenure){
        common_spinner_ll_min_duration.setTag(tenure);
        ((TextView)common_spinner_ll_min_duration.findViewById(R.id.common_spinner_tv)).setText(tenure.getTENURE_NAME());
    }

    public void setupMaxDuration(Tenure tenure){
        common_spinner_ll_max_duration.setTag(tenure);
        ((TextView)common_spinner_ll_max_duration.findViewById(R.id.common_spinner_tv)).setText(tenure.getTENURE_NAME());
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