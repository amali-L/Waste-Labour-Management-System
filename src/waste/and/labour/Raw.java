package waste.and.labour;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * The Raw class manages the Raw Material Management module of the system.
 * It provides a graphical user interface to perform operations such as
 * adding, updating, viewing, and generating reports for raw materials.
 *
 * Responsibilities:
 * - Add new raw material records to the database
 * - Update existing material details
 * - View all stored material records in a table
 * - Generate reports based on a date range
 * - Handle navigation within the application
 *
 * OOP Concepts Used:
 * - Inheritance: Extends JFrame to create a GUI window
 * - Interface: Implements ActionListener to handle button events
 * - Polymorphism: actionPerformed() handles multiple button actions
 * - Encapsulation: clearFields() method hides field reset logic
 *
 * Data Validation:
 * - All fields must be filled before Add or Update
 * - Quantity must be a positive number
 * - Date must follow YYYY-MM-DD format
 * - Start and End dates must be valid before generating reports
 *
 * Database Features:
 * - Uses Statement for executing SQL queries
 * - Uses CallableStatement to execute stored procedures
 * - Retrieves data using ResultSet
 *
 * Exception Handling:
 * - Handles invalid input and database errors
 * - Displays meaningful messages to the user
 *
 * @author ACER
 */
public class Raw extends JFrame implements ActionListener {

    JTextField tfId, tfName, tfQuantity, tfDate;
    JTextField tfStartDate, tfEndDate;
    JButton btnAdd, btnUpdate, btnView, btnReport, btnBack;

