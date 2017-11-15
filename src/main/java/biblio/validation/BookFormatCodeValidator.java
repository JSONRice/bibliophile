package biblio.validation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import biblio.model.BookFormat;

public class BookFormatCodeValidator implements ConstraintValidator<BookFormatCode, String> {

	private static List<String> codes = Arrays.asList(BookFormat.values())
			.stream()
			.map(BookFormat::getCode)
			.collect(Collectors.toList());
	
	@Override
	public void initialize(BookFormatCode bookFormatConstraint) {}

	@Override
	public boolean isValid(String formatCode, ConstraintValidatorContext context) {
		return codes.contains(formatCode);
	}
}
