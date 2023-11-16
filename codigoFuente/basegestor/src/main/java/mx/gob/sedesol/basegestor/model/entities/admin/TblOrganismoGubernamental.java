package mx.gob.sedesol.basegestor.model.entities.admin;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the tbl_organismos_gubernamentales database table.
 * 
 */
@Entity
@Table(name="tbl_organismos_gubernamentales")
@NamedQuery(name="TblOrganismoGubernamental.findAll", query="SELECT t FROM TblOrganismoGubernamental t")
public class TblOrganismoGubernamental implements Serializable {
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

	@ManyToOne
	@JoinColumn(name="id_padre")
	private TblOrganismoGubernamental orgGubPadre;

	@Column(name="id_tipo_organismo")
	private Integer idTipoOrganismo;

	private String nombre;

	private Integer orden;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;
	
	@OneToMany(mappedBy="orgGubPadre", fetch=FetchType.EAGER, cascade= CascadeType.REMOVE)
	private List<TblOrganismoGubernamental> lstHijosOrgGub;
	
//	//bi-directional many-to-one association to TblPlan
//	@OneToMany(mappedBy="tblOrganismoGubernamental",fetch=FetchType.EAGER, cascade= CascadeType.REMOVE)
//	private List<TblPlan> tblPlanes;

	public TblOrganismoGubernamental() {
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

	public Integer getIdTipoOrganismo() {
		return this.idTipoOrganismo;
	}

	public void setIdTipoOrganismo(Integer idTipoOrganismo) {
		this.idTipoOrganismo = idTipoOrganismo;
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


	/**
	 * @return the orgGubPadre
	 */
	public TblOrganismoGubernamental getOrgGubPadre() {
		return orgGubPadre;
	}

	/**
	 * @param orgGubPadre the orgGubPadre to set
	 */
	public void setOrgGubPadre(TblOrganismoGubernamental orgGubPadre) {
		this.orgGubPadre = orgGubPadre;
	}

	/**
	 * @return the lstHijosOrgGub
	 */
	public List<TblOrganismoGubernamental> getLstHijosOrgGub() {
		return lstHijosOrgGub;
	}

	/**
	 * @param lstHijosOrgGub the lstHijosOrgGub to set
	 */
	public void setLstHijosOrgGub(List<TblOrganismoGubernamental> lstHijosOrgGub) {
		this.lstHijosOrgGub = lstHijosOrgGub;
	}

}