package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.time.LocalDateTime;

public class InscripcionBajasDTO {

	private Long idBaja;
	private Long idEvento;
	private Long idPlan;
	private Long idPrograma;
	private String clavePrograma;
	private Long idTipoBaja;
	private String tipoBaja;
	private LocalDateTime fechaModificacion;
	private String periodoEvento;
	private LocalDateTime fechaInicioEvento;
	private LocalDateTime fechaFinEvento;

	public Long getIdBaja() {
		return idBaja;
	}

	public void setIdBaja(Long idBaja) {
		this.idBaja = idBaja;
	}

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

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

	public String getPeriodoEvento() {
		return periodoEvento;
	}

	public void setPeriodoEvento(String periodoEvento) {
		this.periodoEvento = periodoEvento;
	}

	public LocalDateTime getFechaInicioEvento() {
		return fechaInicioEvento;
	}

	public void setFechaInicioEvento(LocalDateTime fechaInicioEvento) {
		this.fechaInicioEvento = fechaInicioEvento;
	}

	public LocalDateTime getFechaFinEvento() {
		return fechaFinEvento;
	}

	public void setFechaFinEvento(LocalDateTime fechaFinEvento) {
		this.fechaFinEvento = fechaFinEvento;
	}

	public String getClavePrograma() {
		return clavePrograma;
	}

	public void setClavePrograma(String clavePrograma) {
		this.clavePrograma = clavePrograma;
	}

}
