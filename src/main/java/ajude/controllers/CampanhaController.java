package ajude.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import ajude.classesAuxiliares.LoginResponse;
import ajude.entities.Campanha;
import ajude.entities.URLCampanha;
import ajude.entities.Usuario;
import ajude.services.CampanhaService;

@RestController
public class CampanhaController {

	private CampanhaService campanhaService;	
	
	public CampanhaController(CampanhaService campanhaService) {
		super();
		this.campanhaService = campanhaService;
	}
	
	@PostMapping("/campanha")
	public ResponseEntity<Campanha> adicionaCampanha(@RequestBody Campanha camp){
		Campanha c = campanhaService.addCampanha(camp);
		
		if (c != null) {
			return new ResponseEntity<Campanha>(c, HttpStatus.OK);
		} else {
			return new ResponseEntity<Campanha>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/campanha/{url}")
	public ResponseEntity<Campanha> pegaCampanha(@PathVariable String url){
		return new ResponseEntity<Campanha>(campanhaService.getCampanha(url),HttpStatus.OK);
	}
	
	/*
	@PutMapping("/campanha/encerrar")
	public ResponseEntity<Campanha> encerraCampanha(@RequestHeader("Authorization") String header, @RequestBody Usuario dono) {
		if ()
	}*/
	
	
	
}
