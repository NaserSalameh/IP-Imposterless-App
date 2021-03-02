package com.nasersalameh.imposterphenomenoninterventionapp.models;

import java.io.Serializable;

public class AchievementType implements Serializable {

    private String achievementType;

    private int achievementScore;

    public AchievementType(String achievementType, int achievementScore){
        this.achievementType = achievementType;
        this.achievementScore = achievementScore;
    }

    public String getAchievementType() {
        return achievementType;
    }

    public int getAchievementScore() {
        return achievementScore;
    }
}
