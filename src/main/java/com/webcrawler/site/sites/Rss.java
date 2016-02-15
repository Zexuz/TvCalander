package com.webcrawler.site.sites;

import com.webcrawler.site.Site;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Rss extends Site {

    private HashMap<String, String> hashMap = null;

    public Rss(String url) {
        super(url);
    }

    //https://www.torrentday.com/torrents/rss?download;1;2;3;5;7;11;13;14;21;22;24;25;26;31;32;33;44;46;u=2191010;tp=38a72acbc991348fd6e82c80ca12625d


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

    Needs to convert a torrent rss feed to a more "general" json object.

     */

    public JSONArray getItem() throws Exception {
        return getRss().getJSONObject("rss").getJSONObject("channel").getJSONArray("item");
    }

    private JSONObject getRss() throws Exception {
        try {
            return XML.toJSONObject(getDoc().toString());
        } catch (JSONException je) {
            System.out.println(je.toString());

        }

        throw new Exception();
    }


}
