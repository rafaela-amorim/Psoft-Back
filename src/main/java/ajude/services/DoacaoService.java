package ajude.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ajude.DAOs.CampanhaRepository;
import ajude.DAOs.DoacaoRepository;
import ajude.classesAuxiliares.Quantia;
import ajude.entities.Campanha;
import ajude.entities.Doacao;

@Service
public class DoacaoService {
	
	@Autowired
	private DoacaoRepository<Doacao,Long> doacaoRepo;
	@Autowired
	private JWTService jwtService;
	@Autowired
	private CampanhaService campService;
	@Autowired
	private CampanhaRepository<Campanha,Long> campRepo;
	
	
	public Quantia addDoacao(Doacao doacao, String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		if (!campService.campanhaExiste(doacao.getUrlCampanha()))
			throw new Exception("campanha nao existe");
		else if(!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		
		doacao.setEmailDoador(email);
		doacaoRepo.save(doacao);
		Campanha camp = campService.getCampanha(doacao.getUrlCampanha());
		
		camp.fazDoacao(doacao.getQuantia());
		
		campRepo.save(camp);
		
		return new Quantia(camp.getMeta());
	}
	
	public List<Doacao> getDoacoesUsuario(String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		if (!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		
		return doacaoRepo.getDoacoesUsuario(email);
	}
	
	public List<Doacao> getDoacoesCampanha(String urlCampanha) throws Exception {
		if (!campService.campanhaExiste(urlCampanha))
			throw new Exception("campanha nao existe");
		return doacaoRepo.getDoacoesCampanha(urlCampanha);
	}
}