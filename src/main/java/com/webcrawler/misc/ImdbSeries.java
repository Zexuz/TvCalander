package com.webcrawler.misc;

public class ImdbSeries {

    private int id;
    private String title;

    public ImdbSeries(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
