package com.webcrawler.site.sites;

import com.webcrawler.site.Site;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Imdb extends Site {

    // TODO: 2016-02-16
    /*

    Only methods that is active

    isPageValid(ImdbId)
    getInfo
    getSeason
    getEpisodes

    getIds(start,end)
     */

    public Imdb(String url) {
        super(url);
    }

    public ArrayList<String> getIds(int start, int end) {
        ArrayList<String> ids = new ArrayList<>();

        int count = 50;

        if (end < count)
            count = end;

        System.out.println(count);

        for (int i = start; i < end; i += count) {

            if (end - i < count)
                count = end - i;

            setPath("/search/title?title_type=tv_series&start=" + i + "&count=" + count);
            Document doc = webConn.getDocument(getPath());


            Elements links = doc.select("tr.detailed td.title a");

            for (Element link : links) {
                if (link.children().size() != 0)
                    continue;

                if (!link.attr("href").contains("/title/tt"))
                    continue;

                String address = link.attr("href");
                ids.add(address.substring(address.length() - 8, address.length() - 1));
            }

        }


        return ids;
    }

    public boolean isPageValid(String imdbId) {
        return isActive() && hasTitle() && hasSeries();
    }

    private boolean isActive() {
        return false;
    }

    private boolean hasTitle() {
        return false;
    }

    private boolean hasSeries() {
        return false;
    }


}
