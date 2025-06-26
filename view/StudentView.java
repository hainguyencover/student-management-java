package view;

import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

public class StudentView {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> searchCombo;

    // Buttons
    private JButton addButton, editButton, deleteButton, searchButton, refreshButton;
    private JButton sortNameButton, sortYearButton, sortIdButton;
    private JButton importButton, exportButton;
    private JButton firstButton, prevButton, nextButton, lastButton, prev2Button, next2Button;

    public StudentView() {
        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        frame = new JFrame("Hệ thống quản lý sinh viên");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null);

        // Create table model
        String[] columnNames = {"ID", "Tên", "Địa chỉ", "Số ĐT", "Email", "Giới tính", "Năm sinh", "Tuổi", "Chuyên ngành"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(25);
        table.getTableHeader().setReorderingAllowed(false);

        // Alternate row colors
        table.setDefaultRenderer(Object.class, new AlternatingRowRenderer());

        // Search components
        searchField = new JTextField(20);
        searchCombo = new JComboBox<>(new String[]{"Tên", "Địa chỉ", "Năm sinh", "Chuyên ngành"});

        // Initialize buttons
        addButton = new JButton("Thêm");
        editButton = new JButton("Sửa");
        deleteButton = new JButton("Xóa");
        searchButton = new JButton("Tìm kiếm");
        refreshButton = new JButton("Làm mới");

        sortNameButton = new JButton("Sắp xếp theo tên");
        sortYearButton = new JButton("Sắp xếp theo năm sinh");
        sortIdButton = new JButton("Sắp xếp theo ID");

        importButton = new JButton("Import CSV");
        exportButton = new JButton("Export CSV");

        firstButton = new JButton("<<");
        prevButton = new JButton("<");
        nextButton = new JButton(">");
        lastButton = new JButton(">>");
        prev2Button = new JButton("< <");
        next2Button = new JButton("> >");

        // Button tooltips
        addButton.setToolTipText("Thêm sinh viên mới");
        editButton.setToolTipText("Sửa thông tin sinh viên đã chọn");
        deleteButton.setToolTipText("Xóa sinh viên đã chọn");
        searchButton.setToolTipText("Tìm kiếm sinh viên");
        refreshButton.setToolTipText("Làm mới danh sách");

        firstButton.setToolTipText("Đến đầu danh sách");
        prevButton.setToolTipText("Lùi 1 bước");
        nextButton.setToolTipText("Tiến 1 bước");
        lastButton.setToolTipText("Đến cuối danh sách");
        prev2Button.setToolTipText("Lùi 2 bước");
        next2Button.setToolTipText("Tiến 2 bước");
    }

    private void setupLayout() {
        frame.setLayout(new BorderLayout());

        // Top panel - Search
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        searchPanel.add(new JLabel("Tìm theo:"));
        searchPanel.add(searchCombo);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);

        // Left panel - Action buttons
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        actionPanel.setBorder(BorderFactory.createTitledBorder("Thao tác"));
        actionPanel.setPreferredSize(new Dimension(150, 0));

        actionPanel.add(addButton);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        actionPanel.add(editButton);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        actionPanel.add(deleteButton);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        actionPanel.add(new JLabel("Sắp xếp:"));
        actionPanel.add(sortIdButton);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        actionPanel.add(sortNameButton);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        actionPanel.add(sortYearButton);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        actionPanel.add(new JLabel("File:"));
        actionPanel.add(importButton);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        actionPanel.add(exportButton);

        // Center panel - Table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách sinh viên"));

        // Bottom panel - Navigation
        JPanel navigationPanel = new JPanel(new FlowLayout());
        navigationPanel.setBorder(BorderFactory.createTitledBorder("Điều hướng"));
        navigationPanel.add(firstButton);
        navigationPanel.add(prev2Button);
        navigationPanel.add(prevButton);
        navigationPanel.add(nextButton);
        navigationPanel.add(next2Button);
        navigationPanel.add(lastButton);

        frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(actionPanel, BorderLayout.WEST);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(navigationPanel, BorderLayout.SOUTH);
    }

    public void updateTable(List<Student> students) {
        tableModel.setRowCount(0);
        for (Student student : students) {
            Object[] row = {
                    student.getId(),
                    student.getName(),
                    student.getAddress(),
                    student.getPhone(),
                    student.getEmail(),
                    student.getGender(),
                    student.getBirthYear(),
                    student.getAge(),
                    student.getMajor()
            };
            tableModel.addRow(row);
        }
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    // Getters for controller access
    public JFrame getFrame() {
        return frame;
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JComboBox<String> getSearchCombo() {
        return searchCombo;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JButton getSortNameButton() {
        return sortNameButton;
    }

    public JButton getSortYearButton() {
        return sortYearButton;
    }

    public JButton getSortIdButton() {
        return sortIdButton;
    }

    public JButton getImportButton() {
        return importButton;
    }

    public JButton getExportButton() {
        return exportButton;
    }

    public JButton getFirstButton() {
        return firstButton;
    }

    public JButton getPrevButton() {
        return prevButton;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public JButton getLastButton() {
        return lastButton;
    }

    public JButton getPrev2Button() {
        return prev2Button;
    }

    public JButton getNext2Button() {
        return next2Button;
    }

    // Custom renderer for alternating row colors
    private static class AlternatingRowRenderer extends JLabel implements TableCellRenderer {
        public AlternatingRowRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {

            setText(value != null ? value.toString() : "");

            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                if (row % 2 == 0) {
                    setBackground(new Color(240, 248, 255)); // Light blue for even rows
                } else {
                    setBackground(Color.WHITE); // White for odd rows
                }
                setForeground(table.getForeground());
            }

            return this;
        }
    }
}
