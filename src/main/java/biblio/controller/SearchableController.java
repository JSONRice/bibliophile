package biblio.controller;

import java.util.Map;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface SearchableController<R extends ResourceSupport> {
	
	@RequestMapping(method = RequestMethod.GET)
	Resources<R> search(@RequestParam Map<String, String> params);
}
