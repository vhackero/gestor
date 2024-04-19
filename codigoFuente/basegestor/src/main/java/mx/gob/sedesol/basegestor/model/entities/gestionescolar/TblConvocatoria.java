package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbl_convocatoria database table.
 * 
 */
@Entity
@Table(name="tbl_convocatoria")
@NamedQuery(name="TblConvocatoria.findAll", query="SELECT c FROM TblConvocatoria c")
public class TblConvocatoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="convocatoria_id")
	private int id;
	
	private String nombre;
	
	@Column(name="nombre_corto")
	private String nombreCorto;
	
	private String descripcion;
	
	@Column(name="fecha_apertura")
	private Timestamp fechaApertura;

	@Column(name="fecha_cierre")
	private Timestamp fechaCierre;
	
	private int semestre;
	
	@Column(name="url_convocatoria")
	private String urlConvocatoria;
	
	private byte activo;
	
	@Column(name="fecha_alta")
	private Timestamp fechaAlta;

	@Column(name="fecha_modificacion")
	private Timestamp fechaModificacion;
	
	@Column(name="cupo_limite")
	private int cupoLimite;

	public TblConvocatoria() {
	}

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