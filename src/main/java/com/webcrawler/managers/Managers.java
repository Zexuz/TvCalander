package com.webcrawler.managers;

import com.webcrawler.misc.Common;
import com.webcrawler.series.ImdbSeries;
import com.webcrawler.torrent.Torrent;

import java.util.ArrayList;

public class Managers {

    private TorrentManager torrentManager;
    private SiteManager siteManager;

    public Managers() {
        torrentManager = new TorrentManager();
        siteManager = new SiteManager();
    }

    public SiteManager getSiteManager() {
        return siteManager;
    }

    public TorrentManager getTorrentManager() {
        return torrentManager;
    }

    public void tick() {
        // TODO: 2016-02-15

        //1 get active shows from imdb once every day and post it to the database
        //2 search thePirateBay and other sites after those torrents
        //3 if it's a show we are looking for, add the torrent to the database.


        // TODO: 2016-02-18
        /*
        isPageValid() in ImdbSeries is not working.
        Returning true for none active show also.
         */

        //1
        ArrayList<String> list = siteManager.getImdbIds(0, 50);


        //2
        ArrayList<Torrent> torrents = siteManager.getRecentTorrentPages(10);

        int maches = 0;

        ArrayList<Torrent> matchedTorrents = new ArrayList<>();
        for (String s : list) {

            ImdbSeries imdbSeries = siteManager.getSeries(s);
            if (!imdbSeries.isPageValid()){
                System.out.println("not valid");
                continue;
            }

            if(imdbSeries.getTitle() == null) {
                System.out.println("series is null");
                System.out.println(imdbSeries.getId());
                continue;
            }

            for (Torrent torrent : torrents) {
                if (Common.isTorrentMatch(imdbSeries, torrent)) {
                   // System.out.println(Common.toJson(torrent));
                    matchedTorrents.add(torrent);
                    maches++;
                }

            }

        }

        System.out.println(String.format("the list was %d, found %d torrents, matches %d ", list.size(), torrents.size(), maches));
        System.out.println(Common.toJson(matchedTorrents));

    }
}
