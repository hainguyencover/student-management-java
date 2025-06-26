package model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Student extends Person implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static int nextId = 1;
    private final int id;
    private int birthYear;
    private String major;

    public Student() {
        this(nextId, "Chưa có tên", "Chưa có địa chỉ", "Chưa có số điện thoại",
                "Chưa có email", "Khác", 2000, "Chưa xác định");
    }

    public Student(int id, String name, String address, String phone, String email,
                   String gender, int birthYear, String major) {
        super(name, address, phone, email, gender);
        this.id = id;
        this.birthYear = birthYear;
        this.major = major;
        if (id >= nextId) {
            nextId = id + 1;
        }
    }

    // Getters and Setters
    public int getId() { return id; }
    public int getBirthYear() { return birthYear; }
    public void setBirthYear(int birthYear) { this.birthYear = birthYear; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public static int getNextId() { return nextId; }
    public static void setNextId(int nextId) { Student.nextId = nextId; }

    public int getAge() {
        return LocalDateTime.now().getYear() - birthYear;
    }

    @Override
    public String toString() {
        return String.format("Student{id=%d, name='%s', address='%s', phone='%s', email='%s', gender='%s', birthYear=%d, major='%s'}",
                id, name, address, phone, email, gender, birthYear, major);
    }
}
