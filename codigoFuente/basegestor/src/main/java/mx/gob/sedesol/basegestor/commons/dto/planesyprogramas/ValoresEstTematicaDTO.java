package mx.gob.sedesol.basegestor.commons.dto.planesyprogramas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;

public class ValoresEstTematicaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombreTema;
	private Integer unidadTematica;
	private List<String> unidadesTematicas;
        private BitacoraDTO bitacoraDTO;
	
	/**
	 * @return the nombreTema
	 */
	public String getNombreTema() {
		if(this.nombreTema == null)
			this.nombreTema = new String();
		return this.nombreTema;
	}
	/**
	 * @param nombreTema the nombreTema to set
	 */
	public void setNombreTema(String nombreTema) {
		this.nombreTema = nombreTema;
	}
	/**
	 * @return the unidadTematica
	 */
	public Integer getUnidadTematica() {
		if(this.unidadTematica == null)
			this.unidadTematica = new Integer(0);
		return this.unidadTematica;
	}
	/**
	 * @param unidadTematica the unidadTematica to set
	 */
	public void setUnidadTematica(Integer unidadTematica) {
		this.unidadTematica = unidadTematica;
	}
	/**
	 * @return the unidadesTematicas
	 */
	public List<String> getUnidadesTematicas() {
		if(this.unidadesTematicas == null)
			this.unidadesTematicas = new ArrayList<String>();
		return unidadesTematicas;
	}
	/**
	 * @param unidadesTematicas the unidadesTematicas to set
	 */
	public void setUnidadesTematicas(List<String> unidadesTematicas) {
		this.unidadesTematicas = unidadesTematicas;
	}

    /**
     * @return the bitacoraDTO
     */
    public BitacoraDTO getBitacoraDTO() {
        return bitacoraDTO;
    }

    /**
     * @param bitacoraDTO the bitacoraDTO to set
     */
    public void setBitacoraDTO(BitacoraDTO bitacoraDTO) {
        this.bitacoraDTO = bitacoraDTO;
    }
}
