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

@RestController
public class DoacaoController {
	
	@Autowired
	private DoacaoService doacaoService;
	
	
	@PostMapping("/auth/doacao")
	public ResponseEntity<Quantia> addDoacao(@RequestBody Doacao doacao, @RequestHeader("Authorization") String token) {
		try {
			return new ResponseEntity<Quantia>(doacaoService.addDoacao(doacao, token), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Quantia>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/doacoes/campanha/{url}")
	public ResponseEntity<List<Doacao>> getDoacoesCampanha(@PathVariable String url) {
		try {
			return new ResponseEntity<List<Doacao>>(doacaoService.getDoacoesCampanha(url), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/auth/doacoes/usuario")
	public ResponseEntity<List<Doacao>> getDoacoesUsuario(@RequestHeader("Authorization") String token) throws Exception {
		try {
			return new ResponseEntity<List<Doacao>>(doacaoService.getDoacoesUsuario(token), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} 
	}
}