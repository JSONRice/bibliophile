package biblio.service.resourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrapper;
import org.springframework.hateoas.core.EmbeddedWrappers;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public abstract class EmbeddableResourceAssemblerSupport<T, D extends ResourceSupport, C> extends ResourceAssemblerSupport<T, D> {

	protected final RelProvider relProvider;
	protected final EntityLinks entityLinks;
	protected final Class<C> controllerClass;
	
	public EmbeddableResourceAssemblerSupport(final RelProvider relProvider, final EntityLinks entityLinks, Class<C> controllerClass, Class<D> resourceType) {
		super(controllerClass, resourceType);
		this.relProvider = relProvider;
		this.entityLinks = entityLinks;
		this.controllerClass = controllerClass;
	}
	
	/**
	 * Creates a wrapped collection of resources representing a collection of entities,
	 * to be added to the _embedded resources of the containing resource.
	 * 
	 * <p>It relies on the embedded resource being annotated  with {@link Relation} and the
	 * collection of embedded entities being annotated with {@Link JsonUnwrapped} (see).
	 */
	public List<EmbeddedWrapper> toEmbeddable(Iterable<T> entities) {
		final EmbeddedWrappers wrappers = new EmbeddedWrappers(true); // prefer creating a collection
		final List<D> resources = toResources(entities);
		return resources.stream().map(wrappers::wrap).collect(Collectors.toList());
	}
	
	/**
	 * Creates a wrapped resource representing a single entity, to be added to the _embedded resources of the containing resource.
	 */
	public EmbeddedWrapper toEmbeddable(T entity) {
		final EmbeddedWrappers wrapper = new EmbeddedWrappers(false); // prefer creating a single object
		final D resource = toResource(entity);
		return wrapper.wrap(resource);
	}
	
	/**
	 * Creates an empty primary resource object wrapping a list of representing a collection of entities.
	 * This is done because of how HAL expects collections to be returned.
	 */
	public Resources<D> toEmbeddedList(Iterable<T> entities) {
		final List<D> resources = toResources(entities);
		return new Resources<>(resources, ControllerLinkBuilder.linkTo(controllerClass).withSelfRel());
	}
	
	public abstract Link linkToSingleResource(T entity);
}
