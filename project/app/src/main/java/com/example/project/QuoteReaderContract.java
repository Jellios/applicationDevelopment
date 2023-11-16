package com.example.project;

import android.net.Uri;
import android.provider.BaseColumns;

import java.util.Date;

public final class QuoteReaderContract {
    // Content provider authority
    public static final String CONTENT_AUTHORITY = "com.example.project.provider";

    // Base content URI
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Path for the "quotes" table
    public static final String PATH_QUOTES = "quotes";

    private QuoteReaderContract() {}

    public static class QuoteEntry implements BaseColumns {
        // Content URI for the "quotes" table
        public static final Uri CONTENT_URI_QUOTES = BASE_CONTENT_URI.buildUpon().appendPath(PATH_QUOTES).build();

        // MIME type for a list of quotes
        public static final String CONTENT_TYPE_QUOTES = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_QUOTES;

        // MIME type for a single quote
        public static final String CONTENT_ITEM_TYPE_QUOTE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_QUOTES;

        // Table name
        public static final String TABLE_NAME = "quotes";

        // Column names
        public static final String COLUMN_QUOTE_TEXT = "quote_text";
        public static final String COLUMN_QUOTE_PERSON = "quote_person";
        public static final String COLUMN_QUOTE_DATE = "quote_date";
    }
}
