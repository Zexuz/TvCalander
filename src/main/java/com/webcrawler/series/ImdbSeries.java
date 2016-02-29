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
    private String year;
    private String imgLink = null;

    public ImdbSeries(String id) {
        super(url);
        this.id = id;


        setPath("/title/tt" + id);
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

            setYear(matcher.group(0).substring(1, 5));
            setTitle(element.text().substring(0, element.text().length() - matcher.group(0).length()).trim());
            return true;
        }
        return false;
    }

    public String getImgLink() {
        if (imgLink != null)
            return imgLink;

        Elements images = doc.getElementsByTag("img");

        for (Element image : images) {
            if (image.attr("alt").contains(title) && image.attr("alt").contains(year) && image.attr("alt").contains("Poster"))
                return image.attr("src");
        }

        System.out.println("No image poster found");
        return null;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
