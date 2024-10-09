package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ENTITY CONVOCATORIA
 * @author ITTIVA
 * 
 */
public class Convocatoria implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer convocatoriaId;
	
	private String nombre;
	
	private String nombreCorto;
	
	private String descripcion;
	
	private Date fecha_Apertura;
	
	private Date fechaCierre;
	
	private Integer semestre;
	
	private String tipo;
	
	private String urlConvocatoria;
	
	private String activo;
	
	private Date fechaAlta;
	
	private Date fechaModificacion;
	
	private Integer cupoLimite;
	
	private String nombreNivel;
	
	public String getNombreNivel() {
		return nombreNivel;
	}

	public void setNombreNivel(String nombreNivel) {
		this.nombreNivel = nombreNivel;
	}

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha_Apertura() {
		return fecha_Apertura;
	}

	public void setFecha_Apertura(Date fecha_Apertura) {
		this.fecha_Apertura = fecha_Apertura;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public Integer getSemestre() {
		return semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}

	

	public String getUrlConvocatoria() {
		return urlConvocatoria;
	}

	public void setUrlConvocatoria(String urlConvocatoria) {
		this.urlConvocatoria = urlConvocatoria;
	}

	

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Integer getCupoLimite() {
		return cupoLimite;
	}

	public void setCupoLimite(Integer cupoLimite) {
		this.cupoLimite = cupoLimite;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}
	

	
}
