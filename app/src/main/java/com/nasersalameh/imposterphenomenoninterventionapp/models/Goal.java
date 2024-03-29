package com.nasersalameh.imposterphenomenoninterventionapp.models;

import java.io.Serializable;
import java.util.ArrayList;

//Imports Serializable to send across Intents
public class Goal implements Serializable {

    private String name;
    private String details;

    //Type is either small, medium, or large Goal
    private String type;

    private Long deadlineUnixDate;

    private Long completionUnixDate;

    private ArrayList<Task> tasks;

    //Abilities This goal targets
    private ArrayList<Ability> abilities;

    //Achievements this goal will create
    private ArrayList<Achievement> achievements;

    private Reflection reflection;

    public Goal(String name, String details, String type, Long deadlineUnixDate) {
        this.name = name;
        this.details = details;
        this.type = type;
        this.deadlineUnixDate = deadlineUnixDate;

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

    public Long getDeadlineUnixDate() {
        return deadlineUnixDate;
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
        return progress;
    }

    public void removeTask(Task taskToRemove) {
        //Temp task to remove concurrency issues
        Task tempTask = tasks.get(0);
        for(Task task: tasks)
            if(task == taskToRemove)
                tempTask = task;

        tasks.remove(tempTask);
    }

    public Reflection getReflection() {
        return reflection;
    }

    public void setReflection(Reflection reflection) {
        this.reflection = reflection;
    }


    public Long getCompletionUnixDate() {
        return completionUnixDate;
    }

    public void setCompletionUnixDate(Long completionUnixDate) {
        this.completionUnixDate = completionUnixDate;
    }
}
