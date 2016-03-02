package com.webcrawler.torrent;

import com.webcrawler.managers.TorrentManager;
import com.webcrawler.misc.Common;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Torrent {
    private static final String regexp = ".* s(\\d+)e(\\d+) .*";

    protected String title;
    protected String originalTitle;
    private String siteLink;
    private String torrentLink;
    private Date pubDate;
    private String upLoader;
    private String upLoaderStatus;


    public Torrent(String title, Date pubDate, String siteLink, String torrentLink, String upLoader, String upLoaderStatus) {
        this.title = TorrentManager.reformatTitle(title);
        this.pubDate = pubDate;
        this.siteLink = siteLink;
        this.torrentLink = torrentLink;
        this.upLoader = upLoader;
        this.upLoaderStatus = upLoaderStatus;

        this.originalTitle = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public String getSiteLink() {
        return siteLink;
    }

    public String getTitle() {
        return title;
    }

    public String getTorrentLink() {
        return torrentLink;
    }

    public String getUpLoader() {
        return upLoader;
    }

    public String getUpLoaderStatus() {
        return upLoaderStatus;
    }


    public boolean isSeries() {
        return title.toLowerCase().matches(regexp);
    }

    public int getEpisodeNumber() {

        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(title.toLowerCase());

        if (matcher.matches()) {
            return Integer.parseInt(matcher.toMatchResult().group(2));
        }

        return 0;
    }

    public int getSeasonNumber() {

        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(title.toLowerCase());

        if (matcher.matches()) {
            return Integer.parseInt(matcher.toMatchResult().group(1));
        }

        return 0;
    }
}