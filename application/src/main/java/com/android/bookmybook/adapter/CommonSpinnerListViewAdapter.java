package com.android.bookmybook.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.bookmybook.R;
import com.android.bookmybook.model.BooksList;
import com.android.bookmybook.model.Category;
import com.android.bookmybook.model.Tenure;

import java.util.ArrayList;
import java.util.List;

import static com.android.bookmybook.util.Constants.UI_FONT;
import static com.android.bookmybook.util.Constants.UN_IDENTIFIED_OBJECT_TYPE;

/**
 * Created by ajit on 17/1/15.
 */
public class CommonSpinnerListViewAdapter extends BaseAdapter {
    private final String CLASS_NAME = this.getClass().getName();
    private Context mContext;
    private LayoutInflater inflater;

    private static int layout = R.layout.common_spinner_fragment_item;

    private static List<? extends Object> list;
    private static String selectedItemIdStr;

    public CommonSpinnerListViewAdapter(Context mContext, List<? extends Object> list, String selectedItemIdStr) {
        super();

        this.mContext = mContext;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.list = list;
        this.selectedItemIdStr = selectedItemIdStr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;

        if(convertView == null) {
            mHolder = new ViewHolder();
            convertView = inflater.inflate(layout, null);

            mHolder.common_spinner_fragment_item_ll = (LinearLayout) convertView.findViewById(R.id.common_spinner_fragment_item_ll);
            mHolder.common_spinner_fragment_item_view = convertView.findViewById(R.id.common_spinner_fragment_item_selected_view);
            mHolder.common_spinner_fragment_item_tv = (TextView) convertView.findViewById(R.id.common_spinner_fragment_item_tv);

            convertView.setTag(layout, mHolder);

        } else {
            mHolder = (ViewHolder) convertView.getTag(layout);
        }

        Object object = list.get(position);

        //category
        if(object instanceof Category){
            Category category = (Category) object;

            if(selectedItemIdStr.equalsIgnoreCase(category.getCTGRY_ID())){
                mHolder.common_spinner_fragment_item_view.setVisibility(View.VISIBLE);
            }
            else{
                mHolder.common_spinner_fragment_item_view.setVisibility(View.INVISIBLE);
            }

            mHolder.common_spinner_fragment_item_tv.setText(category.getCTGRY_NAME());
        }

        //tenure
        else if(object instanceof Tenure){
            Tenure tenure = (Tenure) object;

            if(selectedItemIdStr.equalsIgnoreCase(tenure.getTENURE_ID())){
                mHolder.common_spinner_fragment_item_view.setVisibility(View.VISIBLE);
            }
            else{
                mHolder.common_spinner_fragment_item_view.setVisibility(View.INVISIBLE);
            }

            mHolder.common_spinner_fragment_item_tv.setText(tenure.getTENURE_NAME());
        }

        else{
            Log.e(CLASS_NAME, UN_IDENTIFIED_OBJECT_TYPE+object);
        }

        mHolder.common_spinner_fragment_item_ll.setTag(object);

        setFont(mHolder.common_spinner_fragment_item_ll);

        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
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

    private class ViewHolder {
        private LinearLayout common_spinner_fragment_item_ll;
        private View common_spinner_fragment_item_view;
        private TextView common_spinner_fragment_item_tv;
    }
}