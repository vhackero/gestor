package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;

public class PersonaResponsabilidadesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3287419080675808937L;

	private Integer id;

	private Date fechaActualizacion;

	private Date fechaRegistro;

	private Long usuarioModifico;

	private PersonaDTO tblPersona;

	private CatalogoComunDTO catTipoResponsabilidadEc;
	
	private Boolean esResponsablePrincipal;
        

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

	public PersonaDTO getTblPersona() {
		return tblPersona;
	}

	public void setTblPersona(PersonaDTO tblPersona) {
		this.tblPersona = tblPersona;
	}

	public CatalogoComunDTO getCatTipoResponsabilidadEc() {
		return catTipoResponsabilidadEc;
	}

	public void setCatTipoResponsabilidadEc(CatalogoComunDTO catTipoResponsabilidadEc) {
		this.catTipoResponsabilidadEc = catTipoResponsabilidadEc;
	}

	public Long getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public Boolean getEsResponsablePrincipal() {
		return esResponsablePrincipal;
	}

	public void setEsResponsablePrincipal(Boolean esResponsablePrincipal) {
		this.esResponsablePrincipal = esResponsablePrincipal;
	}	


	
}
