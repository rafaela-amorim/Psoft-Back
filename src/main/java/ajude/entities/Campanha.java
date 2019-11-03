package ajude.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ajude.classesAuxiliares.StatusCampanha;

@Entity
public class Campanha {

	@Id
	@GeneratedValue
	private long id;
	private String nome;
	private String descricao;
	private Date dataLimite;
	private StatusCampanha status;
	
	@OneToOne(cascade=CascadeType.REMOVE)
	private URLCampanha url;
	private double meta;
	private double doacoes;
	
	@ManyToOne(cascade=CascadeType.DETACH)
	@JsonIgnore
	private Usuario dono;
	//private Comentario comentarios;
	//private List<String> likes;
	
	
	public Campanha() {
		super();
		this.doacoes = 0;
		this.dataLimite = new Date();
		this.status = StatusCampanha.ATIVA;
	}
	
	public Campanha(String nome, String descricao, double meta, Usuario dono) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.meta = meta;
		this.dono = dono;
	
		this.doacoes = 0;
		this.dataLimite = new Date();
		this.status = StatusCampanha.ATIVA;
	}
	
	// ----------------------------


	public URLCampanha getURLCampanha() {
		return url;
	}

	public void setURLCampanha(URLCampanha u) {
		this.url = u;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataLimite() {
		return dataLimite;
	}

	public void setDataLimite(Date data) {
		this.dataLimite = data;
	}

	public StatusCampanha getStatus() {
		return status;
	}

	public void setStatus(StatusCampanha status) {
		this.status = status;
	}

	public double getMeta() {
		return meta;
	}

	public void setMeta(double meta) {
		this.meta = meta;
	}

	public double getDoacoes() {
		return doacoes;
	}

	public void setDoacoes(double doacoes) {
		this.doacoes = doacoes;
	}

	public Usuario getDono() {
		return dono;
	}

	public void setDono(Usuario dono) {
		this.dono = dono;
	}

}
