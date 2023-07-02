package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;

public class RelProgCompEspecificaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer idPrograma;
	private Integer idCompEspecifica;
	private Long usuarioModifico;
	private Date fechaRegistro;
	private FichaDescProgramaDTO fichaDescProgramaDTO;
	private CompetenciaEspecificaDTO catCompetenciaEspecifica;
	
	public RelProgCompEspecificaDTO(){
		
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
	public CompetenciaEspecificaDTO getCatCompetenciaEspecifica() {
		return catCompetenciaEspecifica;
	}
	/**
	 * @param catCompetenciaEspecifica the catCompetenciaEspecifica to set
	 */
	public void setCatCompetenciaEspecifica(CompetenciaEspecificaDTO catCompetenciaEspecifica) {
		this.catCompetenciaEspecifica = catCompetenciaEspecifica;
		this.idCompEspecifica = this.catCompetenciaEspecifica.getId();
	}
	/**
	 * @return the fichaDescProgramaDTO
	 */
	public FichaDescProgramaDTO getFichaDescProgramaDTO() {
		return fichaDescProgramaDTO;
	}
	
	/**
	 * @param fichaDescProgramaDTO the fichaDescProgramaDTO to set
	 */
	public void setFichaDescProgramaDTO(FichaDescProgramaDTO fichaDescProgramaDTO) {
		this.fichaDescProgramaDTO = fichaDescProgramaDTO;
		this.idPrograma = this.fichaDescProgramaDTO.getIdPrograma();
	}
}
