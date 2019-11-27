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

import ajude.classesAuxiliares.Senha;
import ajude.entities.Campanha;
import ajude.entities.Usuario;
import ajude.services.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@ApiOperation(value = "Adiciona um usuário novo, depois de verificar que o email ja nao existia no sistema")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se o usuario for criado"),
	    @ApiResponse(code = 409, message = "Se ja existir um usuario com aquele email")
	})
	@PostMapping("usuarios")
	public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario usuario) {
		try {
			return new ResponseEntity<Usuario>(usuarioService.addUsuario(usuario), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Usuario>(HttpStatus.CONFLICT);
		}
	}
	
	@ApiOperation(value = "Retorna uma lista com todos os usuarios")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se tudo ocorrer bem")
	})
	@GetMapping("usuarios")
	public ResponseEntity<List<Usuario>> getUsuarios() {
		return new ResponseEntity<List<Usuario>>(usuarioService.getUsuarios(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Retorna o usuario do email na URI, caso exista")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se o usuario existir"),
	    @ApiResponse(code = 409, message = "Se o usuario nao existir")
	})
	@GetMapping("usuarios/{email}")
	public ResponseEntity<Usuario> getUsuario(@PathVariable String email) {
		try {
			return new ResponseEntity<Usuario>(usuarioService.getUsuario(email), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	} 
	
	@ApiOperation(value = "Muda a senha do usuario")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se ocorrer tudo bem"),
	    @ApiResponse(code = 404, message = "Caso haja algum erro")
	})
	@PostMapping("usuarios/mudarSenha/{email}")
	public ResponseEntity<Usuario> emailMudarSenha(@PathVariable String email) {
		try {
			return new ResponseEntity<Usuario>(usuarioService.emailMudarSenha(email), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation(value = "Muda a senha do usuario")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se ocorrer tudo bem"),
	    @ApiResponse(code = 404, message = "Caso haja algum erro")
	})
	@PutMapping("auth/usuarios")
	public ResponseEntity<Usuario> mudarSenha(HttpServletRequest request, @RequestBody Senha senha) {
		try {
			String email = (String) request.getAttribute("email");
			return new ResponseEntity<Usuario>(usuarioService.mudarSenha(email, senha.getSenha()), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation(value = "Retorna a lista de campanhas que o usuario é dono")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se ocorrer tudo bem"),
	    @ApiResponse(code = 400, message = "Se o usuario nao existir")
	})
	@GetMapping("usuarios/campanhas/{email}")
	public ResponseEntity<List<Campanha>> getCampanhas(@PathVariable String email){
		try {
			return new ResponseEntity<List<Campanha>>(usuarioService.getCampanhasDono(email),HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<List<Campanha>>(HttpStatus.BAD_REQUEST);
		}
	}
	
}