package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.sql.Timestamp;

public class TblConvocatoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private String nombre;
	private String nombreCorto;
	private String descripcion;
	private Timestamp fechaApertura;
	private Timestamp fechaCierre;
	private int semestre;
	private String urlConvocatoria;
	private byte activo;
	private Timestamp fechaAlta;
	private Timestamp fechaModificacion;
	private int cupoLimite;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Timestamp getFechaApertura() {
		return fechaApertura;
	}
	public void setFechaApertura(Timestamp fechaApertura) {
		this.fechaApertura = fechaApertura;
	}
	public Timestamp getFechaCierre() {
		return fechaCierre;
	}
	public void setFechaCierre(Timestamp fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	public int getSemestre() {
		return semestre;
	}
	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}
	public String getUrlConvocatoria() {
		return urlConvocatoria;
	}
	public void setUrlConvocatoria(String urlConvocatoria) {
		this.urlConvocatoria = urlConvocatoria;
	}
	public byte getActivo() {
		return activo;
	}
	public void setActivo(byte activo) {
		this.activo = activo;
	}
	public Timestamp getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Timestamp fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Timestamp getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public int getCupoLimite() {
		return cupoLimite;
	}
	public void setCupoLimite(int cupoLimite) {
		this.cupoLimite = cupoLimite;
	}

}