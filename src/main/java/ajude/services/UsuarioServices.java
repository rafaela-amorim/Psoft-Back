package ajude.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ajude.DAOs.UsuarioRepository;
import ajude.entities.Usuario;

@Service
public class UsuarioServices {
	
	private UsuarioRepository<Usuario, String> usuariosRepo;
	
	public UsuarioServices(UsuarioRepository<Usuario, String> usuariosRepo) {
		super();
		this.usuariosRepo = usuariosRepo;
	}

	// ----------------------
	
	// antes de criar um novo usuario, verifica se o email já nao existia antes no banco de dados
	public Usuario addUsuario(Usuario usuario) {
		return usuariosRepo.save(usuario);
	}
	
	public Usuario recuperaUsuario(String email) {
		return usuariosRepo.findById(email).get();
	}
	
	// verifica se o usuario existe no controller
	public Usuario mudarSenha(String email, String novaSenha) {
		Usuario u = recuperaUsuario(email);
		u.setSenha(novaSenha);
		addUsuario(u);
		return u;
	}
	
	
	// ----------------------
	
	public boolean UsuarioExiste(String email) {
		return usuariosRepo.findById(email).isPresent();
	}
}
