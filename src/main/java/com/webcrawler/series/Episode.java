package com.webcrawler.series;

public class Episode {

    private String airDate;
    private int number;


    public Episode(String airDate, int number) {
        this.airDate = airDate;
        this.number = number;
    }

    public String getAirDate() {
        return airDate;
    }

    public int getNumber() {
        return number;
    }


}
