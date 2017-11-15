package biblio.service.resourceAssembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RelProvider;
import org.springframework.stereotype.Service;

import biblio.controller.BookSubjectController;
import biblio.model.BookSubject;
import biblio.resource.BookSubjectResource;
import biblio.util.WebIdUtils;

@Service
public class BookSubjectResourceAssembler extends EmbeddableResourceAssemblerSupport<BookSubject, BookSubjectResource, BookSubjectController> {

	@Autowired
	public BookSubjectResourceAssembler(RelProvider relProvider, EntityLinks entityLinks) {
		super(relProvider, entityLinks, BookSubjectController.class, BookSubjectResource.class);
	}

	@Override
	public BookSubjectResource toResource(BookSubject entity) {
		return createResourceWithId(entity.getWebId(), entity);
	}

	@Override
	public Link linkToSingleResource(BookSubject entity) {
		return entityLinks.linkToSingleResource(BookSubjectResource.class, entity.getWebId());
	}
	
	@Override
	protected BookSubjectResource instantiateResource(BookSubject entity) {
		return new BookSubjectResource(entity);
	}
}
