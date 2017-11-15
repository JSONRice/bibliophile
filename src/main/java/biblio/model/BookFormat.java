package biblio.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BookFormat {
	BOOK ("B", "book"),
	E_BOOK ("EB", "e-book"),
	AUDIOBOOK ("AB", "audiobook"),
	OTHER ("OT", "other"),
	UNKNOWN ("UN", "unknown");
	
	private static final Map<String, BookFormat> map = new HashMap<>();
	
	private String code;
	private String name;
	
	static {
		for (BookFormat format : BookFormat.values()) {
			map.put(format.code, format);
		}
	}
	
	private BookFormat(String code, String name) {
		this.code = code;
		this.name= name;
	}
	
	public static BookFormat of(String code) {
		return Optional.ofNullable(map.get(code))
				.orElseThrow(() -> new IllegalArgumentException("Invalid BookFormat code: " + code));
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
}