    /**
 * Constructs the Raw Material Management window and initializes
 * all UI components including labels, text fields, and buttons.
 */
    Raw() {
        setTitle("Raw Material Management");
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ── Background image ─────────────────────────────────────────────
        ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("icons/raw.jpg"));
        Image img = bgIcon.getImage().getScaledInstance(1400, 800, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(img));
        background.setBounds(0, 0, 1400, 800);
        add(background);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 1400, 800);
        panel.setOpaque(false);
        background.add(panel);

        // ── Title ─────────────────────────────────────────────────────────
        JLabel title = new JLabel("RAW MATERIAL MANAGEMENT");
        title.setBounds(450, 30, 500, 40);
        title.setFont(new Font("Tahoma", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        panel.add(title);

        // ── Left side: Material ID, Name, Quantity, Date ──────────────────
        int leftX = 250, fieldX = 420, y = 150, gap = 70;
        JLabel l1 = new JLabel("Material ID");
        JLabel l2 = new JLabel("Material Name");
        JLabel l3 = new JLabel("Quantity");
        JLabel l4 = new JLabel("Date (YYYY-MM-DD)");
        l1.setBounds(leftX, y, 170, 30);       l1.setForeground(Color.WHITE);
        l2.setBounds(leftX, y+gap, 170, 30);   l2.setForeground(Color.WHITE);
        l3.setBounds(leftX, y+2*gap, 170, 30); l3.setForeground(Color.WHITE);
        l4.setBounds(leftX, y+3*gap, 170, 30); l4.setForeground(Color.WHITE);
        panel.add(l1); panel.add(l2); panel.add(l3); panel.add(l4);

        tfId       = new JTextField(); tfId      .setBounds(fieldX, y,       250, 35);
        tfName     = new JTextField(); tfName    .setBounds(fieldX, y+gap,   250, 35);
        tfQuantity = new JTextField(); tfQuantity.setBounds(fieldX, y+2*gap, 250, 35);
        tfDate     = new JTextField(); tfDate    .setBounds(fieldX, y+3*gap, 250, 35);
        panel.add(tfId); panel.add(tfName); panel.add(tfQuantity); panel.add(tfDate);

        // ── Right side: Start and End Date for report ─────────────────────
        int rightX = 750, rightFieldX = 900;
        JLabel l5 = new JLabel("Start Date (YYYY-MM-DD)");
        JLabel l6 = new JLabel("End Date (YYYY-MM-DD)");
        l5.setBounds(rightX, y, 200, 30);     l5.setForeground(Color.WHITE);
        l6.setBounds(rightX, y+gap, 200, 30); l6.setForeground(Color.WHITE);
        panel.add(l5); panel.add(l6);

        tfStartDate = new JTextField(); tfStartDate.setBounds(rightFieldX, y,     250, 35);
        tfEndDate   = new JTextField(); tfEndDate  .setBounds(rightFieldX, y+gap, 250, 35);
        panel.add(tfStartDate); panel.add(tfEndDate);

        // ── Buttons ───────────────────────────────────────────────────────
        int btnY = y + 4*gap + 60;
        btnAdd    = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnView   = new JButton("View");
        btnReport = new JButton("Material Report");
        btnBack   = new JButton("Back");
        btnAdd   .setBounds(300, btnY, 130, 40);
        btnUpdate.setBounds(450, btnY, 130, 40);
        btnView  .setBounds(600, btnY, 130, 40);
        btnReport.setBounds(750, btnY, 180, 40);
        btnBack  .setBounds(950, btnY, 130, 40);
        panel.add(btnAdd); panel.add(btnUpdate); panel.add(btnView);
        panel.add(btnReport); panel.add(btnBack);
        btnAdd.addActionListener(this); btnUpdate.addActionListener(this);
        btnView.addActionListener(this); btnReport.addActionListener(this);
        btnBack.addActionListener(this);

        setVisible(true);
    }

    /**
     * Handles all button click events.
     *
     * @param ae ActionEvent triggered by user interaction
     */
    public void actionPerformed(ActionEvent ae) {
        /**
 * Handles all button click events such as Add, Update, View,
 * Report, and Back operations.
 *
 * Performs validation, executes database operations,
 * and displays results to the user.
 *
 * @param ae the ActionEvent triggered by user interaction
 */
        Conn conn = new Conn();
        try {
           
            if (ae.getSource() == btnAdd) {
                String idText  = tfId.getText().trim();
                String nameText = tfName.getText().trim();
                String qtyText  = tfQuantity.getText().trim();
                String dateText = tfDate.getText().trim();

                if (Validator.isEmptyOrNull(idText) || Validator.isEmptyOrNull(nameText)
                        || Validator.isEmptyOrNull(qtyText) || Validator.isEmptyOrNull(dateText)) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields!");
                    return;
                }
                if (!Validator.isPositiveInt(qtyText)) {
                    JOptionPane.showMessageDialog(null, "Quantity must be a positive number!");
                    return;
                }
                if (!Validator.isValidDate(dateText)) {
                    JOptionPane.showMessageDialog(null, "Date must be in YYYY-MM-DD format (e.g. 2024-06-15)!");
                    return;
                }

                String query = "INSERT INTO raw_material VALUES ('"
                        + idText + "','" + nameText + "','" + qtyText + "','" + dateText + "')";
                conn.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Added Successfully");
                clearFields();
            }

         
            if (ae.getSource() == btnUpdate) {
                String idText   = tfId.getText().trim();
                String nameText = tfName.getText().trim();
                String qtyText  = tfQuantity.getText().trim();
                String dateText = tfDate.getText().trim();

                if (Validator.isEmptyOrNull(idText)) {
                    JOptionPane.showMessageDialog(null, "Material ID is required to update!");
                    return;
                }
                if (!Validator.isPositiveInt(qtyText)) {
                    JOptionPane.showMessageDialog(null, "Quantity must be a positive number!");
                    return;
                }
                if (!Validator.isValidDate(dateText)) {
                    JOptionPane.showMessageDialog(null, "Date must be in YYYY-MM-DD format!");
                    return;
                }

                String query = "UPDATE raw_material SET material_name='" + nameText
                        + "', quantity='" + qtyText
                        + "', date='" + dateText
                        + "' WHERE id='" + idText + "'";
                int rows = conn.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, rows > 0 ? "Updated Successfully" : "ID Not Found");
                clearFields();
            }

            if (ae.getSource() == btnView) {
                JFrame f = new JFrame("Records");
                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(new String[]{"ID", "Name", "Qty", "Date"});
                ResultSet rs = conn.s.executeQuery("SELECT * FROM raw_material");
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id"), rs.getString("material_name"),
                        rs.getInt("quantity"), rs.getString("date")
                    });
                }
                JTable table = new JTable(model);
                JScrollPane sp = new JScrollPane(table);
                f.add(sp); f.setSize(600, 400);
                f.setLocationRelativeTo(null); f.setVisible(true);
            }

            if (ae.getSource() == btnReport) {
                String startText = tfStartDate.getText().trim();
                String endText   = tfEndDate.getText().trim();

                
                if (!Validator.isValidDate(startText) || !Validator.isValidDate(endText)) {
                    JOptionPane.showMessageDialog(null,
                        "Enter valid Start and End dates in YYYY-MM-DD format!");
                    return;
                }

                CallableStatement cs = conn.c.prepareCall("{CALL total_raw_material_period(?, ?)}");
                cs.setDate(1, java.sql.Date.valueOf(startText));
                cs.setDate(2, java.sql.Date.valueOf(endText));
                ResultSet rs = cs.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null,
                        "Total Raw Material: " + rs.getInt("total_material"));
                }
            }

           
            if (ae.getSource() == btnBack) {
                setVisible(false);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    /**
 * Clears all input fields in the form.
 * Used after successful operations to reset the UI.
 */
    void clearFields() {
        tfId.setText(""); tfName.setText("");
        tfQuantity.setText(""); tfDate.setText("");
    }

    /**
 * Main method to launch the Raw Material Management application.
 *
 * @param args command line arguments
 */
    public static void main(String[] args) {
        new Raw();
    }
}


