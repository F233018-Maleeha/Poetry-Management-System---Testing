package DataAccessLayer;

/**
 * MYSQLDAOFactory is the concrete implementation of the AbstractDAOFactory 
 * for the MySQL database. It is responsible for instantiating the specific 
 * MySQL Data Access Objects (DAOs) for the Business Layer.
 * * This class is loaded dynamically via the 'dao.factory.class' property 
 * in config.properties at application startup.
 */
public class MYSQLDAOFactory extends AbstractDAOFactory {

    /**
     * Creates and returns a new instance of the concrete Book DAO 
     * implementation for MySQL.
     * * @return An object implementing the IBookDAO interface.
     */
    @Override
    public IBookDAO createBookDAO() {
        // Since MYSQLBookDAO implements IBookDAO, this is a correct 
        // return type and resolves the type mismatch in the factory usage.
        return new MYSQLBookDAO();
    }
    
    // NOTE: Future development will require implementing methods for 
    // PoetDAO, PoemDAO, etc., as per the SRS.
    
    // Example for future entities (Poet)
    // @Override
    // public IPoetDAO createPoetDAO() {
    //     return new MYSQLPoetDAO();
    // }
}