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
 * The persistent class for the cat_encuesta_estatus database table.
 * 
 */
@Entity
@Table(name="cat_encuesta_estatus")
@NamedQuery(name="CatEncuestaEstatus.findAll", query="SELECT c FROM CatEncuestaEstatus c")
public class CatEncuestaEstatus implements Serializable {

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

	public CatEncuestaEstatus() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_encuesta_estatus")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

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
	@Column(name="fecha_creacion")
	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

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


	@Column(name="usuario_modifico")
	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}


	//bi-directional many-to-one association to TblEncuesta
	@OneToMany(mappedBy="encuestaEstatus")
	public List<TblEncuesta> getTblEncuestas() {
		return this.tblEncuestas;
	}

	public void setTblEncuestas(List<TblEncuesta> tblEncuestas) {
		this.tblEncuestas = tblEncuestas;
	}
}