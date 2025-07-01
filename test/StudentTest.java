package test;

import model.Student;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import service.FormValidator;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StudentTest {
    private FormValidator validator;

    @Before
    public void setUp() {
        validator = new FormValidator(); // Khởi tạo trước mỗi test
    }

    private boolean validate(String name, String address, String phone, String email, String birthYear, String major) {
        return validator.validateStudentInput(name, address, phone, email, birthYear, major);
    }

    // ------------------------ Name Tests ------------------------
    @Test
    @DisplayName("Kiểm tra tên đúng định dạng")
    public void testValidName() {
        assertTrue(validate("Nguyễn Văn A", "123 Đường ABC", "0987654321", "test@example.com", "2000", "CNTT"));
    }

    @Test
    @DisplayName("Kiểm tra tên không được để trống")
    public void testEmptyName() {
        assertFalse(validate("", "123 Đường ABC", "0987654321", "test@example.com", "2000", "CNTT"));
    }

    @Test
    @DisplayName("Kiểm tra tên không được sử dụng số")
    public void testNameWithNumbers() {
        assertFalse(validate("An123", "123 Đường ABC", "0987654321", "test@example.com", "2000", "CNTT"));
    }

    @Test
    @DisplayName("Kiểm tra tên không được sử dụng ký tự đặc biệt")
    public void testNameWithSymbols() {
        assertFalse(validate("An@#", "123 Đường ABC", "0987654321", "test@example.com", "2000", "CNTT"));
    }

    @Test
    @DisplayName("Kiểm tra tên có ký tự Unicode")
    public void testNameWithUnicode() {
        assertTrue(validate("Đặng Thị Ánh", "123 Đường ABC", "0987654321", "test@example.com", "2000", "CNTT"));
    }

    // ------------------------ Address Tests ------------------------
    @Test
    @DisplayName("Kiểm tra địa chỉ đúng định dạng")
    public void testValidAddress() {
        assertTrue(validate("Nguyễn Văn A", "123 Lê Lợi, Q1", "0987654321", "test@example.com", "2000", "CNTT"));
    }

    @Test
    @DisplayName("Kiểm tra địa chỉ không được để trống")
    public void testEmptyAddress() {
        assertFalse(validate("Nguyễn Văn A", "", "0987654321", "test@example.com", "2000", "CNTT"));
    }

    @Test
    @DisplayName("Kiểm tra địa chỉ quá ngắn")
    public void testShortAddress() {
        assertFalse(validate("Nguyễn Văn A", "abc", "0987654321", "test@example.com", "2000", "CNTT"));
    }

    @Test
    @DisplayName("Kiểm tra địa chỉ quá dài")
    public void testLongAddress() {
        String longAddress = "a".repeat(300);
        assertFalse(validate("Nguyễn Văn A", longAddress, "0987654321", "test@example.com", "2000", "CNTT"));
    }

    // ------------------------ Phone Tests ------------------------
    @Test
    @DisplayName("Kiểm tra điện thoại đúng định dạng 10-11 chữ số")
    public void testValidPhone10Digits() {
        assertTrue(validate("Nguyễn Văn A", "123 Lê Lợi", "0987654321", "test@example.com", "2000", "CNTT"));
    }

    @Test
    @DisplayName("Kiểm tra điện thoại đúng định dạng 10-11 chữ số")
    public void testValidPhone11Digits() {
        assertTrue(validate("Nguyễn Văn A", "123 Lê Lợi", "09123456789", "test@example.com", "2000", "CNTT"));
    }

    @Test
    @DisplayName("Kiểm tra điện thoại có chữ cái")
    public void testPhoneWithLetters() {
        assertFalse(validate("Nguyễn Văn A", "123 Lê Lợi", "09abcd5678", "test@example.com", "2000", "CNTT"));
    }

    @Test
    @DisplayName("Kiểm tra điện thoại không đúng định dạng")
    public void testPhoneTooShort() {
        assertFalse(validate("Nguyễn Văn A", "123 Lê Lợi", "123456", "test@example.com", "2000", "CNTT"));
    }

    // ------------------------ Email Tests ------------------------
    @Test
    @DisplayName("Kiểm tra email đúng định dạng")
    public void testValidEmail() {
        assertTrue(validate("Nguyễn Văn A", "123 Lê Lợi", "0912345678", "abc123@gmail.com", "2000", "CNTT"));
    }

    @Test
    @DisplayName("Kiểm tra email không được để trống")
    public void testEmptyEmail() {
        assertFalse(validate("Nguyễn Văn A", "123 Lê Lợi", "0912345678", "", "2000", "CNTT"));
    }

    @Test
    @DisplayName("Kiểm tra email không đúng định dạng")
    public void testInvalidEmailNoAt() {
        assertFalse(validate("Nguyễn Văn A", "123 Lê Lợi", "0912345678", "abcgmail.com", "2000", "CNTT"));
    }

    @Test
    @DisplayName("Kiểm tra email không đúng định dạng")
    public void testInvalidEmailNoDomain() {
        assertFalse(validate("Nguyễn Văn A", "123 Lê Lợi", "0912345678", "abc@", "2000", "CNTT"));
    }

    // ------------------------ BirthYear Tests ------------------------
    @Test
    @DisplayName("Kiểm tra số năm sinh hợp lệ")
    public void testValidBirthYear() {
        assertTrue(validate("Nguyễn Văn A", "123 Lê Lợi", "0912345678", "abc@gmail.com", "1995", "CNTT"));
    }

    @Test
    @DisplayName("Kiểm tra số năm sinh không hợp lệ (1990-2025)")
    public void testBirthYearTooOld() {
        assertFalse(validate("Nguyễn Văn A", "123 Lê Lợi", "0912345678", "abc@gmail.com", "1800", "CNTT"));
    }

    @Test
    @DisplayName("Kiểm tra số năm sinh không hợp lệ (1990-2025)")
    public void testBirthYearFuture() {
        int futureYear = LocalDateTime.now().getYear() + 1;
        assertFalse(validate("Nguyễn Văn A", "123 Lê Lợi", "0912345678", "abc@gmail.com", String.valueOf(futureYear), "CNTT"));
    }

    @Test
    @DisplayName("Kiểm tra số năm sinh không hợp lệ ")
    public void testBirthYearNotNumber() {
        assertFalse(validate("Nguyễn Văn A", "123 Lê Lợi", "0912345678", "abc@gmail.com", "abcd", "CNTT"));
    }

    @Test
    @DisplayName("Kiểm tra năm sinh không được để trống")
    public void testEmptyBirthYear() {
        assertFalse(validate("Nguyễn Văn A", "123 Lê Lợi", "0912345678", "abc@gmail.com", "", "CNTT"));
    }

    // ------------------------ Major Tests ------------------------
    @Test
    @DisplayName("Kiểm tra chuyên ngành đúng định dạng")
    public void testValidMajor() {
        assertTrue(validate("Nguyễn Văn A", "123 Lê Lợi", "0912345678", "abc@gmail.com", "1998", "Kỹ thuật phần mềm"));
    }

    @Test
    @DisplayName("Kiểm tra chuyên ngành không được để trống")
    public void testEmptyMajor() {
        assertFalse(validate("Nguyễn Văn A", "123 Lê Lợi", "0912345678", "abc@gmail.com", "1998", ""));
    }

    @Test
    @DisplayName("Kiểm tra chuyên ngành quá ngắn")
    public void testMajorWithSpacesOnly() {
        assertFalse(validate("Nguyễn Văn A", "123 Lê Lợi", "0912345678", "abc@gmail.com", "1998", "    "));
    }
}
