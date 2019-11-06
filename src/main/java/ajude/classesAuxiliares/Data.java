package ajude.classesAuxiliares;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Data {
	@JsonFormat(pattern="yyyy-MM-dd")
	Date data;
	
	public Data() {
		super();
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	
}
