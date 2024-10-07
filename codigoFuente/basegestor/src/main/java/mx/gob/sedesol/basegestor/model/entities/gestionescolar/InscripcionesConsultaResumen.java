package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import java.math.BigInteger;

public class InscripcionesConsultaResumen implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String procesoInscripcionId;
	private String idConvocatoria;
	private String nombreConvocatoria;
	private String nombre;
	private String fecIni;
	private String fecFin;
	private String tipoProceso;
	private String estatus;
	
	public String getProcesoInscripcionId() {
		return procesoInscripcionId;
	}
	public void setProcesoInscripcionId(String procesoInscripcionId) {
		this.procesoInscripcionId = procesoInscripcionId;
	}
	public String getIdConvocatoria() {
		return idConvocatoria;
	}
	public void setIdConvocatoria(String idConvocatoria) {
		this.idConvocatoria = idConvocatoria;
	}
	public String getNombreConvocatoria() {
		return nombreConvocatoria;
	}
	public void setNombreConvocatoria(String nombreConvocatoria) {
		this.nombreConvocatoria = nombreConvocatoria;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getFecIni() {
		return fecIni;
	}
	public void setFecIni(String fecIni) {
		this.fecIni = fecIni;
	}
	public String getFecFin() {
		return fecFin;
	}
	public void setFecFin(String fecFin) {
		this.fecFin = fecFin;
	}
	public String getTipoProceso() {
		return tipoProceso;
	}
	public void setTipoProceso(String tipoProceso) {
		this.tipoProceso = tipoProceso;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	 
	
	
	

}
