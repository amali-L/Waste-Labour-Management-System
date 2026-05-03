package waste.and.labour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * Waste form — manages waste calculation and reporting.
 *
 * OOP CONCEPTS:
 * ─────────────────────────────────────────────────────────────────────────
 * 1. INHERITANCE   — "extends BaseForm" (BaseForm already extends JFrame)
 *                    Waste gets setupWindow() for free from BaseForm.
 *
 * 2. METHOD OVERRIDING (@Override)
 *    ► getFormTitle() — returns "Waste Management" (Waste's own version)
 *    ► initUI()       — builds the Waste-specific form fields
 *    ► clearFields()  — clears the Waste form's text fields
 *
 * 3. ENCAPSULATION (WasteRecord)
 *    ► When saving, we create a WasteRecord object.
 *    ► We read values using getters: record.getWasteAmount()
 *      instead of doing the calculation manually in the button handler.
 */
public class Waste extends BaseForm implements ActionListener {

    JTextField tfMaterial, tfTotal, tfUsed, tfWaste;
    JButton btnCalculate, btnReport, btnBack;

    /**
     * Constructor — calls super() which runs BaseForm's setupWindow().
     * Then calls initUI() to build the form.
     */
    Waste() {
        super();    // ← calls BaseForm() → setupWindow() → sets title, size, location
        initUI();   // ← builds the form components
        setVisible(true);
    }

    /**
     * METHOD OVERRIDING — overrides BaseForm's abstract getFormTitle().
     * The @Override annotation tells the compiler to verify we are
     * correctly overriding a parent method (catches typos in method names).
     *
     * @return "Waste Management" as the window title
     */
    @Override
    public String getFormTitle() {
        return "Waste Management";
    }

    /**
     * METHOD OVERRIDING — overrides BaseForm's abstract initUI().
     * Builds all the Waste form components: labels, fields, buttons.
     */
    @Override
    public void initUI() {
        // Background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/a4.png"));
        Image i2 = i1.getImage().getScaledInstance(1400, 800, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(i2));
        background.setBounds(0, 0, 1400, 800);
        add(background);
        background.setLayout(null);

        JLabel heading = new JLabel("Waste Management");
        heading.setBounds(550, 40, 400, 40);
        heading.setFont(new Font("Arial", Font.BOLD, 30));
        heading.setForeground(Color.WHITE);
        background.add(heading);

        JLabel lblMaterial = new JLabel("Material Name:");
        lblMaterial.setBounds(400, 150, 150, 30);
        lblMaterial.setForeground(Color.WHITE);
        background.add(lblMaterial);
        tfMaterial = new JTextField();
        tfMaterial.setBounds(600, 150, 300, 30);
        background.add(tfMaterial);

        JLabel lblTotal = new JLabel("Total Material:");
        lblTotal.setBounds(400, 220, 150, 30);
        lblTotal.setForeground(Color.WHITE);
        background.add(lblTotal);
        tfTotal = new JTextField();
        tfTotal.setBounds(600, 220, 300, 30);
        background.add(tfTotal);

        JLabel lblUsed = new JLabel("Used Material:");
        lblUsed.setBounds(400, 290, 150, 30);
        lblUsed.setForeground(Color.WHITE);
        background.add(lblUsed);
        tfUsed = new JTextField();
        tfUsed.setBounds(600, 290, 300, 30);
        background.add(tfUsed);

        JLabel lblWaste = new JLabel("Waste:");
        lblWaste.setBounds(400, 360, 150, 30);
        lblWaste.setForeground(Color.WHITE);
        background.add(lblWaste);
        tfWaste = new JTextField();
        tfWaste.setBounds(600, 360, 300, 30);
        tfWaste.setEditable(false);
        background.add(tfWaste);

        btnCalculate = new JButton("Calculate");
        btnCalculate.setBounds(400, 450, 150, 40);
        btnCalculate.addActionListener(this);
        background.add(btnCalculate);

        btnReport = new JButton("Report");
        btnReport.setBounds(600, 450, 150, 40);
        btnReport.addActionListener(this);
        background.add(btnReport);

        btnBack = new JButton("Back");
        btnBack.setBounds(800, 450, 150, 40);
        btnBack.addActionListener(this);
        background.add(btnBack);
    }

    /**
     * METHOD OVERRIDING — overrides BaseForm's abstract clearFields().
     * Clears only the fields that belong to the Waste form.
     */
    @Override
    public void clearFields() {
        tfMaterial.setText("");
        tfTotal.setText("");
        tfUsed.setText("");
        tfWaste.setText("");
    }

    /**
     * Handles button clicks.
     *
     * ENCAPSULATION IN ACTION:
     * ► We create a WasteRecord object to hold and validate the data.
     * ► We read wasteAmount using record.getWasteAmount() — the getter —
     *   instead of computing it here in the button handler.
     * ► This keeps business logic inside the model (WasteRecord), not scattered
     *   across the UI code.
     */
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == btnCalculate) {
            String material = tfMaterial.getText().trim();
            String totalText = tfTotal.getText().trim();
            String usedText  = tfUsed.getText().trim();

            // Validation
            if (Validator.isEmptyOrNull(material)) {
                JOptionPane.showMessageDialog(null, "Material Name cannot be empty!");
                return;
            }
            if (!Validator.isPositiveInt(totalText) || !Validator.isPositiveInt(usedText)) {
                JOptionPane.showMessageDialog(null, "Total and Used must be positive numbers!");
                return;
            }

            int total = Integer.parseInt(totalText);
            int used  = Integer.parseInt(usedText);

            // ── ENCAPSULATION: create WasteRecord object ──────────────────
            // WasteRecord calculates waste internally and protects the data.
            WasteRecord record = new WasteRecord(material, total, used);

            if (record.getWasteAmount() == 0 && used > total) {
                JOptionPane.showMessageDialog(null,
                    "Used material cannot exceed total material!");
                return;
            }

            // Read the result using the getter — not a raw field
            tfWaste.setText(String.valueOf(record.getWasteAmount()));

            try {
                Conn con = new Conn();
                String query = "INSERT INTO waste(material_name, waste_amount, date) VALUES('"
                        + record.getMaterialName() + "', "
                        + record.getWasteAmount()  + ", CURDATE())";
                con.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Saved Successfully!");
                clearFields();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }

        if (ae.getSource() == btnReport) {
            try {
                Conn con = new Conn();
                ResultSet rs = con.s.executeQuery("SELECT * FROM waste");
                String[] columnNames = {"ID", "Material", "Waste", "Date"};
                javax.swing.table.DefaultTableModel model =
                        new javax.swing.table.DefaultTableModel(columnNames, 0);
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id"), rs.getString("material_name"),
                        rs.getInt("waste_amount"), rs.getDate("date")
                    });
                }
                JTable table = new JTable(model);
                JScrollPane scrollPane = new JScrollPane(table);
                JFrame reportFrame = new JFrame("Waste Report");
                reportFrame.setSize(800, 500);
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

    public static void main(String[] args) { new Waste(); }
}

