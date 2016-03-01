package com.webcrawler.connections.restapi;

import com.google.gson.*;
import com.webcrawler.managers.Managers;
import com.webcrawler.misc.Common;
import com.webcrawler.series.Episode;
import com.webcrawler.series.Season;
import com.webcrawler.series.Series;

import java.io.IOException;
import java.util.ArrayList;

public class ImdbApi extends RestApi {


    public ImdbApi(Managers managers) {
        super(managers.options.getHostAndPortForREST(), "ImdbService", "v1");
    }

    public ArrayList<Series> getAllSeries() {
        String data;

        try {
            data = sendGet("Series");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return stringToSeries(data);
    }

    public Series addSeries(Series series) {
        try {
            return stringToOneSeries(sendPost("Series", imdbSeriesToJsonString(series)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Series getOneSeries(String seriesId) {
        try {
            return stringToOneSeries(sendGet("Series/" + seriesId));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Series updateSeries(Series series) {
        try {
            return stringToOneSeries(sendPut("Series/" + series.getImdbId(), imdbSeriesToJsonString(series)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Series stringToOneSeries(String response) {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(response);

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
                season.addEpisode(Episode.createEpisode(ep.get("airDate").getAsString(),ep.get("number").getAsInt()));
            }
            series.addSeason(season);

        }


        return series;
    }

    private ArrayList<Series> stringToSeries(String response) {
        ArrayList<Series> series = new ArrayList<>();

        JsonParser parser = new JsonParser();
        JsonElement jsonResponse = parser.parse(response);
        JsonArray jsonArray = jsonResponse.getAsJsonArray();

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


}