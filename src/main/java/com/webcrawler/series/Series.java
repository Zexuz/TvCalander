package com.webcrawler.series;

import java.util.ArrayList;

public class Series {

    public String title;
    private String imdbId;
    private String startYear;

    private ArrayList<Season> seasons;

    public Series() {
        seasons = new ArrayList<>();
    }

    public Series(String title) {
        this.title = title;
        seasons = new ArrayList<>();
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addSeason(Season season) {
        seasons.add(season);
    }

    public void addSeasons(ArrayList<Season> list) {
        seasons.addAll(list);
    }

    public Season getSeason(int index) {
        return seasons.get(index);
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

}
