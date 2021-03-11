package com.nasersalameh.imposterphenomenoninterventionapp.models;

import android.graphics.Color;

import com.nasersalameh.imposterphenomenoninterventionapp.R;

import java.util.ArrayList;

public class Ability {

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
        return (int) Math.sqrt(experience/EXP_MODIFIER);
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

    public int getLevelColor(){
        int level = getLevel();
        switch (level){
            case 1: return R.color.level_1;
            case 2: return R.color.level_2;
            case 3: return R.color.level_3;
            case 4: return R.color.level_4;
            case 5: return  R.color.level_5;
            case 6: return  R.color.level_6;
            case 7: return  R.color.level_7;
            case 8: return  R.color.level_8;
            case 9: return  R.color.level_9;
            case 10: return R.color.level_10;
            case 11: return R.color.level_11;
            case 12: return R.color.level_12;
            case 13: return R.color.level_13;
            case 14: return R.color.level_14;
            case 15: return R.color.level_15;
            case 16: return R.color.level_16;
            case 17: return R.color.level_17;
            case 18: return R.color.level_18;
            case 19: return R.color.level_19;
            case 20: return R.color.level_20;
        }
        return Color.RED;
    }

}
