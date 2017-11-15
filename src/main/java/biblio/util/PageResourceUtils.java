package biblio.util;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;

import biblio.controller.SearchableController;

public class PageResourceUtils {
	
	public static final String NEXT_PAGE = "next";
	public static final String PREV_PAGE = "previous";
	
	public static <T, R extends ResourceSupport> Resources<R> pageToResources(Page<T> page, Function<T, R> entityToResource) {
		List<R> resources = page.getContent()
				.stream()
				.map(entityToResource)
				.collect(Collectors.toList());
		return new Resources<>(resources);
	}
	
	public static <T, R extends ResourceSupport, C extends SearchableController<R>> void addNextPrevPageLinks(
			Resources<R> resources, Page<T> currentPage, Map<String, String> currentParams, Class<C> controllerClass) {
		int pageNum = currentPage.getNumber();
		if (currentPage.hasNext()) {
			Map<String, String> nextParams = SearchUtils.nextPageParams(currentParams, pageNum + 1);
			Link nextPage = linkTo(methodOn(controllerClass).search(nextParams)).withRel(NEXT_PAGE);
			resources.add(nextPage);
		}
		if (currentPage.hasPrevious()) {
			Map<String, String> prevParams = SearchUtils.nextPageParams(currentParams, pageNum - 1);
			Link prevPage = linkTo(methodOn(controllerClass).search(prevParams)).withRel(PREV_PAGE);
			resources.add(prevPage);
		}
	}
}
