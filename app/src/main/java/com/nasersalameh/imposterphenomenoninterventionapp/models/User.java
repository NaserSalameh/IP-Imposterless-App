package com.nasersalameh.imposterphenomenoninterventionapp.models;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private String userName;
    private String imagePath;

    //behaviour severities ordered by Index
    private ArrayList<String> severities;

    //Mapping of tab/trainings
    private HashMap<String, Boolean> unlockedTabs;
    private HashMap<String, Boolean> unlockedTrainings;


    public User(String userName, String imagePath, ArrayList<String> severities, HashMap<String, Boolean> tabs, HashMap<String, Boolean> trainings) {
        this.userName = userName;
        this.imagePath = imagePath;

        this.severities = severities;

        this.unlockedTabs = tabs;
        this.unlockedTrainings = trainings;
    }

    public String getUserName() {
        return userName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ArrayList<String> getSeverities() {
        return severities;
    }

    public HashMap<String, Boolean> getUnlockedTabs() {
        return unlockedTabs;
    }

    public HashMap<String, Boolean> getUnlockedTrainings() {
        return unlockedTrainings;
    }
}
