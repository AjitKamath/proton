package com.android.bookmybook.adapter;

/**
 * Created by ajit on 6/1/17.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.bookmybook.R;
import com.android.bookmybook.activity.HomeActivity;
import com.android.bookmybook.model.Book;
import com.bumptech.glide.Glide;
import com.bumptech.glide.provider.ChildLoadProvider;

import java.lang.reflect.Field;
import java.util.List;

import static com.android.bookmybook.util.Constants.UI_FONT;

public class RecyclerViewAdapterBooks extends RecyclerView.Adapter<RecyclerViewAdapterBooks.ViewHolder> {
    private static final String CLASS_NAME = HomeActivity.class.getName();

    private List<Book> items;
    private Context context;
    private LayoutInflater inflater;

    public RecyclerViewAdapterBooks(Context context, List<Book> items) {
        super();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.grid_item_book, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterBooks.ViewHolder viewHolder, int position) {
        Book book = items.get(position);

        // loading album cover using Glide library
        Glide.with(context).load(R.drawable.book_check).into(viewHolder.book_iv);

        viewHolder.book_title_tv.setText(book.getTITLE());
        viewHolder.book_author_tv.setText(book.getAUTHOR());
        viewHolder.book_rent_tv.setText(String.valueOf(book.getRENT()));
        viewHolder.book_duration_tv.setText("MONTH");

        viewHolder.book_options_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });

        setFont(viewHolder.book_ll);
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.book_options);

        // Force icons to show
        Object menuHelper;
        Class[] argTypes;
        try {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popupMenu);
            argTypes = new Class[] { boolean.class };
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
        } catch (Exception e) {
            // Possible exceptions are NoSuchMethodError and NoSuchFieldError
            //
            // In either case, an exception indicates something is wrong with the reflection code, or the
            // structure of the PopupMenu class or its dependencies has changed.
            //
            // These exceptions should never happen since we're shipping the AppCompat library in our own apk,
            // but in the case that they do, we simply can't force icons to display, so log the error and
            // show the menu normally.

            Log.w(CLASS_NAME, "error forcing menu icons to show", e);
            popupMenu.show();
            return;
        }

        popupMenu.show();



        /*PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.book_options, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        popup.show();*/
    }

    //method iterates over each component in the activity and when it finds a text view..sets its font
    public void setFont(ViewGroup group) {
        //set font for all the text view
        final Typeface robotoCondensedLightFont = Typeface.createFromAsset(context.getAssets(), UI_FONT);

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

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * View holder to display each RecylerView item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout book_ll;
        private ImageView book_iv;
        private TextView book_title_tv;
        private TextView book_author_tv;
        private TextView book_rent_tv;
        private TextView book_duration_tv;
        private ImageView book_options_iv;

        public ViewHolder(View view) {
            super(view);
            book_ll = (LinearLayout) view.findViewById(R.id.book_ll);
            book_iv = (ImageView) view.findViewById(R.id.book_iv);
            book_title_tv = (TextView) view.findViewById(R.id.book_title_tv);
            book_author_tv = (TextView) view.findViewById(R.id.book_author_tv);
            book_rent_tv = (TextView) view.findViewById(R.id.book_rent_tv);
            book_duration_tv = (TextView) view.findViewById(R.id.book_duration_tv);
            book_options_iv = (ImageView) view.findViewById(R.id.book_options_iv);
        }
    }
}
