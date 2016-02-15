package com.webcrawler.site.sites;

import com.webcrawler.site.Site;

public class TBP extends Site {
    public TBP(String url) {
        super(url);
    }

    /**
     * Searches for the thing String casted as a param and returns a object if match is found.
     *
     * @param name The name or id to search for.
     * @return A object, Can be anything.
     */
    @Override
    public Object find(String name) {
        return null;
    }

    // TODO: 2016-02-12
    /*
        Get latest Torrents

        Needs all the scraper methods from old version.

     */

}
