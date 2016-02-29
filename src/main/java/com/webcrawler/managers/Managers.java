package com.webcrawler.managers;

import com.webcrawler.connections.restapi.ImdbApi;
import com.webcrawler.misc.Common;
import com.webcrawler.options.Options;
import com.webcrawler.series.ImdbSeries;
import com.webcrawler.torrent.Torrent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

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
        // TODO: 2016-02-15

        //1 get active shows from imdb once every day and post it to the database
        //2 search thePirateBay and other sites after those torrents
        //3 compare ids and torrents.
        //4 if it's a show we are looking for, add the torrent to the database.

        ArrayList<ImdbSeries> imdbSeriesArrayList;
        ArrayList<Torrent> torrents, matchedTorrents;


        // TODO: 2016-02-21 this is some bad code!!!
        /*
            We should only add series that is not already in the database
         */

        // TODO: 2016-02-21
        /*
            Clean up project
            Clean up code.

         */

        imdbSeriesArrayList = imdbApi.getAllSeries();

        if (shouldScrapeImdb()) {
            for (String imdbId : siteManager.getImdbIds(0, 50)) {
                //s is
                ImdbSeries imdbSeries = Managers.createSeries(imdbId);
                imdbSeries.load();

                if (!imdbSeries.isPageValid()) {
                    System.out.println(imdbSeries.getId() + " is not valid");
                    continue;
                }

                //check if the ArrayList imdbSeriesArrayList contains the current series
                if (!doesContain(imdbSeriesArrayList, imdbSeries)) {
                    //if it does not contain we just update it to the api and continues with the loop
                    imdbApi.addSeries(imdbSeries);
                    continue;
                }


                //check if we should update a series eg, imdb has new data
                for (ImdbSeries apiSeriesShortInfo : imdbSeriesArrayList) {
                    if (apiSeriesShortInfo.getId().equals(imdbSeries.getId())) {
                        //check if our old seasons data is correct.

                        //not the same image
                        ImdbSeries apiSeriesFullInfo = imdbApi.getOneSeries(apiSeriesShortInfo.getId());
                        if(!apiSeriesFullInfo.getImgLink().equals(imdbSeries.getImgLink())){
                            imdbApi.updateSeries(imdbSeries);
                            break;
                        }

                        //not the same title (yes I know, what's the odds...)
                        if (!apiSeriesFullInfo.getTitle().equals(imdbSeries.getTitle())) {
                            imdbApi.updateSeries(imdbSeries);
                            break;
                        }
                    }
                }
            }
        }


        //1
        //imdbSeries = imdbApi.getAllSeries();
        //2
        torrents = siteManager.getRecentTorrentPages(5);

        //3
        matchedTorrents = pairTorrentAndSeries(torrents, imdbSeriesArrayList);

        System.out.println(String.format("the list was %d, found %d torrents, matches %d ", imdbSeriesArrayList.size(), torrents.size(), matchedTorrents.size()));
        System.out.println(Common.toJson(matchedTorrents));

        //4


    }

    private boolean doesContain(ArrayList<ImdbSeries> imdbSeriesArrayList, ImdbSeries imdbSeries) {
        boolean contains = false;
        for (ImdbSeries imdbSery : imdbSeriesArrayList) {
            if (imdbSery.getId().equals(imdbSeries.getId())) contains = true;
        }

        return contains;
    }

    private boolean shouldScrapeImdb() {
        return true;
    }

    private ArrayList<Torrent> pairTorrentAndSeries(ArrayList<Torrent> torrents, ArrayList<ImdbSeries> imdbSeriesArrayList) {
        ArrayList<Torrent> matchedTorrents = new ArrayList<>();
        for (ImdbSeries imdbSeries : imdbSeriesArrayList) {

            for (Torrent torrent : torrents) {

                if (!torrent.isSeries()) continue;

                if (Common.isTorrentMatch(imdbSeries, torrent)) {
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
