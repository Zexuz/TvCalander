package com.webcrawler.site;

public interface SiteInterface {
    /**
     * Connects and returns the page content.
     */
    void load();

    /**
     * Deletes the page content. Dunno why this is her but it is.
     */
    void empty();
}
