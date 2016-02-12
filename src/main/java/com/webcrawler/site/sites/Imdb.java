package com.webcrawler.site.sites;

import com.webcrawler.site.Site;

public class Imdb extends Site{
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
        Need to be able to get new ids from this url http://www.imdb.com/search/title?title_type=tv_series

        Needs all the scraper methods from old version.

     */
}
