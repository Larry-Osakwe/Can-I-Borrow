package com.larry.osakwe.caniborrow;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.larry.osakwe.caniborrow.data.ItemContract.ItemEntry;

/**
 * Displays list of borrowed items that were entered and stored in the app.
 */


public class CatalogActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    // Identifies a particular Loader being used in this component
    private static final int ITEM_LOADER = 0;

    // This is the Adapter being used to display the list's data.
    private ItemCursorAdapter mItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Find the ListView that will be populated with item data
        ListView itemListView = (ListView) findViewById(R.id.list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        itemListView.setEmptyView(emptyView);

        // Set up an adapter to set a list item for each row of item data in the cursor
        mItemAdapter = new ItemCursorAdapter(this, null);

        // Attach adapter to the ListView
        itemListView.setAdapter(mItemAdapter);


        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                // Create a new intent to go to the {@link EditorActivity}
                Intent itemIntent = new Intent(CatalogActivity.this, EditorActivity.class);

                // Form the content URI that represents the specific item that was clicked on
                Uri itemUri = ContentUris.withAppendedId(ItemEntry.CONTENT_URI, id);

                // Set the URI on the dta field of the intent
                itemIntent.setData(itemUri);

                // Send the intent to launch a new activity
                startActivity(itemIntent);
            }
        });

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        //Initialize the CursorLoader
        getLoaderManager().initLoader(ITEM_LOADER, null, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deletePets();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deletePets() {
        int rowsDeleted = getContentResolver().delete(ItemEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from item database");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle args) {

        // Perform this raw SQL query "SELECT * FROM items"

        String[] projection = {ItemEntry._ID,
                ItemEntry.COLUMN_ITEM_NAME,
                ItemEntry.COLUMN_BORROWER_NAME,
                ItemEntry.COLUMN_DATE_TIME};

        switch (loaderID) {
            case ITEM_LOADER:
                // Returns a new CursorLoader
                return new CursorLoader(this, ItemEntry.CONTENT_URI, projection, null, null, null);
            default:
                // An invalid id was passed in
                return null;
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Swap the new cursor in.
        mItemAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when data needs to be deleted
        mItemAdapter.swapCursor(null);
    }
}
