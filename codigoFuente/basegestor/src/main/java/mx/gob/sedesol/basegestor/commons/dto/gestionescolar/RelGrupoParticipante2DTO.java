package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.Date;

public class RelGrupoParticipante2DTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Date fechaActualizacion;

	private Date fechaRegistro;

	private Long usuarioModifico;

	private Integer idGrupo;

	private Integer idPersonaParticipante;
	
	private Integer idPersonaLms;
	
	private Double califTotal;
	
	private Integer	porcentajeAsist;
	
	private Double califFinal;

	public RelGrupoParticipante2DTO() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Long getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Integer getIdPersonaParticipante() {
		return idPersonaParticipante;
	}

	public void setIdPersonaParticipante(Integer idPersonaParticipante) {
		this.idPersonaParticipante = idPersonaParticipante;
	}

	public Integer getIdPersonaLms() {
		return idPersonaLms;
	}

	public void setIdPersonaLms(Integer idPersonaLms) {
		this.idPersonaLms = idPersonaLms;
	}

	public Double getCalifTotal() {
		return califTotal;
	}

	public void setCalifTotal(Double califTotal) {
		this.califTotal = califTotal;
	}

	public Integer getPorcentajeAsist() {
		return porcentajeAsist;
	}

	public void setPorcentajeAsist(Integer porcentajeAsist) {
		this.porcentajeAsist = porcentajeAsist;
	}

	public Double getCalifFinal() {
		return califFinal;
	}

	public void setCalifFinal(Double califFinal) {
		this.califFinal = califFinal;
	}
	
	

}
