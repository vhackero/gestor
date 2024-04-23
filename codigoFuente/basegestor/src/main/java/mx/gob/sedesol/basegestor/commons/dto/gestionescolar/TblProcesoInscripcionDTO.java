package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.sql.Timestamp;


public class TblProcesoInscripcionDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer procesoInscripcionId;
	private String nombre;
	private String claveProceso;
	private String descripcion;
	private Timestamp fechaInicio;
	private Timestamp fechaFin;	
	private byte activo;
	private CatProcesoInscripcionDTO tipoProceso;
	private TblConvocatoriaDTO convocatoria;
	
	public Integer getProcesoInscripcionId() {
		return procesoInscripcionId;
	}
	public void setProcesoInscripcionId(Integer procesoInscripcionId) {
		this.procesoInscripcionId = procesoInscripcionId;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getClaveProceso() {
		return claveProceso;
	}
	public void setClaveProceso(String claveProceso) {
		this.claveProceso = claveProceso;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Timestamp getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Timestamp getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}
	public byte getActivo() {
		return activo;
	}
	public void setActivo(byte activo) {
		this.activo = activo;
	}
	public CatProcesoInscripcionDTO getTipoProceso() {
		return tipoProceso;
	}
	public void setTipoProceso(CatProcesoInscripcionDTO tipoProceso) {
		this.tipoProceso = tipoProceso;
	}
	public TblConvocatoriaDTO getConvocatoria() {
		return convocatoria;
	}
	public void setConvocatoria(TblConvocatoriaDTO convocatoria) {
		this.convocatoria = convocatoria;
	}
	
	@Override
	public String toString() {
		return "TblProcesoInscripcionDTO [procesoInscripcionId=" + procesoInscripcionId + ", nombre=" + nombre
				+ ", claveProceso=" + claveProceso + ", descripcion=" + descripcion + ", fechaInicio=" + fechaInicio
				+ ", fechaFin=" + fechaFin + ", activo=" + activo + ", tipoProceso=" + tipoProceso + ", convocatoria="
				+ convocatoria + "]";
	}
}