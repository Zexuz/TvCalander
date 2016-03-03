package com.webcrawler.managers;

import com.webcrawler.connections.restapi.ImdbApi;
import com.webcrawler.misc.Common;
import com.webcrawler.options.Options;
import com.webcrawler.series.ImdbSeries;
import com.webcrawler.series.Series;
import com.webcrawler.site.imdb.Imdb;
import com.webcrawler.torrent.Torrent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;

public class Managers {
    // TODO: 2016-02-22
    /*
        change SiteManager to WebScraperManager?
     */

    private SiteManager siteManager;
    private ImdbApi imdbApi;
    public Options options;

    public Managers() {
        try {
            options = new Options();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Can't read options.config file, exiting");
            System.exit(9456);
        }

        siteManager = new SiteManager(this);
        imdbApi = new ImdbApi(this);

    }


    public void tick() throws InterruptedException {
        //1 get active shows from imdb once every day and post it to the database
        //2 search thePirateBay and other sites after those torrents
        //3 compare ids and torrents.
        //4 if it's a show we are looking for, add the torrent to the database.

        ArrayList<Series> seriesShortList = imdbApi.getAllSeries();


        ArrayList<Series> seriesFullList = new ArrayList<>();
        boolean scrapeImdb = siteManager.shouldScrapeImdb();

        if (scrapeImdb) {
            for (String imdbId : siteManager.getImdbIds(0, 50)) {
                //s is
                ImdbSeries imdbSeries = Imdb.createImdbSeries(imdbId);
                imdbSeries.load();

                Thread.sleep(3000);
                if (!imdbSeries.isPageValid()) {
                    System.out.println(imdbSeries.getId() + " is not valid");
                    continue;
                }

                Series series = imdbSeries.getSeries();
                seriesFullList.add(series); //if we already is getting the full info we should save it for later


                //check if the ArrayList imdbSeriesArrayList contains the current series
                if (!doesContain(seriesShortList, series)) {
                    //if it does not contain we just update it to the api and continues with the loop
                    imdbApi.addSeries(series);
                    seriesShortList.add(series);
                    continue;
                }


                //check if we should update a series eg, imdb has new data
                for (Series apiSeriesShortInfo : seriesShortList) {
                    if (apiSeriesShortInfo.getImdbId().equals(series.getImdbId())) {

                        //check if our old seasons data is correct.
                        Series apiSeriesFullInfo = imdbApi.getOneSeries(apiSeriesShortInfo.getImdbId());

                        if (!apiSeriesFullInfo.isSame(series)) {
                            imdbApi.updateSeries(series);
                            break;
                        }
                    }
                }
            }
        }

        //2
        ArrayList<Torrent> torrents = siteManager.getRecentTorrentPages(33);
        //3
        // we need to remove every series in seriesFullList that does not have a torrent match
        if (scrapeImdb) {
            seriesFullList = getMatchedSeries(seriesFullList, torrents);
        } else {
            //if we don't have the full info on the series, we get it
            for (Series series : getMatchedSeries(seriesShortList, torrents)) {
                seriesFullList.add(imdbApi.getOneSeries(series.getImdbId()));
            }

        }

        for (Series series : seriesFullList) {

            Series oldDataSeries = imdbApi.getOneSeries(series.getImdbId());
            for (Torrent torrent : torrents) {
                if (TorrentManager.isTorrentMatch(series, torrent)) {
                    ArrayList<Torrent> episodeTorrents =
                            series.
                                    getSeason(Integer.parseInt(torrent.getSeasonNumber())).
                                    getEpisode(Integer.parseInt(torrent.getEpisodeNumber())).getTorrents();

                    boolean add = true;
                    for (Torrent episodeTorrent : episodeTorrents) {
                        if (Objects.equals(episodeTorrent.getSiteLink(), torrent.getSiteLink())) {
                            add = false;
                        }
                    }
                    if (add)
                        series.
                                getSeason(Integer.parseInt(torrent.getSeasonNumber())).
                                getEpisode(Integer.parseInt(torrent.getEpisodeNumber())).addTorrent(torrent);

                }

            }
            if (!series.isSame(oldDataSeries)) {
                imdbApi.updateSeries(series);
                System.out.printf("Updating series : %s", series.getTitle());
            }
        }


    }

    private ArrayList<Series> getMatchedSeries(ArrayList<Series> seriesArrayList, ArrayList<Torrent> torrents) {
        ArrayList<Series> matchedSeries = new ArrayList<>();
        for (Series series : seriesArrayList) {
            for (Torrent torrent : torrents) {
                if (TorrentManager.isTorrentMatch(series, torrent)) {
                    if (!doesContain(matchedSeries, series)) {
                        matchedSeries.add(series);
                    }
                }

            }

        }
        return matchedSeries;
    }


    private boolean doesContain(ArrayList<Series> seriesArrayList, Series series) {
        boolean contains = false;
        for (Series tempSeries : seriesArrayList) {
            if (tempSeries.getImdbId().equals(series.getImdbId())) contains = true;
        }

        return contains;
    }
}

/*


        ArrayList<Series> seriesWithTorrents = pairTorrentAndSeries(torrents, seriesShortList);

        System.out.println(String.format("the list was %d, found %d torrents, matches %d ", seriesShortList.size(), torrents.size(), seriesWithTorrents.size()));

        System.out.println(Common.toJson(seriesWithTorrents));
        for (Series newSeries : seriesWithTorrents) {
            if (!newSeries.isSame(imdbApi.getOneSeries(newSeries.getImdbId()))) {
                imdbApi.updateSeries(newSeries);
            }
        }

        //4


    }

    private ArrayList<Series> pairTorrentAndSeries(ArrayList<Torrent> torrents, ArrayList<Series> seriesArrayList) {
        ArrayList<Series> seriesWithTorrents = new ArrayList<>();
        for (Series series : seriesArrayList) {
            boolean add = false;
            for (Torrent torrent : torrents) {

                if (TorrentManager.isTorrentMatch(series, torrent)) {
                    System.out.println(Common.toJson(torrent));
                    series = imdbApi.getOneSeries(series.getImdbId());
                    series
                            .getSeason(Integer.parseInt(torrent.getSeasonNumber()))
                            .getEpisode(Integer.parseInt(torrent.getEpisodeNumber())).addTorrent(torrent);
                    add = true;
                }

            }
            if (add) {
                //System.exit(400120);
                //seriesWithTorrents.add(series);
            }


        }
        System.exit(400120);
        return seriesWithTorrents;
    }

 */
