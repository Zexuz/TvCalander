package com.webcrawler.managers;

import com.webcrawler.torrent.Torrent;

public class TorrentManager {

    public TorrentManager() {
    }

    public static Torrent createTorrent(String name) {
        return new Torrent(name);
    }


}
