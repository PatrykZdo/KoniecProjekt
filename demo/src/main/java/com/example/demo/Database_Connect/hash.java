package com.example.demo.Database_Connect;

import jakarta.xml.bind.DatatypeConverter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class hash {
    public static String hashingPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hashedPassword = DatatypeConverter.printHexBinary(digest);
        return hashedPassword;
    }
}
