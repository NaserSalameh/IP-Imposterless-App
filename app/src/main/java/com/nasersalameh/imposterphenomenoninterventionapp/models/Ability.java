package com.nasersalameh.imposterphenomenoninterventionapp.models;

import android.graphics.Color;

import com.nasersalameh.imposterphenomenoninterventionapp.R;

import java.io.Serializable;
import java.util.ArrayList;

//Imports Serializable to send across Intents
public class Ability implements Serializable {

    private String name;
    private String details;

    private ArrayList<String> improvements;

    private int experience;

    private static int EXP_MODIFIER = 10;

    public Ability(String name, String details, int experience) {
        this.name = name;
        this.details = details;
        this.experience = experience;
    }

    //Only to create temp abilities for goals writing
    public Ability(String name) {
        this.name = name;
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
        int level = ((int) Math.sqrt(experience/EXP_MODIFIER) + 1);
        if(level > 20)
            return 20;
        else
            return level;
    }

    public int getLevelExp(int level){
        return (int) Math.pow(2,level)*EXP_MODIFIER;
    }

    public int getProgress(){
        int level = getLevel();
        int nextLevelExp = getLevelExp(level+1);

        return (int) (((double) experience / (double) nextLevelExp) * 100) +1 ;
    }

    public void setImprovements(ArrayList<String> improvements) {
        this.improvements = improvements;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

}
