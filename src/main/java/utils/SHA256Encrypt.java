package utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Encrypt {
    //Doc: https://www.geeksforgeeks.org/sha-256-hash-in-java/
    public byte[] Hash(String input) {
        if (input.trim().isEmpty()) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String HashString(String input) {
        if (input.trim().isEmpty()) return null;
        byte[] arr = Hash(input);
        BigInteger number = new BigInteger(1, arr);
        StringBuilder str = new StringBuilder(number.toString(16));
        while(str.length() < 32) {
            str.insert(0, '0');
        }
        return str.toString();
    }
}
