package biblio.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import biblio.controller.BookSubjectController;
import biblio.dto.NewSubject;
import biblio.model.BookSubject;
import biblio.repository.BookSubjectRepository;
import biblio.resource.BookSubjectResource;
import biblio.service.resourceAssembler.BookSubjectResourceAssembler;
import biblio.util.PageResourceUtils;
import biblio.util.SearchUtils;
import biblio.util.WebIdUtils;
import biblio.validation.FieldValueExists;
import biblio.validation.WebIdExists;

@Service
public class BookSubjectService implements WebIdExists, FieldValueExists {

	@Autowired private BookSubjectResourceAssembler bookSubjectResourceAssembler;
	
	@Autowired private BookSubjectRepository bookSubjectRepository;
	
	private Map<String, String> sortTermMap = new HashMap<>();
	
	public BookSubjectService() {
		sortTermMap.put("name", "name");
	}
	
	public Resources<BookSubjectResource> getBookSubjects(Map<String, String> params) {
		Pageable pageRequest = SearchUtils.paramsToPageRequest(params, sortTermMap);
		Page<BookSubject> results;
		
		if (SearchUtils.hasSearchParam(params)) {
			String searchTerm = SearchUtils.getSearchParam(params);
			results = bookSubjectRepository.findByNameContainsAllIgnoreCase(searchTerm, pageRequest);
		} else {
			results = bookSubjectRepository.findAll(pageRequest);
		}
		
		Resources<BookSubjectResource> resource = PageResourceUtils.pageToResources(results, bookSubjectResourceAssembler::toResource);
		
		PageResourceUtils.addNextPrevPageLinks(resource, results, params, BookSubjectController.class);
		
		return resource;
	}
	
	public BookSubject addBookSubject(NewSubject newSubject) {
		BookSubject bookSubject = new BookSubject(newSubject);
		bookSubject.setWebId(WebIdUtils.nextSubjectId());
		return bookSubjectRepository.save(bookSubject);
	}
	
	@Override
	public boolean webIdExists(String webId) {
		return bookSubjectRepository.existsByWebId(webId);
	}
	
	@Override
	public boolean webIdExists(List<String> webIds) {
		return webIds.stream().allMatch(bookSubjectRepository::existsByWebId);
	}

	@Override
	public boolean fieldValueExists(String fieldName, String value) {
		if (fieldName.equals("name")) {
			if (value == null) return false;
			return bookSubjectRepository.existsByName(value);
		} else {
			throw new UnsupportedOperationException("Field \"" + fieldName + "\" not supported");
		}
	}
}
