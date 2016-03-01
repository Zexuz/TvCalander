package com.webcrawler.series;

import java.util.ArrayList;

public class Series {

    private String title;
    private String imdbId;
    private String startYear;
    private String imgLink;

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

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public boolean isSame(Series series) {

        if (!getImdbId().equals(series.getImdbId()))
            return false;


        if (!getImgLink().equals(series.getImgLink()))
            return false;


        if (!getStartYear().equals(series.getStartYear()))
            return false;


        if (!getTitle().equals(series.getTitle()))
            return false;

        if (getSeasons().size() != series.getSeasons().size())
            return false;

        for (int i = 0; i < seasons.size(); i++) {
            Season cs1 = seasons.get(i);
            Season cs2 = series.seasons.get(i);

            if (cs1.getEpisodes().size() != cs2.getEpisodes().size())
                return false;

            for (int episodeIndex = 0; episodeIndex < cs1.getEpisodes().size(); episodeIndex++) {
                Episode ep1 = cs1.getEpisodes().get(episodeIndex);
                Episode ep2 = cs2.getEpisodes().get(episodeIndex);

                if (!ep1.getAirDate().equals(ep2.getAirDate()))
                    return false;


                if (ep1.getNumber() != ep2.getNumber())
                    return false;

            }
        }
        return true;
    }
}
