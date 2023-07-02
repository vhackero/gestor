package mx.gob.sedesol.basegestor.service.gestionescolar;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CatAsistenciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelAsistenciaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelDiasEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.service.ServiceException;

public interface RegistroAsistenciaService {
    

	

	public List<CatAsistenciaDTO> getCatAsistencia(Integer idEvento);
	
	
	public void resetAsistenciaPartipante(List<RelGrupoParticipanteDTO> lista);
	
	public void crearArregloAsistenciasXparticipante(List<RelDiasEventoCapacitacionDTO> diasEvento
			,List<RelGrupoParticipanteDTO> participantes,  List<RelAsistenciaDTO> asistencias);
	
	public String getTipoAsistencia(int idTipoAsistencia);
	
	public ResultadoDTO guardarRelAsistencia (long usuarioSesion, List<RelGrupoParticipanteDTO> grupoParticipantes) throws ServiceException;
	
	public ResultadoDTO agregarDiaEvento(RelDiasEventoCapacitacionDTO eventoDiaDTO);
	
	public String getXML(int idEvento);
	
	public CatalogoComunDTO getEjeCapacitacion(int idEjeCapacitacion, List<CatalogoComunDTO> ejesCapacitacion);
	
	public List<RelGrupoParticipanteDTO>  getGrupoParticipante(int idEvento, int idGrupo);
	
	public void calcularPorcentajeAsistencia(List<RelGrupoParticipanteDTO> grupoParticipantes);
	
	
}
