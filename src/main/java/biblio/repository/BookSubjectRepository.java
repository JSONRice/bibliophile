package biblio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import biblio.model.BookSubject;

public interface BookSubjectRepository extends RepositoryWithWebId<BookSubject, Long> {
	
	boolean existsByName(String name);

	Page<BookSubject> findByNameContainsAllIgnoreCase(String name, Pageable pageRequest);
	
}
