package ajude.DAOs;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ajude.entities.Doacao;


@Repository
public interface DoacaoRepository<T, ID extends Serializable> extends JpaRepository<Doacao, Long> {

	
	@Query("select d from Doacao as d where d.emailDoador = ?1")
	public List<Doacao> getDoacoesUsuario(String email);
	
	@Query("select d from Doacao as d where d.urlCampanha = ?1")
	public List<Doacao> getDoacoesCampanha(String id);
}
