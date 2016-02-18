package com.webcrawler.torrent;

import com.webcrawler.misc.Common;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Torrent {

    private static final String regexp = ".* s(\\d+)e(\\d+) .*";

    protected String title;
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

    public Torrent(Torrent torrent) {
        this.title = torrent.title;
        this.siteLink = torrent.siteLink;
        this.torrentLink = torrent.torrentLink;
        this.description = torrent.description;
        this.pubDate = torrent.pubDate;
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

    public boolean isSeries() {
        return title.toLowerCase().matches(regexp);
    }

    public String getEpisodeNumber() {

        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(title.toLowerCase());

        if (matcher.matches()) {
            String episode = matcher.toMatchResult().group(2);

            return episode;

        }

        return null;
    }

    public String getSeasonNumber() {

        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(title.toLowerCase());

        if (matcher.matches()) {

            String season = matcher.toMatchResult().group(1);


            return season;
        }

        return null;
    }


    // TODO: 2016-02-18
    // Delete this
    private String match;

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }
}
