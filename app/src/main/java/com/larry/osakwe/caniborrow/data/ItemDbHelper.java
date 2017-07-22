package com.larry.osakwe.caniborrow.data;

/**
 * Created by Larry Osakwe on 7/18/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.larry.osakwe.caniborrow.data.ItemContract.ItemEntry;

/**
 * Database helper for Borrow app. Manages database creation and version management.
 */

public class ItemDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = ItemDbHelper.class.getSimpleName();

    // Create a String that contains the SQL statement to create the items table
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
            ItemEntry.TABLE_NAME + " (" +
            ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ItemEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, " +
            ItemEntry.COLUMN_BORROWER_NAME + " TEXT NOT NULL, " +
            ItemEntry.COLUMN_DATE_TIME + " INTEGER NOT NULL DEFAULT 0);";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ItemEntry.TABLE_NAME;

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "borrowedItems.db";

    /**
     * Database version. If the database schema changes, increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instanct of {@link ItemDbHelper}.
     *
     * @param context of the app
     */
    public ItemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
