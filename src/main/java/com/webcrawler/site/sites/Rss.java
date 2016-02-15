package com.webcrawler.site.sites;

import com.webcrawler.site.Site;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class Rss extends Site {

    public Rss(String url) {
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

    public JSONArray getItems() throws Exception {
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
