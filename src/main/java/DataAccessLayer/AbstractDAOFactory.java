package DataAccessLayer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class AbstractDAOFactory implements IDAOFactory{
	private static IDAOFactory instance = null; 
	public static final IDAOFactory getInstance() {
	    if (instance == null) {
	        String factoryClassName = null;
	        try (FileInputStream input = new FileInputStream("config.properties")) {
	            Properties prop = new Properties();
	            prop.load(input);
	            factoryClassName = prop.getProperty("dal.factory");

	            // Load class by name
	            Class<?> clazz = Class.forName(factoryClassName);

	            // Instantiate class
	            instance = (IDAOFactory) clazz.getDeclaredConstructor().newInstance();

	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return instance;
	}


}
