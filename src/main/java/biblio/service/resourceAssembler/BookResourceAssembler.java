package biblio.service.resourceAssembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrapper;
import org.springframework.stereotype.Service;

import biblio.controller.BookController;
import biblio.model.Book;
import biblio.resource.AuthorResource;
import biblio.resource.BookResource;
import biblio.resource.BookSubjectResource;
import biblio.resource.PublisherResource;
import biblio.util.WebIdUtils;

@Service
public class BookResourceAssembler extends EmbeddableResourceAssemblerSupport<Book, BookResource, BookController> {

	@Autowired private AuthorResourceAssembler authorResourceAssembler;
	@Autowired private PublisherResourceAssembler publisherResourceAssembler;
	@Autowired private BookSubjectResourceAssembler bookSubjectResourceAssembler;
	
	@Autowired
	public BookResourceAssembler(RelProvider relProvider, EntityLinks entityLinks) {
		super(relProvider, entityLinks, BookController.class, BookResource.class);
	}
	
	@Override
	public BookResource toResource(Book entity) {
		return createResourceWithId(entity.getWebId(), entity);
	}
	
	public void addEmbeddedResources(BookResource resource, Book entity) {
		List<EmbeddedWrapper> embeddedResources = new ArrayList<>();
		embeddedResources.addAll(authorResourceAssembler.toEmbeddable(entity.getAuthors()));
		embeddedResources.addAll(bookSubjectResourceAssembler.toEmbeddable(entity.getSubjects()));
		embeddedResources.add(publisherResourceAssembler.toEmbeddable(entity.getPublisher()));
		resource.setEmbeddedResources(new Resources<>(embeddedResources));
	}
	
	public void addActionLinks(BookResource resource, Book entity) {
		Link deleteLink = linkTo(methodOn(controllerClass).deleteBook(entity.getWebId())).withRel("delete");

		resource.add(deleteLink);
	}
	
	public void addResourceLinks(BookResource resource, Book entity) {
		final String authorsRel = relProvider.getCollectionResourceRelFor(AuthorResource.class);
		entity.getAuthors().forEach(author -> resource.add(authorResourceAssembler.linkToSingleResource(author).withRel(authorsRel)));
		
		final String publisherRel = relProvider.getItemResourceRelFor(PublisherResource.class);
		resource.add(publisherResourceAssembler.linkToSingleResource(entity.getPublisher()).withRel(publisherRel));
		
		final String bookSubjectsRel = relProvider.getCollectionResourceRelFor(BookSubjectResource.class);
		entity.getSubjects().forEach(subj -> resource.add(bookSubjectResourceAssembler.linkToSingleResource(subj).withRel(bookSubjectsRel)));
	}
	
	@Override
	public Link linkToSingleResource(Book entity) {
		return entityLinks.linkToSingleResource(BookResource.class, entity.getWebId());
	}
	
	@Override
	protected BookResource instantiateResource(Book entity) {
		return new BookResource(entity);
	}
}
