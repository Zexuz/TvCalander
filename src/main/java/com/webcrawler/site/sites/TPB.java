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
import java.util.ArrayList;
import java.util.HashMap;

public class TPB extends Site {

    public TPB(String url) {
        super(url);
        webConn.setCookies(getCookies());
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

    public WebConnection getWebConn() {
        return webConn;
    }

    public HashMap<String, String> getCookies(){
        try {
            Connection.Response res = Jsoup.connect("https://pirateproxy.pw/switchview.php?view=s")
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

        try{
            Element mainInfo = doc.getElementById("searchResult").getElementsByTag("tbody").first();
            Elements torrentElements = mainInfo.getElementsByTag("tr");

            ArrayList<Torrent> torrents = new ArrayList<>();


            for (int i = 0; i < torrentElements.size()-1; i++) {
                Element e = torrentElements.get(i);
                Elements torrentInfo = e.getElementsByTag("td");

                torrents.add(TorrentManager.createTorrent(torrentInfo.get(1).text()));
            }

            return torrents;

        }catch (Exception e){
            e.printStackTrace();
        }
      return null;

    }

}
