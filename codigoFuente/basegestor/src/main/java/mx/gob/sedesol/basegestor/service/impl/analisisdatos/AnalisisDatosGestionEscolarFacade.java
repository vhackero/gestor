package mx.gob.sedesol.basegestor.service.impl.analisisdatos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.EntidadFederativaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MunicipioDTO;
import mx.gob.sedesol.basegestor.commons.dto.analisisdatos.EstadisticasEventoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.service.admin.EntidadFederativaService;
import mx.gob.sedesol.basegestor.service.admin.MunicipioService;
import mx.gob.sedesol.basegestor.service.impl.gestionescolar.EventoCapacitacionServiceFacade;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaCurricularService;

@Service("analisisDatosGestionEscolarFacade")
public class AnalisisDatosGestionEscolarFacade {
	@Autowired
	private EntidadFederativaService entidadFederativaService;
	
	@Autowired
	private MunicipioService municipioService;
	
	@Autowired
	private EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade;
	
	@Autowired
	private MallaCurricularService mallaCurricularService;
	

	
	
	
	public MallaCurricularDTO obtenerMallaCurricular(){
		return eventoCapacitacionServiceFacade.obtenerMallaCurricular();
	}
	
	public List<EntidadFederativaDTO> obtenerEntidadesPorPais(String idPais) {
		return entidadFederativaService.obtenerEntidadesPorPais(idPais);
	}
	
	public List<MunicipioDTO> buscarPorEntidadFederativa(int idEntidadFederativa) {
		return municipioService.buscarPorEntidadFederativa(idEntidadFederativa);
	}

	public EntidadFederativaService getEntidadFederativaService() {
		return entidadFederativaService;
	}

	public void setEntidadFederativaService(EntidadFederativaService entidadFederativaService) {
		this.entidadFederativaService = entidadFederativaService;
	}

	public MunicipioService getMunicipioService() {
		return municipioService;
	}

	public void setMunicipioService(MunicipioService municipioService) {
		this.municipioService = municipioService;
	}

	public EventoCapacitacionServiceFacade getEventoCapacitacionServiceFacade() {
		return eventoCapacitacionServiceFacade;
	}

	public void setEventoCapacitacionServiceFacade(EventoCapacitacionServiceFacade eventoCapacitacionServiceFacade) {
		this.eventoCapacitacionServiceFacade = eventoCapacitacionServiceFacade;
	}



	public MallaCurricularService getMallaCurricularService() {
		return mallaCurricularService;
	}



	public void setMallaCurricularService(MallaCurricularService mallaCurricularService) {
		this.mallaCurricularService = mallaCurricularService;
	}

}
