package DataAccessLayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * TestDB is a simple utility class to ensure successful database connection
 */
public class TestDB {
    public static void main(String[] args) {
        // The try-with-resources block ensures that the Connection, Statement, 
        // and ResultSet are automatically closed upon exiting the block.
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT bookID, title FROM book")) {

            System.out.println("CONNECTION CONFIRMED: Database is accessible.");
            System.out.println("   (Table columns checked: bookID, title)");
            System.out.println("=================================================");

            int count = 0;
            while (rs.next()) {
                System.out.println("Book Found: " + rs.getInt("bookID") + " - " + rs.getString("title"));
                count++;
            }
            
            if (count == 0) {
                 System.out.println("   -> The 'book' table is currently EMPTY.");
            } else {
                 System.out.println("   -> Total records found: " + count);
            }
            System.out.println("-------------------------------------------------");


        } catch (SQLException e) {
            System.err.println("CONNECTION FAILED: Cannot connect to the database.");
            DBConnection.printConnectionError(e); 
        } catch (Exception e) {
             System.err.println("An unexpected error occurred: " + e.getMessage());
             e.printStackTrace();
        }
    }
}