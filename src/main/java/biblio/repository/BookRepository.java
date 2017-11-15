package biblio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import biblio.model.Book;

public interface BookRepository extends RepositoryWithWebId<Book, Long> {
	
	Page<Book> findByTitleContainsOrSummaryContainsAllIgnoreCase(String title, String summary, Pageable pageRequest);
	
}
