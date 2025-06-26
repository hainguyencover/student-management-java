package model;

import java.io.Serial;
import java.io.Serializable;

public class Person implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected String name;
    protected String address;
    protected String phone;
    protected String email;
    protected String gender;

    public Person() {
        this("", "", "", "", "");
    }

    public Person(String name, String address, String phone, String email, String gender) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    @Override
    public String toString() {
        return String.format("| %-20s | %-20s | %-20s | %-20s | %-20s |", name, address, phone, email, gender);
    }
}
