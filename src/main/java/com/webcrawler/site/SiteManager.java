package com.webcrawler.site;

import java.util.ArrayList;
import java.util.HashMap;

public class SiteManager {

    private ArrayList<Site> sites;

    private HashMap<String,Site> sitesHashMap;


    // TODO: 2016-02-15 change sites ArrayList to HashMap

    public SiteManager() {
        sites = new ArrayList<>();
    }

    public void addSite(Site site){
        this.sites.add(site);
    }

    public void addSites(ArrayList<Site> sites){
        this.sites.addAll(sites);
    }

    public ArrayList<Site> getSites() {
        return this.sites;
    }

    public void setSites(ArrayList<Site> sites) {
        this.sites = sites;
    }
}
