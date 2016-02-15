package com.webcrawler;

import com.webcrawler.misc.Common;
import com.webcrawler.site.SiteManager;
import com.webcrawler.site.sites.Rss;
import com.webcrawler.site.sites.TPB;
import com.webcrawler.task.TaskManager;
import com.webcrawler.torrent.Torrent;
import com.webcrawler.torrent.TorrentManager;

import java.util.ArrayList;

public class MainThread implements Runnable {

    private static MainThread singleton = new MainThread();
    private boolean running = false;
    private boolean pause = false;

    public SiteManager siteManager;
    public TaskManager taskManager;
    public TorrentManager torrentManager;

    private MainThread() {
        siteManager = new SiteManager();
        taskManager = new TaskManager();
        torrentManager = new TorrentManager();

        start();
    }

    @Override
    public void run() {
        // Rss rss = new Rss("https://www.torrentday.com");
        // rss.setPath("/torrents/rss?download;1;2;3;5;7;11;13;14;21;22;24;25;26;31;32;33;44;46;u=2191010;tp=38a72acbc991348fd6e82c80ca12625d");


        TPB tpb = new TPB();
        tpb.setPath("/recent/0");

        System.out.println(Common.toJson(torrentManager.getTorrentTPB(tpb)));
/*


        rss.load();
        try {
            rss.getItem();
        } catch (Exception e) {
            e.printStackTrace();
        }

 */

        running = false;


        while (running) {
        }


    }


    public synchronized void stop() {
        running = false;
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        running = true;
        Thread t = new Thread(this, "Main thread");
        t.start();
    }

    public void pause() {
        pause = true;
    }

    public void unPause() {
        pause = false;
    }

    public boolean isPause() {
        return pause;
    }

    public static MainThread getInstance() {
        return singleton;
    }

    public static void main(String args[]) {
        getInstance();
    }

}
