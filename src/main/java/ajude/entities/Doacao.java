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
	private String idDoador;
	private double quantia;
	private Date data;
	private long idCampanha;

	public Doacao(String idDoador, double quantia, Date data, long idCampanha) {
		super();
		this.idDoador = idDoador;
		this.quantia = quantia;
		this.data = data;
		this.idCampanha = idCampanha;
	}

	public long getId() {
		return id;
	}

	public String getIdDoador() {
		return idDoador;
	}

	public void setIdDoador(String idDoador) {
		this.idDoador = idDoador;
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

	public long getIdCampanha() {
		return idCampanha;
	}

	public void setIdCampanha(long idCampanha) {
		this.idCampanha = idCampanha;
	}
	
}