package com.larry.osakwe.caniborrow.data;

/**
 * Created by Larry Osakwe on 7/18/2017.
 */

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * API Contract for the Borrow app.
 **/
public class ItemContract {

    public static final String CONTENT_AUTHORITY = "com.larry.osakwe.caniborrow";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_ITEMS = "items";

    // To prevent someone from accidentally instantiating the contract class, give
    // it an empty constructor
    private ItemContract(){
    }

    /**
     * Inner class that defines constant values for the items database table.
     * Each entry in the table represents a single item.
     */
    public static final class ItemEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ITEMS);

        /**
         * Name of database table for items
         */
        public final static String TABLE_NAME = "items";

        /**
         * Uniquw ID number for the item (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the item.
         *
         * Type: TEXT
         */

        public final static String COLUMN_ITEM_NAME = "name";

        /**
         * Name of the borrower.
         *
         * Type: TEXT
         */

        public final static String COLUMN_BORROWER_NAME = "borrower";

        /**
         * Date and Time of return.
         *
         * Type: INTEGER
         */

        public final static String COLUMN_DATE_TIME = "date";

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE +
                "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;


    }
}
