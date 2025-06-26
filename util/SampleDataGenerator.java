package util;

import model.Student;
import service.StudentService;

public class SampleDataGenerator {
    private static final String[] SAMPLE_NAMES = {
            "Nguyễn Văn An", "Trần Thị Bình", "Lê Hoàng Cường", "Phạm Thị Dung",
            "Hoàng Văn Em", "Vũ Thị Phương", "Đỗ Minh Giang", "Bùi Thị Hoa",
            "Dương Văn Inh", "Chu Thị Kim", "Lý Văn Long", "Mạc Thị Mai",
            "Tạ Văn Nam", "Đinh Thị Oanh", "Phan Văn Phúc", "Quách Thị Quỳnh"
    };

    private static final String[] SAMPLE_ADDRESSES = {
            "Hà Nội", "Hồ Chí Minh", "Đà Nẵng", "Hải Phòng", "Cần Thơ",
            "Bình Dương", "Đồng Nai", "Khánh Hòa", "Lâm Đồng", "Bắc Ninh",
            "Quảng Nam", "Thanh Hóa", "Nghệ An", "Thừa Thiên Huế", "Kiên Giang"
    };

    private static final String[] SAMPLE_MAJORS = {
            "Công nghệ thông tin", "Kỹ thuật phần mềm", "Khoa học máy tính",
            "Kế toán", "Quản trị kinh doanh", "Marketing", "Tài chính ngân hàng",
            "Kỹ thuật điện", "Kỹ thuật cơ khí", "Kỹ thuật xây dựng",
            "Y khoa", "Dược học", "Điều dưỡng", "Luật", "Ngôn ngữ Anh"
    };

    private static final String[] GENDERS = {"Nam", "Nữ"};

    public static void generateSampleData(StudentService studentService, int count) {
        for (int i = 0; i < count; i++) {
            String name = SAMPLE_NAMES[i % SAMPLE_NAMES.length];
            String address = SAMPLE_ADDRESSES[i % SAMPLE_ADDRESSES.length];
            String phone = "09" + String.format("%08d", (int)(Math.random() * 100000000));
            String email = generateEmail(name);
            String gender = GENDERS[i % 2];
            int birthYear = 1995 + (int)(Math.random() * 10); // 1995-2004
            String major = SAMPLE_MAJORS[i % SAMPLE_MAJORS.length];

            Student student = new Student(
                    Student.getNextId(), name, address, phone, email, gender, birthYear, major
            );
            studentService.addStudent(student);
        }
    }

    private static String generateEmail(String name) {
        String[] parts = name.toLowerCase().split(" ");
        StringBuilder email = new StringBuilder();

        // Use last name + first letter of other names
        if (parts.length >= 2) {
            email.append(parts[parts.length - 1]); // Last name
            for (int i = 0; i < parts.length - 1; i++) {
                if (!parts[i].isEmpty()) {
                    email.append(parts[i].charAt(0));
                }
            }
        } else {
            email.append(parts[0]);
        }

        email.append((int)(Math.random() * 1000));
        email.append("@gmail.com");

        return email.toString();
    }
}
