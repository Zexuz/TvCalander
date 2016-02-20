package com.webcrawler.connections.restapi;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.webcrawler.series.ImdbSeries;

import java.io.IOException;
import java.util.ArrayList;

public class ImdbApi extends RestApi {

    public ImdbApi() {
        super("http://localhost:7777", "ImdbService", "v1");
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


}
