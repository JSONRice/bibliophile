package biblio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import biblio.model.Author;

public interface AuthorRepository extends RepositoryWithWebId<Author, Long> {
	
	boolean existsByName(String name);

	Page<Author> findByNameContainsAllIgnoreCase(String name, Pageable pageRequest);
}
