package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

public class TextoSistemaDTO extends ComunDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Length(max=100)
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String clave;
	
	@Length(max=800)
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String valor;
	
	private FuncionalidadDTO funcionalidad;
	
	public TextoSistemaDTO() {
		setFechaRegistro(new Date());
	}
	
	public TextoSistemaDTO(Long usuarioModifico) {
		setUsuarioModifico(usuarioModifico);
		setFechaRegistro(new Date());
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
	public FuncionalidadDTO getFuncionalidad() {
		return funcionalidad;
	}
	public void setFuncionalidad(FuncionalidadDTO funcionalidad) {
		this.funcionalidad = funcionalidad;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		if (ObjectUtils.isNull(valor)) {
			this.valor = null;
		} else {
			this.valor = valor.trim();
		}
	}
}
