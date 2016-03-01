package com.webcrawler.managers;

import com.webcrawler.series.Series;
import com.webcrawler.torrent.Torrent;

import java.util.Date;

public class TorrentManager {

    public TorrentManager() {

    }

    public static boolean isTorrentMatch(Series series, Torrent torrent) {
        String imdbSeriesTitle = reformatTitle(series.getTitle());

        if (!torrent.isSeries()) {
            return false;
        }


        if (!torrent.getTitle().contains(imdbSeriesTitle)) {
            return false;
        }

        if (torrent.getTitle().indexOf(imdbSeriesTitle) > torrent.getTitle().indexOf("s" + torrent.getSeasonNumber() + "e" + torrent.getEpisodeNumber())) {
            System.out.println("the match is not done on the title");
            System.out.println("TorrentTitle:" + torrent.getTitle());
            System.out.println("Series title:" + imdbSeriesTitle);
            return false;
        }


        String version = "s" + torrent.getSeasonNumber() + "e" + torrent.getEpisodeNumber();

        String onlyTitle  = torrent.getTitle().substring(0,torrent.getTitle().indexOf(version));

        return onlyTitle.trim().equals(imdbSeriesTitle.trim());

    }

    public static String reformatTitle(String title) {

        title = title.toLowerCase();
        title = title.replace('.', ' ');
        title = title.replace('-', ' ');

        return title;
    }


    public static Torrent createTorrent(String title, Date pubDate, String siteLink, String torrentLink, String upLoader,String upLoaderStatus){
        return new Torrent(title,pubDate,siteLink,torrentLink,upLoader,upLoaderStatus);
    }

}
