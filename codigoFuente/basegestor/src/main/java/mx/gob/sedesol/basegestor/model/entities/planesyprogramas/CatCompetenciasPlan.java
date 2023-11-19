package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cat_competencias_plan database table.
 * 
 */
@Entity
@Table(name="cat_competencias_plan")
@NamedQuery(name="CatCompetenciasPlan.findAll", query="SELECT c FROM CatCompetenciasPlan c")
public class CatCompetenciasPlan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Integer activo;

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

	//bi-directional many-to-one association to TblPlan
	@OneToMany(mappedBy="catCompetenciasPlan")
	private List<TblPlan> tblPlans;

	public CatCompetenciasPlan() {
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

	public List<TblPlan> getTblPlanes() {
		return this.tblPlans;
	}

	public void setTblPlanes(List<TblPlan> tblPlans) {
		this.tblPlans = tblPlans;
	}

	public TblPlan addTblPlane(TblPlan tblPlan) {
		getTblPlanes().add(tblPlan);
		tblPlan.setCatCompetenciasPlan(this);

		return tblPlan;
	}

	public TblPlan removeTblPlane(TblPlan tblPlan) {
		getTblPlanes().remove(tblPlan);
		tblPlan.setCatCompetenciasPlan(null);

		return tblPlan;
	}

}