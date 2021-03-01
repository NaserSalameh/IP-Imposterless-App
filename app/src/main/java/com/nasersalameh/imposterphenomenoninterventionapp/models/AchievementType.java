package com.nasersalameh.imposterphenomenoninterventionapp.models;

import java.io.Serializable;

public class AchievementType implements Serializable {

    private String achievementType;

    //Constructor for Migrating from install DB;
    public AchievementType(String achievementType){
        this.achievementType = achievementType;
    }

    public String getAchievementType() {
        return achievementType;
    }

}
