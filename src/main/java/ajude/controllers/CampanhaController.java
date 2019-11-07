package ajude.controllers;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import ajude.classesAuxiliares.Data;
import ajude.classesAuxiliares.Meta;
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
	
	@PostMapping("auth/campanha")
	public ResponseEntity<Campanha> adicionaCampanha(@RequestBody Campanha camp, @RequestHeader("Authorization") String token) {
		camp.setDono(jwtService.getUsuario(token));
		Campanha c = campanhaService.addCampanha(camp);
		
		if (c != null) {
			return new ResponseEntity<Campanha>(c, HttpStatus.OK);
		} else {
			return new ResponseEntity<Campanha>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("campanha/{url}")
	public ResponseEntity<Campanha> getCampanha(@PathVariable String url) {
		try {
			return new ResponseEntity<Campanha>(campanhaService.getCampanha(url), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("campanha/find/{substring}")
	public ResponseEntity<List<Campanha>> findByString(@PathVariable String substring) {
		return new ResponseEntity<List<Campanha>>(campanhaService.findBySubstring(substring), HttpStatus.OK);
	}
	
	
	// -----------------------------------
	
	@PutMapping("auth/campanha/encerrar/{url}")
	public ResponseEntity<Campanha> encerraCampanha(@RequestHeader("Authorization") String token, @PathVariable String url) {
		String email = jwtService.getEmailToken(token);
		
		try {
			return new ResponseEntity<Campanha>(campanhaService.encerraCampanha(url, email), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("auth/campanha/deadline/{url}")
	public ResponseEntity<Campanha> alterarDeadline(@RequestHeader("Authorization") String token, @PathVariable String url, @RequestBody Data novaData) throws Exception {
		String email = jwtService.getEmailToken(token);
		
		try {
			return new ResponseEntity<Campanha>(campanhaService.alterarDeadline(url, email, novaData.getData()), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("auth/campanha/meta/{url}")
	public ResponseEntity<Campanha> alterarMeta(@RequestHeader("Authorization") String token, @PathVariable String url, @RequestBody Meta novaMeta) {
		String email = jwtService.getEmailToken(token);
		
		try {
			return new ResponseEntity<Campanha>(campanhaService.alterarMeta(url, email, novaMeta.getMeta()), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
}


























