package service;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormValidator {
    //    private Pattern pattern;
//    private Matcher matcher;
//    private String EMAIL_REGEX = "^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$";
//    private String PHONE_REGEX = "^0[3|5|7|8|9]\\d{8}$"; // Example: +1234567890 or 1234567890
//    private String NAME_REGEX = "^[a-zA-ZÀ-ỹ\\s]+$"; // Example: John Doe
//    private String ADDRESS_REGEX = "^[a-zA-Z0-9\\s,.-]{5,100}$"; // Example: 123 Main St, City, Country
//    private String BIRTH_YEAR_REGEX = "^(19|25)\\d{2}$"; // Example: 1990 or 2000
//    private String MAJOR_REGEX = "^[a-zA-Z\\s]{2,50}$"; // Example: Computer Science or Mathematics
//
//    public boolean validateEmail(String email) {
//        pattern = Pattern.compile(EMAIL_REGEX);
//        matcher = pattern.matcher(email);
//        return matcher.matches();
//    }
//
//    public boolean validatePhone(String phone) {
//        pattern = Pattern.compile(PHONE_REGEX);
//        matcher = pattern.matcher(phone);
//        return matcher.matches();
//    }
//
//    public boolean validateName(String name) {
//        pattern = Pattern.compile(NAME_REGEX);
//        matcher = pattern.matcher(name);
//        return matcher.matches();
//    }
//
//    public boolean validateAddress(String address) {
//        pattern = Pattern.compile(ADDRESS_REGEX);
//        matcher = pattern.matcher(address);
//        return matcher.matches();
//    }
//
//    public boolean validateBirthYear(String birthYear) {
//        pattern = Pattern.compile(BIRTH_YEAR_REGEX);
//        matcher = pattern.matcher(birthYear);
//        return matcher.matches();
//    }
//
//    public boolean validateMajor(String major) {
//        pattern = Pattern.compile(MAJOR_REGEX);
//        matcher = pattern.matcher(major);
//        return matcher.matches();
//    }
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-ZÀ-ỹ\\s]+$");
    private static final Pattern ADDRESS_PATTERN = Pattern.compile("^[\\p{L}0-9\\s,.-]{5,255}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10,11}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern BIRTH_YEAR_PATTERN = Pattern.compile("^(19|25)\\d{2}$");

    public boolean validateStudentInput(String name, String address, String phone,
                                        String email, String birthYear, String major) {
        if (name == null || name.trim().isEmpty() || !NAME_PATTERN.matcher(name).matches()) return false;
        if (address == null || address.trim().isEmpty() || !ADDRESS_PATTERN.matcher(address).matches()) return false;
        if (phone == null || !PHONE_PATTERN.matcher(phone).matches()) return false;
        if (email == null || email.trim().isEmpty() || !EMAIL_PATTERN.matcher(email).matches()) return false;
        if (birthYear == null || !BIRTH_YEAR_PATTERN.matcher(birthYear).matches()) return false;

        try {
            int year = Integer.parseInt(birthYear);
            int currentYear = LocalDateTime.now().getYear();
            if (year < 1900 || year > currentYear) return false;
        } catch (NumberFormatException e) {
            return false;
        }

        return major != null && !major.trim().isEmpty();
    }
}
