package DataAccessLayer;


import java.util.List;

import DTOPkg.BookDTO;

public interface IBookDAO {
	public void createBook(BookDTO book);
	public BookDTO readBook(BookDTO book);
	public BookDTO updateBook(BookDTO book);
	public void deleteBook(BookDTO book);
	List<BookDTO> listAll();

}
