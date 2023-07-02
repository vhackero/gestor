package mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the rel_personalizacion_recursos database table.
 * 
 */
@Entity
@Table(name="rel_personalizacion_recursos")
@IdClass(RelPersonalizacionRecursoPK.class)
@NamedQuery(name="RelPersonalizacionRecurso.findAll", query="SELECT r FROM RelPersonalizacionRecurso r")
public class RelPersonalizacionRecurso implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_personalizacion_area")
	private Integer idPersonalizacionArea;
	
	@Id
	@Column(name="id_recurso")
	private Integer idRecurso;
	
	private boolean activo;

	private Integer cantidad;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="usuario_registro")
	private Long usuarioRegistro;

	//bi-directional many-to-one association to TblPersonalizacionArea
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_personalizacion_area",insertable=false, updatable=false)
	private TblPersonalizacionArea tblPersonalizacionArea;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_recurso", insertable=false, updatable=false)
	private CatRecursosInfraestructura recursoInfraestructura;

	public RelPersonalizacionRecurso() {
	}

	public boolean getActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public int getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(int cantidad) {
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

	public TblPersonalizacionArea getTblPersonalizacionArea() {
		return this.tblPersonalizacionArea;
	}

	public void setTblPersonalizacionArea(TblPersonalizacionArea tblPersonalizacionArea) {
		this.tblPersonalizacionArea = tblPersonalizacionArea;
	}

	/**
	 * @return the idPersonalizacionArea
	 */
	public Integer getIdPersonalizacionArea() {
		return idPersonalizacionArea;
	}

	/**
	 * @param idPersonalizacionArea the idPersonalizacionArea to set
	 */
	public void setIdPersonalizacionArea(Integer idPersonalizacionArea) {
		this.idPersonalizacionArea = idPersonalizacionArea;
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

	/**
	 * @return the recursoInfraestructura
	 */
	public CatRecursosInfraestructura getRecursoInfraestructura() {
		return recursoInfraestructura;
	}

	/**
	 * @param recursoInfraestructura the recursoInfraestructura to set
	 */
	public void setRecursoInfraestructura(CatRecursosInfraestructura recursoInfraestructura) {
		this.recursoInfraestructura = recursoInfraestructura;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

}