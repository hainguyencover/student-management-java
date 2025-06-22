import service.StudentService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentService service = new StudentService();
        Scanner sc = new Scanner(System.in);
        String option;

        do {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║        QUAN LY SINH VIEN     ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║ 1. Hien thi danh sach        ║");
            System.out.println("║ 2. Them sinh vien            ║");
            System.out.println("║ 3. Sua sinh vien             ║");
            System.out.println("║ 4. Xoa sinh vien             ║");
            System.out.println("║ 5. Tim kiem theo ten         ║");
            System.out.println("║ 6. Sap xep theo ten          ║");
            System.out.println("║ 7. Luu va Thoat              ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("Chon: ");
            option = sc.nextLine();

            switch (option) {
                case "1":
                    service.showAll();
                    break;
                case "2":
                    service.addStudent();
                    break;
                case "3":
                    service.updateStudent();
                    break;
                case "4":
                    service.deleteStudent();
                    break;
                case "5":
                    service.searchByName();
                    break;
                case "6":
                    service.sortByName();
                    break;
                case "7":
                    service.save();
                    System.out.println("Du lieu da duoc luu. Tam biet!");
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
                    break;
            }
        } while (!option.equals("7"));
    }
}
