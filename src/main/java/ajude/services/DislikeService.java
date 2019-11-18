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
		if (!campSer.campanhaExiste(dislikes.getIdCampanha()))
			throw new Exception("campanha nao existe");
		if (usuarioAlreadyDislikedById(email, dislikes.getIdCampanha())) 
			throw new Exception("usuario ja deu dislike");
		
		if (usuarioAlreadyLikedById(email, dislikes.getIdCampanha()))
			likesRepo.deleteLike(email, dislikes.getIdCampanha());
			
		dislikes.setEmail(email);
		return dislikesRepo.save(dislikes);			
	}
	
	public Dislikes deleteDislike(String url, String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		if (!usuarioAlreadyDisliked(email, url)) 
			throw new Exception("tentou tirar dislike que não existe");
		
		List<Dislikes> l = dislikesRepo.usuarioDislikedByUrl(url, email);
		
		Dislikes dislike = l.get(0);
		dislikesRepo.deleteDislike(email, dislike.getIdCampanha());
		return dislike;
	}
	
	public Dislikes deleteDislikeById(long id, String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		if (!usuarioAlreadyDislikedById(email, id))
			throw new Exception("tentou tirar dislike que não existe");
		
		Dislikes dislike = dislikesRepo.usuarioDislikedCampanha(email, id).get(0);
		dislikesRepo.deleteDislike(email, id);
		return dislike;
	}
	
	public List<Dislikes> getDislikesCamp(String url) throws Exception {
		Campanha c = campSer.getCampanha(url);
		return dislikesRepo.getDislikesCampanha(c.getId());
	}
	
	public List<Dislikes> getDislikesUsu(String token) throws Exception{
		String email = jwtService.getEmailToken(token);
		
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		
		return dislikesRepo.getDislikesUsuario(email);
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