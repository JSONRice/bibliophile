package biblio.controller;

import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import biblio.dto.NewSubject;
import biblio.model.BookSubject;
import biblio.repository.BookSubjectRepository;
import biblio.resource.BookSubjectResource;
import biblio.service.BookSubjectService;
import biblio.service.resourceAssembler.BookSubjectResourceAssembler;
import biblio.util.ResourceUtils;

@RestController
@RequestMapping(ApiPaths.LIBRARY_SUBJECTS)
@ExposesResourceFor(BookSubjectResource.class)
@Transactional // TODO replace this with a better way of working with the persistence layer
public class BookSubjectController implements SearchableController<BookSubjectResource> {
	
	@Autowired BookSubjectService bookSubjectService;
	@Autowired BookSubjectResourceAssembler bookSubjectResourceAssembler;
	@Autowired BookSubjectRepository bookSubjectRepository;
	
	@Override
	public Resources<BookSubjectResource> search(Map<String, String> params) {
		return bookSubjectService.getBookSubjects(params);
	}
	
	@RequestMapping(value = "/{webId}", method = RequestMethod.GET)
	public BookSubjectResource getBookSubject(@PathVariable("webId") String webId) {
		BookSubject BookSubject = ResourceUtils.entityOrNotFoundException(bookSubjectRepository.findOneByWebId(webId));
		return bookSubjectResourceAssembler.toResource(BookSubject);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public BookSubjectResource addBookSubject(@RequestBody @Valid NewSubject newSubject) {
		BookSubject bookSubject = bookSubjectService.addBookSubject(newSubject);
		return bookSubjectResourceAssembler.toResource(bookSubject);
	}
}
