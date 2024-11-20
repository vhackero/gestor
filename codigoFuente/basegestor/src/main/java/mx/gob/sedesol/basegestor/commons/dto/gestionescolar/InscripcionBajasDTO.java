package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.time.LocalDateTime;

public class InscripcionBajasDTO {
	
	private Long idPlan;
    private Long idPrograma;
    private Long idTipoBaja;
    private String tipoBaja;
    private LocalDateTime fechaModificacion;

    
	public Long getIdPlan() {
		return idPlan;
	}
	public void setIdPlan(Long idPlan) {
		this.idPlan = idPlan;
	}
	public Long getIdPrograma() {
		return idPrograma;
	}
	public void setIdPrograma(Long idPrograma) {
		this.idPrograma = idPrograma;
	}
	public Long getIdTipoBaja() {
		return idTipoBaja;
	}
	public void setIdTipoBaja(Long idTipoBaja) {
		this.idTipoBaja = idTipoBaja;
	}
	public String getTipoBaja() {
		return tipoBaja;
	}
	public void setTipoBaja(String tipoBaja) {
		this.tipoBaja = tipoBaja;
	}
	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(LocalDateTime fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}   
}
