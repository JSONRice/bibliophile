package biblio.resource;

import org.springframework.hateoas.core.Relation;

import biblio.model.Publisher;

@Relation(value = "publisher", collectionRelation = "publishers")
public class PublisherResource extends ResourceWithEmbedded {
	
	private String name;
	
	public PublisherResource() {}
	
	public PublisherResource(Publisher publisher) {
		this.name = publisher.getName();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
