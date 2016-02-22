package com.webcrawler.connections.restapi;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.webcrawler.MainThread;
import com.webcrawler.series.ImdbSeries;

import java.io.IOException;
import java.util.ArrayList;

public class ImdbApi extends RestApi {

    public ImdbApi() {
        super("http://" + MainThread.getInstance().getOptionsProp("host") + ":" + MainThread.getInstance().getOptionsProp("host") + "", "ImdbService", "v1");
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

    public boolean addSeries(ImdbSeries series) {

        try {
            sendPost("Series", imdbSeriesToUrlString(series));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    private ArrayList<ImdbSeries> stringToSeries(String response) {
        ArrayList<ImdbSeries> series = new ArrayList<>();

        JsonParser parser = new JsonParser();
        JsonElement jsonResponse = parser.parse(response);
        JsonArray jsonArray = jsonResponse.getAsJsonArray();

        for (JsonElement jsonElement : jsonArray) {
            ImdbSeries s = new ImdbSeries(jsonElement.getAsJsonObject().get("id").getAsString());
            s.setTitle(jsonElement.getAsJsonObject().get("title").getAsString());
            series.add(s);
        }

        return series;
    }

    private String imdbSeriesToUrlString(ImdbSeries series) {
        String response;
        response = "title=" + series.getTitle() + "&id=" + series.getId();
        return response;
    }


}
