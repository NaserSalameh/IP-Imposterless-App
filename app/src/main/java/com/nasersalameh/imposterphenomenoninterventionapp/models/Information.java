package com.nasersalameh.imposterphenomenoninterventionapp.models;

import java.io.Serializable;

//Imports Serializable to send across Intents
public class Information implements Serializable {

    private String informationName;
    private String informationDetails;

    //Corpus is the full content of the informational card
    private String informationCorpus;

    private Integer progress;
    private boolean unlocked;

    public Information(String informationName, String informationDetails, String informationCorpus, int progress, boolean unlocked){
        this.informationName = informationName;
        this.informationDetails = informationDetails;
        this.informationCorpus = informationCorpus;
        this.progress = progress;

        //set to locked
        this.unlocked = unlocked;
    }

    //Constructor for install database overlooking progress
    public Information(String informationName, String informationDetails, String informationCorpus, boolean unlocked){
        this.informationName = informationName;
        this.informationDetails = informationDetails;
        this.informationCorpus = informationCorpus;
        this.progress = 0;

        this.unlocked = unlocked;
    }


    public String getInformationName() {
        return informationName;
    }

    public String getInformationDetails() {
        return informationDetails;
    }

    public Integer getProgress() {
        return progress;
    }

    public String getInformationCorpus() {
        return informationCorpus;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean locked) {
        this.unlocked = locked;
    }
}
