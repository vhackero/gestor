package mx.gob.sedesol.basegestor.commons.dto.encuestas;

import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;

public class RelEncuestaEventoCapacitacionDTO {
	private Integer idEventoCapacitacion;

	private Integer idEncuesta;

	private Date fechaRegistro;

	private Date fechaActualizacion;

	private Boolean activo;

	private Long usuarioModifico;

	private EncuestaDTO tblEncuesta;

	private EventoCapacitacionDTO tblEvento;
        

	public RelEncuestaEventoCapacitacionDTO(){
		tblEvento = new EventoCapacitacionDTO();
		tblEncuesta = new EncuestaDTO();
		tblEncuesta.setEncuestaTipo(new CatalogoComunDTO());
		tblEncuesta.setEncuestaEstatus(new CatalogoComunDTO());
		tblEncuesta.setEncuestaObjetivo(new CatalogoComunDTO());
	}
	
	public RelEncuestaEventoCapacitacionDTO(Boolean valor){
		this.activo = valor;
	}
	
	public Integer getIdEventoCapacitacion() {
		return idEventoCapacitacion;
	}

	public void setIdEventoCapacitacion(Integer idEventoCapacitacion) {
		this.idEventoCapacitacion = idEventoCapacitacion;
	}

	public Integer getIdEncuesta() {
		return idEncuesta;
	}

	public void setIdEncuesta(Integer idEncuesta) {
		this.idEncuesta = idEncuesta;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Long getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public EncuestaDTO getTblEncuesta() {
		return tblEncuesta;
	}

	public void setTblEncuesta(EncuestaDTO tblEncuesta) {
		this.tblEncuesta = tblEncuesta;
	}

	public EventoCapacitacionDTO getTblEvento() {
		return tblEvento;
	}

	public void setTblEvento(EventoCapacitacionDTO tblEvento) {
		this.tblEvento = tblEvento;
	}

	/**
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	/**
	 * @return the activo
	 */
	public Boolean getActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
}
