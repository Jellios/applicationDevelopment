package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuoteReaderDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "QuoteReader.db";

    public QuoteReaderDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + QuoteReaderContract.QuoteEntry.TABLE_NAME + " (" +
                    QuoteReaderContract.QuoteEntry._ID + " INTEGER PRIMARY KEY, " +
                    QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT + " TEXT, " +
                    QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON + " TEXT, " +
                    QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_DATE + " TEXT " +
                    ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + QuoteReaderContract.QuoteEntry.TABLE_NAME;

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void updateQuote(int id, String text, String author, String date) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT, text);
        values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON, author);
        values.put(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_DATE, date);

        String selection = QuoteReaderContract.QuoteEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = db.update(
                QuoteReaderContract.QuoteEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);



    }
    public Quote getRandomQuote() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + QuoteReaderContract.QuoteEntry.TABLE_NAME +
                " ORDER BY RANDOM() LIMIT 1", null);
        if (cursor.getCount() <= 0)
        {
            return null;
        }
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Quote quote = new Quote();
        quote.setQuoteText(cursor.getString(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT)));
        quote.setQuoteAuthor(cursor.getString(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON)));
        quote.setQuoteDate(cursor.getString(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_DATE)));

        cursor.close();

        return quote;
    }
    public void deleteQuote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(QuoteReaderContract.QuoteEntry.TABLE_NAME, QuoteReaderContract.QuoteEntry._ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

}
