package mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje;

import java.io.Serializable;

public class EstatusDTO implements Serializable{

	private static final long serialVersionUID = 1L;

    private Integer valorEstatus;
	private String nombreEstatus;

    // Constructor
    public EstatusDTO(Integer valorEstatus,String nombreEstatus) {
        this.valorEstatus = valorEstatus;
        this.nombreEstatus = nombreEstatus;
    }    
	
	public Integer getValorEstatus() {
		return valorEstatus;
	}
	public void setValorEstatus(Integer valorEstatus) {
		this.valorEstatus = valorEstatus;
	}
	public String getNombreEstatus() {
		return nombreEstatus;
	}
	public void setNombreEstatus(String nombreEstatus) {
		this.nombreEstatus = nombreEstatus;
	}
	
	

}
