package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.Date;

public class AsistenciaAuxDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;
	private int idDiaCapacitacion;
	private int idtipoAsistencia;
	private String tipoAsistencia;
	private int valor;
	private Date fechaEventoCapacitacion;
	private String fechaEventoFormat;
	private int idGrupoParticipante;

	private Date fechaActualizacion;

	private Date fechaRegistro;

	private long usuarioModifico;

	public int getIdDiaCapacitacion() {
		return idDiaCapacitacion;
	}

	public void setIdDiaCapacitacion(int idDiaCapacitacion) {
		this.idDiaCapacitacion = idDiaCapacitacion;
	}

	public int getIdtipoAsistencia() {
		return idtipoAsistencia;
	}

	public void setIdtipoAsistencia(int idtipoAsistencia) {

		this.idtipoAsistencia = idtipoAsistencia;
	}

	public Date getFechaEventoCapacitacion() {
		return fechaEventoCapacitacion;
	}

	public void setFechaEventoCapacitacion(Date fechaEventoCapacitacion) {
		this.fechaEventoCapacitacion = fechaEventoCapacitacion;
	}

	public String getTipoAsistencia() {
		return tipoAsistencia;
	}

	public void setTipoAsistencia(String tipoAsistencia) {
		this.tipoAsistencia = tipoAsistencia;
	}

	public String getFechaEventoFormat() {
		return fechaEventoFormat;
	}

	public void setFechaEventoFormat(String fechaEventoFormat) {
		this.fechaEventoFormat = fechaEventoFormat;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getIdGrupoParticipante() {
		return idGrupoParticipante;
	}

	public void setIdGrupoParticipante(int idGrupoParticipante) {
		this.idGrupoParticipante = idGrupoParticipante;
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

	public long getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

}
