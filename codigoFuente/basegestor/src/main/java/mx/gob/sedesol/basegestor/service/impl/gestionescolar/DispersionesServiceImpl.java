package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CrearEventoDispersionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DispersionCreacionResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DispersionGrupoEventoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DispersionMatriculaExistenteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DispersionMatriculacionResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GrupoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.MatricularDispersionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.admin.CatParametroWSMoodle;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatEstadoAva;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelUnidadOaAva;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblAmbienteVirtualAprendizaje;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatCategoriaEventoCapacitacion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatDestinatariosEc;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatEstadoEventoCapacitacion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionPreEvento;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Dispersiones;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParam;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.DispersionesParamNuevo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.InscripcionPlanesProgramas;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.ProcesosInscripcion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblGrupo;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TipoMatriculacion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatModalidadPlanPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblDispersionesBusqueda;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.RelProgramaCompEspecifica;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.IDispersionesRepository;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.RelProgCompEspecificaRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.RelUnidadOaAvaRepo;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.RelEstructuraUDidacticaRepo;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.basegestor.service.gestionescolar.DispersionesService;
import mx.gob.sedesol.basegestor.service.gestionescolar.EventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoParticipanteService;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Grupo;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Curso;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Cursos;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Seccion;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.CrearGrupo;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.CursoWS;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.SeccionWS;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.RelEstructuraUnidadDidactica;

@Service("dispersionesService")
public class DispersionesServiceImpl implements DispersionesService {

private static final Logger logger = Logger.getLogger(DispersionesServiceImpl.class);
	private static final int TIPO_MATRICULACION_EVENTO_POR_GRUPO = 1;
	private static final int ID_PROCESO_INSCRIPCION_ORDINARIO = 1;
	private static final int TIPO_PROCESO_EXTRAORDINARIO = 2;

	@Autowired
	private IDispersionesRepository iDispersionesRepository;
	
	@Autowired
	private ParametroWSMoodleService parametroWSMoodleService;
	
	@Autowired
	private EventoCapacitacionService eventoCapacitacionService;
	
	@Autowired
	private GrupoParticipanteService grupoParticipanteService;
	
	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private RelEstructuraUDidacticaRepo relEstructuraUDidacticaRepo;
	
	@Autowired
	private RelProgCompEspecificaRepo relProgCompEspecificaRepo;
	
	@Autowired
	private RelUnidadOaAvaRepo relUnidadOaAvaRepo;

	@Override
	public void altaDisperciones(DispersionesParam dispercionParametros) {

		iDispersionesRepository.altaDisperciones(dispercionParametros);

	}

	@Override
	public List<TblDispersionesBusqueda> consultaDisperciones(DispersionesParam dispercionParametros) {

		List<TblDispersionesBusqueda> lista = iDispersionesRepository.consultaDisperciones(dispercionParametros);

		if (lista.isEmpty()) {
			return lista;
		}
		return lista;

	}

	@Override
	public void borrarDispercsion(TblDispersionesBusqueda tblBusqueda) {
		iDispersionesRepository.borrarDispercsion(tblBusqueda);
	}

	@Override
	public boolean existeRelDispersionGrupo(Integer idDispersion) {
		return iDispersionesRepository.existeRelDispersionGrupo(idDispersion);
	}

	@Override
	public List<TblDispersionesBusqueda> actualizarDispersion(DispersionesParamNuevo dispercionParametros) {

		List<TblDispersionesBusqueda> lista = iDispersionesRepository.actualizarDispersion(dispercionParametros);

		if (lista.isEmpty()) {
			return lista;
		}
		return lista;

	}
	
	@Override
	public boolean actualizarDispersionExc(DispersionesParamNuevo dispercionParametros){
		 return iDispersionesRepository.actualizarDispersionExc(dispercionParametros);

	}
	
	@Override
	public boolean existeCambioNoEstudiantes(Integer idDispersion) {
		return iDispersionesRepository.existeCambioNoEstudiantes(idDispersion);
	}

	@Override
	public boolean validarDispercionExistente(DispersionesParam dispercionParametros) {
		return iDispersionesRepository.validarDispercionExistente(dispercionParametros);
	}

	@Override
	public boolean validarDispercionExistenteOrdinario(DispersionesParam dispercionParametros) {
		return iDispersionesRepository.validarDispercionExistenteOrdinario(dispercionParametros);
	}

	@Override
	public List<ProcesosInscripcion> consultarProcesoInscripcion(DispersionesParam dispercionParametros) {

		List<ProcesosInscripcion> lista = iDispersionesRepository.consultarProcesoInscripcion(dispercionParametros);

		if (lista.isEmpty()) {
			return new ArrayList<ProcesosInscripcion>();
		}
		return lista;
	}

	@Override
	public List<TblPlan> consultarPlan(DispersionesParam dispercionParametros) {

		List<TblPlan> lista = iDispersionesRepository.consultarPlan(dispercionParametros);

		if (lista.isEmpty()) {
			return new ArrayList<TblPlan>();
		}
		return lista;
	}

	@Override
	public List<TblFichaDescriptivaPrograma> consultarPrograma(DispersionesParam dispercionParametros) {
		List<TblFichaDescriptivaPrograma> lista = iDispersionesRepository.consultarPrograma(dispercionParametros);

		if (lista.isEmpty()) {
			return new ArrayList<TblFichaDescriptivaPrograma>();
		}
		return lista;
	}

	@Override
	public List<TipoMatriculacion> consultarTipoMatriculacion() {

		List<TipoMatriculacion> lista = iDispersionesRepository.consultarTipoMatriculacion();

		if (lista.isEmpty()) {
			return new ArrayList<TipoMatriculacion>();
		}
		return lista;
	}
	
	@Override
	public List<InscripcionPlanesProgramas> consultarPlanesProgramas(DispersionesParam dispercionParametros) {
		List<InscripcionPlanesProgramas> lista = iDispersionesRepository.consultarPlanesProgramas(dispercionParametros);
		if (lista.isEmpty()) {
			return new ArrayList<InscripcionPlanesProgramas>();
		}
		return lista;
	}
	
	@Override
	public DispersionPreEvento obtenerDatosPreviosEvento(Integer idPrograma) {
		return iDispersionesRepository.obtenerDatosPreviosEvento(idPrograma);
	}
	
	@Override
	public List<ProcesosInscripcion> consultarProcesosConDispersion() {
		return iDispersionesRepository.consultarProcesosConDispersion();
	}
	
	@Override
	public List<ProcesosInscripcion> consultarProcesosSinDispersion() {
		return iDispersionesRepository.consultarProcesosSinDispersion();
	}

