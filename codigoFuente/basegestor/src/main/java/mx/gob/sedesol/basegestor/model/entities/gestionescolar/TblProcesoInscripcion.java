package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the tbl_procesos_inscripcion database table.
 * 
 */
@Entity
@Table(name="tbl_procesos_inscripcion")
@NamedQuery(name="TblProcesoInscripcion.findAll", query="SELECT t FROM TblProcesoInscripcion t")
public class TblProcesoInscripcion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="proceso_inscripcion_id")
	private Integer procesoInscripcionId;

	private String nombre;

	@Column(name="clave_proceso")
	private String claveProceso;

	private String descripcion;

	@Column(name="fecha_inicio")
	private Timestamp fechaInicio;

	@Column(name="fecha_fin")
	private Timestamp fechaFin;
	
	private byte activo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_proceso")
	private CatProcesoInscripcion tipoProceso;
	
	// bi-directional many-to-one association to TblFichaDescriptivaPrograma
	@ManyToOne
	@JoinColumn(name = "convocatoria_id", insertable = false, updatable = false)
	private TblConvocatoria convocatoria;

	public TblProcesoInscripcion() {
	}

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

	public CatProcesoInscripcion getTipoProceso() {
		return tipoProceso;
	}

	public void setTipoProceso(CatProcesoInscripcion tipoProceso) {
		this.tipoProceso = tipoProceso;
	}

	public TblConvocatoria getConvocatoria() {
		return convocatoria;
	}

	public void setConvocatoria(TblConvocatoria convocatoria) {
		this.convocatoria = convocatoria;
	}

}