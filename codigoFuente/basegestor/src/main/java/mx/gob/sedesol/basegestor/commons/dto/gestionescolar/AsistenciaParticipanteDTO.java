package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.util.Date;

public class AsistenciaParticipanteDTO {
	private String id;
	private Date fecha;
	private Integer idTipoAsistencia;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Integer getIdTipoAsistencia() {
		return idTipoAsistencia;
	}
	public void setIdTipoAsistencia(Integer idTipoAsistencia) {
		this.idTipoAsistencia = idTipoAsistencia;
	}
	

}
