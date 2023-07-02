package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;

public class ParametroSistemaDTO extends ComunDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String clave;
	private String valor;
	
	public ParametroSistemaDTO() {
		setFechaRegistro(new Date());
	}
	
	public ParametroSistemaDTO(Long usuarioModifico) {
		setUsuarioModifico(usuarioModifico);
		setFechaRegistro(new Date());
	}
	
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
}
