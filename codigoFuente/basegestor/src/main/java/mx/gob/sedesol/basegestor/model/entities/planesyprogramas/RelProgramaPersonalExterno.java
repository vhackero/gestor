package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="rel_programa_personal_externo")
@IdClass(RelProgramaPersonalExternoPK.class)
public class RelProgramaPersonalExterno implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public RelProgramaPersonalExterno(){}
	
	@Id
	@Column(name="id_programa")
	private Integer idPrograma;
	
	@Id
	@Column(name="id_estructura_personal_externo")
	private Integer idEstPersonalExt;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;
	
	@ManyToOne
	@JoinColumn(name="id_programa", insertable=false, updatable=false)
	private TblFichaDescriptivaPrograma tblFichaDescriptivaPrograma;
	
	@ManyToOne
	@JoinColumn(name = "id_estructura_personal_externo", insertable = false, updatable = false)
	private TblEstPersonalExterno estPersonalExterno;

	
	/**
	 * @return the idPrograma
	 */
	public Integer getIdPrograma() {
		return idPrograma;
	}

	/**
	 * @param idPrograma the idPrograma to set
	 */
	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}

	/**
	 * @return the idEstPersonalExt
	 */
	public Integer getIdEstPersonalExt() {
		return idEstPersonalExt;
	}

	/**
	 * @param idEstPersonalExt the idEstPersonalExt to set
	 */
	public void setIdEstPersonalExt(Integer idEstPersonalExt) {
		this.idEstPersonalExt = idEstPersonalExt;
	}

	/**
	 * @return the usuarioModifico
	 */
	public Long getUsuarioModifico() {
		return usuarioModifico;
	}

	/**
	 * @param usuarioModifico the usuarioModifico to set
	 */
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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
	 * @return the estPersonalExterno
	 */
	public TblEstPersonalExterno getEstPersonalExterno() {
		return estPersonalExterno;
	}

	/**
	 * @param estPersonalExterno the estPersonalExterno to set
	 */
	public void setEstPersonalExterno(TblEstPersonalExterno estPersonalExterno) {
		this.estPersonalExterno = estPersonalExterno;
	}
}
