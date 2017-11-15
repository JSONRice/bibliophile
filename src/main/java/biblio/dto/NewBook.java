package biblio.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import biblio.service.AuthorService;
import biblio.service.BookSubjectService;
import biblio.service.PublisherService;
import biblio.validation.BookFormatCode;
import biblio.validation.Unique;
import biblio.validation.WebId;

public class NewBook {
	
	@NotNull
	private String title;
	
	@Valid
	private List<NewBook.Author> authors;
	
	// Null value is allowed
	private String summary;
	
	@BookFormatCode
	private String formatCode;
	
	@Valid
	private List<NewBook.Subject> subjects;
	
	@Valid
	private NewBook.Publisher publisher;
	
	@NotNull
	private Integer year;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<NewBook.Author> getAuthors() {
		return authors;
	}
	public void setAuthors(List<NewBook.Author> authors) {
		this.authors = authors;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getFormatCode() {
		return formatCode;
	}
	public void setFormatCode(String formatCode) {
		this.formatCode = formatCode;
	}
	public List<NewBook.Subject> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<NewBook.Subject> subjects) {
		this.subjects = subjects;
	}
	public NewBook.Publisher getPublisher() {
		return publisher;
	}
	public void setPublisher(NewBook.Publisher publisher) {
		this.publisher = publisher;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	
	@Override
	public String toString() {
		return "New Book: {title=" + title + ", authors=" + authors + ", format=" + formatCode + ", publisher=" + publisher + ", subjects=" + subjects + "}";
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Author {
		@WebId(service = AuthorService.class, message = "Invalid author ID", required = false)
		private String id;
		
		@Unique(fieldName = "name", service = AuthorService.class, message = "Author name already exists", required = false)
		private String name;
		
		public String getId() {return id;}
		public void setId(String id) {this.id = id;}
		public String getName() {return name;}
		public void setName(String name) {this.name = name;}
		
		@Override
		public String toString() {
			if (name != null) return name;
			return id;
		}
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Publisher {
		@WebId(service = PublisherService.class, message = "Invalid publisher ID", required = false)
		private String id;
		
		@Unique(fieldName = "name", service = PublisherService.class, message = "Publisher name already exists", required = false)
		private String name;
		
		public String getId() {return id;}
		public void setId(String id) {this.id = id;}
		public String getName() {return name;}
		public void setName(String name) {this.name = name;}
		
		@Override
		public String toString() {
			if (name != null) return name;
			return id;
		}
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Subject {
		@WebId(service = BookSubjectService.class, message = "Invalid subject ID", required = false)
		private String id;
		
		@Unique(fieldName = "name", service = BookSubjectService.class, message = "Subject already exists", required = false)
		private String name;
		
		public String getId() {return id;}
		public void setId(String id) {this.id = id;}
		public String getName() {return name;}
		public void setName(String name) {this.name = name;}
		
		@Override
		public String toString() {
			if (name != null) return name;
			return id;
		}
	}
}
