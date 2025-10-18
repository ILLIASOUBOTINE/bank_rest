package com.example.bankcards.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil {
    private EncryptionUtil() {
        // Приватный конструктор, чтобы нельзя было создать экземпляр утилиты
    }

    public static String encryptNumber(String number) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encoded = digest.digest(number.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : encoded) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Encryption error", e);
        }
    }
}
