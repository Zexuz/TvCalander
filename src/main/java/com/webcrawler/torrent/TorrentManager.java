package com.webcrawler.torrent;

import com.webcrawler.site.sites.TPB;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

public class TorrentManager {

    public TorrentManager() {

    }

    public ArrayList<Torrent> getTorrentTPB(TPB tpb) {

        tpb.getWebConn().setCookies(tpb.getCookies());
        Document doc = tpb.getWebConn().getDocument(tpb.getPath());

        Element mainInfo = doc.getElementById("searchResult").getElementsByTag("tbody").first();
        Elements torrentElements = mainInfo.getElementsByTag("tr");

        ArrayList<Torrent> torrents = new ArrayList<>();


        for (int i = 0; i < torrentElements.size()-1; i++) {
            Element e = torrentElements.get(i);
            Elements torrentInfo = e.getElementsByTag("td");


            torrents.add(new Torrent(torrentInfo.get(1).text()));


        }


        return torrents;

    }

}
