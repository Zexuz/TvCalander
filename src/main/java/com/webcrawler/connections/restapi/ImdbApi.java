package com.webcrawler.connections.restapi;

import com.google.gson.*;
import com.webcrawler.managers.Managers;
import com.webcrawler.managers.TorrentManager;
import com.webcrawler.misc.Common;
import com.webcrawler.series.Episode;
import com.webcrawler.series.Season;
import com.webcrawler.series.Series;
import com.webcrawler.torrent.Torrent;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class ImdbApi extends RestApi {


    public ImdbApi(Managers managers) {
        super(managers.options.getHostAndPortForREST(), "ImdbService", "v1");
    }

    public ArrayList<Series> getAllSeries() throws IOException {
        return stringToSeries(sendGet("Series"));
    }

    public Series addSeries(Series series) throws Exception {
        return stringToOneSeries(sendPost("Series", imdbSeriesToJsonString(series)));
    }

    public Series getOneSeries(String seriesId) throws IOException {
        return stringToOneSeries(sendGet("Series/" + seriesId));
    }

    public Series updateSeries(Series series) {
        try {
            return stringToOneSeries(sendPut("Series/" + series.getImdbId(), imdbSeriesToJsonString(series)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateLastAndNextImdbScrape() throws Exception {
        long currentTime = new Date().getTime();
        String data = "{ \"lastScraped\": \"" + currentTime + "\", \"nextScrape\": \"" + (currentTime + (60 * 60 * 24 * 3 * 1000)) + "\"}";
        sendPost("Info", data);
    }

    private Series stringToOneSeries(String response) {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(response);

        if (!isResponseValid(jsonElement)) throw new IllegalStateException("API success response is false");

        jsonElement = jsonElement.getAsJsonObject().get("data");

        Series series = Series.createSeries();
        series.setImdbId(jsonElement.getAsJsonObject().get("id").getAsString());
        series.setTitle(jsonElement.getAsJsonObject().get("title").getAsString());
        series.setStartYear(jsonElement.getAsJsonObject().get("year").getAsString());
        series.setImgLink(jsonElement.getAsJsonObject().get("imgLink").getAsString());

        //series.setSeasons(new Gson().fromJson(jsonElement.getAsJsonObject().get("seasons"),ArrayList.class));

        for (JsonElement jsonSeason : jsonElement.getAsJsonObject().get("seasons").getAsJsonArray()) {
            Season season = Season.createSeason(jsonSeason.getAsJsonObject().get("season").getAsInt());

            for (JsonElement episodes : jsonSeason.getAsJsonObject().get("episodes").getAsJsonArray()) {
                JsonObject ep = episodes.getAsJsonObject();

                Episode episode = Episode.createEpisode(ep.get("airDate").getAsString(), ep.get("number").getAsInt());

                for (JsonElement torrents : ep.get("torrents").getAsJsonArray()) {
                    JsonObject tr = torrents.getAsJsonObject();

                    String title = tr.get("title").getAsString();
                    String date = tr.get("pubDate").getAsString();
                    String siteLink = tr.get("siteLink").getAsString();
                    String torrentLink = tr.get("torrentLink").getAsString();
                    String upLoader = tr.get("upLoader").getAsString();
                    String upLoaderStatus = tr.get("upLoaderStatus").getAsString();

                    Torrent torrent = TorrentManager.createTorrent(title, date, siteLink, torrentLink, upLoader, upLoaderStatus);
                    episode.addTorrent(torrent);
                }

                season.addEpisode(episode);


            }
            series.addSeason(season);

        }


        return series;
    }

    private ArrayList<Series> stringToSeries(String response) {
        ArrayList<Series> series = new ArrayList<>();

        JsonParser parser = new JsonParser();
        JsonElement jsonResponse = parser.parse(response);

        if (!isResponseValid(jsonResponse)) throw new IllegalStateException("API success response is false");

        JsonArray jsonArray = jsonResponse.getAsJsonObject().get("data").getAsJsonArray();

        for (JsonElement jsonElement : jsonArray) {
            Series s = Series.createSeries();
            s.setImdbId(jsonElement.getAsJsonObject().get("id").getAsString());
            s.setTitle(jsonElement.getAsJsonObject().get("title").getAsString());
            series.add(s);
        }

        return series;
    }

    private String imdbSeriesToJsonString(Series series) {
        String res;
        res = "{" +
                "\"title\":\"" + series.getTitle() + "\"," +
                "\"id\":\"" + series.getImdbId() + "\"," +
                "\"imgLink\":\"" + series.getImgLink() + "\"," +
                "\"year\":\"" + series.getStartYear() + "\"" +
                ",\"seasons\":" + Common.toJson(series.getSeasons()) +
                "}";

        return res;
    }


    public boolean shouldScrapeImdb() throws IOException, ParseException {
        String response = sendGet("Info");

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(response);

        if (!isResponseValid(jsonElement)) throw new IllegalStateException("API success response is false");

        Date nextScrape = new Date(jsonElement.getAsJsonObject().get("data").getAsJsonObject().get("nextScrape").getAsLong());
        Date currentTime = new Date();

        return currentTime.getTime() >= nextScrape.getTime();
    }

}