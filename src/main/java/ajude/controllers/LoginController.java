package ajude.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ajude.classesAuxiliares.LoginResponse;
import ajude.entities.Usuario;
import ajude.services.JWTService;

@RestController
public class LoginController {

	@Autowired
	private JWTService jwtService;

	
	@PostMapping("login/usuarios")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody Usuario user) {
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