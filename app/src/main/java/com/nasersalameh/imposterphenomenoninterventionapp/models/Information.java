package com.nasersalameh.imposterphenomenoninterventionapp.models;

public class Information {

    private String informationName;
    private String informationDetails;

    //Corpus is the full content of the informational card
    private String informationCorpus;

    private Integer progress;

    public Information(String informationName, String informationDetails, String informationCorpus){
        this.informationName = informationName;
        this.informationDetails = informationDetails;
        this.informationCorpus = informationCorpus;
        this.progress = 0;
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
}
