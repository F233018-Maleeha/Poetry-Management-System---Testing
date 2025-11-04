package BusinessLayer;

import DTOPkg.BookDTO;
import DataAccessLayer.IDALFacade;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookBOTest {

    private BookBO bookBO;
    private IDALFacade mockDalFacade;

    @BeforeEach
    void setUp() {
        mockDalFacade = mock(IDALFacade.class);
        bookBO = new BookBO(mockDalFacade);
    }

    @Test
    @Order(1)
    @DisplayName("CreateBook_WithValidData_ShouldCallDAL")
    void createBook_WithValidData_ShouldCallDAL() {

        BookDTO validBook = new BookDTO(0, "Test Book", "Test Compiler", 2023);

        bookBO.createBook(validBook);

        verify(mockDalFacade).createBook(validBook);
    }

    @Test
    @Order(2)
    @DisplayName("ReadBook_WithExistingId_ShouldReturnBook")
    void readBook_WithExistingId_ShouldReturnBook() {

        BookDTO expectedBook = new BookDTO(1, "Existing Book", "Compiler", 2023);
        when(mockDalFacade.readBook(any(BookDTO.class))).thenReturn(expectedBook);

        BookDTO result = bookBO.readBook(new BookDTO(1, null, null, 0));

        assertNotNull(result);
        assertEquals(1, result.getBookID());
        assertEquals("Existing Book", result.getTitle());
    }
 
    @Test
    @Order(3)
    @DisplayName("UpdateBook_WithValidChanges_ShouldReturnUpdatedBook")
    void updateBook_WithValidChanges_ShouldReturnUpdatedBook() {

        BookDTO updatedBook = new BookDTO(1, "Updated Title", "Updated Compiler", 2024);
        when(mockDalFacade.updateBook(any(BookDTO.class))).thenReturn(updatedBook);

        BookDTO result = bookBO.updateBook(updatedBook);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        verify(mockDalFacade).updateBook(updatedBook);
    }

    @Test
    @Order(4)
    @DisplayName("DeleteBook_WithValidId_ShouldCallDAL")
    void deleteBook_WithValidId_ShouldCallDAL() {

        BookDTO bookToDelete = new BookDTO(1, "To Delete", "Compiler", 2023);

        bookBO.deleteBook(bookToDelete);

        verify(mockDalFacade).deleteBook(bookToDelete);
    }

    @Test
    @Order(5)
    @DisplayName("ReadBook_WithNonExistingId_ShouldReturnNull")
    void readBook_WithNonExistingId_ShouldReturnNull() {

        when(mockDalFacade.readBook(any(BookDTO.class))).thenReturn(null);

        BookDTO result = bookBO.readBook(new BookDTO(999, null, null, 0));

        assertNull(result);
    }

 // ECP Tests - Valid Input Classes
    @Test
    @DisplayName("ECP_ValidTitle_ValidCompiler_ValidEra_ShouldSucceed")
    void ecp_ValidTitle_ValidCompiler_ValidEra_ShouldSucceed() {
        BookDTO validBook = new BookDTO(0, "Valid Title", "Valid Compiler", 2023);
        assertDoesNotThrow(() -> bookBO.createBook(validBook));
        verify(mockDalFacade).createBook(validBook);
    }
    
    @Test
    @Order(5)
    @DisplayName("ListAll_ShouldReturnBookList")
    void listAll_ShouldReturnBookList() {
        List<BookDTO> result = assertDoesNotThrow(() -> mysqlBookDAO.listAll());
        assertNotNull(result);
    }
    @Test
    @DisplayName("Integration_CreateThenRead_ShouldWork")
    void integration_CreateThenRead_ShouldWork() {
       
        BookDTO book = new BookDTO(0, "Integration Test", "Integration Compiler", 2023);
        
        assertDoesNotThrow(() -> {
            mysqlBookDAO.createBook(book);
            BookDTO found = mysqlBookDAO.readBook(new BookDTO(book.getBookID(), null, null, 0));
            
        });
    }

    @Test
    @DisplayName("Integration_CreateUpdateRead_ShouldWork")
    void integration_CreateUpdateRead_ShouldWork() {
        BookDTO book = new BookDTO(0, "Original", "Original", 2023);
        
        assertDoesNotThrow(() -> {
            mysqlBookDAO.createBook(book);
            BookDTO updated = new BookDTO(book.getBookID(), "Updated", "Updated", 2024);
            mysqlBookDAO.updateBook(updated);
           
        });
    }


}

