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
@Table(name="rel_udidactica_tpos_competencia")
@IdClass(RelUnidDidacticaTposCompPK.class)
public class RelUnidDidacticaTposComp implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_unidad_didactica")
	private Integer idUnidadDidactica;
	
	@Id
	@Column(name="id_comp_especifica")
	private Integer idCompEspecifica;
	
	@Column(name="usuario_modifico")
	private Long usuarioModifico;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;
	
	@ManyToOne
	@JoinColumn(name="id_comp_especifica", insertable=false, updatable=false)
	private CatCompetenciaEspecifica catCompetenciaEspecifica;
	
	@ManyToOne
	@JoinColumn(name="id_unidad_didactica", insertable=false, updatable=false)
	private DetEstUnidadDidactica detEstUnidadDidactica;
	
	public RelUnidDidacticaTposComp() {
		super();
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
	 * @return the idCompEspecifica
	 */
	public Integer getIdCompEspecifica() {
		return idCompEspecifica;
	}

	/**
	 * @param idCompEspecifica the idCompEspecifica to set
	 */
	public void setIdCompEspecifica(Integer idCompEspecifica) {
		this.idCompEspecifica = idCompEspecifica;
	}

	/**
	 * @return the catCompetenciaEspecifica
	 */
	public CatCompetenciaEspecifica getCatCompetenciaEspecifica() {
		return catCompetenciaEspecifica;
	}

	/**
	 * @param catCompetenciaEspecifica the catCompetenciaEspecifica to set
	 */
	public void setCatCompetenciaEspecifica(CatCompetenciaEspecifica catCompetenciaEspecifica) {
		this.catCompetenciaEspecifica = catCompetenciaEspecifica;
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
