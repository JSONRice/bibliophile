package biblio.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class WebIdValidator implements ConstraintValidator<WebId, String> {

	@Autowired private ApplicationContext applicationContext;
	
	private WebIdExists service;
	private boolean required;
	
	@Override
	public void initialize(WebId constraint) {
		Class<? extends WebIdExists> clazz = constraint.service();
		service = (WebIdExists) applicationContext.getBean(clazz);
		required = constraint.required();
	}

	@Override
	public boolean isValid(String webId, ConstraintValidatorContext context) {
		if (!required && webId == null) return true;
		return service.webIdExists(webId);
	}

}
