package com.webcrawler.site.sites;

import com.webcrawler.connections.WebConnection;
import com.webcrawler.site.Site;
import com.webcrawler.torrent.Torrent;
import com.webcrawler.managers.TorrentManager;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class TPB extends Site {

    public TPB(String url) {
        super(url);
        webConn.setCookies(getCookies());
    }

    public void next() {
        int lastDash = getPath().lastIndexOf('/') + 1;
        String path = getPath().substring(0, lastDash);

        int currentIndex = Integer.parseInt(getPath().substring(lastDash));

        setPath(path + ++currentIndex);
    }

    public void prev() {
        int lastDash = getPath().lastIndexOf('/') + 1;
        String path = getPath().substring(0, lastDash);

        int currentIndex = Integer.parseInt(getPath().substring(lastDash));

        setPath(path + --currentIndex);
    }

    public WebConnection getWebConn() {
        return webConn;
    }

    public HashMap<String, String> getCookies() {
        try {
            Connection.Response res = Jsoup.connect("https://ahoy.re/switchview.php?view=s")
                    .userAgent(WebConnection.HEADER_MOBILE)
                    .referrer(WebConnection.REFERRER_GOOGLE)
                    .data("view", "s")
                    .execute();

            return (HashMap<String, String>) res.cookies();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public ArrayList<Torrent> getTorrentsForSite() {

        System.out.println(getPath());
        Document doc = getWebConn().getDocument(getPath());

        try {
            Element mainInfo = doc.getElementById("searchResult").getElementsByTag("tbody").first();
            Elements torrentElements = mainInfo.getElementsByTag("tr");

            ArrayList<Torrent> torrents = new ArrayList<>();

            for (int i = 0; i < torrentElements.size() - 1; i++) {
                torrents.add(getTorrentFromElement(torrentElements.get(i)));
            }

            return torrents;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Torrent getTorrentFromElement(Element element) {
        Elements torrentInfo = element.getElementsByTag("td");

        String title = torrentInfo.get(1).text();
        String siteLink = torrentInfo.get(1).children().attr("href");
        String torrentLink = "";
        String upLoader = torrentInfo.get(7).text();
        String upLoaderStatus = "Regular";
        String pubDateString = torrentInfo.get(2).text();

        Element linkAndUser = torrentInfo.get(3);

        ThepiratebayDecoder thepiratebayDecoder = new ThepiratebayDecoder(pubDateString);

        Date pubDate = null;

        try {
            pubDate = thepiratebayDecoder.getDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (Element e : linkAndUser.getElementsByTag("a")) {
            if (e.attr("title").equals("Download this torrent using magnet")) {
                torrentLink = e.attr("href");
                continue;
            }

            if (e.attr("href").contains("/user/")) {
                upLoaderStatus = e.children().first().attr("title");
            }
        }

        return TorrentManager.createTorrent(title, pubDate, siteLink, torrentLink, upLoader, upLoaderStatus);
    }

}

class ThepiratebayDecoder {

    private String dateString;
    private Calendar calendar;

    public ThepiratebayDecoder(String dateString) {
        this.dateString = dateString.trim();
        calendar = new GregorianCalendar();

        if (!controlInput()) {
            getDay();
            getHour();
            getMin();
        }
    }

    private boolean controlInput() {
        this.dateString = this.dateString.replace('\u00a0' , ' ').trim();

        if (this.dateString.contains("mins") || this.dateString.contains("min")) {
            this.dateString = this.dateString.replace("mins", "").trim();
            this.dateString = this.dateString.replace("min", "").trim();
            this.dateString = this.dateString.replace("ago", "").trim();

            long time = calendar.getTime().getTime() - (Integer.parseInt(this.dateString) * 60);

            calendar.setTime(new Date(time));

            return true;
        }
        return false;
    }

    public Date getDate() throws ParseException {
        return calendar.getTime();
    }

    private void getDay() {
        if (dateString.contains("Y-day")) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
            return;
        }

        if (dateString.contains("Today")) {
            return;
        }

        String day = dateString.substring(dateString.indexOf('-') + 1, dateString.indexOf(" "));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
    }

    private int getMin() {
        return Integer.parseInt(dateString.substring(dateString.indexOf(':') + 1));
    }

    private int getHour() {
        return Integer.parseInt(dateString.substring(dateString.indexOf(" ") + 1, dateString.indexOf(':')));
    }


}
