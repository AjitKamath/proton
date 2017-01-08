package com.android.bookmybook.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static com.android.bookmybook.util.Constants.UI_FONT;

/**
 * Created by ajit on 30/9/15.
 */
public class ViewPagerAdapterShareBook extends PagerAdapter {
    private final String CLASS_NAME = this.getClass().getName();
    private Context mContext;

    private List<Integer> layoutsList;
    public int activePageIndex = 0;


    public ViewPagerAdapterShareBook(Context context, List<Integer> layoutsList) {
        this.mContext = context;
        this.layoutsList = layoutsList;
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
    }

    private void setupShareBook2(ViewGroup layout) {
    }

    private void setupShareBook3(ViewGroup layout) {
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