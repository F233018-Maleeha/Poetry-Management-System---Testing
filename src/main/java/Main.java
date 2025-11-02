
import java.sql.SQLException;
import BusinessLayer.BookBO;
import DataAccessLayer.AbstractDAOFactory;
import DataAccessLayer.DALFacade;
import DataAccessLayer.IDALFacade;
import DataAccessLayer.DBConnection;
import DataAccessLayer.IBookDAO;
import DataAccessLayer.IDAOFactory;
import PresentationLayer.JavaFXUI; // Assuming JavaFXUI is the main JavaFX component
//import PresentationLayer.JavaFXLauncher; // NEW: The JavaFX Application subclass

public class Main {

    private static BookBO bookBOInstance; // Static field to hold the initialized BookBO

    public static void main(String[] args) {
        try {
            // --- PRE-INITIALIZATION CHECK ---
            if (DBConnection.getConnection() == null) {
                System.err.println("Fatal: Database connection failed. Please ensure DBConnection class is configured correctly and MySQL is running.");
                return;
            }

            // --- 1. Data Access Layer Setup (DAL) ---
            IDAOFactory daoFactory = AbstractDAOFactory.getInstance();
            if (daoFactory == null) {
                throw new RuntimeException("Failed to load IDAOFactory from configuration. Check 'config.properties'.");
            }

            IBookDAO bookDAO = daoFactory.createBookDAO();
            IDALFacade dalFacade = new DALFacade(bookDAO);

            // --- 2. Business Logic Layer Setup (BLL) ---
            // Store the initialized business object statically to pass to the JavaFX app
            bookBOInstance = new BookBO(dalFacade);

            // --- 3. Start JavaFX GUI ---
            // The static launch method will find and run the start() method of JavaFXLauncher
            JavaFXUI.launchApp(args, bookBOInstance); // Pass the initialized BookBO

        } catch (SQLException e) {
            System.err.println("Database Initialization Error: " + e.getMessage());
            e.printStackTrace();
            // In a console app, just print, or use a basic System dialog if available
        } catch (Exception e) {
            System.err.println("Application Initialization Error: " + e.getMessage());
            e.printStackTrace();
            // Use a simple non-JavaFX way to show a fatal error if JavaFX hasn't started
            System.out.println("FATAL ERROR: Failed to start the application. Check console for details.");
        }
    }
}