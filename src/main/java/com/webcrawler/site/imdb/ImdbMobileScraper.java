package com.webcrawler.site.imdb;


import com.webcrawler.series.Episode;
import com.webcrawler.series.Season;
import com.webcrawler.site.Site;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

public class ImdbMobileScraper extends Site {

    private ArrayList<Season> seasonArrayList;
    private String id = null;

    public ImdbMobileScraper() {
        super("http://m.imdb.com");
        seasonArrayList = new ArrayList<>();
    }

    public ArrayList<Season> scrapeSeasons() {
        ArrayList<Season> list = new ArrayList<>();

        list.add(scrapeSeason());

        while (hasNextSeason()) {
            nextSeasons();
            list.add(scrapeSeason());
        }

        return list;
    }

    public Season scrapeSeason() {
        Season season = new Season(getCurrentSeason());
        season.addEpisodes(scrapeEpisodes());
        return season;
    }

    public ArrayList<Episode> scrapeEpisodes() {
        ArrayList<Episode> episodes = new ArrayList<>();

        Elements episodesDivs = doc.select("#eplist > div");

        for (Element div : episodesDivs) {
            Element link = div.select("a.btn-full").first();
            String epNumber = link.select("span.text-large").text().trim();
            int episodeNumber = Integer.parseInt(epNumber.substring(0, epNumber.indexOf('.')));

            //take all the text
            String airDateString = link.text();
            //Loop through every valid element
            for (Element element : link.children()) {
                //remove every valid elements text
                airDateString = airDateString.replace(element.text(), "");
                //we should now have the only invalid element left
            }

            episodes.add(new Episode(formatDateString(airDateString), episodeNumber));
        }

        return episodes;
    }

    private String formatDateString(String airDate) {
        airDate = airDate.replace(".", "");
        airDate = airDate.replace("\u00a0", "");

        return airDate.trim();
    }

    private int getCurrentSeason() {
        return Integer.parseInt(doc.select("ul.list-inline.nav.nav-pills li.season_box.active").first().text());
    }

    private void nextSeasons() {
        int currentSeason = getCurrentSeason();
        setPath("/title/tt" + getId().trim() + "/episodes?season=" + ++currentSeason);
        super.load();
    }

    private boolean hasNextSeason() {
        for (Element element : doc.select("ul.list-inline.nav.nav-pills li.season_box")) {
            int currentSeason = getCurrentSeason();
            if (element.text().equals(++currentSeason + ""))
                return true;
        }
        return false;
    }

    @Override
    public void load() {
        if (getId() == null)
            throw new NullPointerException("Need to set a id before loading site");

        empty();
        setPath("/title/tt" + getId().trim() + "/episodes?ref_=m_tt_eps");
        super.load();

    }


    //===========================================
    public ArrayList<Season> getSeasonArrayList() {
        if (seasonArrayList.size() == 0)
            seasonArrayList = scrapeSeasons();
        return seasonArrayList;
    }

    public void setSeasonArrayList(ArrayList<Season> seasonArrayList) {
        this.seasonArrayList = seasonArrayList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
