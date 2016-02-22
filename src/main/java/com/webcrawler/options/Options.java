package com.webcrawler.options;


import com.webcrawler.MainThread;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class Options {

    private SimpleOptions simpleOptions;

    public Options() throws IOException {
        simpleOptions = new SimpleOptions();

    }

    public Object getOption(String options) {
        return simpleOptions.getProp().get(options);
    }

    public String[] getThePirateBayUrlList(String thePirateBayTag) {
        return simpleOptions.getProp().get(thePirateBayTag).toString().split(",");
    }

    public URL getHostAndPortForREST() {
        URL url;
        try {
            url = new URL("http", getOption("host").toString(), getOption("port").toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        return url;
    }

}

class SimpleOptions {
    private static final String fileName = "options.config";

    private Properties prop;

    public SimpleOptions() throws IOException {
        readOptions();
    }

    public Properties getProp() {
        return prop;
    }

    private void readOptions() throws IOException {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            prop = new Properties();

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + fileName + "' not found in the classpath");
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
