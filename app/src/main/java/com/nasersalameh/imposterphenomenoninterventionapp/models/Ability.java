package com.nasersalameh.imposterphenomenoninterventionapp.models;

import java.util.ArrayList;

public class Ability {

    private String name;
    private String details;

    private ArrayList<String> improvements;

    private int experience;

    public Ability(String name, String details, int experience) {
        this.name = name;
        this.details = details;
        this.experience = experience;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public ArrayList<String> getImprovements() {
        return improvements;
    }

    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return (int) Math.sqrt(experience);
    }

    public int getLevelExp(int level){
        return (int) Math.pow(level,2);
    }

    public int getProgress(){
        int level = getLevel();
        int nextLevelExp = getLevelExp(level+1);

        return (int) (((double) experience / (double) nextLevelExp) * 100);
    }

    public void setImprovements(ArrayList<String> improvements) {
        this.improvements = improvements;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

}
