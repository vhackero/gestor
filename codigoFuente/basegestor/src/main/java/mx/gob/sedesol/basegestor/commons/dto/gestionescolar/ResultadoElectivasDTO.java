package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.util.List;

public class ResultadoElectivasDTO {

	private List<InscripcionMateriasDTO> electivasDeOtrosPlanesConOfertadas;
	private Long cantidadMaximaElectivas;

	public ResultadoElectivasDTO(List<InscripcionMateriasDTO> electivasDeOtrosPlanesConOfertadas,
			Long cantidadMaximaElectivas) {
		this.electivasDeOtrosPlanesConOfertadas = electivasDeOtrosPlanesConOfertadas;
		this.cantidadMaximaElectivas = cantidadMaximaElectivas;
	}

	public List<InscripcionMateriasDTO> getElectivasDeOtrosPlanesConOfertadas() {
		return electivasDeOtrosPlanesConOfertadas;
	}

	public void setElectivasDeOtrosPlanesConOfertadas(List<InscripcionMateriasDTO> electivasDeOtrosPlanesConOfertadas) {
		this.electivasDeOtrosPlanesConOfertadas = electivasDeOtrosPlanesConOfertadas;
	}

	public Long getCantidadMaximaElectivas() {
		return cantidadMaximaElectivas;
	}

	public void setCantidadMaximaElectivas(Long cantidadMaximaElectivas) {
		this.cantidadMaximaElectivas = cantidadMaximaElectivas;
	}

}
