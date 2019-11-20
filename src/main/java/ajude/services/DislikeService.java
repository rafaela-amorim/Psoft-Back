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
public class DislikeService {
	
	@Autowired
	private DislikesRepository<Dislikes,Long> dislikesRepo;
	@Autowired
	private LikesRepository<Likes,Long> likesRepo;
	@Autowired
	private JWTService jwtService;
	@Autowired
	private CampanhaService campSer;
	
	
	public Dislikes addDislike(Dislikes dislikes, String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		if (!campSer.campanhaExiste(dislikes.getUrlCampanha()))
			throw new Exception("campanha nao existe");
		if (usuarioAlreadyDislikedByUrl(email, dislikes.getUrlCampanha())) 
			throw new Exception("usuario ja deu dislike");
		
		if (usuarioAlreadyLikedByUrl(email, dislikes.getUrlCampanha())) {
			likesRepo.deleteLike(email, dislikes.getUrlCampanha());
			campSer.alteraLike(dislikes.getUrlCampanha(), false);
		}
		
		campSer.alteraDislikes(dislikes.getUrlCampanha(), true);
			
		dislikes.setEmail(email);
		return dislikesRepo.save(dislikes);			
	}
	
	public Dislikes deleteDislike(String url, String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		if (!usuarioAlreadyDisliked(email, url)) 
			throw new Exception("tentou tirar dislike que não existe");
		
		List<Dislikes> l = dislikesRepo.usuarioDislikedCampanha(email, url);
		Dislikes dislike = l.get(0);
		dislikesRepo.deleteDislike(email, dislike.getUrlCampanha());
		return dislike;
	}
	
	public Dislikes deleteDislikeByUrl(String url, String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		if (!usuarioAlreadyDislikedByUrl(email, url))
			throw new Exception("tentou tirar dislike que não existe");
		
		Dislikes dislike = dislikesRepo.usuarioDislikedCampanha(email, url).get(0);
		dislikesRepo.deleteDislike(email, url);
		return dislike;
	}
	
	public List<Dislikes> getDislikesCamp(String url) throws Exception {
		return dislikesRepo.getDislikesCampanha(url);
	}
	
	public List<Dislikes> getDislikesUsu(String token) throws Exception{
		String email = jwtService.getEmailToken(token);
		
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		
		return dislikesRepo.getDislikesUsuario(email);
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