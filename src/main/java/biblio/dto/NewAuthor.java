package biblio.dto;

import javax.validation.constraints.NotNull;

import biblio.service.AuthorService;
import biblio.validation.Unique;

public class NewAuthor {
	
	@NotNull
	@Unique(fieldName = "name", service = AuthorService.class)
	private String name;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
