package com.nasersalameh.imposterphenomenoninterventionapp.models;

public class Information {

    private String informationName;
    private String informationDetails;

    private Integer progress;

    public Information(String informationName, String informationDetails, Integer progress){
        this.informationName = informationName;
        this.informationDetails = informationDetails;

        this.progress = progress;
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
}
