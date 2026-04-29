package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoPeriodoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GrupoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.MatriculacionMasivaRegistroDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PeriodoInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.model.entities.admin.CatRol;
import mx.gob.sedesol.basegestor.model.entities.admin.RelPersonaRol;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPersona;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblGrupo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.IMatriculacionMasivaRepository;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.GrupoParticipanteRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.GrupoRepo;
import mx.gob.sedesol.basegestor.model.repositories.admin.PersonaRolesRepo;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.MatriculacionMasivaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoParticipanteService;

@Service("matriculacionMasivaService")
public class MatriculacionMasivaServiceImpl implements MatriculacionMasivaService {

	private static final Logger logger = Logger.getLogger(MatriculacionMasivaServiceImpl.class);

	@Autowired
	private IMatriculacionMasivaRepository matriculacionMasivaRepository;

	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private GrupoParticipanteService grupoParticipanteService;
	
	@Autowired
	private ParametroWSMoodleService parametroWSMoodleService;
	
	@Autowired
	private GrupoRepo grupoRepo;
	
	@Autowired
	private GrupoParticipanteRepo grupoParticipanteRepo;
	
	@Autowired
	private PersonaRolesRepo personaRolesRepo;

	@Override
	@Transactional(readOnly = true)
	public List<PeriodoInscripcionDTO> obtenerPeriodosInscripcion() {
		return matriculacionMasivaRepository.obtenerPeriodosInscripcion();
	}

