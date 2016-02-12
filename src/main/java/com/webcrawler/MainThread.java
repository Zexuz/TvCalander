package com.webcrawler;

import com.webcrawler.site.SiteManager;
import com.webcrawler.task.TaskManager;

public class MainThread implements Runnable {

    private static MainThread singleton = new MainThread();
    private boolean running = false;
    private boolean pause = false;

    public SiteManager siteManager;
    public TaskManager taskManager;

    private MainThread() {
        siteManager = new SiteManager();
        taskManager = new TaskManager();
    }

    @Override
    public void run() {

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

}
