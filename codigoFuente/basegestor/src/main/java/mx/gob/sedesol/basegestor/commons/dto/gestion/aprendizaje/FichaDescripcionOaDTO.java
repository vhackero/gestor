package mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje;

import java.io.Serializable;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;

public class FichaDescripcionOaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idFoa;

	private CatalogoComunDTO catRecursoPredominanteFoa;

	private CatalogoComunDTO catComposicionObjetoOa;

	private CatalogoComunDTO catDensidadSemanticaOa;

	private CatalogoComunDTO catFormatoFoa;

	private CatalogoComunDTO catNivelGranularidadOa;

	private CatalogoComunDTO catIdiomaOa;

	private CatalogoComunDTO catRecPredFoa;

	private CatalogoComunDTO catEstatusFoa;

	private CatalogoComunDTO catDificultadOa;

	private CatalogoComunDTO catNivelInteractividadOa;

	private CatalogoComunDTO catTipoContenidoOa;

	private CatalogoComunDTO catTipoEstructuraOa;

	private CatalogoComunDTO catTipoInteractividadOa;

	public FichaDescripcionOaDTO() {
		catRecursoPredominanteFoa = new CatalogoComunDTO();

		catComposicionObjetoOa = new CatalogoComunDTO();

		catDensidadSemanticaOa = new CatalogoComunDTO();

		setCatFormatoFoa(new CatalogoComunDTO());

		catNivelGranularidadOa = new CatalogoComunDTO();

		catIdiomaOa = new CatalogoComunDTO();

		catRecPredFoa = new CatalogoComunDTO();

		catEstatusFoa = new CatalogoComunDTO();

		catDificultadOa = new CatalogoComunDTO();

		catNivelInteractividadOa = new CatalogoComunDTO();

		catTipoContenidoOa = new CatalogoComunDTO();

		catTipoEstructuraOa = new CatalogoComunDTO();

		catTipoInteractividadOa = new CatalogoComunDTO();
	}


	public CatalogoComunDTO getCatComposicionObjetoOa() {
		return catComposicionObjetoOa;
	}

	public void setCatComposicionObjetoOa(CatalogoComunDTO catComposicionObjetoOa) {
		this.catComposicionObjetoOa = catComposicionObjetoOa;
	}

	public CatalogoComunDTO getCatDensidadSemanticaOa() {
		return catDensidadSemanticaOa;
	}

	public void setCatDensidadSemanticaOa(CatalogoComunDTO catDensidadSemanticaOa) {
		this.catDensidadSemanticaOa = catDensidadSemanticaOa;
	}

	public CatalogoComunDTO getCatNivelGranularidadOa() {
		return catNivelGranularidadOa;
	}

	public void setCatNivelGranularidadOa(CatalogoComunDTO catNivelGranularidadOa) {
		this.catNivelGranularidadOa = catNivelGranularidadOa;
	}

	public CatalogoComunDTO getCatIdiomaOa() {
		return catIdiomaOa;
	}

	public void setCatIdiomaOa(CatalogoComunDTO catIdiomaOa) {
		this.catIdiomaOa = catIdiomaOa;
	}

	public CatalogoComunDTO getCatEstatusFoa() {
		return catEstatusFoa;
	}

	public void setCatEstatusFoa(CatalogoComunDTO catEstatusFoa) {
		this.catEstatusFoa = catEstatusFoa;
	}

	public CatalogoComunDTO getCatDificultadOa() {
		return catDificultadOa;
	}

	public void setCatDificultadOa(CatalogoComunDTO catDificultadOa) {
		this.catDificultadOa = catDificultadOa;
	}

	public CatalogoComunDTO getCatNivelInteractividadOa() {
		return catNivelInteractividadOa;
	}

	public void setCatNivelInteractividadOa(CatalogoComunDTO catNivelInteractividadOa) {
		this.catNivelInteractividadOa = catNivelInteractividadOa;
	}

	public CatalogoComunDTO getCatTipoContenidoOa() {
		return catTipoContenidoOa;
	}

	public void setCatTipoContenidoOa(CatalogoComunDTO catTipoContenidoOa) {
		this.catTipoContenidoOa = catTipoContenidoOa;
	}

	public CatalogoComunDTO getCatTipoEstructuraOa() {
		return catTipoEstructuraOa;
	}

	public void setCatTipoEstructuraOa(CatalogoComunDTO catTipoEstructuraOa) {
		this.catTipoEstructuraOa = catTipoEstructuraOa;
	}

	public CatalogoComunDTO getCatTipoInteractividadOa() {
		return catTipoInteractividadOa;
	}

	public void setCatTipoInteractividadOa(CatalogoComunDTO catTipoInteractividadOa) {
		this.catTipoInteractividadOa = catTipoInteractividadOa;
	}

	public Integer getIdFoa() {
		return idFoa;
	}

	public void setIdFoa(Integer idFoa) {
		this.idFoa = idFoa;
	}

	/**
	 * @return the catFormatoFoa
	 */
	public CatalogoComunDTO getCatFormatoFoa() {
		return catFormatoFoa;
	}

	/**
	 * @param catFormatoFoa
	 *            the catFormatoFoa to set
	 */
	public void setCatFormatoFoa(CatalogoComunDTO catFormatoFoa) {
		this.catFormatoFoa = catFormatoFoa;
	}

	public CatalogoComunDTO getCatRecPredFoa() {
		return catRecPredFoa;
	}

	public void setCatRecPredFoa(CatalogoComunDTO catRecPredFoa) {
		this.catRecPredFoa = catRecPredFoa;
	}

	/**
	 * @return the catRecursoPredominanteFoa
	 */
	public CatalogoComunDTO getCatRecursoPredominanteFoa() {
		return catRecursoPredominanteFoa;
	}

	/**
	 * @param catRecursoPredominanteFoa
	 *            the catRecursoPredominanteFoa to set
	 */
	public void setCatRecursoPredominanteFoa(CatalogoComunDTO catRecursoPredominanteFoa) {
		this.catRecursoPredominanteFoa = catRecursoPredominanteFoa;
	}

}
