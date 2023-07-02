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
@Table(name="rel_udidactica_material_didactico")
@IdClass(RelUniDidacticaMaterialPK.class)
public class RelUniDidacticaMaterial implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RelUniDidacticaMaterial(){}
	
	@Id
	@Column(name="id_unidad_didactica")
	private Integer idUnidadDidactica;
	
	@Id
	@Column(name="id_material_didactico")
	private Integer idMaterialDidactico;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_unidad_didactica", insertable=false, updatable=false)
	private DetEstUnidadDidactica detEstUnidadDidactica;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_material_didactico", insertable=false, updatable=false)
	private CatMaterialDidactico catMaterialDidactico;

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
	 * @return the idMaterialDidactico
	 */
	public Integer getIdMaterialDidactico() {
		return idMaterialDidactico;
	}

	/**
	 * @param idMaterialDidactico the idMaterialDidactico to set
	 */
	public void setIdMaterialDidactico(Integer idMaterialDidactico) {
		this.idMaterialDidactico = idMaterialDidactico;
	}

	/**
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
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
	 * @return the catMaterialDidactico
	 */
	public CatMaterialDidactico getCatMaterialDidactico() {
		return catMaterialDidactico;
	}

	/**
	 * @param catMaterialDidactico the catMaterialDidactico to set
	 */
	public void setCatMaterialDidactico(CatMaterialDidactico catMaterialDidactico) {
		this.catMaterialDidactico = catMaterialDidactico;
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

}
