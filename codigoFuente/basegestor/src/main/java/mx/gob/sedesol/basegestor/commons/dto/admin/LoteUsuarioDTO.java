package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;

public class LoteUsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idLote;
	private Long idPersona;
	private PersonaDTO persona;
	private Date fechaRegistro;
	private long usuarioModifico;
	
	public Integer getIdLote() {
		return idLote;
	}
	public void setIdLote(Integer idLote) {
		this.idLote = idLote;
	}
	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	public PersonaDTO getPersona() {
		return persona;
	}
	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public long getUsuarioModifico() {
		return usuarioModifico;
	}
	public void setUsuarioModifico(long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}


}
