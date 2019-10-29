package ajude.controllers;

import org.springframework.web.bind.annotation.RestController;

import ajude.services.CampanhaService;

@RestController
public class CampanhaController {

	private CampanhaService campanhaService;	
	
	public CampanhaController(CampanhaService campanhaService) {
		super();
		this.campanhaService = campanhaService;
	}
	
	
	
}
