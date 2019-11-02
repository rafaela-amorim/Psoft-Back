package ajude.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ajude.classesAuxiliares.LoginResponse;
import ajude.entities.Campanha;
import ajude.entities.URLCampanha;
import ajude.services.CampanhaService;

@RestController
public class CampanhaController {

	private CampanhaService campanhaService;	
	
	public CampanhaController(CampanhaService campanhaService) {
		super();
		this.campanhaService = campanhaService;
	}
	
	@PostMapping("/campanha")
	public ResponseEntity<Campanha> adicionaCampanha(@RequestBody Campanha camp,@RequestBody URLCampanha url){
		return new ResponseEntity<Campanha>(campanhaService.addCampanha(camp,url),HttpStatus.OK);
	}
	
	@GetMapping("/campanha/{url}")
	public ResponseEntity<Campanha> pegaCampanha(@PathVariable String url){
		return new ResponseEntity<Campanha>(campanhaService.getCampanha(url),HttpStatus.OK);
	}
	
	
	
}
