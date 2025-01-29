package com.plantnursery.bean;

import com.plantnursery.exception.IncorrectDataException;

import java.util.regex.Pattern;

public class OrderBean {

    private Integer idSetPlant;

    private String codeOrder;

    private String lastName;

    private String firstName;

    private String email;

    private String telephone;

    private String address;

    private String city;

    private Boolean onlinePayment;

    public void setCodeOrder(String code) {
        this.codeOrder = code;
    }

    public String getCodeOrder() {
        return this.codeOrder;
    }

    public void setLastName(String lastName) throws IncorrectDataException {
        String lastNamePattern = "^[A-Z][a-zA-Z]*( [A-Z][a-zA-Z]*)?('[A-Z][a-zA-Z]*)?$";
        boolean match = Pattern.matches(lastNamePattern, lastName);
        if (!match) {
            throw new IncorrectDataException("Lastname is not valid.");
        }else if(lastName.length() > 45) {
            throw new IncorrectDataException("Lastname is too long (max 45 characters)");
        } else {
            this.lastName = lastName;
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) throws IncorrectDataException {
        String firstNamePattern = "^[A-Z][a-zA-Z]*( [A-Z][a-zA-Z]*)?$";
        boolean match = Pattern.matches(firstNamePattern, firstName);
        if (!match) {
            throw new IncorrectDataException("Firstname is not valid.");
        }else if(firstName.length() > 45) {
                throw new IncorrectDataException("Firstname is too long (max 45 characters)");
        } else {
            this.firstName = firstName;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setEmail(String email) throws IncorrectDataException {
        String emailPattern = "^[\\w-.]++@([\\w-]++\\.)++[\\w-]{2,3}$";
        boolean match = Pattern.matches(emailPattern, email);
        if (!match) {
            throw new IncorrectDataException("Email is not valid.");
        }else if(email.length() > 45) {
            throw new IncorrectDataException("Email is too long (max 45 characters)");
        } else {
            this.email = email;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setTelephone(String telephone) throws IncorrectDataException {
        String phonePattern = "^\\+\\d{1,3}\\d{10}$";
        boolean match = Pattern.matches(phonePattern, telephone);
        if (!match) {
            throw new IncorrectDataException("Telephone is not valid.");
        } else {
            this.telephone = telephone;
        }
    }


    public void setCity(String city) throws IncorrectDataException {
        if (city == null || city.trim().isEmpty() || !city.matches("[a-zA-Z\\s\\-]{2,100}")) {
            throw new IncorrectDataException("Name city not valid.");
        } else {
            this.city = city.trim();
        }
    }

    public String getCity() {
        return city;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setAddress(String address) throws IncorrectDataException {
        if (address == null || address.isEmpty()) {
            throw new IncorrectDataException("Address is not valid.");
        } else {
            this.address = address;
        }
    }

    public String getAddress() {
        return address;
    }

    public void setOnlinePayment(Boolean onlinePayment) {
        this.onlinePayment = onlinePayment;
    }

    public Boolean getOnlinePayment() {
        return onlinePayment;
    }

    public void setIdSetPlant(Integer idSetPlant) {
        this.idSetPlant = idSetPlant;
    }

    public Integer getIdSetPlant() {
        return idSetPlant;
    }

}
