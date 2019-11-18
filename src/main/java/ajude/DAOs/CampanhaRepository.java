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
	
	@Query("select distinct c from Campanha as c, Doacao as d where c.url = d.urlCampanha AND d.emailDoador = ?1")
	List<Campanha> campanhasDoador(String email);
	
	@Query("select c from Campanha as c where lower(c.nome) like %?1% or lower(c.descricao) like %?1%")
	List<Campanha> findByDescrSubstr(String substring);
}
