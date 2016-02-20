package com.webcrawler.connections.restapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class RestApi {

    private static final String baseUrl = "http://192.168.1.200:8000";

    private String url;
    private String version;
    private String service;


    public RestApi(String url, String service, String version) {
        this.url = url;
        this.service = service;
        this.version = version;
    }

    protected String sendGet(String method) throws IOException {
        URL obj = new URL(getWholeUrl(method));
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();


        System.out.println("test");

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();

    }

    protected String getWholeUrl(String method) {
        return getUrl() + "/" + getService() + "/" + getVersion() + "/" + method;
    }

    protected String getService() {
        return service;
    }

    protected String getVersion() {
        return version;
    }

    protected String getUrl() {
        return url;
    }
}
