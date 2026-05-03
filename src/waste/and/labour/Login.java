package waste.and.labour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.border.*;
/**
 * Login class provides the user authentication interface.
 * It allows users to enter username and password to access the system.
 * It also provides options to navigate to Signup and Forget Password screens.
 * This class connects to the database to validate user credentials.
 * 
 * @author ACER
 */
public class Login extends JFrame implements ActionListener {

    JTextField tfusername;
    JPasswordField tfpassword;
    JButton login, signup, password;
    
    /**
     * Constructor initializes the Login UI.
     */
    Login() {
        setSize(900, 400);
        setLocation(400, 200);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

       
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(131, 193, 233));
        p1.setBounds(0, 0, 400, 400);
        p1.setLayout(null);
        add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/login.png"));
        Image i2 = i1.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(100, 120, 200, 200);
        p1.add(image);

     
        JPanel p2 = new JPanel();
        p2.setLayout(null);
        p2.setBounds(400, 30, 450, 300);
        add(p2);

        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(60, 20, 100, 25);
        lblusername.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p2.add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(60, 60, 300, 30);
        p2.add(tfusername);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(60, 110, 100, 25);
        lblpassword.setFont(new Font("SansSerif", Font.PLAIN, 20));
        p2.add(lblpassword);

        tfpassword = new JPasswordField();
        tfpassword.setBounds(60, 150, 300, 30);
        p2.add(tfpassword);

        login = new JButton("Login");
        login.setBounds(60, 200, 130, 30);
        login.setBackground(new Color(133, 193, 233));
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        p2.add(login);

        signup = new JButton("Signup");
        signup.setBounds(230, 200, 130, 30);
        signup.setBackground(new Color(133, 193, 233));
        signup.setForeground(Color.WHITE);
        signup.addActionListener(this);
        p2.add(signup);

        password = new JButton("Forgot Password");
        password.setBounds(130, 250, 160, 30);
        password.setBackground(new Color(133, 193, 233));
        password.setForeground(Color.WHITE);
        password.addActionListener(this);
        p2.add(password);

        setVisible(true);
    }

    

      /**
     * Handles button click events for Login, Signup, and Forgot Password.
     * Validates user credentials and performs navigation.
     * 
     * @param ae ActionEvent triggered by button click
     */
    public void actionPerformed(ActionEvent ae) {

    if (ae.getSource() == login) {

        String username = tfusername.getText().trim();
        String pass = new String(tfpassword.getPassword()).trim();

   
        if (username.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter Username and Password!");
            return;
        }

        try {
            Conn con = new Conn();

            String query = "SELECT * FROM signup WHERE username='" + username + "' AND password='" + pass + "'";
            ResultSet rs = con.s.executeQuery(query);

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Login Successful ");
                setVisible(false);
                new home(username);

            } else {
                JOptionPane.showMessageDialog(null, "Invalid Username or Password ");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    else if (ae.getSource() == signup) {
        setVisible(false);
        new Signup();
    }

    else if (ae.getSource() == password) {
        setVisible(false);
        new ForgetPassword();
    }
}
     /**
     * Main method to launch the Login screen.
     * 
     * @param args command line arguments
     */
   

    public static void main(String[] args) {
        new Login();
    }
}

