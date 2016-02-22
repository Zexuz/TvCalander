package com.webcrawler;

import com.webcrawler.managers.Managers;
import com.webcrawler.options.Options;

import java.io.IOException;

public class MainThread implements Runnable {

    private static MainThread singleton = new MainThread();
    private boolean running = false;
    private boolean pause = false;

    public Managers managers;

    private MainThread() {
        managers = new Managers();
        start();
    }

    @Override
    public void run() {

        running = false;

        //managers.tick();
        Options options;
        try {
            options = new Options();
        } catch (IOException e) {
            e.printStackTrace();
            throw new NullPointerException("Can't read options file");
        }


        for (String tpbUrl : options.getThePirateBayUrlList("tpb")) {
            System.out.println(tpbUrl);
        }

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
