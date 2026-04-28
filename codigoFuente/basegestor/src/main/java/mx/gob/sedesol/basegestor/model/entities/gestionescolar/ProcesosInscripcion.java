package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;

/**
 * ENTITY 
 * @author ITTIVA
 * 
 */
public class ProcesosInscripcion implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idProcesoInscripcion;
	private Integer idConvocatoria;
	private Integer idTipoProceso;
	private String nombre;
	
	public Integer getIdProcesoInscripcion() {
		return idProcesoInscripcion;
	}
	public void setIdProcesoInscripcion(Integer idProcesoInscripcion) {
		this.idProcesoInscripcion = idProcesoInscripcion;
	}
	public Integer getIdConvocatoria() {
		return idConvocatoria;
	}
	public void setIdConvocatoria(Integer idConvocatoria) {
		this.idConvocatoria = idConvocatoria;
	}
	public Integer getIdTipoProceso() {
		return idTipoProceso;
	}
	public void setIdTipoProceso(Integer idTipoProceso) {
		this.idTipoProceso = idTipoProceso;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	

}
