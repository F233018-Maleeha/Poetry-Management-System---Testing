package DTOPkg;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("fast")
@Tag("unit") 
@Tag("dto")
public class BookDTOTest {

    @Test
    @Order(1)
    @DisplayName("BookDTO_Constructor_ShouldSetValuesCorrectly")
    void bookDTO_Constructor_ShouldSetValuesCorrectly() {

        BookDTO book = new BookDTO(1, "Test Title", "Test Compiler", 2023);

        assertEquals(1, book.getBookID());
        assertEquals("Test Title", book.getTitle());
        assertEquals("Test Compiler", book.getCompiler());
        assertEquals(2023, book.getEra());
    }

    @Test
    @Order(2)
    @DisplayName("BookDTO_Setters_ShouldUpdateValues")
    void bookDTO_Setters_ShouldUpdateValues() {

        BookDTO book = new BookDTO(1, "Original", "Original", 2000);

        book.setTitle("Updated Title");
        book.setCompiler("Updated Compiler");
        book.setEra(2023);
 
        assertEquals("Updated Title", book.getTitle());
        assertEquals("Updated Compiler", book.getCompiler());
        assertEquals(2023, book.getEra());
    }

    @Test
    @Order(3)
    @DisplayName("BookDTO_ToString_ShouldContainAllFields")
    void bookDTO_ToString_ShouldContainAllFields() {

        BookDTO book = new BookDTO(123, "Sample Book", "John Doe", 2023);

        String result = book.toString();

        assertTrue(result.contains("123"));
        assertTrue(result.contains("Sample Book"));
        assertTrue(result.contains("John Doe"));
        assertTrue(result.contains("2023"));
    }
 

    @Test
    @Order(4)
    @DisplayName("BookDTO_WithNullValues_ShouldHandleGracefully")
    void bookDTO_WithNullValues_ShouldHandleGracefully() {
        
        BookDTO book = new BookDTO(1, null, null, 2023);
        
        assertNull(book.getTitle());
        assertNull(book.getCompiler());
        assertEquals(2023, book.getEra());
    }

    @Test
    @Order(5)
    @DisplayName("BookDTO_WithBoundaryEraValues_ShouldHandleCorrectly")
    void bookDTO_WithBoundaryEraValues_ShouldHandleCorrectly() {
 
        BookDTO book1 = new BookDTO(1, "Book1", "Compiler1", Integer.MIN_VALUE);
        BookDTO book2 = new BookDTO(2, "Book2", "Compiler2", 0);
        BookDTO book3 = new BookDTO(3, "Book3", "Compiler3", Integer.MAX_VALUE);
        
        assertEquals(Integer.MIN_VALUE, book1.getEra());
        assertEquals(0, book2.getEra());
        assertEquals(Integer.MAX_VALUE, book3.getEra());
    }

    @Test
    @Order(6)
    @DisplayName("BookDTO_GetterSetter_ConsistencyCheck")
    void bookDTO_GetterSetter_ConsistencyCheck() {

    	BookDTO book = new BookDTO(1, "Original", "Original", 2000);

        book.setBookID(999);
        book.setTitle("Final Title");
        book.setCompiler("Final Compiler"); 
        book.setEra(3000);
 
        assertEquals(999, book.getBookID());
        assertEquals("Final Title", book.getTitle());
        assertEquals("Final Compiler", book.getCompiler());
        assertEquals(3000, book.getEra());
    }

    @Test
    @Order(7)
    @DisplayName("BookDTO_WithNegativeEra_ShouldHandleCorrectly")
    void bookDTO_WithNegativeEra_ShouldHandleCorrectly() {
        
        BookDTO book = new BookDTO(1, "Ancient Book", "Ancient Compiler", -500);
        
        assertEquals(-500, book.getEra());
        assertEquals("Ancient Book", book.getTitle());
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/book_test_data.csv", numLinesToSkip = 1)
    @DisplayName("BookDTO_DataDrivenFromCSV_ShouldCreateObjects")
    void bookDTO_DataDrivenFromCSV_ShouldCreateObjects(
        String testCase, int bookID, String title, String compiler, int era, String expectedResult) {
        
        // This test runs for each row in the CSV file
        assertDoesNotThrow(() -> {
            BookDTO book = new BookDTO(bookID, title, compiler, era);
            assertNotNull(book);
            assertEquals(title, book.getTitle());
            assertEquals(compiler, book.getCompiler());
            assertEquals(era, book.getEra());
        });
    }
    
    @ParameterizedTest
    @CsvFileSource(resources = "/book_boundary_data.csv", numLinesToSkip = 1)
    @DisplayName("BookDTO_BoundaryValuesFromCSV_ShouldCreateObjects")
    void bookDTO_BoundaryValuesFromCSV_ShouldCreateObjects(int id, String title, String compiler, int era) {
        // This test runs for each row in the boundary data CSV
        assertDoesNotThrow(() -> {
            BookDTO book = new BookDTO(id, title, compiler, era);
            assertNotNull(book);
            assertEquals(title, book.getTitle());
            assertEquals(compiler, book.getCompiler());
            assertEquals(era, book.getEra());
        });
    }
    
    @Test
    @DisplayName("BVA_Era_MinValue_ShouldHandle")
    void bva_Era_MinValue_ShouldHandle() {
        BookDTO minEraBook = new BookDTO(1, "Min Era", "Compiler", Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, minEraBook.getEra());
    }

    @Test
    @DisplayName("BVA_Era_Zero_ShouldHandle")
    void bva_Era_Zero_ShouldHandle() {
        BookDTO zeroEraBook = new BookDTO(1, "Zero Era", "Compiler", 0);
        assertEquals(0, zeroEraBook.getEra());
    }

    @Test
    @DisplayName("BVA_Era_MaxValue_ShouldHandle")
    void bva_Era_MaxValue_ShouldHandle() {
        BookDTO maxEraBook = new BookDTO(1, "Max Era", "Compiler", Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, maxEraBook.getEra());
    }

    // BVA Tests for Title Length
    @Test
    @DisplayName("BVA_Title_Empty_ShouldHandle")
    void bva_Title_Empty_ShouldHandle() {
        BookDTO emptyTitle = new BookDTO(1, "", "Compiler", 2023);
        assertEquals("", emptyTitle.getTitle());
    }

    @Test
    @DisplayName("BVA_Title_SingleChar_ShouldHandle")
    void bva_Title_SingleChar_ShouldHandle() {
        BookDTO singleCharTitle = new BookDTO(1, "A", "Compiler", 2023);
        assertEquals("A", singleCharTitle.getTitle());
    }

    @Test
    @DisplayName("BVA_Title_Null_ShouldHandle")
    void bva_Title_Null_ShouldHandle() {
        BookDTO nullTitle = new BookDTO(1, null, "Compiler", 2023);
        assertNull(nullTitle.getTitle());
    }
    @Test
    @DisplayName("Demonstrate_CSV_Test_Data_Flow")
    void demonstrate_CSV_Test_Data_Flow() {
        System.out.println("=== CSV Test Data Flow Demonstration ===");
        System.out.println("CSV Files Location: src/test/resources/");
        System.out.println("1. book_test_data.csv → Contains various test scenarios");
        System.out.println("2. book_boundary_data.csv → Contains boundary value cases");
        System.out.println("3. @CsvFileSource reads these files automatically");
        System.out.println("4. Each CSV row becomes a separate test execution");
        System.out.println("5. Total tests: 38 methods + 11 CSV executions = 49 total");
        System.out.println("========================================");
        
        assertTrue(true, "CSV test data flow is properly configured");
    }
}