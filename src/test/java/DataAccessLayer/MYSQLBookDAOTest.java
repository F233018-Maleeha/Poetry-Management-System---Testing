package DataAccessLayer;

import DTOPkg.BookDTO;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MYSQLBookDAOTest {

    private MYSQLBookDAO mysqlBookDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        mysqlBookDAO = new MYSQLBookDAO();
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
    }

    @Test
    @Order(1)
    @DisplayName("CreateBook_WithValidBook_ShouldExecuteInsert")
    void createBook_WithValidBook_ShouldExecuteInsert() throws SQLException {
        BookDTO book = new BookDTO(0, "Test Book", "Test Compiler", 2023);
        assertDoesNotThrow(() -> mysqlBookDAO.createBook(book));
    }

    @Test
    @Order(2)
    @DisplayName("ReadBook_WithValidId_ShouldReturnBook")
    void readBook_WithValidId_ShouldReturnBook() {
        BookDTO book = new BookDTO(1, null, null, 0);
        assertDoesNotThrow(() -> mysqlBookDAO.readBook(book));
    }

    @Test
    @Order(3)
    @DisplayName("UpdateBook_WithValidData_ShouldReturnBook")
    void updateBook_WithValidData_ShouldReturnBook() {
        BookDTO book = new BookDTO(1, "Updated", "Updated", 2024);
        assertDoesNotThrow(() -> mysqlBookDAO.updateBook(book));
    }

    @Test
    @Order(4)
    @DisplayName("DeleteBook_WithValidId_ShouldExecuteDelete")
    void deleteBook_WithValidId_ShouldExecuteDelete() {
        BookDTO book = new BookDTO(1, null, null, 0);
        assertDoesNotThrow(() -> mysqlBookDAO.deleteBook(book));
    }

}