package ajude.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ajude.entities.Comentario;
import ajude.services.ComentarioService;

@RestController
public class ComentarioController {
	
	@Autowired
	ComentarioService comentarioService;
	
	@DeleteMapping("comentario/deletar/{id}")
	public ResponseEntity<Comentario> deletarComentario(@PathVariable Long id) {
		return new ResponseEntity<Comentario>(comentarioService.deletarComentario(id), HttpStatus.OK);
	}
	
}
