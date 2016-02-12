package com.webcrawler.task;

import java.util.ArrayList;

public class TaskManager {

    private ArrayList<Task> tasks;

    public TaskManager() {
        this.tasks = new ArrayList<>();
    }

    public void addTasks(ArrayList<Task> tasks) {
        this.tasks.addAll(tasks);
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
