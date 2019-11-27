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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class ComentarioController {
	
	@Autowired
	private ComentarioService comentarioService;

	@ApiOperation(value = "Adiciona um comentario a uma campanha, usuario deve estar autenticado")
	@ApiResponses(value = {
	    @ApiResponse(code = 201, message = "Se o comentario for adicionado"),
	    @ApiResponse(code = 404, message = "Se a campanha ou o comentario não existir")
	})
	@PostMapping("auth/comentario")
	public ResponseEntity<Comentario> addComentario(@RequestBody Comentario comentario, @RequestHeader("Authorization") String token) throws Exception {
		try {
			return new ResponseEntity<Comentario>(comentarioService.addComentario(comentario, token), HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<Comentario>(HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation(value = "Retorna a lista com todos os comentarios da campanha correspondente ao id da URI")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se ocorrer tudo certo"),
	    @ApiResponse(code = 404, message = "Se houver algum erro")
	})
	@GetMapping("comentario/campanha/{url}")
	public ResponseEntity<List<Comentario>> getComentarioCampanha(@PathVariable String url) throws Exception {
		try {
			return new ResponseEntity<List<Comentario>>(comentarioService.getComentariosCamp(url), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation(value = "Retorna a lista de todos os comentarios que referenciam ao comentário correspondente ao id da URI")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se ocorrer tudo certo"),
	    @ApiResponse(code = 400, message = "Se o comentário não existe")
	})
	@GetMapping("comentario/respostas/{id}")
	public ResponseEntity<List<Comentario>> getComentarioResp(@PathVariable long id) {
		try {
			return new ResponseEntity<List<Comentario>>(comentarioService.getComentariosResp(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation(value = "Retorna uma lista com todos os comentarios feitos pelo usuário autenticado que faz a requisição")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se tudo correu bem"),
	    @ApiResponse(code = 400, message = "Se o usuário não foi encontrado")
	})
	@GetMapping("auth/comentario/usuario")
	public ResponseEntity<List<Comentario>> getComentarioUsuario(@RequestHeader("Authorization") String token) {
		try {
			return new ResponseEntity<List<Comentario>>(comentarioService.getComentariosDono(token), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<List<Comentario>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation(value = "Deleta o comentario correspondente ao id na URI, o usuário que tenta apagar deve ser o mesmo usuário que criou o comentario e o deve estar autenticado. Todos os comentarios resposta deste comentario que esta sendo deletado devem ser deletados em cascata.")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se tudo correu bem"),
	    @ApiResponse(code = 403, message = "Se o usuário não é o dono"),
	    @ApiResponse(code = 404, message = "Se algo deu errado")
	})
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