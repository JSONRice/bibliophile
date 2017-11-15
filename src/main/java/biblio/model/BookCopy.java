package biblio.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="copies")
public class BookCopy extends AbstractPersistable<Long> {
	
	private static final long serialVersionUID = -4124034596723209598L;

	@Column(name="web_id", columnDefinition="VARCHAR(10)", unique = true, nullable = false)
	private String webId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="book_id", referencedColumnName="id", nullable=false, updatable=true)
	private Book book;
	
	public String getWebId() {
		return webId;
	}
	public void setWebId(String webId) {
		this.webId = webId;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
}
