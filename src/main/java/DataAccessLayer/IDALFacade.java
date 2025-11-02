package DataAccessLayer;

import java.util.List;

import DTOPkg.BookDTO;

public interface IDALFacade extends IBookDAO{
	List<BookDTO> listAll();

}
