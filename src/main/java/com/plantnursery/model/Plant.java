package com.plantnursery.model;

public class Plant {
    private String name;

    private String scientificName;

    public Plant( String name, String scientificName) {
        this.name = name;
        this.scientificName = scientificName;
    }

    public String getName() {
        return name;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

}
