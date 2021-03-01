package com.nasersalameh.imposterphenomenoninterventionapp.models;

public class Achievement {

    private String achievementName;

    private AchievementType achievementType;

    //Date in Unix
    private Long achievementDate;

    public Achievement(String achievementName, AchievementType achievementType, Long achievementDate){
        this.achievementName =achievementName;
        this.achievementType = achievementType;
        this.achievementDate = achievementDate;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public AchievementType getAchievementType() {
        return achievementType;
    }

    public Long getAchievementDate() {
        return achievementDate;
    }
}
