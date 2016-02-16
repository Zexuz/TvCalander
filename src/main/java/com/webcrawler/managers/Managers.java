package com.webcrawler.managers;

public class Managers {

    private TorrentManager torrentManager;
    private SiteManager siteManager;

    public Managers(){
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


    }
}
