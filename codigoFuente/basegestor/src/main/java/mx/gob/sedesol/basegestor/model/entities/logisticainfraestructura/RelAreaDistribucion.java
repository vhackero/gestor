package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the rel_area_distribucion database table.
 * 
 */
@Entity
@Table(name="rel_area_distribucion")
@IdClass(RelAreaDistribucionPK.class)
@NamedQuery(name="RelAreaDistribucion.findAll", query="SELECT r FROM RelAreaDistribucion r")
public class RelAreaDistribucion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_area_config")
	private Integer idAreaConfig;

	@Id
	@Column(name="id_distribucion")
	private Integer idDistribucion;

	private Integer activo;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="usuario_registro")
	private Long usuarioRegistro;

	//bi-directional many-to-one association to TblConfiguracionArea
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_area_config", insertable=false, updatable=false)
	private TblConfiguracionArea tblConfiguracionArea;

	//bi-directional many-to-one association to CatDistribucionArea
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_distribucion", insertable=false, updatable=false)
	private CatDistribucionArea catDistribucionArea;

	public RelAreaDistribucion() {
	}

	public Integer getActivo() {
		return this.activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public Integer getIdAreaConfig() {
		return idAreaConfig;
	}

	public void setIdAreaConfig(Integer idAreaConfig) {
		this.idAreaConfig = idAreaConfig;
	}

	public Integer getIdDistribucion() {
		return idDistribucion;
	}

	public void setIdDistribucion(Integer idDistribucion) {
		this.idDistribucion = idDistribucion;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Long getUsuarioRegistro() {
		return this.usuarioRegistro;
	}

	public void setUsuarioRegistro(Long usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	public TblConfiguracionArea getTblConfiguracionArea() {
		return this.tblConfiguracionArea;
	}

	public void setTblConfiguracionArea(TblConfiguracionArea tblConfiguracionArea) {
		this.tblConfiguracionArea = tblConfiguracionArea;
	}

	public CatDistribucionArea getCatDistribucionArea() {
		return this.catDistribucionArea;
	}

	public void setCatDistribucionArea(CatDistribucionArea catDistribucionArea) {
		this.catDistribucionArea = catDistribucionArea;
	}

}