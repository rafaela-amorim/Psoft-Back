package ajude.DAOs;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ajude.entities.Campanha;


public interface CampanhaRepository<T, ID extends Serializable> extends JpaRepository<Campanha, Long>  {

	@Query("select c.nome as nome \n" + 
			"	from Campanha  as c\n" + 
			"	where nome like '%?1%' ")
	List<Campanha> findBySubstring(String substring);
}
