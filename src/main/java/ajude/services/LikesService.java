package ajude.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ajude.DAOs.DislikesRepository;
import ajude.DAOs.LikesRepository;
import ajude.entities.Dislikes;
import ajude.entities.Likes;

@Service
public class LikesService {
	
	@Autowired
	private LikesRepository<Likes,Long> likesRepo;
	@Autowired
	private DislikesRepository<Dislikes,Long> dislikesRepo;
	@Autowired
	private JWTService jwtService;
	@Autowired
	private CampanhaService campSer;
	
	public LikesService() {
		super();
	}
	
	
	public Likes addLike(Likes like, String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		
		if(!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		if(!campSer.campanhaExiste(like.getIdCampanha()))
			throw new Exception("campanha nao existe");
		
		if (!usuarioAlreadyLiked(email, like.getIdCampanha())) {
			like.setEmail(email);
			return likesRepo.save(like);
		} else 
			throw new Exception("usuario ja deu like");
	}
	
	public Dislikes addDislike(Dislikes dislikes, String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		
		if(!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		if(!campSer.campanhaExiste(dislikes.getIdCampanha()))
			throw new Exception("campanha nao existe");
		
		if (!usuarioAlreadyDisliked(email, dislikes.getIdCampanha())) {
			dislikes.setEmail(email);
			return dislikesRepo.save(dislikes);
		} else 
			throw new Exception("usuario ja deu dislike");
	}
	
	public List<Likes> getLikesCamp(long id) throws Exception{
		if(!campSer.campanhaExiste(id))
			throw new Exception("campanha nao existe");
		return likesRepo.getLikesCampanha(id);
	}
	
	public List<Likes> getLikesUsu(String token) throws Exception{
		String email = jwtService.getEmailToken(token);
		if(!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		return likesRepo.getLikesUsuario(email);
	}
	
	public List<Dislikes> getDislikesCamp(long id) throws Exception{
		if(!campSer.campanhaExiste(id))
			throw new Exception("campanha nao existe");
		return dislikesRepo.getDislikesCampanha(id);
	}
	
	public List<Dislikes> getDislikesUsu(String token) throws Exception{
		String email = jwtService.getEmailToken(token);
		if(!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		return dislikesRepo.getDislikesUsuario(email);
	}
	
	public boolean usuarioAlreadyLiked(String email, long id) {
		return likesRepo.usuarioLikedCampanha(email, id).size() > 0;
	}
	
	public boolean usuarioAlreadyDisliked(String email, long id) {
		return dislikesRepo.usuarioDislikedCampanha(email, id).size() > 0;
	}
}
