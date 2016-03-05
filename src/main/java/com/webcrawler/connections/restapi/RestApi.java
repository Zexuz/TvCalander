package com.webcrawler.connections.restapi;

import com.google.gson.JsonElement;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class RestApi {

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
        System.out.println("\nSending 'GET' request to URL : " + getWholeUrl(method));
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();

    }

    protected String sendPost(String path, String urlParameters) throws Exception {
        return sendRequest(path, urlParameters, "POST");
    }

    protected String sendPut(String path, String urlParameters) throws Exception {
        return sendRequest(path, urlParameters, "PUT");
    }

    private String sendRequest(String path, String urlParameters, String method) throws Exception {

        URL obj = new URL(getWholeUrl(path));
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod(method);

        // Send post request
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
        writer.write(urlParameters);
        writer.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending '" + method + "' request to URL : " + getWholeUrl(path));
        //System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);


        InputStream inputStream;
        if (responseCode != 200) {
            inputStream = con.getErrorStream();
        } else {
            inputStream = con.getInputStream();
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(inputStream));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        return response.toString();
    }

    protected boolean isResponseValid(JsonElement jsonElement) {
        return jsonElement.getAsJsonObject().get("success").getAsBoolean();
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
