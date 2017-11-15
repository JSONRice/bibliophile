package biblio.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class UniqueValidator implements ConstraintValidator<Unique, String> {

	@Autowired ApplicationContext applicationContext;
	
	private FieldValueExists service;
	private String fieldName;
	private boolean required;
	
	@Override
	public void initialize(Unique constraint) {
		Class<? extends FieldValueExists> clazz = constraint.service();
		fieldName = constraint.fieldName();
		required = constraint.required();
		service = applicationContext.getBean(clazz);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (!required && value == null) return true;
		return !service.fieldValueExists(fieldName, value);
	}

}
