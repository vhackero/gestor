package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_ficha_descriptiva_objeto_aprendizaje")
@NamedQuery(name = "TblFichaDescriptivaObjetoAprendizaje.findAll", query = "SELECT t FROM TblFichaDescriptivaObjetoAprendizaje t")
public class TblFichaDescriptivaObjetoAprendizaje implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_foa")
	private Integer idFoa;	

	private String anotacion;

	@Column(name = "condiciones_uso")
	private String condicionesUso;

	@Column(name = "descripcion_contenido", columnDefinition="TEXT")
	private String descripcionContenido;

	@Column(name="esta_disponible")
	private Boolean estaDisponible;
	
	@Column(name="evaluacion_del_aprendizaje", columnDefinition="TEXT")
	private String evaluacionDelAprendizaje;

	private String contenidos;
	
	@Column(name = "descripcion_objeto_relacionado")
	private String descripcionObjetoRelacionado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_registro")
	private Date fechaRegistro;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_liberacion")
	private Date fechaLiberacion;
	
	@Column(name="tiempo_tipico_aprendizaje")
	private String tiempoTipicoAprendizaje;

	@Column(name = "lugar_edicion")
	private String lugarEdicion;

	@Column(name = "palabras_clave", columnDefinition="TEXT")
	private String palabrasClave;
	
	@Column(name="objetivo_general", columnDefinition="TEXT")
	private String objetivoGeneral;
	
	@Column(name="nombre_actividad")
	private String nombreActividad;

	@Column(name = "peso_archivos_scorm")
	private String pesoArchivosScorm;

	private String proposito;

	@Column(name="requisitos_conocimientos_previos")
	private String requisitosConocimientosPrevios;

	@Column(name = "rango_tipico_de_edad")
	private String rangoTipicoDeEdad;
	
	@Column(name = "resumen", columnDefinition="TEXT")
	private String resumen;



	@Column(name="temas_relacionados")
	private String temasRelacionados;

	@Column(name="tiempo_estimado_rep_lectura")
	private String tiempoEstimadoRepLectura;

	@Column(name = "tratamiento_editorial", columnDefinition="TEXT")
	private String tratamientoEditorial;

	@Column(name="tiene_costo")
	private Boolean tieneCosto;

	@Column(name="tiene_derechos_autor")
	private Boolean tieneDerechosAutor;
	
	private String url;
	
	@Column(name = "usuario_modifico")
	private Long usuarioModifico;

	private String version;
	
	@Column(name = "estatus_df")
	private Boolean estatusDF;
	
	@Column(name = "comentarios", columnDefinition="TEXT")
	private String comentarios;

	@Column(name = "responsable_academico")
	private String responsableAcademico;

	@Column(name = "ambito_aplicacion")
	private String ambitoAplicacion;

	// bi-directional one-to-one association to
	// TblFichaDescripcionObjetoAprendizaje
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "fichaDescriptivaOa", cascade = CascadeType.ALL)
	private TblFichaDescripcionObjetoAprendizaje fichaDescripcionOa;

	public TblFichaDescriptivaObjetoAprendizaje() {
	}

	public TblFichaDescripcionObjetoAprendizaje getFichaDescripcionOa() {
		return fichaDescripcionOa;
	}

	public void setFichaDescripcionOa(TblFichaDescripcionObjetoAprendizaje fichaDescripcionOa) {
		this.fichaDescripcionOa = fichaDescripcionOa;
	}

	public Integer getIdFoa() {
		return this.idFoa;
	}

	public void setIdFoa(Integer idFoa) {
		this.idFoa = idFoa;
	}


	public String getAnotacion() {
		return this.anotacion;
	}

	public void setAnotacion(String anotacion) {
		this.anotacion = anotacion;
	}

	public String getCondicionesUso() {
		return this.condicionesUso;
	}

	public void setCondicionesUso(String condicionesUso) {
		this.condicionesUso = condicionesUso;
	}

	public String getDescripcionContenido() {
		return this.descripcionContenido;
	}

	public void setDescripcionContenido(String descripcionContenido) {
		this.descripcionContenido = descripcionContenido;
	}

	public String getDescripcionObjetoRelacionado() {
		return this.descripcionObjetoRelacionado;
	}

	public void setDescripcionObjetoRelacionado(String descripcionObjetoRelacionado) {
		this.descripcionObjetoRelacionado = descripcionObjetoRelacionado;
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

	public String getLugarEdicion() {
		return this.lugarEdicion;
	}

	public void setLugarEdicion(String lugarEdicion) {
		this.lugarEdicion = lugarEdicion;
	}

	public String getPalabrasClave() {
		return this.palabrasClave;
	}

	public void setPalabrasClave(String palabrasClave) {
		this.palabrasClave = palabrasClave;
	}

	public String getPesoArchivosScorm() {
		return this.pesoArchivosScorm;
	}

	public void setPesoArchivosScorm(String pesoArchivosScorm) {
		this.pesoArchivosScorm = pesoArchivosScorm;
	}

	public String getProposito() {
		return this.proposito;
	}

	public void setProposito(String proposito) {
		this.proposito = proposito;
	}

	public String getRangoTipicoDeEdad() {
		return this.rangoTipicoDeEdad;
	}

	public void setRangoTipicoDeEdad(String rangoTipicoDeEdad) {
		this.rangoTipicoDeEdad = rangoTipicoDeEdad;
	}

	public String getResumen() {
		return this.resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public String getTratamientoEditorial() {
		return this.tratamientoEditorial;
	}

	public void setTratamientoEditorial(String tratamientoEditorial) {
		this.tratamientoEditorial = tratamientoEditorial;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getEvaluacionDelAprendizaje() {
		return evaluacionDelAprendizaje;
	}

	public void setEvaluacionDelAprendizaje(String evaluacionDelAprendizaje) {
		this.evaluacionDelAprendizaje = evaluacionDelAprendizaje;
	}

	public String getRequisitosConocimientosPrevios() {
		return requisitosConocimientosPrevios;
	}

	public void setRequisitosConocimientosPrevios(String requisitosConocimientosPrevios) {
		this.requisitosConocimientosPrevios = requisitosConocimientosPrevios;
	}

	public String getTiempoEstimadoRepLectura() {
		return tiempoEstimadoRepLectura;
	}

	public void setTiempoEstimadoRepLectura(String tiempoEstimadoRepLectura) {
		this.tiempoEstimadoRepLectura = tiempoEstimadoRepLectura;
	}

	public String getTiempoTipicoAprendizaje() {
		return tiempoTipicoAprendizaje;
	}

	public void setTiempoTipicoAprendizaje(String tiempoTipicoAprendizaje) {
		this.tiempoTipicoAprendizaje = tiempoTipicoAprendizaje;
	}

	public String getTemasRelacionados() {
		return temasRelacionados;
	}

	public void setTemasRelacionados(String temasRelacionados) {
		this.temasRelacionados = temasRelacionados;
	}

	public Boolean getEstaDisponible() {
		return estaDisponible;
	}

	public void setEstaDisponible(Boolean estaDisponible) {
		this.estaDisponible = estaDisponible;
	}

	public String getContenidos() {
		return contenidos;
	}

	public void setContenidos(String contenidos) {
		this.contenidos = contenidos;
	}

	public Date getFechaLiberacion() {
		return fechaLiberacion;
	}

	public void setFechaLiberacion(Date fechaLiberacion) {
		this.fechaLiberacion = fechaLiberacion;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the tieneCosto
	 */
	public Boolean getTieneCosto() {
		return tieneCosto;
	}

	/**
	 * @param tieneCosto the tieneCosto to set
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
	 * @param tieneDerechosAutor the tieneDerechosAutor to set
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
	 * @param estatusDF the estatusDF to set
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
	 * @param comentarios the comentarios to set
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
	 * @param responsableAcademico the responsableAcademico to set
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
	 * @param ambitoAplicacion the ambitoAplicacion to set
	 */
	public void setAmbitoAplicacion(String ambitoAplicacion) {
		this.ambitoAplicacion = ambitoAplicacion;
	}

	
}