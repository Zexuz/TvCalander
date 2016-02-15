package com.webcrawler.site;

import com.webcrawler.connections.WebConnection;
import org.jsoup.nodes.Document;

public abstract class Site implements SiteInterface {

    private String url;
    private String path;
    private Document doc;
    private WebConnection webConn;

    public Site(String url) {
        webConn = new WebConnection(url);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void addPath(String path) {
        this.path = getPath() + path;
    }

    public void load() {
        doc = webConn.getDocument(path);
    }

    public void empty() {
        doc = null;
    }

    public Document getDoc() {
        return this.doc;
    }

    /**
     * Searches for the thing String casted as a param and returns a object if match is found.
     *
     * @param name The name or id to search for.
     * @return A object, Can be anything.
     */
    public abstract Object find(String name);
}
