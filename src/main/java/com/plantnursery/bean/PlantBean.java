package com.plantnursery.bean;

import com.plantnursery.exception.IncorrectDataException;

public class PlantBean {

    private String name;

    private String scientificName;

    public void setName(String name) throws IncorrectDataException {
        if(name == null || name.isEmpty())
            throw new IncorrectDataException("Name cannot be empty");
        else if(name.length() > 45)
            throw new IncorrectDataException("Name cannot be longer than 45 characters");
        else {
            this.name = name;
        }
    }

    public void setScientificName(String scientificName) throws IncorrectDataException {
        if (scientificName == null || scientificName.isEmpty()) {
            throw new IncorrectDataException("Scientific name cannot be empty.");
        }
        else if (scientificName.length() > 100) {
            throw new IncorrectDataException("Scientific name cannot be longer than 100 characters.");
        } else {
            this.scientificName = scientificName;
        }
    }

    public String getScientificName() {
        return scientificName;
    }

    public String getName() {
        return name;
    }


}
