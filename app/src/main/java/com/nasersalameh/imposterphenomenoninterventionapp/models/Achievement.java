package com.nasersalameh.imposterphenomenoninterventionapp.models;

import java.io.Serializable;

//Imports Serializable to send across Intents
public class Achievement implements Serializable {

    private String achievementName;
    private String achievementDetails;

    private AchievementType achievementType;

    //Date in Unix
    private Long achievementDate;

    public Achievement(String achievementName, String achievementDetails, AchievementType achievementType, Long achievementDate){
        this.achievementName =achievementName;
        this.achievementDetails = achievementDetails;
        this.achievementType = achievementType;
        this.achievementDate = achievementDate;
    }

    public Achievement(String achievementName, String achievementDetails, Long achievementDate) {
        this.achievementName = achievementName;
        this.achievementDetails = achievementDetails;
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

    public String getAchievementDetails() {
        return achievementDetails;
    }

    public void setAchievementType(AchievementType achievementType) {
        this.achievementType = achievementType;
    }
}
