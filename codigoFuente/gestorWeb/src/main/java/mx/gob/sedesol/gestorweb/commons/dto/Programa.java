package mx.gob.sedesol.gestorweb.commons.dto;

public class Programa {
	private String idPrograma;
	private String nombrePrograma;
	private String descripcionPrograma;
	private String modalidadPrograma;
	private String nivelConocimiento;
	private String nivelEnsenanza;
	private String tipoCompetencia;
	private String ejeCapacitacion;
	private CargaHorariaPrograma cargaHorariaPrograma;

	public String getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(String idPrograma) {
		this.idPrograma = idPrograma;
	}

	public String getNombrePrograma() {
		return nombrePrograma;
	}

	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}

	public String getDescripcionPrograma() {
		return descripcionPrograma;
	}

	public void setDescripcionPrograma(String descripcionPrograma) {
		this.descripcionPrograma = descripcionPrograma;
	}

	public String getModalidadPrograma() {
		return modalidadPrograma;
	}

	public void setModalidadPrograma(String modalidadPrograma) {
		this.modalidadPrograma = modalidadPrograma;
	}

	public String getNivelConocimiento() {
		return nivelConocimiento;
	}

	public void setNivelConocimiento(String nivelConocimiento) {
		this.nivelConocimiento = nivelConocimiento;
	}

	public String getNivelEnsenanza() {
		return nivelEnsenanza;
	}

	public void setNivelEnsenanza(String nivelEnsenanza) {
		this.nivelEnsenanza = nivelEnsenanza;
	}

	public String getTipoCompetencia() {
		return tipoCompetencia;
	}

	public void setTipoCompetencia(String tipoCompetencia) {
		this.tipoCompetencia = tipoCompetencia;
	}

	public String getEjeCapacitacion() {
		return ejeCapacitacion;
	}

	public void setEjeCapacitacion(String ejeCapacitacion) {
		this.ejeCapacitacion = ejeCapacitacion;
	}

	public CargaHorariaPrograma getCargaHorariaPrograma() {
		return cargaHorariaPrograma;
	}

	public void setCargaHorariaPrograma(CargaHorariaPrograma cargaHorariaPrograma) {
		this.cargaHorariaPrograma = cargaHorariaPrograma;
	}

}
