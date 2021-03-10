package com.nasersalameh.imposterphenomenoninterventionapp.models;

public class Task {

    private String name;
    private boolean completed;

    private Goal parentGoal;

    public Task(String name) {
        this.name = name;
        this.completed = false;
    }

    public Task(String name, boolean completed) {
        this.name = name;
        this.completed = completed;
    }

    public Task(String name, Goal parentGoal ,boolean completed) {
        this.name = name;
        this.parentGoal = parentGoal;
        this.completed = completed;
    }

    public Task(String name, Goal parentGoal) {
        this.name = name;
        this.parentGoal = parentGoal;
        this.completed = false;
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

    public void setName(String name) {
        this.name = name;
    }

    public Goal getParentGoal() {
        return parentGoal;
    }

    public void setParentGoal(Goal parentGoal) {
        this.parentGoal = parentGoal;
    }
}
