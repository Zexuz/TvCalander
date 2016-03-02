package com.webcrawler.series;

import com.webcrawler.torrent.Torrent;

import java.util.ArrayList;

public class Episode {

    private String airDate;
    private int number;
    private ArrayList<Torrent> torrents;


    public Episode(String airDate, int number) {
        this.airDate = airDate;
        this.number = number;
        torrents = new ArrayList<>();
    }


    public void addTorrent(Torrent torrent){
        if(!torrents.contains(torrent)){
            System.out.println("adding torrent");
            torrents.add(torrent);
            return;
        }

        System.out.println("Torrent already added");

    }

    public String getAirDate() {
        return airDate;
    }

    public int getNumber() {
        return number;
    }

    public static Episode createEpisode(String airDate, int number){
        return new Episode(airDate,number);
    }

    public ArrayList<Torrent> getTorrents() {
        return torrents;
    }

    public void setTorrents(ArrayList<Torrent> torrents) {
        this.torrents = torrents;
    }



}
