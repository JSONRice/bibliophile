package biblio.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrapper;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public abstract class ResourceWithEmbedded extends ResourceSupport {
	
	@JsonUnwrapped
	protected Resources<EmbeddedWrapper> embeddedResources;
	
	public Resources<EmbeddedWrapper> getEmbeddedResources() {
		return embeddedResources;
	}

	public void setEmbeddedResources(Resources<EmbeddedWrapper> embeddedResources) {
		this.embeddedResources = embeddedResources;
	}
}
