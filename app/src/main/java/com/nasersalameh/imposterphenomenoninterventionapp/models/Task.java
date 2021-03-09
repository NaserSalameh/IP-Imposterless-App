package com.nasersalameh.imposterphenomenoninterventionapp.models;

public class Task {

    private String name;
    private boolean completed;

    public Task(String name) {
        this.name = name;
        this.completed = false;
    }

    public Task(String name, boolean completed) {
        this.name = name;
        this.completed = completed;
    }


    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markAsComplete(){
        this.completed = true;
    }

    public void markAsIncomplete(){
        this.completed = false;
    }
}
