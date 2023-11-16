package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.sedesol.basegestor.model.entities.admin.TblOrganismoGubernamental;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cat_sedes database table.
 * 
 */
@Entity
@Table(name="cat_sedes")
@NamedQuery(name="CatSede.findAll", query="SELECT c FROM CatSede c")
public class CatSede implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_sede")
	private Integer idSede;

	private boolean activo;

	@Column(name="codigo_postal")
	private String codigoPostal;

	private String colonia;

	private String direccion;

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

	//bi-directional many-to-one association to CatAreasSede
	@OneToMany(mappedBy="catSede", cascade={ CascadeType.ALL})
	private List<CatAreasSede> catAreasSedes;

	//bi-directional many-to-one association to CatUbicacionTerritorial
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_ubicacion")
	private CatUbicacionTerritorial catUbicacionTerritorial;
	
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_org_gub")
	private TblOrganismoGubernamental organismoGubernamental;

	public CatSede() {
	}

	public Integer getIdSede() {
		return this.idSede;
	}

	public void setIdSede(Integer idSede) {
		this.idSede = idSede;
	}

	public boolean getActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getCodigoPostal() {
		return this.codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getColonia() {
		return this.colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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

	public List<CatAreasSede> getCatAreasSedes() {
		return this.catAreasSedes;
	}

	public void setCatAreasSedes(List<CatAreasSede> catAreasSedes) {
		this.catAreasSedes = catAreasSedes;
	}

	public CatAreasSede addCatAreasSede(CatAreasSede catAreasSede) {
		getCatAreasSedes().add(catAreasSede);
		catAreasSede.setCatSede(this);

		return catAreasSede;
	}

	public CatAreasSede removeCatAreasSede(CatAreasSede catAreasSede) {
		getCatAreasSedes().remove(catAreasSede);
		catAreasSede.setCatSede(null);

		return catAreasSede;
	}

	public CatUbicacionTerritorial getCatUbicacionTerritorial() {
		return this.catUbicacionTerritorial;
	}

	public void setCatUbicacionTerritorial(CatUbicacionTerritorial catUbicacionTerritorial) {
		this.catUbicacionTerritorial = catUbicacionTerritorial;
	}

	/**
	 * @return the organismoGubernamental
	 */
	public TblOrganismoGubernamental getOrganismoGubernamental() {
		return organismoGubernamental;
	}

	/**
	 * @param organismoGubernamental the organismoGubernamental to set
	 */
	public void setOrganismoGubernamental(TblOrganismoGubernamental organismoGubernamental) {
		this.organismoGubernamental = organismoGubernamental;
	}

}