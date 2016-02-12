package com.webcrawler.task;

import java.util.Date;

public abstract class Task implements TaskInterface {

    private Date dateCreated;

    public Task() {
        dateCreated = new Date();
    }


    public Date getDateCreated() {
        return dateCreated;
    }
}
