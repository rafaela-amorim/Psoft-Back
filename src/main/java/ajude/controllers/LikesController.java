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

import ajude.entities.Likes;
import ajude.services.LikesService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class LikesController {

	@Autowired
	private LikesService likesService;

	@ApiOperation(value = "Um usuário autenticado pode dar like em uma campanha")
	@ApiResponses(value = {
	    @ApiResponse(code = 201, message = "Se correu tudo bem"),
	    @ApiResponse(code = 404, message = "Se o usuario ou a campanha não existe"),
	    @ApiResponse(code = 409, message = "Se o usuário já deu like")
	})
	@PostMapping("auth/like")
	public ResponseEntity<Likes> addLike(@RequestBody Likes like, @RequestHeader("Authorization") String token) {
		try {
			return new ResponseEntity<Likes>(likesService.addLike(like, token), HttpStatus.CREATED);
		} catch (Exception e) {
			if (e.getMessage().equals("usuario ja deu like")) {
				System.out.println(e.getMessage());
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}		
	}
	
	@ApiOperation(value = "Retorna uma lista com todos os likes da campanha correspondente à url na URI")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se correu tudo bem"),
	    @ApiResponse(code = 400, message = "Se a campanha não existe")
	})
	@GetMapping("likes/campanha/{url}")
	public ResponseEntity<List<Likes>> getLikesCampanha(@PathVariable String url) {
		try {
			return new ResponseEntity<List<Likes>>(likesService.getLikesCamp(url), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation(value = "Retorna uma lista com todos os likes que foram dados pelo usuário autenticado que fez a requisição")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se correu tudo bem"),
	    @ApiResponse(code = 400, message = "Se o usuário não existe")
	})
	@GetMapping("auth/likes/usuario")
	public ResponseEntity<List<Likes>> getLikesUsuario(@RequestHeader("Authorization") String token) {
		try {
			return new ResponseEntity<List<Likes>>(likesService.getLikesUsu(token), HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "O usuário dono do like pode retirá-lo de uma campanha correspondente à url na URI, usuário deve estar autenticado")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se correu tudo bem"),
	    @ApiResponse(code = 400, message = "Se o usuário não existe")
	})
	@DeleteMapping("auth/like/campanha/remove/{url}")
	public ResponseEntity<Likes> deleteLike(@RequestHeader("Authorization") String token, @PathVariable String url) {
		try {
			
			return new ResponseEntity<Likes>(likesService.deleteLike(url, token), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}