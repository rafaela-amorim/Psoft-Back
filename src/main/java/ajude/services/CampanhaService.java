package ajude.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ajude.DAOs.CampanhaRepository;
import ajude.DAOs.URLCampanhaRepository;
import ajude.DAOs.UsuarioRepository;
import ajude.classesAuxiliares.FormataURL;
import ajude.classesAuxiliares.StatusCampanha;
import ajude.entities.Campanha;
import ajude.entities.URLCampanha;
import ajude.entities.Usuario;

@Service
public class CampanhaService {

	private UsuarioRepository<Usuario, String> usuariosRepo;
	private CampanhaRepository<Campanha, Long> campanhaRepo;
	private URLCampanhaRepository<URLCampanha,String> URLRepo;
	
	
	public CampanhaService(UsuarioRepository<Usuario, String> usuariosRepo, CampanhaRepository<Campanha, Long> campanhaRepo, URLCampanhaRepository<URLCampanha,String> URLRepo) {
		super();
		this.campanhaRepo = campanhaRepo;
		this.URLRepo = URLRepo;
		this.usuariosRepo = usuariosRepo;
	}
	
	// ------------------------------
	
	public Campanha addCampanha(Campanha campanha) {
		Usuario u = campanha.getDono();
		
		if (usuariosRepo.existsById(u.getEmail())) {
			URLCampanha url = new URLCampanha();
			url.setUrl(FormataURL.formataURL(campanha.getNome()));
			url.setCampanha(campanha);
			
			URLRepo.save(url);
			
			campanha.setURLCampanha(url);
			
			u.adicionaCampanha(campanha);
			usuariosRepo.save(u);
			return campanhaRepo.save(campanha);
		}
		
		return new Campanha();
	}
	
	public Campanha getCampanha(long id) {
		return campanhaRepo.findById(id).get();
	}
	
	public Campanha getCampanha(String url) {
		return URLRepo.findById(url).get().getCampanha();
	}
	
	public boolean campanhaExiste(long id) {
		return campanhaRepo.findById(id).isPresent();
	}
	
	public List<Campanha> getCampanhas() {
		return campanhaRepo.findAll();
	}
	
	public Campanha encerraCampanha(long id, Usuario dono) {
		// fazer autenticação para verificar se o Usuario passado é o dono mesmo
		Campanha c = getCampanha(id);
		c.setStatus(StatusCampanha.ENCERRADA);
		campanhaRepo.save(c);
		return c;
	}
	
	public Campanha verificaStatus(long id) {
		Campanha c = getCampanha(id);
		Date d = new Date();
		
		if (c.getDataLimite().after(d)) {
			if (c.getMeta() > c.getDoacoes()) {
				c.setStatus(StatusCampanha.VENCIDA);
			} else {
				c.setStatus(StatusCampanha.CONCLUIDA);
			}
			
			campanhaRepo.save(c);
		}
		
		return c;
	}
	
	public Campanha removeCampanha(long id) {
		Campanha c = getCampanha(id);
		campanhaRepo.delete(c);
		return c;
	}
	
	
}
