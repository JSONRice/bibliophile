package biblio.service.resourceAssembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RelProvider;
import org.springframework.stereotype.Service;

import biblio.controller.PublisherController;
import biblio.model.Publisher;
import biblio.resource.PublisherResource;
import biblio.util.WebIdUtils;

@Service
public class PublisherResourceAssembler extends EmbeddableResourceAssemblerSupport<Publisher, PublisherResource, PublisherController> {

	@Autowired
	public PublisherResourceAssembler(RelProvider relProvider, EntityLinks entityLinks) {
		super(relProvider, entityLinks, PublisherController.class, PublisherResource.class);
	}
	
	@Override
	public PublisherResource toResource(Publisher entity) {
		return createResourceWithId(entity.getWebId(), entity);
	}
	
	@Override
	public Link linkToSingleResource(Publisher entity) {
		return entityLinks.linkToSingleResource(PublisherResource.class, entity.getWebId());
	}
	
	@Override
	protected PublisherResource instantiateResource(Publisher entity) {
		return new PublisherResource(entity);
	}
}
