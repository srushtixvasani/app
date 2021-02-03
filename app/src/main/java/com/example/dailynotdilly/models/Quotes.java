package com.example.dailynotdilly.models;

public class Quotes {
    private String quote;

    public Quotes(String quote) {
        this.quote = quote;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    @Override
    public String toString() {
        return  quote;
    }
}
