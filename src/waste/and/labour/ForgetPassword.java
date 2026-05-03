package waste.and.labour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ForgetPassword extends JFrame implements ActionListener {
    /**
 * ForgetPassword class provides a UI for users to retrieve their password.
 * 
 * It allows users to:
 * 
 *     Search their account using username
 *     View their security question
 *     Retrieve password using the correct answer
 * 
 * 
 * 
 * This class connects to the database and verifies user credentials
 * from the "signup" table.
 * 
 * @author Amali
 * @author aarthi
 * @author samreen
 */
    JTextField tfUsername, tfName, tfQuestion, tfAnswer;
    JPasswordField tfPassword;
    JButton btnSearch, btnRetrieve, btnBack;

    ForgetPassword() {
        setBounds(400, 200, 700, 350);
        setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(30, 30, 350, 250);
        panel.setBackground(new Color(220, 220, 220));
        panel.setLayout(null);
        add(panel);

        JLabel l1 = new JLabel("Username");
        l1.setBounds(30, 20, 100, 25);
        panel.add(l1);

        JLabel l2 = new JLabel("Name");
        l2.setBounds(30, 60, 100, 25);
        panel.add(l2);

        JLabel l3 = new JLabel("Security Question");
        l3.setBounds(30, 100, 150, 25);
        panel.add(l3);

        JLabel l4 = new JLabel("Answer");
        l4.setBounds(30, 140, 100, 25);
        panel.add(l4);

        JLabel l5 = new JLabel("Password");
        l5.setBounds(30, 180, 100, 25);
        panel.add(l5);

        tfUsername = new JTextField();
        tfUsername.setBounds(150, 20, 120, 25);
        panel.add(tfUsername);

        tfName = new JTextField();
        tfName.setBounds(150, 60, 180, 25);
        panel.add(tfName);

        tfQuestion = new JTextField();
        tfQuestion.setBounds(150, 100, 180, 25);
        panel.add(tfQuestion);

        tfAnswer = new JTextField();
        tfAnswer.setBounds(150, 140, 120, 25);
        panel.add(tfAnswer);

        tfPassword = new JPasswordField();
        tfPassword.setBounds(150, 180, 180, 25);
        panel.add(tfPassword);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(280, 20, 80, 25);
        btnSearch.addActionListener(this);
        panel.add(btnSearch);

        btnRetrieve = new JButton("Show");
        btnRetrieve.setBounds(280, 140, 80, 25);
        btnRetrieve.addActionListener(this);
        panel.add(btnRetrieve);

        btnBack = new JButton("Back");
        btnBack.setBounds(120, 210, 100, 30);
        btnBack.addActionListener(this);
        panel.add(btnBack);

        
        ImageIcon c1 = new ImageIcon(ClassLoader.getSystemResource("icons/forgotpassword.jpg"));
        Image i1 = c1.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        JLabel l6 = new JLabel(new ImageIcon(i1));
        l6.setBounds(420, 60, 200, 200);
        add(l6);

        setVisible(true); 
    }

    public void actionPerformed(ActionEvent ae) {


          /**
     * Handles button click events.
     * 
     * Performs three main actions:
     * 
     *     Search: Fetch user details using username
     *     Retrieve: Verify answer and display password
     *     Back: Navigate back to login screen
     * 
     * 
     * 
     * @param ae ActionEvent triggered by button click
     */
    
    if (ae.getSource() == btnSearch) {
        try {
            Conn con = new Conn();

            String query = "SELECT * FROM signup WHERE username='" + tfUsername.getText() + "'";
            ResultSet r = con.s.executeQuery(query);

            if (r.next()) {
                tfName.setText(r.getString("name"));
                tfQuestion.setText(r.getString("security_question"));
            } else {
                JOptionPane.showMessageDialog(null, "User not found ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  
    else if (ae.getSource() == btnRetrieve) {
        try {
            Conn con = new Conn();

            String query = "SELECT * FROM signup WHERE username='" + tfUsername.getText() +
                    "' AND answer='" + tfAnswer.getText() + "'";

            ResultSet r = con.s.executeQuery(query);

            if (r.next()) {
                tfPassword.setText(r.getString("password"));
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect Answer ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    else if (ae.getSource() == btnBack) {
        setVisible(false);
        new Login();  
    }
}
    
    /**
     * Main method to launch Forget Password screen.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        new ForgetPassword();
    }
}