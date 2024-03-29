package ajude.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ajude.DAOs.UsuarioRepository;
import ajude.classesAuxiliares.JavaMail;
import ajude.classesAuxiliares.LoginResponse;
import ajude.entities.Campanha;
import ajude.entities.Usuario;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository<Usuario, String> usuariosRepo;
	@Autowired
	private JWTService jwtService;
	
	
	public Usuario addUsuario(Usuario user) throws Exception {
		if (usuarioExiste(user.getEmail()))
			throw new Exception("usuario ja existe");
		String subj = "Welcome to AJuDE, " + user.getNome() + "!";
		String txt = "O link de acesso para o sistema é https://wineone.github.io/";
		try {
			JavaMail.enviar(subj, txt, user.getEmail());
		} catch(Exception e) {}
		return saveUsuario(user);
	}
	
	public Usuario saveUsuario(Usuario usuario) {
		return usuariosRepo.save(usuario);
	}
	
	public Usuario getUsuario(String email) throws Exception {
		if (!usuarioExiste(email))
			throw new Exception("usuario nao existe");
		return usuariosRepo.findById(email).get();
	}
	
	public List<Usuario> getUsuarios() {
		return usuariosRepo.findAll();
	}
	
	public Usuario emailMudarSenha(String email) throws Exception {
		if (!usuarioExiste(email))
			throw new Exception("usuario nao existe");
		
		LoginResponse lr = jwtService.geraTokenMudarSenha(email);
		String subj = "Change your password";
		String txt = "Para modificar a senha acesse o link abaixo: http://127.0.0.1:5500/mudarSenha/" + lr.getToken();
		
		try {
			JavaMail.enviar(subj, txt, email);
		} catch(Exception e) {
			throw new Exception("erro ao enviar o email");
		}
		
		return getUsuario(email);
	}
	
	public Usuario mudarSenha(String email, String novaSenha) throws Exception {
		Usuario u = getUsuario(email);
		u.setSenha(novaSenha);
		usuariosRepo.save(u);
		return u;
	}
	
	public boolean senhaIgual(String email, String senha) throws Exception {
		if (!usuarioExiste(email))
			throw new Exception("usuario nao existe");
		
		return usuariosRepo.findById(email).get().verificaSenha(senha);
	}
	
	public Usuario removeUsuario(String email) throws Exception {
		if (!usuarioExiste(email))
			throw new Exception("usuario nao existe");
		
		Usuario u = usuariosRepo.findById(email).get();
		usuariosRepo.delete(u);
		return u;
	}
	
	public boolean usuarioExiste(String email) {
		return usuariosRepo.findById(email).isPresent();
	}
	
	public List<Campanha> getCampanhasDono(String email) throws Exception {
		if (!usuarioExiste(email))
			throw new Exception("usuario nao existe");
		return usuariosRepo.findById(email).get().getCampanhas();
	}
}