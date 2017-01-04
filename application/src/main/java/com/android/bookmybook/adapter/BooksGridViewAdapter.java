package com.android.bookmybook.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.bookmybook.R;
import com.android.bookmybook.model.Book;

import java.util.List;

import static com.android.bookmybook.util.Constants.UI_FONT;

/**
 * Created by ajit on 8/1/15.
 */
// Inner Class
public class BooksGridViewAdapter extends BaseAdapter {
    private final String CLASS_NAME = this.getClass().getName();
    private final Context mContext;
    private LayoutInflater inflater;

    private static final int layout = R.layout.grid_item_book;

    private List<Book> booksList;

    public BooksGridViewAdapter(Context context, List<Book> booksList) {
        super();
        this.mContext = context;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.booksList = booksList;
    }

    @Override
    public int getCount() {
        return booksList.size();
    }

    @Override
    public Object getItem(int i) {
        return booksList.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        View view = convertView;

        if(convertView == null) {
            mHolder = new ViewHolder();
            view = inflater.inflate(layout, null);

            mHolder.book_ll = (LinearLayout) view.findViewById(R.id.book_ll);
            mHolder.book_iv = (ImageView) view.findViewById(R.id.book_iv);
            mHolder.book_title_tv = (TextView) view.findViewById(R.id.book_title_tv);
            mHolder.book_author_tv = (TextView) view.findViewById(R.id.book_author_tv);
            mHolder.book_rent_tv = (TextView) view.findViewById(R.id.book_rent_tv);
            mHolder.book_duration_tv = (TextView) view.findViewById(R.id.book_duration_tv);
            /*mHolder.book_share_tv = (TextView) view.findViewById(R.id.book_share_tv);
            mHolder.book_seek_tv = (TextView) view.findViewById(R.id.book_seek_tv);
            mHolder.book_wish_tv = (TextView) view.findViewById(R.id.book_wish_tv);*/

            view.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) view.getTag();
        }

        Book book = booksList.get(position);

        //mHolder.book_iv.setImageBitmap();
        mHolder.book_title_tv.setText(book.getTITLE());
        mHolder.book_author_tv.setText(book.getAUTHOR());
        mHolder.book_rent_tv.setText(String.valueOf(book.getRENT()));
        mHolder.book_duration_tv.setText("month");

        setFont(mHolder.book_ll);

        return view;
    }

    public class ViewHolder {
        private LinearLayout book_ll;
        private ImageView book_iv;
        private TextView book_title_tv;
        private TextView book_author_tv;
        private TextView book_rent_tv;
        private TextView book_duration_tv;
        /*private TextView book_share_tv;
        private TextView book_seek_tv;
        private TextView book_wish_tv;*/
    }

    //method iterates over each component in the activity and when it finds a text view..sets its font
    public void setFont(ViewGroup group) {
        final Typeface font = Typeface.createFromAsset(mContext.getAssets(), UI_FONT);

        int count = group.getChildCount();
        View v;

        for(int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if(v instanceof TextView) {
                ((TextView) v).setTypeface(font);
            }
            else if(v instanceof ViewGroup) {
                setFont((ViewGroup) v);
            }
        }
    }
}