	@Override
	@Transactional(readOnly = true)
	public List<EventoPeriodoDTO> obtenerEventosPorPeriodo(String nombrePeriodo) {
		try {
			return matriculacionMasivaRepository.obtenerEventosPorPeriodo(nombrePeriodo);
		} catch (Exception ex) {
			logger.error("Error al consultar eventos por periodo", ex);
			throw ex;
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<MatriculacionMasivaRegistroDTO> procesarMatriculacionMasiva(
			List<MatriculacionMasivaRegistroDTO> registros, Long idUsuario) {
		List<MatriculacionMasivaRegistroDTO> respuesta = new ArrayList<>();
		if (registros == null || registros.isEmpty()) {
			return respuesta;
		}
		
		for (MatriculacionMasivaRegistroDTO registro : registros) {
			MatriculacionMasivaRegistroDTO resultado = registro != null ? registro : new MatriculacionMasivaRegistroDTO();
			try {
				String estadoValidacion = validarEntrada(resultado);
				if (estadoValidacion != null) {
					resultado.setEstado(estadoValidacion);
					respuesta.add(resultado);
					continue;
				}
				
				Long idPersona = personaService.obtenerIdPersonaPorMatricula(resultado.getUsername()).orElse(null);
				if (idPersona == null) {
					resultado.setEstado("Usuario no encontrado");
					respuesta.add(resultado);
					continue;
				}
				
				if (!matriculacionMasivaRepository.existeRol(resultado.getRol())) {
					resultado.setEstado("Rol no existe");
					respuesta.add(resultado);
					continue;
				}
				
				TblGrupo grupoEntidad = grupoRepo.findOne(resultado.getIdGrupo().intValue());
				if (grupoEntidad == null) {
					resultado.setEstado("Grupo no existe");
					respuesta.add(resultado);
					continue;
				}
				
				if (grupoParticipanteRepo.existeParticipanteEnGrupo(grupoEntidad.getIdGrupo(), idPersona)) {
					resultado.setEstado("Ya inscrito en el grupo");
					respuesta.add(resultado);
					continue;
				}
				
				registrarRolSiNoExiste(idPersona, resultado.getRol(), idUsuario);
				
				PersonaDTO persona = personaService.buscarPorId(idPersona);
				EventoCapacitacionDTO evento = construirEvento(grupoEntidad.getEvento());
				GrupoDTO grupo = construirGrupo(grupoEntidad, idUsuario, evento);
				ParametroWSMoodleDTO plataforma = obtenerPlataforma(evento.getIdPlataformaLmsBorrador());
				
				ResultadoDTO<RelGrupoParticipanteDTO> resultadoRegistro = grupoParticipanteService
						.almacenarParticipantes(Collections.singletonList(persona), grupo, evento, plataforma);
				
				if (resultadoRegistro != null && resultadoRegistro.esCorrecto()) {
					resultado.setEstado("Matriculado");
				} else {
					resultado.setEstado(obtenerMensajeError(resultadoRegistro));
				}
			} catch (Exception ex) {
				logger.error("Error al procesar registro de matrícula masiva", ex);
				resultado.setEstado("Error al matricular");
			}
			respuesta.add(resultado);
		}
		
		return respuesta;
	}
	
	private String validarEntrada(MatriculacionMasivaRegistroDTO registro) {
		if (registro == null) {
			return "Registro vacío";
		}
		if (registro.getIdGrupo() == null) {
			return "id_grupo vacío";
		}
		if (registro.getUsername() == null || registro.getUsername().trim().isEmpty()) {
			return "username vacío";
		}
		if (registro.getRol() == null) {
			return "rol vacío";
		}
		return null;
	}
	
	private EventoCapacitacionDTO construirEvento(TblEvento eventoEntidad) {
		EventoCapacitacionDTO evento = new EventoCapacitacionDTO();
		if (eventoEntidad != null) {
			evento.setIdEvento(eventoEntidad.getIdEvento());
			evento.setIdCursoLmsBorrador(eventoEntidad.getIdCursoLmsBorrador());
			evento.setIdPlataformaLmsBorrador(eventoEntidad.getIdPlataformaLmsBorrador());
			if (eventoEntidad.getCatModalidadPlanPrograma() != null) {
				evento.getCatModalidadPlanPrograma().setId(eventoEntidad.getCatModalidadPlanPrograma().getId());
			}
		}
		return evento;
	}
	
	private GrupoDTO construirGrupo(TblGrupo grupoEntidad, Long idUsuario, EventoCapacitacionDTO evento) {
		GrupoDTO grupo = new GrupoDTO();
		grupo.setIdGrupo(grupoEntidad.getIdGrupo());
		grupo.setNombre(grupoEntidad.getNombre());
		grupo.setClave(grupoEntidad.getClave());
		grupo.setIdMoodle(grupoEntidad.getIdMoodle());
		grupo.setNumMaxAlumnos(grupoEntidad.getNumMaxAlumnos());
		grupo.setUsuarioModifico(idUsuario);
		grupo.setEvento(evento);
		return grupo;
	}
	
	private ParametroWSMoodleDTO obtenerPlataforma(Integer idPlataforma) {
		if (idPlataforma == null) {
			return new ParametroWSMoodleDTO();
		}
		try {
			ParametroWSMoodleDTO plataforma = parametroWSMoodleService.buscarPorId(idPlataforma);
			return plataforma != null ? plataforma : new ParametroWSMoodleDTO();
		} catch (Exception ex) {
			logger.error("No fue posible recuperar la plataforma Moodle " + idPlataforma, ex);
			return new ParametroWSMoodleDTO();
		}
	}
	
	private String obtenerMensajeError(ResultadoDTO<RelGrupoParticipanteDTO> resultado) {
		if (resultado == null) {
			return "Error al matricular";
		}
		if (ResultadoTransaccionEnum.EXITOSO.equals(resultado.getResultado())) {
			return "Matriculado";
		}
		if (resultado.getMensajes() != null && !resultado.getMensajes().isEmpty()) {
			return String.join("; ", resultado.getMensajes());
		}
		return "Error al matricular";
	}
	
	private void registrarRolSiNoExiste(Long idPersona, Integer idRol, Long idUsuario) {
		if (idPersona == null || idRol == null) {
			return;
		}
		try {
			if (personaRolesRepo.existeRelacionPersonaRol(idPersona, idRol)) {
				return;
			}
			TblPersona persona = new TblPersona();
			persona.setIdPersona(idPersona);
			CatRol rol = new CatRol();
			rol.setIdRol(idRol);
			RelPersonaRol relacion = new RelPersonaRol(rol, persona, idUsuario != null ? idUsuario : 0L);
			personaRolesRepo.save(relacion);
		} catch (Exception ex) {
			logger.error(String.format("No fue posible asignar el rol %d a la persona %d", idRol, idPersona), ex);
		}
	}
}