	@Override
	public List<String> consultarProgramasCompartidos(Integer idProcesoInscripcion) {
		List<String> lista = iDispersionesRepository.consultarProgramasCompartidos(idProcesoInscripcion);
		return lista != null ? lista : new ArrayList<String>();
	}

	@Override
	public List<TblDispersionesBusqueda> consultarDispersionesGruposCompartidos(Integer idProcesoInscripcion,
			String nombreProgramaSeleccionado) {
		List<TblDispersionesBusqueda> lista = iDispersionesRepository
				.consultarDispersionesGruposCompartidos(idProcesoInscripcion, nombreProgramaSeleccionado);
		return lista != null ? lista : new ArrayList<TblDispersionesBusqueda>();
	}
	
	@Override
	public List<DispersionMatriculaExistenteDTO> consultarDispersionesExistentes(Integer idProcesoConDispersion,
			Integer idProcesoMatricular) {
		return iDispersionesRepository.consultarDispersionesExistentes(idProcesoConDispersion, idProcesoMatricular);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<DispersionMatriculacionResultadoDTO> matricularDispersionExistente(DispersionMatriculaExistenteDTO dispersion, Long idUsuario) {
		ResultadoDTO<DispersionMatriculacionResultadoDTO> resultado = new ResultadoDTO<>();
		resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
		DispersionMatriculacionResultadoDTO detalle = new DispersionMatriculacionResultadoDTO();
		resultado.setDto(detalle);
		
		if (dispersion == null || dispersion.getIdDispersion() == null
				|| dispersion.getIdProcesoInscripcionMatricular() == null || dispersion.getIdPrograma() == null) {
			resultado.agregaMensaje("La información para matricular es incompleta.");
			return resultado;
		}
		
		List<DispersionGrupoEventoDTO> grupos = iDispersionesRepository.obtenerRelacionesDispersion(dispersion.getIdDispersion());
		if (grupos == null || grupos.isEmpty()) {
			resultado.agregaMensaje("No se encontraron grupos asociados a la dispersión seleccionada.");
			return resultado;
		}
		
		Integer idNuevaDispersion = iDispersionesRepository.crearDispersionBasica(
				dispersion.getIdProcesoInscripcionMatricular(),
				dispersion.getIdPrograma(),
				dispersion.getNoEstudiantesInscritos(),
				0,
				idUsuario);
		if (idNuevaDispersion == null) {
			resultado.agregaMensaje("No fue posible registrar la dispersión de destino.");
			return resultado;
		}
		
		for (DispersionGrupoEventoDTO grupo : grupos) {
			iDispersionesRepository.guardarRelacionDispersionGrupo(idNuevaDispersion, grupo.getIdEvento(),
					grupo.getIdGrupo(), idUsuario);
		}
		
		List<Long> personas = iDispersionesRepository.obtenerPersonasMatriculaExistente(
				dispersion.getIdProcesoInscripcionMatricular(), dispersion.getIdPrograma());
		if (personas == null || personas.isEmpty()) {
			resultado.agregaMensaje("No se encontraron inscripciones para matricular.");
			return resultado;
		}
		
		// Ordenamos grupos por id para aplicar recorrido de mayor a menor y viceversa
		grupos.sort((a, b) -> a.getIdGrupo().compareTo(b.getIdGrupo()));
		List<GrupoAsignacion> asignaciones = construirAsignaciones(grupos);
		for (GrupoAsignacion asignacion : asignaciones) {
			if (asignacion != null) {
				asignacion.permitirCapacidadIlimitada();
			}
		}
		RecorridoAlternado recorrido = new RecorridoAlternado(asignaciones.size());
		Map<Long, PersonaDTO> cachePersonas = new HashMap<>();
		for (Long idPersona : personas) {
			PersonaDTO persona = obtenerPersonaPorId(idPersona, cachePersonas);
			if (persona == null) {
				continue;
			}
			Integer indice = recorrido.siguienteIndice();
			if (indice == null || indice >= asignaciones.size()) {
				continue;
			}
			asignaciones.get(indice).asignar(persona);
		}
		
		int matriculados = 0;
		int gruposProcesados = 0;
		Map<Integer, ParametroWSMoodleDTO> cachePlataformas = new HashMap<>();
		for (GrupoAsignacion asignacion : asignaciones) {
			if (asignacion.personas.isEmpty()) {
				continue;
			}
			ResultadoDTO<RelGrupoParticipanteDTO> respuesta = registrarParticipantes(asignacion, idUsuario,
					cachePlataformas);
			copiarMensajes(respuesta, resultado);
			if (!respuesta.esCorrecto()) {
				throw new RuntimeException("No fue posible matricular el grupo " + asignacion.info.getIdGrupo());
			}
			matriculados += asignacion.personas.size();
			gruposProcesados++;
		}
		
		detalle.setGruposProcesados(gruposProcesados);
		detalle.setParticipantesDetectados(personas.size());
		detalle.setParticipantesMatriculados(matriculados);
		detalle.setParticipantesSinCupo(0);
		
		if (matriculados > 0) {
			resultado.setResultado(ResultadoTransaccionEnum.EXITOSO);
			resultado.agregaMensaje(String.format("Se han matriculado %d alumno(s) en %d grupo(s).", matriculados,
					gruposProcesados));
		} else {
			resultado.agregaMensaje("No se asignaron participantes a los grupos seleccionados.");
		}
		
		return resultado;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<DispersionCreacionResultadoDTO> crearEventosDispersion(CrearEventoDispersionDTO solicitud) {
		ResultadoDTO<DispersionCreacionResultadoDTO> resultado = new ResultadoDTO<>();
		resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
		DispersionCreacionResultadoDTO detalle = new DispersionCreacionResultadoDTO();
		resultado.setDto(detalle);
		
		if (solicitud == null || solicitud.getIdDispersion() == null || solicitud.getIdPrograma() == null) {
			resultado.agregaMensaje("La información de la dispersión es incompleta.");
			return resultado;
		}
		
		if (solicitud.getIdUsuario() == null) {
			resultado.agregaMensaje("No se pudo identificar al usuario que ejecuta la acción.");
			return resultado;
		}
		
		if (solicitud.getIdPlataformaLms() == null || solicitud.getIdClasificacionAva() == null) {
			resultado.agregaMensaje("Debe vincular un AVA seleccionando la plataforma Moodle y la clasificación correspondiente.");
			return resultado;
		}
		
		int generales = Math.max(0, defaultValue(solicitud.getGruposGenerales()));
		int resto = Math.max(0, defaultValue(solicitud.getGruposResto()));
		int totalGrupos = generales + resto;
		if (totalGrupos <= 0) {
			resultado.agregaMensaje("La dispersión seleccionada no tiene grupos configurados.");
			return resultado;
		}
		
		boolean multiEvento = esTipoEventosPorGrupo(solicitud.getTipoMatriculacion());
		int eventosObjetivo = multiEvento ? totalGrupos : 1;
		int gruposPorEvento = multiEvento ? 1 : totalGrupos;
		List<RelEstructuraUnidadDidactica> estructurasPrograma = obtenerEstructurasPrograma(solicitud.getIdPrograma());
		int numeroUnidadesCurso = !estructurasPrograma.isEmpty() ? estructurasPrograma.size() : Math.max(1, gruposPorEvento);
		
		int eventosCreados = 0;
		int gruposCreados = 0;
		CapacidadContext capacidad = new CapacidadContext(generales, resto, solicitud);
		
		for (int eventoIndex = 1; eventoIndex <= eventosObjetivo; eventoIndex++) {
			TblEvento evento = construirEvento(solicitud, eventoIndex, gruposPorEvento);
			evento = iDispersionesRepository.guardarEventoDispersion(evento);
			
			Integer idCursoLms;
			try {
				idCursoLms = crearCursoEnMoodle(solicitud, evento, numeroUnidadesCurso);
			} catch (Exception ex) {
				logger.error("No fue posible crear o reutilizar el curso en Moodle para el evento "
						+ evento.getIdEvento(), ex);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultado.agregaMensaje(obtenerMensajeCreacionMoodle(ex,
						"No fue posible crear el grupo porque ocurrió un error al crear el curso en Moodle."));
				return resultado;
			}
			if (idCursoLms != null) {
				evento.setIdCursoLmsBorrador(idCursoLms);
				iDispersionesRepository.actualizarEvento(evento);
			}
			eventosCreados++;
			
			TblAmbienteVirtualAprendizaje ava = null;
			if (Boolean.TRUE.equals(solicitud.getVincularAva()) && solicitud.getIdPlataformaLms() != null) {
				ava = crearAmbienteVirtual(solicitud, evento, idCursoLms);
			}
			
			aprovisionarCursoEnMoodle(solicitud, evento, idCursoLms, ava, estructurasPrograma);
			
			for (int grupo = 1; grupo <= gruposPorEvento; grupo++) {
				int capacidadGrupo = capacidad.obtenerCapacidad();
				if (capacidadGrupo <= 0) {
					capacidadGrupo = solicitud.getCupoGeneral() != null ? solicitud.getCupoGeneral() : 0;
				}
				String nombreGrupo = construirNombreGrupo(solicitud, evento, gruposCreados + 1);
				Integer idGrupoMoodle;
				try {
					idGrupoMoodle = crearGrupoEnMoodle(solicitud, evento, nombreGrupo);
				} catch (Exception ex) {
					logger.error("No fue posible crear el grupo en Moodle para el evento " + evento.getIdEvento(), ex);
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					resultado.agregaMensaje(obtenerMensajeCreacionMoodle(ex,
							"No fue posible crear el grupo porque ocurrió un error al registrar el grupo en Moodle."));
					return resultado;
				}
				if (idGrupoMoodle == null) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					resultado.agregaMensaje(
							"No fue posible crear el grupo porque Moodle no devolvió un identificador válido.");
					return resultado;
				}
				TblGrupo grupoCreado = construirGrupo(evento, nombreGrupo, capacidadGrupo, solicitud.getIdUsuario());
				grupoCreado.setIdMoodle(idGrupoMoodle);
				grupoCreado = iDispersionesRepository.guardarGrupoDispersion(grupoCreado);
				iDispersionesRepository.guardarRelacionDispersionGrupo(solicitud.getIdDispersion(),
						evento.getIdEvento(), grupoCreado.getIdGrupo(), solicitud.getIdUsuario());
				gruposCreados++;
				if (multiEvento) {
					break;
				}
			}
		}
		
		detalle.setEventosCreados(eventosCreados);
		detalle.setGruposCreados(gruposCreados);
		resultado.setResultado(ResultadoTransaccionEnum.EXITOSO);
		resultado.agregaMensaje(String.format("Se han generado %d evento(s) y %d grupo(s) satisfactoriamente.",
				eventosCreados, gruposCreados));
		return resultado;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<DispersionMatriculacionResultadoDTO> matricularUsuariosDispersion(MatricularDispersionDTO solicitud) {
		ResultadoDTO<DispersionMatriculacionResultadoDTO> resultado = new ResultadoDTO<>();
		resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
		DispersionMatriculacionResultadoDTO detalle = new DispersionMatriculacionResultadoDTO();
		resultado.setDto(detalle);
		
		if (solicitud == null || solicitud.getIdDispersion() == null || solicitud.getIdUsuario() == null
				|| solicitud.getIdConvocatoria() == null || solicitud.getIdTipoProceso() == null
				|| solicitud.getIdProcesoInscripcion() == null) {
			resultado.agregaMensaje("La información para matricular usuarios es incompleta.");
			return resultado;
		}
		
		boolean matriculacionExtraordinariaSinGrupos = esMatriculacionExtraordinariaSinGrupos(solicitud);
		List<DispersionGrupoEventoDTO> grupos = iDispersionesRepository.obtenerRelacionesDispersion(solicitud.getIdDispersion());
		if (grupos.isEmpty() && matriculacionExtraordinariaSinGrupos && solicitud.getIdPrograma() != null) {
			grupos = iDispersionesRepository.obtenerGruposOrdinariosPorPrograma(solicitud.getIdPrograma(),
					ID_PROCESO_INSCRIPCION_ORDINARIO);
			if (!grupos.isEmpty() && !iDispersionesRepository.existeRelDispersionGrupo(solicitud.getIdDispersion())) {
				registrarGruposExtraordinarios(solicitud.getIdDispersion(), grupos, solicitud.getIdUsuario());
			}
		}
		if (grupos.isEmpty()) {
			resultado.agregaMensaje("La dispersión seleccionada no cuenta con eventos y grupos registrados.");
			return resultado;
		}
		
		List<Long> personas;
		if (Boolean.TRUE.equals(solicitud.getMatriculacionCompartidos())) {
			personas = iDispersionesRepository.obtenerPersonasMatriculacionCompartidos(
					solicitud.getIdDispersion(),
					solicitud.getIdProcesoInscripcion(),
					solicitud.getIdPrograma(),
					solicitud.getNombreProgramaSeleccionado(),
					solicitud.getBloquePrograma());
		} else {
			personas = iDispersionesRepository.obtenerPersonasMatriculacion(solicitud.getIdDispersion(),
					solicitud.getIdConvocatoria(), solicitud.getIdTipoProceso(), solicitud.getIdProcesoInscripcion());
		}
		if (personas.isEmpty()) {
			resultado.agregaMensaje("No se encontraron inscripciones para matricular en los grupos de la dispersión.");
			resultado.setResultado(ResultadoTransaccionEnum.EXITOSO);
			return resultado;
		}
		
		List<GrupoAsignacion> asignaciones = construirAsignaciones(grupos);
		if (matriculacionExtraordinariaSinGrupos) {
			for (GrupoAsignacion asignacion : asignaciones) {
				if (asignacion != null) {
					asignacion.permitirCapacidadIlimitada();
				}
			}
		}
		Map<Long, PersonaDTO> cachePersonas = new HashMap<>();
		int indiceGrupo = 0;
		int sinCupo = 0;
		int omitidos = 0;
		RecorridoAlternado recorrido = matriculacionExtraordinariaSinGrupos
				? new RecorridoAlternado(asignaciones.size())
				: null;
		
		for (Long idPersona : personas) {
			PersonaDTO persona = obtenerPersonaPorId(idPersona, cachePersonas);
			if (persona == null) {
				omitidos++;
				continue;
			}
			if (matriculacionExtraordinariaSinGrupos) {
				if (!hayCapacidadDisponible(asignaciones)) {
					sinCupo++;
					continue;
				}
				GrupoAsignacion grupo = seleccionarGrupoAlternando(asignaciones, recorrido);
				if (grupo == null) {
					sinCupo++;
					continue;
				}
				grupo.asignar(persona);
				continue;
			}
			while (indiceGrupo < asignaciones.size() && asignaciones.get(indiceGrupo).sinCapacidad()) {
				indiceGrupo++;
			}
			if (indiceGrupo >= asignaciones.size()) {
				sinCupo++;
				continue;
			}
			asignaciones.get(indiceGrupo).asignar(persona);
		}
		
		int matriculados = 0;
		int gruposProcesados = 0;
		Map<Integer, ParametroWSMoodleDTO> cachePlataformas = new HashMap<>();
		for (GrupoAsignacion asignacion : asignaciones) {
			if (asignacion.personas.isEmpty()) {
				continue;
			}
			ResultadoDTO<RelGrupoParticipanteDTO> respuesta = registrarParticipantes(asignacion, solicitud.getIdUsuario(),
					cachePlataformas);
				if (!respuesta.esCorrecto()) {
					copiarMensajes(respuesta, resultado);
					throw new RuntimeException("No fue posible matricular el grupo " + asignacion.info.getIdGrupo());
				}
				copiarMensajes(respuesta, resultado);
				matriculados += asignacion.personas.size();
				gruposProcesados++;
			}
		
		detalle.setGruposProcesados(gruposProcesados);
		detalle.setParticipantesDetectados(personas.size() - omitidos);
		detalle.setParticipantesMatriculados(matriculados);
		detalle.setParticipantesSinCupo(sinCupo);
		
		if (matriculados > 0) {
			resultado.setResultado(ResultadoTransaccionEnum.EXITOSO);
			resultado.agregaMensaje(String.format("Se han matriculado %d alumno(s) en %d grupo(s).", matriculados,
					gruposProcesados));
			agregarDetalleDistribucion(asignaciones, resultado);
			int pendientes = detalle.getParticipantesDetectados() - matriculados;
			if (pendientes > 0) {
				resultado.agregaMensaje(String.format("%d alumno(s) quedaron pendientes de asignación por falta de cupo.",
						pendientes));
			}
			if (sinCupo > 0) {
				resultado.agregaMensaje(
						String.format("%d alumno(s) no pudieron asignarse porque todos los grupos alcanzaron su cupo.",
								sinCupo));
			}
		} else {
			resultado.agregaMensaje("No se asignaron participantes a los grupos de la dispersión.");
		}
		
		return resultado;
	}
	
	private void copiarMensajes(ResultadoDTO<?> origen, ResultadoDTO<?> destino) {
		if (origen == null || destino == null || origen.getMensajes() == null) {
			return;
		}
		for (String mensaje : origen.getMensajes()) {
			destino.agregaMensaje(mensaje);
		}
	}
	
	private void agregarDetalleDistribucion(List<GrupoAsignacion> asignaciones, ResultadoDTO<?> resultado) {
		Map<Integer, Integer> gruposPorTamano = new HashMap<>();
		for (GrupoAsignacion asignacion : asignaciones) {
			if (asignacion.personas.isEmpty()) {
				continue;
			}
			int alumnos = asignacion.personas.size();
			gruposPorTamano.merge(alumnos, 1, Integer::sum);
		}
		if (gruposPorTamano.isEmpty()) {
			return;
		}
		StringBuilder detalle = new StringBuilder("Detalle por grupo: ");
		int indice = 0;
		for (Map.Entry<Integer, Integer> entry : gruposPorTamano.entrySet()) {
			if (indice++ > 0) {
				detalle.append(", ");
			}
			detalle.append(String.format("%d grupo(s) de %d alumno(s)", entry.getValue(), entry.getKey()));
		}
		resultado.agregaMensaje(detalle.toString());
	}
	
	private boolean esTipoEventosPorGrupo(Integer tipoMatriculacion) {
		return tipoMatriculacion != null && tipoMatriculacion.intValue() == TIPO_MATRICULACION_EVENTO_POR_GRUPO;
	}
	
	private int defaultValue(Integer valor) {
		return valor != null ? valor.intValue() : 0;
	}
	
	private TblEvento construirEvento(CrearEventoDispersionDTO solicitud, int consecutivo, int gruposPorEvento) {
		TblEvento evento = new TblEvento();
		evento.setIdPrograma(solicitud.getIdPrograma());
		evento.setNombreEc(construirNombreEvento(solicitud, consecutivo));
		evento.setCveEventoCap(construirClaveEvento(solicitud, consecutivo));
		evento.setFechaInicial(solicitud.getFechaInicial());
		evento.setFechaFinal(solicitud.getFechaFinal());
		evento.setObjetivoGeneralEc(solicitud.getObjetivosGenerales());
		evento.setPerfilEc(solicitud.getPerfilEgreso());
		evento.setRequisitosEc(solicitud.getRequisitosIngreso());
		evento.setConstancia(Boolean.TRUE);
		evento.setCalificacionMinAprobatoria(solicitud.getCalificacionMinAprobatoria());
		evento.setUsuarioModifico(solicitud.getIdUsuario());
		evento.setIdDirigido(1);
		evento.setIdClasificacionAva(solicitud.getIdClasificacionAva());
		evento.setIdEventoCapacitacionAnterior(null);
		evento.setIdPlataformaLmsBorrador(solicitud.getIdPlataformaLms());
		evento.setAlcanceEc(null);
		evento.setNoRegistroEc(null);
		evento.setIdEntidadFederativa(null);
		evento.setIdMunicipio(null);
		evento.setIdProgramaSocial(null);
		evento.setOportunidadesEvaluacion(null);
		evento.setValorAsistencia(100);
		evento.setValorRetardo(80);
		evento.setValorFaltaJustificada(80);
		evento.setValorFalta(0);
		evento.setValorInconcluso(0);
		evento.setTpoCalificacion(1);
		evento.setTpoDictamen(1);
		evento.setValorCalificacionDictamen(100);
		evento.setValorAsistenciaDictamen(0);
		evento.setProcentajeMinAsistencia(80);
		evento.setPrivado(Boolean.TRUE.equals(solicitud.getPrivado()));
		evento.setAplicaEncuesta(Boolean.FALSE);
		evento.setVersionEncuesta(null);
		evento.setUrlImagen(null);
		evento.setCategoriaEC(crearCategoriaEvento());
		evento.setCatEstadoEventoCapacitacion(crearEstadoEvento(solicitud.getIdEstatusEvento()));
		evento.setCatModalidadPlanPrograma(crearModalidad(solicitud.getIdModalidad()));
		evento.setIdDestinatario(crearDestinatario());
		return evento;
	}
	
	private CatCategoriaEventoCapacitacion crearCategoriaEvento() {
		CatCategoriaEventoCapacitacion cat = new CatCategoriaEventoCapacitacion();
		cat.setId(1);
		return cat;
	}
	
	private CatEstadoEventoCapacitacion crearEstadoEvento(Integer id) {
		if (id == null) {
			return null;
		}
		CatEstadoEventoCapacitacion estado = new CatEstadoEventoCapacitacion();
		estado.setId(id);
		return estado;
	}
	
	private CatModalidadPlanPrograma crearModalidad(Integer idModalidad) {
		if (idModalidad == null) {
			return null;
		}
		CatModalidadPlanPrograma modalidad = new CatModalidadPlanPrograma();
		modalidad.setId(idModalidad);
		return modalidad;
	}
	
	private CatDestinatariosEc crearDestinatario() {
		CatDestinatariosEc destinatario = new CatDestinatariosEc();
		destinatario.setId(1);
		return destinatario;
	}
	
	private String construirNombreEvento(CrearEventoDispersionDTO solicitud, int consecutivo) {
		String base = solicitud.getNombrePrograma();
		if (base == null || base.trim().isEmpty()) {
			base = solicitud.getNombreEventoBase();
		}
		if (base == null) {
			base = "Evento";
		}
		return String.format("%s | %03d", base.trim(), consecutivo);
	}
	
	private String construirClaveEvento(CrearEventoDispersionDTO solicitud, int consecutivo) {
		StringBuilder sb = new StringBuilder();
		if (solicitud.getClaveParaEvento() != null) {
			sb.append(solicitud.getClaveParaEvento());
		}
		if (solicitud.getAnioPeriodo() != null) {
			sb.append("-").append(solicitud.getAnioPeriodo());
		}
		if (solicitud.getNumeroElementos() != null) {
			sb.append("-").append(solicitud.getNumeroElementos());
		}
		return sb.toString();
	}
	
	private Integer crearCursoEnMoodle(CrearEventoDispersionDTO solicitud, TblEvento evento, int numeroUnidades)
			throws Exception {
		if (eventoCapacitacionService == null || solicitud.getIdPlataformaLms() == null
				|| solicitud.getIdClasificacionAva() == null) {
			return null;
		}
		try {
			int unidades = 1;
			try {
				unidades = solicitud.getNumeroElementos() != null
						? Integer.parseInt(solicitud.getNumeroElementos())
						: numeroUnidades;
			} catch (NumberFormatException ex) {
				unidades = numeroUnidades;
			}
			Integer cursoExistente = buscarCursoExistenteMoodle(evento);
			if (cursoExistente != null) {
				return cursoExistente;
			}
			return eventoCapacitacionService.obtenerIdMoodle(evento, unidades);
		} catch (Exception ex) {
			Integer cursoExistente = buscarCursoExistenteMoodle(evento);
			if (cursoExistente != null) {
				logger.warn("Se reutilizará el curso existente en Moodle para el evento " + evento.getIdEvento()
						+ " tras detectar una colisión durante la creación.");
				return cursoExistente;
			}
			throw ex;
		}
	}
	
	private TblAmbienteVirtualAprendizaje crearAmbienteVirtual(CrearEventoDispersionDTO solicitud, TblEvento evento, Integer idCursoLms) {
		TblAmbienteVirtualAprendizaje ava = new TblAmbienteVirtualAprendizaje();
		ava.setActivo(Boolean.TRUE);
		ava.setUsuarioModifico(solicitud.getIdUsuario());
		ava.setCatEstadoAva(crearEstadoAva());
		ava.setEventoCapacitacion(evento);
		if (solicitud.getIdPlataformaLms() != null) {
			CatParametroWSMoodle plataforma = new CatParametroWSMoodle();
			plataforma.setIdParametroWSMoodle(solicitud.getIdPlataformaLms());
			ava.setPlataformaMoodle(plataforma);
			ava.setUrlLms(construirUrlLms(solicitud.getIdPlataformaLms(), idCursoLms));
		}
		ava.setIdCursoLms(idCursoLms);
		ava.setPorcentajeAvance(Byte.valueOf((byte) 0));
		ava.setValidacionAva(Boolean.FALSE);
		ava.setEsAvaArchivado(Boolean.FALSE);
		ava.setAutonomo(Boolean.TRUE.equals(solicitud.getEventoAutonomo()) ? 1 : 0);
		return iDispersionesRepository.guardarAmbienteVirtual(ava);
	}
	
	private void aprovisionarCursoEnMoodle(CrearEventoDispersionDTO solicitud, TblEvento evento, Integer idCursoLms,
			TblAmbienteVirtualAprendizaje ava, List<RelEstructuraUnidadDidactica> estructurasPrograma) {
		if (idCursoLms == null || solicitud.getIdPlataformaLms() == null || !esModalidadEnLinea(evento)) {
			return;
		}
		try {
			ParametroWSMoodleDTO plataforma = parametroWSMoodleService.buscarPorId(solicitud.getIdPlataformaLms());
			if (plataforma == null) {
				return;
			}
			List<RelEstructuraUnidadDidactica> estructuras = estructurasPrograma;
			if (estructuras == null || estructuras.isEmpty()) {
				estructuras = obtenerEstructurasPrograma(evento.getIdPrograma());
			}
			guardarUnidadesAva(ava, estructuras, solicitud.getIdUsuario());
			List<Seccion> secciones = construirSecciones(estructuras, idCursoLms);
			if (!secciones.isEmpty()) {
				SeccionWS seccionWS = new SeccionWS(plataforma);
				seccionWS.actualizarSeccion(secciones);
			}
			registrarCompetenciasCurso(plataforma, evento.getIdPrograma(), idCursoLms);
		} catch (Exception ex) {
			logger.error("No fue posible aprovisionar el curso en Moodle para el evento " + evento.getIdEvento(), ex);
		}
	}
	
	private List<RelEstructuraUnidadDidactica> obtenerEstructurasPrograma(Integer idPrograma) {
		if (idPrograma == null) {
			return new ArrayList<>();
		}
		List<RelEstructuraUnidadDidactica> estructuras = relEstructuraUDidacticaRepo
				.obtieneRelEstUnidadDidPorPrograma(idPrograma);
		return obtenerListUnidDidactSinRepetir(estructuras);
	}
	
	private List<RelEstructuraUnidadDidactica> obtenerListUnidDidactSinRepetir(
			List<RelEstructuraUnidadDidactica> relEstUnidadDidactica) {
		if (relEstUnidadDidactica == null) {
			return new ArrayList<>();
		}
		Set<Integer> idsUnidadesDidacticas = new HashSet<>();
		return relEstUnidadDidactica.stream()
				.filter(e -> idsUnidadesDidacticas.add(e.getIdUnidadDidactica()))
				.collect(Collectors.toList());
	}
	
	private List<Seccion> construirSecciones(List<RelEstructuraUnidadDidactica> estructuras, Integer idCursoLms) {
		List<Seccion> secciones = new ArrayList<>();
		if (estructuras == null || estructuras.isEmpty() || idCursoLms == null) {
			return secciones;
		}
		int secuencia = 1;
		for (RelEstructuraUnidadDidactica estructura : estructuras) {
			secciones.add(generarSeccion(estructura, idCursoLms, secuencia));
			secuencia++;
		}
		return secciones;
	}
	
	private Seccion generarSeccion(RelEstructuraUnidadDidactica estructura, Integer idCursoLms, int secuencia) {
		Seccion seccion = new Seccion();
		seccion.setId(secuencia);
		seccion.setCourse(idCursoLms);
		if (estructura != null && estructura.getDetEstUnidadDidactica() != null) {
			seccion.setName(estructura.getDetEstUnidadDidactica().getNombreUnidad());
			seccion.setSummary(estructura.getDetEstUnidadDidactica().getObjetivosEspecificos());
		}
		return seccion;
	}
	
	private void registrarCompetenciasCurso(ParametroWSMoodleDTO plataforma, Integer idPrograma, Integer idCursoLms) {
		if (plataforma == null || idPrograma == null || idCursoLms == null) {
			return;
		}
		try {
			List<RelProgramaCompEspecifica> comps = relProgCompEspecificaRepo.obtenerCompEspPorIdPrograma(idPrograma);
			if (comps == null) {
				return;
			}
			List<String> competencias = comps.stream()
					.filter(rel -> rel.getCatCompetenciaEspecifica() != null)
					.map(rel -> rel.getCatCompetenciaEspecifica().getNombre())
					.collect(Collectors.toList());
			if (competencias.isEmpty()) {
				return;
			}
			CursoWS cursoWS = new CursoWS(plataforma);
			cursoWS.registrarCompetencias(idCursoLms, competencias);
		} catch (Exception ex) {
			logger.error("No fue posible registrar competencias en Moodle para el curso " + idCursoLms, ex);
		}
	}
	
	private void guardarUnidadesAva(TblAmbienteVirtualAprendizaje ava, List<RelEstructuraUnidadDidactica> estructuras,
			Long usuarioModifico) {
		if (ava == null || estructuras == null || estructuras.isEmpty()) {
			return;
		}
		for (RelEstructuraUnidadDidactica estructura : estructuras) {
			RelUnidadOaAva unidad = new RelUnidadOaAva();
			unidad.setAmbienteVirtualAprendizaje(ava);
			unidad.setDetEstUnidadDidactica(estructura.getDetEstUnidadDidactica());
			unidad.setUsuarioModifico(usuarioModifico);
			unidad.setPorcentajeAvanceOa(Byte.valueOf((byte) 0));
			relUnidadOaAvaRepo.save(unidad);
		}
	}
	
	private boolean esModalidadEnLinea(TblEvento evento) {
		if (evento == null || evento.getCatModalidadPlanPrograma() == null
				|| evento.getCatModalidadPlanPrograma().getId() == null) {
			return false;
		}
		Integer modalidad = evento.getCatModalidadPlanPrograma().getId();
		return modalidad.equals(ConstantesGestor.MODALIDAD_LINEA) || modalidad.equals(ConstantesGestor.MODALIDAD_MIXTO);
	}
	
	private CatEstadoAva crearEstadoAva() {
		CatEstadoAva estado = new CatEstadoAva();
		estado.setId(3);
		return estado;
	}
	
	private String construirUrlLms(Integer idParametro, Integer idCurso) {
		if (idParametro == null || idCurso == null || parametroWSMoodleService == null) {
			return null;
		}
		try {
			ParametroWSMoodleDTO parametro = parametroWSMoodleService.buscarPorId(idParametro);
			if (parametro != null && parametro.getHost() != null) {
				return parametro.getHost().concat("/course/view.php?id=").concat(String.valueOf(idCurso));
			}
		} catch (Exception ex) {
			logger.error("No fue posible construir la URL del LMS", ex);
		}
		return null;
	}
	
	private TblGrupo construirGrupo(TblEvento evento, String nombreGrupo, Integer capacidad, Long usuario) {
		TblGrupo grupo = new TblGrupo();
		grupo.setEvento(evento);
		grupo.setNombre(nombreGrupo);
		grupo.setClave(null);
		grupo.setDescripcion(null);
		grupo.setFachaActualizacion(new Date());
		grupo.setUsuarioModifico(usuario);
		grupo.setNumMaxAlumnos(capacidad);
		grupo.setActaCerrada(false);
		return grupo;
	}
	
	private Integer crearGrupoEnMoodle(CrearEventoDispersionDTO solicitud, TblEvento evento, String nombreGrupo)
			throws Exception {
		if (evento == null || evento.getIdCursoLmsBorrador() == null || solicitud.getIdPlataformaLms() == null) {
			return null;
		}
		ParametroWSMoodleDTO plataforma = parametroWSMoodleService.buscarPorId(solicitud.getIdPlataformaLms());
		if (plataforma == null) {
			return null;
		}
		CrearGrupo ws = new CrearGrupo(plataforma);
		Grupo grupo = new Grupo();
		grupo.setCourseid(evento.getIdCursoLmsBorrador());
		grupo.setName(nombreGrupo);
		List<Grupo> creados = ws.crearGrupos(Collections.singletonList(grupo));
		if (creados != null && !creados.isEmpty()) {
			int idMoodle = creados.get(0).getId();
			return idMoodle > 0 ? idMoodle : null;
		}
		return null;
	}

	private Integer buscarCursoExistenteMoodle(TblEvento evento) {
		if (evento == null || evento.getIdEvento() == null || evento.getIdPlataformaLmsBorrador() == null) {
			return null;
		}
		try {
			ParametroWSMoodleDTO plataforma = parametroWSMoodleService.buscarPorId(evento.getIdPlataformaLmsBorrador());
			if (plataforma == null) {
				return null;
			}
			CursoWS cursoWS = new CursoWS(plataforma);
			String shortnameEsperado = "EC-" + evento.getIdEvento();
			String idnumberEsperado = String.valueOf(evento.getIdEvento());
			Cursos cursos = cursoWS.verificarExisteCurso("search", shortnameEsperado);
			if (cursos == null || cursos.getCourses() == null) {
				return null;
			}
			for (Curso curso : cursos.getCourses()) {
				if (curso == null) {
					continue;
				}
				boolean coincideShortname = shortnameEsperado.equals(curso.getShortname());
				boolean coincideIdnumber = idnumberEsperado.equals(curso.getIdnumber());
				if ((coincideShortname || coincideIdnumber) && curso.getId() > 0) {
					return curso.getId();
				}
			}
		} catch (Exception ex) {
			logger.warn("No fue posible verificar si el curso ya existe en Moodle para el evento "
					+ evento.getIdEvento(), ex);
		}
		return null;
	}

	private String obtenerMensajeCreacionMoodle(Exception ex, String mensajeDefault) {
		if (ex != null && ex.getMessage() != null && !ex.getMessage().trim().isEmpty()) {
			return ex.getMessage();
		}
		return mensajeDefault;
	}
	
	private String construirNombreGrupo(CrearEventoDispersionDTO dto, TblEvento evento, int consecutivo) {
		String consecutivoTexto = String.format("%03d", consecutivo);
		if (Boolean.TRUE.equals(dto.getGenerarPorClave())) {
			String base = primerValorNoVacio(dto.getClaveParaGrupo(), dto.getNombreGrupoBase(), "GRP");
			String periodo = obtenerUltimosDosDigitos(dto.getAnioPeriodo());
			String elementos = formatearNumeroElementos(dto.getNumeroElementos());
			String bloque = dto.getBloquePrograma();
			
			StringBuilder nombre = new StringBuilder(base);
			if (!esVacio(periodo) || !esVacio(elementos)) {
				nombre.append("-");
				nombre.append(!esVacio(periodo) ? periodo : "00");
				nombre.append(!esVacio(elementos) ? elementos : "00");
			}
			if (!esVacio(bloque)) {
				nombre.append("-");
				nombre.append(bloque);
			}
			nombre.append("-");
			nombre.append(consecutivoTexto);
			return nombre.toString();
		}
		return String.format("EC-%s-G-%s", evento.getIdEvento(), consecutivoTexto);
	}
	
	private String primerValorNoVacio(String... valores) {
		if (valores == null) {
			return null;
		}
		for (String valor : valores) {
			if (!esVacio(valor)) {
				return valor.trim();
			}
		}
		return null;
	}
	
	private boolean esVacio(String valor) {
		return valor == null || valor.trim().isEmpty();
	}
	
	private String obtenerUltimosDosDigitos(String valor) {
		if (esVacio(valor)) {
			return null;
		}
		String soloDigitos = valor.replaceAll("\\D", "");
		if (soloDigitos.isEmpty()) {
			return null;
		}
		return soloDigitos.length() <= 2 ? soloDigitos : soloDigitos.substring(soloDigitos.length() - 2);
	}
	
	private String formatearNumeroElementos(String numero) {
		if (esVacio(numero)) {
			return null;
		}
		try {
			int valor = Integer.parseInt(numero);
			return String.format("%02d", Math.max(0, valor));
		} catch (NumberFormatException ex) {
			return null;
		}
	}
	
	private List<GrupoAsignacion> construirAsignaciones(List<DispersionGrupoEventoDTO> grupos) {
		List<GrupoAsignacion> asignaciones = new ArrayList<>();
		for (DispersionGrupoEventoDTO info : grupos) {
			asignaciones.add(new GrupoAsignacion(info));
		}
		return asignaciones;
	}
	
	private PersonaDTO obtenerPersonaPorId(Long idPersona, Map<Long, PersonaDTO> cachePersonas) {
		if (idPersona == null) {
			return null;
		}
		if (cachePersonas.containsKey(idPersona)) {
			return cachePersonas.get(idPersona);
		}
		try {
			PersonaDTO persona = personaService.buscarPorId(idPersona);
			cachePersonas.put(idPersona, persona);
			return persona;
		} catch (Exception ex) {
			logger.error("No fue posible recuperar la información de la persona " + idPersona, ex);
			cachePersonas.put(idPersona, null);
			return null;
		}
	}
	
	private ResultadoDTO<RelGrupoParticipanteDTO> registrarParticipantes(GrupoAsignacion asignacion, Long idUsuario,
			Map<Integer, ParametroWSMoodleDTO> cachePlataformas) {
		GrupoDTO grupo = construirGrupoDto(asignacion.info, idUsuario);
		ParametroWSMoodleDTO plataforma = obtenerParametroPlataforma(asignacion.info.getIdPlataformaLms(), cachePlataformas);
		return grupoParticipanteService.almacenarParticipantes(asignacion.personas, grupo, grupo.getEvento(), plataforma);
	}
	
	private GrupoDTO construirGrupoDto(DispersionGrupoEventoDTO info, Long usuario) {
		GrupoDTO grupo = new GrupoDTO();
		grupo.setIdGrupo(info.getIdGrupo());
		grupo.setNombre(info.getNombreGrupo());
		grupo.setClave(info.getNombreGrupo());
		grupo.setIdMoodle(info.getIdGrupoMoodle());
		grupo.setNumMaxAlumnos(info.getCapacidadTotal());
		grupo.setUsuarioModifico(usuario);
		grupo.setEvento(construirEventoMatriculacion(info));
		return grupo;
	}
	
	private EventoCapacitacionDTO construirEventoMatriculacion(DispersionGrupoEventoDTO info) {
		EventoCapacitacionDTO evento = new EventoCapacitacionDTO();
		evento.setIdEvento(info.getIdEvento());
		evento.setIdCursoLmsBorrador(info.getIdCursoLms());
		evento.setIdPlataformaLmsBorrador(info.getIdPlataformaLms());
		if (info.getIdModalidad() != null) {
			evento.getCatModalidadPlanPrograma().setId(info.getIdModalidad());
		}
		return evento;
	}
	
	private ParametroWSMoodleDTO obtenerParametroPlataforma(Integer idPlataforma,
			Map<Integer, ParametroWSMoodleDTO> cachePlataformas) {
		if (idPlataforma == null) {
			return new ParametroWSMoodleDTO();
		}
		if (cachePlataformas.containsKey(idPlataforma)) {
			return cachePlataformas.get(idPlataforma);
		}
		ParametroWSMoodleDTO parametro = parametroWSMoodleService.buscarPorId(idPlataforma);
		if (parametro == null) {
			parametro = new ParametroWSMoodleDTO();
		}
		cachePlataformas.put(idPlataforma, parametro);
		return parametro;
	}
	
	private void registrarGruposExtraordinarios(Integer idDispersion, List<DispersionGrupoEventoDTO> grupos,
			Long idUsuario) {
		if (idDispersion == null || grupos == null || grupos.isEmpty()) {
			return;
		}
		for (DispersionGrupoEventoDTO grupo : grupos) {
			if (grupo == null || grupo.getIdEvento() == null || grupo.getIdGrupo() == null) {
				continue;
			}
			try {
				iDispersionesRepository.guardarRelacionDispersionGrupo(idDispersion, grupo.getIdEvento(),
						grupo.getIdGrupo(), idUsuario);
			} catch (Exception ex) {
				logger.warn(String.format(
						"No fue posible vincular el grupo %d del evento %d a la dispersión extraordinaria %d.",
						grupo.getIdGrupo(), grupo.getIdEvento(), idDispersion), ex);
			}
		}
	}
	
	private GrupoAsignacion seleccionarGrupoAlternando(List<GrupoAsignacion> asignaciones,
			RecorridoAlternado recorrido) {
		if (recorrido == null || asignaciones == null || asignaciones.isEmpty()) {
			return null;
		}
		int totalGrupos = asignaciones.size();
		for (int intentos = 0; intentos < totalGrupos; intentos++) {
			Integer indice = recorrido.siguienteIndice();
			if (indice == null) {
				return null;
			}
			if (indice < 0 || indice >= totalGrupos) {
				continue;
			}
			GrupoAsignacion grupo = asignaciones.get(indice);
			if (!grupo.sinCapacidad()) {
				return grupo;
			}
		}
		return null;
	}
	
	private boolean hayCapacidadDisponible(List<GrupoAsignacion> asignaciones) {
		if (asignaciones == null || asignaciones.isEmpty()) {
			return false;
		}
		for (GrupoAsignacion asignacion : asignaciones) {
			if (asignacion != null && !asignacion.sinCapacidad()) {
				return true;
			}
		}
		return false;
	}
	
	private boolean esMatriculacionExtraordinariaSinGrupos(MatricularDispersionDTO solicitud) {
		return solicitud != null && esProcesoExtraordinario(solicitud)
				&& defaultValue(solicitud.getGruposGenerales()) == 0 && defaultValue(solicitud.getGrupoResto()) == 0;
	}
	
	private boolean esProcesoExtraordinario(MatricularDispersionDTO solicitud) {
		return solicitud != null && solicitud.getIdTipoProceso() != null
				&& solicitud.getIdTipoProceso().intValue() == TIPO_PROCESO_EXTRAORDINARIO;
	}
	
	private static class RecorridoAlternado {
		private final List<Integer> ordenDesc;
		private final List<Integer> ordenAsc;
		private boolean cicloDescendente = true;
		private int posicion = 0;
		
		RecorridoAlternado(int totalGrupos) {
			ordenDesc = new ArrayList<>();
			ordenAsc = new ArrayList<>();
			for (int i = totalGrupos - 1; i >= 0; i--) {
				ordenDesc.add(i);
			}
			for (int i = 0; i < totalGrupos; i++) {
				ordenAsc.add(i);
			}
		}
		
		Integer siguienteIndice() {
			List<Integer> ordenActual = cicloDescendente ? ordenDesc : ordenAsc;
			if (ordenActual.isEmpty()) {
				return null;
			}
			if (posicion >= ordenActual.size()) {
				if (cicloDescendente) {
					cicloDescendente = false;
				}
				posicion = 0;
				ordenActual = cicloDescendente ? ordenDesc : ordenAsc;
				if (ordenActual.isEmpty()) {
					return null;
				}
			}
			Integer indice = ordenActual.get(posicion);
			posicion++;
			return indice;
		}
	}
	
	private static class GrupoAsignacion {
		private final DispersionGrupoEventoDTO info;
		private final List<PersonaDTO> personas = new ArrayList<>();
		private int capacidadDisponible;
		
		GrupoAsignacion(DispersionGrupoEventoDTO info) {
			this.info = info;
			int total = info.getCapacidadTotal() != null ? info.getCapacidadTotal() : 0;
			int ocupados = info.getInscritosActuales() != null ? info.getInscritosActuales() : 0;
			this.capacidadDisponible = Math.max(0, total - ocupados);
		}
		
		boolean sinCapacidad() {
			return capacidadDisponible <= 0;
		}
		
		void asignar(PersonaDTO persona) {
			personas.add(persona);
			capacidadDisponible--;
		}
		
		void permitirCapacidadIlimitada() {
			this.capacidadDisponible = Integer.MAX_VALUE;
		}
	}
	
	private static class CapacidadContext {
		private int generalesRestantes;
		private int restoRestante;
		private final CrearEventoDispersionDTO solicitud;
		
		CapacidadContext(int generales, int resto, CrearEventoDispersionDTO solicitud) {
			this.generalesRestantes = generales;
			this.restoRestante = resto;
			this.solicitud = solicitud;
		}
		
		int obtenerCapacidad() {
			if (generalesRestantes > 0) {
				generalesRestantes--;
				return solicitud.getCupoGeneral() != null ? solicitud.getCupoGeneral() : 0;
			}
			if (restoRestante > 0) {
				restoRestante--;
				return solicitud.getCupoResto() != null ? solicitud.getCupoResto() : 0;
			}
			return solicitud.getCupoGeneral() != null ? solicitud.getCupoGeneral() : 0;
		}
	}

	@Override
	public List<Dispersiones> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dispersiones buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultadoDTO<Dispersiones> guardar(Dispersiones dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultadoDTO<Dispersiones> actualizar(Dispersiones dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultadoDTO<Dispersiones> eliminar(Dispersiones dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultadoDTO<Dispersiones> sonDatosRequeridosValidos(TipoAccion accion, Dispersiones dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
