package biblio.resource;

import org.springframework.hateoas.core.Relation;

import biblio.model.Author;

@Relation(value = "author", collectionRelation = "authors")
public class AuthorResource extends ResourceWithEmbedded {
	
	private String name;
	
	public AuthorResource() {}
	
	public AuthorResource(Author author) {
		this.name = author.getName();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
