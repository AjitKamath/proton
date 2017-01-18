package com.android.bookmybook.adapter;

/**
 * Created by ajit on 17/1/17.
 */

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.bookmybook.R;
import com.android.bookmybook.model.Condition;

import java.util.List;

import static com.android.bookmybook.util.Constants.UI_FONT;

public class HorizontalListAdapter extends RecyclerView.Adapter<HorizontalListAdapter.ViewHolder> {
    private final String CLASS_NAME = this.getClass().getName();

    private static final int layout = R.layout.grid_item_condition;

    private Activity activity;
    private List<Condition> itemsList;

    public HorizontalListAdapter(Activity activity, List<Condition> conditionList) {
        this.activity = activity;
        this.itemsList = conditionList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HorizontalListAdapter.ViewHolder viewHolder, final int position) {
        Condition condition = itemsList.get(position);

        viewHolder.grid_item_condition_category_tv.setText(condition.getCNDTN_CTGRY());
        viewHolder.grid_item_condition_description_tv.setText(condition.getConditionDesc());

        viewHolder.grid_item_condition_options_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });

        setFont(viewHolder.grid_item_condition_ll);
    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(activity.getApplicationContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.condition_options, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        popup.show();
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    /**
     * View holder to display each RecylerView item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout grid_item_condition_ll;
        private TextView grid_item_condition_category_tv;
        private ImageView grid_item_condition_options_iv;
        private TextView grid_item_condition_description_tv;

        public ViewHolder(View view) {
            super(view);
            grid_item_condition_ll = (LinearLayout) view.findViewById(R.id.grid_item_condition_ll);
            grid_item_condition_category_tv = (TextView) view.findViewById(R.id.grid_item_condition_category_tv);
            grid_item_condition_options_iv = (ImageView) view.findViewById(R.id.grid_item_condition_options_iv);
            grid_item_condition_description_tv = (TextView) view.findViewById(R.id.grid_item_condition_description_tv);
        }
    }

    //method iterates over each component in the activity and when it finds a text view..sets its font
    public void setFont(ViewGroup group) {
        //set font for all the text view
        final Typeface robotoCondensedLightFont = Typeface.createFromAsset(activity.getApplicationContext().getAssets(), UI_FONT);

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
