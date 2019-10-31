package ajude.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ajude.DAOs.UsuarioRepository;
import ajude.entities.Usuario;

@Service
public class UsuarioService {
	
	private UsuarioRepository<Usuario, String> usuariosRepo;
	
	public UsuarioService(UsuarioRepository<Usuario, String> usuariosRepo) {
		super();
		this.usuariosRepo = usuariosRepo;
	}

	// ----------------------
	
	// antes de criar um novo usuario, verifica se o email já nao existia antes no banco de dados
	public Usuario addUsuario(Usuario usuario) {
		return usuariosRepo.save(usuario);
	}
	
	public Usuario getUsuario(String email) {
		return usuariosRepo.findById(email).get();
	}
	
	public List<Usuario> getUsuarios() {
		return usuariosRepo.findAll();
	}
	
	// verifica se o usuario existe no controller
	public Usuario mudarSenha(String email, String novaSenha) {
		Usuario u = getUsuario(email);
		u.setSenha(novaSenha);
		addUsuario(u);
		return u;
	}
	
	public boolean senhaIgual(String email, String senha) {
		if(usuarioExiste(email)) {
			return getUsuario(email).verificaSenha(senha);
		}
		else
			return false;
	}
	
	public Usuario removeUsuario(String email) {
		Usuario u = getUsuario(email);
		usuariosRepo.delete(u);
		return u;
	}
	
	// ----------------------
	
	public boolean usuarioExiste(String email) {
		return usuariosRepo.findById(email).isPresent();
	}
}
