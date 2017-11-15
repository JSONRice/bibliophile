package biblio.service.resourceAssembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import biblio.resource.AuthorResource;
import biblio.resource.BookResource;
import biblio.resource.BookSubjectResource;
import biblio.resource.IndexResource;
import biblio.resource.PublisherResource;

import org.springframework.hateoas.*;
import org.springframework.stereotype.Service;

@Service
public class IndexResourceAssembler {
	
	private final RelProvider relProvider;
	private final EntityLinks entityLinks;
	
	public IndexResourceAssembler(RelProvider relProvider, EntityLinks entityLinks) {
		this.relProvider = relProvider;
		this.entityLinks = entityLinks;
	}

	public IndexResource buildIndex() {
		final IndexResource resource = new IndexResource("library", "Description of library goes here.");

		resource.add(linkToCollection(BookResource.class));
		resource.add(linkToCollection(AuthorResource.class));
		resource.add(linkToCollection(BookSubjectResource.class));
		resource.add(linkToCollection(PublisherResource.class));
		
		return resource;
	}
	
	private Link linkToCollection(Class<? extends ResourceSupport> clazz) {
		return entityLinks.linkToCollectionResource(clazz)
				.withRel(relProvider.getCollectionResourceRelFor(clazz));
	}
}
