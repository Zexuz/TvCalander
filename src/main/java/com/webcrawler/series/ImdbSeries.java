package com.webcrawler.series;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImdbSeries {


    // TODO: 2016-02-16
    /*
    fix this disgusting class
     */

    private String id;
    private String title = null;
    private Element page = null;

    public ImdbSeries(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public ImdbSeries(String id) {
        this.id = id;
    }

    public boolean isPageValid(String imdbId) {
        return isActive() && hasValidHeader() && hasSeason();
    }

    private boolean isActive() {
        return false;
    }

    private boolean hasValidHeader() {
        Elements titleElement = page.getElementsByTag("h1");

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
        Element section = page.getElementById("titleOverview");

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

    public Element getPage() {
        return page;
    }

    public void setPage(Element page) {
        this.page = page;
    }
}
