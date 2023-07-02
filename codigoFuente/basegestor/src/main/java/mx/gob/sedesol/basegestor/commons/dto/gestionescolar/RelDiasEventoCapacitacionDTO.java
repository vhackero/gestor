package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.Date;


public class RelDiasEventoCapacitacionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private Date fechaActualizacion;
	private Date fechaEventoCapacitacion;
	private Date fechaRegistro;
	private long usuarioModifico;
	private String fechaEventoFormat;
	private GrupoDTO grupo;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaEventoCapacitacion() {
		return this.fechaEventoCapacitacion;
	}

	public void setFechaEventoCapacitacion(Date fechaEventoCapacitacion) {
		this.fechaEventoCapacitacion = fechaEventoCapacitacion;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

//	public int getIdGrupo() {
//		return this.idGrupo;
//	}
//
//	public void setIdGrupo(int idGrupo) {
//		this.idGrupo = idGrupo;
//	}



	public String getFechaEventoFormat() {
		return fechaEventoFormat;
	}

	public void setFechaEventoFormat(String fechaEventoFormat) {
		this.fechaEventoFormat = fechaEventoFormat;
	}

	public long getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	/**
	 * @return the grupo
	 */
	public GrupoDTO getGrupo() {
		return grupo;
	}

	/**
	 * @param grupo the grupo to set
	 */
	public void setGrupo(GrupoDTO grupo) {
		this.grupo = grupo;
	}	

}