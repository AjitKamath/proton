package com.android.bookmybook.adapter;

/**
 * Created by ajit on 6/1/17.
 */

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.bookmybook.R;
import com.android.bookmybook.model.Book;
import com.bumptech.glide.Glide;

import java.util.List;

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.ViewHolder> {
    private List<Book> items;
    private Context context;
    private LayoutInflater inflater;

    public GridViewAdapter(Context context, List<Book> items) {
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
    public void onBindViewHolder(GridViewAdapter.ViewHolder viewHolder, int position) {
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
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.book_options, popup.getMenu());
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
