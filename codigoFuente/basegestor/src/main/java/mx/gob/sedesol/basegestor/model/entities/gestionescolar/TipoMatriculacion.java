package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;

public class TipoMatriculacion implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idTipoMatriculacion;
	private String nombre;
	
	public Integer getIdTipoMatriculacion() {
		return idTipoMatriculacion;
	}
	public void setIdTipoMatriculacion(Integer idTipoMatriculacion) {
		this.idTipoMatriculacion = idTipoMatriculacion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
