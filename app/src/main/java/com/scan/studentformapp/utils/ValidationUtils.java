package com.scan.studentformapp.utils;

import android.util.Patterns;

public class ValidationUtils {

    public static boolean isValidEmail(String email) {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPhoneNumber(String phone) {
        return phone.length() == 10 && phone.matches("\\d+");
    }

    public static boolean isValidName(String name) {
        return !name.isEmpty() && name.length() >= 2;
    }

    public static boolean isValidIdNumber(String id) {
        return !id.isEmpty() && id.length() >= 5;
    }

    public static boolean isFileSizeValid(long sizeInKB) {
        return sizeInKB >= 90 && sizeInKB <= 120;
    }

    public static String validateFormData(String name, String idNumber, String email,
                                          String phone, Long panSizeKB, boolean hasAadhaar) {
        if (!isValidName(name)) {
            return "Please enter a valid customer name (minimum 2 characters)";
        }
        if (!isValidIdNumber(idNumber)) {
            return "Please enter a valid ID number (minimum 5 characters)";
        }
        if (!isValidEmail(email)) {
            return "Please enter a valid email address";
        }
        if (!isValidPhoneNumber(phone)) {
            return "Please enter a valid 10-digit phone number";
        }
        if (panSizeKB == null) {
            return "Please upload PAN file";
        }
        if (!isFileSizeValid(panSizeKB)) {
            return "PAN file size must be between 90-120 KB";
        }
        if (!hasAadhaar) {
            return "Please upload Aadhaar file";
        }
        return null;
    }
}
