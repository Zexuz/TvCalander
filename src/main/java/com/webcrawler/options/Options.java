package com.webcrawler.options;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Options {

    private static final String fileName = "options.config";

    private Properties prop;

    public Options() throws IOException {
        readOptions();
    }

    public Object getOption(String options){
        return prop.get(options);
    }

    public String[] getThePirateBayUrlList(String thePirateBayTag){
        return prop.get(thePirateBayTag).toString().split(",");
    }

    public void readOptions() throws IOException {
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
