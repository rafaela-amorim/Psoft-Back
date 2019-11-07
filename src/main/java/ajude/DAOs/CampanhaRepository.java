package ajude.DAOs;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ajude.entities.Campanha;

public interface CampanhaRepository<T, ID extends Serializable> extends JpaRepository<Campanha, Long> {

	@Query("select c from Campanha as c where lower(nome) like %?1% ")
	List<Campanha> findBySubstring(String substring);
	
	@Query("select c from Campanha as c where c.url = ?1")
	Optional<Campanha> findByUrl(String url);
	
	@Query("select c from Campanha as c")
	List<Campanha> countAll();
}
