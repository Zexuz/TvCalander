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
