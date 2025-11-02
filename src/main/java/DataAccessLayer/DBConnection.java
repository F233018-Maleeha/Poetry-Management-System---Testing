package DataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection handles MySQL connection setup.
 * It follows the robust connection-per-operation model,
 * ensuring each DAO method uses a fresh, valid connection.
 * The DAOs MUST use try-with-resources with the returned Connection.
 */
public class DBConnection {

    // Update these according to your actual setup (XAMPP defaults)
    private static final String URL = "jdbc:mysql://localhost:3306/PoetryDB"; 
    private static final String USER = "root";  
    private static final String PASSWORD = "";  
    
    // The driver is loaded once via a static initializer
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ MySQL JDBC Driver loaded.");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL JDBC Driver not found! Please check libraries.");
            e.printStackTrace();
        }
    }

    /**
     * Creates and returns a new database connection object.
     * This method must be called within a try-with-resources block 
     * by the DAO layer to ensure the connection is closed after use.
     * * @return A new and valid Connection object.
     * @throws SQLException if the connection cannot be established.
     */
    public static Connection getConnection() throws SQLException {
        // Establishes a fresh connection every time
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        
        // Optional: Print successful connection status for debugging
        if (conn != null) {
            System.out.println("🔗 New connection established.");
        }
        return conn;
    }

    /**
     * Utility method to print an error message when a connection fails.
     */
    public static void printConnectionError(SQLException e) {
        System.err.println("❌ Database operation failed:");
        System.err.println("Error Code: " + e.getErrorCode());
        System.err.println("SQL State: " + e.getSQLState());
        System.err.println("Message: " + e.getMessage());
        e.printStackTrace();
    }
    
    // The closeConnection() method is now obsolete for individual operations 
    // because the DAO layer handles closure via try-with-resources.
    // However, we can keep it for final application shutdown (e.g., in PoetrySystemMain).
    // public static void closeConnection() { /* ... */ }
}