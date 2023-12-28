package com.example.project;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.SystemClock;

import androidx.test.core.app.ApplicationProvider;

import androidx.test.espresso.contrib.RecyclerViewActions;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class EditQuoteTest {
    @Rule
    public ActivityScenarioRule<MainActivity2> activityRule = new ActivityScenarioRule<>(MainActivity2.class);

    @Test
    public void clickQuote_opensDetailFragment_and_updatesQuote() {

        // Click on the first quote in the list
        onView(withId(R.id.recycler_view_quotes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Define a new quote, author, and date
        String newQuote = "This is an updated quote";
        String newAuthor = "Updated Author";
        String newDate = "01/01/2022";

        // Clear and input the new quote, author, and date
        onView(withId(R.id.editText_quote)).perform(clearText(), typeText(newQuote));
        onView(withId(R.id.editText_author)).perform(clearText(), typeText(newAuthor));
        onView(withId(R.id.editText_date)).perform(clearText(), typeText(newDate));
        SystemClock.sleep(250);
        // Click on the "Save" button
        onView(withId(R.id.button_save)).perform(click());
       // onView(ViewMatchers.withId(R.id.button_save)).perform(ViewActions.scrollTo()).perform(ViewActions.click());
        // Fetch the updated quote from the database
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

        // Check that the quote was updated in the database
        boolean quoteUpdated = false;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String quoteText = cursor.getString(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_TEXT));
                String quoteAuthor = cursor.getString(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_PERSON));
                String quoteDate = cursor.getString(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry.COLUMN_QUOTE_DATE));

                if (quoteText.equals(newQuote) && quoteAuthor.equals(newAuthor) && quoteDate.equals(newDate)) {
                    quoteUpdated = true;
                    break;
                }
            }
            cursor.close();
        }

        assertTrue(quoteUpdated);
    }

    @Test
    public void clickCancelButton_closesFragment() {
        // Click on the first quote in the list
        onView(withId(R.id.recycler_view_quotes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Click the cancel button
        onView(withId(R.id.discard_button)).perform(click());

        // Check if the fragment is closed by asserting that the RecyclerView (or any other view in the main activity) is displayed
        onView(withId(R.id.recycler_view_quotes)).check(matches(isDisplayed()));
    }
    @Test
    public void clickDeleteButton_removesQuoteFromDatabase() {
        // Click on the first quote in the list
        onView(withId(R.id.recycler_view_quotes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Get the ID of the quote to be deleted
        int quoteId = getFirstQuoteId();

        // Click the delete button
        onView(withId(R.id.button_delete)).perform(click());

        // Check if the quote has been removed from the database
        assertFalse(isQuoteInDatabase(quoteId));
    }

    private int getFirstQuoteId() {
        ContentResolver contentResolver = ApplicationProvider.getApplicationContext().getContentResolver();
        String[] projection = { QuoteReaderContract.QuoteEntry._ID };
        Cursor cursor = contentResolver.query(
                QuoteReaderContract.QuoteEntry.CONTENT_URI_QUOTES,
                projection,
                null,
                null,
                null
        );

        int quoteId = -1;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                quoteId = cursor.getInt(cursor.getColumnIndexOrThrow(QuoteReaderContract.QuoteEntry._ID));
            }
            cursor.close();
        }

        return quoteId;
    }

    private boolean isQuoteInDatabase(int quoteId) {
        ContentResolver contentResolver = ApplicationProvider.getApplicationContext().getContentResolver();
        String[] projection = { QuoteReaderContract.QuoteEntry._ID };
        String selection = QuoteReaderContract.QuoteEntry._ID + "=?";
        String[] selectionArgs = { String.valueOf(quoteId) };
        Cursor cursor = contentResolver.query(
                QuoteReaderContract.QuoteEntry.CONTENT_URI_QUOTES,
                projection,
                selection,
                selectionArgs,
                null
        );

        boolean quoteFound = false;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                quoteFound = true;
            }
            cursor.close();
        }

        return quoteFound;
    }
}