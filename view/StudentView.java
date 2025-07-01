package view;

import model.Student;

import javax.swing.*;
import javax.swing.table.*;
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
    private JButton importButton, exportButton;
    private JButton firstButton, prevButton, nextButton, lastButton;

    public StudentView() {
        initializeComponents();
        setupLayout();
        setupTableFormatting();
    }

    private void initializeComponents() {
        frame = new JFrame("Hệ thống quản lý sinh viên");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null);

        // Create table model
        String[] columnNames = {"ID", "Họ tên", "Địa chỉ", "Số điện thoại", "Email", "Giới tính", "Năm sinh", "Tuổi", "Chuyên ngành"};
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
        table = new JTable(tableModel);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        // Alternate row colors
        table.setDefaultRenderer(Object.class, new AlternatingRowRenderer());

        // Search components
        searchField = new JTextField(20);
        searchCombo = new JComboBox<>(new String[]{"Họ tên", "Địa chỉ", "Năm sinh", "Chuyên ngành"});

        // Initialize buttons
        addButton = new JButton("Thêm");
        editButton = new JButton("Sửa");
        deleteButton = new JButton("Xóa");
        searchButton = new JButton("Tìm kiếm");
        refreshButton = new JButton("Làm mới");

        importButton = new JButton("Nhập CSV");
        exportButton = new JButton("Xuất CSV");

        firstButton = new JButton("<<");
        prevButton = new JButton("<");
        nextButton = new JButton(">");
        lastButton = new JButton(">>");


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

    }

    private void setupTableFormatting() {
        // Thiết lập độ rộng cột phù hợp
        int[] columnWidths = {50, 150, 200, 120, 180, 80, 80, 50, 150};
        for (int i = 0; i < columnWidths.length && i < table.getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
            column.setMinWidth(columnWidths[i] - 20);
            column.setMaxWidth(columnWidths[i] + 50);
        }

        // Căn giữa cho các cột số
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Áp dụng căn lề cho ID, Tên, Địa chỉ, Số điện thoại, Email, Giới tính, Năm sinh, Tuổi, Chuyên ngành
        table.getColumnModel().getColumn(0).setCellRenderer(new LeftAlignedRenderer()); // ID
        table.getColumnModel().getColumn(1).setCellRenderer(new LeftAlignedRenderer()); // Tên
        table.getColumnModel().getColumn(2).setCellRenderer(new LeftAlignedRenderer()); // Địa chỉ
        table.getColumnModel().getColumn(3).setCellRenderer(new LeftAlignedRenderer()); // Số điện thoại
        table.getColumnModel().getColumn(4).setCellRenderer(new LeftAlignedRenderer()); // Email
        table.getColumnModel().getColumn(5).setCellRenderer(new LeftAlignedRenderer()); // Giới tính
        table.getColumnModel().getColumn(6).setCellRenderer(new LeftAlignedRenderer()); // Năm sinh
        table.getColumnModel().getColumn(7).setCellRenderer(new LeftAlignedRenderer()); // Tuổi
        table.getColumnModel().getColumn(8).setCellRenderer(new LeftAlignedRenderer()); // Chuyên ngành

        // Thiết lập font cho bảng
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        // Thiết lập màu cho header
        table.getTableHeader().setBackground(new Color(70, 130, 180));
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
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
        navigationPanel.add(prevButton);
        navigationPanel.add(nextButton);
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
        // Refresh table display
        table.revalidate();
        table.repaint();
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

    // Renderer cải tiến cho alternating rows
    private static class ImprovedAlternatingRowRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Đảm bảo text không null
            setText(value != null ? value.toString().trim() : "");

            if (isSelected) {
                setBackground(new Color(51, 153, 255));
                setForeground(Color.WHITE);
            } else {
                if (row % 2 == 0) {
                    setBackground(new Color(245, 250, 255)); // Light blue cho hàng chẵn
                } else {
                    setBackground(Color.WHITE); // White cho hàng lẻ
                }
                setForeground(Color.BLACK);
            }

            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            return this;
        }
    }

    // Renderer căn giữa
    private static class CenteredRenderer extends ImprovedAlternatingRowRenderer {
        public CenteredRenderer() {
            setHorizontalAlignment(CENTER);
        }
    }

    // Renderer căn trái
    private static class LeftAlignedRenderer extends ImprovedAlternatingRowRenderer {
        public LeftAlignedRenderer() {
            setHorizontalAlignment(LEFT);
        }
    }
}
