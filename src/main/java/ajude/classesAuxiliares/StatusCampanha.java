package ajude.classesAuxiliares;

public enum StatusCampanha {
	ATIVA("Ativa"),
	ENCERRADA("Encerrada"), 
	VENCIDA("Vencida"), 
	CONCLUIDA("Concluída");
	
	private String status;
	
	StatusCampanha(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
}
