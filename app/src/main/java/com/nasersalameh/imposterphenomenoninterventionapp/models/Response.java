package com.nasersalameh.imposterphenomenoninterventionapp.models;

import java.util.ArrayList;

import javax.xml.namespace.QName;

public class Response {

    private int id;
    private String name;
    private ArrayList<Integer> responses;

    public Response(int id, String name, ArrayList responses){
        this.id = id;
        this.name = name;
        this.responses = responses;
    }

    //toString
    @Override
    public String toString() {
        return "Response{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", responses=" + responses +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getResponses() {
        return responses;
    }

    public void setResponses(ArrayList<Integer> responses) {
        this.responses = responses;
    }
}
