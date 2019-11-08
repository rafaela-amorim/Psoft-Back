package ajude.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ajude.DAOs.CampanhaRepository;
import ajude.DAOs.ComentarioRepository;
import ajude.entities.Campanha;
import ajude.entities.Comentario;

@Service
public class ComentarioService {
	
	private CampanhaRepository<Campanha, Long> campanhaRepo;
	private ComentarioRepository<Comentario,Long> comentarioRepo;
	private JWTService jwtService;

	public ComentarioService(CampanhaRepository<Campanha, Long> campanhaRepo,ComentarioRepository<Comentario,Long> comentarioRepo,JWTService jwtService) {
		super();
		this.campanhaRepo = campanhaRepo;
		this.comentarioRepo = comentarioRepo;
		this.jwtService = jwtService;
	}
	
	public Comentario addComentario(Comentario comentario, String token) throws Exception {
//		Optional<Campanha> c = campanhaRepo.findById(comentario.getIdCampanha());
//		if (!c.isPresent())
//			throw new Exception();
		
		
//		c.get().addComentario(comentario);
//		campanhaRepo.save(c.get());
		
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
	
	public List<Comentario> getComentariosResp(long id) {
		return comentarioRepo.pegaIdComen(id);
	}
	
	public List<Comentario> getComentariosCamp(long id) {
		return comentarioRepo.pegaIdCampanha(id);
	}
	
	public List<Comentario> getComentariosDono(String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		return comentarioRepo.pegaComentDono(email);
	}
	
	public Comentario deletarComentario(Long id, String token) {
		String email = jwtService.getEmailToken(token);
		Optional<Comentario> c = getComentario(id);
		
		if (c.isPresent() && c.get().getCommentOwner().equals(email))
			c.get().setApagado(true);
		
		comentarioRepo.save(c.get());
		
		return c.get();
	}
}
