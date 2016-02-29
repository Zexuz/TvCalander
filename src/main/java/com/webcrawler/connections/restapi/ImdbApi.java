package com.webcrawler.connections.restapi;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.webcrawler.managers.Managers;
import com.webcrawler.misc.Common;
import com.webcrawler.series.ImdbSeries;

import java.io.IOException;
import java.util.ArrayList;

public class ImdbApi extends RestApi {


    public ImdbApi(Managers managers) {
        super(managers.options.getHostAndPortForREST(), "ImdbService", "v1");
    }

    public ArrayList<ImdbSeries> getAllSeries() {
        String data;

        try {
            data = sendGet("Series");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return stringToSeries(data);
    }

    public ImdbSeries addSeries(ImdbSeries series) {
        try {
            return stringToOneSeries(sendPost("Series", imdbSeriesToJsonString(series)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ImdbSeries getOneSeries(String seriesId) {
        try {
            return stringToOneSeries(sendGet("Series/" + seriesId));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public ImdbSeries updateSeries(ImdbSeries series) {
        try {
            return stringToOneSeries(sendPut("Series", imdbSeriesToJsonString(series)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ImdbSeries stringToOneSeries(String response) {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(response);

        ImdbSeries series = Managers.createSeries(jsonElement.getAsJsonObject().get("id").getAsString());
        series.setTitle(jsonElement.getAsJsonObject().get("title").getAsString());
        series.setYear(jsonElement.getAsJsonObject().get("year").getAsString());
        series.setImgLink(jsonElement.getAsJsonObject().get("imgLink").getAsString());

        return series;
    }

    private ArrayList<ImdbSeries> stringToSeries(String response) {
        ArrayList<ImdbSeries> series = new ArrayList<>();

        JsonParser parser = new JsonParser();
        JsonElement jsonResponse = parser.parse(response);
        JsonArray jsonArray = jsonResponse.getAsJsonArray();

        for (JsonElement jsonElement : jsonArray) {
            ImdbSeries s = Managers.createSeries(jsonElement.getAsJsonObject().get("id").getAsString());
            s.setTitle(jsonElement.getAsJsonObject().get("title").getAsString());
            series.add(s);
        }

        return series;
    }

    private String imdbSeriesToJsonString(ImdbSeries series) {
        String res;
        res = "{" +
                "\"title\":\"" + series.getTitle() + "\"," +
                "\"id\":\"" + series.getId() + "\"," +
                "\"imgLink\":\"" + series.getImgLink() + "\"," +
                "\"year\":\"" + series.getYear() + "\"" +
                //",\"seasons\":\"" + Common.toJson(series.getSeasons()) + "\"" +
                "}";

        return res;
    }


}