package com.webcrawler;

import com.webcrawler.managers.Managers;

public class MainThread implements Runnable {


    private final static MainThread singleton = new MainThread();
    private boolean running = false;
    private boolean pause = false;


    public Managers managers;

    private MainThread() {
        managers = new Managers();
        start();
    }

    @Override
    public void run() {

        while (running) {

            try {
                managers.tick();
                //we now sleep for 1 min
                System.out.println("------sleeping----------------------");

                Thread.sleep(1000 * 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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
    }

}
