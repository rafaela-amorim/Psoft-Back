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

import ajude.entities.Dislikes;
import ajude.services.DislikeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class DislikesController {

	@Autowired
	private DislikeService dislikeService;

	@ApiOperation(value = "Um usuário autenticado pode dar dislike em uma campanha")
	@ApiResponses(value = {
	    @ApiResponse(code = 201, message = "Se correu tudo bem"),
	    @ApiResponse(code = 404, message = "Se o usuario ou a campanha não existe"),
	    @ApiResponse(code = 409, message = "Se o usuário já deu dislike")
	})
	@PostMapping("auth/dislike")
	public ResponseEntity<Dislikes> addDislike(@RequestBody Dislikes dislikes, @RequestHeader("Authorization") String token) {
		try {
			return new ResponseEntity<Dislikes>(dislikeService.addDislike(dislikes, token), HttpStatus.CREATED);
		} catch (Exception e) {
			if (e.getMessage().equals("usuario ja deu dislike")) {
				System.out.println(e.getMessage());
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}		
	}
	
	@ApiOperation(value = "Retorna uma lista com todos os dislikes da campanha correspondente à url na URI")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se correu tudo bem"),
	    @ApiResponse(code = 400, message = "Se a campanha não existe")
	})
	@GetMapping("dislikes/campanha/{url}")
	public ResponseEntity<List<Dislikes>> getDislikesCampanha(@PathVariable String url) {
		try {
			return new ResponseEntity<List<Dislikes>>(dislikeService.getDislikesCamp(url), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Retorna uma lista com todos os dislikes que foram dados pelo usuário autenticado que fez a requisição")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se correu tudo bem"),
	    @ApiResponse(code = 400, message = "Se o usuário não existe")
	})
	@GetMapping("auth/dislikes/usuario")
	public ResponseEntity<List<Dislikes>> getDislikesUsuario(@RequestHeader("Authorization") String token) {
		try {
			return new ResponseEntity<List<Dislikes>>(dislikeService.getDislikesUsu(token), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation(value = "O usuário dono do dislike pode retirá-lo de uma campanha correspondente à url na URI, usuário deve estar autenticado")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se correu tudo bem"),
	    @ApiResponse(code = 400, message = "Se o usuário não existe")
	})
	@DeleteMapping("auth/dislike/campanha/remove/{url}")
	public ResponseEntity<Dislikes> deleteDislike(@RequestHeader("Authorization") String token, @PathVariable String url) {
		try {
			return new ResponseEntity<Dislikes>(dislikeService.deleteDislike(url, token), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
