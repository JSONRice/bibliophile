package biblio.util;

import java.util.Optional;

import biblio.resource.ResourceNotFoundException;

public class ResourceUtils {
	
	private ResourceUtils() {}
	
	public static <T> T entityOrNotFoundException(T entity) {
		return entityOrNotFoundException(Optional.ofNullable(entity));
	}
	
	public static <T> T entityOrNotFoundException(Optional<T> optional) {
		return optional.orElseThrow(ResourceNotFoundException::new);
	}
}
