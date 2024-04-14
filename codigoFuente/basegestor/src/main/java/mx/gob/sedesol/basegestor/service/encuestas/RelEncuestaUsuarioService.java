package mx.gob.sedesol.basegestor.service.encuestas;

import java.util.Date;
import java.util.List;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.EncuestaDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.RelEncuestaUsuarioDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Acta;
import mx.gob.sedesol.basegestor.service.admin.CommonService;

public interface RelEncuestaUsuarioService extends CommonService<RelEncuestaUsuarioDTO, Integer>
{
	List<RelEncuestaUsuarioDTO> busquedaPorCriterios(RelEncuestaUsuarioDTO criterios);
	List<RelEncuestaUsuarioDTO> consultarEncuestasAsignadas(Integer clEvento ,Long clPersona,Boolean esActivo);
	List<RelEncuestaUsuarioDTO> consultarEncuestasAsignadas(Long clPersona,Boolean esActivo);
	List<EncuestaDTO> obtenerDetalleEncuestas(Integer clEvento,Long clPersona,Boolean esActivo);
	List<RelEncuestaUsuarioDTO> consultarEncuestasAsignadas(Integer clEvento,Long clPersona,Integer idTipoEncuesta,Boolean esActivo);
	void asignarEncuestaParticipantes(Integer idGrupo,Date fechafinalEC,Long usuarioModifico);
	void asigarEncuestaAlumno(Integer idEvento,Integer idGrupo,Long idPersona,Date fechaFinalEC,Long usuarioModifico);
	void asignarEncuestasPorDefecto(Integer idGrupo,Date fechafinalEC,Long usuarioModifico);
	List<RelEncuestaUsuarioDTO> consultarEncuestasAsignadas(List<Integer> clEventoList,Long clPersona,Integer idTipoEncuesta,Boolean esActivo);
	List<RelEncuestaUsuarioDTO> consultarEncuestasAsignadas(List<Integer> clEventoList,Long clPersona,Integer idTipoEncuesta);
	List<RelEncuestaUsuarioDTO> consultarEncuestasAsignadas(List<Integer> clEventoList,Long clPersona,Boolean esActivo);
	List<RelEncuestaUsuarioDTO> obtenerParticipantesEncuestasPorPrograma(Integer idPrograma);
	String fecharLimite(Date fecha, Integer dias);
	
	public void cargaActa(Acta acta);
	public void eliminarActa(Acta acta);
	public Acta descargaActa(int idGrupo, long idUser);
	public Acta getActaByIdGrupo(int idGrupo) ;
}
