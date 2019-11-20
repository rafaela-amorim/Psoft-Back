package ajude.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Likes {
	
	@Id
	@GeneratedValue
	private long id;
	private String email;
	private String urlCampanha;
	
	public Likes() {
		super();
	}
	
	public Likes(String email, String urlCam) {
		this.email = email;
		this.urlCampanha = urlCam;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrlCampanha() {
		return urlCampanha;
	}

	public void setUrlCampanha(String urlCampanha) {
		this.urlCampanha = urlCampanha;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}