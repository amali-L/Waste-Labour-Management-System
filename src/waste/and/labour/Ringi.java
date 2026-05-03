package waste.and.labour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * The Ringi class manages the Ring Inventory module of the system.
 * It provides a graphical interface to perform operations such as
 * adding, updating, checking, and generating reports for ring inventory.
 *
 * Responsibilities:
 * - Add new ring records into the database
 * - Update existing ring stock
 * - Search and display ring details
 * - Generate reports based on date range
 * - Display total stock and ring types
 *
 * OOP Concepts Used:
 * - Inheritance: Extends JFrame to create a GUI window
 * - Interface: Implements ActionListener to handle button events
 * - Polymorphism: actionPerformed() handles multiple button actions
 * - Encapsulation: clearFields() method hides reset logic inside the class
 *
 * Data Validation:
 * - Ring ID must be a positive integer
 * - Quantity must be a positive integer
 * - Dates must follow YYYY-MM-DD format
 * - Start and End dates must be valid before generating reports
 *
 * Database Features:
 * - Uses PreparedStatement for secure queries
 * - Uses CallableStatement to execute stored procedures
 * - Retrieves and updates data using ResultSet
 *
 * Exception Handling:
 * - Handles invalid number formats
 * - Handles incorrect date formats
 * - Catches and displays database errors
 *
 * @author ACER
 */
public class Ringi extends JFrame implements ActionListener {

    JTextField tfRingId, tfRingType, tfQuantity, tfDate;
    JTextField tfStartDate, tfEndDate;
    JButton btnAdd, btnUpdate, btnCheck, btnReport, btnStock, btnBack;

