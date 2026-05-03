/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package waste.and.labour;

import java.sql.*;

/**
 * Conn class is used to establish a connection with the MySQL database.
 * 
 * This class loads the MySQL JDBC driver, connects to the database
 * named "steel_management", and creates a Statement object for executing SQL queries.
 * 
 * 
 * Database Details:
 *
 *   URL: jdbc:mysql://localhost:3306/steel_management
 *   Username: root
 *   Password: Amali@123
 * 
 *
 * Usage:
 * 
 *     Conn conn = new Conn();
 * 
 * 
 * 
 * @author amali
 * @authiir aarthi
 * @author samreen
 */
public class Conn {

    /** Connection object used to connect to the database */
    Connection c;

    /** Statement object used to execute SQL queries */
    Statement s;

    /**
     * Constructor to initialize the database connection.
     * 
     * Loads the MySQL JDBC driver, establishes the connection,
     * and creates a Statement object.
     * 
     */
    public Conn() {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            c = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/steel_management",
                "root",
                "Amali@123"
            );

            // Create statement object
            s = c.createStatement();

            System.out.println("Connected");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method to test database connection.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        new Conn();
    }
}