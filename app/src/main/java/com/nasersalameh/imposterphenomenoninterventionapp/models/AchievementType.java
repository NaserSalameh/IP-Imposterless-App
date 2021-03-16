package com.nasersalameh.imposterphenomenoninterventionapp.models;

import java.io.Serializable;

public class AchievementType implements Serializable {

    private String achievementType;

    private int achievementScore;
    private boolean userAddable;

    public AchievementType(String achievementType, int achievementScore, boolean userAddable){
        this.achievementType = achievementType;
        this.achievementScore = achievementScore;
        this.userAddable = userAddable;
    }

    public String getAchievementType() {
        return achievementType;
    }

    public int getAchievementScore() {
        return achievementScore;
    }

    public boolean isUserAddable() {
        return userAddable;
    }
}
