package BusinessLayer;

import java.util.List;

import DTOPkg.BookDTO;
import DataAccessLayer.IDALFacade;

public class BookBO {
	private IDALFacade dalFacade; 

    public BookBO(IDALFacade dalFacade) {  
        this.dalFacade = dalFacade;
    }

    public void createBook(BookDTO book) {
        
        dalFacade.createBook(book); 
    }
    
    public BookDTO readBook(BookDTO book) {
        
        return dalFacade.readBook(book); 
    }
    
    public BookDTO updateBook(BookDTO book) {
        
        return dalFacade.updateBook(book); 
    }
    
    public void deleteBook(BookDTO book) {
        
        dalFacade.deleteBook(book); 
    }
    
	
	  public List<BookDTO> listAll() { return dalFacade.listAll(); }
	 
}
