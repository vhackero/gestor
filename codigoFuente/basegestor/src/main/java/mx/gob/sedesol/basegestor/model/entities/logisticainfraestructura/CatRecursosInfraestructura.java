package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cat_recursos_infraestructura database table.
 * 
 */
@Entity
@Table(name="cat_recursos_infraestructura")
@NamedQuery(name="CatRecursosInfraestructura.findAll", query="SELECT c FROM CatRecursosInfraestructura c")
public class CatRecursosInfraestructura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_recurso")
	private Integer idRecurso;

	private Integer activo;

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

	//bi-directional many-to-one association to CatTipoRecurso
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_tipo_recurso")
	private CatTipoRecurso catTipoRecurso;

	//bi-directional many-to-one association to RelAreaRecurso
	@OneToMany(mappedBy="catRecursosInfraestructura")
	private List<RelAreaRecurso> relAreaRecursos;

	public CatRecursosInfraestructura() {
	}

	public Integer getIdRecurso() {
		return this.idRecurso;
	}

	public void setIdRecurso(Integer idRecurso) {
		this.idRecurso = idRecurso;
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

	public CatTipoRecurso getCatTipoRecurso() {
		return this.catTipoRecurso;
	}

	public void setCatTipoRecurso(CatTipoRecurso catTipoRecurso) {
		this.catTipoRecurso = catTipoRecurso;
	}

	public List<RelAreaRecurso> getRelAreaRecursos() {
		return this.relAreaRecursos;
	}

	public void setRelAreaRecursos(List<RelAreaRecurso> relAreaRecursos) {
		this.relAreaRecursos = relAreaRecursos;
	}

	public RelAreaRecurso addRelAreaRecurso(RelAreaRecurso relAreaRecurso) {
		getRelAreaRecursos().add(relAreaRecurso);
		relAreaRecurso.setCatRecursosInfraestructura(this);

		return relAreaRecurso;
	}

	public RelAreaRecurso removeRelAreaRecurso(RelAreaRecurso relAreaRecurso) {
		getRelAreaRecursos().remove(relAreaRecurso);
		relAreaRecurso.setCatRecursosInfraestructura(null);

		return relAreaRecurso;
	}

}