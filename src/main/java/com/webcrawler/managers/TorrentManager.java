package com.webcrawler.managers;

import com.webcrawler.torrent.Torrent;

import java.util.Date;

public class TorrentManager {

    public TorrentManager() {

    }

    public static Torrent createTorrent(String title, Date pubDate, String siteLink, String torrentLink, String upLoader,String upLoaderStatus){
        return new Torrent(title,pubDate,siteLink,torrentLink,upLoader,upLoaderStatus);
    }

}
