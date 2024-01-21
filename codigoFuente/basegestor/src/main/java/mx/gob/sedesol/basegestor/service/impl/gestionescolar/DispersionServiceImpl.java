package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TblInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.TblInscripcionResumenDTO;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblInscripcion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblInscripcionResumen;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.GrupoParticipanteRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.GrupoRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.InscripcionRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.InscripcionResumenRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.RelProgCompEspecificaRepo;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.RelPersonaPlataformaMoodleService;
import mx.gob.sedesol.basegestor.service.badges.BadgeService;
import mx.gob.sedesol.basegestor.service.badges.ClasificacionBadgeService;
import mx.gob.sedesol.basegestor.service.encuestas.RelEncuestaUsuarioService;
import mx.gob.sedesol.basegestor.service.gestionescolar.DispersionService;

@Service("dispersionServiceImpl")
public class DispersionServiceImpl 
//extends ComunValidacionService<RelGrupoParticipanteDTO>
		implements DispersionService {

	private static final Logger logger = Logger.getLogger(DispersionServiceImpl.class);

	@Autowired
	private InscripcionResumenRepo inscripcionResumenRepo;
	
	@Autowired
	private InscripcionRepo inscripcionRepo;
	
	@Autowired
	private GrupoParticipanteRepo grupoParticipanteRepo;

	@Autowired
	private GrupoRepo grupoRepo;

	@Autowired
	private RelPersonaPlataformaMoodleService relPersonaPlataformaMoodleService;

	@Autowired
	private ParametroWSMoodleService parametroWSMoodleService;

	@Autowired
	private RelProgCompEspecificaRepo relProgCompEspecificaRepo;

	private ModelMapper modelMapper = new ModelMapper();

	@Autowired
	private RelEncuestaUsuarioService relEncuestaUsuarioService;

	@Autowired
	private BadgeService badgeService;

	@Autowired
	private ClasificacionBadgeService clasificacionBadgeService;

	@Autowired
	private ParametroSistemaService parametroSistemaService;

	@PostConstruct
	public void init() {

		PropertyMap<EventoCapacitacionDTO, TblEvento> tblEventoMap = new PropertyMap<EventoCapacitacionDTO, TblEvento>() {
			protected void configure() {
				skip().setCategoriaEC(null);
				skip().setCatEstadoEventoCapacitacion(null);
				skip().setFichaDescriptivaPrograma(null);
				skip().setAmbienteVirtualAprendizajes(null);
				skip().setReponsableProduccionEcs(null);
				skip().setCatModalidadPlanPrograma(null);
			}
		};
		modelMapper.addMappings(tblEventoMap);
	}


	@Override
	public List<TblInscripcionResumenDTO> getInscripcionResumenByProgramaEducativo(List<String> programasEducativos) {
		List<TblInscripcionResumenDTO> listDTO;

		List<TblInscripcionResumen> lista = inscripcionResumenRepo.getInscripcionResumenByProgramaEducativo(programasEducativos);

		Type objetoDTO = new TypeToken<List<TblInscripcionResumenDTO>>() {
		}.getType();

		listDTO = modelMapper.map(lista, objetoDTO);

		return listDTO;
	}
	
	@Override
	public List<TblInscripcionDTO> getInscripcionesByIdPlan(Integer idPlan) {
		List<TblInscripcionDTO> listDTO;

		List<TblInscripcion> lista = inscripcionRepo.getInscripcionesByIdPlan(idPlan);

		Type objetoDTO = new TypeToken<List<TblInscripcionDTO>>() {
		}.getType();

		listDTO = modelMapper.map(lista, objetoDTO);

		return listDTO;
	}
	
	@Override
	public List<TblInscripcionDTO> getInscripcionesByProgramasEducativos(List<String> programas) {
		List<TblInscripcionDTO> listDTO;

		List<TblInscripcion> lista = inscripcionRepo.getInscripcionesByProgramasEducativos(programas);

		Type objetoDTO = new TypeToken<List<TblInscripcionDTO>>() {
		}.getType();

		listDTO = modelMapper.map(lista, objetoDTO);

		return listDTO;
	}
	
	@Override
	public List<TblInscripcionDTO> getInscripcionesByIdPlanes(List<Integer> idPlanes) {
		List<TblInscripcionDTO> listDTO;

		List<TblInscripcion> lista = inscripcionRepo.getInscripcionesByIdPlanes(idPlanes);

		Type objetoDTO = new TypeToken<List<TblInscripcionDTO>>() {
		}.getType();

		listDTO = modelMapper.map(lista, objetoDTO);

		return listDTO;
	}
	
	
	
	/*
	@Override
	public void validarPersistencia(RelGrupoParticipanteDTO dto, ResultadoDTO<RelGrupoParticipanteDTO> resultado) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void validarActualizacion(RelGrupoParticipanteDTO dto, ResultadoDTO<RelGrupoParticipanteDTO> resultado) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void validarEliminacion(RelGrupoParticipanteDTO dto, ResultadoDTO<RelGrupoParticipanteDTO> resultado) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	/*
	@Override
	public List<RelGrupoParticipanteDTO> buscarAlumno(Integer idGrupo, String nombre) {
		List<RelGrupoParticipanteDTO> listDTO;

		List<RelGrupoParticipante> lista = grupoParticipanteRepo.getParticipantesByNombre(idGrupo, nombre);

		Type objetoDTO = new TypeToken<List<RelGrupoParticipanteDTO>>() {
		}.getType();

		listDTO = modelMapper.map(lista, objetoDTO);

		return listDTO;
	}

	@Override
	public List<RelGrupoParticipanteDTO> buscarParticipanteEnEvento(Integer idEvento, Long idPersona) {
		List<RelGrupoParticipanteDTO> listDTO;

		List<RelGrupoParticipante> lista = grupoParticipanteRepo.buscarParticipanteEnEvento(idEvento, idPersona);

		Type objetoDTO = new TypeToken<List<RelGrupoParticipanteDTO>>() {
		}.getType();

		listDTO = modelMapper.map(lista, objetoDTO);

		return listDTO;
	}

	@Override
	public List<RelGrupoParticipanteDTO> getParticipantesByGrupo(Integer idGrupo) {
		List<RelGrupoParticipanteDTO> listaDTO;

		List<RelGrupoParticipante> lista = grupoParticipanteRepo.getParticipantesByGrupo(idGrupo);

		Type objetoDTO = new TypeToken<List<RelGrupoParticipanteDTO>>() {
		}.getType();

		listaDTO = modelMapper.map(lista, objetoDTO);

		
		    //Se carga un consecutivo a la lista de participantes
		  
		  
		for (int x = 0; x < listaDTO.size(); x++) {
			RelGrupoParticipanteDTO grupoParticiDTO = listaDTO.get(x);
			grupoParticiDTO.setSecuencia(x + 1);
		}

		return listaDTO;

	}

	@Override
	public List<RelGrupoParticipanteDTO> getParticipantesByEvento(Integer idEvento) {
		List<RelGrupoParticipanteDTO> listaDTO;

		List<RelGrupoParticipante> lista = grupoParticipanteRepo.getParticipantesByEvento(idEvento);

		Type objetoDTO = new TypeToken<List<RelGrupoParticipanteDTO>>() {
		}.getType();

		listaDTO = modelMapper.map(lista, objetoDTO);
		 
		 * Se carga un consecutivo a la lista de participantes
		 
		for (int x = 0; x < listaDTO.size(); x++) {
			RelGrupoParticipanteDTO grupoParticiDTO = listaDTO.get(x);
			grupoParticiDTO.setSecuencia(x + 1);
		}
		return listaDTO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RelGrupoParticipanteDTO almacenarParticipante(GrupoDTO grupo, PersonaDTO persona,
			EventoCapacitacionDTO evento, ParametroWSMoodleDTO parametroWSMoodleDTO) {
		Integer idPersonaLms;

		if (evento.getCatModalidadPlanPrograma().getId() == ConstantesGestor.MODALIDAD_LINEA
				|| evento.getCatModalidadPlanPrograma().getId() == ConstantesGestor.MODALIDAD_MIXTO) {
			idPersonaLms = relPersonaPlataformaMoodleService.obtenerIdMoodle(persona,
					evento.getIdPlataformaLmsBorrador(), grupo.getUsuarioModifico());

			if (ObjectUtils.isNullOrCero(idPersonaLms)) {
				throw new NullPointerException();
			} else {
				RolesWS rolesWS = new RolesWS(parametroWSMoodleDTO);
				UsuariosGrupoWS usuariosGrupoWS = new UsuariosGrupoWS(parametroWSMoodleDTO);

				List<Enrol> listaEnrol = generarEnrol(evento, idPersonaLms);

				List<AlumnoGrupo> listaAlumnosGrupo = generarAlumnosGrupo(grupo.getIdMoodle(), idPersonaLms);

				try {
					rolesWS.enrolarEstudiantes(listaEnrol);
					usuariosGrupoWS.agregarUsuariosGrupo(listaAlumnosGrupo);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		} else {
			idPersonaLms = null;
		}
		RelGrupoParticipante grupoParticipante = new RelGrupoParticipante();
		grupoParticipante.setGrupo(modelMapper.map(grupo, TblGrupo.class));
		grupoParticipante.setPersona(modelMapper.map(persona, TblPersona.class));
		grupoParticipante.setIdPersonaLms(idPersonaLms);
		grupoParticipante.setFechaRegistro(new Date());
		grupoParticipante.setUsuarioModifico(grupo.getUsuarioModifico());

		grupoParticipanteRepo.save(grupoParticipante);

		grupoRepo.save(modelMapper.map(grupo, TblGrupo.class));

		RelGrupoParticipanteDTO dto = modelMapper.map(grupoParticipante, RelGrupoParticipanteDTO.class);

		return dto;
	}

	@Override
	public ResultadoDTO<RelGrupoParticipanteDTO> almacenarParticipantes(List<PersonaDTO> listaPersonas, GrupoDTO grupo,
			EventoCapacitacionDTO evento, ParametroWSMoodleDTO parametroWSMoodleDTO) {

		ResultadoDTO<RelGrupoParticipanteDTO> resultado;

		if (evento.getCatModalidadPlanPrograma().getId() == ConstantesGestor.MODALIDAD_LINEA
				|| evento.getCatModalidadPlanPrograma().getId() == ConstantesGestor.MODALIDAD_MIXTO) {
			resultado = almacenarAlumnosMoodle(listaPersonas, grupo, evento, parametroWSMoodleDTO);
		} else {
			resultado = almacenarAlumnosNormales(listaPersonas, grupo);
		}
		return resultado;
	}

	private ResultadoDTO<RelGrupoParticipanteDTO> almacenarAlumnosNormales(List<PersonaDTO> listaPersonas,
			GrupoDTO grupo) {
		ResultadoDTO<RelGrupoParticipanteDTO> resultado = new ResultadoDTO<>();
		for (PersonaDTO persona : listaPersonas) {
			RelGrupoParticipante grupoParticipante = new RelGrupoParticipante();
			grupoParticipante.setGrupo(modelMapper.map(grupo, TblGrupo.class));
			grupoParticipante.setPersona(modelMapper.map(persona, TblPersona.class));
			grupoParticipante.setFechaRegistro(new Date());
			grupoParticipante.setUsuarioModifico(grupo.getUsuarioModifico());
			RelGrupoParticipanteDTO dto = modelMapper.map(grupoParticipante, RelGrupoParticipanteDTO.class);

			grupoParticipanteRepo.save(grupoParticipante);
		}
		resultado.agregaMensaje(MensajesSistemaEnum.EC_MATRICULACION_GRUPAL_EXITO.getId() + listaPersonas.size());
		return resultado;
	}

	private ResultadoDTO<RelGrupoParticipanteDTO> almacenarAlumnosMoodle(List<PersonaDTO> listaPersonas, GrupoDTO grupo,
			EventoCapacitacionDTO evento, ParametroWSMoodleDTO parametroWSMoodleDTO) {
		ResultadoDTO<RelGrupoParticipanteDTO> resultado = new ResultadoDTO<>();
		List<Enrol> listaEnrol = new ArrayList<>();
		List<AlumnoGrupo> listaAlumnosGrupo = new ArrayList<>();
		List<RelGrupoParticipante> personasCorrectas = new ArrayList<>();
		for (PersonaDTO persona : listaPersonas) {
			Integer idPersonaMoodle = relPersonaPlataformaMoodleService.obtenerIdMoodle(persona, parametroWSMoodleDTO,
					grupo.getUsuarioModifico());
			if (ObjectUtils.isNullOrCero(idPersonaMoodle)) {
				resultado.agregaMensaje(
						MensajesSistemaEnum.EC_REGISTRO_MOODLE_ERROR.getId() + persona.getNombreCompleto());
			} else {
				personasCorrectas.add(generarGrupoParticipante(persona, grupo, idPersonaMoodle));
				Enrol enrol = new Enrol();
				enrol.setUserId(idPersonaMoodle);
				enrol.setCourseId(evento.getIdCursoLmsBorrador());
				enrol.setTimestart((new Date()).getTime());
				listaEnrol.add(enrol);
				AlumnoGrupo alumnoGrupo = new AlumnoGrupo();
				alumnoGrupo.setGroupid(grupo.getIdMoodle());
				alumnoGrupo.setUserid(idPersonaMoodle);
				listaAlumnosGrupo.add(alumnoGrupo);
			}
		}
		if (personasCorrectas.isEmpty()) {
			resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
		} else {
			if (almacenarAlumnosMoodle(listaEnrol, listaAlumnosGrupo, parametroWSMoodleDTO)) {
				for (RelGrupoParticipante grupoParticipante : personasCorrectas) {
					grupoParticipanteRepo.save(grupoParticipante);
					RelGrupoParticipanteDTO dto = modelMapper.map(grupoParticipante, RelGrupoParticipanteDTO.class);

				}
				grupoRepo.save(modelMapper.map(grupo, TblGrupo.class));
				resultado.agregaMensaje(
						MensajesSistemaEnum.EC_MATRICULACION_GRUPAL_EXITO.getId() + personasCorrectas.size());
			} else {
				resultado.setMensajeError(MensajesSistemaEnum.EC_MATRICULACION_ERROR);
			}
		}
		return resultado;
	}

	private RelGrupoParticipante generarGrupoParticipante(PersonaDTO persona, GrupoDTO grupo, Integer idPersonaLms) {
		RelGrupoParticipante grupoParticipante = new RelGrupoParticipante();
		grupoParticipante.setGrupo(modelMapper.map(grupo, TblGrupo.class));
		grupoParticipante.setPersona(modelMapper.map(persona, TblPersona.class));
		grupoParticipante.setFechaRegistro(new Date());
		grupoParticipante.setUsuarioModifico(grupo.getUsuarioModifico());
		grupoParticipante.setIdPersonaLms(idPersonaLms);
		return grupoParticipante;
	}

	private boolean almacenarAlumnosMoodle(List<Enrol> listaEnrol, List<AlumnoGrupo> listaAlumnosGrupo,
			ParametroWSMoodleDTO parametroWSMoodleDTO) {
		boolean correcto = true;
		try {
			RolesWS rolesWS = new RolesWS(parametroWSMoodleDTO);
			UsuariosGrupoWS usuariosGrupoWS = new UsuariosGrupoWS(parametroWSMoodleDTO);

			rolesWS.enrolarEstudiantes(listaEnrol);
			usuariosGrupoWS.agregarUsuariosGrupo(listaAlumnosGrupo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			correcto = false;
		}
		return correcto;
	}

	private List<AlumnoGrupo> generarAlumnosGrupo(Integer idGrupoMoodle, Integer idPersonaMoodle) {
		List<AlumnoGrupo> listaAlumnosGrupo = new ArrayList<>();
		AlumnoGrupo alumnoGrupo = new AlumnoGrupo();
		alumnoGrupo.setGroupid(idGrupoMoodle);
		alumnoGrupo.setUserid(idPersonaMoodle);
		listaAlumnosGrupo.add(alumnoGrupo);
		return listaAlumnosGrupo;
	}

	private List<Enrol> generarEnrol(EventoCapacitacionDTO evento, Integer idPersonaMoodle) {
		List<Enrol> listaEnrol = new ArrayList<>();
		Enrol enrol = new Enrol();
		enrol.setUserId(idPersonaMoodle);
		enrol.setCourseId(evento.getIdCursoLmsBorrador());
		enrol.setTimestart((new Date()).getTime());
		listaEnrol.add(enrol);
		return listaEnrol;
	}

	*/
/*
	/**
	 * Metodo que obtiene los participantes de cada evento de capacitacion
	 * realizando un agrupado.s
	 *
	 * @param idEventosList
	 * @return
	 */
	/*public Map<String, Long> obtenerParticipantesPorIdEventoCapacitacion(List<Integer> idEventosList) {
		logger.info("obtenerParticipantesPorIdEventoCapacitacion: El id de los Eventos a agrupar son " + idEventosList);
		List<GroupByGestionEscolarDTO> commonGroupByList = this.grupoParticipanteRepo
				.obtenerParticipantesPorEventosAgrupados(idEventosList);

		Map<String, Long> mapEventoNumeroParticipante = new HashMap<String, Long>();

		logger.info("EventoCapacitacionDTO:obtenerEventosPorEstatusAgrupados");
		for (GroupByGestionEscolarDTO groupByGestionEscolarDTO : commonGroupByList) {
			logger.info("llave: " + groupByGestionEscolarDTO.getValor() + " valor: "
					+ groupByGestionEscolarDTO.getLlavePrimaria());
			mapEventoNumeroParticipante.put(groupByGestionEscolarDTO.getLlavePrimaria(),
					Long.valueOf(groupByGestionEscolarDTO.getValor()));
		}

		return mapEventoNumeroParticipante;

	}

	

	public Map<String, Map<String, String>> obtenerGeneroParticipantesPorIdEventoCapacitacion(
			List<Integer> idEventosList) {
		logger.info("obtenerGeneroParticipantesPorIdEventoCapacitacion: El id de los Eventos a agrupar son "
				+ idEventosList);
		String idEventoCapacitacionTemp = "-1";
		List<GroupByGestionEscolarDTO> groupByGestionEscolarDTOList = grupoParticipanteRepo
				.obtenerGeneroParticipantesPorIdEventoCapacitacion(idEventosList);
		Map<String, Map<String, String>> mapEventos = new HashMap<String, Map<String, String>>();

		Map<String, String> mapGeneroParticipantes = null;

		for (GroupByGestionEscolarDTO groupByGestionEscolarDTO : groupByGestionEscolarDTOList) {

			if (idEventoCapacitacionTemp.equals(groupByGestionEscolarDTO.getLlavePrimaria())) {

				mapGeneroParticipantes.put(groupByGestionEscolarDTO.getLlaveSecundaria(),
						groupByGestionEscolarDTO.getValor());

			} else {

				mapGeneroParticipantes = new HashMap<String, String>();
				mapGeneroParticipantes.put(groupByGestionEscolarDTO.getLlaveSecundaria(),
						groupByGestionEscolarDTO.getValor());
				mapEventos.put(groupByGestionEscolarDTO.getLlavePrimaria(), mapGeneroParticipantes);

			}

			 
			 * Anade el id de el Evento a el Mapa *
			 
			idEventoCapacitacionTemp = groupByGestionEscolarDTO.getLlavePrimaria();

		}

		return mapEventos;
	}

	public List<RelGrupoParticipanteDTO> obtenerEventosCapacitacionPorIdParticipante(Long idParticipante,
			Integer idEstatusEc) {

		List<RelGrupoParticipante> relGrupoParticipanteList = grupoParticipanteRepo
				.obtenerEventosCapacitacionPorIdParticipante(idParticipante, idEstatusEc);

		Type objetoDTO = new TypeToken<List<RelGrupoParticipanteDTO>>() {
		}.getType();

		return modelMapper.map(relGrupoParticipanteList, objetoDTO);
	}

	public List<RelGrupoParticipanteDTO> obtenEvtsEnLineayMixtosPorIdParticipante(Long idParticipante,
			Integer idEstatusEc) {

		List<RelGrupoParticipante> relGrupoParticipanteList = grupoParticipanteRepo
				.obtenEvtsEnLineayMixtosPorIdParticipante(idParticipante, idEstatusEc);

		Type objetoDTO = new TypeToken<List<RelGrupoParticipanteDTO>>() {
		}.getType();

		return modelMapper.map(relGrupoParticipanteList, objetoDTO);
	}

	@Override
	public ResultadoDTO<RelGrupoParticipanteDTO> actualizaRelGrupoParticipante(RelGrupoParticipanteDTO dto) {

		RelGrupoParticipante entity = modelMapper.map(dto, RelGrupoParticipante.class);
		if (ObjectUtils.isNotNull(entity)) {
			entity = grupoParticipanteRepo.saveAndFlush(entity);

			// GUSTAVO --guardarBitacoraGrupoParticipante(dto,
			// String.valueOf(entity.getId()));

		}

		return null;
	}

	@Override
	public List<EventoConstanciaDTO> getParticipanteByActaCerradaYconstancia(Long idPersona) {
		List<RelGrupoParticipante> rgp = grupoParticipanteRepo.getParticipanteByActaCerradaYconstancia(idPersona);
		List<EventoConstanciaDTO> eventos = new ArrayList<>();
		List<RelEncuestaUsuarioDTO> encuestas = new ArrayList<>();
		if (ObjectUtils.isNotNull(rgp)) {
			String nombreDirectorGeneral = parametroSistemaService
					.obtenerParametro(ConstantesGestor.NOMBRE_DIRECTOR_GENERAL);
			String ciudadConstancia = parametroSistemaService.obtenerParametro(ConstantesGestor.CIUDAD_CONSTANCIA);

			for (RelGrupoParticipante gp : rgp) {
				EventoConstanciaDTO evento = new EventoConstanciaDTO();
				Double calificacionTotal = 0.0;
				if (ObjectUtils.isNotNull(gp.getCalifTotal())) {
					calificacionTotal = gp.getCalifTotal().doubleValue() * 10;
				}

				double grupoCalificacionFinal = 0;
				if (ObjectUtils.isNotNull(gp.getCalifFinal())) {
					grupoCalificacionFinal = gp.getCalifFinal().doubleValue() * 10;
				}

				double eventoCalificacionMinima = 0;
				if (ObjectUtils.isNotNull(gp.getGrupo().getEvento())) {
					if (ObjectUtils.isNotNull(gp.getGrupo().getEvento().getCalificacionMinAprobatoria())) {
						String califString = gp.getGrupo().getEvento().getCalificacionMinAprobatoria();
						eventoCalificacionMinima = Double.parseDouble(califString);

						if (grupoCalificacionFinal >= eventoCalificacionMinima) {
							evento.setTipoConstancia(1);

						} else {
							evento.setTipoConstancia(0);

						}

					}
				}

				Integer porcentajeAsistencia = 0;
				if (ObjectUtils.isNotNull(gp.getPorcentajeAsist())) {
					porcentajeAsistencia = gp.getPorcentajeAsist();
				}

				if (ObjectUtils.isNull(evento.getTipoConstancia()) || evento.getTipoConstancia().intValue() != 1) {

					Integer porcentajeAsistenciaMinimo = 0;
					if (ObjectUtils.isNotNull(gp.getGrupo().getEvento())) {
						if (ObjectUtils.isNotNull(gp.getGrupo().getEvento().getProcentajeMinAsistencia())) {
							porcentajeAsistenciaMinimo = gp.getGrupo().getEvento().getProcentajeMinAsistencia();
							if (porcentajeAsistencia.intValue() >= porcentajeAsistenciaMinimo.intValue()) {
								evento.setTipoConstancia(2);
							} else {
								evento.setTipoConstancia(0);
							}
						}
					}
				}

				if (evento.getTipoConstancia() != 0) {
					String nombreEc = gp.getGrupo().getEvento().getNombreEc();
					String modalidad = gp.getGrupo().getEvento().getCatModalidadPlanPrograma().getNombre();
					Date fecha = gp.getGrupo().getEvento().getFechaFinal();

					String nombreAcreditado = gp.getPersona().getNombre() + " " + gp.getPersona().getApellidoPaterno()
							+ " " + gp.getPersona().getApellidoMaterno();
					String calificacion = String.valueOf(grupoCalificacionFinal);
					String nombrePrograma = gp.getGrupo().getEvento().getFichaDescriptivaPrograma()
							.getNombreTentativo();
					String direccion = ciudadConstancia;
					String directorGral = nombreDirectorGeneral;
					String duracionHrs = calcularDuracion(
							gp.getGrupo().getEvento().getFichaDescriptivaPrograma().getRelProgramaDuracion());
					if (gp.getGrupo().getEvento().getAplicaEncuesta().booleanValue() == true) {
						encuestas = relEncuestaUsuarioService.consultarEncuestasAsignadas(
								gp.getGrupo().getEvento().getIdEvento(), gp.getPersona().getIdPersona(), 1, true);
						if (encuestas.isEmpty()) {
							evento.setNombreEstatusEncuesta("Realizada");
						} else {
							evento.setNombreEstatusEncuesta("Pendiente");
						}
					} else {
						evento.setNombreEstatusEncuesta("No aplica");
					}

					evento.setNombreEc(nombreEc);
					evento.setModalidad(modalidad);
					evento.setFecha(fecha);
					evento.setCalificacion(calificacion);
					evento.setNombreAcreditado(nombreAcreditado);
					evento.setNombrePrograma(nombrePrograma);
					evento.setDireccion(direccion);
					evento.setDirectorGral(directorGral);
					evento.setDuracionHrs(duracionHrs);
					evento.setCalificacionTotal(calificacionTotal);
					evento.setPorcentajeAsistencia(porcentajeAsistencia);
					evento.setCalificacionFinal(new Double(grupoCalificacionFinal));

					eventos.add(evento);
				}
			}
		}
		return eventos;
	}

	private String calcularDuracion(List<RelProgramaCargaHoraria> rel) {
		int numHoras = 0;
		int numMinutos = 0;
		for (RelProgramaCargaHoraria duracion : rel) {
			if (!ObjectUtils.isNullOrEmpty(duracion.getHoras())) {
				numHoras += Integer.parseInt(duracion.getHoras());
			}
			if (!ObjectUtils.isNullOrEmpty(duracion.getMinutos())) {
				numMinutos += Integer.parseInt(duracion.getMinutos());
			}
		}
		int minutosReales = numMinutos % 60;
		int horasRestantes = (int) numMinutos / 60;
		int horasReales = numHoras + horasRestantes;
		return String.valueOf(horasReales);
	}

	public RelEncuestaUsuarioService getRelEncuestaUsuarioService() {
		return relEncuestaUsuarioService;
	}

	public void setRelEncuestaUsuarioService(RelEncuestaUsuarioService relEncuestaUsuarioService) {
		this.relEncuestaUsuarioService = relEncuestaUsuarioService;
	}

	public BadgeService getBadgeService() {
		return badgeService;
	}

	public void setBadgeService(BadgeService badgeService) {
		this.badgeService = badgeService;
	}

	public ClasificacionBadgeService getClasificacionBadgeService() {
		return clasificacionBadgeService;
	}

	public void setClasificacionBadgeService(ClasificacionBadgeService clasificacionBadgeService) {
		this.clasificacionBadgeService = clasificacionBadgeService;
	}

	/*
	 * private void guardarBitacoraGrupoParticipante(RelGrupoParticipanteDTO
	 * dto, String registro) { //Usar la clave/id del registro guardado
	 * dto.getBitacoraDTO().setRegistro(registro); //Guarda la bitácora
	 * 
	 * //GUSTAVO --guardarBitacora(dto.getBitacoraDTO()); }
	 */
/*
	
	

	/**
	 * Obtiene los alumnos cuya calificacion final sea igual o mayor a la
	 * establecida en la calificacion minima aprobatoria del evento
	 */
/*	@Override
	public List<RelGrupoParticipanteDTO> obtenerAlumnosQueRecibieronConstPorIdGrupo(Integer idGrupo) throws Exception {

		List<RelGrupoParticipante> listaRgpEntidad = grupoParticipanteRepo
				.getParticipantesByGrupoConActaCerradayConstancia(idGrupo);
		if (!listaRgpEntidad.isEmpty()) {
			Type listaRgpDTO = new TypeToken<List<RelGrupoParticipanteDTO>>() {
			}.getType();
			return filtrarAlumnosPorCalificacionAprobatoria(modelMapper.map(listaRgpEntidad, listaRgpDTO));
		}
		return new ArrayList<>();

	}

	/**
	 * Obtiene los alumnos que recibieron constancia sin importar si fue de
	 * aprobacion o participacion
	 */
	/*@Override
	public List<RelGrupoParticipanteDTO> getAlumnosQueRecibieronConstPorIdGrupo(Integer idGrupo) throws Exception {

		List<RelGrupoParticipante> listaRgpEntidad = grupoParticipanteRepo
				.getParticipantesByGrupoConActaCerradayConstancia(idGrupo);
		if (!listaRgpEntidad.isEmpty()) {
			Type listaRgpDTO = new TypeToken<List<RelGrupoParticipanteDTO>>() {
			}.getType();
			return modelMapper.map(listaRgpEntidad, listaRgpDTO);
		}
		return new ArrayList<>();

	}

	/***
	 * Compara la calificacion final del alumno con la calificacion min
	 * aprobatoria del evento
	 * 
	 * @param rgp
	 * @return
	 */
	/*private List<RelGrupoParticipanteDTO> filtrarAlumnosPorCalificacionAprobatoria(
			List<RelGrupoParticipanteDTO> listrgp) throws Exception {

		List<RelGrupoParticipanteDTO> alumnosQueRecibieronConstancia = new ArrayList<>();
		double eventoCalifMinAprob = Double
				.valueOf(listrgp.get(0).getGrupo().getEvento().getCalificacionMinAprobatoria());
		double alumnoCalifFinal;

		for (RelGrupoParticipanteDTO rgp : listrgp) {
			alumnoCalifFinal = rgp.getCalifFinal() * 10;
			if (alumnoCalifFinal >= eventoCalifMinAprob) {
				alumnosQueRecibieronConstancia.add(rgp);
			}
		}
		return alumnosQueRecibieronConstancia;
	}
*/
}
