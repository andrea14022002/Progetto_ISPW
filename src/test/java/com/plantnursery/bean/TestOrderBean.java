package com.plantnursery.bean;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.plantnursery.exception.IncorrectDataException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TestBookingBean {

    @Test
    void testSetEmail() throws IncorrectDataException {
        OrderBean orderBean = new OrderBean();
        orderBean.setEmail("2p.ro.v_a0@tor.vergata.com");
        Assertions.assertEquals("2p.ro.v_a0@tor.vergata.com", orderBean.getEmail());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "pr@va@torvergata.com", // DoubleAt
            "provatorvergata.com",  // NoAt
            "prova@torvergatacom", // NoDot
            "prova@torvergata.", // NoDomain
            "@torvergata.com", // NoUser
            "prova@torvergata", // NoTLD
            "prova@torvergata.comm", // LongTLD
            "prova@torvergata.c" // ShortTLD
            })
    void testSetEmailIncorrect(String email) {
        String errorMess = "";
        OrderBean orderBean = new OrderBean();
        try {
            orderBean.setEmail(email);
        } catch (IncorrectDataException e) {
            errorMess = e.getMessage();
        }
        Assertions.assertEquals("Email is not valid.", errorMess);
    }

    @Test
    void testSetTelephone() throws IncorrectDataException {
        OrderBean orderBean = new OrderBean();
        orderBean.setTelephone("+393895996644");
        Assertions.assertEquals("+393895996644", orderBean.getTelephone());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "+3932678903", // Short - Length must be 3 prefix + 10 number
            "+39326789038909", // Long - Length must be 3 prefix + 10 number
            "+393267a90A89", // NotNumber
            "393267890389", // NoPlus
            })
    void testSetTelephoneIncorrect(String telephone) {
        String errorMess = "";
        OrderBean orderBean = new OrderBean();
        try {
            orderBean.setTelephone(telephone);
        } catch (IncorrectDataException e) {
            errorMess = e.getMessage();
        }
        Assertions.assertEquals("Telephone is not valid.", errorMess);
    }

    @Test
    void testSetFirstName() throws IncorrectDataException {
        OrderBean orderBean = new OrderBean();
        orderBean.setFirstName("Luca Flavio");
        Assertions.assertEquals("Luca Flavio", orderBean.getFirstName());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "luca", // Lowercase
            "Luca1", // WithNumber
            "Luca@", // WithSpecialChar
            "Luca ", // WithSpace
            })
    void testSetFirstNameIncorrect(String firstName) {
        String errorMess = "";
        OrderBean orderBean = new OrderBean();
        try {
            orderBean.setFirstName(firstName);
        } catch (IncorrectDataException e) {
            errorMess = e.getMessage();
        }
        Assertions.assertEquals("Firstname is not valid.", errorMess);
    }

    @Test
    void testSetLastName() throws IncorrectDataException {
        OrderBean orderBean = new OrderBean();
        orderBean.setLastName("O'Brien");
        Assertions.assertEquals("O'Brien", orderBean.getLastName());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "martorelli", // Lowercase
            "Martorelli1", // WithNumber
            "Martorelli@", // WithSpecialChar
            "Martorelli ", // WithSpace
            })
    void testSetLastNameIncorrect(String lastName) {
        String errorMess = "";
        OrderBean orderBean = new OrderBean();
        try {
            orderBean.setLastName(lastName);
        } catch (IncorrectDataException e) {
            errorMess = e.getMessage();
        }
        Assertions.assertEquals("Lastname is not valid.", errorMess);
    }

}
