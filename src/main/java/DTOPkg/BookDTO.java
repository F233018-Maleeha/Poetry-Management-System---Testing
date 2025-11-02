package DTOPkg;

/*import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;*/

public class BookDTO {
	private int bookID;
	private String title;
	private String compiler;
	private int era;
	//private final Map<Integer, BookDTO> storage = new HashMap<>();
//	private final List<Poem> poems = new ArrayList<>();
	
	public BookDTO(int bookID, String title, String compiler, int era) {
		this.bookID = bookID;
		this.title = title;
		this.compiler = compiler;
		this.era = era;
	}

	public int getBookID() {
		return bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getEra() {
		return era;
	}

	public void setEra(int era) {
		this.era = era;
	}

	public String getCompiler() {
		return compiler;
	}

	public void setCompiler(String compiler) {
		this.compiler = compiler;
	}
	
	/*
	 * public List<Poem> getPoems() { return Collections.unmodifiableList(poems); }
	 * 
	 * public synchronized void setPoems(List<Poem> newPoems) { poems.clear(); if
	 * (newPoems != null) { for (Poem p : newPoems) { if (p != null) poems.add(p); }
	 * } }
	 */
	
	
	/*
	 * public synchronized List<BookDTO> listAll() { List<BookDTO> list = new
	 * ArrayList<>(); for (BookDTO b : storage.values()) { list.add(new
	 * BookDTO(b.getBookID(), b.getTitle(), b.getCompiler(), b.getEra())); }
	 * list.sort(Comparator.comparing(BookDTO::getTitle,
	 * Comparator.nullsFirst(String::compareToIgnoreCase))); return list; }
	 */
	 
	@Override
	public String toString() {
		return "BookDTO [bookID=" + bookID + ", title=" + title + ", compiler=" + compiler + ", era=" + era + "]";
	}


}
