package biblio.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface RepositoryWithWebId<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {
	
	boolean existsByWebId(String webId);
	
	Optional<T> findOneByWebId(String webId);
	
	List<T> findByWebIdIn(List<String> webIds);
}
