package biblio.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import biblio.model.BookFormat;
import biblio.resource.IndexResource;
import biblio.service.resourceAssembler.IndexResourceAssembler;

@RestController
@RequestMapping("/api")
public class IndexController {
	
	@Autowired private IndexResourceAssembler indexResourceAssembler;
	
	@RequestMapping(method = RequestMethod.GET)
	public IndexResource getIndex() {
		return indexResourceAssembler.buildIndex();
	}
	
	@RequestMapping(value = "/utils/formats")
	public List<BookFormat> getSupportedBookFormats() {
		return Arrays.asList(BookFormat.values());
	}
}
