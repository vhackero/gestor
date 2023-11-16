package mx.gob.sedesol.basegestor.model.entities.encuestas;

import java.io.Serializable;
import java.math.BigInteger;
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
 * The persistent class for the cat_encuesta_contexto database table.
 * 
 */
@Entity
@Table(name="cat_encuesta_contexto")
@NamedQuery(name="CatEncuestaContexto.findAll", query="SELECT c FROM CatEncuestaContexto c")
public class CatEncuestaContexto implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer activo;
	private String descripcion;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private String nombre;
	private Integer orden;
	private BigInteger usuarioModifico;
	private List<CatEncuestaTipo> catEncuestaTipo;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_encuesta_contexto", unique=true, nullable=false)
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


	@Column(nullable=false, length=150)
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
	public BigInteger getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(BigInteger usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}


	//bi-directional many-to-one association to CatEncuestaTipo
	@OneToMany(mappedBy="contexto")
	public List<CatEncuestaTipo> getCatEncuestaTipo() {
		return this.catEncuestaTipo;
	}

	public void setCatEncuestaTipo(List<CatEncuestaTipo> catEncuestaTipo) {
		this.catEncuestaTipo = catEncuestaTipo;
	}

}