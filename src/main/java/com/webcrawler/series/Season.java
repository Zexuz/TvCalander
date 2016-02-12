package com.webcrawler.series;


import java.util.ArrayList;

public class Season {

    public int season;
    private ArrayList<Episode> episodes;

    public Season(int season) {
        this.season = season;

        episodes = new ArrayList<Episode>();
    }


    public int getSeason() {
        return season;
    }

    public void addEpisode(Episode episode){
        episodes.add(episode);
    }

    public void addEpisodes(ArrayList<Episode> ep){
        episodes.addAll(ep);
    }

    public Episode getEpisode(int index){
        return episodes.get(index);
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }


}
