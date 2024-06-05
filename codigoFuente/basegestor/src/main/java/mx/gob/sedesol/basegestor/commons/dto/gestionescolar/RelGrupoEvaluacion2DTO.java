package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.Date;

public class RelGrupoEvaluacion2DTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer idGpoEvaluacion;

	private Date fechaRegistro;

	private String nombreEvaluacion;

	private Long usuarioModifico;

	private Integer idGrupo;

	private Integer idTipoCalificacion;

	private Integer ponderacion;

	public RelGrupoEvaluacion2DTO() {
	}

	public Integer getIdGpoEvaluacion() {
		return idGpoEvaluacion;
	}

	public void setIdGpoEvaluacion(Integer idGpoEvaluacion) {
		this.idGpoEvaluacion = idGpoEvaluacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNombreEvaluacion() {
		return nombreEvaluacion;
	}

	public void setNombreEvaluacion(String nombreEvaluacion) {
		this.nombreEvaluacion = nombreEvaluacion;
	}

	public Long getUsuarioModifico() {
		return usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Integer getIdTipoCalificacion() {
		return idTipoCalificacion;
	}

	public void setIdTipoCalificacion(Integer idTipoCalificacion) {
		this.idTipoCalificacion = idTipoCalificacion;
	}

	public Integer getPonderacion() {
		return ponderacion;
	}

	public void setPonderacion(Integer ponderacion) {
		this.ponderacion = ponderacion;
	}
	
	

}
