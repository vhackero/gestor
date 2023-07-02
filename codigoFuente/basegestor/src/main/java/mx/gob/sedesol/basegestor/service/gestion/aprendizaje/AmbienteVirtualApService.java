package mx.gob.sedesol.basegestor.service.gestion.aprendizaje;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.AmbienteVirtualAprendizajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;


public interface AmbienteVirtualApService {

	AmbienteVirtualAprendizajeDTO findAvaById(Integer idAva);
		
	void save(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO);
	
	ResultadoDTO<AmbienteVirtualAprendizajeDTO> actualizar(AmbienteVirtualAprendizajeDTO dto);

	List<AmbienteVirtualAprendizajeDTO>  consultarAvaPorEstatusYEventoCapacitacion(Integer estatusAva,
		List<Integer> modalidadEvtCapList,Integer estatusEvtCap);

	
	List<AmbienteVirtualAprendizajeDTO> consultarAvasPorTipoCompetenciaEjeCapacitacion
	(List<Integer> modalidadEvtCapList,List <Integer> estatusEvtCap,Integer idTipoCom,
							Integer idEjeCapacitacion);
	
	List<AmbienteVirtualAprendizajeDTO> busquedaDeAvasConCriterios(EventoCapacitacionDTO filtro,String tipoDatoFechas);


	AmbienteVirtualAprendizajeDTO obtenerAVAPorEvento(Integer idEvento);
	
	List<AmbienteVirtualAprendizajeDTO> obtenerAvasPorIdEventos(List<Integer> idEventos);
	
	String asignaColorSemaro(AmbienteVirtualAprendizajeDTO ava);
		
	ResultadoDTO<AmbienteVirtualAprendizajeDTO> activaAvas(Date fechaVencimiento,Boolean estatusActual);
	
	String obtenerUrlCursoLms(Long idPersona,Integer idCurso,ParametroWSMoodleDTO parametroWSMoodleDTO);
	
	ResultadoDTO<AmbienteVirtualAprendizajeDTO> activaAvaEjecutaEventoCapacitacion(
			AmbienteVirtualAprendizajeDTO ava,
			Long idPersona,
			CatalogoComunDTO estatusAvaActivo,
			CatalogoComunDTO edoEvtCapEnEjecucion);
	
	
	Boolean respaldarCurso(AmbienteVirtualAprendizajeDTO ava,Long usuarioModifico);
	
	
	}

