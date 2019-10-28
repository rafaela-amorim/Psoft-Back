package ajude.entities;

import javax.persistence.Id;

public class Usuario {
	
	private String nome;
	private String ultimoNome;
	@Id
	private String email;
	private String cartaoDeCredito;
	private String senha;
	
	public Usuario(String nome, String ultimoNome, String email, String cartaoDeCredito, String senha) {
		super();
		this.nome = nome;
		this.ultimoNome = ultimoNome;
		this.email = email;
		this.cartaoDeCredito = cartaoDeCredito;
		this.senha = senha;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getUltimoNome() {
		return ultimoNome;
	}



	public void setUltimoNome(String ultimoNome) {
		this.ultimoNome = ultimoNome;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getCartaoDeCredito() {
		return cartaoDeCredito;
	}



	public void setCartaoDeCredito(String cartaoDeCredito) {
		this.cartaoDeCredito = cartaoDeCredito;
	}



	public String getSenha() {
		return senha;
	}



	public void setSenha(String senha) {
		this.senha = senha;
	}

}
