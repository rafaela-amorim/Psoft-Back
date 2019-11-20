package ajude.DAOs;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import ajude.entities.Dislikes;
import ajude.entities.Likes;



public interface LikesRepository<T, ID extends Serializable> extends JpaRepository<Likes, Long> {

	
	@Query("select l from Likes as l where l.urlCampanha = ?1")
	public List<Likes> getLikesCampanha(String id);
	
	@Query("select l from Likes as l where l.email = ?1")
	public List<Likes> getLikesUsuario(String email);
	
	@Query("select l from Likes as l where l.email = ?1 and l.urlCampanha = ?2")
	public List<Likes> usuarioLikedCampanha(String email, String url);
	
	@Transactional
	@Modifying
	@Query("delete from Likes as l where l.email = ?1 and l.urlCampanha = ?2")
	public void deleteLike(String email, String url);
	
}
