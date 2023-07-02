package mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje;

import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;

public class ElementosMultimediaFoaDTO {
	
	private Integer idFoa;
	private Integer idElementoMultimediaFoa;
	private Date fechaRegistro;
	private Date fechaActualizacion;
	private Long usuarioModifico;
	private FichaDescriptivaOaDTO fichaDescriptivaObjetoAprendizaje;
	private CatalogoComunDTO catElementosMultimedia;
        
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Long getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	/**
	 * @return the idFoa
	 */
	public Integer getIdFoa() {
		return idFoa;
	}

	/**
	 * @param idFoa the idFoa to set
	 */
	public void setIdFoa(Integer idFoa) {
		this.idFoa = idFoa;
	}

	/**
	 * @return the idElementoMultimediaFoa
	 */
	public Integer getIdElementoMultimediaFoa() {
		return idElementoMultimediaFoa;
	}

	/**
	 * @param idElementoMultimediaFoa the idElementoMultimediaFoa to set
	 */
	public void setIdElementoMultimediaFoa(Integer idElementoMultimediaFoa) {
		this.idElementoMultimediaFoa = idElementoMultimediaFoa;
	}

	/**
	 * @return the fichaDescriptivaObjetoAprendizaje
	 */
	public FichaDescriptivaOaDTO getFichaDescriptivaObjetoAprendizaje() {
		return fichaDescriptivaObjetoAprendizaje;
	}

	/**
	 * @param fichaDescriptivaObjetoAprendizaje the fichaDescriptivaObjetoAprendizaje to set
	 */
	public void setFichaDescriptivaObjetoAprendizaje(FichaDescriptivaOaDTO fichaDescriptivaObjetoAprendizaje) {
		this.fichaDescriptivaObjetoAprendizaje = fichaDescriptivaObjetoAprendizaje;
	}

	/**
	 * @return the catElementosMultimedia
	 */
	public CatalogoComunDTO getCatElementosMultimedia() {
		return catElementosMultimedia;
	}

	/**
	 * @param catElementosMultimedia the catElementosMultimedia to set
	 */
	public void setCatElementosMultimedia(CatalogoComunDTO catElementosMultimedia) {
		this.catElementosMultimedia = catElementosMultimedia;
	}
}