   /**
 * Constructs the Ringi window and initializes all UI components.
 * Sets layout, adds form fields, buttons, and images.
 */
    Ringi() {
        setTitle("Ring Inventory Management");
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       
        ImageIcon imgIcon = new ImageIcon("src/icons/a1.jpg");
        Image img = imgIcon.getImage().getScaledInstance(700, 800, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img));
        imageLabel.setBounds(700, 0, 700, 800);
        add(imageLabel);

      
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 700, 800);
        panel.setBackground(new Color(245, 245, 245));
        add(panel);

        JLabel title = new JLabel("RING INVENTORY");
        title.setBounds(220, 40, 300, 40);
        title.setFont(new Font("Tahoma", Font.BOLD, 24));
        panel.add(title);

       
        int labelX = 100, fieldX = 250, y = 120, gap = 60;
        JLabel l1 = new JLabel("Ring ID");
        JLabel l2 = new JLabel("Ring Type");
        JLabel l3 = new JLabel("Quantity");
        JLabel l4 = new JLabel("Date (YYYY-MM-DD)");
        JLabel l5 = new JLabel("Start Date");
        JLabel l6 = new JLabel("End Date");
        l1.setBounds(labelX, y, 140, 30);      l2.setBounds(labelX, y+gap, 140, 30);
        l3.setBounds(labelX, y+2*gap, 140, 30); l4.setBounds(labelX, y+3*gap, 140, 30);
        l5.setBounds(labelX, y+4*gap, 140, 30); l6.setBounds(labelX, y+5*gap, 140, 30);
        panel.add(l1); panel.add(l2); panel.add(l3);
        panel.add(l4); panel.add(l5); panel.add(l6);

        
        tfRingId    = new JTextField(); tfRingId   .setBounds(fieldX, y,       250, 30);
        tfRingType  = new JTextField(); tfRingType .setBounds(fieldX, y+gap,   250, 30);
        tfQuantity  = new JTextField(); tfQuantity .setBounds(fieldX, y+2*gap, 250, 30);
        tfDate      = new JTextField(); tfDate     .setBounds(fieldX, y+3*gap, 250, 30);
        tfStartDate = new JTextField(); tfStartDate.setBounds(fieldX, y+4*gap, 250, 30);
        tfEndDate   = new JTextField(); tfEndDate  .setBounds(fieldX, y+5*gap, 250, 30);
        panel.add(tfRingId); panel.add(tfRingType);
        panel.add(tfQuantity); panel.add(tfDate);
        panel.add(tfStartDate); panel.add(tfEndDate);

        
        int btnY1 = y + 6*gap + 20, btnY2 = btnY1 + 60;
        btnAdd    = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnCheck  = new JButton("Check");
        btnReport = new JButton("Ring Report");
        btnStock  = new JButton("Stock Report");
        btnBack   = new JButton("Back");
        btnAdd   .setBounds(100, btnY1, 120, 40); btnUpdate.setBounds(240, btnY1, 120, 40);
        btnCheck .setBounds(380, btnY1, 120, 40); btnReport.setBounds(100, btnY2, 180, 40);
        btnStock .setBounds(300, btnY2, 180, 40); btnBack  .setBounds(230, btnY2+60, 150, 40);
        panel.add(btnAdd); panel.add(btnUpdate); panel.add(btnCheck);
        panel.add(btnReport); panel.add(btnStock); panel.add(btnBack);
        btnAdd.addActionListener(this); btnUpdate.addActionListener(this);
        btnCheck.addActionListener(this); btnReport.addActionListener(this);
        btnStock.addActionListener(this); btnBack.addActionListener(this);

        setVisible(true);
    }

    /**
 * Handles button click events for all operations:
 * Add, Update, Check, Report, Stock, and Back.
 *
 * Performs validation, executes database operations,
 * and displays appropriate messages to the user.
 *
 * @param ae the ActionEvent triggered by user interaction
 */
    public void actionPerformed(ActionEvent ae) {
        
        Conn con = new Conn();
        try {

           
            if (ae.getSource() == btnAdd) {
                String idText  = tfRingId.getText().trim();
                String qtyText = tfQuantity.getText().trim();
                String dateText = tfDate.getText().trim();

              
                if (Validator.isEmptyOrNull(idText) || Validator.isEmptyOrNull(qtyText)
                        || Validator.isEmptyOrNull(dateText)) {
                    JOptionPane.showMessageDialog(null, "Ring ID, Quantity and Date are required!");
                    return;
                }
                if (!Validator.isPositiveInt(idText)) {
                    JOptionPane.showMessageDialog(null, "Ring ID must be a positive number!");
                    return;
                }
                if (!Validator.isPositiveInt(qtyText)) {
                    JOptionPane.showMessageDialog(null, "Quantity must be a positive number!");
                    return;
                }
                if (!Validator.isValidDate(dateText)) {
                    JOptionPane.showMessageDialog(null, "Date must be YYYY-MM-DD format (e.g. 2024-06-15)!");
                    return;
                }

                String query = "INSERT INTO ring_inventory (id, ring_type, quantity, created_date) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = con.c.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(idText));
                ps.setString(2, tfRingType.getText());
                ps.setInt(3, Integer.parseInt(qtyText));
                ps.setDate(4, java.sql.Date.valueOf(dateText));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Ring Added Successfully!");
                clearFields();
            }

         
            if (ae.getSource() == btnUpdate) {
                if (!Validator.isPositiveInt(tfRingId.getText().trim())) {
                    JOptionPane.showMessageDialog(null, "Enter a valid Ring ID to update!");
                    return;
                }
                if (!Validator.isPositiveInt(tfQuantity.getText().trim())) {
                    JOptionPane.showMessageDialog(null, "Quantity must be a positive number!");
                    return;
                }
                String query = "UPDATE ring_inventory SET quantity=? WHERE ring_id=?";
                PreparedStatement ps = con.c.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(tfQuantity.getText().trim()));
                ps.setInt(2, Integer.parseInt(tfRingId.getText().trim()));
                int rows = ps.executeUpdate();
                JOptionPane.showMessageDialog(null,
                    rows > 0 ? "Stock Updated!" : "Ring ID not found!");
            }

       
            if (ae.getSource() == btnCheck) {
                if (!Validator.isPositiveInt(tfRingId.getText().trim())) {
                    JOptionPane.showMessageDialog(null, "Enter a valid Ring ID to search!");
                    return;
                }
                String query = "SELECT * FROM ring_inventory WHERE ring_id=?";
                PreparedStatement ps = con.c.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(tfRingId.getText().trim()));
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    tfRingType.setText(rs.getString("ring_type"));
                    tfQuantity.setText(String.valueOf(rs.getInt("quantity")));
                    tfDate.setText(rs.getDate("created_date").toString());
                } else {
                    JOptionPane.showMessageDialog(null, "No Record Found");
                }
            }

          
            if (ae.getSource() == btnReport) {
                if (!Validator.isValidDate(tfStartDate.getText().trim())
                        || !Validator.isValidDate(tfEndDate.getText().trim())) {
                    JOptionPane.showMessageDialog(null, "Enter valid Start and End dates (YYYY-MM-DD)!");
                    return;
                }
                CallableStatement cs = con.c.prepareCall("{CALL total_rings_period(?, ?)}");
                cs.setDate(1, java.sql.Date.valueOf(tfStartDate.getText().trim()));
                cs.setDate(2, java.sql.Date.valueOf(tfEndDate.getText().trim()));
                ResultSet rs = cs.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null,
                        "Total Ring Types\nFrom: " + tfStartDate.getText()
                        + "\nTo: " + tfEndDate.getText()
                        + "\nTotal = " + rs.getInt("total_types"));
                }
            }

            if (ae.getSource() == btnStock) {
                if (!Validator.isValidDate(tfStartDate.getText().trim())
                        || !Validator.isValidDate(tfEndDate.getText().trim())) {
                    JOptionPane.showMessageDialog(null, "Enter valid Start and End dates (YYYY-MM-DD)!");
                    return;
                }
                CallableStatement cs = con.c.prepareCall("{CALL total_stock_period(?, ?)}");
                cs.setDate(1, java.sql.Date.valueOf(tfStartDate.getText().trim()));
                cs.setDate(2, java.sql.Date.valueOf(tfEndDate.getText().trim()));
                ResultSet rs = cs.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null,
                        "Total Stock\nFrom: " + tfStartDate.getText()
                        + "\nTo: " + tfEndDate.getText()
                        + "\nTotal = " + rs.getInt("total_available_stock"));
                }
            }

         
            if (ae.getSource() == btnBack) {
                setVisible(false);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Enter valid numbers in numeric fields!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Use date format: YYYY-MM-DD");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

  
    void clearFields() {
        tfRingId.setText(""); tfRingType.setText("");
        tfQuantity.setText(""); tfDate.setText("");
        tfStartDate.setText(""); tfEndDate.setText("");
    }

   
    public static void main(String[] args) {
        /**
 * Main method to launch the Ring Inventory application.
 *
 * @param args command line arguments
 */
        new Ringi();
    }
}

