/**
 * 
 */
package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

/**
 * @author PlanetMedia
 *
 */
public class FuncionalidadDTO extends ComunDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idFuncionalidad;
	@Length(max=100)
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String clave;
	@Length(max=250)
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String descripcion;
	private boolean activo;
	private FuncionalidadDTO funcionalidadPadre;
	
	private List<FuncionalidadDTO> funcionalidadesHijo;
	
	public FuncionalidadDTO() {
		setFechaRegistro(new Date());
		this.activo = true;
	}
	
	public FuncionalidadDTO(Long usuarioModifico) {
		setUsuarioModifico(usuarioModifico);
		setFechaRegistro(new Date());
		this.activo = true;
	}
	
	public Long getIdFuncionalidad() {
		return idFuncionalidad;
	}
	public void setIdFuncionalidad(Long idFuncionalidad) {
		this.idFuncionalidad = idFuncionalidad;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		if (ObjectUtils.isNull(clave)) {
			this.clave = null;
		} else {
			this.clave = clave.trim();
		}
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		if (ObjectUtils.isNull(descripcion)) {
			this.descripcion = null;
		} else {
			this.descripcion = descripcion.trim();
		}
	}
	public boolean isActivo() {
		return this.activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public FuncionalidadDTO getFuncionalidadPadre() {
		return funcionalidadPadre;
	}
	public void setFuncionalidadPadre(FuncionalidadDTO funcionalidadPadre) {
		this.funcionalidadPadre = funcionalidadPadre;
	}

	public List<FuncionalidadDTO> getFuncionalidadesHijo() {
		return funcionalidadesHijo;
	}

	public void setFuncionalidadesHijo(List<FuncionalidadDTO> funcionalidadesHijo) {
		this.funcionalidadesHijo = funcionalidadesHijo;
	}
}
