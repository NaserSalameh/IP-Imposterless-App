package com.nasersalameh.imposterphenomenoninterventionapp.models;

public class Content {

    private String id;
    private String name;
    private String content;

    public Content(String id, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }
}
