package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cat_modalidad_plan_programas database table.
 * 
 */
@Entity
@Table(name="cat_modalidad_plan_programas")
@NamedQuery(name="CatModalidadPlanPrograma.findAll", query="SELECT c FROM CatModalidadPlanPrograma c")
public class CatModalidadPlanPrograma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="activo")
	private Integer activo;

	@Column(name="descripcion")
	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="nombre")
	private String nombre;

	@Column(name="orden")
	private Integer orden;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	public CatModalidadPlanPrograma() {
	}

	//bi-directional many-to-one association to TblEvento
	@OneToMany(mappedBy="catModalidadPlanPrograma")
	private List<TblEvento> eventosCapacitacion;
	
	
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

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

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

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public List<TblEvento> getEventosCapacitacion() {
		return eventosCapacitacion;
	}

	public void setEventosCapacitacion(List<TblEvento> eventosCapacitacion) {
		this.eventosCapacitacion = eventosCapacitacion;
	}

	
}