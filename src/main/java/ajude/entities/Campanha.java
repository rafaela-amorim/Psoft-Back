package ajude.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import ajude.enums.StatusCampanha;

@Entity
/*
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "url", name = "url")}
)*/
public class Campanha {

	@Id
	@GeneratedValue
	private long id;
	private String nome;
	private String descricao;
	private Date dataLimite;
	private StatusCampanha status;
	private String url;
	private double meta;
	private double doacoes;
	@ManyToOne(cascade = CascadeType.DETACH)
	private Usuario dono;
	
	private int likes;
	private int dislikes;
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@JoinColumn(name = "campanha_id", referencedColumnName = "id")
//	private List<Comentario> comentarios;
	// private List<String> likes;

	public Campanha() {
		super();
		this.doacoes = 0;
		this.likes = 0;
		this.dislikes = 0;
		this.dataLimite = new Date();
		this.status = StatusCampanha.ATIVA;
	}

	@SuppressWarnings("deprecation")
	public Campanha(String nome, String descricao, double meta, String data) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.meta = meta;
		this.dono = null;
		
		this.doacoes = 0;
		this.likes = 0;
		this.dislikes = 0;
		this.dataLimite = new Date(data);
		this.status = StatusCampanha.ATIVA;
	}

	// ----------------------------
	
	public double fazDoacao(double quantia) {
		this.doacoes += quantia;
		this.meta = Math.max(0,this.getMeta() - quantia);
		return this.getMeta();
	}

	public boolean estaAtiva() {
		return this.status == StatusCampanha.ATIVA;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getId() {
		return id;
	}

	public int getDislikes() {
		return dislikes;
	}
	
	public int getLikes() {
		return likes;
	}
	
	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}
	
	public void setLikes(int likes) {
		this.likes = likes;
	}

	public void incrementaLike() {
		this.likes++;
	}

	public void incrementaDislike() {
		this.dislikes++;
	}

	public void decrementaLike() {
		this.likes--;		
	}

	public void decrementaDislike() {
		this.dislikes--;
	}
	
//	public List<Comentario> getComentarios() {
//		return comentarios;
//	}
//
//	public void setComentarios(List<Comentario> comentarios) {
//		this.comentarios = comentarios;
//	}
//	
//	public void addComentario(Comentario comentario) {
//		this.comentarios.add(comentario);
//	}
}