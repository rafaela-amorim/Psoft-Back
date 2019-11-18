package ajude.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import ajude.entities.Comentario;
import ajude.services.ComentarioService;

@RestController
public class ComentarioController {
	
	@Autowired
	private ComentarioService comentarioService;

	
	@PostMapping("auth/comentario")
	public ResponseEntity<Comentario> addComentario(@RequestBody Comentario comentario, @RequestHeader("Authorization") String token) throws Exception {
		try {
			return new ResponseEntity<Comentario>(comentarioService.addComentario(comentario, token), HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<Comentario>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("comentario/campanha/{url}")
	public ResponseEntity<List<Comentario>> getComentarioCampanha(@PathVariable String url) throws Exception {
		try {
			return new ResponseEntity<List<Comentario>>(comentarioService.getComentariosCamp(url), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("comentario/respostas/{id}")
	public ResponseEntity<List<Comentario>> getComentarioResp(@PathVariable long id) {
		try {
			return new ResponseEntity<List<Comentario>>(comentarioService.getComentariosResp(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("auth/comentario/usuario")
	public ResponseEntity<List<Comentario>> getComentarioUsuario(@RequestHeader("Authorization") String token) {
		try {
			return new ResponseEntity<List<Comentario>>(comentarioService.getComentariosDono(token), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<List<Comentario>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("auth/comentario/deletar/{id}")
	public ResponseEntity<Comentario> deletarComentario(@PathVariable Long id, @RequestHeader("Authorization") String token) {
		try {
			return new ResponseEntity<Comentario>(comentarioService.deletarComentario(id, token), HttpStatus.OK);
		} catch (Exception e) {
			if (e.getMessage().equals("usuário não é o dono"))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}