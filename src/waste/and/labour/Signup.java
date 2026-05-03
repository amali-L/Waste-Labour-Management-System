package waste.and.labour;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

/**
 * Signup class allows a new user to create an account.
 *
 * OOP CONCEPTS USED:
 * ─────────────────
 * 1. INHERITANCE   — extends JFrame
 * 2. INTERFACE     — implements ActionListener
 * 3. POLYMORPHISM  — actionPerformed() handles Create vs Back differently
 * 4. ENCAPSULATION — form fields are private to this class
 *
 * DATA VALIDATION (using Validator class):
 * ────────────────────────────────────────
 * ✔ No field can be empty
 * ✔ Username must be at least 4 characters
 * ✔ Password must be at least 6 characters with letters + numbers
 *
 * @author ACER
 */
public class Signup extends JFrame implements ActionListener {

    JTextField tfUsername, tfName, tfAnswer;
    JPasswordField pfPassword;
    JComboBox<String> securityQ;
    JButton btnCreate, btnBack;

    /**
     * Constructor: builds the Signup UI window.
     */
    Signup() {
        setBounds(450, 200, 700, 400);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel panel = new JPanel();
        panel.setBounds(30, 30, 640, 300);
        panel.setBackground(new Color(133, 193, 233));
        panel.setLayout(null);
        add(panel);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(50, 30, 120, 25);
        panel.add(lblUsername);
        tfUsername = new JTextField();
        tfUsername.setBounds(200, 30, 200, 25);
        panel.add(tfUsername);

        JLabel lblName = new JLabel("Name");
        lblName.setBounds(50, 70, 120, 25);
        panel.add(lblName);
        tfName = new JTextField();
        tfName.setBounds(200, 70, 200, 25);
        panel.add(tfName);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(50, 110, 120, 25);
        panel.add(lblPassword);
        pfPassword = new JPasswordField();
        pfPassword.setBounds(200, 110, 200, 25);
        panel.add(pfPassword);

        JLabel lblSecurity = new JLabel("Security Question");
        lblSecurity.setBounds(50, 150, 150, 25);
        panel.add(lblSecurity);
        String[] questions = {
            "Your Nickname?",
            "Your Favorite Teacher?",
            "Your Birth Place?",
            "Your Best Friend Name?"
        };
        securityQ = new JComboBox<>(questions);
        securityQ.setBounds(200, 150, 200, 25);
        panel.add(securityQ);

        JLabel lblAnswer = new JLabel("Answer");
        lblAnswer.setBounds(50, 190, 120, 25);
        panel.add(lblAnswer);
        tfAnswer = new JTextField();
        tfAnswer.setBounds(200, 190, 200, 25);
        panel.add(tfAnswer);

        btnCreate = new JButton("Create");
        btnCreate.setBounds(100, 240, 120, 30);
        btnCreate.addActionListener(this);
        panel.add(btnCreate);

        btnBack = new JButton("Back");
        btnBack.setBounds(250, 240, 120, 30);
        btnBack.addActionListener(this);
        panel.add(btnBack);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/signup.png"));
        Image i2 = i1.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(420, 50, 200, 200);
        panel.add(image);

        setVisible(true);
    }

    /**
     * Handles Create Account and Back button events.
     *
     * @param ae ActionEvent triggered by button click
     */
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnCreate) {
            try {
                String username = tfUsername.getText().trim();
                String name     = tfName.getText().trim();
                String password = new String(pfPassword.getPassword()).trim();
                String question = (String) securityQ.getSelectedItem();
                String answer   = tfAnswer.getText().trim();

                // DATA VALIDATION — using Validator class
                if (Validator.isEmptyOrNull(username) || Validator.isEmptyOrNull(name)
                        || Validator.isEmptyOrNull(password) || Validator.isEmptyOrNull(answer)) {
                    JOptionPane.showMessageDialog(null, "All fields are required!");
                    return;
                }
                if (!Validator.isValidUsername(username)) {
                    JOptionPane.showMessageDialog(null, "Username must be at least 4 characters!");
                    return;
                }
                if (!Validator.isValidPassword(password)) {
                    JOptionPane.showMessageDialog(null,
                        "Password must be at least 6 characters and contain letters and numbers!");
                    return;
                }

                // All validations passed — save to database
                Conn con = new Conn();
                String query = "INSERT INTO signup(username, name, password, security_question, answer) VALUES('"
                        + username + "', '" + name + "', '" + password
                        + "', '" + question + "', '" + answer + "')";
                con.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Account Created Successfully!");

                // Clear fields after success
                tfUsername.setText(""); tfName.setText("");
                pfPassword.setText(""); tfAnswer.setText("");

            } catch (SQLIntegrityConstraintViolationException e) {
                JOptionPane.showMessageDialog(null, "Username already exists! Choose a different one.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }

        if (ae.getSource() == btnBack) {
            setVisible(false);
            new Login();
        }
    }

    /** Main method to launch the Signup application. */
    public static void main(String[] args) {
        new Signup();
    }
}


