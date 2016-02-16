package com.webcrawler.managers;


import com.webcrawler.misc.Common;
import com.webcrawler.site.sites.Imdb;
import com.webcrawler.site.sites.Rss;

public class SiteManager {

    private static String str = "http://www.imdb.com";

    private Imdb imdb;
    private Rss rss;

    public SiteManager(){
        rss = new Rss("https://www.torrentday.com");
        rss.setPath("/torrents/rss?download;1;2;3;5;7;11;13;14;21;22;24;25;26;31;32;33;44;46;u=2191010;tp=38a72acbc991348fd6e82c80ca12625d");


        imdb = new Imdb(str);

    }

    public void getImdbIds(int start,int end){
        //imdb.getIds(start,end);
        System.out.println(Common.toJson(imdb.getIds(start,end)));
    }

}
