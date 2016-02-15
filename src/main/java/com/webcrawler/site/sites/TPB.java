package com.webcrawler.site.sites;

import com.webcrawler.connections.WebConnection;
import com.webcrawler.site.Site;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;

public class TPB extends Site {

    private static String str = "http://www.pirateproxy.pw";


    private WebConnection webConn;

    public TPB() {
        super(str);
        webConn = new WebConnection(str);
    }

    public void next() {
        int lastDash = getPath().lastIndexOf('/') +1;
        String path = getPath().substring(0, lastDash );

        int currentIndex = Integer.parseInt(getPath().substring(lastDash ));

        setPath(path + ++currentIndex);
    }

    public void prev() {
        int lastDash = getPath().lastIndexOf('/') +1;
        String path = getPath().substring(0, lastDash );

        int currentIndex = Integer.parseInt(getPath().substring(lastDash ));

        setPath(path + --currentIndex);
    }

    /**
     * Searches for the thing String casted as a param and returns a object if match is found.
     *
     * @param name The name or id to search for.
     * @return A object, Can be anything.
     */
    @Override
    public Object find(String name) {
        return null;
    }

    // TODO: 2016-02-12
    /*
        Get latest Torrents

        Needs all the scraper methods from old version.

     */

    public WebConnection getWebConn() {
        return webConn;
    }

    public HashMap<String, String> getCookies(){
        Document response = null;
        Connection.Response res = null;
        try {
            res = Jsoup.connect("https://pirateproxy.pw/switchview.php?view=s")
                    .userAgent(WebConnection.HEADER_MOBILE)
                    .referrer(WebConnection.REFERRER_GOOGLE)
                    .data("view", "s")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (HashMap<String, String>) res.cookies();

    }
}
