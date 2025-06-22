package service;

import model.Student;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StudentService {
    private List<Student> students = new ArrayList<>();
    private final String FILE_PATH = "data/students.txt";
    private Scanner sc = new Scanner(System.in);

    public StudentService() {
        Thread loadThread = new Thread(() -> {
            List<Student> list = FileUtil.readFromFile(FILE_PATH);
            if (list != null)
                students = list;
        });
        loadThread.start();
        try {
            loadThread.join();
        } catch (InterruptedException e) {
            System.out.println("Loi khi load du lieu!");
        }
    }

    public void save() {
        FileUtil.saveToFile(FILE_PATH, students);
    }

    public void showAll() {
        if (students.isEmpty()) {
            System.out.println("Danh sach rong.");
            return;
        }
        System.out.println("Danh sach sinh vien:");
        System.out.println("+------+----------------------+-----+------------+");
        System.out.println("| ID   | Name                 | Age | Major      |");
        System.out.println("+------+----------------------+-----+------------+");
        students.forEach(System.out::println);
        System.out.println("+------+----------------------+-----+------------+");
    }

    public void addStudent() {
        try {
            System.out.print("Nhap ID: ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Nhap ten: ");
            String name = sc.nextLine();
            System.out.print("Nhap tuoi: ");
            int age = Integer.parseInt(sc.nextLine());
            System.out.print("Nhap chuyen nganh: ");
            String major = sc.nextLine();

            students.add(new Student(id, name, age, major));
            System.out.println("Them thanh cong!");
        } catch (NumberFormatException e) {
            System.out.println("Du lieu khong hop le!");
        }
    }

    public void updateStudent() {
        System.out.print("Nhap ID sinh vien can sua: ");
        int id = Integer.parseInt(sc.nextLine());
        Student s = findById(id);
        if (s == null) {
            System.out.println("Khong tim thay sinh vien!");
            return;
        }
        System.out.print("Ten moi: ");
        s.setName(sc.nextLine());
        System.out.print("Tuoi moi: ");
        s.setAge(Integer.parseInt(sc.nextLine()));
        System.out.print("Nganh moi: ");
        s.setMajor(sc.nextLine());

        System.out.println("Cap nhat thanh cong!");
    }

    public void deleteStudent() {
        System.out.print("Nhap ID sinh vien can xoa: ");
        int id = Integer.parseInt(sc.nextLine());
        Student s = findById(id);
        if (s != null) {
            students.remove(s);
            System.out.println("Da xoa!");
        } else {
            System.out.println("Khong tim thay!");
        }
    }

    private Student findById(int id) {
        return students.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public void searchByName() {
        System.out.print("Nhap ten can tim: ");
        String keyword = sc.nextLine().toLowerCase();
        List<Student> results = students.stream()
                .filter(s -> s.getName().toLowerCase().contains(keyword))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            System.out.println("Khong tim thay sinh vien nao.");
        } else {
            System.out.println("Ket qua tim kiem:");
            System.out.println("+------+----------------------+-----+------------+");
            System.out.println("| ID   | Name                 | Age | Major      |");
            System.out.println("+------+----------------------+-----+------------+");
            results.forEach(System.out::println);
            System.out.println("+------+----------------------+-----+------------+");
        }
    }

    public void sortByName() {
        if (students.isEmpty()) {
            System.out.println("Danh sach rong.");
            return;
        }

        students.sort((s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName()));
        System.out.println("Da sap xep danh sach theo ten A → Z.");
        showAll();
    }
}
