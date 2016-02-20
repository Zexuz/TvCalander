package com.webcrawler.managers;

import com.webcrawler.misc.Common;
import com.webcrawler.series.ImdbSeries;
import com.webcrawler.torrent.Torrent;

import java.util.ArrayList;

public class Managers {

    private SiteManager siteManager;

    public Managers() {
        siteManager = new SiteManager();
    }

    public void tick() {
        // TODO: 2016-02-15

        ArrayList<String> imdbIds;
        ArrayList<Torrent> torrents, matchedTorrents;

        //1 get active shows from imdb once every day and post it to the database
        //2 search thePirateBay and other sites after those torrents
        //3 compare ids and torrents.
        //4 if it's a show we are looking for, add the torrent to the database.

        //1
        imdbIds = siteManager.getImdbIds(0, 50);
        //2
        torrents = siteManager.getRecentTorrentPages(20);

        //3
        matchedTorrents = pairTorrentAndSeries(torrents, imdbIds);

        System.out.println(String.format("the list was %d, found %d torrents, matches %d ", imdbIds.size(), torrents.size(), matchedTorrents.size()));
        System.out.println(Common.toJson(matchedTorrents));

        //4


    }

    private ArrayList<Torrent> pairTorrentAndSeries(ArrayList<Torrent> torrents, ArrayList<String> imdbIds) {
        ArrayList<Torrent> matchedTorrents = new ArrayList<>();
        for (String s : imdbIds) {

            ImdbSeries imdbSeries = siteManager.getSeries(s);
            imdbSeries.load();

            if (!imdbSeries.isPageValid()) {
                System.out.println("not valid");
                continue;
            }

            for (Torrent torrent : torrents) {

                if (!torrent.isSeries()) {
                    continue;
                }


                if (Common.isTorrentMatch(imdbSeries, torrent)) {
                    torrent.setMatch(imdbSeries.getTitle());
                    matchedTorrents.add(torrent);
                }

            }

        }
        return matchedTorrents;
    }
}
