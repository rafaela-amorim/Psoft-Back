package ajude.controllers;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import ajude.entities.Campanha;
import ajude.services.CampanhaService;
import ajude.services.JwtService;

@RestController
public class CampanhaController {

	private CampanhaService campanhaService;
	private JwtService jwtService;
	
	public CampanhaController(CampanhaService campanhaService, JwtService jwt) {
		super();
		this.campanhaService = campanhaService;
		this.jwtService = jwt;
	}
	
	@PostMapping("/campanha")
	public ResponseEntity<Campanha> adicionaCampanha(@RequestBody Campanha camp, @RequestHeader("Authorization") String token) {
		camp.setDono(jwtService.getUsuario(token));
		Campanha c = campanhaService.addCampanha(camp);
		
		if (c != null) {
			return new ResponseEntity<Campanha>(c, HttpStatus.OK);
		} else {
			return new ResponseEntity<Campanha>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/campanha/{url}")
	public ResponseEntity<Campanha> getCampanha(@PathVariable String url) {
		return new ResponseEntity<Campanha>(campanhaService.getCampanha(url), HttpStatus.OK);
	}
	
	@GetMapping("/campanha/find/{substring}")
	public ResponseEntity<List<Campanha>> findByString(@PathVariable String substring) {
		return new ResponseEntity<List<Campanha>>(campanhaService.findBySubstring(substring), HttpStatus.OK);
	}
	
	/*
	@PutMapping("/campanha/encerrar")
	public ResponseEntity<Campanha> encerraCampanha(@RequestHeader("Authorization") String header, @RequestBody Usuario dono) {
		if ()
	}*/
	
	
	
}
