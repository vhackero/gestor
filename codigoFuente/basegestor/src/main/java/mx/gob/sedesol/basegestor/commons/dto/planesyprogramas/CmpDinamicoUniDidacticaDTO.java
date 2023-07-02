package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.DetEstUniDidacticaDTO;

public class CmpDinamicoUniDidacticaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer identificador;
	private DetEstUniDidacticaDTO detUnidadDidactica;
	private String nombreUnidad;
	private List<String> materialDidApoyoSel;
	private List<String> tiposCompetenciaXEje;
	
	/**
	 * @return the detUnidadDidactica
	 */
	public DetEstUniDidacticaDTO getDetUnidadDidactica() {
		return detUnidadDidactica;
	}
	/**
	 * @param detUnidadDidactica the detUnidadDidactica to set
	 */
	public void setDetUnidadDidactica(DetEstUniDidacticaDTO detUnidadDidactica) {
		this.detUnidadDidactica = detUnidadDidactica;
	}
	/**
	 * @return the nombreUnidad
	 */
	public String getNombreUnidad() {
		return nombreUnidad;
	}
	/**
	 * @param nombreUnidad the nombreUnidad to set
	 */
	public void setNombreUnidad(String nombreUnidad) {
		this.nombreUnidad = nombreUnidad;
	}
	/**
	 * @return the materialDidApoyoSel
	 */
	public List<String> getMaterialDidApoyoSel() {
		return materialDidApoyoSel;
	}
	/**
	 * @param materialDidApoyoSel the materialDidApoyoSel to set
	 */
	public void setMaterialDidApoyoSel(List<String> materialDidApoyoSel) {
		this.materialDidApoyoSel = materialDidApoyoSel;
	}
	/**
	 * @return the identificador
	 */
	public Integer getIdentificador() {
		return identificador;
	}
	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}
	/**
	 * @return the tiposCompetenciaXEje
	 */
	public List<String> getTiposCompetenciaXEje() {
		return tiposCompetenciaXEje;
	}
	/**
	 * @param tiposCompetenciaXEje the tiposCompetenciaXEje to set
	 */
	public void setTiposCompetenciaXEje(List<String> tiposCompetenciaXEje) {
		this.tiposCompetenciaXEje = tiposCompetenciaXEje;
	}
}
