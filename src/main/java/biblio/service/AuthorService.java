package biblio.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import biblio.controller.AuthorController;
import biblio.dto.NewAuthor;
import biblio.model.Author;
import biblio.repository.AuthorRepository;
import biblio.resource.AuthorResource;
import biblio.service.resourceAssembler.AuthorResourceAssembler;
import biblio.util.PageResourceUtils;
import biblio.util.SearchUtils;
import biblio.util.WebIdUtils;
import biblio.validation.FieldValueExists;
import biblio.validation.WebIdExists;

@Service
public class AuthorService implements WebIdExists, FieldValueExists {
	
	@Autowired private AuthorResourceAssembler authorResourceAssembler;
	@Autowired private AuthorRepository authorRepository;
	
	private Map<String, String> sortTermMap = new HashMap<>();
	
	public AuthorService() {
		sortTermMap.put("name", "name");
	}
	
	public Resources<AuthorResource> getAuthors(Map<String, String> params) {
		Pageable pageRequest = SearchUtils.paramsToPageRequest(params, sortTermMap);
		Page<Author> results;
		
		if (SearchUtils.hasSearchParam(params)) {
			String searchTerm = SearchUtils.getSearchParam(params);
			results = authorRepository.findByNameContainsAllIgnoreCase(searchTerm, pageRequest);
		} else {
			results = authorRepository.findAll(pageRequest);
		}
		
		Resources<AuthorResource> resource = PageResourceUtils.pageToResources(results, authorResourceAssembler::toResource);
		
		PageResourceUtils.addNextPrevPageLinks(resource, results, params, AuthorController.class);
		
		return resource;
	}
	
	public AuthorResource toResource(Author author) {
		return authorResourceAssembler.toResource(author);
	}
	
	public Author addAuthor(NewAuthor newAuthor) {
		Author author = new Author(newAuthor);
		author.setWebId(WebIdUtils.nextAuthorId());
		return authorRepository.save(author);
	}
	
	@Override
	public boolean webIdExists(String webId) {
		return authorRepository.existsByWebId(webId);
	}

	@Override
	public boolean webIdExists(List<String> webIds) {
		return webIds.stream().allMatch(authorRepository::existsByWebId);
	}

	@Override
	public boolean fieldValueExists(String fieldName, String value) throws UnsupportedOperationException {
		if (fieldName.equals("name"))
			return value != null && authorRepository.existsByName(value);
		// else
		throw new UnsupportedOperationException("Field \"" + fieldName + "\" not supported");
	}

	public void deleteAuthor(String webId) {
		authorRepository.findOneByWebId(webId).ifPresent(authorRepository::delete);
	}
}
