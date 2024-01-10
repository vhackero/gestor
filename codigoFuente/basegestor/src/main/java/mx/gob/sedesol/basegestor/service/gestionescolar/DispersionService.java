package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TblInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TblInscripcionResumenDTO;

public interface DispersionService {
	
    List<TblInscripcionResumenDTO> getInscripcionResumenByProgramaEducativo(List<String> programaEducativo);
    
    List<TblInscripcionDTO> getInscripcionesByIdPlan(Integer idPlan);
    
    List<TblInscripcionDTO> getInscripcionesByProgramasEducativos(List<String> programas);
	
	
/*	 
	
	List<RelGrupoParticipanteDTO> buscarAlumno(Integer idGrupo, String nombre);
	
	List<RelGrupoParticipanteDTO> getParticipantesByGrupo(Integer idGrupo) ;
	
	List<RelGrupoParticipanteDTO> getParticipantesByEvento(Integer idEvento) ;

	RelGrupoParticipanteDTO almacenarParticipante(GrupoDTO grupo, PersonaDTO persona, EventoCapacitacionDTO evento, ParametroWSMoodleDTO parametroWSMoodleDTO);

	 

	ResultadoDTO<RelGrupoParticipanteDTO> almacenarParticipantes(List<PersonaDTO> listaPersonas, GrupoDTO grupo,
			EventoCapacitacionDTO evento, ParametroWSMoodleDTO parametroWSMoodleDTO);	
	
	Map<String,Long> obtenerParticipantesPorIdEventoCapacitacion(List<Integer> idEventosList);
	
	Map<String,Map<String,String>> obtenerGeneroParticipantesPorIdEventoCapacitacion(List<Integer> idEventosList);
	
    List<RelGrupoParticipanteDTO> obtenerEventosCapacitacionPorIdParticipante(Long idParticipante,Integer idEstatusEc);
    
    List<RelGrupoParticipanteDTO> obtenEvtsEnLineayMixtosPorIdParticipante(Long idParticipante,Integer idEstatusEc);
    
    List<RelGrupoParticipanteDTO> obtenerAlumnosQueRecibieronConstPorIdGrupo(Integer idGrupo) throws Exception;
    
    ResultadoDTO<RelGrupoParticipanteDTO> actualizaRelGrupoParticipante(RelGrupoParticipanteDTO dto);
    
    List<RelGrupoParticipanteDTO> buscarParticipanteEnEvento(Integer idEvento, Long idPersona);

	List<RelGrupoParticipanteDTO> getAlumnosQueRecibieronConstPorIdGrupo(Integer idGrupo) throws Exception; */
}



