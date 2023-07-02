package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona;

public class SsoElementoDTO extends ComunDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String idPadreElemento;

	private String idElemento;

	private String nombreElemento;

	private String tipoInformacion;

	private String iDFuente;

	private String fuente;

	private String tipoFuente;

	private PersonaDTO persona;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdPadreElemento() {
		return idPadreElemento;
	}

	public void setIdPadreElemento(String idPadreElemento) {
		this.idPadreElemento = idPadreElemento;
	}

	public String getIdElemento() {
		return idElemento;
	}

	public void setIdElemento(String idElemento) {
		this.idElemento = idElemento;
	}

	public String getNombreElemento() {
		return nombreElemento;
	}

	public void setNombreElemento(String nombreElemento) {
		this.nombreElemento = nombreElemento;
	}

	public String getTipoInformacion() {
		return tipoInformacion;
	}

	public void setTipoInformacion(String tipoInformacion) {
		this.tipoInformacion = tipoInformacion;
	}

	public String getiDFuente() {
		return iDFuente;
	}

	public void setiDFuente(String iDFuente) {
		this.iDFuente = iDFuente;
	}

	public String getFuente() {
		return fuente;
	}

	public void setFuente(String fuente) {
		this.fuente = fuente;
	}

	public String getTipoFuente() {
		return tipoFuente;
	}

	public void setTipoFuente(String tipoFuente) {
		this.tipoFuente = tipoFuente;
	}

	public PersonaDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}
	
	
	

}
