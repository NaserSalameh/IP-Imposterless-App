package com.nasersalameh.imposterphenomenoninterventionapp.models;

public class Log {

    private String tab;
    private String action;

    public Log(String tab, String action) {
        this.tab = tab;
        this.action = action;
    }

    public String getTab() {
        return tab;
    }

    public String getAction() {
        return action;
    }
}
