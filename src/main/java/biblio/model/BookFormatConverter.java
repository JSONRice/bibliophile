package biblio.model;

import javax.persistence.AttributeConverter;

public class BookFormatConverter implements AttributeConverter<BookFormat, String> {

	@Override
	public String convertToDatabaseColumn(BookFormat format) {
		return format.getCode();
	}

	@Override
	public BookFormat convertToEntityAttribute(String code) {
		return BookFormat.of(code);
	}
}
