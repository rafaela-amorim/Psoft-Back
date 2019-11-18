package ajude.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Comentario {
	
	@Id
	@GeneratedValue
	private long id;
	
	@NotNull
	@NotEmpty
	private String comentario;
	
//	@ManyToOne(cascade = CascadeType.DETACH)
//	@JoinColumn(name = "comment_owner", referencedColumnName = "email")
	private String commentOwner;
	
	private long idCampanha;
	private long idComentario;
	
	private boolean apagado;
	
	public Comentario() {
		super();
		this.apagado = false;
	}

	public Comentario(String comentario, long idCampanha, long idComentario) {
		this.comentario = comentario;
		this.idCampanha = idCampanha;
		this.idComentario = idComentario;
		this.apagado = false;
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
		this.idComentario = -1;
		this.idCampanha = idCampanha;
	}

	public long getIdComentario() {
		return idComentario;
	}

	public void setIdComentario(long idComentario) {
		this.idCampanha = -1;
		this.idComentario = idComentario;
	}

	public boolean isApagado() {
		return apagado;
	}

	public void setApagado(boolean apagado) {
		this.apagado = apagado;
	}

	public String getCommentOwner() {
		return commentOwner;
	}

	public void setCommentOwner(String commentOwner) {
		this.commentOwner = commentOwner;
	}

	public long getId() {
		return id;
	}
}