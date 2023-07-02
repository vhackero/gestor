package mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;

public class FichaDescriptivaOaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idFoa;

	@Length(max = 100)
	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String anotacion;

	@Length(max = 500)
	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String condicionesUso;

	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String descripcionContenido;

	private Boolean estaDisponible;

	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String evaluacionDelAprendizaje;

	@Length(max = 500)
	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String contenidos;

	@Length(max = 500)
	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String descripcionObjetoRelacionado;

	private Date fechaActualizacion;

	private Date fechaRegistro;

	@NotNull(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private Date fechaLiberacion;

	@Length(max = 50)
	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String tiempoTipicoAprendizaje;

	@Length(max = 255)
	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String lugarEdicion;

	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String palabrasClave;

	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String objetivoGeneral;

	@Length(max = 100)
	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String nombreActividad;

	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String pesoArchivosScorm;

	@Length(max = 500)
	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String proposito;

	@Length(max = 500)
	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String requisitosConocimientosPrevios;

	@Length(max = 100)
	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String rangoTipicoDeEdad;

	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String resumen;

	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String temasRelacionados;

	@Length(max = 20)
	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String tiempoEstimadoRepLectura;

	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String tratamientoEditorial;

	private Boolean tieneCosto;

	private Boolean tieneDerechosAutor;

	@Length(max = 200)
	private String url;

	private Long usuarioModifico;

	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String version;

	private Boolean estatusDF;

	// @NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String comentarios;

	@Length(max = 500)
	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String responsableAcademico;

	@NotEmpty(message = MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String ambitoAplicacion;

	private FichaDescripcionOaDTO fichaDescripcionOa;

	public FichaDescriptivaOaDTO() {
		fichaDescripcionOa = new FichaDescripcionOaDTO();
	}

	public Integer getIdFoa() {
		return idFoa;
	}

	public void setIdFoa(Integer idFoa) {
		this.idFoa = idFoa;
	}

	public String getAnotacion() {
		return anotacion;
	}

	public void setAnotacion(String anotacion) {
		this.anotacion = anotacion;
	}

	public String getCondicionesUso() {
		return condicionesUso;
	}

	public void setCondicionesUso(String condicionesUso) {
		this.condicionesUso = condicionesUso;
	}

	public String getDescripcionContenido() {
		return descripcionContenido;
	}

	public void setDescripcionContenido(String descripcionContenido) {
		this.descripcionContenido = descripcionContenido;
	}

	public Boolean getEstaDisponible() {
		return estaDisponible;
	}

	public void setEstaDisponible(Boolean estaDisponible) {
		this.estaDisponible = estaDisponible;
	}

	public String getEvaluacionDelAprendizaje() {
		return evaluacionDelAprendizaje;
	}

	public void setEvaluacionDelAprendizaje(String evaluacionDelAprendizaje) {
		this.evaluacionDelAprendizaje = evaluacionDelAprendizaje;
	}

	public String getContenidos() {
		return contenidos;
	}

	public void setContenidos(String contenidos) {
		this.contenidos = contenidos;
	}

	public String getDescripcionObjetoRelacionado() {
		return descripcionObjetoRelacionado;
	}

	public void setDescripcionObjetoRelacionado(String descripcionObjetoRelacionado) {
		this.descripcionObjetoRelacionado = descripcionObjetoRelacionado;
	}

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

	public Date getFechaLiberacion() {
		return fechaLiberacion;
	}

	public void setFechaLiberacion(Date fechaLiberacion) {
		this.fechaLiberacion = fechaLiberacion;
	}

	public String getTiempoTipicoAprendizaje() {
		return tiempoTipicoAprendizaje;
	}

	public void setTiempoTipicoAprendizaje(String tiempoTipicoAprendizaje) {
		this.tiempoTipicoAprendizaje = tiempoTipicoAprendizaje;
	}

	public String getLugarEdicion() {
		return lugarEdicion;
	}

	public void setLugarEdicion(String lugarEdicion) {
		this.lugarEdicion = lugarEdicion;
	}

	public String getPalabrasClave() {
		return palabrasClave;
	}

	public void setPalabrasClave(String palabrasClave) {
		this.palabrasClave = palabrasClave;
	}

	public String getObjetivoGeneral() {
		return objetivoGeneral;
	}

	public void setObjetivoGeneral(String objetivoGeneral) {
		this.objetivoGeneral = objetivoGeneral;
	}

	public String getNombreActividad() {
		return nombreActividad;
	}

	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}

	public String getPesoArchivosScorm() {
		return pesoArchivosScorm;
	}

	public void setPesoArchivosScorm(String pesoArchivosScorm) {
		this.pesoArchivosScorm = pesoArchivosScorm;
	}

	public String getProposito() {
		return proposito;
	}

	public void setProposito(String proposito) {
		this.proposito = proposito;
	}

	public String getRequisitosConocimientosPrevios() {
		return requisitosConocimientosPrevios;
	}

	public void setRequisitosConocimientosPrevios(String requisitosConocimientosPrevios) {
		this.requisitosConocimientosPrevios = requisitosConocimientosPrevios;
	}

	public String getRangoTipicoDeEdad() {
		return rangoTipicoDeEdad;
	}

	public void setRangoTipicoDeEdad(String rangoTipicoDeEdad) {
		this.rangoTipicoDeEdad = rangoTipicoDeEdad;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public String getTemasRelacionados() {
		return temasRelacionados;
	}

	public void setTemasRelacionados(String temasRelacionados) {
		this.temasRelacionados = temasRelacionados;
	}

	public String getTiempoEstimadoRepLectura() {
		return tiempoEstimadoRepLectura;
	}

	public void setTiempoEstimadoRepLectura(String tiempoEstimadoRepLectura) {
		this.tiempoEstimadoRepLectura = tiempoEstimadoRepLectura;
	}

	public String getTratamientoEditorial() {
		return tratamientoEditorial;
	}

	public void setTratamientoEditorial(String tratamientoEditorial) {
		this.tratamientoEditorial = tratamientoEditorial;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the fichaDescripcionOa
	 */
	public FichaDescripcionOaDTO getFichaDescripcionOa() {
		return fichaDescripcionOa;
	}

	/**
	 * @param fichaDescripcionOa
	 *            the fichaDescripcionOa to set
	 */
	public void setFichaDescripcionOa(FichaDescripcionOaDTO fichaDescripcionOa) {
		this.fichaDescripcionOa = fichaDescripcionOa;
	}

	/**
	 * @return the tieneCosto
	 */
	public Boolean getTieneCosto() {
		return tieneCosto;
	}

	/**
	 * @param tieneCosto
	 *            the tieneCosto to set
	 */
	public void setTieneCosto(Boolean tieneCosto) {
		this.tieneCosto = tieneCosto;
	}

	/**
	 * @return the tieneDerechosAutor
	 */
	public Boolean getTieneDerechosAutor() {
		return tieneDerechosAutor;
	}

	/**
	 * @param tieneDerechosAutor
	 *            the tieneDerechosAutor to set
	 */
	public void setTieneDerechosAutor(Boolean tieneDerechosAutor) {
		this.tieneDerechosAutor = tieneDerechosAutor;
	}

	/**
	 * @return the estatusDF
	 */
	public Boolean getEstatusDF() {
		return estatusDF;
	}

	/**
	 * @param estatusDF
	 *            the estatusDF to set
	 */
	public void setEstatusDF(Boolean estatusDF) {
		this.estatusDF = estatusDF;
	}

	/**
	 * @return the comentarios
	 */
	public String getComentarios() {
		return comentarios;
	}

	/**
	 * @param comentarios
	 *            the comentarios to set
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	/**
	 * @return the responsableAcademico
	 */
	public String getResponsableAcademico() {
		return responsableAcademico;
	}

	/**
	 * @param responsableAcademico
	 *            the responsableAcademico to set
	 */
	public void setResponsableAcademico(String responsableAcademico) {
		this.responsableAcademico = responsableAcademico;
	}

	/**
	 * @return the ambitoAplicacion
	 */
	public String getAmbitoAplicacion() {
		return ambitoAplicacion;
	}

	/**
	 * @param ambitoAplicacion
	 *            the ambitoAplicacion to set
	 */
	public void setAmbitoAplicacion(String ambitoAplicacion) {
		this.ambitoAplicacion = ambitoAplicacion;
	}

}