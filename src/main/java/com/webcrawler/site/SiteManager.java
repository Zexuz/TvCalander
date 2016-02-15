package com.webcrawler.site;

import java.util.HashMap;

public class SiteManager {

    private HashMap<String, Site> sitesHashMap;

    public void addSite(String key, Site value) {
        sitesHashMap.put(key, value);
    }

    public HashMap<String, Site> getSites() {
        return this.sitesHashMap;
    }

    public Site getSite(String key){
        if(sitesHashMap.containsKey(key))
            return sitesHashMap.get(key);
        return null;
    }

    public SiteManager() {
        // sites = new ArrayList<>();
        sitesHashMap = new HashMap<>();
    }
}
