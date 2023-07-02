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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the tbl_estructura_tematica database table.
 * 
 */
@Entity
@Table(name="tbl_estructura_tematica")
@NamedQuery(name="TblEstructuraTematica.findAll", query="SELECT t FROM TblEstructuraTematica t")
public class TblEstructuraTematica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_estructura_tematica")
	private Integer idEstructuraTematica;

	@Column(name="activo")
	private Integer activo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;
	
	@Column(name="num_unidades_tematicas")
	private Integer numUnidadesTematicas;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_programa")
	private TblFichaDescriptivaPrograma tblFichaDescriptivaPrograma;
	
	//bi-directional many-to-one association to DetEtematicaTema
	@OneToMany(mappedBy="tblEstructuraTematica",cascade= CascadeType.ALL)
	private List<DetEtematicaTema> detEtematicaTemas;


	@Column(name="usuario_modifico")
	private Long usuarioModifico;
	
	//bi-directional many-to-one association to RelEtematicaModuloMdl
	@OneToMany(mappedBy="tblEstructuraTematica",cascade= CascadeType.ALL)
	private List<RelEtematicaModuloMdl> relEtematicaModulosMdl;
	
	
	
	
	public TblEstructuraTematica() {
	}

	public Integer getIdEstructuraTematica() {
		return this.idEstructuraTematica;
	}

	public void setIdEstructuraTematica(Integer idEstructuraTematica) {
		this.idEstructuraTematica = idEstructuraTematica;
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


	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}


	/**
	 * @return the relEtematicaModulosMdl
	 */
	public List<RelEtematicaModuloMdl> getRelEtematicaModulosMdl() {
		return relEtematicaModulosMdl;
	}

	/**
	 * @param relEtematicaModulosMdl the relEtematicaModulosMdl to set
	 */
	public void setRelEtematicaModulosMdl(List<RelEtematicaModuloMdl> relEtematicaModulosMdl) {
		this.relEtematicaModulosMdl = relEtematicaModulosMdl;
	}

	/**
	 * @return the numUnidadesTematicas
	 */
	public Integer getNumUnidadesTematicas() {
		return numUnidadesTematicas;
	}

	/**
	 * @param numUnidadesTematicas the numUnidadesTematicas to set
	 */
	public void setNumUnidadesTematicas(Integer numUnidadesTematicas) {
		this.numUnidadesTematicas = numUnidadesTematicas;
	}

	/**
	 * @return the tblFichaDescriptivaPrograma
	 */
	public TblFichaDescriptivaPrograma getTblFichaDescriptivaPrograma() {
		return tblFichaDescriptivaPrograma;
	}

	/**
	 * @param tblFichaDescriptivaPrograma the tblFichaDescriptivaPrograma to set
	 */
	public void setTblFichaDescriptivaPrograma(TblFichaDescriptivaPrograma tblFichaDescriptivaPrograma) {
		this.tblFichaDescriptivaPrograma = tblFichaDescriptivaPrograma;
	}

	/**
	 * @return the detEtematicaTemas
	 */
	public List<DetEtematicaTema> getDetEtematicaTemas() {
		return detEtematicaTemas;
	}

	/**
	 * @param detEtematicaTemas the detEtematicaTemas to set
	 */
	public void setDetEtematicaTemas(List<DetEtematicaTema> detEtematicaTemas) {
		this.detEtematicaTemas = detEtematicaTemas;
	}
}