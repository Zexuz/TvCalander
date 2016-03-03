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
        return seasons.get(--index);
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
        System.out.printf("doing series %s",series.getTitle());

        if (!getImgLink().equals(series.getImgLink())) {
            System.out.println("link");
            return false;
        }


        if (!getStartYear().equals(series.getStartYear())) {
            System.out.println("year");

            return false;
        }


        if (!getTitle().equals(series.getTitle())) {
            System.out.println("title");
            return false;
        }

        if (getSeasons().size() != series.getSeasons().size()) {
            System.out.println("seasons size");
            return false;
        }

        for (int i = 0; i < seasons.size(); i++) {
            Season cs1 = seasons.get(i);
            Season cs2 = series.seasons.get(i);

            if (cs1.getEpisodes().size() != cs2.getEpisodes().size()) {
                System.out.println("episode size");
                return false;
            }

            for (int episodeIndex = 0; episodeIndex < cs1.getEpisodes().size(); episodeIndex++) {
                Episode ep1 = cs1.getEpisodes().get(episodeIndex);
                Episode ep2 = cs2.getEpisodes().get(episodeIndex);

                if (!ep1.getAirDate().equals(ep2.getAirDate())) {
                    System.out.println("air dates");
                    return false;
                }


                if (ep1.getNumber() != ep2.getNumber()) {
                    System.out.println("episode number");
                    return false;
                }


                if (ep1.getTorrents().size() != ep2.getTorrents().size()) {
                    System.out.printf("\ntorrent size 1 %d",ep1.getTorrents().size());
                    System.out.printf("\ntorrent size 2 %d",ep2.getTorrents().size());
                    System.out.println("torrent size");
                    return false;
                }


            }
        }
        return true;
    }

    public static Series createSeries(String title) {
        return new Series(title);
    }

    public static Series createSeries() {
        return new Series();
    }
}
