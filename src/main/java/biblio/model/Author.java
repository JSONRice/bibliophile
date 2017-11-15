package biblio.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import biblio.dto.NewAuthor;
import biblio.dto.NewBook;
import biblio.util.WebIdUtils;

@Entity
@Table(name="authors")
public class Author extends AbstractPersistable<Long> implements Comparable<Author> {
	
	private static final long serialVersionUID = -1310161707998264455L;

	@Column(name="web_id", columnDefinition="VARCHAR(10) UNIQUE NOT NULL")
	private String webId;
	
	@Column(name="name", columnDefinition="VARCHAR(100)")
	private String name;
	
	@ManyToMany(mappedBy="authors")
	private Set<Book> books = new HashSet<>();
	
	public Author() {}
	
	public Author(NewAuthor newAuthor) {
		this.name = newAuthor.getName();
		this.webId = WebIdUtils.nextAuthorId();
	}
	
	public Author(NewBook.Author newAuthor) {
		this.name = newAuthor.getName();
		this.webId = WebIdUtils.nextAuthorId();
	}
	
	public Author(String webId, String name) {
		this.webId = webId;
		this.name = name;
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
	public int compareTo(Author other) {
		return this.name.compareTo(other.name);
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("Author [webId=").append(webId)
				.append(", name=").append(name)
				.append("]").toString();
	}
}
