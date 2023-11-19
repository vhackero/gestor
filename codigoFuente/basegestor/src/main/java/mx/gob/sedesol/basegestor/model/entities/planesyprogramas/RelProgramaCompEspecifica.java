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
@Table(name="rel_programa_comp_especificas")
@IdClass(RelProgramaCompEspecificaPK.class)
public class RelProgramaCompEspecifica implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id_programa")
	private Integer idPrograma;
	
	@Id
	@Column(name="id_comp_especifica")
	private Integer idCompEspecifica;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;
	
	@ManyToOne
	@JoinColumn(name="id_programa",insertable=false, updatable=false)
	private TblFichaDescriptivaPrograma fichaDescriptivaPrograma;
	
	@ManyToOne
	@JoinColumn(name = "id_comp_especifica", insertable = false, updatable = false)
	private CatCompetenciaEspecifica catCompetenciaEspecifica;
	
	public RelProgramaCompEspecifica(){
		
	}

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
	 * @return the fichaDescriptivaPrograma
	 */
	public TblFichaDescriptivaPrograma getFichaDescriptivaPrograma() {
		return fichaDescriptivaPrograma;
	}

	/**
	 * @param fichaDescriptivaPrograma the fichaDescriptivaPrograma to set
	 */
	public void setFichaDescriptivaPrograma(TblFichaDescriptivaPrograma fichaDescriptivaPrograma) {
		this.fichaDescriptivaPrograma = fichaDescriptivaPrograma;
	}

}
