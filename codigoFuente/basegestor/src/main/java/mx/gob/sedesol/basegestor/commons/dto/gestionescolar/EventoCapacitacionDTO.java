package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.RelEncuestaEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.encuestas.RelEncuestaEventoCapacitacion;

public class EventoCapacitacionDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Integer DIAS_EXTRA_FECHA_LLENADO = 4;

	private Integer idEvento;
	private Integer idPrograma;
	private String alcanceEc;
	private Boolean constancia;
	private Integer tipoConstancia;
	private Integer procentajeMinAsistencia;

	@NotNull(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private Date fechaInicial;

	@NotNull(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private Date fechaFinal;

	private Date fechaActualizacion;
	private Date fechaRegistro;
	private String cveEventoCap;
	private String noRegistroEc;

	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String nombreEc;

	private String objetivoGeneralEc;

	private String perfilEc;

	private String requisitosEc;

	private Long usuarioModifico;
	private CatalogoComunDTO catEstadoEventoCapacitacion;
	private CatalogoComunDTO idDestinatario;
	private FichaDescProgramaDTO fichaDescriptivaPrograma;
	private String fechaFinalFormat;
	private String fechaInicialFormat;
	private List<ReponsableProduccionEcDTO> reponsableProduccionEcs;
	private CatalogoComunDTO catModalidadPlanPrograma;
	private Integer tipoBusqueda;

	private CatalogoComunDTO categoriaEC;

	private Integer idClasificacionAva;
	private Integer idEventoCapacitacionAnterior;
	private Integer idCursoLmsBorrador;

	private String calificacionMinAprobatoria;
	private int oportunidadesEvaluacion;

	private Integer idDirigido;
	
	private Integer idProgramaSocial;
	private Integer idEntidadFederativa;
	private Integer idMunicipio;
	private Integer idPlataformaLmsBorrador;

	private Date fechaLimiteCierre;
	private Date fechaLimiteLLenado;

	
	private ReponsableProduccionEcDTO  responsableCoordinadorAcademico;
	private ReponsableProduccionEcDTO  responsableProduccion;

	private Integer tpoCalificacion;
	private Integer tpoEscalaCalificacion;
	private Integer tpoDictamen;

	private String urlImagen;

	private Boolean aplicaEncuesta;
	private String versionEncuesta;

	private Integer valorAsistencia;
	private Integer valorFalta;
	private Integer valorFaltaJustificada;
	private Integer valorInconcluso;
	private Integer valorRetardo;

	private Integer valorAsistenciaDictamen;
	private Integer valorCalificacionDictamen;

	private Boolean privado;
	private Integer numEstrellas;
	private String rutaRelativa;
	
	private List<RelEncuestaEventoCapacitacionDTO> relEncuestaEvento;

	public EventoCapacitacionDTO() {
		this.fechaRegistro = new Date();
		this.fechaActualizacion = this.fechaRegistro;
		this.fichaDescriptivaPrograma = new FichaDescProgramaDTO();
		this.categoriaEC = new CatalogoComunDTO();
		this.catModalidadPlanPrograma = new CatalogoComunDTO();
		this.catEstadoEventoCapacitacion = new CatalogoComunDTO();
		this.idDestinatario = new CatalogoComunDTO();
		this.valorAsistencia = ConstantesGestor.VALOR_INICIAL_ASISTENCIA;
		this.valorRetardo = ConstantesGestor.VALOR_INICIAL_RETARDO;
		this.valorFaltaJustificada = ConstantesGestor.VALOR_INICIAL_FALTA_JUSTIFICADA;
		this.valorFalta = ConstantesGestor.VALOR_INICIAL_FALTA;
		this.valorInconcluso = ConstantesGestor.VALOR_INICIAL_INCONCLUSO;
		this.valorAsistenciaDictamen = ConstantesGestor.VALOR_INICIAL_DICTAMEN_ASISTENCIA;
		this.aplicaEncuesta = false;
		
		this.procentajeMinAsistencia = ConstantesGestor.VALOR_INICIAL_PORCENTAJE_MIN_ASISTENCIA;
		this.numEstrellas = ((int)(Math.random() * 5)) + 1;
	}
	
	public int[] getEstrellas(){
		return new int[numEstrellas];
		
	}

	public String getAlcanceEc() {
		return this.alcanceEc;
	}

	public void setAlcanceEc(String alcanceEc) {
		this.alcanceEc = alcanceEc;
	}

	public Boolean getConstancia() {
		return constancia;
	}

	public void setConstancia(Boolean constancia) {
		this.constancia = constancia;
	}

	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNoRegistroEc() {
		return this.noRegistroEc;
	}

	public void setNoRegistroEc(String noRegistroEc) {
		this.noRegistroEc = noRegistroEc;
	}

	public String getNombreEc() {
		return this.nombreEc;
	}

	public void setNombreEc(String nombreEc) {
		this.nombreEc = nombreEc;
	}

	public String getObjetivoGeneralEc() {
		return this.objetivoGeneralEc;
	}

	public void setObjetivoGeneralEc(String objetivoGeneralEc) {
		this.objetivoGeneralEc = objetivoGeneralEc;
	}

	public String getPerfilEc() {
		return this.perfilEc;
	}

	public void setPerfilEc(String perfilEc) {
		this.perfilEc = perfilEc;
	}

	public String getRequisitosEc() {
		return this.requisitosEc;
	}

	public void setRequisitosEc(String requisitosEc) {
		this.requisitosEc = requisitosEc;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public Integer getIdEvento() {
		return this.idEvento;
	}

	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	public Integer getIdPrograma() {
		return this.idPrograma;
	}

	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}

	public CatalogoComunDTO getCatEstadoEventoCapacitacion() {
		return catEstadoEventoCapacitacion;
	}

	public void setCatEstadoEventoCapacitacion(CatalogoComunDTO catEstadoEventoCapacitacion) {
		this.catEstadoEventoCapacitacion = catEstadoEventoCapacitacion;
	}

	public FichaDescProgramaDTO getFichaDescriptivaPrograma() {
		return fichaDescriptivaPrograma;
	}

	public void setFichaDescriptivaPrograma(FichaDescProgramaDTO fichaDescriptivaPrograma) {
		this.fichaDescriptivaPrograma = fichaDescriptivaPrograma;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public String getFechaFinalFormat() {
		return fechaFinalFormat;
	}

	public void setFechaFinalFormat(String fechaFinalFormat) {
		this.fechaFinalFormat = fechaFinalFormat;
	}

	public String getFechaInicialFormat() {
		return fechaInicialFormat;
	}

	public void setFechaInicialFormat(String fechaInicialFormat) {
		this.fechaInicialFormat = fechaInicialFormat;
	}

	public List<ReponsableProduccionEcDTO> getReponsableProduccionEcs() {
		return reponsableProduccionEcs;
	}

	public void setReponsableProduccionEcs(List<ReponsableProduccionEcDTO> reponsableProduccionEcs) {
		this.reponsableProduccionEcs = reponsableProduccionEcs;
	}

	public CatalogoComunDTO getCatModalidadPlanPrograma() {
		return catModalidadPlanPrograma;
	}

	public void setCatModalidadPlanPrograma(CatalogoComunDTO catModalidadPlanPrograma) {
		this.catModalidadPlanPrograma = catModalidadPlanPrograma;
	}

	public String getCveEventoCap() {
		return cveEventoCap;
	}

	public void setCveEventoCap(String cveEventoCap) {
		this.cveEventoCap = cveEventoCap;
	}

	public Integer getTipoBusqueda() {
		return tipoBusqueda;
	}

	public void setTipoBusqueda(Integer tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	public CatalogoComunDTO getCategoriaEC() {
		return categoriaEC;
	}

	public void setCategoriaEC(CatalogoComunDTO categoriaEC) {
		this.categoriaEC = categoriaEC;
	}

	public String getCalificacionMinAprobatoria() {
		return calificacionMinAprobatoria;
	}

	public void setCalificacionMinAprobatoria(String calificacionMinAprobatoria) {
		this.calificacionMinAprobatoria = calificacionMinAprobatoria;
	}

	public int getOportunidadesEvaluacion() {
		return oportunidadesEvaluacion;
	}

	public void setOportunidadesEvaluacion(int oportunidadesEvaluacion) {
		this.oportunidadesEvaluacion = oportunidadesEvaluacion;
	}

	public Integer getIdDirigido() {
		return idDirigido;
	}

	public void setIdDirigido(Integer idDirigido) {
		this.idDirigido = idDirigido;
	}

	public Integer getIdProgramaSocial() {
		return idProgramaSocial;
	}

	public void setIdProgramaSocial(Integer idProgramaSocial) {
		this.idProgramaSocial = idProgramaSocial;
	}

	public Integer getIdEntidadFederativa() {
		return idEntidadFederativa;
	}

	public void setIdEntidadFederativa(Integer idEntidadFederativa) {
		this.idEntidadFederativa = idEntidadFederativa;
	}

	public Integer getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public Integer getIdClasificacionAva() {
		return idClasificacionAva;
	}

	public Integer getIdEventoCapacitacionAnterior() {
		return idEventoCapacitacionAnterior;
	}

	public Integer getIdCursoLmsBorrador() {
		return idCursoLmsBorrador;
	}

	public void setIdClasificacionAva(Integer idClasificacionAva) {
		this.idClasificacionAva = idClasificacionAva;
	}

	public void setIdEventoCapacitacionAnterior(Integer idEventoCapacitacionAnterior) {
		this.idEventoCapacitacionAnterior = idEventoCapacitacionAnterior;
	}

	public void setIdCursoLmsBorrador(Integer idCursoLmsBorrador) {
		this.idCursoLmsBorrador = idCursoLmsBorrador;
	}

	public Integer getIdPlataformaLmsBorrador() {
		return idPlataformaLmsBorrador;
	}

	public void setIdPlataformaLmsBorrador(Integer idPlataformaLmsBorrador) {
		this.idPlataformaLmsBorrador = idPlataformaLmsBorrador;
	}

	public ReponsableProduccionEcDTO getResponsableCoordinadorAcademico() {
		return responsableCoordinadorAcademico;
	}

	public void setResponsableCoordinadorAcademico(ReponsableProduccionEcDTO responsableCoordinadorAcademico) {
		this.responsableCoordinadorAcademico = responsableCoordinadorAcademico;
	}

	/**
	 * @return the fechaLimiteCierre
	 */
	public Date getFechaLimiteCierre() {
		return fechaLimiteCierre;
	}

	/**
	 * @param fechaLimiteCierre
	 *            the fechaLimiteCierre to set
	 */
	public void setFechaLimiteCierre(Date fechaLimiteCierre) {
		this.fechaLimiteCierre = fechaLimiteCierre;
	}

	/**
	 * @return the fechaLimiteLLenado
	 */
	public Date getFechaLimiteLLenado() {

		if (ObjectUtils.isNotNull(this.fechaLimiteCierre)) {
			fechaLimiteLLenado = DateUtils.agregaDiasHabilesAFecha(this.fechaLimiteCierre, DIAS_EXTRA_FECHA_LLENADO);
		}

		return fechaLimiteLLenado;
	}

	/**
	 * @param fechaLimiteLLenado
	 *            the fechaLimiteLLenado to set
	 */
	public void setFechaLimiteLLenado(Date fechaLimiteLLenado) {
		this.fechaLimiteLLenado = fechaLimiteLLenado;
	}

	/**
	 * @return the tpoCalificacion
	 */
	public Integer getTpoCalificacion() {
		return tpoCalificacion;
	}

	/**
	 * @param tpoCalificacion
	 *            the tpoCalificacion to set
	 */
	public void setTpoCalificacion(Integer tpoCalificacion) {
		this.tpoCalificacion = tpoCalificacion;
	}

	/**
	 * @return the tpoEscalaCalificacion
	 */
	public Integer getTpoEscalaCalificacion() {
		return tpoEscalaCalificacion;
	}

	/**
	 * @param tpoEscalaCalificacion
	 *            the tpoEscalaCalificacion to set
	 */
	public void setTpoEscalaCalificacion(Integer tpoEscalaCalificacion) {
		this.tpoEscalaCalificacion = tpoEscalaCalificacion;
	}

	public String getUrlImagen() {
		return urlImagen;
	}

	public Boolean getAplicaEncuesta() {
		return aplicaEncuesta;
	}

	public Integer getValorAsistencia() {
		return valorAsistencia;
	}

	public Integer getValorFalta() {
		return valorFalta;
	}

	public Integer getValorFaltaJustificada() {
		return valorFaltaJustificada;
	}

	public Integer getValorInconcluso() {
		return valorInconcluso;
	}

	public Integer getValorRetardo() {
		return valorRetardo;
	}

	public Integer getValorAsistenciaDictamen() {
		return valorAsistenciaDictamen;
	}

	public Integer getValorCalificacionDictamen() {
		return valorCalificacionDictamen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public void setAplicaEncuesta(Boolean aplicaEncuesta) {
		this.aplicaEncuesta = aplicaEncuesta;
	}

	public void setValorAsistencia(Integer valorAsistencia) {
		this.valorAsistencia = valorAsistencia;
	}

	public void setValorFalta(Integer valorFalta) {
		this.valorFalta = valorFalta;
	}

	public void setValorFaltaJustificada(Integer valorFaltaJustificada) {
		this.valorFaltaJustificada = valorFaltaJustificada;
	}

	public void setValorInconcluso(Integer valorInconcluso) {
		this.valorInconcluso = valorInconcluso;
	}

	public void setValorRetardo(Integer valorRetardo) {
		this.valorRetardo = valorRetardo;
	}

	public void setValorAsistenciaDictamen(Integer valorAsistenciaDictamen) {
		this.valorAsistenciaDictamen = valorAsistenciaDictamen;
	}

	public void setValorCalificacionDictamen(Integer valorCalificacionDictamen) {
		this.valorCalificacionDictamen = valorCalificacionDictamen;
	}

	/**
	 * @return the tpoDictamen
	 */
	public Integer getTpoDictamen() {
		return tpoDictamen;
	}

	/**
	 * @param tpoDictamen
	 *            the tpoDictamen to set
	 */
	public void setTpoDictamen(Integer tpoDictamen) {
		this.tpoDictamen = tpoDictamen;
	}


	public String getVersionEncuesta() {
		return versionEncuesta;
	}

	public void setVersionEncuesta(String versionEncuesta) {
		this.versionEncuesta = versionEncuesta;
	}

	public Integer getTipoConstancia() {
		return tipoConstancia;
	}

	public void setTipoConstancia(Integer tipoConstancia) {
		this.tipoConstancia = tipoConstancia;
	}

	public Boolean getPrivado() {
		return privado;
	}

	public void setPrivado(Boolean privado) {
		this.privado = privado;
	}



	public ReponsableProduccionEcDTO getResponsableProduccion() {
		return responsableProduccion;
	}

	public void setResponsableProduccion(ReponsableProduccionEcDTO responsableProduccion) {
		this.responsableProduccion = responsableProduccion;
	}

	


	public Integer getNumEstrellas() {
		return numEstrellas;
	}

	public void setNumEstrellas(Integer numEstrellas) {
		this.numEstrellas = numEstrellas;
	}

	public String getRutaRelativa() {
		return rutaRelativa;
	}

	public void setRutaRelativa(String rutaRelativa) {
		this.rutaRelativa = rutaRelativa;
	}
	
	public Integer obtenerDuracion(){
		return DateUtils.restaFechasEnDiasMes(fechaInicial, fechaFinal);
	}

	public boolean hayCoincidencia(String filtro) {
		filtro = filtro.toLowerCase();
		return (this.nombreEc != null && this.nombreEc.toLowerCase().contains(filtro))
				|| (this.objetivoGeneralEc != null && this.objetivoGeneralEc.toLowerCase().contains(filtro))
				|| (this.fichaDescriptivaPrograma.getCatNivelConocimiento().getNombre() != null
						&& this.fichaDescriptivaPrograma.getCatNivelConocimiento().getNombre().toLowerCase()
								.contains(filtro));
	}

	/**
	 * @return the idDestinatario
	 */
	public CatalogoComunDTO getIdDestinatario() {
		return idDestinatario;
	}

	/**
	 * @param idDestinatario the idDestinatario to set
	 */
	public void setIdDestinatario(CatalogoComunDTO idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

	public Integer getProcentajeMinAsistencia() {
		return procentajeMinAsistencia;
	}

	public void setProcentajeMinAsistencia(Integer procentajeMinAsistencia) {
		this.procentajeMinAsistencia = procentajeMinAsistencia;
	}

	public List<RelEncuestaEventoCapacitacionDTO> getRelEncuestaEvento() {
		return relEncuestaEvento;
	}

	public void setRelEncuestaEvento(List<RelEncuestaEventoCapacitacionDTO> relEncuestaEvento) {
		this.relEncuestaEvento = relEncuestaEvento;
	}

	@Override
	public String toString() {
		return "EventoCapacitacionDTO [idEvento=" + idEvento + ", idPrograma=" + idPrograma + ", cveEventoCap="
				+ cveEventoCap + ", nombreEc=" + nombreEc + "]";
	}

	
}
