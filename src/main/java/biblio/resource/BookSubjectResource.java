package biblio.resource;

import org.springframework.hateoas.core.Relation;

import biblio.model.BookSubject;

@Relation(value = "subject", collectionRelation = "subjects")
public class BookSubjectResource extends ResourceWithEmbedded {
	
	private String name;
	
	public BookSubjectResource() {}
	
	public BookSubjectResource(BookSubject subject) {
		this.name = subject.getName();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
