package com.example.project;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.content.UriMatcher;

public class MyContentProvider extends ContentProvider {

    private QuoteReaderDbHelper dbHelper;
    private static final int QUOTES = 1;
    private static final int QUOTE_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
       // sUriMatcher.addURI(QuoteReaderContract.CONTENT_AUTHORITY,QuoteReaderContract.PATH_QUOTES,QUOTES);
        sUriMatcher.addURI("com.example.project.provider","quotes", QUOTES);
        sUriMatcher.addURI(QuoteReaderContract.CONTENT_AUTHORITY, QuoteReaderContract.PATH_QUOTES + "/#", QUOTE_ID);
    }



    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case QUOTES:
                id = db.insert(QuoteReaderContract.QuoteEntry.TABLE_NAME, null, values);
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        if (id != -1) {
            getContext().getContentResolver().notifyChange(uri, null);
            return ContentUris.withAppendedId(uri, id);
        } else {
            throw new android.database.SQLException("Failed to insert row into " + uri);
        }
    }

    @Override
    public boolean onCreate() {
        dbHelper = new QuoteReaderDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // Get readable database
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case QUOTES:
                // For the QUOTES code, query the quotes table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the quotes table.
                cursor = database.query(QuoteReaderContract.QuoteEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        // Set notification URI on the Cursor, so we know what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the cursor
        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writable database
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case QUOTES:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(QuoteReaderContract.QuoteEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case QUOTE_ID:
                // Delete a single row given by the ID in the URI
                selection = QuoteReaderContract.QuoteEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(QuoteReaderContract.QuoteEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case QUOTES:
                // Update quotes that match the selection and selection args
                return updateQuote(uri, values, selection, selectionArgs);
            case QUOTE_ID:
                // Update a single quote given by the ID in the URI
                selection = QuoteReaderContract.QuoteEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateQuote(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }
    private int updateQuote(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Get writable database
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(QuoteReaderContract.QuoteEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows updated
        return rowsUpdated;
    }
}
