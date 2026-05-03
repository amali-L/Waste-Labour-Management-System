package waste.and.labour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * The Wage class represents the Wage Management form of the system.
 * It allows users to calculate labour wages, store records in the database,
 * and generate reports.
 *
 * OOP Concepts Used:
 * - Inheritance: This class extends BaseForm to reuse window setup.
 * - Method Overriding: Overrides getFormTitle(), initUI(), and clearFields().
 * - Constructor Chaining: Uses super() to call BaseForm constructor.
 * - Encapsulation: Uses WageRecor class to calculate and protect salary data.
 *
 * This class handles user interaction, validation, salary calculation,
 * database operations, and report generation.
 *
 * @author
 *     Your Name
 */
public class Wage extends BaseForm implements ActionListener {

    /** Text fields for input and output */
    JTextField tfName, tfDays, tfWage, tfTotal;

    /** Buttons for user actions */
    JButton btnCalculate, btnReport, btnBack;

    /**
     * Constructs the Wage form.
     * Calls the parent constructor and initializes UI components.
     */
    Wage() {
        super();
        initUI();
        setVisible(true);
    }

    /**
     * Returns the title of the form.
     *
     * @return "Wage Management"
     */
    @Override
    public String getFormTitle() {
        return "Wage Management";
    }

    /**
     * Initializes and builds all UI components of the Wage form.
     */
    @Override
    public void initUI() {

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/s.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1400, 800, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(i2));
        background.setBounds(0, 0, 1400, 800);
        add(background);
        background.setLayout(null);

        JLabel title = new JLabel("WAGE MANAGEMENT");
        title.setBounds(550, 40, 400, 40);
        title.setFont(new Font("Tahoma", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        background.add(title);

        JLabel l0 = new JLabel("Labour Name");
        JLabel l1 = new JLabel("Days Worked");
        JLabel l2 = new JLabel("Wage / Day");
        JLabel l3 = new JLabel("Total Salary");

        l0.setBounds(400, 150, 150, 30);
        l1.setBounds(400, 220, 150, 30);
        l2.setBounds(400, 290, 150, 30);
        l3.setBounds(400, 360, 150, 30);

        l0.setForeground(Color.WHITE);
        l1.setForeground(Color.WHITE);
        l2.setForeground(Color.WHITE);
        l3.setForeground(Color.WHITE);

        background.add(l0);
        background.add(l1);
        background.add(l2);
        background.add(l3);

        tfName  = new JTextField();
        tfDays  = new JTextField();
        tfWage  = new JTextField();
        tfTotal = new JTextField();

        tfName.setBounds(600, 150, 300, 30);
        tfDays.setBounds(600, 220, 300, 30);
        tfWage.setBounds(600, 290, 300, 30);
        tfTotal.setBounds(600, 360, 300, 30);
        tfTotal.setEditable(false);

        background.add(tfName);
        background.add(tfDays);
        background.add(tfWage);
        background.add(tfTotal);

        btnCalculate = new JButton("Calculate");
        btnReport    = new JButton("Report");
        btnBack      = new JButton("Back");

        btnCalculate.setBounds(400, 450, 150, 40);
        btnReport.setBounds(600, 450, 150, 40);
        btnBack.setBounds(800, 450, 150, 40);

        btnCalculate.addActionListener(this);
        btnReport.addActionListener(this);
        btnBack.addActionListener(this);

        background.add(btnCalculate);
        background.add(btnReport);
        background.add(btnBack);
    }

    /**
     * Clears all input fields in the form.
     */
    @Override
    public void clearFields() {
        tfName.setText("");
        tfDays.setText("");
        tfWage.setText("");
        tfTotal.setText("");
    }

    /**
     * Handles button click events.
     *
     * Performs validation, calculates salary using WageRecor,
     * stores data in the database, and generates reports.
     *
     * @param ae the action event triggered by button click
     */
    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == btnCalculate) {

            String nameText = tfName.getText().trim();
            String daysText = tfDays.getText().trim();
            String wageText = tfWage.getText().trim();

            if (Validator.isEmptyOrNull(nameText)) {
                JOptionPane.showMessageDialog(null, "Labour Name cannot be empty!");
                return;
            }

            if (!Validator.isPositiveInt(daysText)) {
                JOptionPane.showMessageDialog(null, "Days must be a positive number!");
                return;
            }

            if (!Validator.isPositiveInt(wageText)) {
                JOptionPane.showMessageDialog(null, "Wage must be a positive number!");
                return;
            }

            WageRecor record = new WageRecor(
                    nameText,
                    Integer.parseInt(daysText),
                    Integer.parseInt(wageText)
            );

            tfTotal.setText(String.valueOf(record.getTotalSalary()));

            try {
                Conn con = new Conn();

                String query = "INSERT INTO wage(labour_name, days_worked, wage_per_day, total_salary) VALUES('"
                        + record.getName() + "', "
                        + record.getDaysWorked() + ", "
                        + record.getWagePerDay() + ", "
                        + record.getTotalSalary() + ")";

                con.s.executeUpdate(query);

                JOptionPane.showMessageDialog(null, "Saved Successfully!");
                clearFields();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }

        if (ae.getSource() == btnReport) {
            try {
                Conn con = new Conn();
                ResultSet rs = con.s.executeQuery("SELECT * FROM wage");

                String[] columnNames = {"ID", "Name", "Days", "Wage/Day", "Total"};
                javax.swing.table.DefaultTableModel model =
                        new javax.swing.table.DefaultTableModel(columnNames, 0);

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("labour_name"),
                            rs.getInt("days_worked"),
                            rs.getInt("wage_per_day"),
                            rs.getInt("total_salary")
                    });
                }

                JTable table = new JTable(model);
                table.setRowHeight(25);

                JScrollPane scrollPane = new JScrollPane(table);
                JFrame reportFrame = new JFrame("Wage Report");

                reportFrame.setSize(900, 500);
                reportFrame.setLocationRelativeTo(null);
                reportFrame.add(scrollPane);
                reportFrame.setVisible(true);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }

        if (ae.getSource() == btnBack) {
            setVisible(false);
        }
    }

    /**
     * Main method to run the Wage form.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        new Wage();
    }
}