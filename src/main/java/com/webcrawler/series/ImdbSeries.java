package com.webcrawler.series;

import com.webcrawler.site.Site;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImdbSeries extends Site {

    private static final String url = "http://m.imdb.com";

    // TODO: 2016-02-16
    /*
    fix this disgusting class
     */

    private String id;
    private String title = null;

    public ImdbSeries(String id){
        super(url);
        this.id = id;
        load();
    }


    public boolean isPageValid() {
        return hasValidHeader();
    }

    private boolean hasValidHeader() {
        Elements titleElement = getDoc().getElementsByTag("h1");

        if (titleElement.size() == 0)
            return false;

        for (Element element : titleElement) {

            if (element.children().size() == 0)
                continue;

            Pattern pattern = Pattern.compile("[(](\\d){4}\u2013 [)]");
            Matcher matcher = pattern.matcher(element.children().first().text());
            if (!matcher.matches())
                continue;

            setTitle(element.text().substring(0, element.text().length() - matcher.group(0).length()).trim());
            break;
        }
        return true;
    }

    private boolean hasSeason() {
        Element section = getDoc().getElementById("titleOverview");

        for (Element a : section.getElementsByTag("a"))
            if (a.text().equalsIgnoreCase("Episode Guide") && a.attr("href").contains(id + "")) return true;

        return false;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
