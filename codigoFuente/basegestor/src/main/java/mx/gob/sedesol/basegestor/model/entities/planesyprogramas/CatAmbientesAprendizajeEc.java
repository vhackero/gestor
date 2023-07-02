package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the cat_ambientes_aprendizaje_ec database table.
 * 
 */
@Entity
@Table(name="cat_ambientes_aprendizaje_ec")
@NamedQuery(name="CatAmbientesAprendizajeEc.findAll", query="SELECT c FROM CatAmbientesAprendizajeEc c")
public class CatAmbientesAprendizajeEc implements Serializable {
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
	
	@Column(name="id_catg_tipo_plataforma")
	private Integer idCatgTipoPlataforma;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to TblFichaDescriptivaPrograma
//	@OneToMany(mappedBy="catAmbientesAprendizajeEc")
//	private List<TblFichaDescriptivaPrograma> tblFichaDescriptivaProgramas;

	public CatAmbientesAprendizajeEc() {
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

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public Integer getIdCatgTipoPlataforma() {
		return idCatgTipoPlataforma;
	}

	public void setIdCatgTipoPlataforma(Integer idCatgTipoPlataforma) {
		this.idCatgTipoPlataforma = idCatgTipoPlataforma;
	}

}