package com.webcrawler.managers;

import com.webcrawler.torrent.Torrent;

public class TorrentManager {

    public TorrentManager() {
    }

    public Torrent createTorrent(String name) {
        return new Torrent(name);
    }


}
