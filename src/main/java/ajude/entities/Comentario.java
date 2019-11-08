package ajude.entities;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class Comentario {
	
	@Id
	@GeneratedValue
	private int id;
	private String comentario;
	
	@ManyToOne(cascade = CascadeType.DETACH)
	private Usuario dono;
	
	private long idCampanha;
	private long idComentario;
	
	private boolean apagada;
	
	public Comentario() {
		super();
		this.apagada = false;
	}
	
	
	public Comentario(String comen, long idCam, long idComen) {
		this.comentario = comen;
		this.idCampanha = idCam;
		this.idComentario = idComen;
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


	public Usuario getDono() {
		return dono;
	}


	public void setDono(Usuario dono) {
		this.dono = dono;
	}
	
}
