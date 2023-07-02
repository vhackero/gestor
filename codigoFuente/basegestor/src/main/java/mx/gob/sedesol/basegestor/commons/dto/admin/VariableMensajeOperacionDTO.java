package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

public class VariableMensajeOperacionDTO extends ComunDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idVariableMensajeOperacion;
	private FuncionalidadDTO funcionalidadDTO;
	
	@Length(max=100)
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String descripcion;
	
	@Length(max=45)
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String clave;
	
	private String valor;
	
	public VariableMensajeOperacionDTO() {
		setFechaRegistro(new Date());
	}
	
	public VariableMensajeOperacionDTO(String clave, String valor) {
		setFechaRegistro(new Date());
		this.clave = clave;
		this.valor = valor;
	}
	
	public VariableMensajeOperacionDTO(Long usuarioModifico, FuncionalidadDTO funcionalidad) {
		setUsuarioModifico(usuarioModifico);
		setFechaRegistro(new Date());
		this.funcionalidadDTO = funcionalidad;
	}
	
	public Integer getIdVariableMensajeOperacion() {
		return idVariableMensajeOperacion;
	}
	public void setIdVariableMensajeOperacion(Integer idVariableMensajeOperacion) {
		this.idVariableMensajeOperacion = idVariableMensajeOperacion;
	}
	public FuncionalidadDTO getFuncionalidadDTO() {
		return funcionalidadDTO;
	}
	public void setFuncionalidadDTO(FuncionalidadDTO funcionalidad) {
		this.funcionalidadDTO = funcionalidad;
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
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}

}
