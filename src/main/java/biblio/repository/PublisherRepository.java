package biblio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import biblio.model.Publisher;

public interface PublisherRepository extends RepositoryWithWebId<Publisher, Long> {
	
	boolean existsByName(String name);

	Page<Publisher> findByNameContainsAllIgnoreCase(String name, Pageable pageRequest);
	
}
