package com.webcrawler.connections;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;

public class WebConnection {

    public static final String HEADER_MOBILE = "Mozilla/5.0 (Linux; U; Android 4.0.3; ko-kr; LG-L160L Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";
    public static final String REFERRER_GOOGLE = "http://www.google.com";

    private String header;
    private String referrer;
    private String url;
    private HashMap<String,String> cookies;
    private int timeout;

    public WebConnection(String url) {
        this.header = HEADER_MOBILE;
        this.referrer = REFERRER_GOOGLE;

        this.url = url;
        this.timeout = 10 * 1000;
        this.cookies = new HashMap<>();
    }

    public Document getDocument(String path){
        while (true) {
            try {
                Connection.Response response = Jsoup.connect(url + path)
                        .userAgent(header)
                        .referrer(referrer)
                        .timeout(timeout)
                        .cookies(cookies)
                        .execute();

                if (response.statusCode() == 200) {
                    return response.parse();
                } else if (response.statusCode() == 501) {
                    Thread.sleep(60 * 1000);
                } else if (response.statusCode() == 404) {
                    System.out.println("null!");
                    return null;
                } else {
                    System.exit(-11);
                }

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getUrl() {
        return url;
    }

    public HashMap<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(HashMap<String, String> cookies) {
        this.cookies = cookies;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
