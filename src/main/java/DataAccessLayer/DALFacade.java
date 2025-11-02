package DataAccessLayer;


import java.util.List;

import DTOPkg.BookDTO;

public class DALFacade implements IDALFacade{
	private IBookDAO bookDAO;

	public DALFacade(IBookDAO bookDAO) {
		super();
		this.bookDAO = bookDAO;
	}
	
	//@Override
    public void createBook(BookDTO book) {
        
		bookDAO.createBook(book); 
    }
	//@Override
    public BookDTO readBook(BookDTO book) {
        
        return bookDAO.readBook(book); 
    }
	//@Override
    public BookDTO updateBook(BookDTO book) {
        
        return bookDAO.updateBook(book); 
    }
	//@Override
    public void deleteBook(BookDTO book) {
        
		bookDAO.deleteBook(book); 
    }
	
	public List<BookDTO> listAll() { return bookDAO.listAll(); }
	 
}
