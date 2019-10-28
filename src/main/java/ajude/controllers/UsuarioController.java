package ajude.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ajude.entities.Usuario;
import ajude.services.UsuarioServices;

@RestController
public class UsuarioController {

	private UsuarioServices usuarioService;
	
	public UsuarioController(UsuarioServices usuarioService) {
		super();
		this.usuarioService = usuarioService;
	}
	
	
	// ajeitar o retorno do erro
	@PostMapping("usuarios")
	public ResponseEntity<Usuario> adicionaUsuario(@RequestBody Usuario usuario) {
		if(!usuarioService.usuarioExiste(usuario.getEmail()))
			return new ResponseEntity<Usuario>(usuarioService.addUsuario(usuario), HttpStatus.OK);
		else
			return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("usuarios")
	public ResponseEntity<List<Usuario>> getUsuarios() {
		return new ResponseEntity<List<Usuario>>(usuarioService.getUsuarios(), HttpStatus.OK);
	}
	
	@GetMapping("usuarios/{email}")
	public ResponseEntity<Usuario> getUsuario(@PathVariable String email) {
		if (!usuarioService.usuarioExiste(email))
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Usuario>(usuarioService.recuperaUsuario(email), HttpStatus.OK);
	}
	
	@PutMapping("usuarios/{email}")
	public ResponseEntity<Usuario> mudarSenha(@RequestBody String senha, @PathVariable String email) {
		return new ResponseEntity<Usuario>(usuarioService.mudarSenha(email, senha), HttpStatus.OK);
	}
	
}