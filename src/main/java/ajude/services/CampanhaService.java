package ajude.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import ajude.DAOs.CampanhaRepository;
import ajude.DAOs.UsuarioRepository;
import ajude.classesAuxiliares.FormataURL;
import ajude.entities.Campanha;
import ajude.entities.Usuario;
import ajude.enums.StatusCampanha;

@Service
public class CampanhaService {

	private UsuarioService usuarioService;
	private UsuarioRepository<Usuario, String> usuariosRepo;
	private CampanhaRepository<Campanha, Long> campanhaRepo;
	
	public CampanhaService(UsuarioService usuarioService, CampanhaRepository<Campanha, Long> campanhaRepo) {
		super();
		this.campanhaRepo = campanhaRepo;
		this.usuarioService = usuarioService;
	}
	
	// ------------------------------
	
	public Campanha addCampanha(Campanha campanha, Usuario user) {
		campanha.setDono(user);
		String url = FormataURL.formataURL(campanha.getNome());
		url += countAll();
				
		user.adicionaCampanha(campanha);
		campanha.setUrl(url);
		
		usuarioService.saveUsuario(user);
		return campanhaRepo.save(campanha);
	}
	
	public List<Campanha> findBySubstring(String substring) {
		return campanhaRepo.findBySubstring(substring.toLowerCase());
	}
	
	public Campanha encerraCampanha(String url, String email) throws Exception {
		Campanha c = getCampanha(url);
		
		if (!verificaDono(url, email))
			throw new Exception("usuario nao e o dono");

		c.setStatus(StatusCampanha.ENCERRADA);
		campanhaRepo.save(c);
		
		return c;
	}
	
	public Campanha alterarDeadline(String url, String email, Date novaData) throws Exception {
		Campanha c = getCampanha(url);
		Date data = new Date();
		
		if (!verificaDono(url, email))
			throw new Exception("usuario nao e o dono");
		if (!data.before(novaData))
			throw new Exception("a nova data nao esta no futuro");

		c.setDataLimite(novaData);
		campanhaRepo.save(c);
		
		return c;
	}
	
	public Campanha alterarMeta(String url, String email, double novaMeta) throws Exception {
		Campanha c = getCampanha(url);
		
		if (!c.estaAtiva())
			throw new Exception("campanha nao esta ativa");
		if (!verificaDono(url, email))
			throw new Exception("usuario nao e o dono");
		
		c.setMeta(novaMeta);
		campanhaRepo.save(c);
		
		return c;
	}
	
	public Campanha alteraDescricao(String url, String email, String novaDescr) throws Exception {
		Campanha c = getCampanha(url);	// lança excecao se a campanha nao existe
		
		if (!c.estaAtiva())
			throw new Exception("campanha nao esta ativa");
		if (!verificaDono(url, email))
			throw new Exception("usuario nao e o dono");
		
		c.setDescricao(novaDescr);
		campanhaRepo.save(c);
		
		return c;
	}
	
	public int countAll() {
		return campanhaRepo.countAll().size();
	}
	
	public Campanha verificaStatus(String url) throws Exception {
		Campanha c = getCampanha(url);	// lança exceção se a campanha não existir
		Date d = new Date();
		
		if (d.after(c.getDataLimite())) {			// Se hoje ja passou da data limite
			if (c.getMeta() > c.getDoacoes())
				c.setStatus(StatusCampanha.VENCIDA);
			else
				c.setStatus(StatusCampanha.CONCLUIDA);
		
			campanhaRepo.save(c);
		} 
		
		return c;
	}
	
	public boolean verificaDono(String url, String userEmail) throws Exception {
		if (!usuarioService.usuarioExiste(userEmail))
			throw new Exception("usuario nao existe");
		
		Usuario user = usuarioService.getUsuario(userEmail);
		Campanha camp = getCampanha(url); 				 // lança exceção se a campanha não existir
		
		return user.equals(camp.getDono());
	}
	
	public List<Campanha> getCampanhas() {
		return campanhaRepo.findAll();
	}
	
	public Campanha getCampanha(long id) {
		return campanhaRepo.findById(id).get();
	}
	
	public Campanha getCampanha(String url) throws Exception {
		try {
			return campanhaRepo.findByUrl(url).get();
		} catch (Exception e) {
			throw new Exception("campanha nao existe");
		}
	}

	public boolean campanhaExiste(String url) {
		return campanhaRepo.findByUrl(url).isPresent();
	}
	
	public boolean campanhaExiste(long id) {
		return campanhaRepo.findById(id).isPresent();
	}
}