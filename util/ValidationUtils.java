package util;

import java.util.regex.Pattern;

public class ValidationUtils {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^(\\+84|0)[0-9]{9,10}$");

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.trim().length() >= 2;
    }

    public static boolean isValidBirthYear(int year) {
        int currentYear = java.time.LocalDateTime.now().getYear();
        return year >= 1900 && year <= currentYear;
    }

    public static String formatPhoneNumber(String phone) {
        if (phone == null) return "";

        // Remove all non-digit characters
        String digits = phone.replaceAll("\\D", "");

        // Format as xxx-xxx-xxxx or similar
        if (digits.length() == 10) {
            return digits.substring(0, 3) + "-" +
                    digits.substring(3, 6) + "-" +
                    digits.substring(6);
        } else if (digits.length() == 11 && digits.startsWith("0")) {
            return digits.substring(0, 4) + "-" +
                    digits.substring(4, 7) + "-" +
                    digits.substring(7);
        }

        return phone; // Return original if can't format
    }
}
