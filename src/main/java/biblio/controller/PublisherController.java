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

import biblio.dto.NewPublisher;
import biblio.model.Publisher;
import biblio.repository.PublisherRepository;
import biblio.resource.PublisherResource;
import biblio.service.PublisherService;
import biblio.service.resourceAssembler.PublisherResourceAssembler;
import biblio.util.ResourceUtils;

@RestController
@RequestMapping(ApiPaths.LIBRARY_PUBLISHERS)
@ExposesResourceFor(PublisherResource.class)
@Transactional // TODO replace this with a better way of working with the persistence layer
public class PublisherController implements SearchableController<PublisherResource> {
	
	@Autowired PublisherService publisherService;
	@Autowired PublisherResourceAssembler publisherResourceAssembler;
	@Autowired PublisherRepository publisherRepository;
	
	@Override
	public Resources<PublisherResource> search(Map<String, String> params) {
		return publisherService.getPublishers(params);
	}
	
	@RequestMapping(value = "/{webId}", method = RequestMethod.GET)
	public PublisherResource getPublisher(@PathVariable("webId") String webId) {
		Publisher publisher = ResourceUtils.entityOrNotFoundException(publisherRepository.findOneByWebId(webId));
		return publisherResourceAssembler.toResource(publisher);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public PublisherResource addPublisher(@RequestBody @Valid NewPublisher newPublisher) {
		Publisher publisher = publisherService.addPublisher(newPublisher);
		return publisherResourceAssembler.toResource(publisher);
	}
}
