package ajude.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ajude.DAOs.CampanhaRepository;
import ajude.entities.Campanha;

@Service
public class CampanhaService {

	private CampanhaRepository<Campanha, Long> campanhaDAO;
	
	
	public CampanhaService(CampanhaRepository<Campanha, Long> campanhaDAO) {
		super();
		this.campanhaDAO = campanhaDAO;
	}
	
	// ------------------------------
	
	public Campanha addCampanha(Campanha campanha) {
		return campanhaDAO.save(campanha);
	}
	
	public Campanha getCampanha(long id) {
		return campanhaDAO.findById(id).get();
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
