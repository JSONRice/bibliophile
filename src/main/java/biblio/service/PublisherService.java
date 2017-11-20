package biblio.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import biblio.controller.PublisherController;
import biblio.dto.NewPublisher;
import biblio.model.Publisher;
import biblio.repository.PublisherRepository;
import biblio.resource.PublisherResource;
import biblio.service.resourceAssembler.PublisherResourceAssembler;
import biblio.util.PageResourceUtils;
import biblio.util.SearchUtils;
import biblio.util.WebIdUtils;
import biblio.validation.FieldValueExists;
import biblio.validation.WebIdExists;

@Service
public class PublisherService implements WebIdExists, FieldValueExists {

	@Autowired private PublisherResourceAssembler publisherResourceAssembler;
	
	@Autowired private PublisherRepository publisherRepository;
	
	private Map<String, String> sortTermMap = new HashMap<>();
	
	public PublisherService() {
		sortTermMap.put("name", "name");
	}
	
	public Resources<PublisherResource> getPublishers(Map<String, String> params) {
		Pageable pageRequest = SearchUtils.paramsToPageRequest(params, sortTermMap);
		Page<Publisher> results;
		
		if (SearchUtils.hasSearchParam(params)) {
			String searchTerm = SearchUtils.getSearchParam(params);
			results = publisherRepository.findByNameContainsAllIgnoreCase(searchTerm, pageRequest);
		} else {
			results = publisherRepository.findAll(pageRequest);
		}
		
		Resources<PublisherResource> resource = PageResourceUtils.pageToResources(results, publisherResourceAssembler::toResource);
		
		PageResourceUtils.addNextPrevPageLinks(resource, results, params, PublisherController.class);
		
		return resource;
	}
	
	public Publisher addPublisher(NewPublisher newPublisher) {
		Publisher publisher = new Publisher(newPublisher);
		publisher.setWebId(WebIdUtils.nextPublisherId());
		return publisherRepository.save(publisher);
	}
	
	@Override
	public boolean webIdExists(String webId) {
		return publisherRepository.existsByWebId(webId);
	}
	
	@Override
	public boolean webIdExists(List<String> webIds) {
		return webIds.stream().allMatch(publisherRepository::existsByWebId);
	}

	@Override
	public boolean fieldValueExists(String fieldName, String value)
            throws UnsupportedOperationException {
		if (fieldName.equals("name"))
			return value != null && publisherRepository.existsByName(value);
		// else
		throw new UnsupportedOperationException("Field \"" + fieldName + "\" not supported");
	}
}
