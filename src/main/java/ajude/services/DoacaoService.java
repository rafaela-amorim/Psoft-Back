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
	private CampanhaService campSer;
	@Autowired
	private CampanhaRepository<Campanha,Long> campRepo;
	
	
	public DoacaoService() {
		super();
	}
	
	
	public Quantia addDoacao(Doacao doa,String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		if(!campSer.campanhaExiste(doa.getUrlCampanha()))
			throw new Exception("campanha nao existe");
		else if(!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		
		doa.setEmailDoador(email);
		doacaoRepo.save(doa);
		Campanha camp = campSer.getCampanha(doa.getUrlCampanha());
		
		camp.fazDoacao(doa.getQuantia());
		
		campRepo.save(camp);
		
		return new Quantia(camp.getMeta());
	}
	
	public List<Doacao> getDoacoesUsuario(String token) throws Exception{
		String email = jwtService.getEmailToken(token);
		if(!jwtService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		
		return doacaoRepo.getDoacoesUsuario(email);
	}
	
	public List<Doacao> getDoacoesCampanha(String urlCampanha) throws Exception{
		if(!campSer.campanhaExiste(urlCampanha))
			throw new Exception("campanha nao existe");
		return doacaoRepo.getDoacoesCampanha(urlCampanha);
	}
	
}
