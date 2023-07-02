package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "tbl_ficha_descripcion_objeto_aprendizaje")
@NamedQuery(name = "TblFichaDescripcionObjetoAprendizaje.findAll", query = "SELECT t FROM TblFichaDescripcionObjetoAprendizaje t")
public class TblFichaDescripcionObjetoAprendizaje implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@MapsId
	//@Column(name = "id_foa")
	private Integer idFoa;

	// bi-directional many-to-one association to CatAmbitoAplicacionFoa
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_recurso_predominante")
	private CatRecursoPredominanteFoa catRecursoPredominanteFoa;

	// bi-directional many-to-one association to CatComposicionObjetoOa
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_composicion_objeto")
	private CatComposicionObjetoOa catComposicionObjetoOa;

	// bi-directional many-to-one association to CatDensidadSemanticaOa
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_densidad_semantica")
	private CatDensidadSemanticaOa catDensidadSemanticaOa;



	// bi-directional many-to-one association to CatNivelGranularidadOa
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_nivel_granularidad")
	private CatNivelGranularidadOa catNivelGranularidadOa;

	// bi-directional many-to-one association to CatIdiomaOa
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_idioma")
	private CatIdiomaOa catIdiomaOa;

	//bi-directional many-to-one association to CatEstatusFoa
	@ManyToOne
	@JoinColumn(name="id_estatus_foa")
	private CatEstatusFoa catEstatusFoa;

	// bi-directional many-to-one association to CatDificultadOa
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_nivel_dificultad")
	private CatDificultadOa catDificultadOa;

	// bi-directional many-to-one association to CatNivelInteractividadOa
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_nivel_interactividad")
	private CatNivelInteractividadOa catNivelInteractividadOa;

	// bi-directional many-to-one association to CatTipoContenidoOa
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_contenido")
	private CatTipoContenidoOa catTipoContenidoOa;

	// bi-directional many-to-one association to CatTipoEstructuraOa
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_estructura")
	private CatTipoEstructuraOa catTipoEstructuraOa;

	// bi-directional many-to-one association to CatTipoInteractividadOa
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_interactividad")
	private CatTipoInteractividadOa catTipoInteractividadOa;

	//bi-directional many-to-one association to CatFormatoFoa
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_formato_foa")
	private CatFormatoFoa catFormatoFoa;

	// bi-directional one-to-one association to
	// TblFichaDescriptivaObjetoAprendizaje
	
	
	@MapsId
	@OneToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_foa")
	private TblFichaDescriptivaObjetoAprendizaje fichaDescriptivaOa;

	public TblFichaDescriptivaObjetoAprendizaje getFichaDescriptivaOa() {
		return fichaDescriptivaOa;
	}

	public void setFichaDescriptivaOa(TblFichaDescriptivaObjetoAprendizaje fichaDescriptivaOa) {
		this.fichaDescriptivaOa = fichaDescriptivaOa;
	}
	
	public TblFichaDescripcionObjetoAprendizaje() {
	}

	public CatComposicionObjetoOa getCatComposicionObjetoOa() {
		return this.catComposicionObjetoOa;
	}

	public void setCatComposicionObjetoOa(CatComposicionObjetoOa catComposicionObjetoOa) {
		this.catComposicionObjetoOa = catComposicionObjetoOa;
	}

	public CatDensidadSemanticaOa getCatDensidadSemanticaOa() {
		return this.catDensidadSemanticaOa;
	}

	public void setCatDensidadSemanticaOa(CatDensidadSemanticaOa catDensidadSemanticaOa) {
		this.catDensidadSemanticaOa = catDensidadSemanticaOa;
	}

	
	public CatNivelGranularidadOa getCatNivelGranularidadOa() {
		return this.catNivelGranularidadOa;
	}

	public void setCatNivelGranularidadOa(CatNivelGranularidadOa catNivelGranularidadOa) {
		this.catNivelGranularidadOa = catNivelGranularidadOa;
	}

	public CatIdiomaOa getCatIdiomaOa() {
		return this.catIdiomaOa;
	}

	public void setCatIdiomaOa(CatIdiomaOa catIdiomaOa) {
		this.catIdiomaOa = catIdiomaOa;
	}

	

	
	public CatDificultadOa getCatDificultadOa() {
		return this.catDificultadOa;
	}

	public void setCatDificultadOa(CatDificultadOa catDificultadOa) {
		this.catDificultadOa = catDificultadOa;
	}

	public CatNivelInteractividadOa getCatNivelInteractividadOa() {
		return this.catNivelInteractividadOa;
	}

	public void setCatNivelInteractividadOa(CatNivelInteractividadOa catNivelInteractividadOa) {
		this.catNivelInteractividadOa = catNivelInteractividadOa;
	}

	public CatTipoContenidoOa getCatTipoContenidoOa() {
		return this.catTipoContenidoOa;
	}

	public void setCatTipoContenidoOa(CatTipoContenidoOa catTipoContenidoOa) {
		this.catTipoContenidoOa = catTipoContenidoOa;
	}

	public CatTipoEstructuraOa getCatTipoEstructuraOa() {
		return this.catTipoEstructuraOa;
	}

	public void setCatTipoEstructuraOa(CatTipoEstructuraOa catTipoEstructuraOa) {
		this.catTipoEstructuraOa = catTipoEstructuraOa;
	}

	public CatTipoInteractividadOa getCatTipoInteractividadOa() {
		return this.catTipoInteractividadOa;
	}

	public void setCatTipoInteractividadOa(CatTipoInteractividadOa catTipoInteractividadOa) {
		this.catTipoInteractividadOa = catTipoInteractividadOa;
	}

	public Integer getIdFoa() {
		return idFoa;
	}

	public void setIdFoa(Integer idFoa) {
		this.idFoa = idFoa;
	}

	public CatEstatusFoa getCatEstatusFoa() {
		return catEstatusFoa;
	}

	public void setCatEstatusFoa(CatEstatusFoa catEstatusFoa) {
		this.catEstatusFoa = catEstatusFoa;
	}

	/**
	 * @return the catFormatoFoa
	 */
	public CatFormatoFoa getCatFormatoFoa() {
		return catFormatoFoa;
	}

	/**
	 * @param catFormatoFoa the catFormatoFoa to set
	 */
	public void setCatFormatoFoa(CatFormatoFoa catFormatoFoa) {
		this.catFormatoFoa = catFormatoFoa;
	}

	/**
	 * @return the catRecursoPredominanteFoa
	 */
	public CatRecursoPredominanteFoa getCatRecursoPredominanteFoa() {
		return catRecursoPredominanteFoa;
	}

	/**
	 * @param catRecursoPredominanteFoa the catRecursoPredominanteFoa to set
	 */
	public void setCatRecursoPredominanteFoa(CatRecursoPredominanteFoa catRecursoPredominanteFoa) {
		this.catRecursoPredominanteFoa = catRecursoPredominanteFoa;
	}

}