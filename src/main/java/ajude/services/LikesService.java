package ajude.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ajude.DAOs.DislikesRepository;
import ajude.DAOs.LikesRepository;
import ajude.entities.Campanha;
import ajude.entities.Dislikes;
import ajude.entities.Likes;

@Service
public class LikesService {
	
	@Autowired
	private DislikesRepository<Dislikes,Long> dislikesRepo;
	@Autowired
	private LikesRepository<Likes,Long> likesRepo;
	@Autowired
	private JWTService jwtService;
	@Autowired
	private CampanhaService campSer;
	
	
	public Likes addLike(Likes like, String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		if (!campSer.campanhaExiste(like.getIdCampanha()))
			throw new Exception("campanha nao existe");
		if (usuarioAlreadyLikedById(email, like.getIdCampanha())) 
			throw new Exception("usuario ja deu like");
		
		if (usuarioAlreadyDislikedById(email, like.getIdCampanha()))
			dislikesRepo.deleteDislike(email, like.getIdCampanha());
		
		like.setEmail(email);
		return likesRepo.save(like);
	}
	
	public Likes deleteLike(String url, String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		if (!usuarioAlreadyLiked(email, url))
			throw new Exception("tentou tirar like que não existe");
		
		Likes like = likesRepo.usuarioLikedByUrl(url, email).get(0);
		likesRepo.deleteLike(email, like.getIdCampanha());
		return like;
	}
	
	public Likes deleteLikeById(long id, String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		if (!usuarioAlreadyLikedById(email, id))
			throw new Exception("tentou tirar like que não existe");
		
		Likes like = likesRepo.usuarioLikedCampanha(email, id).get(0);
		likesRepo.deleteLike(email, id);
		return like;
	}
	
	public List<Likes> getLikesCamp(String url) throws Exception {
		Campanha c = campSer.getCampanha(url);
		return likesRepo.getLikesCampanha(c.getId());
	}
	
	public List<Likes> getLikesUsu(String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		return likesRepo.getLikesUsuario(email);
	}
	
	public boolean usuarioAlreadyDisliked(String email, String url) {
		return dislikesRepo.usuarioDislikedByUrl(email, url).size() > 0;
	}
	
	public boolean usuarioAlreadyDislikedById(String email, long id) {
		return dislikesRepo.usuarioDislikedCampanha(email, id).size() > 0;
	}
	
	public boolean usuarioAlreadyLiked(String email, String url) {
		return likesRepo.usuarioLikedByUrl(email, url).size() > 0;
	}
	
	public boolean usuarioAlreadyLikedById(String email, long id) {
		return likesRepo.usuarioLikedCampanha(email, id).size() > 0;
	}
}