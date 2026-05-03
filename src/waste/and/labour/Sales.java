package waste.and.labour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * The Sales class manages the Sales & Reporting module of the system.
 *
 * OOP Concepts Used:
 * - Inheritance: Extends BaseForm (BaseForm extends JFrame) to create a GUI window.
 *                Gets setupWindow() for free — no repeated setTitle/setSize/etc.
 * - Interface: Implements ActionListener to handle button events
 * - Polymorphism: actionPerformed() handles Calculate, Sales Report, and Back
 * - Method Overriding: getFormTitle(), initUI(), clearFields() override BaseForm's abstract methods
 *
 * Validation Rules:
 * - Rings Sold must be a positive number
 * - Price per Ring must be a positive number
 * - Start and End dates required and must be valid YYYY-MM-DD
 *
 * Exception Handling:
 * - Handles database errors during save
 * - Handles invalid date format during report
 *
 * @author ACER
 */
public class Sales extends BaseForm implements ActionListener {

    JTextField tfRings, tfPrice, tfTotal, tfStartDate, tfEndDate;
    JButton btnCalculate, btnSalesReport, btnBack;

    /**
     * Constructor — CONSTRUCTOR CHAINING:
     *   Sales() → super() → BaseForm() → setupWindow() → getFormTitle()
     *
     * BaseForm.setupWindow() handles:
     *   setTitle("Sales & Reporting")  [via getFormTitle()]
     *   setSize(1400, 800)
     *   setLocationRelativeTo(null)
     *   setLayout(null)
     *   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
     */
    Sales() {
        super();     // calls BaseForm() → setupWindow() → sets title, size, location
        initUI();    // builds all UI components for this form
        setVisible(true);
    }

    /**
     * METHOD OVERRIDING — returns this form's window title.
     * Called automatically by BaseForm.setupWindow().
     */
    @Override
    public String getFormTitle() {
        return "Sales & Reporting";
    }

    /**
     * METHOD OVERRIDING — builds all Sales form UI components.
     * Every line below is UNCHANGED from the original Sales() constructor body.
     */
    @Override
    public void initUI() {

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/w1.png"));
        Image i2 = i1.getImage().getScaledInstance(1400, 800, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(i2));
        background.setBounds(0, 0, 1400, 800);
        add(background);

        JLabel title = new JLabel("SALES & REPORTING");
        title.setBounds(500, 40, 500, 40);
        title.setFont(new Font("Tahoma", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(JLabel.CENTER);
        background.add(title);

        JLabel l1 = new JLabel("Rings Sold");
        JLabel l2 = new JLabel("Price per Ring");
        JLabel l3 = new JLabel("Total Sales");
        l1.setBounds(350, 180, 150, 30); l1.setForeground(Color.WHITE);
        l2.setBounds(350, 240, 150, 30); l2.setForeground(Color.WHITE);
        l3.setBounds(350, 300, 150, 30); l3.setForeground(Color.WHITE);
        background.add(l1); background.add(l2); background.add(l3);

        JLabel l4 = new JLabel("Start Date (YYYY-MM-DD)");
        JLabel l5 = new JLabel("End Date (YYYY-MM-DD)");
        l4.setBounds(800, 180, 200, 30); l4.setForeground(Color.WHITE);
        l5.setBounds(800, 240, 200, 30); l5.setForeground(Color.WHITE);
        background.add(l4); background.add(l5);

        tfRings = new JTextField(); tfRings.setBounds(520, 180, 250, 30);
        tfPrice = new JTextField(); tfPrice.setBounds(520, 240, 250, 30);
        tfTotal = new JTextField(); tfTotal.setBounds(520, 300, 250, 30);
        tfTotal.setEditable(false); // auto-calculated
        background.add(tfRings); background.add(tfPrice); background.add(tfTotal);

        tfStartDate = new JTextField(); tfStartDate.setBounds(1020, 180, 200, 30);
        tfEndDate   = new JTextField(); tfEndDate  .setBounds(1020, 240, 200, 30);
        background.add(tfStartDate); background.add(tfEndDate);

        btnCalculate   = new JButton("Calculate");
        btnSalesReport = new JButton("Sales Report");
        btnBack        = new JButton("Back");

        btnCalculate  .setBounds(450, 420, 150, 40);
        btnSalesReport.setBounds(650, 420, 170, 40);
        btnBack       .setBounds(870, 420, 150, 40);

        btnCalculate.addActionListener(this);
        btnSalesReport.addActionListener(this);
        btnBack.addActionListener(this);

        background.add(btnCalculate); background.add(btnSalesReport); background.add(btnBack);
    }

    /**
     * Handles button click events.
     *
     * If "Calculate" is clicked:
     * - Validates input fields
     * - Calculates total sales and saves to database
     *
     * If "Sales Report" is clicked:
     * - Validates date range
     * - Calls stored procedure and shows total sales
     *
     * If "Back" is clicked:
     * - Hides the Sales window
     *
     * @param ae the ActionEvent triggered by user interaction
     */
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == btnCalculate) {

            String ringsText = tfRings.getText().trim();
            String priceText = tfPrice.getText().trim();

            if (!Validator.isPositiveInt(ringsText)) {
                JOptionPane.showMessageDialog(null, "Rings Sold must be a positive number!");
                return;
            }
            if (!Validator.isPositiveInt(priceText)) {
                JOptionPane.showMessageDialog(null, "Price per Ring must be a positive number!");
                return;
            }

            int rings = Integer.parseInt(ringsText);
            int price = Integer.parseInt(priceText);
            int total = rings * price;
            tfTotal.setText(String.valueOf(total));

            try {
                Conn con = new Conn();
                String ringType = "Steel Ring";
                String date = java.time.LocalDate.now().toString();
                String query = "INSERT INTO sales(ring_type, quantity, total_price, date) VALUES('"
                        + ringType + "', " + rings + ", " + total + ", '" + date + "')";
                con.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Sales Saved to Database!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }

        if (ae.getSource() == btnSalesReport) {

            String startText = tfStartDate.getText().trim();
            String endText   = tfEndDate.getText().trim();

            if (!Validator.isValidDate(startText) || !Validator.isValidDate(endText)) {
                JOptionPane.showMessageDialog(null,
                    "Enter valid Start and End dates in YYYY-MM-DD format (e.g. 2024-01-01)!");
                return;
            }

            try {
                Conn con = new Conn();
                CallableStatement cs = con.c.prepareCall("{CALL total_sales_amount_period(?, ?)}");
                cs.setDate(1, java.sql.Date.valueOf(startText));
                cs.setDate(2, java.sql.Date.valueOf(endText));
                ResultSet rs = cs.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null,
                        "Total Sales: " + rs.getInt("total_sales"));
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error fetching sales report: " + e.getMessage());
            }
        }

        if (ae.getSource() == btnBack) {
            setVisible(false);
        }
    }

    /**
     * METHOD OVERRIDING — clears all Sales form input fields.
     * Required by BaseForm's abstract method contract.
     */
    @Override
    public void clearFields() {
        tfRings.setText("");
        tfPrice.setText("");
        tfTotal.setText("");
        tfStartDate.setText("");
        tfEndDate.setText("");
    }

    /**
     * Main method to start the Sales application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        new Sales();
    }
}

