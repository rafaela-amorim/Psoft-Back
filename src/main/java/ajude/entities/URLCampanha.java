package ajude.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class URLCampanha {
	
	@Id
	private String url;
	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private Campanha campanha;
	
	public URLCampanha() {
		super();
	}
	
	public URLCampanha(String url, Campanha campanha) {
		this.url = url;
		this.campanha = campanha;
	}
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Campanha getCampanha() {
		return campanha;
	}
	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}
	
	
}
