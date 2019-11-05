package ajude.DAOs;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ajude.entities.Campanha;


public interface CampanhaRepository<T, ID extends Serializable> extends JpaRepository<Campanha, Long>  {

	@Query("select c from Campanha as c where lower(nome) like %?1% ")
	List<Campanha> findBySubstring(String substring);
}
