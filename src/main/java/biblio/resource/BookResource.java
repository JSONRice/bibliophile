package biblio.resource;

import org.springframework.hateoas.core.Relation;

import biblio.model.Book;
import biblio.model.BookFormat;

@Relation(value = "book", collectionRelation = "books")
public class BookResource extends ResourceWithEmbedded {
	
	private String title;
	private BookFormat format;
	private String summary;
	private Integer year;
	
	public BookResource() {}
	
	public BookResource(Book book) {
		this.title = book.getTitle();
		this.year = book.getPublishYear();
		this.format = book.getFormat();
	}
	
	public void addDetails(Book book) {
		this.summary = book.getSummary();
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public BookFormat getFormat() {
		return format;
	}
	public void setFormat(BookFormat format) {
		this.format = format;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
}
