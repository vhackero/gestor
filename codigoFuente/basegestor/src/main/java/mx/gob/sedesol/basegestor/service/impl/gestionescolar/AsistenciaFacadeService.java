package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CatAsistenciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GroupByGestionEscolarDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GrupoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelAsistenciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelDiasEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.service.ServiceException;
import mx.gob.sedesol.basegestor.service.gestionescolar.EventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoParticipanteService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RegistroAsistenciaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RelAsistenciaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RelDiasEventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RelReponsableProduccionEcService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaCurricularService;

@Service("asistenciaFacadeService")
public class AsistenciaFacadeService {
	
	@Autowired
	private RegistroAsistenciaService registroAsistenciaService;
	
	@Autowired
	private RelReponsableProduccionEcService relReponsableProduccionEcService;
	
	@Autowired
	private EventoCapacitacionService eventoCapacitacionService;
	
	@Autowired
	private MallaCurricularService mallaCurricularService;
	
	@Autowired
	private GrupoService grupoService;  

	@Autowired
	private RelDiasEventoCapacitacionService relDiasEventoCapacitacionService;
	
	@Autowired
	private GrupoParticipanteService grupoParticipanteService;
	
	@Autowired
	private RelAsistenciaService relAsistenciaService;
	
	public EventoCapacitacionDTO getEvento(Integer idEvento) {
		return eventoCapacitacionService.getEvento(idEvento);
	}
	
	public List<GrupoDTO> getGruposByEvento(Integer idEvento) {
	   return grupoService.getGruposByEvento(idEvento);
	}
	
	public List<RelGrupoParticipanteDTO> getParticipantesByGrupo(Integer idGrupo) {
		return grupoParticipanteService.getParticipantesByGrupo(idGrupo);
	}
	
	 public List<RelDiasEventoCapacitacionDTO> getDiasEventoByGrupo (Integer idGrupo) {
		 return relDiasEventoCapacitacionService.getDiasEventoByGrupo(idGrupo);
	 }

	 public List<RelAsistenciaDTO> getAsistenciaByIdGrupoParticipante (List<Integer> lisIdGrupoParticipante) {
		 return relAsistenciaService.getAsistenciaByIdGrupoParticipante(lisIdGrupoParticipante);
	 }
	
	public List<RelGrupoParticipanteDTO> buscarAlumno(Integer idGrupo, String nombre){
		return grupoParticipanteService.buscarAlumno(idGrupo, nombre);
	}

	public RegistroAsistenciaService getRegistroAsistenciaService() {
		return registroAsistenciaService;
	}

	public void setRegistroAsistenciaService(RegistroAsistenciaService registroAsistenciaService) {
		this.registroAsistenciaService = registroAsistenciaService;
	}

	public EventoCapacitacionService getEventoCapacitacionService() {
		return eventoCapacitacionService;
	}

	public void setEventoCapacitacionService(EventoCapacitacionService eventoCapacitacionService) {
		this.eventoCapacitacionService = eventoCapacitacionService;
	}

	public MallaCurricularService getMallaCurricularService() {
		return mallaCurricularService;
	}
	
	public void crearArregloAsistenciasXparticipante(List<RelDiasEventoCapacitacionDTO> diasEvento
			,List<RelGrupoParticipanteDTO> participantes,  List<RelAsistenciaDTO> asistencias){
		
		registroAsistenciaService.crearArregloAsistenciasXparticipante(diasEvento, participantes, asistencias);
	}
	
	public List<CatAsistenciaDTO> getCatAsistencia(int idEvento){
		return registroAsistenciaService.getCatAsistencia(idEvento);	
	}
	
	public CatalogoComunDTO getEjeCapacitacion(int idEjeCapacitacion, List<CatalogoComunDTO> ejesCapacitacion){
		return registroAsistenciaService.getEjeCapacitacion(idEjeCapacitacion, ejesCapacitacion);
	}
	
	public String getXML(int idEvento ){
		return registroAsistenciaService.getXML(idEvento);
	}
	
	public ResultadoDTO agregarDiaEvento(RelDiasEventoCapacitacionDTO eventoDiaDTO){
		return registroAsistenciaService.agregarDiaEvento( eventoDiaDTO);
	}
	
	public String getTipoAsistencia(int idTipoAsistencia){
		return registroAsistenciaService.getTipoAsistencia (idTipoAsistencia);
	}
	
