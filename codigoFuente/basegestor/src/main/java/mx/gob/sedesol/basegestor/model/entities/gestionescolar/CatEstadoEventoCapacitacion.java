package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.math.BigInteger;


/**
 * The persistent class for the cat_estado_evento_capacitacion database table.
 * 
 */
@Entity
@Table(name="cat_estado_evento_capacitacion")
@NamedQuery(name="CatEstadoEventoCapacitacion.findAll", query="SELECT c FROM CatEstadoEventoCapacitacion c")
public class CatEstadoEventoCapacitacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Integer activo;

	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	private String nombre;

	private Integer orden;

	@Column(name="usuario_modifico")
	private BigInteger usuarioModifico;
	
	//bi-directional many-to-one association to TblEvento
	@OneToMany(mappedBy="catEstadoEventoCapacitacion",cascade= CascadeType.ALL)
	private List<TblEvento> tblEventos;

	public CatEstadoEventoCapacitacion() {
	}

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

	public BigInteger getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(BigInteger usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public List<TblEvento> getTblEventos() {
		return tblEventos;
	}

	public void setTblEventos(List<TblEvento> tblEventos) {
		this.tblEventos = tblEventos;
	}
	
	

}