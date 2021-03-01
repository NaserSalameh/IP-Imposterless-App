package com.nasersalameh.imposterphenomenoninterventionapp.models;

public class AchievementType {

    private String achievementType;

    private static String ICON_DIRECTORY = "";
    private String achievementIconPath;

    //Constructor for Migrating from install DB;
    public AchievementType(String achievementType){
        this.achievementType = achievementType;
        this.achievementIconPath = ICON_DIRECTORY+"/"+achievementType+".png";
    }

    public String getAchievementType() {
        return achievementType;
    }

    public String getAchievementIconPath() {
        return achievementIconPath;
    }
}
