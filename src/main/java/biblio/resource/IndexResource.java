package biblio.resource;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by ericmanwill on 6/20/17.
 */
public class IndexResource extends ResourceSupport {
    private String name;
    private String description;

    public IndexResource(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
}
