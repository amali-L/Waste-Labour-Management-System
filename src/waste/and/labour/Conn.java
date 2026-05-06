
package waste.and.labour;

import java.sql.*;

/**
 * Conn class is used to establish a connection with the MySQL database.
 

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
     
            Class.forName("com.mysql.cj.jdbc.Driver");

            c = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/steel_management",
                "root",
                "Amali@123"
            );

        
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
