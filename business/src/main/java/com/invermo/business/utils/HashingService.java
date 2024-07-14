package com.invermo.business.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HashingService {

    private static final String HASHING_ALGORITHM = "SHA-256";

    public static String hashText(String text) {
        final MessageDigest digest = getMessageDigest();
        final byte[] encodedHash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedHash);
    }

    private static MessageDigest getMessageDigest() {
        try {
            return MessageDigest.getInstance(HASHING_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
