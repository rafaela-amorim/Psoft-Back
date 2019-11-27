package ajude.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import ajude.classesAuxiliares.Descricao;
import ajude.classesAuxiliares.Meta;
import ajude.entities.Campanha;
import ajude.services.CampanhaService;
import ajude.services.JWTService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class CampanhaController {

	@Autowired
	private CampanhaService campanhaService;
	@Autowired
	private JWTService jwtService;
	
	public CampanhaController() {}	
	
	@ApiOperation(value = "Adiciona uma nova campanha ao sistema")
	@ApiResponses(value = {
	    @ApiResponse(code = 201, message = "Se a campanha for cadastrada com sucesso"),
	    @ApiResponse(code = 404, message = "Se houver algum erro")
	})
	@PostMapping("auth/campanha")
	public ResponseEntity<Campanha> addCampanha(@RequestBody Campanha campanha, @RequestHeader("Authorization") String token) {
		Campanha c;
		try {
			c = campanhaService.addCampanha(campanha, token);
			return new ResponseEntity<Campanha>(c, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation(value = "Retorna uma lista com todas as campanhas")
	@GetMapping("campanhas")
	public ResponseEntity<List<Campanha>> getCampanhas() {
		return new ResponseEntity<List<Campanha>>(campanhaService.getCampanhas(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Retorna a campanha correspondente à url na URI, caso a campanha exista")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se a campanha existir"),
	    @ApiResponse(code = 400, message = "Se a campanha não existir")
	})
	@GetMapping("campanha/{url}")
	public ResponseEntity<Campanha> getCampanha(@PathVariable String url) {		
		try {
			return new ResponseEntity<Campanha>(campanhaService.getCampanha(url), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Retorna uma lista com todas as campanhas que contenham a string como substring no título")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se ocorrer tudo bem")
	})
	@GetMapping("campanha/find/busca={substring}") 
	public ResponseEntity<List<Campanha>> findBySubstring(@PathVariable String substring) {
		return new ResponseEntity<List<Campanha>>(campanhaService.findBySubstring(substring), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Retorna uma lista com todas as campanhas que contenham a string recebida como substring no título OU na descrição")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se ocorrer tudo bem")
	})
	@GetMapping("campanha/find/descr/busca={substring}")
	public ResponseEntity<List<Campanha>> findByDescrSubstr(@PathVariable String substring) {
		return new ResponseEntity<List<Campanha>>(campanhaService.findByDescrSubstring(substring), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Retorna todas as campanhas que um usuario doou")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se o usuario existir"),
	    @ApiResponse(code = 400, message = "Se o usuario não existir")
	})
	@GetMapping("auth/campanha/doacao")
	public ResponseEntity<List<Campanha>> campanhasDoacao(@RequestHeader("Authorization") String token) {
		try {
			return new ResponseEntity<List<Campanha>>(campanhaService.campanhasDoacao(token), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Retorna todas as campanhas ordenadas por meta")
	@GetMapping("campanha/sort/meta")
	public ResponseEntity<List<Campanha>> getCampanhasByMeta() {
		return new ResponseEntity<List<Campanha>>(campanhaService.sortCampanhasByMeta(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Retorna todas as campanhas em ordem cronológica")
	@GetMapping("campanha/sort/data")
	public ResponseEntity<List<Campanha>> getCampanhasByData() {
		return new ResponseEntity<List<Campanha>>(campanhaService.sortCampanhasByData(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Retorna todas as campanhas em ordem de mais likes para menos likes")
	@GetMapping("campanha/sort/likes")
	public ResponseEntity<List<Campanha>> getCampanhasByLikes() {
		return new ResponseEntity<List<Campanha>>(campanhaService.sortCampanhasByLikes(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "O usuario que for dono da campanha corresponde à url na URI pode encerrá-la")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se ocorrer tudo bem"),
	    @ApiResponse(code = 400, message = "Se a campanha não existir")
	})	
	@PutMapping("auth/campanha/encerrar/{url}")
	public ResponseEntity<Campanha> encerraCampanha(@RequestHeader("Authorization") String token, @PathVariable String url) {
		String email = jwtService.getEmailToken(token);
		
		try {
			return new ResponseEntity<Campanha>(campanhaService.encerraCampanha(url, email), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "O usuario que for dono da campanha corresponde à url na URI pode mudar a data que ela vence")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se ocorrer tudo bem"),
	    @ApiResponse(code = 400, message = "Se houver algum erro")
	})
	@PutMapping("auth/campanha/deadline/{url}")
	public ResponseEntity<Campanha> alterarDeadline(@RequestHeader("Authorization") String token, @PathVariable String url, @RequestBody Data novaData) throws Exception {
		String email = jwtService.getEmailToken(token);
		
		try {
			return new ResponseEntity<Campanha>(campanhaService.alterarDeadline(url, email, novaData.getData()), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "O usuario que for dono da campanha corresponde à url na URI pode alterar a meta de doações")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se ocorrer tudo bem"),
	    @ApiResponse(code = 400, message = "Se houver algum erro")
	})
	@PutMapping("auth/campanha/meta/{url}")
	public ResponseEntity<Campanha> alterarMeta(@RequestHeader("Authorization") String token, @PathVariable String url, @RequestBody Meta novaMeta) {
		String email = jwtService.getEmailToken(token);
		
		try {
			return new ResponseEntity<Campanha>(campanhaService.alterarMeta(url, email, novaMeta.getMeta()), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "O usuario que for dono da campanha corresponde à url na URI pode alterar a descrição")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Se ocorrer tudo bem"),
	    @ApiResponse(code = 403, message = "Se o usuario não for dono"),
	   	@ApiResponse(code = 404, message = "Se a campanha não existir"),
	    @ApiResponse(code = 412, message = "Se a campanha não estiver ativa")
	    
	})
	@PutMapping("auth/campanha/descricao/{url}")
	public ResponseEntity<Campanha> alteraDescricao(@RequestHeader("Authorization") String token, @PathVariable String url, @RequestBody Descricao novaDescr) {
		String email = jwtService.getEmailToken(token);
		
		try {
			return new ResponseEntity<Campanha>(campanhaService.alteraDescricao(url, email, novaDescr.getDescricao()), HttpStatus.OK);
		} catch (Exception e) {
			if (e.getMessage().equals("campanha nao esta ativa"))
				return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
			if (e.getMessage().equals("usuario nao e o dono"))
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}