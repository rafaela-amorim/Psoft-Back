package ajude.services;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Comparators.DataComparator;
import Comparators.LikesComparator;
import Comparators.MetaComparator;
import ajude.DAOs.CampanhaRepository;
import ajude.classesAuxiliares.FormataURL;
import ajude.entities.Campanha;
import ajude.entities.Usuario;
import ajude.enums.StatusCampanha;

@Service
public class CampanhaService {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private CampanhaRepository<Campanha, Long> campanhaRepo;
	@Autowired
	private JWTService jwtService;
	
	
	public Campanha addCampanha(Campanha campanha, String token) throws Exception {
		String email = jwtService.getEmailToken(token);
		
		if (!usuarioService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		
		Usuario user = usuarioService.getUsuario(email);
		
		campanha.setDono(user);
		String url = FormataURL.formataURL(campanha.getNome());
		url += countAll();
				
		campanha.setUrl(url);
		user.adicionaCampanha(campanha);
		
		usuarioService.saveUsuario(user);
		return campanhaRepo.save(campanha);
	}
	
	public List<Campanha> findBySubstring(String substring) {
		return campanhaRepo.findBySubstring(substring.toLowerCase());
	}
	
	public List<Campanha> findByDescrSubstring(String substring) {
		return campanhaRepo.findByDescrSubstr(substring.toLowerCase());
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
	
	public void alteraLike(String url, boolean incrementa) throws Exception {
		if (!campanhaExiste(url))
			throw new Exception("campanha nao existe");
		Campanha c = getCampanha(url);
		if (incrementa)
			c.incrementaLike();
		else
			c.decrementaLike();
		
		campanhaRepo.save(c);
	}
	
	public void alteraDislikes(String url, boolean incrementa) throws Exception {
		if (!campanhaExiste(url))
			throw new Exception("campanha nao existe");
		Campanha c = getCampanha(url);
		if (incrementa)
			c.incrementaDislike();
		else
			c.decrementaDislike();
		campanhaRepo.save(c);
	}
	
	public List<Campanha> getCampanhas() {
		return campanhaRepo.findAll();
	}
	
	public Campanha getCampanha(long id) {
		return campanhaRepo.findById(id).get();
	}
	
	public Campanha getCampanha(String url) throws Exception {
		if (!campanhaExiste(url))
			throw new Exception("campanha nao existe");
		return campanhaRepo.findByUrl(url).get();
	}

	public boolean campanhaExiste(String url) {
		return campanhaRepo.findByUrl(url).isPresent();
	}
	
	public boolean campanhaExiste(long id) {
		return campanhaRepo.findById(id).isPresent();
	}
	
	
	public List<Campanha> campanhasDoacao(String token) throws Exception{
		String email = jwtService.getEmailToken(token);
		if(!usuarioService.usuarioExiste(email))
			throw new Exception("usuario nao existe");
		return campanhaRepo.campanhasDoador(email);
	}
	
	public List<Campanha> sortCampanhasByMeta() {
		List<Campanha> lista = getCampanhas();
		MetaComparator mc = new MetaComparator();
		Collections.sort(lista, mc);
		
		return lista;
	}
	
	public List<Campanha> sortCampanhasByData() {
		List<Campanha> lista = getCampanhas();
		DataComparator dc = new DataComparator();
		Collections.sort(lista, dc);
		
		return lista;
	}
	
	public List<Campanha> sortCampanhasByLikes() {
		List<Campanha> lista = getCampanhas();
		LikesComparator lc = new LikesComparator();
		Collections.sort(lista, lc);
		
		return lista;
	}
}























