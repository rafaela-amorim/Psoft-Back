package ajude.services;

import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.stereotype.Service;

import ajude.classesAuxiliares.LoginResponse;
import ajude.entities.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTService {

	private final String TOKEN = "omae wa mou shindeiru.";
	private final int TOKEN_INDEX = 7;

	private UsuarioService usuarioService;

	public JWTService(UsuarioService usuarioService) {
		super();
		this.usuarioService = usuarioService;
	}

	public LoginResponse geraToken(String email) {
		return new LoginResponse(Jwts.builder().setSubject(email).signWith(SignatureAlgorithm.HS512, TOKEN)
				.setExpiration(new Date(System.currentTimeMillis() + 1 * 60 * 7000)).compact());
	}

	public LoginResponse authenticate(Usuario usuario) throws ServletException {
		Usuario authUser = usuarioService.getUsuario(usuario.getEmail());

		if (authUser.verificaSenha(usuario.getSenha()))
			return this.geraToken(authUser.getEmail());
		throw new ServletException("Senha invalida!");
	}

	public String getEmailToken(String token) {
		String Token = token.substring(TOKEN_INDEX);
		return Jwts.parser().setSigningKey(TOKEN).parseClaimsJws(Token).getBody().getSubject();
	}

	public Usuario getUsuario(String token) {
		return usuarioService.getUsuario(getEmailToken(token));
	}
	
	public boolean usuarioExiste(String email) {
		return usuarioService.usuarioExiste(email);
	}
}