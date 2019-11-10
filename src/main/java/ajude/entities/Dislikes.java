package ajude.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Dislikes {

	
	@Id
	@GeneratedValue
	private long id;
	private String email;
	private long idCampanha;
	
	public Dislikes() {
		super();
	}
	
	public Dislikes(String email, long idCam) {
		this.email = email;
		this.idCampanha = idCam;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getIdCampanha() {
		return idCampanha;
	}

	public void setIdCampanha(long idCampanha) {
		this.idCampanha = idCampanha;
	}
	
	
}