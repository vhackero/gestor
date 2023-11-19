package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.io.Serializable;
import javax.persistence.*;


import java.util.Date;


/**
 * The persistent class for the cat_areas_sede database table.
 * 
 */
@Entity
@Table(name="cat_areas_sede")
@NamedQuery(name="CatAreasSede.findAll", query="SELECT c FROM CatAreasSede c")
public class CatAreasSede implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_area_sede")
	private Integer idAreaSede;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_area")
	private CatAreaInfraestructura catArea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;


	private Integer orden;

	private Integer piso;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to CatSede
	//@ManyToOne(fetch=FetchType.LAZY)
	//@OneToOne(fetch=FetchType.LAZY)
	@ManyToOne
	@JoinColumn(name="id_sede")
	private CatSede catSede;

	//bi-directional many-to-one association to CatEstatusArea
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_estatus_area")
	private CatEstatusArea catEstatusArea;

	public CatAreasSede() {
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


	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Integer getPiso() {
		return this.piso;
	}

	public void setPiso(Integer piso) {
		this.piso = piso;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public CatSede getCatSede() {
		return this.catSede;
	}

	public void setCatSede(CatSede catSede) {
		this.catSede = catSede;
	}

	public CatEstatusArea getCatEstatusArea() {
		return this.catEstatusArea;
	}

	public void setCatEstatusArea(CatEstatusArea catEstatusArea) {
		this.catEstatusArea = catEstatusArea;
	}

	/**
	 * @return the idAreaSede
	 */
	public Integer getIdAreaSede() {
		return idAreaSede;
	}

	/**
	 * @param idAreaSede the idAreaSede to set
	 */
	public void setIdAreaSede(Integer idAreaSede) {
		this.idAreaSede = idAreaSede;
	}

	/**
	 * @return the catArea
	 */
	public CatAreaInfraestructura getCatArea() {
		return catArea;
	}

	/**
	 * @param catArea the catArea to set
	 */
	public void setCatArea(CatAreaInfraestructura catArea) {
		this.catArea = catArea;
	}

}