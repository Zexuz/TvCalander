package com.webcrawler.managers;

import com.webcrawler.connections.restapi.ImdbApi;
import com.webcrawler.misc.Common;
import com.webcrawler.series.ImdbSeries;
import com.webcrawler.torrent.Torrent;

import java.util.ArrayList;

public class Managers {
    // TODO: 2016-02-22
    /*
        change SiteManager to WebScraperManager?
     */
    private SiteManager siteManager;

    private ImdbApi imdbApi;

    public Managers() {
        siteManager = new SiteManager();
        imdbApi = new ImdbApi();
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

                ImdbSeries imdbSeriesTemp = SiteManager.createImdbSeries(s);

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
                    torrent.setMatch(imdbSeries.getTitle());
                    matchedTorrents.add(torrent);
                }

            }

        }
        return matchedTorrents;
    }
}
