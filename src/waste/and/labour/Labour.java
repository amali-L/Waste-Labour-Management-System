package waste.and.labour;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * The Labour class manages the Labour Management module of the system.
 * It provides a graphical interface to add, update, and view labour records.
 *
 * Responsibilities:
 * - Add new labour records into the database
 * - Update existing labour details
 * - View all labour records in a table format
 * - Handle user interaction through buttons
 *
 * OOP Concepts Used:
 * - Inheritance: Extends BaseForm to reuse common window setup
 * - Method Overriding: Overrides getFormTitle(), initUI(), and clearFields()
 * - Encapsulation: Uses LabourRecord class to store and manage data securely
 * - Constructor Chaining: Calls parent constructor using super()
 *
 * Data Validation:
 * - Name and Skill fields must not be empty
 * - Wage must be a positive number
 *
 * Database Features:
 * - Uses PreparedStatement for secure insert and update operations
 * - Uses ResultSet to fetch and display records
 *
 * Exception Handling:
 * - Catches and displays errors during database operations
 *
 * @author ACER
 */
public class Labour extends BaseForm implements ActionListener {

    JTextField tfId, tfName, tfSkill, tfWage;
    JButton btnAdd, btnUpdate, btnView, btnBack;
/**
 * Constructs the Labour Management window.
 * Calls the parent constructor and initializes UI components.
 */
    Labour() {
        super();    
        initUI();
        setVisible(true);
    }

    @Override
    public String getFormTitle() { return "Labour Management"; }

    @Override
    public void initUI() {
        /**
 * Initializes and builds all UI components such as labels,
 * text fields, buttons, and background styling.
 */
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/steel_worker2.png"));
        Image i2 = i1.getImage().getScaledInstance(1400, 800, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(i2));
        background.setBounds(0, 0, 1400, 800);
        add(background);

        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(255, 255, 255, 150));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
            }
        };
        panel.setLayout(null);
        panel.setBounds(450, 150, 500, 450);
        panel.setOpaque(false);
        background.add(panel);

        JLabel title = new JLabel("LABOUR MANAGEMENT");
        title.setBounds(120, 30, 300, 35);
        title.setFont(new Font("Tahoma", Font.BOLD, 22));
        panel.add(title);

        int labelX = 60, fieldX = 220, y = 100, gap = 70;
        JLabel l1 = new JLabel("Labour ID");  l1.setBounds(labelX, y,       150, 30);
        JLabel l2 = new JLabel("Name");        l2.setBounds(labelX, y+gap,   150, 30);
        JLabel l3 = new JLabel("Skill Type");  l3.setBounds(labelX, y+2*gap, 150, 30);
        JLabel l4 = new JLabel("Wage Rate");   l4.setBounds(labelX, y+3*gap, 150, 30);
        panel.add(l1); panel.add(l2); panel.add(l3); panel.add(l4);

        tfId    = new JTextField(); tfId   .setBounds(fieldX, y,       200, 35);
        tfName  = new JTextField(); tfName .setBounds(fieldX, y+gap,   200, 35);
        tfSkill = new JTextField(); tfSkill.setBounds(fieldX, y+2*gap, 200, 35);
        tfWage  = new JTextField(); tfWage .setBounds(fieldX, y+3*gap, 200, 35);
        panel.add(tfId); panel.add(tfName); panel.add(tfSkill); panel.add(tfWage);

        int btnY = y + 4*gap + 40;
        btnAdd    = new JButton("Add");    btnAdd   .setBounds(60,  btnY,    120, 40);
        btnUpdate = new JButton("Update"); btnUpdate.setBounds(190, btnY,    130, 40);
        btnView   = new JButton("View");   btnView  .setBounds(330, btnY,    100, 40);
        btnBack   = new JButton("Back");   btnBack  .setBounds(190, btnY+70, 120, 40);
        panel.add(btnAdd); panel.add(btnUpdate); panel.add(btnView); panel.add(btnBack);
        btnAdd.addActionListener(this); btnUpdate.addActionListener(this);
        btnView.addActionListener(this); btnBack.addActionListener(this);
    }

    @Override
    public void clearFields() {
        tfId.setText(""); tfName.setText("");
        tfSkill.setText(""); tfWage.setText("");
    }

    public void actionPerformed(ActionEvent ae) {
        /**
 * Handles button click events for Add, Update, View, and Back operations.
 *
 * Performs validation, interacts with the database,
 * and displays results to the user.
 *
 * @param ae the ActionEvent triggered by user interaction
 */
        try {
            Conn con = new Conn();

            if (ae.getSource() == btnAdd) {
                if (Validator.isEmptyOrNull(tfName.getText()) || Validator.isEmptyOrNull(tfSkill.getText())) {
                    JOptionPane.showMessageDialog(null, "Name and Skill cannot be empty!"); return;
                }
                if (!Validator.isPositiveInt(tfWage.getText().trim())) {
                    JOptionPane.showMessageDialog(null, "Wage must be a positive number!"); return;
                }

                // ── ENCAPSULATION: create LabourRecord object ─────────────
                // Data is stored and validated inside LabourRecord.
                // We read back using getters to build the SQL query.
                LabourRecord record = new LabourRecord(0,
                    tfName.getText().trim(), tfSkill.getText().trim(),
                    Integer.parseInt(tfWage.getText().trim()));

                String query = "INSERT INTO labour(name, skill, wage_rate) VALUES (?, ?, ?)";
                PreparedStatement ps = con.c.prepareStatement(query);
                ps.setString(1, record.getName());
                ps.setString(2, record.getSkill());
                ps.setInt(3, record.getWageRate());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Labour Added Successfully");
                clearFields();
            }

            if (ae.getSource() == btnUpdate) {
                if (Validator.isEmptyOrNull(tfId.getText())) {
                    JOptionPane.showMessageDialog(null, "Enter ID to Update!"); return;
                }
                if (!Validator.isPositiveInt(tfWage.getText().trim())) {
                    JOptionPane.showMessageDialog(null, "Wage must be a positive number!"); return;
                }
                int id = Integer.parseInt(tfId.getText().trim());
                LabourRecord record = new LabourRecord(id,
                    tfName.getText().trim(), tfSkill.getText().trim(),
                    Integer.parseInt(tfWage.getText().trim()));

                String query = "UPDATE labour SET name=?, skill=?, wage_rate=? WHERE id=?";
                PreparedStatement ps = con.c.prepareStatement(query);
                ps.setString(1, record.getName());
                ps.setString(2, record.getSkill());
                ps.setInt(3, record.getWageRate());
                ps.setInt(4, record.getId());
                int rows = ps.executeUpdate();
                JOptionPane.showMessageDialog(null, rows > 0 ? "Updated Successfully" : "ID Not Found");
                clearFields();
            }

            if (ae.getSource() == btnView) {
                JFrame f = new JFrame("Labour Records");
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(new String[]{"ID", "Name", "Skill", "Wage"});
                ResultSet rs = con.s.executeQuery("SELECT * FROM labour");
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id"), rs.getString("name"),
                        rs.getString("skill"), rs.getInt("wage_rate")
                    });
                }
                JTable table = new JTable(model);
                JScrollPane sp = new JScrollPane(table);
                f.add(sp); f.setSize(700, 400);
                f.setLocationRelativeTo(null); f.setVisible(true);
            }

            if (ae.getSource() == btnBack) { setVisible(false); }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
/**
 * Main method to launch the Labour Management application.
 *
 * @param args command line arguments
 */
    public static void main(String[] args) { new Labour(); }
}

