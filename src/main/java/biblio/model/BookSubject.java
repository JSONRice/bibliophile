package biblio.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import biblio.dto.NewBook;
import biblio.dto.NewSubject;
import biblio.util.WebIdUtils;

@Entity
@Table(name="subjects")
public class BookSubject extends AbstractPersistable<Long> implements Comparable<BookSubject> {

	private static final long serialVersionUID = -5035299408907533960L;
	
	@Column(name="web_id", columnDefinition="VARCHAR(10) UNIQUE NOT NULL")
	private String webId;

	@Column(name="name", columnDefinition="VARCHAR(60) UNIQUE NOT NULL")
	private String name;

	@ManyToMany(mappedBy="subjects")
	private Set<Book> books = new HashSet<>();
	
	public BookSubject() {}
	
	public BookSubject(String webId, String name) {
		this.webId = webId;
		this.name = name;
	}
	
	public BookSubject(NewSubject newSubject) {
		this.name = newSubject.getName();
		this.webId = WebIdUtils.nextSubjectId();
	}
	
	public BookSubject(NewBook.Subject newSubject) {
		this.name = newSubject.getName();
		this.webId = WebIdUtils.nextSubjectId();
	}

	public String getWebId() {
		return webId;
	}
	public void setWebId(String webId) {
		this.webId = webId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Book> getBooks() {
		return books;
	}
	public void setBooks(Set<Book> books) {
		this.books = books;
	}
	
	@Override
	public int compareTo(BookSubject other) {
		return this.name.compareTo(other.name);
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("Book Subject [webId=").append(webId)
				.append(", name=").append(name)
				.append("]").toString();
	}
}
