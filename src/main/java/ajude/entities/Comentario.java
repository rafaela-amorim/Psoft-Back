package ajude.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Comentario {
	
	@Id
	@GeneratedValue
	private int id;
	
	@NotNull
	@NotEmpty
	private String comentario;
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "comment_owner", referencedColumnName = "email")
	private Usuario commentOwner;
	
	private long idCampanha;
	private long idComentario; //????
	
	private boolean apagada;
	
	public Comentario() {
		super();
		this.apagada = false;
	}
	
	public Comentario(String comentario, long idCampanha, long idComentario) {
		this.comentario = comentario;
		this.idCampanha = idCampanha;
		this.idComentario = idComentario;
		this.apagada = false;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public long getIdCampanha() {
		return idCampanha;
	}

	public void setIdCampanha(long idCampanha) {
		this.idCampanha = idCampanha;
	}

	public long getIdComentario() {
		return idComentario;
	}

	public void setIdComentario(long idComentario) {
		this.idComentario = idComentario;
	}

	public boolean isApagada() {
		return apagada;
	}

	public void setApagada(boolean apagada) {
		this.apagada = apagada;
	}

	public Usuario getCommentOwner() {
		return commentOwner;
	}

	public void setCommentOwner(Usuario commentOwner) {
		this.commentOwner = commentOwner;
	}
}