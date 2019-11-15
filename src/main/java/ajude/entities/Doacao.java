package ajude.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Doacao {

	@Id
	@GeneratedValue
	private long id;
	private String emailDoador;
	private double quantia;
	private Date data;
	private String urlCampanha;
	
	public Doacao() {
		super();
	}

	public Doacao(String idDoador, double quantia, String urlCampanha) {
		super();
		this.emailDoador = idDoador;
		this.quantia = quantia;
		this.data = new Date();
		this.urlCampanha = urlCampanha;
	}

	public long getId() {
		return id;
	}

	public String getEmailDoador() {
		return emailDoador;
	}

	public void setEmailDoador(String idDoador) {
		this.emailDoador = idDoador;
	}

	public double getQuantia() {
		return quantia;
	}

	public void setQuantia(double quantia) {
		this.quantia = quantia;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getUrlCampanha() {
		return urlCampanha;
	}

	public void setUrlCampanha(String urlCampanha) {
		this.urlCampanha = urlCampanha;
	}
	
}