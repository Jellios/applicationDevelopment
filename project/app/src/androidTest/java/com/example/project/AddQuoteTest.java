package com.example.project;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import android.content.ContentResolver;
import android.database.Cursor;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class AddQuoteTest {
    @Rule
    public ActivityScenarioRule<MainActivity2> activityRule = new ActivityScenarioRule<>(MainActivity2.class);

    @Test
    public void clickAddQuoteButton_insertsQuoteIntoDatabase() {
        // Define a quote, author, and date
        String quote = "Dit is een automated test met esspresso";
        String author = "Espresso";
        String date = "18/11/2001";

        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.editText_quote)).perform(typeText(quote));
        onView(withId(R.id.editText_author)).perform(typeText(author));
        onView(withId(R.id.editText_date)).perform(typeText(date));

        onView(withId(R.id.button_add)).perform(click());

        ContentResolver contentResolver = ApplicationProvider.getApplicationContext().getContentResolver();
        String[] projection = {
                QuoteReaderContract.QuoteEntry._ID,
                QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT,
                QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON,
                QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_DATE
        };
        Cursor cursor = contentResolver.query(
                QuoteReaderContract.QuoteEntry.CONTENT_URI_QUOTES,
                projection,
                null,
                null,
                null
        );

        boolean quoteFound = false;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String quoteText = cursor.getString(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT));
                String quoteAuthor = cursor.getString(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON));
                String quoteDate = cursor.getString(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_DATE));

                if (quoteText.equals(quote) && quoteAuthor.equals(author) && quoteDate.equals(date)) {
                    quoteFound = true;
                    break;
                }
            }
            cursor.close();
        }

        assertTrue(quoteFound);
    }
    @Test
    public void clickCancelButton_closesFragment() {
        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.button_cancel)).perform(click());

        onView(withId(R.id.recycler_view_quotes)).check(matches(isDisplayed()));
    }
}