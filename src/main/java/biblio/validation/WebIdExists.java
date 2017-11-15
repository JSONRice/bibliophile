package biblio.validation;

import java.util.List;

public interface WebIdExists {
	
	boolean webIdExists(String webId);
	boolean webIdExists(List<String> webIds);
}
