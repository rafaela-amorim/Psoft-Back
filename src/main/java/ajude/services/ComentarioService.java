package ajude.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ajude.DAOs.CampanhaRepository;
import ajude.DAOs.ComentarioRepository;
import ajude.DAOs.UsuarioRepository;
import ajude.entities.Campanha;
import ajude.entities.Comentario;
import ajude.entities.Usuario;

@Service
public class ComentarioService {
	
	private CampanhaRepository<Campanha, Long> campanhaRepo;
	private ComentarioRepository<Comentario,Long> comentarioRepo;
	private JWTService jwt;

	public ComentarioService(UsuarioRepository<Usuario, String> usuariosRepo, CampanhaRepository<Campanha, Long> campanhaRepo,ComentarioRepository<Comentario,Long> comentarioRepo,JWTService jwt) {
		super();
		this.campanhaRepo = campanhaRepo;
		this.comentarioRepo = comentarioRepo;
		this.jwt = jwt;
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
		
		String email = jwt.getEmailToken(token);
		if(!jwt.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		
		comentario.setCommentOwner(email);
		comentarioRepo.save(comentario);
		
		
		return comentario;
	}
	

	public Comentario getComentario(Long id) {
		return null;
	}
	
	public List<Comentario> getComentariosResp(long id) {
		return comentarioRepo.pegaIdComen(id);
	}
	
	public List<Comentario> getComentariosCamp(long id) {
		return comentarioRepo.pegaIdCampanha(id);
	}
	
	public List<Comentario> getComentariosDono(String token) throws Exception {
		String email = jwt.getEmailToken(token);
		if(!jwt.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		return comentarioRepo.pegaComentDono(email);
	}
	
	public Comentario deletarComentario(Long id) {
		return null;
	}

	
}
