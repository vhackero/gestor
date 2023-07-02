package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.List;



public class ControlEstTematicaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<ValoresEstTematicaDTO> temas;
	private List<ValoresEstTematicaDTO> unidadesDidacticas;
	private List<ValoresEstTematicaDTO> unidDidSelect;

	//private List<int[]> unidadesDidMS;
	
	
	/**
	 * @return the temas
	 */
	public List<ValoresEstTematicaDTO> getTemas() {
		return temas;
	}
	/**
	 * @param temas the temas to set
	 */
	public void setTemas(List<ValoresEstTematicaDTO> temas) {
		this.temas = temas;
	}
	/**
	 * @return the unidadesDidacticas
	 */
	public List<ValoresEstTematicaDTO> getUnidadesDidacticas() {
		return unidadesDidacticas;
	}
	/**
	 * @param unidadesDidacticas the unidadesDidacticas to set
	 */
	public void setUnidadesDidacticas(List<ValoresEstTematicaDTO> unidadesDidacticas) {
		this.unidadesDidacticas = unidadesDidacticas;
	}
	/**
	 * @return the unidDidSelect
	 */
	public List<ValoresEstTematicaDTO> getUnidDidSelect() {
		
		return this.unidDidSelect;
	}
	
//	public void addUnidadSeleccionada(List<Integer> item, Integer maxOptions ){
//		//if(ObjectUtils.isNull(this.unidDidSelect))
//		//	this.unidDidSelect = new ArrayList<List<Integer>>();
//
//		//Inicializa los objtos de la lista
//		for(int i = 1; i<= maxOptions; i++){
//			item.add(new Integer(0));
//		}
//		
//		this.unidDidSelect.add(item);
//		
//	}
	
	/**
	 * @param unidDidSelect the unidDidSelect to set
	 */
	public void setUnidDidSelect(List<ValoresEstTematicaDTO> unidDidSelect) {
		
		this.unidDidSelect = unidDidSelect;
	}
	
}
