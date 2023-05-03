package com.example.project;

import android.provider.BaseColumns;

import java.util.Date;

public final class QuoteReaderContract {
    private QuoteReaderContract() {}
    public static class QuoteEntry implements BaseColumns {
        public static final  String TABLE_NAME = "quotes";
        public static final String COLUMN_QUOTE_TEXT = "quote_text";
        public static final String COLUMN_QUOTE_PERSON = "quote_person";

        public static  final String COLUMN_QUOTE_DATE = "quote_date" ;
    }
}
