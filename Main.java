import controller.StudentController;

import javax.swing.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
//        StudentService service = new StudentService();
//        Scanner sc = new Scanner(System.in);
//        String option;
//        StudentService manager = new StudentService();
//        StudentInputHandler input = new StudentInputHandler();
//        Scanner sc = new Scanner(System.in);
//        String option;
//        do {
//            System.out.println("\n╔══════════════════════════════╗");
//            System.out.println("║        QUAN LY SINH VIEN     ║");
//            System.out.println("╠══════════════════════════════╣");
//            System.out.println("║ 1. Hien thi danh sach        ║");
//            System.out.println("║ 2. Them sinh vien            ║");
//            System.out.println("║ 3. Sua sinh vien             ║");
//            System.out.println("║ 4. Xoa sinh vien             ║");
//            System.out.println("║ 5. Tim kiem                  ║");
//            System.out.println("║ 6. Sap xep theo ten          ║");
//            System.out.println("║ 7. Doc tu file               ║");
//            System.out.println("║ 8. Ghi vao file              ║");
//            System.out.println("║ 9. Thoat                     ║");
//            System.out.println("╚══════════════════════════════╝");
//            System.out.print("Chon: ");
//            option = sc.nextLine();
//
//            switch (option) {
//                case "1":
//                    manager.showAll();
//                    break;
//                case "2":
//                    manager.addStudent(input.inputStudent());
//                    break;
//                case "3":
//                    int idSua = input.inputId("Nhap ID can sua: ");
//                    manager.updateStudent(idSua, input.inputStudent());
//                    break;
//                case "4":
//                    int idXoa = input.inputId("Nhap ID can xoa: ");
//                    manager.deleteStudent(idXoa);
//                    break;
//                case "5":
//                    System.out.println("Nhap tu khoa can tim kiem: ");
//                    String keyWord = sc.nextLine();
//                    manager.searchStudent(keyWord);
//                    break;
//                case "6":
//                    manager.sortByName();
//                    break;
//                case "7":
//                    manager.saveToFile();
//                    break;
//                case "8":
//                    manager.readFromFile();
//                    break;
//                case "9":
//                    manager.exit();
//                    break;
//                default:
//                    System.out.println("Lua chon khong hop le!");
//                    break;
//            }
//        } while (!option.equals("9"));
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Không thể thiết lập Look and Feel: " + e.getMessage());
        }

        // Create data directory if it doesn't exist
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        // Initialize and show the application
        SwingUtilities.invokeLater(() -> {
            try {
                StudentController controller = new StudentController();
                controller.showView();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Lỗi khởi tạo ứng dụng: " + e.getMessage(),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });

    }
}
