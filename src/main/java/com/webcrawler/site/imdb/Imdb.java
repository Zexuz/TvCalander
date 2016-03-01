package com.webcrawler.site.imdb;

import com.webcrawler.site.Site;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Imdb extends Site {

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



}
