package com.webcrawler.managers;

import com.webcrawler.connections.restapi.ImdbApi;
import com.webcrawler.misc.Common;
import com.webcrawler.options.Options;
import com.webcrawler.series.ImdbSeries;
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
        // TODO: 2016-02-15

        //1 get active shows from imdb once every day and post it to the database
        //2 search thePirateBay and other sites after those torrents
        //3 compare ids and torrents.
        //4 if it's a show we are looking for, add the torrent to the database.

        ArrayList<ImdbSeries> imdbSeries;
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
        if (shouldScrapeImdb()) {
            for (String s : siteManager.getImdbIds(0, 50)) {

                ImdbSeries imdbSeriesTemp = Managers.createSeries(s);

                imdbSeriesTemp.load();

                if (!imdbSeriesTemp.isPageValid()) {
                    System.out.println("not valid");
                    continue;
                }
                imdbApi.addSeries(imdbSeriesTemp);
            }
        }


        //1
        imdbSeries = imdbApi.getAllSeries();
        //2
        torrents = siteManager.getRecentTorrentPages(5);

        //3
        matchedTorrents = pairTorrentAndSeries(torrents, imdbSeries);

        System.out.println(String.format("the list was %d, found %d torrents, matches %d ", imdbSeries.size(), torrents.size(), matchedTorrents.size()));
        System.out.println(Common.toJson(matchedTorrents));

        //4


    }

    private boolean shouldScrapeImdb() {
        return false;
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
