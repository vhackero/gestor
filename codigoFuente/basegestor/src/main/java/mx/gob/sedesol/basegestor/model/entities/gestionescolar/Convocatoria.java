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
@Entity
@Table(name="tbl_convocatoria")
@NamedQuery(name="Convocatoria.findAll", query="SELECT r FROM Convocatoria r")
public class Convocatoria implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="convocatoria_id")
	private Integer convocatoriaId;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="nombre_corto")
	private String nombreCorto;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_apertura")
	private Date fecha_Apertura;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_cierre")
	private Date fechaCierre;
	
	@Column(name="semestre")
	private Integer semestre;
	
	@Column(name="tipo", nullable = false, columnDefinition = "TINYINT(1)")
	private String tipo;
	
	@Column(name="url_convocatoria")
	private String urlConvocatoria;
	

	@Column(name="activo", nullable = false, columnDefinition = "TINYINT(1)")
	private String activo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_alta")
	private Date fechaAlta;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_modificacion")
	private Date fechaModificacion;
	
	@Column(name="cupo_limite")
	private Integer cupoLimite;
	

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