	public ResultadoDTO guardarRelAsistencia (long usuarioSesion, List<RelGrupoParticipanteDTO> grupoParticipantes) throws ServiceException{
		return registroAsistenciaService.guardarRelAsistencia(usuarioSesion, grupoParticipantes);
	}
	
	public Map<String,String> obtenerPorcentajeAsistenciasPorEventoCapacitacion(List<Integer> idEventoCapacitacionList,Integer idTipoAsistencia){
		
		List<GroupByGestionEscolarDTO> diasPorEventoCapacitacion = null;
		List<GroupByGestionEscolarDTO> asistenciaPorEventoCapacitacion = null;
		Map<String,String> porcentajeAsistenciasPorEventoCapacitacion = new HashMap<String,String>();
		
		diasPorEventoCapacitacion = 		
				relDiasEventoCapacitacionService.obtenerDiasEventoAgrupadoPorEvento(idEventoCapacitacionList);
		
	    asistenciaPorEventoCapacitacion =
	    		relAsistenciaService.obtenerAsistenciaAgrupadaPorEventoCapacitacion(idEventoCapacitacionList, idTipoAsistencia);
		
		for (Integer idEventoCapacitacion : idEventoCapacitacionList) {
			
			Long numeroDias = 
					obtenerValorDeLaListaPorIdEventoCapacitacion
							(diasPorEventoCapacitacion,idEventoCapacitacion);

			Long asistencias = 
					obtenerValorDeLaListaPorIdEventoCapacitacion
							(asistenciaPorEventoCapacitacion,idEventoCapacitacion);
									
			if(ObjectUtils.isNotNull(numeroDias) && ObjectUtils.isNotNull(asistencias)){
			
				Double  porcentajeAsistencias  = 
						Double.valueOf((asistencias * ConstantesGestor.NUMERO_CIEN)/numeroDias);
			
					
				porcentajeAsistenciasPorEventoCapacitacion.
							put(String.valueOf(idEventoCapacitacion),String.valueOf(porcentajeAsistencias));
			}
		}
						
		return porcentajeAsistenciasPorEventoCapacitacion;
		
	}
	
	private Long obtenerValorDeLaListaPorIdEventoCapacitacion(List<GroupByGestionEscolarDTO> diasPorEventoCapacitacion,
			Integer idEventoCapacitacion){
		
		Long diasPoreventoCapacitacion = null;
		for (GroupByGestionEscolarDTO groupByGestionEscolarDTO : diasPorEventoCapacitacion) {
				if(groupByGestionEscolarDTO.getLlavePrimaria().equals(String.valueOf(idEventoCapacitacion))){
					diasPoreventoCapacitacion = Long.valueOf(groupByGestionEscolarDTO.getValor()); 			
				}
			}		
		return diasPoreventoCapacitacion;
	}
	
	
	public void setMallaCurricularService(MallaCurricularService mallaCurricularService) {
		this.mallaCurricularService = mallaCurricularService;
	}

	public GrupoService getGrupoService() {
		return grupoService;
	}

	public void setGrupoService(GrupoService grupoService) {
		this.grupoService = grupoService;
	}

	public RelDiasEventoCapacitacionService getRelDiasEventoCapacitacionService() {
		return relDiasEventoCapacitacionService;
	}

	public void setRelDiasEventoCapacitacionService(RelDiasEventoCapacitacionService relDiasEventoCapacitacionService) {
		this.relDiasEventoCapacitacionService = relDiasEventoCapacitacionService;
	}

	public GrupoParticipanteService getGrupoParticipanteService() {
		return grupoParticipanteService;
	}

	public void setGrupoParticipanteService(GrupoParticipanteService grupoParticipanteService) {
		this.grupoParticipanteService = grupoParticipanteService;
	}

	public RelAsistenciaService getRelAsistenciaService() {
		return relAsistenciaService;
	}

	public void setRelAsistenciaService(RelAsistenciaService relAsistenciaService) {
		this.relAsistenciaService = relAsistenciaService;
	}

	public RelReponsableProduccionEcService getRelReponsableProduccionEcService() {
		return relReponsableProduccionEcService;
	}

	public void setRelReponsableProduccionEcService(RelReponsableProduccionEcService relReponsableProduccionEcService) {
		this.relReponsableProduccionEcService = relReponsableProduccionEcService;
	}
	
	
	
	
	
	

}
