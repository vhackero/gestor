package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the tbl_malla_curricular database table.
 * 
 */
@Entity
@Table(name="tbl_malla_curricular")
@NamedQuery(name="TblMallaCurricular.findAll", query="SELECT t FROM TblMallaCurricular t")
public class TblMallaCurricular implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	private Integer activo;

	@Column(name="descripcion", columnDefinition="TEXT")
	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@ManyToOne
	@JoinColumn(name = "id_padre")
	private TblMallaCurricular mallaCurricularPadre;
	
	
	@OneToMany(mappedBy="mallaCurricularPadre", fetch=FetchType.LAZY, cascade= CascadeType.REMOVE)
	private List<TblMallaCurricular> lstHijosMallaCurr;

	@Column(name="id_plan")
	private Integer idPlan;

	private String nombre;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional one-to-one association to CatObjetoCurricular
	@OneToOne
	@JoinColumn(name="id_objeto_curricular")
	private CatObjetoCurricular objetoCurricular;
	
	@Column(name="id_categoria_mdl")
	private Integer idCategoriaMdl;

	public TblMallaCurricular() {
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

	/**
	 * @return the mallaCurricularPadre
	 */
	public TblMallaCurricular getMallaCurricularPadre() {
		return mallaCurricularPadre;
	}

	/**
	 * @param mallaCurricularPadre the mallaCurricularPadre to set
	 */
	public void setMallaCurricularPadre(TblMallaCurricular mallaCurricularPadre) {
		this.mallaCurricularPadre = mallaCurricularPadre;
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

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	/**
	 * @return the objetoCurricular
	 */
	public CatObjetoCurricular getObjetoCurricular() {
		return objetoCurricular;
	}

	/**
	 * @param objetoCurricular the objetoCurricular to set
	 */
	public void setObjetoCurricular(CatObjetoCurricular objetoCurricular) {
		this.objetoCurricular = objetoCurricular;
	}

	/**
	 * @return the lstHijosMallaCurr
	 */
	public List<TblMallaCurricular> getLstHijosMallaCurr() {
		return lstHijosMallaCurr;
	}

	/**
	 * @param lstHijosMallaCurr the lstHijosMallaCurr to set
	 */
	public void setLstHijosMallaCurr(List<TblMallaCurricular> lstHijosMallaCurr) {
		this.lstHijosMallaCurr = lstHijosMallaCurr;
	}

	/**
	 * @return the idPlan
	 */
	public Integer getIdPlan() {
		return idPlan;
	}

	/**
	 * @param idPlan the idPlan to set
	 */
	public void setIdPlan(Integer idPlan) {
		this.idPlan = idPlan;
	}

	/**
	 * @return the idCategoriaMdl
	 */
	public Integer getIdCategoriaMdl() {
		return idCategoriaMdl;
	}

	/**
	 * @param idCategoriaMdl the idCategoriaMdl to set
	 */
	public void setIdCategoriaMdl(Integer idCategoriaMdl) {
		this.idCategoriaMdl = idCategoriaMdl;
	}

}