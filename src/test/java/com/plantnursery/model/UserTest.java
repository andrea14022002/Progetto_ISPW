package com.plantnursery.model;

import com.plantnursery.exception.EncryptionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testGetUsername() throws EncryptionException {
        User user = new User("user", "password");
        assertEquals("user", user.getUsername());
    }

    @Test
    void testCheckPassword() throws EncryptionException {
        User user = new User("user", "password");
        assertTrue(user.checkPassword("password"));
    }
}