import controller.StudentController;

import javax.swing.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
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
