package mx.gob.sedesol.gestorscheduler.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="respuesta")
public class RespuestaDTO{
	
	private String descripcionError =  "Ocurrio un error";
	private String nombreCron;
	private Integer estatus = -1;
	
	
	
	public String getDescripcionError() {
		return descripcionError;
	}
	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}
	public String getNombreCron() {
		return nombreCron;
	}
	public void setNombreCron(String nombreCron) {
		this.nombreCron = nombreCron;
	}
	public Integer getEstatus() {
		return estatus;
	}
	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}
	
	
	
	
	
	
	
	
}