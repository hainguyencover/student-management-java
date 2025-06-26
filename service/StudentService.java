package service;

import model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService {
    private List<Student> students;
    private final String DATA_FILE = "data/students.dat";
    private final String CSV_FILE = "data/students.csv";

    public StudentService() {
        this.students = new ArrayList<>();
        loadFromFile();
    }

    // CRUD Operations
    public void addStudent(Student student) {
        students.add(student);
        saveToFile();
    }

    public boolean updateStudent(int id, Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == id) {
                Student newStudent = new Student(id, updatedStudent.getName(),
                        updatedStudent.getAddress(), updatedStudent.getPhone(),
                        updatedStudent.getEmail(), updatedStudent.getGender(),
                        updatedStudent.getBirthYear(), updatedStudent.getMajor());
                students.set(i, newStudent);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public boolean deleteStudent(int id) {
        boolean removed = students.removeIf(student -> student.getId() == id);
        if (removed) {
            saveToFile();
        }
        return removed;
    }

    public Student findStudentById(int id) {
        return students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    // Search Operations
    public List<Student> searchByName(String name) {
        return students.stream()
                .filter(student -> student.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Student> searchByAddress(String address) {
        return students.stream()
                .filter(student -> student.getAddress().toLowerCase().contains(address.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Student> searchByBirthYear(int birthYear) {
        return students.stream()
                .filter(student -> student.getBirthYear() == birthYear)
                .collect(Collectors.toList());
    }

    public List<Student> searchByMajor(String major) {
        return students.stream()
                .filter(student -> student.getMajor().toLowerCase().contains(major.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Sort Operations
    public List<Student> sortByName() {
        return students.stream()
                .sorted(Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    public List<Student> sortByBirthYear() {
        return students.stream()
                .sorted(Comparator.comparing(Student::getBirthYear))
                .collect(Collectors.toList());
    }

    public List<Student> sortById() {
        return students.stream()
                .sorted(Comparator.comparing(Student::getId))
                .collect(Collectors.toList());
    }

    // File Operations
    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
                students = (List<Student>) ois.readObject();
                if (!students.isEmpty()) {
                    int maxId = students.stream().mapToInt(Student::getId).max().orElse(0);
                    Student.setNextId(maxId + 1);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Lỗi khi đọc file: " + e.getMessage());
                students = new ArrayList<>();
            }
        }
    }

    public void exportToCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE))) {
            writer.println("ID,Name,Address,Phone,Email,Gender,BirthYear,Major");
            for (Student student : students) {
                writer.printf("%d,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%d,\"%s\"%n",
                        student.getId(), student.getName(), student.getAddress(),
                        student.getPhone(), student.getEmail(), student.getGender(),
                        student.getBirthYear(), student.getMajor());
            }
            System.out.println("Đã xuất dữ liệu ra file: " + CSV_FILE);
        } catch (IOException e) {
            System.err.println("Lỗi khi xuất file CSV: " + e.getMessage());
        }
    }

    public void importFromCSV() {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            System.out.println("File CSV không tồn tại: " + CSV_FILE);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            boolean isFirstLine = true;
            int importedCount = 0;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }

                String[] tokens = parseCSVLine(line);
                if (tokens.length >= 8) {
                    try {
                        int id = Integer.parseInt(tokens[0]);
                        String name = tokens[1];
                        String address = tokens[2];
                        String phone = tokens[3];
                        String email = tokens[4];
                        String gender = tokens[5];
                        int birthYear = Integer.parseInt(tokens[6]);
                        String major = tokens[7];

                        Student student = new Student(id, name, address, phone, email, gender, birthYear, major);
                        if (findStudentById(id) == null) {
                            students.add(student);
                            importedCount++;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Lỗi định dạng số trong dòng: " + line);
                    }
                }
            }
            saveToFile();
            System.out.println("Đã import " + importedCount + " sinh viên từ file CSV.");
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file CSV: " + e.getMessage());
        }
    }

    private String[] parseCSVLine(String line) {
        List<String> tokens = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                tokens.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        tokens.add(current.toString());

        return tokens.toArray(new String[0]);
    }
}
