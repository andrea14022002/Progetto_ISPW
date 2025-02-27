package com.plantnursery.model;

import com.plantnursery.exception.EncryptionException;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    private final String username;

    private final String password;

    public User(String username, String password) throws EncryptionException {
        this.username = username;
        this.password = encrypt(password);
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean checkPassword(String password) throws EncryptionException {
        return this.password.equals(encrypt(password));
    }

    private String encrypt(String input) throws EncryptionException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder sb = new StringBuilder(no.toString(16));
            while (sb.length() < 128) {
                sb.insert(0, "0");
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException("Error in encrypt in user model.", e);
        }
    }
}
