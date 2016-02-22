package com.webcrawler.connections.restapi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

    protected void sendPost(String method,String urlParameters) throws Exception {

        URL obj = new URL(getWholeUrl(method));
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

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
