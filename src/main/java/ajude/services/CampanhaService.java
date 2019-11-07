package ajude.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import ajude.DAOs.CampanhaRepository;
import ajude.DAOs.UsuarioRepository;
import ajude.classesAuxiliares.FormataURL;
import ajude.classesAuxiliares.StatusCampanha;
import ajude.entities.Campanha;
import ajude.entities.Usuario;

@Service
public class CampanhaService {

	private UsuarioRepository<Usuario, String> usuariosRepo;
	private CampanhaRepository<Campanha, Long> campanhaRepo;
		
	public CampanhaService(UsuarioRepository<Usuario, String> usuariosRepo, CampanhaRepository<Campanha, Long> campanhaRepo) {
		super();
		this.campanhaRepo = campanhaRepo;
		this.usuariosRepo = usuariosRepo;
	}
	
	// ------------------------------
	
	public Campanha addCampanha(Campanha campanha, Usuario user) {
		campanha.setDono(user);
		campanha.setUrl(FormataURL.formataURL(campanha.getNome()));
		
		user.adicionaCampanha(campanha);
		usuariosRepo.save(user);
		
		return campanhaRepo.save(campanha);
	}
	
	public Campanha getCampanha(long id) {
		return campanhaRepo.findById(id).get();
	}
	
	public Campanha getCampanha(String url) throws Exception {
		return campanhaRepo.findByUrl(url).get();
	}
	
	public List<Campanha> findBySubstring(String substring) {
		return campanhaRepo.findBySubstring(substring.toLowerCase());
	}
	
	public boolean campanhaExiste(long id) {
		return campanhaRepo.findById(id).isPresent();
	}
	
	public List<Campanha> getCampanhas() {
		return campanhaRepo.findAll();
	}
	
	public Campanha encerraCampanha(String url, String email) throws Exception {
		Campanha c = getCampanha(url);
		
		if (verificaDono(url, email)) {
			c.setStatus(StatusCampanha.ENCERRADA);
			campanhaRepo.save(c);
		}
		
		return c;
	}
	
	public Campanha verificaStatus(String url) throws Exception {
		Campanha c = getCampanha(url);
		Date d = new Date();
		
		if (c.getDataLimite().after(d)) {
			if (c.getMeta() > c.getDoacoes())
				c.setStatus(StatusCampanha.VENCIDA);
			else
				c.setStatus(StatusCampanha.CONCLUIDA);
			
			campanhaRepo.save(c);
		}
		
		return c;
	}
	
	public Campanha alterarDeadline(String url, String userEmail, Date novaData) throws Exception {
		Campanha c = getCampanha(url);
		Date data = new Date();
		
		if (verificaDono(url, userEmail) && data.before(novaData)) {
			c.setDataLimite(novaData);
			campanhaRepo.save(c);
		}
		
		return c;
	}
	
	public Campanha alterarMeta(String url, String email, double novaMeta) throws Exception {
		Campanha c = getCampanha(url);
		
		if (c.estaAtiva() && verificaDono(url, email)) {
			c.setMeta(novaMeta);
			campanhaRepo.save(c);
		}
		
		return c;
	}
	
	public Campanha removeCampanha(long id) {
		Campanha c = getCampanha(id);
		campanhaRepo.delete(c);
		return c;
	}
	
	public boolean verificaDono(String url, String userEmail) throws Exception {
		Usuario user = usuariosRepo.findById(userEmail).get();
		Campanha camp = getCampanha(url);
		
		return user.equals(camp.getDono());
	}
	
	public boolean existeCampanha(String url) {
		return campanhaRepo.findByUrl(url).isPresent();
	}
}