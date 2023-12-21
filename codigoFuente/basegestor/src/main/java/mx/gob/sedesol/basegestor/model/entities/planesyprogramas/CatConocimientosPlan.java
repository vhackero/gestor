package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * Entity implementation class for Entity: CatConocimientosPlan
 *
 */
@Entity
@Table(name="cat_conocimientos_plan")
@NamedQuery(name="CatConocimientosPlan.findAll", query="SELECT c FROM CatConocimientosPlan c")
public class CatConocimientosPlan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String nombre;
	private String descripcion;
	private Integer activo;
	private Integer orden;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;
	
	@Column(name="usuario_modifico")
	private Long usuario_modifico;

	public CatConocimientosPlan() {
		super();
	}   
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}   
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}   
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}   
	public Integer getActivo() {
		return this.activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}   
	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}   
	
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public Long getUsuario_modifico() {
		return this.usuario_modifico;
	}

	public void setUsuario_modifico(Long usuario_modifico) {
		this.usuario_modifico = usuario_modifico;
	}
   
}
