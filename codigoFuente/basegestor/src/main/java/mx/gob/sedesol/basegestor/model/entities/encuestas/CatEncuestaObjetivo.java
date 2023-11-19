package mx.gob.sedesol.basegestor.model.entities.encuestas;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the cat_encuesta_objetivo database table.
 * 
 */
@Entity
@Table(name="cat_encuesta_objetivo")
@NamedQuery(name="CatEncuestaObjetivo.findAll", query="SELECT c FROM CatEncuestaObjetivo c")
public class CatEncuestaObjetivo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer activo;
	private String descripcion;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private String nombre;
	private Integer orden;
	private Long usuarioModifico;
	private List<TblEncuesta> tblEncuestas;

	public CatEncuestaObjetivo() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_encuesta_objetivo", unique=true, nullable=false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getActivo() {
		return this.activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}


	@Column(length=255)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_creacion", nullable=false)
	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}


	@Column(nullable=false, length=155)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}


	@Column(name="usuario_modifico", nullable=false)
	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}


	//bi-directional many-to-one association to TblEncuesta
	@OneToMany(mappedBy="encuestaObjetivo")
	public List<TblEncuesta> getTblEncuestas() {
		return this.tblEncuestas;
	}

	public void setTblEncuestas(List<TblEncuesta> tblEncuestas) {
		this.tblEncuestas = tblEncuestas;
	}

}