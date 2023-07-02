package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cat_tipo_recurso database table.
 * 
 */
@Entity
@Table(name="cat_tipo_recurso")
@NamedQuery(name="CatTipoRecurso.findAll", query="SELECT c FROM CatTipoRecurso c")
public class CatTipoRecurso implements Serializable {
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
	private Long usuarioModifico;

	//bi-directional many-to-one association to CatRecursosInfraestructura
	@OneToMany(mappedBy="catTipoRecurso")
	private List<CatRecursosInfraestructura> catRecursosInfraestructuras;

	public CatTipoRecurso() {
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

	public List<CatRecursosInfraestructura> getCatRecursosInfraestructuras() {
		return this.catRecursosInfraestructuras;
	}

	public void setCatRecursosInfraestructuras(List<CatRecursosInfraestructura> catRecursosInfraestructuras) {
		this.catRecursosInfraestructuras = catRecursosInfraestructuras;
	}

	public CatRecursosInfraestructura addCatRecursosInfraestructura(CatRecursosInfraestructura catRecursosInfraestructura) {
		getCatRecursosInfraestructuras().add(catRecursosInfraestructura);
		catRecursosInfraestructura.setCatTipoRecurso(this);

		return catRecursosInfraestructura;
	}

	public CatRecursosInfraestructura removeCatRecursosInfraestructura(CatRecursosInfraestructura catRecursosInfraestructura) {
		getCatRecursosInfraestructuras().remove(catRecursosInfraestructura);
		catRecursosInfraestructura.setCatTipoRecurso(null);

		return catRecursosInfraestructura;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}