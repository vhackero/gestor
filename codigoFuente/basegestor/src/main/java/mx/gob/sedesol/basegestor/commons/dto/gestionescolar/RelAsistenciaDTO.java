package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;


import java.io.Serializable;
import java.util.Date;



public class RelAsistenciaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
     
	private long  id;
	private Date fechaActualizacion;  
	private Date fechaRegistro;
	private int idDiasEventoCap;
	private int idGrupoParticipante;
	private int idTpoAsistencia;
	private long usuarioModifico;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public int getIdDiasEventoCap() {
		return this.idDiasEventoCap;
	}

	public void setIdDiasEventoCap(int idDiasEventoCap) {
		this.idDiasEventoCap = idDiasEventoCap;
	}

	public int getIdGrupoParticipante() {
		return this.idGrupoParticipante;
	}

	public void setIdGrupoParticipante(int idGrupoParticipante) {
		this.idGrupoParticipante = idGrupoParticipante;
	}

	public int getIdTpoAsistencia() {
		return this.idTpoAsistencia;
	}

	public void setIdTpoAsistencia(int idTpoAsistencia) {
		this.idTpoAsistencia = idTpoAsistencia;
	}

	public long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}


	

}