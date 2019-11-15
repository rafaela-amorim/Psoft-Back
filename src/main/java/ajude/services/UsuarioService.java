package ajude.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ajude.DAOs.UsuarioRepository;
import ajude.entities.Campanha;
import ajude.entities.Usuario;

@Service
public class UsuarioService {
	
	private UsuarioRepository<Usuario, String> usuariosRepo;
	
	public UsuarioService(UsuarioRepository<Usuario, String> usuariosRepo) {
		super();
		this.usuariosRepo = usuariosRepo;
	}

	// ----------------------
	
	public Usuario addUsuario(Usuario user) throws Exception {
		if (usuarioExiste(user.getEmail()))
			throw new Exception("usuario ja existe");
		
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
	
	public Usuario mudarSenha(String email, String novaSenha) throws Exception {
		if (!usuarioExiste(email))
			throw new Exception("usuario nao existe");
		
		Usuario u = getUsuario(email);
		u.setSenha(novaSenha);
		
		usuariosRepo.save(u);
		return u;
	}
	
	public boolean senhaIgual(String email, String senha) throws Exception {
		if(!usuarioExiste(email))
			throw new Exception("usuario nao existe");
		
		return usuariosRepo.findById(email).get().verificaSenha(senha);
	}
	
	public Usuario removeUsuario(String email) throws Exception {
		if(!usuarioExiste(email))
			throw new Exception("usuario nao existe");
		
		Usuario u = usuariosRepo.findById(email).get();
		usuariosRepo.delete(u);
		return u;
	}
	
	public boolean usuarioExiste(String email) {
		return usuariosRepo.findById(email).isPresent();
	}
	
	public List<Campanha> getCampanhasDono(String email) throws Exception{
		if(!usuarioExiste(email))
			throw new Exception("usuario nao existe");
		return usuariosRepo.findById(email).get().getCampanhas();
	}
}