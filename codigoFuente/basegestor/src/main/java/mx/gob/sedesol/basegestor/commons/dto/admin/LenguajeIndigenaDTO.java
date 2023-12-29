package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;

public class LenguajeIndigenaDTO extends ComunDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idLenguajeIndigena;
	private String lenguaje;
	private String descripcion;
	
	public LenguajeIndigenaDTO() {
		setFechaRegistro(new Date());		
	}
	
	public LenguajeIndigenaDTO(Long usuarioModifico) {
		setUsuarioModifico(usuarioModifico);
		setFechaRegistro(new Date());
	}
	
	
	public Integer getIdLenguajeIndigena() {
		return idLenguajeIndigena;
	}

	public void setIdLenguajeIndigena(Integer idLenguajeIndigena) {
		this.idLenguajeIndigena = idLenguajeIndigena;
	}

	public String getLenguaje() {
		return lenguaje;
	}
	public void setLenguaje(String lenguaje) {
		this.lenguaje = lenguaje;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
