package ajude.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sun.mail.iap.Response;

import ajude.classesAuxiliares.Senha;
import ajude.entities.Campanha;
import ajude.entities.Usuario;
import ajude.services.UsuarioService;

@RestController
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	
	@PostMapping("usuarios")
	public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario usuario) {
		try {
			return new ResponseEntity<Usuario>(usuarioService.addUsuario(usuario), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Usuario>(HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("usuarios")
	public ResponseEntity<List<Usuario>> getUsuarios() {
		return new ResponseEntity<List<Usuario>>(usuarioService.getUsuarios(), HttpStatus.OK);
	}
	
	@GetMapping("usuarios/{email}")
	public ResponseEntity<Usuario> getUsuario(@PathVariable String email) {
		try {
			return new ResponseEntity<Usuario>(usuarioService.getUsuario(email), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	} 
	
	@PostMapping("usuarios/mudarSenha/{email}")
	public ResponseEntity<Usuario> emailMudarSenha(@PathVariable String email) {
		try {
			return new ResponseEntity<Usuario>(usuarioService.emailMudarSenha(email), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("auth/usuarios")
	public ResponseEntity<Usuario> mudarSenha(HttpServletRequest request, @RequestBody Senha senha) {
		try {
			String email = (String) request.getAttribute("email");
			return new ResponseEntity<Usuario>(usuarioService.mudarSenha(email, senha.getSenha()), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("usuarios/campanhas/{email}")
	public ResponseEntity<List<Campanha>> getCampanhas(@PathVariable String email){
		try {
			return new ResponseEntity<List<Campanha>>(usuarioService.getCampanhasDono(email),HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<List<Campanha>>(HttpStatus.BAD_REQUEST);
		}
	}
	
}