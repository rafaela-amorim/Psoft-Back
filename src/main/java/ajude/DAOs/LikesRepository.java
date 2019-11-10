package ajude.DAOs;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import ajude.entities.Likes;



public interface LikesRepository<T, ID extends Serializable> extends JpaRepository<Likes, Long> {

	
	@Query("select l from Likes as l where l.idCampanha = ?1")
	public List<Likes> getLikesCampanha(long id);
	
	@Query("select l from Likes as l where l.email = ?1")
	public List<Likes> getLikesUsuario(String email);
	
	@Query("select l from Likes as l where l.email = ?1 and l.idCampanha = ?2")
	public List<Likes> usuarioLikedCampanha(String email, long id);
	
	@Transactional
	@Modifying
	@Query("delete from Likes as l where l.email = ?1 and l.idCampanha = ?2")
	public void deleteLike(String email, long id);
}
