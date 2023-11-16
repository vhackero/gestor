package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the rel_area_recursos database table.
 * 
 */
@Entity
@Table(name="rel_area_recursos")
@IdClass(RelAreaRecursoPK.class)
@NamedQuery(name="RelAreaRecurso.findAll", query="SELECT r FROM RelAreaRecurso r")
public class RelAreaRecurso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_area_config")
	private Integer idAreaConfig;
	@Id
	@Column(name="id_recurso")
	private Integer idRecurso;

	private Integer activo;

	private Integer cantidad;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	@Column(name="usuario_registro")
	private Long usuarioRegistro;

	//bi-directional many-to-one association to CatRecursosInfraestructura
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_recurso", insertable=false, updatable=false)
	private CatRecursosInfraestructura catRecursosInfraestructura;

	//bi-directional many-to-one association to TblConfiguracionArea
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_area_config", insertable=false, updatable=false)
	private TblConfiguracionArea tblConfiguracionArea;

	public RelAreaRecurso() {
	}

	public Integer getActivo() {
		return this.activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
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

	public CatRecursosInfraestructura getCatRecursosInfraestructura() {
		return this.catRecursosInfraestructura;
	}

	public void setCatRecursosInfraestructura(CatRecursosInfraestructura catRecursosInfraestructura) {
		this.catRecursosInfraestructura = catRecursosInfraestructura;
	}

	public TblConfiguracionArea getTblConfiguracionArea() {
		return this.tblConfiguracionArea;
	}

	public void setTblConfiguracionArea(TblConfiguracionArea tblConfiguracionArea) {
		this.tblConfiguracionArea = tblConfiguracionArea;
	}

	/**
	 * @return the idAreaConfig
	 */
	public Integer getIdAreaConfig() {
		return idAreaConfig;
	}

	/**
	 * @param idAreaConfig the idAreaConfig to set
	 */
	public void setIdAreaConfig(Integer idAreaConfig) {
		this.idAreaConfig = idAreaConfig;
	}

	/**
	 * @return the idRecurso
	 */
	public Integer getIdRecurso() {
		return idRecurso;
	}

	/**
	 * @param idRecurso the idRecurso to set
	 */
	public void setIdRecurso(Integer idRecurso) {
		this.idRecurso = idRecurso;
	}

}