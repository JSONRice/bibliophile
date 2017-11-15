package biblio.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import biblio.dto.NewBook;
import biblio.dto.NewPublisher;
import biblio.util.WebIdUtils;

@Entity
@Table(name="publishers")
public class Publisher extends AbstractPersistable<Long> {

	private static final long serialVersionUID = 9181885644727673613L;
	
	@Column(name="web_id", columnDefinition="VARCHAR(10) UNIQUE NOT NULL")
	private String webId;
	
	@Column(name="name", columnDefinition="VARCHAR(100)")
	private String name;
	
	@OneToMany(mappedBy="publisher")
	private Set<Book> books;

	public Publisher() {}
	
	public Publisher(String webId, String name) {
		this.webId = webId;
		this.name = name;
	}
	
	public Publisher(NewPublisher newPublisher) {
		this.name = newPublisher.getName();
		this.webId = WebIdUtils.nextPublisherId();
	}
	
	public Publisher(NewBook.Publisher newPublisher) {
		this.name = newPublisher.getName();
		this.webId = WebIdUtils.nextPublisherId();
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
	
	
}
