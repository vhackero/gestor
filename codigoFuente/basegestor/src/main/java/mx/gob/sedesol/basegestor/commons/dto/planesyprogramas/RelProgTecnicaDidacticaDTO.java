package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;


public class RelProgTecnicaDidacticaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private Integer idTecnicaDidactica;
	private Long usuarioModifico;
	private FichaDescProgramaDTO fichaDescriptivaPrograma;
	private Integer idPrograma;
	private CatalogoComunDTO catTecnicaDidacticaPrograma;
	
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Integer getIdTecnicaDidactica() {
		return idTecnicaDidactica;
	}
	public void setIdTecnicaDidactica(Integer idTecnicaDidactica) {
		this.idTecnicaDidactica = idTecnicaDidactica;
	}
	public Long getUsuarioModifico() {
		return usuarioModifico;
	}
	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}
	public FichaDescProgramaDTO getFichaDescriptivaPrograma() {
		return fichaDescriptivaPrograma;
	}
	
	public void setFichaDescriptivaPrograma(FichaDescProgramaDTO fichaDescriptivaPrograma) {
		this.fichaDescriptivaPrograma = fichaDescriptivaPrograma;
		this.setIdPrograma(this.fichaDescriptivaPrograma.getIdPrograma());
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
	 * @return the catTecnicaDidacticaPrograma
	 */
	public CatalogoComunDTO getCatTecnicaDidacticaPrograma() {
		return catTecnicaDidacticaPrograma;
	}
	/**
	 * @param catTecnicaDidacticaPrograma the catTecnicaDidacticaPrograma to set
	 */
	public void setCatTecnicaDidacticaPrograma(CatalogoComunDTO catTecnicaDidacticaPrograma) {
		this.catTecnicaDidacticaPrograma = catTecnicaDidacticaPrograma;
		this.setIdTecnicaDidactica(this.catTecnicaDidacticaPrograma.getId());
	}

}
