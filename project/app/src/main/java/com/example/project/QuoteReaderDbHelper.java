package com.example.project;

import android.content.Context;
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
                    QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON + " TEXT)";

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

}