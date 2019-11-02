package ajude.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ajude.DAOs.CampanhaRepository;
import ajude.DAOs.URLCampanhaRepository;
import ajude.entities.Campanha;
import ajude.entities.URLCampanha;

@Service
public class CampanhaService {

	private CampanhaRepository<Campanha, Long> campanhaDAO;
	private URLCampanhaRepository<URLCampanha,String> URLDAO;
	
	
	public CampanhaService(CampanhaRepository<Campanha, Long> campanhaDAO,URLCampanhaRepository<URLCampanha,String> urldao) {
		super();
		this.campanhaDAO = campanhaDAO;
		this.URLDAO = urldao;
	}
	
	// ------------------------------
	
	public Campanha addCampanha(Campanha campanha, URLCampanha camp) {
		URLDAO.save(camp);
		return campanhaDAO.save(campanha);
	}
	
	public Campanha getCampanha(long id) {
		return campanhaDAO.findById(id).get();
	}
	
	public Campanha getCampanha(String url) {
		return URLDAO.findById(url).get().getCampanha();
	}
	
	public boolean campanhaExiste(long id) {
		return campanhaDAO.findById(id).isPresent();
	}
	
	public List<Campanha> getCampanhas() {
		return campanhaDAO.findAll();
	}
	
	public Campanha removeCampanha(long id) {
		Campanha c = getCampanha(id);
		campanhaDAO.delete(c);
		return c;
	}
	
	
}
