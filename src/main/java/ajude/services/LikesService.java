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
		if (!campSer.campanhaExiste(like.getUrlCampanha()))
			throw new Exception("campanha nao existe");
		if (usuarioAlreadyLikedByUrl(email, like.getUrlCampanha())) 
			throw new Exception("usuario ja deu like");
		
		if (usuarioAlreadyDislikedByUrl(email, like.getUrlCampanha())) {
			dislikesRepo.deleteDislike(email, like.getUrlCampanha());
			campSer.alteraDislikes(like.getUrlCampanha(), false);
		}
		
		campSer.alteraLike(like.getUrlCampanha(), true);
		
		like.setEmail(email);
		return likesRepo.save(like);
	}
	
	public Likes deleteLike(String url, String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		
		if (!usuarioAlreadyLiked(email, url))
			throw new Exception("tentou tirar like que não existe");

		Likes like = likesRepo.usuarioLikedCampanha(email, url).get(0);
		likesRepo.deleteLike(email, like.getUrlCampanha());
		
		campSer.alteraLike(url, false);
		
		return like;
	}
	
	public Likes deleteLikeByUrl(String url, String token) throws Exception {
		
		String email = jwtService.getEmailToken(token);
		
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		
		if (!usuarioAlreadyLikedByUrl(email, url))
			throw new Exception("tentou tirar like que não existe");
		
		Likes like = likesRepo.usuarioLikedCampanha(email, url).get(0);
		
		likesRepo.deleteLike(email, url);
		
		campSer.alteraLike(url, false);
		
		return like;
	}
	
	public List<Likes> getLikesCamp(String url) throws Exception {
		return likesRepo.getLikesCampanha(url);
	}
	
	public List<Likes> getLikesUsu(String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		return likesRepo.getLikesUsuario(email);
	}
	
	public boolean usuarioAlreadyDisliked(String email, String url) {
		return dislikesRepo.usuarioDislikedCampanha(email, url).size() > 0;
	}
	
	public boolean usuarioAlreadyDislikedByUrl(String email, String url) {
		return dislikesRepo.usuarioDislikedCampanha(email, url).size() > 0;
	}
	
	public boolean usuarioAlreadyLiked(String email, String url) {
		return likesRepo.usuarioLikedCampanha(email, url).size() > 0;
	}
	
	public boolean usuarioAlreadyLikedByUrl(String email, String url) {
		return likesRepo.usuarioLikedCampanha(email, url).size() > 0;
	}
}