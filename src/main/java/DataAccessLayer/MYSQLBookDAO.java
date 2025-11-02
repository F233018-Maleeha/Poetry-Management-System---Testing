package DataAccessLayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import DTOPkg.BookDTO;

/**
 * MYSQLBookDAO implements the IBookDAO interface, providing CRUD operations 
 * for the Book entity using JDBC against a MySQL database.
 * * CRITICAL FIX: Uses Connection-Per-Operation and try-with-resources 
 * for thread safety and reliable resource closure.
 */
public class MYSQLBookDAO implements IBookDAO {

    @Override
    public void createBook(BookDTO book) {
        String sql = "INSERT INTO book (title, compiler, era) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getCompiler());
            ps.setInt(3, book.getEra());
            
            ps.executeUpdate();
            
            // Retrieve the auto-generated primary key
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    book.setBookID(rs.getInt(1));
                }
            }
            
        } catch (SQLException e) {
            DBConnection.printConnectionError(e);
        }
    }

    @Override
    public BookDTO readBook(BookDTO book) {
        String sql = "SELECT bookID, title, compiler, era FROM book WHERE bookID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, book.getBookID());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new BookDTO(
                        rs.getInt("bookID"),
                        rs.getString("title"),
                        rs.getString("compiler"),
                        rs.getInt("era")
                    );
                }
            }
        } catch (SQLException e) {
            DBConnection.printConnectionError(e);
        }
        return null;
    }

    @Override
    public BookDTO updateBook(BookDTO book) {
        String sql = "UPDATE book SET title = ?, compiler = ?, era = ? WHERE bookID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getCompiler());
            ps.setInt(3, book.getEra());
            ps.setInt(4, book.getBookID());
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                return book;
            }
            
        } catch (SQLException e) {
            DBConnection.printConnectionError(e);
        }
        return null;
    }

    @Override
    public void deleteBook(BookDTO book) {
        String sql = "DELETE FROM book WHERE bookID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, book.getBookID());
            ps.executeUpdate();
            
        } catch (SQLException e) {
            DBConnection.printConnectionError(e);
        }
    }

    @Override
    public List<BookDTO> listAll() {
        List<BookDTO> list = new ArrayList<>();
        String sql = "SELECT bookID, title, compiler, era FROM book";
        
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement(); // <-- The missing declaration is fixed here
             ResultSet rs = st.executeQuery(sql)) { // Now 'st' is correctly defined
            
            while (rs.next()) {
                list.add(new BookDTO(
                    rs.getInt("bookID"),
                    rs.getString("title"),
                    rs.getString("compiler"),
                    rs.getInt("era")
                ));
            }
            
        } catch (SQLException e) {
            DBConnection.printConnectionError(e);
        }
        return list;
    }
}