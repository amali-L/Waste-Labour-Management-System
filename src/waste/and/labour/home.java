package waste.and.labour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
/**
 * Home class represents the main dashboard of the Steel Ring Management System.
 * It provides navigation to different modules like Raw Material, Ring Inventory,
 * Wage Management, Sales, Waste Management, Labour Management, Billing, and Payment.
 * 
 * It also includes utility options like Calculator, Notepad, Reset Data, and Logout.
 * 
 * @author ACER
 */
public class home extends JFrame {

    String username;
    
    /**
     * Main method to launch the Home dashboard.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        new home("").setVisible(true);
    }
    
    /**
     * Constructor initializes the Home dashboard UI
     * and sets up all panels, buttons, and actions.
     *
     * @param username the logged-in user's name
     */
    public home(String username) {
        this.username = username;

        setTitle("Steel Ring Management System");
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0, 0, 102));
        panel.setBounds(0, 0, 300, 800);
        add(panel);

        JLabel dashboard = new JLabel("Dashboard");
        dashboard.setBounds(70, 20, 200, 40);
        dashboard.setForeground(Color.WHITE);
        dashboard.setFont(new Font("Tahoma", Font.BOLD, 22));
        panel.add(dashboard);

        
        JButton b1 = createButton("Raw Material", 100);
        JButton b2 = createButton("Ring Inventory", 160);
        JButton b3 = createButton("Wage Management", 220);
        JButton b4 = createButton("Sales & Reports", 280);
        JButton b5 = createButton("Waste Management", 340);
        JButton b6 = createButton("Labour Management", 400);
        JButton b7 = createButton("Calculator", 460);
        JButton b8 = createButton("Notepad", 520);
        JButton b9 = createButton("Billing", 580);
        JButton b11 = createButton("Payment", 640); // NEW BUTTON
        JButton reset = createButton("Reset Data", 700);
        JButton b10 = createButton("Logout", 760);

        
        panel.add(b1); panel.add(b2); panel.add(b3); panel.add(b4);
        panel.add(b5); panel.add(b6); panel.add(b7); panel.add(b8);
        panel.add(b9); panel.add(b11); panel.add(reset); panel.add(b10);

        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/h2.png"));
        Image i2 = i1.getImage().getScaledInstance(1100, 800, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(300, 0, 1100, 800);
        add(image);

        JLabel title = new JLabel("SANTHANA MADHA TRADERS");
        title.setBounds(350, 30, 800, 50);
        title.setFont(new Font("Tahoma", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        image.add(title);

        
        b1.addActionListener(e -> new Raw().setVisible(true));
        b2.addActionListener(e -> new Ringi().setVisible(true));
        b3.addActionListener(e -> new Wage().setVisible(true));
        b4.addActionListener(e -> new Sales().setVisible(true));
        b5.addActionListener(e -> new Waste().setVisible(true));
        b6.addActionListener(e -> new Labour().setVisible(true));
        b9.addActionListener(e -> new Bill().setVisible(true));

        
        b11.addActionListener(e -> new Payment().setVisible(true));

        
        b7.addActionListener(e -> {
            try {
                Runtime.getRuntime().exec("calc.exe");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Calculator not found");
            }
        });

        
        b8.addActionListener(e -> {
            try {
                Runtime.getRuntime().exec("notepad.exe");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Notepad not found");
            }
        });

        
        reset.addActionListener(e -> resetDatabase());

        
        b10.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });

        setVisible(true);
    }

     /**
     * Creates and styles a button with given text and position.
     *
     * @param text the label of the button
     * @param y the vertical position of the button
     * @return styled JButton
     */
    private JButton createButton(String text, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(20, y, 250, 40);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(0, 0, 153));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Tahoma", Font.BOLD, 15));
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(new Color(0, 0, 204));
            }

            public void mouseExited(MouseEvent evt) {
                btn.setBackground(new Color(0, 0, 153));
            }
        });

        return btn;
    }

      /**
     * Resets the entire database by calling a stored procedure.
     * Prompts user for confirmation before deleting all data.
     */
    private void resetDatabase() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure?\nThis will DELETE ALL DATA!",
                "Warning",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection con = DBConnection.getConnection();

                CallableStatement cs = con.prepareCall("{call reset_demo()}");
                cs.execute();

                con.close();

                JOptionPane.showMessageDialog(this, "System Reset Successful!");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }
}