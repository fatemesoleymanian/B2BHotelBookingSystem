package com.example.B2BHotelBookingSystem.config.utils;

import java.security.SecureRandom;
import java.util.regex.Pattern;

public class Utilities {
        private static final String CHARACTERS =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        private static final SecureRandom SECURE_RANDOM = new SecureRandom();

        public static String generateRandomString(int size) {
            if (size <= 0) {
                throw new IllegalArgumentException("Size must be positive");
            }

            StringBuilder sb = new StringBuilder(size);
            for (int i = 0; i < size; i++) {
                int index = SECURE_RANDOM.nextInt(CHARACTERS.length());
                sb.append(CHARACTERS.charAt(index));
            }
            return sb.toString();
        }


        // RFC 5322 compliant (simplified & safe for backend validation)
        private static final Pattern EMAIL_PATTERN = Pattern.compile(
                "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
                Pattern.CASE_INSENSITIVE
        );

        public static boolean isEmail(String str) {
            if (str == null) return false;
            return EMAIL_PATTERN.matcher(str).matches();
        }

        // ================= Phone Number =================

        // Supports international numbers (E.164) and common formats
        private static final Pattern PHONE_PATTERN = Pattern.compile(
                "^\\+?[0-9]{10,15}$"
        );

        public static boolean isPhoneNumber(String str) {
            if (str == null) return false;
            return PHONE_PATTERN.matcher(str).matches();
        }
    }
