package com.webcrawler.site;

import com.webcrawler.connections.WebConnection;
import org.jsoup.nodes.Document;

public abstract class Site implements SiteInterface {

    private String url;
    private String path;
    protected Document doc;
    protected WebConnection webConn;

    public Site(String url) {
        this.url = url;
        webConn = new WebConnection(url);
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

}
