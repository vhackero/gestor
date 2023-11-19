package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "rel_estructura_unidad_didactica")
@IdClass(RelEstructuraUnidadDidacticaPK.class)
public class RelEstructuraUnidadDidactica implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public RelEstructuraUnidadDidactica(){}
	
	@Id
	@Column(name="id_det_tema")
	private Integer idDetTema;
	
	@Id
	@Column(name="id_unidad_didactica")
	private Integer idUnidadDidactica;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;
	
	@Column(name="usuario_modifico")
	private Long usuarioModifico;
	
	//bi-directional many-to-one association to DetEstUnidadDidactica
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_unidad_didactica",insertable=false, updatable=false)
	private DetEstUnidadDidactica detEstUnidadDidactica;

	//bi-directional many-to-one association to DetEtematicaTema
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_det_tema",insertable=false, updatable=false)
	private DetEtematicaTema detEtematicaTema;
		
	

	/**
	 * @return the idUnidadDidactica
	 */
	public Integer getIdUnidadDidactica() {
		return idUnidadDidactica;
	}

	/**
	 * @param idUnidadDidactica the idUnidadDidactica to set
	 */
	public void setIdUnidadDidactica(Integer idUnidadDidactica) {
		this.idUnidadDidactica = idUnidadDidactica;
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
	 * @return the idDetTema
	 */
	public Integer getIdDetTema() {
		return idDetTema;
	}

	/**
	 * @param idDetTema the idDetTema to set
	 */
	public void setIdDetTema(Integer idDetTema) {
		this.idDetTema = idDetTema;
	}

	/**
	 * @return the detEstUnidadDidactica
	 */
	public DetEstUnidadDidactica getDetEstUnidadDidactica() {
		return detEstUnidadDidactica;
	}

	/**
	 * @param detEstUnidadDidactica the detEstUnidadDidactica to set
	 */
	public void setDetEstUnidadDidactica(DetEstUnidadDidactica detEstUnidadDidactica) {
		this.detEstUnidadDidactica = detEstUnidadDidactica;
	}

	/**
	 * @return the detEtematicaTema
	 */
	public DetEtematicaTema getDetEtematicaTema() {
		return detEtematicaTema;
	}

	/**
	 * @param detEtematicaTema the detEtematicaTema to set
	 */
	public void setDetEtematicaTema(DetEtematicaTema detEtematicaTema) {
		this.detEtematicaTema = detEtematicaTema;
	}

	

}
