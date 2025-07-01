package controller;

import model.Student;
import service.StudentService;
import view.StudentView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class StudentController {
    private final StudentService studentService;
    private final StudentView studentView;
    private List<Student> currentStudnets;

    public StudentController() {
        this.studentService = new StudentService();
        this.studentView = new StudentView();
        initializeEventListeners();
        updateTable();
    }

    private void initializeEventListeners() {
        // Add Student
        studentView.getAddButton().addActionListener(e -> showAddDialog());
        // Edit Student
        studentView.getEditButton().addActionListener(e -> showEditDialog());
        // Delete Student
        studentView.getDeleteButton().addActionListener(e -> deleteStudent());
        // Search
        studentView.getSearchButton().addActionListener(e -> performSearch());
        // File operations
        studentView.getImportButton().addActionListener(e -> importFromCSV());
        studentView.getExportButton().addActionListener(e -> exportToCSV());
        // Navigation
        studentView.getFirstButton().addActionListener(e -> navigateFirst());
        studentView.getPrevButton().addActionListener(e -> navigatePrevious());
        studentView.getNextButton().addActionListener(e -> navigateNext());
        studentView.getLastButton().addActionListener(e -> navigateLast());
        // Refresh
        studentView.getRefreshButton().addActionListener(e -> updateTable());
        // Clear search
        studentView.getSearchField().addActionListener(e -> {
            if (studentView.getSearchField().getText().trim().isEmpty()) {
                updateTable();
            }
        });
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog(studentView.getFrame(), "Thêm sinh viên mới", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(studentView.getFrame());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField nameField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        JComboBox<String> genderCombo = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        JTextField birthYearField = new JTextField();
        JTextField majorField = new JTextField();

        panel.add(new JLabel("Tên:"));
        panel.add(nameField);
        panel.add(new JLabel("Địa chỉ:"));
        panel.add(addressField);
        panel.add(new JLabel("Số điện thoại:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Giới tính:"));
        panel.add(genderCombo);
        panel.add(new JLabel("Năm sinh:"));
        panel.add(birthYearField);
        panel.add(new JLabel("Chuyên ngành:"));
        panel.add(majorField);

        JButton saveButton = new JButton("Lưu");
        JButton cancelButton = new JButton("Hủy");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel);

        saveButton.addActionListener(e -> {
            if (validateStudentInput(nameField.getText(), addressField.getText(),
                    phoneField.getText(), emailField.getText(),
                    birthYearField.getText(), majorField.getText())) {

                Student student = new Student(
                        Student.getNextId(),
                        nameField.getText().trim(),
                        addressField.getText().trim(),
                        phoneField.getText().trim(),
                        emailField.getText().trim(),
                        (String) genderCombo.getSelectedItem(),
                        Integer.parseInt(birthYearField.getText().trim()),
                        majorField.getText().trim()
                );
                boolean success = studentService.addStudent(student);
                if (success) {
                    reloadTableAfterOperation();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(studentView.getFrame(), "Thêm sinh viên thành công!");
                } else {
                    JOptionPane.showMessageDialog(studentView.getFrame(), "Lỗi khi thêm sinh viên!");
                }
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showEditDialog() {
        int selectedRow = studentView.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(studentView.getFrame(), "Vui lòng chọn sinh viên cần sửa!");
            return;
        }

        int id = (Integer) studentView.getTableModel().getValueAt(selectedRow, 0);
        Student student = studentService.findStudentById(id);

        if (student == null) {
            JOptionPane.showMessageDialog(studentView.getFrame(), "Không tìm thấy sinh viên!");
            return;
        }

        JDialog dialog = new JDialog(studentView.getFrame(), "Sửa thông tin sinh viên", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(studentView.getFrame());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField nameField = new JTextField(student.getName());
        JTextField addressField = new JTextField(student.getAddress());
        JTextField phoneField = new JTextField(student.getPhone());
        JTextField emailField = new JTextField(student.getEmail());
        JComboBox<String> genderCombo = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        genderCombo.setSelectedItem(student.getGender());
        JTextField birthYearField = new JTextField(String.valueOf(student.getBirthYear()));
        JTextField majorField = new JTextField(student.getMajor());

        panel.add(new JLabel("Tên:"));
        panel.add(nameField);
        panel.add(new JLabel("Địa chỉ:"));
        panel.add(addressField);
        panel.add(new JLabel("Số điện thoại:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Giới tính:"));
        panel.add(genderCombo);
        panel.add(new JLabel("Năm sinh:"));
        panel.add(birthYearField);
        panel.add(new JLabel("Chuyên ngành:"));
        panel.add(majorField);

        JButton saveButton = new JButton("Cập nhật");
        JButton cancelButton = new JButton("Hủy");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel);

        saveButton.addActionListener(e -> {
            if (validateStudentInput(nameField.getText(), addressField.getText(),
                    phoneField.getText(), emailField.getText(),
                    birthYearField.getText(), majorField.getText())) {

                Student updatedStudent = new Student(
                        id,
                        nameField.getText().trim(),
                        addressField.getText().trim(),
                        phoneField.getText().trim(),
                        emailField.getText().trim(),
                        (String) genderCombo.getSelectedItem(),
                        Integer.parseInt(birthYearField.getText().trim()),
                        majorField.getText().trim()
                );

                boolean success = studentService.updateStudent(id, updatedStudent);
                if (success) {
                    // Reload table và giữ lại selection
                    int currentSelectedId = id;
                    reloadTableAfterOperation();
                    selectStudentById(currentSelectedId);
                    dialog.dispose();
                    JOptionPane.showMessageDialog(studentView.getFrame(), "Cập nhật sinh viên thành công!");
                } else {
                    JOptionPane.showMessageDialog(studentView.getFrame(), "Lỗi khi cập nhật sinh viên!");
                }
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private boolean validateStudentInput(String name, String address, String phone,
                                         String email, String birthYear, String major) {
        if (name.trim().isEmpty() || !name.matches("^[a-zA-ZÀ-ỹ\\s]+$")) {
            JOptionPane.showMessageDialog(studentView.getFrame(), "Tên không hợp lệ!");
            return false;
        }

        if (address.trim().isEmpty() || !address.matches("^[\\p{L}0-9\\s,.-]{5,255}$")) {
            JOptionPane.showMessageDialog(studentView.getFrame(), "Địa chỉ không hợp lệ!");
            return false;
        }

        if (phone.trim().isEmpty() || !phone.matches("\\d{10,11}")) {
            JOptionPane.showMessageDialog(studentView.getFrame(), "Số điện thoại không hợp lệ");
            return false;
        }

        if (email.trim().isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(studentView.getFrame(), "Email không hợp lệ!");
            return false;
        }

        if (birthYear.trim().isEmpty() || !birthYear.matches("^(19|20)\\d{2}$")) {
            JOptionPane.showMessageDialog(studentView.getFrame(), "Năm sinh không hợp lệ!");
            return false;
        }

        try {
            int year = Integer.parseInt(birthYear);
            int currentYear = LocalDateTime.now().getYear();
            if (year < 1900 || year > currentYear) {
                JOptionPane.showMessageDialog(studentView.getFrame(),
                        "Năm sinh phải từ 1900 đến " + currentYear + "!");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(studentView.getFrame(), "Năm sinh phải là số!");
            return false;
        }

        if (major.trim().isEmpty()) {
            JOptionPane.showMessageDialog(studentView.getFrame(), "Chuyên ngành không được để trống!");
            return false;
        }

        return true;
    }

    private void deleteStudent() {
        int selectedRow = studentView.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(studentView.getFrame(), "Vui lòng chọn sinh viên cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                studentView.getFrame(),
                "Bạn có chắc chắn muốn xóa sinh viên này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            int id = (Integer) studentView.getTableModel().getValueAt(selectedRow, 0);

            boolean success = studentService.deleteStudent(id);
            if (success) {
                // Cập nhật bảng và điều chỉnh selection
                adjustSelectionAfterDelete(selectedRow);
                reloadTableAfterOperation();
                JOptionPane.showMessageDialog(studentView.getFrame(), "Xóa sinh viên thành công!");
            } else {
                JOptionPane.showMessageDialog(studentView.getFrame(), "Lỗi khi xóa sinh viên!");
            }
        }
    }

    private void performSearch() {
        String searchText = studentView.getSearchField().getText().trim();
        String searchType = (String) studentView.getSearchCombo().getSelectedItem();

        if (searchText.isEmpty()) {
            updateTable();
            return;
        }

        List<Student> results = null;
        switch (Objects.requireNonNull(searchType)) {
            case "Tên":
                results = studentService.searchByName(searchText);
                break;
            case "Địa chỉ":
                results = studentService.searchByAddress(searchText);
                break;
            case "Năm sinh":
                try {
                    int year = Integer.parseInt(searchText);
                    results = studentService.searchByBirthYear(year);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(studentView.getFrame(), "Năm sinh phải là số!");
                    return;
                }
                break;
            case "Chuyên ngành":
                results = studentService.searchByMajor(searchText);
                break;
        }

        if (results != null) {
            studentView.updateTable(results);
        }
    }

    private void exportToCSV() {
        studentService.exportToCSV();
        JOptionPane.showMessageDialog(studentView.getFrame(), "Xuất file CSV thành công!");
    }

    private void importFromCSV() {
        int confirm = JOptionPane.showConfirmDialog(
                studentView.getFrame(),
                "Import dữ liệu từ file CSV sẽ thêm các sinh viên mới. Tiếp tục?",
                "Xác nhận import",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            studentService.importFromCSV();
            updateTable();
        }
    }

    // Navigation methods
    private void navigateFirst() {
        if (studentView.getTable().getRowCount() > 0) {
            studentView.getTable().setRowSelectionInterval(0, 0);
            studentView.getTable().scrollRectToVisible(studentView.getTable().getCellRect(0, 0, true));
        }
    }

    private void navigateLast() {
        int lastRow = studentView.getTable().getRowCount() - 1;
        if (lastRow >= 0) {
            studentView.getTable().setRowSelectionInterval(lastRow, lastRow);
            studentView.getTable().scrollRectToVisible(studentView.getTable().getCellRect(lastRow, 0, true));
        }
    }

    private void navigatePrevious() {
        int currentRow = studentView.getTable().getSelectedRow();
        if (currentRow > 0) {
            studentView.getTable().setRowSelectionInterval(currentRow - 1, currentRow - 1);
            studentView.getTable().scrollRectToVisible(studentView.getTable().getCellRect(currentRow - 1, 0, true));
        }
    }

    private void navigateNext() {
        int currentRow = studentView.getTable().getSelectedRow();
        int totalRows = studentView.getTable().getRowCount();
        if (currentRow < totalRows - 1) {
            studentView.getTable().setRowSelectionInterval(currentRow + 1, currentRow + 1);
            studentView.getTable().scrollRectToVisible(studentView.getTable().getCellRect(currentRow + 1, 0, true));
        }
    }

    private void navigatePrevious2() {
        int currentRow = studentView.getTable().getSelectedRow();
        int newRow = Math.max(0, currentRow - 2);
        if (newRow != currentRow) {
            studentView.getTable().setRowSelectionInterval(newRow, newRow);
            studentView.getTable().scrollRectToVisible(studentView.getTable().getCellRect(newRow, 0, true));
        }
    }

    private void navigateNext2() {
        int currentRow = studentView.getTable().getSelectedRow();
        int totalRows = studentView.getTable().getRowCount();
        int newRow = Math.min(totalRows - 1, currentRow + 2);
        if (newRow != currentRow) {
            studentView.getTable().setRowSelectionInterval(newRow, newRow);
            studentView.getTable().scrollRectToVisible(studentView.getTable().getCellRect(newRow, 0, true));
        }
    }

    private void updateTable() {
//        List<Student> students = studentService.getAllStudents();
//        studentView.updateTable(students);
        try {
            currentStudnets = studentService.getAllStudents();
            studentView.updateTable(currentStudnets);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(studentView.getFrame(), "Lỗi khi cập nhật bảng: " + e.getMessage());
        }
    }


    private void reloadTableAfterOperation() {
        String searchText = studentView.getSearchField().getText().trim();
        if (searchText.isEmpty()) {
            // Nếu không có search, load toàn bộ
            updateTable();
        } else {
            // Nếu đang search, thực hiện lại search để cập nhật kết quả
            performSearch();
        }
    }


    // Chọn sinh viên theo ID sau khi cập nhật
    private void selectStudentById(int id) {
        for (int i = 0; i < studentView.getTable().getRowCount(); i++) {
            int modelRow = studentView.getTable().convertRowIndexToModel(i);
            int rowId = (Integer) studentView.getTableModel().getValueAt(modelRow, 0);
            if (rowId == id) {
                studentView.getTable().setRowSelectionInterval(i, i);
                studentView.getTable().scrollRectToVisible(studentView.getTable().getCellRect(i, 0, true));
                break;
            }
        }
    }

    // Điều chỉnh selection sau khi xóa
    private void adjustSelectionAfterDelete(int deletedRow) {
        int rowCount = studentView.getTable().getRowCount();
        if (rowCount > 0) {
            int newSelectedRow = Math.min(deletedRow, rowCount - 1);
            studentView.getTable().setRowSelectionInterval(newSelectedRow, newSelectedRow);
            studentView.getTable().scrollRectToVisible(
                    studentView.getTable().getCellRect(newSelectedRow, 0, true));
        }
    }


    public void showView() {
        studentView.setVisible(true);
    }


}
