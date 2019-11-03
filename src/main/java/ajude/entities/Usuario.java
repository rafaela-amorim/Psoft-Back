package ajude.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Usuario {
	
	private String nome;
	private String ultimoNome;
	@Id
	private String email;
	private String cartaoDeCredito;
	private String senha;
	
	@OneToMany(mappedBy = "dono", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Campanha> campanhas;
	
	public Usuario() {
		super();
		campanhas = new ArrayList<>();
	}
	
	public Usuario(String nome, String ultimoNome, String email, String cartaoDeCredito, String senha) {
		super();
		this.nome = nome;
		this.ultimoNome = ultimoNome;
		this.email = email;
		this.cartaoDeCredito = cartaoDeCredito;
		this.senha = senha;
		
		campanhas = new ArrayList<>();
	}

	
	public void adicionaCampanha(Campanha c) {
		campanhas.add(c);
	}

	public boolean verificaSenha(String pass) {
		return this.senha.equals(pass);
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

	public List<Campanha> getCampanhas() {
		return campanhas;
	}

	public void setCampanhas(List<Campanha> campanhas) {
		this.campanhas = campanhas;
	}
	
 
}
