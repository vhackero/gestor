package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cat_distribucion_area database table.
 * 
 */
@Entity
@Table(name="cat_distribucion_area")
@NamedQuery(name="CatDistribucionArea.findAll", query="SELECT c FROM CatDistribucionArea c")
public class CatDistribucionArea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Integer activo;

	@Column(name="descripcion")
	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	private String nombre;

	private Integer orden;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to RelAreaDistribucion
	@OneToMany(mappedBy="catDistribucionArea")
	private List<RelAreaDistribucion> relAreaDistribucions;

	public CatDistribucionArea() {
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

	public List<RelAreaDistribucion> getRelAreaDistribucions() {
		return this.relAreaDistribucions;
	}

	public void setRelAreaDistribucions(List<RelAreaDistribucion> relAreaDistribucions) {
		this.relAreaDistribucions = relAreaDistribucions;
	}

	public RelAreaDistribucion addRelAreaDistribucion(RelAreaDistribucion relAreaDistribucion) {
		getRelAreaDistribucions().add(relAreaDistribucion);
		relAreaDistribucion.setCatDistribucionArea(this);

		return relAreaDistribucion;
	}

	public RelAreaDistribucion removeRelAreaDistribucion(RelAreaDistribucion relAreaDistribucion) {
		getRelAreaDistribucions().remove(relAreaDistribucion);
		relAreaDistribucion.setCatDistribucionArea(null);

		return relAreaDistribucion;
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