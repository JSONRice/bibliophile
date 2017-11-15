package biblio.dto;

import javax.validation.constraints.NotNull;

import biblio.service.BookSubjectService;
import biblio.validation.Unique;

public class NewSubject {

	@NotNull
	@Unique(fieldName = "name", service = BookSubjectService.class)
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}