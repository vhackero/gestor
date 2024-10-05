package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import java.util.Date;

public class ConvocatoriaParamConsulta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String consulNombreConvocatoria;
	private String consulNombreCorto;
	private String consulFechaApertura;
	private String consulFechaCierre;
	private Integer consulNivelEducativo;
	private Integer valueConvocatoriaEstatus;
	
	
	public ConvocatoriaParamConsulta() {
		super();
		this.consulNombreConvocatoria = consulNombreConvocatoria;
		this.consulNombreCorto = consulNombreCorto;
		this.consulFechaApertura = consulFechaApertura;
		this.consulFechaCierre = consulFechaCierre;
		this.consulNivelEducativo = consulNivelEducativo;
		this.valueConvocatoriaEstatus = valueConvocatoriaEstatus;
	}
	public String getConsulNombreConvocatoria() {
		return consulNombreConvocatoria;
	}
	public void setConsulNombreConvocatoria(String consulNombreConvocatoria) {
		this.consulNombreConvocatoria = consulNombreConvocatoria;
	}
	public String getConsulNombreCorto() {
		return consulNombreCorto;
	}
	public void setConsulNombreCorto(String consulNombreCorto) {
		this.consulNombreCorto = consulNombreCorto;
	}
	
	
	public String getConsulFechaApertura() {
		return consulFechaApertura;
	}
	public void setConsulFechaApertura(String consulFechaApertura) {
		this.consulFechaApertura = consulFechaApertura;
	}
	public String getConsulFechaCierre() {
		return consulFechaCierre;
	}
	public void setConsulFechaCierre(String consulFechaCierre) {
		this.consulFechaCierre = consulFechaCierre;
	}
	public Integer getConsulNivelEducativo() {
		return consulNivelEducativo;
	}
	public void setConsulNivelEducativo(Integer consulNivelEducativo) {
		this.consulNivelEducativo = consulNivelEducativo;
	}
	public Integer getValueConvocatoriaEstatus() {
		return valueConvocatoriaEstatus;
	}
	public void setValueConvocatoriaEstatus(Integer valueConvocatoriaEstatus) {
		this.valueConvocatoriaEstatus = valueConvocatoriaEstatus;
	}
	
	

}
