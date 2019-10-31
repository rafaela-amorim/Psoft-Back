package ajude.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import ajude.classesAuxiliares.StatusCampanha;

@Entity
public class Campanha {

	@Id
	@GeneratedValue
	private long id;
	private String nome;
//	@Id
	private String url;
	private String descricao;
	private Date data;
	private StatusCampanha status;
	private double meta;
	private double doacoes;
	private Usuario usuario;
	//private Comentario comentarios;
	private int likes;
	
	public Campanha() {}
	
	public Campanha(String nome, String url, String descricao, Date data, StatusCampanha status, double meta, double doacoes,
			Usuario usuario, int likes) {
		super();
		this.nome = nome;
		this.url = url;
		this.descricao = descricao;
		this.data = data;
		this.status = status;
		this.meta = meta;
		this.doacoes = doacoes;
		this.usuario = usuario;
		this.likes = likes;
	}
	
	// ----------------------------

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public StatusCampanha getStatus() {
		return status;
	}

	public void setStatus(StatusCampanha status) {
		this.status = StatusCampanha.ATIVA;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}
}
