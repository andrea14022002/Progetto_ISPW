package com.plantnursery.bean;

import com.plantnursery.exception.IncorrectDataException;

import java.util.regex.Pattern;

public class SellerBean extends UserBean {

    private String firstName;

    private String lastName;

    private String email;

    private String infoPayPal;

    private String address;

    private String city;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws IncorrectDataException {
        String firstNamePattern = "^[A-Z][a-zA-Z]*( [A-Z][a-zA-Z]*)?$";
        boolean match = Pattern.matches(firstNamePattern, firstName);
        if (!match) {
            throw new IncorrectDataException("Firstname is not valid.");
        } else if (firstName.length() > 45){
            throw new IncorrectDataException("Firstname too long (max 45 characters).");
        } else {
            this.firstName = firstName;
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws IncorrectDataException {
        String lastNamePattern = "^[A-Z][a-zA-Z]*( [A-Z][a-zA-Z]*)?('[A-Z][a-zA-Z]*)?$";
        boolean match = Pattern.matches(lastNamePattern, lastName);
        if (!match) {
            throw new IncorrectDataException("Lastname is not valid.");
        } else if (lastName.length() > 45){
            throw new IncorrectDataException("Lastname too long (max 45 characters).");
        } else {
            this.lastName = lastName;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws IncorrectDataException {
        String emailPattern = "^[\\w-.]++@([\\w-]++\\.)++[\\w-]{2,3}$";
        boolean match = Pattern.matches(emailPattern, email);
        if (!match) {
            throw new IncorrectDataException("Email is not valid.");
        } else if (email.length() > 45){
            throw new IncorrectDataException("Email too long (max 45 characters).");
        } else {
            this.email = email;
        }
    }

    public String getInfoPayPal() {
        return infoPayPal;
    }

    public void setInfoPayPal(String infoPayPal) throws IncorrectDataException {
        String infoPayPalPattern = "^[\\w-.]++@([\\w-]++\\.)++[\\w-]{2,3}$";
        boolean match = Pattern.matches(infoPayPalPattern, infoPayPal);
        if (!match) {
            throw new IncorrectDataException("InfoPayPal is not valid.");
        } else if (infoPayPal.length() > 45){
            throw new IncorrectDataException("InfoPayPal too long (max 45 characters).");
        } else {
            this.infoPayPal = infoPayPal;
        }
    }

    public String getAddress() {
        return address;
    }

    public void setCity(String city) throws IncorrectDataException {
        String cityPattern = "^[A-Za-zÀ-ÿ' -]+$";
        boolean match = Pattern.matches(cityPattern, city);

        if (!match) {
            throw new IncorrectDataException("City name is not valid.");
        } else if (city.length() > 45) {
            throw new IncorrectDataException("City name too long (max 45 characters).");
        } else {
            this.city = city;
        }
    }

    public String getCity() {
        return city;
    }

    public void setAddress(String address) throws IncorrectDataException {
        String addressPattern = "^[A-Za-z0-9À-ÿ' ,.-]+$";
        boolean match = Pattern.matches(addressPattern, address);
        if (!match) {
            throw new IncorrectDataException("Address is not valid.");
        } else if (address.length() > 100) {
            throw new IncorrectDataException("Address too long (max 100 characters).");
        } else {
            this.address = address;
        }
    }
}
