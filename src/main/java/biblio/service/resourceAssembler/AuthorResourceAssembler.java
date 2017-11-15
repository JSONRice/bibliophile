package biblio.service.resourceAssembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RelProvider;
import org.springframework.stereotype.Service;

import biblio.controller.AuthorController;
import biblio.model.Author;
import biblio.resource.AuthorResource;
import biblio.util.WebIdUtils;

@Service
public class AuthorResourceAssembler extends EmbeddableResourceAssemblerSupport<Author, AuthorResource, AuthorController> {

	@Autowired
	public AuthorResourceAssembler(RelProvider relProvider, EntityLinks entityLinks) {
		super(relProvider, entityLinks, AuthorController.class, AuthorResource.class);
	}

	@Override
	public AuthorResource toResource(Author entity) {
		return createResourceWithId(entity.getWebId(), entity);
	}

	@Override
	public Link linkToSingleResource(Author entity) {
		return entityLinks.linkToSingleResource(AuthorResource.class, entity.getWebId());
	}

	@Override
	protected AuthorResource instantiateResource(Author entity) {
		return new AuthorResource(entity);
	}
}
