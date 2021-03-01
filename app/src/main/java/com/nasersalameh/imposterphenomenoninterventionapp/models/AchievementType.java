package com.nasersalameh.imposterphenomenoninterventionapp.models;

import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

public class AchievementType {

    private String achievementType;

    //Constructor for Migrating from install DB;
    public AchievementType(String achievementType){
        this.achievementType = achievementType;
    }

    public String getAchievementType() {
        return achievementType;
    }

}
