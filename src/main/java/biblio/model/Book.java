package biblio.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="books")
public class Book extends AbstractPersistable<Long> {
	
	private static final long serialVersionUID = 4839392927555943332L;

	@Column(name="web_id", columnDefinition="VARCHAR(10)", unique = true, nullable = false)
	private String webId;
	
	@Column(name="title", columnDefinition="VARCHAR(120)", nullable = false)
	private String title;
	
	@Column(name="format_code", columnDefinition="VARCHAR(10)", nullable = false)
	@Convert(converter = BookFormatConverter.class)
	private BookFormat format;
	
	@NotEmpty
	@ManyToMany
	@JoinTable(name="books_authors", 
		joinColumns = {@JoinColumn(name="author_id", referencedColumnName="id", nullable=false, updatable=true)}, 
		inverseJoinColumns = {@JoinColumn(name="book_id", referencedColumnName="id", nullable=false, updatable=true)})
	private Set<Author> authors = new HashSet<>();
	
	@Column(name="summary", columnDefinition="TEXT")
	private String summary;

	@NotEmpty
	@ManyToMany
	@JoinTable(name="books_subjects", 
		joinColumns = {@JoinColumn(name="subject_id", referencedColumnName="id", nullable=false, updatable=true)}, 
		inverseJoinColumns = {@JoinColumn(name="book_id", referencedColumnName="id", nullable=false, updatable=true)})
	private Set<BookSubject> subjects = new HashSet<>();

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="publisher_id", referencedColumnName="id", nullable=false, updatable=true)
	private Publisher publisher;
	
	@Column(name="publish_year")
	private Integer publishYear;
	
	@OneToMany(mappedBy="book")
	private Set<BookCopy> copies = new HashSet<>();
	
	public String getWebId() {
		return webId;
	}
	public void setWebId(String webId) {
		this.webId = webId;
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
	public Set<Author> getAuthors() {
		return authors;
	}
	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Set<BookSubject> getSubjects() {
		return subjects;
	}
	public void setSubjects(Set<BookSubject> subjects) {
		this.subjects = subjects;
	}
	public Publisher getPublisher() {
		return publisher;
	}
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	public Integer getPublishYear() {
		return publishYear;
	}
	public void setPublishYear(Integer publishYear) {
		this.publishYear = publishYear;
	}
	public Set<BookCopy> getCopies() {
		return copies;
	}
	public void setCopies(Set<BookCopy> copies) {
		this.copies = copies;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("Book [webID=").append(webId)
				.append(", title=").append(title)
				.append(", publisher=").append(publisher)
				.append(", year=").append(publishYear)
				.append("]").toString();
	}
}
