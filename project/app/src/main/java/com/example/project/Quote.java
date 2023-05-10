package com.example.project;

import java.util.Date;

public class Quote {
    private String quoteText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public String getQuoteAuthor() {
        return quoteAuthor;
    }

    public void setQuoteAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }

    private String quoteAuthor;

    public String getQuoteDate() {
        return quoteDate;
    }

    public void setQuoteDate(String quoteDate) {
        this.quoteDate = quoteDate;
    }

    private String quoteDate;

    public Quote(String quoteText, String author, String writeDate, int id)
    {
        this.quoteText = quoteText;
        this.quoteAuthor = author;
        this.quoteDate = writeDate;
        this.id = id;
    }
    public Quote()
    {
        this.id = 0;
        this.quoteText = "";
        this.quoteAuthor = "";
        this.quoteDate = "";
    }

    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }


}
