package com.webcrawler.managers;

import com.webcrawler.series.ImdbSeries;
import com.webcrawler.site.sites.Imdb;
import com.webcrawler.site.sites.Rss;
import com.webcrawler.site.sites.TPB;
import com.webcrawler.torrent.Torrent;

import java.util.ArrayList;

public class SiteManager {

    private Imdb imdb;
    private Rss rss;
    private TPB thePirateBay;

    public SiteManager() {
        rss = new Rss("https://www.torrentday.com");
        imdb = new Imdb("http://www.imdb.com");
        thePirateBay = new TPB("http://www.thepiratebay.se");


        rss.setPath("/torrents/rss?download;1;2;3;5;7;11;13;14;21;22;24;25;26;31;32;33;44;46;u=2191010;tp=38a72acbc991348fd6e82c80ca12625d");
        thePirateBay.setPath("/recent/0");

    }

    public ArrayList<String> getImdbIds(int start, int end) {
        return imdb.getIds(start, end);
    }

    public static ImdbSeries createImdbSeries(String id) {
        return new ImdbSeries(id);
    }


    public ArrayList<Torrent> getRecentTorrentPages(int pages) {

        ArrayList<Torrent> t = new ArrayList<>();

        for (int i = 0; i < pages; i++) {
            t.addAll(thePirateBay.getTorrentsForSite());
            thePirateBay.next();
        }

        return t;
    }

    public ArrayList<Torrent> getPage() {
        return thePirateBay.getTorrentsForSite();
    }


}
