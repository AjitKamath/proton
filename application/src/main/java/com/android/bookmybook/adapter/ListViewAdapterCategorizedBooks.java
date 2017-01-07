package com.android.bookmybook.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.bookmybook.R;
import com.android.bookmybook.model.BooksList;

import java.util.List;

import static com.android.bookmybook.util.Constants.UI_FONT;

/**
 * Created by ajit on 17/1/15.
 */
public class ListViewAdapterCategorizedBooks extends BaseAdapter {
    private final String CLASS_NAME = this.getClass().getName();
    private Context mContext;
    private LayoutInflater inflater;

    private static final int layout = R.layout.list_item_category_books;

    private List<BooksList> booksList;

    public ListViewAdapterCategorizedBooks(Context mContext, List<BooksList> booksList) {
        super();

        this.mContext = mContext;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.booksList = booksList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;

        if(convertView == null) {
            mHolder = new ViewHolder();
            convertView = inflater.inflate(layout, null);

            mHolder.category_books_ll = (LinearLayout) convertView.findViewById(R.id.category_books_ll);
            mHolder.book_category_heading_tv = (TextView) convertView.findViewById(R.id.book_category_heading_tv);
            mHolder.book_category_heading_rl = (RelativeLayout) convertView.findViewById(R.id.book_category_heading_rl);
            mHolder.category_books_rv = (RecyclerView) convertView.findViewById(R.id.category_books_rv);

            convertView.setTag(layout, mHolder);

        } else {
            mHolder = (ViewHolder) convertView.getTag(layout);
        }

        BooksList categoryBooksList = booksList.get(position);

        //heading
        //hard codes
        if("FICTION".equalsIgnoreCase(categoryBooksList.getCategory())){
            mHolder.book_category_heading_rl.setBackgroundResource(R.drawable.science_fiction);
        }
        else if("THRILLER".equalsIgnoreCase(categoryBooksList.getCategory())){
            mHolder.book_category_heading_rl.setBackgroundResource(R.drawable.thriller);
        }

        mHolder.book_category_heading_tv.setText(categoryBooksList.getCategory());

        //books grid
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        mHolder.category_books_rv.setLayoutManager(layoutManager);

        RecyclerViewAdapterBooks adapter = new RecyclerViewAdapterBooks(mContext, categoryBooksList.getBooksList());
        mHolder.category_books_rv.setAdapter(adapter);

        mHolder.category_books_rv.setNestedScrollingEnabled(false);

        setFont(mHolder.category_books_ll);

        return convertView;
    }

    @Override
    public int getCount() {
        return booksList.size();
    }

    @Override
    public BooksList getItem(int position) {
        return booksList.get(position);
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
        private LinearLayout category_books_ll;
        private TextView book_category_heading_tv;
        private RecyclerView category_books_rv;
        private RelativeLayout book_category_heading_rl;

    }

}