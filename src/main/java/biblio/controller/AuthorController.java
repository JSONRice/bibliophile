package biblio.controller;

import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import biblio.dto.NewAuthor;
import biblio.model.Author;
import biblio.repository.AuthorRepository;
import biblio.resource.AuthorResource;
import biblio.service.AuthorService;
import biblio.service.resourceAssembler.AuthorResourceAssembler;
import biblio.util.ResourceUtils;

@RestController
@RequestMapping(ApiPaths.LIBRARY_AUTHORS)
@ExposesResourceFor(AuthorResource.class)
@Transactional // TODO replace this with a better way of working with the persistence layer
public class AuthorController implements SearchableController<AuthorResource> {
	
	@Autowired AuthorService authorService;
	@Autowired AuthorResourceAssembler authorResourceAssembler;
	@Autowired AuthorRepository authorRepository;
	
	@Override
	public Resources<AuthorResource> search(Map<String, String> params) {
		return authorService.getAuthors(params);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public AuthorResource getAuthor(@PathVariable(value = "id") String webId) {
		Author author = ResourceUtils.entityOrNotFoundException(authorRepository.findOneByWebId(webId));
		return authorResourceAssembler.toResource(author);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public AuthorResource addAuthor(@RequestBody @Valid NewAuthor newAuthor) {
		Author author = authorService.addAuthor(newAuthor);
		return authorResourceAssembler.toResource(author);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public @ResponseBody ResponseEntity<?> deleteAuthor(@PathVariable(value = "id") String webId) {
		if (authorService.webIdExists(webId)) {
			authorService.deleteAuthor(webId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
}
