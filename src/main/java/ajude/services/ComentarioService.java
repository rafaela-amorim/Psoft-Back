package ajude.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ajude.DAOs.ComentarioRepository;
import ajude.entities.Comentario;

public class ComentarioService {

	@Autowired
	ComentarioRepository comentarioRepo;
	
	public Comentario salvarComentario(Comentario comentario) {
		return null;
	}

	public Comentario getComentario(Long id) {
		return null;
	}
	
	public List<Comentario> getComentarios() {
		return null;
	}
	
	public Comentario deletarComentario(Long id) {
		return null;
	}

	
}
