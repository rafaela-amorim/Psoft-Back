package ajude.services;

import java.util.List;
import java.util.Optional;

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
		if (usuarioAlreadyLiked(email, like.getIdCampanha())) 
			throw new Exception("usuario ja deu like");
		
		if (usuarioAlreadyDisliked(email, like.getIdCampanha()))
			deleteDislike(like.getIdCampanha(), token);
		
		like.setEmail(email);
		return likesRepo.save(like);
	}
	
	public Dislikes addDislike(Dislikes dislikes, String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		
		if(!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		if(!campSer.campanhaExiste(dislikes.getIdCampanha()))
			throw new Exception("campanha nao existe");
		if (usuarioAlreadyDisliked(email, dislikes.getIdCampanha())) 
			throw new Exception("usuario ja deu dislike");
		
		if (usuarioAlreadyLiked(email, dislikes.getIdCampanha())) 
			deleteLike(dislikes.getIdCampanha(), token);
			
		dislikes.setEmail(email);
		return dislikesRepo.save(dislikes);			
	}
	
	public Likes deleteLikeById(long id, String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		
		Optional<Likes> likeOpt = likesRepo.findById(id);
		if (likeOpt.isPresent()) 
			throw new Exception("tentou tirar like que não existe");
		
		Likes like = likeOpt.get();
		likesRepo.delete(like);
		return like;
	}
	
	public Dislikes deleteDislikebyId(long id, String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		
		Optional<Dislikes> dislikeOpt = dislikesRepo.findById(id);
		if (dislikeOpt.isPresent()) 
			throw new Exception("tentou tirar dislike que não existe");
		
		Dislikes dislike = dislikeOpt.get();
		dislikesRepo.delete(dislike);
		return dislike;
	}
	
	public Likes deleteLike(long idCampanha, String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		if (!usuarioAlreadyLiked(email, idCampanha))
			throw new Exception("tentou tirar like que não existe");
		
		Likes like = likesRepo.usuarioLikedCampanha(email, idCampanha).get(0);
		likesRepo.deleteLike(email, idCampanha);
		return like;
	}
	

	public Dislikes deleteDislike(long idCampanha, String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		if (!usuarioAlreadyDisliked(email, idCampanha)) 
			throw new Exception("tentou tirar dislike que não existe");
		
		Dislikes dislike = dislikesRepo.usuarioDislikedCampanha(email, idCampanha).get(0);
		dislikesRepo.deleteDislike(email, idCampanha);
		return dislike;
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
