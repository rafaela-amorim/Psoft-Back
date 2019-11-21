package ajude.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.PrematureJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class Filter extends GenericFilterBean {

	private final int TOKEN_INDEX = 7;
	private final String TOKEN = "omae wa mou shindeiru.";
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		
		String header = req.getHeader("Authorization");
		
		if (header == null || !header.startsWith("Bearer "))
			throw new ServletException("Token inexistente ou mal formatado!");
		
		String token = header.substring(TOKEN_INDEX);
		
		try {
			String email = Jwts.parser().setSigningKey(TOKEN).parseClaimsJws(token).getBody().getSubject();
			request.setAttribute("email", email);
		} catch(SignatureException | ExpiredJwtException | MalformedJwtException | PrematureJwtException | UnsupportedJwtException | IllegalArgumentException e) {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,e.getMessage());
			return;
		}
		
		chain.doFilter(request, response);
	}
}
