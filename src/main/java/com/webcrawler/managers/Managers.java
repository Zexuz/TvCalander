package com.webcrawler.managers;

import com.webcrawler.connections.restapi.ImdbApi;
import com.webcrawler.misc.Common;
import com.webcrawler.options.Options;
import com.webcrawler.series.ImdbSeries;
import com.webcrawler.series.Series;
import com.webcrawler.torrent.Torrent;

import java.io.IOException;
import java.util.ArrayList;

public class Managers {
    // TODO: 2016-02-22
    /*
        change SiteManager to WebScraperManager?
     */
    private SiteManager siteManager;
    private ImdbApi imdbApi;
    public Options options;

    public Managers() {
        try {
            options = new Options();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Can't read options.config file, exiting");
            System.exit(9456);
        }

        siteManager = new SiteManager(this);
        imdbApi = new ImdbApi(this);

    }


    public void tick() {
        //1 get active shows from imdb once every day and post it to the database
        //2 search thePirateBay and other sites after those torrents
        //3 compare ids and torrents.
        //4 if it's a show we are looking for, add the torrent to the database.

        // TODO: 2016-03-01
        /*
            Change from ImdbSeries to just series
         */
        //1
        ArrayList<Series> seriesArrayList = imdbApi.getAllSeries();

        if (shouldScrapeImdb()) {
            for (String imdbId : siteManager.getImdbIds(0, 50)) {
                //s is
                ImdbSeries imdbSeries = Managers.createSeries(imdbId);
                imdbSeries.load();

                if (!imdbSeries.isPageValid()) {
                    System.out.println(imdbSeries.getId() + " is not valid");
                    continue;
                }

                Series series = imdbSeries.getSeries();
                //check if the ArrayList imdbSeriesArrayList contains the current series
                if (!doesContain(seriesArrayList, series)) {
                    //if it does not contain we just update it to the api and continues with the loop
                    imdbApi.addSeries(series);
                    seriesArrayList.add(series);
                    continue;
                }


                //check if we should update a series eg, imdb has new data
                for (Series apiSeriesShortInfo : seriesArrayList) {
                    if (apiSeriesShortInfo.getImdbId().equals(series.getImdbId())) {

                        //check if our old seasons data is correct.
                        Series apiSeriesFullInfo = imdbApi.getOneSeries(apiSeriesShortInfo.getImdbId());

                        if(!apiSeriesFullInfo.isSame(series)){
                            imdbApi.updateSeries(series);
                            break;
                        }
                    }
                }
            }
        }


        //2
        ArrayList<Torrent> torrents = siteManager.getRecentTorrentPages(5);
        //3
        ArrayList<Torrent> matchedTorrents = pairTorrentAndSeries(torrents, seriesArrayList);

        System.out.println(String.format("the list was %d, found %d torrents, matches %d ", seriesArrayList.size(), torrents.size(), matchedTorrents.size()));
        System.out.println(Common.toJson(matchedTorrents));

        //4


    }

    private boolean doesContain(ArrayList<Series> seriesArrayList, Series imdbSeries) {
        boolean contains = false;
        for (Series tempSeries : seriesArrayList) {
            if (tempSeries.getImdbId().equals(imdbSeries.getImdbId())) contains = true;
        }

        return contains;
    }

    private boolean shouldScrapeImdb() {
        return true;
    }

    private ArrayList<Torrent> pairTorrentAndSeries(ArrayList<Torrent> torrents, ArrayList<Series> seriesArrayList) {
        ArrayList<Torrent> matchedTorrents = new ArrayList<>();
        for (Series series : seriesArrayList) {

            for (Torrent torrent : torrents) {

                if (!torrent.isSeries()) continue;

                if (TorrentManager.isTorrentMatch(series, torrent)) {
                    matchedTorrents.add(torrent);
                }

            }

        }
        return matchedTorrents;
    }

    public static ImdbSeries createSeries(String id) {
        return new ImdbSeries(id);
    }

}
