package ajude.controllers;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ajude.classesAuxiliares.LoginResponse;
import ajude.entities.Usuario;
import ajude.services.JwtServices;
import ajude.services.UsuarioServices;

@RestController
public class LoginController {

	private UsuarioServices usuarioService;
	private JwtServices jwtService;
	
	public LoginController(UsuarioServices usuarioService, JwtServices jwtService) {
		super();
		this.usuarioService = usuarioService;
		this.jwtService = jwtService;
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticate(Usuario user) {
		if (!usuarioService.usuarioExiste(user.getEmail())) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			
			try {
				LoginResponse token = jwtService.authenticate(user);
				return new ResponseEntity<LoginResponse>(token, HttpStatus.OK);
			} catch (ServletException e) {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}
	}
	
}
