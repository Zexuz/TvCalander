package com.webcrawler.options;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

    public String getHostAndPortForREST() {
        return "http://" + getOption("host").toString() + ":" + getOption("port").toString();
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
