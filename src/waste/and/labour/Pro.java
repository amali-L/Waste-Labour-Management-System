package waste.and.labour;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
/**
 * Pro class handles the Production Management module.
 * 
 * It allows users to:
 * - Record production details into the database
 * - Calculate material usage
 * - View all production records in a table
 * - Navigate back to the previous screen
 * 
 * This class connects with the database using the Conn class
 * and performs CRUD operations on the production table.
 * 
 * @author ACER
 */
public class Pro extends JFrame implements ActionListener {

    JTextField tfProdId, tfRingType, tfRawMaterial, tfUsedQty;
    JButton btnRecord, btnCalculate, btnView, btnBack;
      /**
     * Constructor initializes the Production Management UI.
     * It sets up labels, text fields, buttons, and background design.
     */
    Pro() {

    
        setTitle("Production Management");
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/steel_rod3.png"));
        Image i2 = i1.getImage().getScaledInstance(1400, 800, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(i2));
        background.setBounds(0, 0, 1400, 800);
        add(background);

        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(150, 120, 450, 500);
        panel.setBackground(new Color(255, 255, 255, 180));
        background.add(panel);

       
        JLabel title = new JLabel("PRODUCTION MANAGEMENT");
        title.setBounds(60, 20, 350, 35);
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel.add(title);

        int labelX = 40;
        int fieldX = 200;
        int y = 100;
        int gap = 70;

      
        JLabel l1 = new JLabel("Production ID");
        JLabel l2 = new JLabel("Ring Type");
        JLabel l3 = new JLabel("Raw Material");
        JLabel l4 = new JLabel("Used Quantity");

        l1.setBounds(labelX, y, 150, 30);
        l2.setBounds(labelX, y + gap, 150, 30);
        l3.setBounds(labelX, y + 2 * gap, 150, 30);
        l4.setBounds(labelX, y + 3 * gap, 150, 30);

        panel.add(l1); panel.add(l2); panel.add(l3); panel.add(l4);

      
        tfProdId = new JTextField();
        tfRingType = new JTextField();
        tfRawMaterial = new JTextField();
        tfUsedQty = new JTextField();

        tfProdId.setBounds(fieldX, y, 180, 35);
        tfRingType.setBounds(fieldX, y + gap, 180, 35);
        tfRawMaterial.setBounds(fieldX, y + 2 * gap, 180, 35);
        tfUsedQty.setBounds(fieldX, y + 3 * gap, 180, 35);

        panel.add(tfProdId);
        panel.add(tfRingType);
        panel.add(tfRawMaterial);
        panel.add(tfUsedQty);

        
        int btnY = y + 4 * gap + 40;

        btnRecord = new JButton("Record");
        btnCalculate = new JButton("Calculate");
        btnView = new JButton("View");
        btnBack = new JButton("Back");

        btnRecord.setBounds(40, btnY, 110, 40);
        btnCalculate.setBounds(160, btnY, 120, 40);
        btnView.setBounds(290, btnY, 100, 40);
        btnBack.setBounds(150, btnY + 70, 120, 40);

        panel.add(btnRecord);
        panel.add(btnCalculate);
        panel.add(btnView);
        panel.add(btnBack);

      
        btnRecord.addActionListener(this);
        btnCalculate.addActionListener(this);
        btnView.addActionListener(this);
        btnBack.addActionListener(this);

        setVisible(true);
    }
    /**
     * Handles button click events for all operations:
     * Record, Calculate, View, and Back.
     * 
     * @param ae ActionEvent triggered by user interaction
     */
    public void actionPerformed(ActionEvent ae) {

        Conn conn = new Conn();

        try {

            
            if (ae.getSource() == btnRecord) {

                String query = "INSERT INTO production VALUES ('"
                        + tfProdId.getText() + "','"
                        + tfRingType.getText() + "','"
                        + tfRawMaterial.getText() + "','"
                        + tfUsedQty.getText() + "')";

                conn.s.executeUpdate(query);

                JOptionPane.showMessageDialog(null, "Production Recorded");
                clearFields();
            }

           
            if (ae.getSource() == btnCalculate) {

                int used = Integer.parseInt(tfUsedQty.getText());
                int remaining = used - 10;

                JOptionPane.showMessageDialog(null,
                        "Used Quantity: " + used +
                                "\nRemaining (approx): " + remaining);
            }

          
            if (ae.getSource() == btnView) {

                JFrame f = new JFrame("Production Records");

                DefaultTableModel model = new DefaultTableModel();
                model.setColumnIdentifiers(new String[]{"ID", "Ring", "Material", "Qty"});

                ResultSet rs = conn.s.executeQuery("SELECT * FROM production");

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("prod_id"),
                            rs.getString("ring_type"),
                            rs.getString("raw_material"),
                            rs.getInt("used_quantity")
                    });
                }

                JTable table = new JTable(model);
                JScrollPane sp = new JScrollPane(table);

                f.add(sp);
                f.setSize(700, 400);
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            }

           
            if (ae.getSource() == btnBack) {
                setVisible(false);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
      /**
     * Clears all input fields after successful operation.
     */
    void clearFields() {
        tfProdId.setText("");
        tfRingType.setText("");
        tfRawMaterial.setText("");
        tfUsedQty.setText("");
    }
    
    /**
     * Main method to launch the Production Management screen.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        new Pro();
    }
}