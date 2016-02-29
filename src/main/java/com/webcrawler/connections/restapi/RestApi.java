package com.webcrawler.connections.restapi;

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

    protected String sendPost(String path,String urlParameters) throws Exception {
        return sendRequest(path,urlParameters,"POST");
    }

    protected String sendPut(String path,String urlParameters) throws Exception {
        return sendRequest(path,urlParameters,"PUT");
    }



    private String sendRequest(String path,String urlParameters,String method) throws Exception {

        URL obj = new URL(getWholeUrl(path));
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod(method);

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending '"+method+"' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);


        InputStream inputStream;
        if(responseCode != 200){
            inputStream =con.getErrorStream();
        }else{
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
