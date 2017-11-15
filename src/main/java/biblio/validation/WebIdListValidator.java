package biblio.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class WebIdListValidator implements ConstraintValidator<WebIdList, List<String>> {

	@Autowired private ApplicationContext applicationContext;
	
	private WebIdExists service;
	
	@Override
	public void initialize(WebIdList constraint) {
		Class<? extends WebIdExists> clazz = constraint.service();
		service = (WebIdExists) applicationContext.getBean(clazz);
	}

	@Override
	public boolean isValid(List<String> webIds, ConstraintValidatorContext context) {
		return service.webIdExists(webIds);
	}

}
