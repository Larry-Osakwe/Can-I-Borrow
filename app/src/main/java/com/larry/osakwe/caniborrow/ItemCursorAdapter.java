package com.larry.osakwe.caniborrow;

import android.content.Context;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.larry.osakwe.caniborrow.data.ItemContract.ItemEntry;

import java.util.Date;

/**
 * Created by Larry Osakwe on 7/21/2017.
 */


/**
 * {@link ItemCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of item data as its data source. This adapter knows
 * how to create list items for each row of item data in the {@link Cursor}.
 */
public class ItemCursorAdapter extends CursorAdapter {

    public ItemCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Create and return new blank list item
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Populate list item view with item data

        TextView itemName = (TextView) view.findViewById(R.id.name);
        TextView borrowerName = (TextView) view.findViewById(R.id.borrower);
        TextView datePicker = (TextView) view.findViewById(R.id.date);


        String name = cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_NAME));
        String borrower = cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_BORROWER_NAME));
        String date = dateFormat(cursor);

        // If the borrower name is empty string or null, then use some default text
        // that says "Unknown Borrower", so the TextView isn't blank.
        if (TextUtils.isEmpty(borrower)) {
            borrower = context.getString(R.string.empty_view_subtitle_text);
        }
        itemName.setText(name);
        borrowerName.setText(borrower);
        datePicker.setText(date);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String dateFormat(Cursor cursor) {
        long timeInMilliseconds = cursor.getLong(cursor.getColumnIndex(ItemEntry.COLUMN_DATE_TIME));
        Date dateObject = new Date(timeInMilliseconds);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yy, hh:mm a");
        return dateFormatter.format(dateObject);
    }
}
