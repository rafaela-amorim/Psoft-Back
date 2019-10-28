package ajude.services;

import java.util.Date;

import org.springframework.stereotype.Service;

import ajude.classesAuxiliares.Token;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServices {
	private final String TOKEN = "omae wa mou shindeiru.";
	private final int TOKEN_INDEX = 7;
	
	
	public Token geraToken(String email) {
		return new Token(Jwts.builder().setSubject(email).signWith(SignatureAlgorithm.HS512, TOKEN).setExpiration(new Date(System.currentTimeMillis() + 1 * 60 * 7000)).compact());
	}
}
