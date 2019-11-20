package ajude.DAOs;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ajude.entities.Comentario;

public interface ComentarioRepository<T, ID extends Serializable> extends JpaRepository<Comentario, Long> {

	@Query("select c from Comentario as c where c.idComentario = ?1 AND c.apagado = FALSE")
	public List<Comentario> pegaIdComen(long id);
	
	@Query("select c from Comentario as c where c.idCampanha = ?1 AND c.apagado = FALSE")
	public List<Comentario> pegaIdCampanha(long id);
	
	@Query("select c from Comentario as c where c.commentOwner = ?1")
	public List<Comentario> pegaComentDono(String email);
}