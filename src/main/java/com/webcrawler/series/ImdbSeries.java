package com.webcrawler.series;

import com.webcrawler.site.Site;
import com.webcrawler.site.imdb.ImdbMobileScraper;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImdbSeries extends Site {

    private static final String url = "http://m.imdb.com";

    private ImdbMobileScraper imdbMobileScraper;

    private String id;
    private String title = null;
    private String year = null;
    private String imgLink = null;

    private ArrayList<Season> seasons = null;

    private boolean hasValidHeaders;

    public ImdbSeries(String id) {
        super(url);
        this.id = id;
        imdbMobileScraper = new ImdbMobileScraper();
        imdbMobileScraper.setId(id);

        setPath("/title/tt" + id);
    }

    @Override
    public void load() {
        super.load();

        hasValidHeaders = hasValidHeader();
        if (!hasValidHeaders)
            return;
        setImgLink(scrapeImg());


        if (!hasSeason())
            return;

        seasons = getSeasons();
    }

    public boolean isPageValid() {
        return hasValidHeaders;
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

    private String scrapeImg() {
        Elements images = doc.getElementsByTag("img");

        for (Element image : images) {
            if (image.attr("alt").contains(title) && image.attr("alt").contains(year) && image.attr("alt").contains("Poster"))
                return image.attr("src");
        }

        return null;
    }

    private boolean hasSeason() {
        Element section = getDoc().getElementById("titleOverview");

        for (Element a : section.getElementsByTag("a"))
            if (a.text().equalsIgnoreCase("Episode Guide") && a.attr("href").contains(id + "")) return true;

        return false;
    }

    //==========================================

    public Series getSeries() {
        Series series = new Series(getTitle());
        series.addSeasons(getSeasons());
        series.setImdbId(getId());
        series.setStartYear(getYear());
        series.setImgLink(getImgLink());
        return series;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public ArrayList<Season> getSeasons() {
        if (seasons == null) {
            imdbMobileScraper.load();
            seasons = imdbMobileScraper.getSeasonArrayList();
        }
        return seasons;
    }

}
