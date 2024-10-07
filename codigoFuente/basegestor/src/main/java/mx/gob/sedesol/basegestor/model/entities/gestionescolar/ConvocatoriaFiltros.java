package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import java.util.Date;

public class ConvocatoriaFiltros implements Serializable {

	private static final long serialVersionUID = 1L;	

	private Integer convocatoriaId;
	
	private String nombre;

	private String nombreCorto;

	private Date fechaApertura;

	private Date fechaCierre;

	private boolean activo;

	public Integer getConvocatoriaId() {
		return convocatoriaId;
	}

	public void setConvocatoriaId(Integer convocatoriaId) {
		this.convocatoriaId = convocatoriaId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	

	public Date getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	

}
