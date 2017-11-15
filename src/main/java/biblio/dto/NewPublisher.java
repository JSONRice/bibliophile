package biblio.dto;

import javax.validation.constraints.NotNull;

import biblio.service.PublisherService;
import biblio.validation.Unique;

public class NewPublisher {

	@NotNull
	@Unique(fieldName = "name", service = PublisherService.class)
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}