package ajude.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ajude.classesAuxiliares.LoginResponse;
import ajude.entities.Usuario;
import ajude.services.JWTService;
import ajude.services.UsuarioService;

@RestController
public class LoginController {

	private UsuarioService usuarioService;
	private JWTService jwtService;

	public LoginController(UsuarioService usuarioService, JWTService jwtService) {
		super();
		this.usuarioService = usuarioService;
		this.jwtService = jwtService;
	}

	@PostMapping("login/usuarios")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody Usuario user) {
		if (!usuarioService.usuarioExiste(user.getEmail()))
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		try {
			LoginResponse token = jwtService.authenticate(user);
			return new ResponseEntity<LoginResponse>(token, HttpStatus.OK);
		} catch (Exception e) {
			if (e.getMessage().equals("Senha invalida!"))
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}