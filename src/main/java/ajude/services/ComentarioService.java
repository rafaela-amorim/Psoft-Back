package ajude.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ajude.DAOs.CampanhaRepository;
import ajude.DAOs.ComentarioRepository;
import ajude.entities.Campanha;
import ajude.entities.Comentario;

@Service
public class ComentarioService {
	
	@Autowired
	private CampanhaRepository<Campanha, Long> campanhaRepo;
	@Autowired
	private ComentarioRepository<Comentario,Long> comentarioRepo;
	@Autowired
	private CampanhaService campanhaService;
	@Autowired
	private JWTService jwtService;

	public ComentarioService() {
		super();
	}
	
	public Comentario addComentario(Comentario comentario, String token) throws Exception {
		long idcomen = comentario.getIdComentario();
		long idcamp = comentario.getIdCampanha();
		if(!comentarioRepo.findById(idcomen).isPresent() && !campanhaRepo.findById(idcamp).isPresent())
			throw new Exception("nao existe comentario ou campanha com esse id");
		
		String email = jwtService.getEmailToken(token);
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		
		comentario.setCommentOwner(email);
		comentarioRepo.save(comentario);
		
		
		return comentario;
	}

	public Optional<Comentario> getComentario(Long id) {
		return comentarioRepo.findById(id);
	}
	
	public List<Comentario> getComentariosResp(long id) throws Exception {
		if (!comentarioRepo.findById(id).isPresent())
			throw new Exception("comentario nao existe");
		return comentarioRepo.pegaIdComen(id);
	}
	
	public List<Comentario> getComentariosCamp(String url) throws Exception {
		Campanha c = campanhaService.getCampanha(url);
		return comentarioRepo.pegaIdCampanha(c.getId());
	}
	
	public List<Comentario> getComentariosDono(String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		return comentarioRepo.pegaComentDono(email);
	}
	
	public Comentario deletarComentario(Long id, String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		Optional<Comentario> c = getComentario(id);
		
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		
		if (c.get().getCommentOwner().equals(email)) {
			if (c.isPresent())
				c.get().setApagado(true);
		} else {
			throw new Exception("usuário não é o dono");
		}
		
		comentarioRepo.save(c.get());
		
		return c.get();
	}
}
