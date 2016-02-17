package com.webcrawler.torrent;

import com.webcrawler.misc.Common;

import java.util.Date;

public class Torrent {

    private String title;
    private String siteLink;
    private String torrentLink;
    private String description;
    private Date pubDate;

    public Torrent(String title, String siteLink, String torrentLink, String description, Date pubDate) {
        this.title = title;
        this.siteLink = siteLink;
        this.torrentLink = torrentLink;
        this.description = description;
        this.pubDate = pubDate;
    }

    public Torrent(String title) {
        this.title = Common.reformatTitle(title);
    }

    public String getTitle() {
        return title;
    }

    public String getSiteLink() {
        return siteLink;
    }

    public String getTorrentLink() {
        return torrentLink;
    }

    public String getDescription() {
        return description;
    }

    public Date getPubDate() {
        return pubDate;
    }
}
