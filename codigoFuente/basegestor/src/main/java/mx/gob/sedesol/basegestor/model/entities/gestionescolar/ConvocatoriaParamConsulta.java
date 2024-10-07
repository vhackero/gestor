package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import java.util.Date;

public class ConvocatoriaParamConsulta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String consulNombreConvocatoria;
	private String consulNombreCorto;
	private Date consulFechaApertura;
	private Date consulFechaCierre;
	private String consulNivelEducativo;
	private String valueConvocatoriaEstatus;
		
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
	
	

	public Date getConsulFechaApertura() {
		return consulFechaApertura;
	}
	public void setConsulFechaApertura(Date consulFechaApertura) {
		this.consulFechaApertura = consulFechaApertura;
	}
	public Date getConsulFechaCierre() {
		return consulFechaCierre;
	}
	public void setConsulFechaCierre(Date consulFechaCierre) {
		this.consulFechaCierre = consulFechaCierre;
	}
	public String getConsulNivelEducativo() {
		return consulNivelEducativo;
	}

	public void setConsulNivelEducativo(String consulNivelEducativo) {
		this.consulNivelEducativo = consulNivelEducativo;
	}

	public String getValueConvocatoriaEstatus() {
		return valueConvocatoriaEstatus;
	}

	public void setValueConvocatoriaEstatus(String valueConvocatoriaEstatus) {
		this.valueConvocatoriaEstatus = valueConvocatoriaEstatus;
	}
	
	

}
