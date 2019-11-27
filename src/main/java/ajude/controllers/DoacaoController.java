package ajude.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import ajude.classesAuxiliares.Quantia;
import ajude.entities.Doacao;
import ajude.services.DoacaoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class DoacaoController {
	
	@Autowired
	private DoacaoService doacaoService;
	
	@ApiOperation(value = "Adiciona uma doação a uma capanha, o usuario tem que estar autenticado para realizar a doação")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se tudo correu bem"),
	    @ApiResponse(code = 400, message = "Se o usuario ou a campanha não existe")
	})
	@PostMapping("/auth/doacao")
	public ResponseEntity<Quantia> addDoacao(@RequestBody Doacao doacao, @RequestHeader("Authorization") String token) {
		try {
			return new ResponseEntity<Quantia>(doacaoService.addDoacao(doacao, token), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Quantia>(HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation(value = "Retorna todas as doações realizadas a uma campanha")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Se tudo correu bem"),
	    @ApiResponse(code = 400, message = "Se a campanha não foi encontrada")
	})
	@GetMapping("/doacoes/campanha/{url}")
	public ResponseEntity<List<Doacao>> getDoacoesCampanha(@PathVariable String url) {
		try {
			return new ResponseEntity<List<Doacao>>(doacaoService.getDoacoesCampanha(url), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation(value = "Retorna todas as doações feitas por um usuario")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se tudo correu bem"),
	    @ApiResponse(code = 400, message = "Se o usuario não existe")
	})
	@GetMapping("/auth/doacoes/usuario")
	public ResponseEntity<List<Doacao>> getDoacoesUsuario(@RequestHeader("Authorization") String token) throws Exception {
		try {
			return new ResponseEntity<List<Doacao>>(doacaoService.getDoacoesUsuario(token), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} 
	}
}