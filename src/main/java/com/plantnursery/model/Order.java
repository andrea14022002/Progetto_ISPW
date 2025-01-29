package com.plantnursery.model;

import java.security.SecureRandom;
import java.util.Random;

public class Order {

    private Integer idOrder = null;

    private String codeOrder= null;

    private final String lastName;

    private final String firstName;

    private String email;

    private String telephone;

    private String address;

    private String city;

    private Boolean onlinePayment;

    public Order(String lastName, String firstName, String address, String email,
                 String telephone, String city, Boolean onlinePayment){
        this.lastName = lastName;
        this.firstName = firstName;
        this.city = city;
        this.email = email;
        this.telephone = telephone;
        this.address = address;
        this.onlinePayment = onlinePayment;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setIdAndCodeOrder(Integer idOrder) {
        this.idOrder = idOrder;
        this.codeOrder = generateCodeOrder(idOrder);
    }

    public void setIdAndCodeOrder(String codeOrder) {
        this.codeOrder = codeOrder;
        this.idOrder = reverseCodeOrder(codeOrder);
    }

    public Integer getIdOrder() {
        return this.idOrder;
    }

    public String getFirstname() {
        return this.firstName;
    }

    public String getLastname() {
        return this.lastName;
    }

    public String getCity() {
        return this.city;
    }

    public String getAddress() {
        return this.address;
    }

    public String getEmail() {
        return this.email;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Boolean getOnlinePayment() {
        return this.onlinePayment;
    }

    public String getCodeOrder() {
        return this.codeOrder;
    }

    /**
     * Generates a code order string of length 10 using the given id booking.
     *
     * @param  idOrder the id order
     * @return            the generated code order
     */
    private String generateCodeOrder(Integer idOrder) {
        String numStr = String.format("%04d", idOrder);

        char[] digits = new char[4];
        digits[0] = numStr.charAt(0);
        digits[1] = numStr.charAt(1);
        digits[2] = numStr.charAt(2);
        digits[3] = numStr.charAt(3);

        String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvwxyz"
                + "0123456789";

        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characterSet.length());
            sb.append(characterSet.charAt(index));
        }

        String randomString = sb.toString();

        char[] resultArray = randomString.toCharArray();

        //shuffle the digits in the code order string
        resultArray[1] = digits[3];
        resultArray[3] = digits[0];
        resultArray[5] = digits[2];
        resultArray[7] = digits[1];

        return new String(resultArray);
    }


    /**
     * Reverses the order of the characters in the given code order string and converts it to an integer.
     *
     * @param  codeOrder  the code order string to be reversed and converted to an integer
     * @return              the reversed and converted code order as an integer
     */
    private Integer reverseCodeOrder(String codeOrder) {
        char[] resultArray = codeOrder.toCharArray();
        char[] digits = new char[4];

        digits[0] = resultArray[3];
        digits[1] = resultArray[1];
        digits[2] = resultArray[7];
        digits[3] = resultArray[5];

        String numStr = String.valueOf(digits);

        return Integer.parseInt(numStr);
    }




}
