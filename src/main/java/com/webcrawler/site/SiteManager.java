package com.webcrawler.site;

import java.util.ArrayList;

public class SiteManager {

    private ArrayList<Site> sites;


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
