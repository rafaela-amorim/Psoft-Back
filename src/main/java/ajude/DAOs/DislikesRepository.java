package ajude.DAOs;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ajude.entities.Dislikes;


@Repository
public interface DislikesRepository<T, ID extends Serializable> extends JpaRepository<Dislikes, Long>  {

	
	@Query("select d from Dislikes as d where d.idCampanha = ?1")
	public List<Dislikes> getDislikesCampanha(long id);
	
	@Query("select d from Dislikes as d where d.email = ?1")
	public List<Dislikes> getDislikesUsuario(String email);
}
