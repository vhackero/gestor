package mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PersonaResponsabilidadesDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelUnidadOaAva;

public class AmbienteVirtualAprendizajeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Boolean activo;
	private Date fechaActualizacion;
	private Date fechaRegistro;
	private Long usuarioModifico;
	private Boolean validacionAva;
	private List<RelUnidadOaAva> relUnidadOaAvas;
	private Byte porcentajeAvance;
	private CatalogoComunDTO catEstadoAva;
	private PersonaResponsabilidadesDTO personaResponsabilidades;
	private EventoCapacitacionDTO eventoCapacitacion;
	private ParametroWSMoodleDTO plataformaMoodle;
	private Integer idCursoLms;
	private String urlLms;
	private Boolean esAvaArchivado;
	private Integer idAvaClonLms;

	public AmbienteVirtualAprendizajeDTO() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
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

	public Byte getPorcentajeAvance() {
		return porcentajeAvance;
	}

	public void setPorcentajeAvance(Byte porcentajeAvance) {
		this.porcentajeAvance = porcentajeAvance;
	}

	public Long getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public ParametroWSMoodleDTO getPlataformaMoodle() {
		return plataformaMoodle;
	}

	public void setPlataformaMoodle(ParametroWSMoodleDTO plataformaMoodle) {
		this.plataformaMoodle = plataformaMoodle;
	}

	public Boolean getValidacionAva() {
		return validacionAva;
	}

	public void setValidacionAva(Boolean validacionAva) {
		this.validacionAva = validacionAva;
	}

	public List<RelUnidadOaAva> getRelUnidadOaAvas() {
		return this.relUnidadOaAvas;
	}

	public void setRelUnidadOaAvas(List<RelUnidadOaAva> relUnidadOaAvas) {
		this.relUnidadOaAvas = relUnidadOaAvas;
	}

	public CatalogoComunDTO getCatEstadoAva() {
		return catEstadoAva;
	}

	public void setCatEstadoAva(CatalogoComunDTO catEstadoAva) {
		this.catEstadoAva = catEstadoAva;
	}

	public PersonaResponsabilidadesDTO getPersonaResponsabilidades() {
		return personaResponsabilidades;
	}

	public void setPersonaResponsabilidades(PersonaResponsabilidadesDTO personaResponsabilidades) {
		this.personaResponsabilidades = personaResponsabilidades;
	}

	public EventoCapacitacionDTO getEventoCapacitacion() {
		return eventoCapacitacion;
	}

	public void setEventoCapacitacion(EventoCapacitacionDTO eventoCapacitacion) {
		this.eventoCapacitacion = eventoCapacitacion;
	}

	public Integer getIdCursoLms() {
		return idCursoLms;
	}

	public void setIdCursoLms(Integer idCursoLms) {
		this.idCursoLms = idCursoLms;
	}

	public String getUrlLms() {
		return urlLms;
	}

	public void setUrlLms(String urlLms) {
		this.urlLms = urlLms;
	}

	public Boolean getEsAvaArchivado() {
		return esAvaArchivado;
	}

	public void setEsAvaArchivado(Boolean esAvaArchivado) {
		this.esAvaArchivado = esAvaArchivado;
	}

	public Integer getIdAvaClonLms() {
		return idAvaClonLms;
	}

	public void setIdAvaClonLms(Integer idAvaClonLms) {
		this.idAvaClonLms = idAvaClonLms;
	}

}
