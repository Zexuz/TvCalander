package com.webcrawler.connections;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class WebConnection {

    public static final String HEADER_MOBILE = "Mozilla/5.0 (Linux; U; Android 4.0.3; ko-kr; LG-L160L Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";
    public static final String REFERRER_GOOGLE = "http://www.google.com";


    public static Document getDocument(String url, String header, String referrer) {
        while (true) {
            try {
                Connection.Response response = Jsoup.connect(url)
                        .userAgent(header)
                        .referrer(referrer)
                        .timeout(1000 * 10)
                        .execute();

                if (response.statusCode() == 200) {
                    return response.parse();
                } else if (response.statusCode() == 501) {
                    Thread.sleep(60 * 1000);
                } else if (response.statusCode() == 404) {
                    System.out.println("null!");
                    return null;
                }

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static Document getDocument(String url) {
        return getDocument(url, HEADER_MOBILE, REFERRER_GOOGLE);
    }


}
