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
    @DisplayName("ECP_EmptyTitle_ValidCompiler_ValidEra_ShouldHandle")
    void ecp_EmptyTitle_ValidCompiler_ValidEra_ShouldHandle() {
        BookDTO emptyTitleBook = new BookDTO(0, "", "Valid Compiler", 2023);
        assertDoesNotThrow(() -> bookBO.createBook(emptyTitleBook));
        verify(mockDalFacade).createBook(emptyTitleBook);
    }

    @Test
    @DisplayName("ECP_NullTitle_ValidCompiler_ValidEra_ShouldHandle")
    void ecp_NullTitle_ValidCompiler_ValidEra_ShouldHandle() {
        BookDTO nullTitleBook = new BookDTO(0, null, "Valid Compiler", 2023);
        assertDoesNotThrow(() -> bookBO.createBook(nullTitleBook));
        verify(mockDalFacade).createBook(nullTitleBook);
    }
    
    @Test
    @Order(6)
    @DisplayName("UpdateBook_WithNonExistingId_ShouldReturnNull")
    void updateBook_WithNonExistingId_ShouldReturnNull() {

        when(mockDalFacade.updateBook(any(BookDTO.class))).thenReturn(null);

        BookDTO result = bookBO.updateBook(new BookDTO(999, "Title", "Compiler", 2023));
 
        assertNull(result);
    }

    @Test
    @Order(7)
    @DisplayName("CreateBook_WithNullTitle_ShouldHandleGracefully")
    void createBook_WithNullTitle_ShouldHandleGracefully() {

        BookDTO bookWithNullTitle = new BookDTO(0, null, "Compiler", 2023);
 
        assertDoesNotThrow(() -> bookBO.createBook(bookWithNullTitle));
        verify(mockDalFacade).createBook(bookWithNullTitle);
    }
 
    @Test
    @Order(8)
    @DisplayName("ListAll_WhenBooksExist_ShouldReturnAllBooks")
    void listAll_WhenBooksExist_ShouldReturnAllBooks() {

        List<BookDTO> expectedBooks = java.util.Arrays.asList(
            new BookDTO(1, "Book One", "Compiler One", 2023),
            new BookDTO(2, "Book Two", "Compiler Two", 2024)
        );
        when(mockDalFacade.listAll()).thenReturn(expectedBooks);

        List<BookDTO> result = bookBO.listAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Book One", result.get(0).getTitle());
    }

    @Test
    @Order(9)
    @DisplayName("ListAll_WhenNoBooks_ShouldReturnEmptyList")
    void listAll_WhenNoBooks_ShouldReturnEmptyList() {
 
        when(mockDalFacade.listAll()).thenReturn(java.util.Arrays.asList());

        List<BookDTO> result = bookBO.listAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @Order(10)
    @DisplayName("CreateBook_WithEmptyTitle_ShouldCallDAL")
    void createBook_WithEmptyTitle_ShouldCallDAL() {

        BookDTO bookWithEmptyTitle = new BookDTO(0, "", "Compiler", 2023);
 
        assertDoesNotThrow(() -> bookBO.createBook(bookWithEmptyTitle));
        verify(mockDalFacade).createBook(bookWithEmptyTitle);
    }


}

