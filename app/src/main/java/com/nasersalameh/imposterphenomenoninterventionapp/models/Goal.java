package com.nasersalameh.imposterphenomenoninterventionapp.models;

import java.util.ArrayList;

public class Goal {

    private String name;
    private String details;

    //Type is either small, medium, or large Goal
    private String type;

    private Long unixDate;

    private ArrayList<Task> tasks;

    //Abilities This goal targets
    private ArrayList<Ability> abilities;

    //Achievements this goal will create
    private ArrayList<Achievement> achievements;

    public Goal(String name, String details, String type, Long unixDate) {
        this.name = name;
        this.details = details;
        this.type = type;
        this.unixDate = unixDate;

        this.tasks = new ArrayList<>();
        this.abilities = new ArrayList<>();
        this.achievements = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public String getType() {
        return type;
    }

    public Long getUnixDate() {
        return unixDate;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task){
        this.tasks.add(task);
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public void addAbility(Ability ability){
        this.abilities.add(ability);
    }

    public void setAbilities(ArrayList<Ability> abilities) {
        this.abilities = abilities;
    }

    public ArrayList<Achievement> getAchievements() {
        return achievements;
    }

    public void addAchievement(Achievement achievement){
        this.achievements.add(achievement);
    }

    public void setAchievements(ArrayList<Achievement> achievements) {
        this.achievements = achievements;
    }

    public int getNumberOfCompletedTasks(){
        int completeTasks = 0;
        for(Task task :tasks)
            if(task.isCompleted())
                completeTasks++;

        return completeTasks;
    }

    public int getTasksProgress(){
        int completeTasks = getNumberOfCompletedTasks();

        int progress = (int) (((double) completeTasks / (double) tasks.size()) * 100);
        System.out.println("Progress: " + progress );
        return progress;
    }

}
