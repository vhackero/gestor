package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.CapturaEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PersonaResponsabilidadesDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.PersonalizacionAreaDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelAreaRecursoDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelPersonalizacionRecursoDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RelSolicitudEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.ReservacionEventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.SolicitudReservacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoUsuarioEnum;
import mx.gob.sedesol.basegestor.model.entities.admin.CatParametroWSMoodle;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatClasificacionArchivoOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatEstadoAva;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelReponsableProduccionOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.RelUnidadOaAva;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblAmbienteVirtualAprendizaje;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblCargaArchivosOa;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblFichaDescripcionObjetoAprendizaje;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblFichaDescriptivaObjetoAprendizaje;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblRecursosOa;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelPersonaResponsabilidades;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelReponsableProduccionEc;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.RelEstructuraUnidadDidactica;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.RelProgramaCompEspecifica;
import mx.gob.sedesol.basegestor.model.especificaciones.EventoCapEspecificacion;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.AmbienteVirtualApRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.CargaArchivosOaRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.FichaDescriptivaOaRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.RecursosOaRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.RelReponsableProduccionEcRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.RelUnidadOaAvaRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.ReponsableProduccionOaRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.EventoCapacitacionRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.RelProgCompEspecificaRepo;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.RelEstructuraUDidacticaRepo;
import mx.gob.sedesol.basegestor.service.ParametroWSMoodleService;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.RelPersonaPlataformaMoodleService;
import mx.gob.sedesol.basegestor.service.encuestas.RelEncuestaEventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.EventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.PersonalizacionAreaService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.PersonalizacionRecursoService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.ReservacionEventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.SolicitudEventoCapacitacionService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.SolicitudReservacionService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.FichaDescProgramaService;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Curso;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Enrol;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.model.entities.Seccion;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.CursoWS;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.RolesWS;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.SeccionWS;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;

@Service("eventoCapacitacionService")
public class EventoCapacitacionServiceImpl extends ComunValidacionService<EventoCapacitacionDTO>
		implements EventoCapacitacionService {

	private static final Logger logger = Logger.getLogger(EventoCapacitacionServiceImpl.class);

	@Autowired
	private EventoCapacitacionRepo eventoCapacitacionRepo;

	@Autowired
	private RelReponsableProduccionEcRepo relReponsableProduccionEcRepo;

	@Autowired
	private AmbienteVirtualApRepo ambienteVirtualApRepo;

	@Autowired
	private RelEstructuraUDidacticaRepo relEstructuraUDidacticaRepo;

	@Autowired
	private RelUnidadOaAvaRepo relUnidadOaAvaRepo;

	@Autowired
	private FichaDescriptivaOaRepo fichaDescriptivaOaRepo;

	@Autowired
	private RecursosOaRepo recursosOaRepo;

	@Autowired
	private CatalogoComunService<CatClasificacionArchivoOa, Integer> catClasificacionArchivoOaService;

	@Autowired
	private CargaArchivosOaRepo cargaArchivosOaRepo;

	@Autowired
	private ReponsableProduccionOaRepo reponsableProduccionOaRepo;

	@Autowired
	private ParametroWSMoodleService parametroWSMoodleService;

	@Autowired
	private FichaDescProgramaService fichaDescProgramaService;

	@Autowired
	private SolicitudReservacionService solicitudReservacionService;

	@Autowired
	private SolicitudEventoCapacitacionService solicitudEventoCapacitacionService;

	@Autowired
	private ReservacionEventoCapacitacionService reservacionEventoCapacitacionService;

	@Autowired
	private PersonalizacionAreaService personalizacionAreaService;

	@Autowired
	private PersonalizacionRecursoService personalizacionRecursoService;

	@Autowired
	private RelPersonaPlataformaMoodleService relPersonaPlataformaMoodleService;

	@Autowired
	private RelProgCompEspecificaRepo relProgCompEspecificaRepo;

	@Autowired
	private RelEncuestaEventoCapacitacionService relEncuestaEventoCapacitacionService;

	private final ModelMapper modelMapper = new ModelMapper();

	Type tipoListaEvento = new TypeToken<List<EventoCapacitacionDTO>>() {
	}.getType();

	@Override
	public List<EventoCapacitacionDTO> obtenerTodosLosEventos() {
		// TODO Auto-generated method stub
		List<TblEvento> listEntitiesEventos = eventoCapacitacionRepo.findAll();
		return modelMapper.map(listEntitiesEventos, tipoListaEvento);
	}

	@Override
	public List<EventoCapacitacionDTO> consultaEventoPorEstatus(Integer idEstatus) {
		List<TblEvento> res = eventoCapacitacionRepo.consultaEventoPorEstatus(idEstatus);

		return modelMapper.map(res, tipoListaEvento);
	}

	@Override
	public List<EventoCapacitacionDTO> buscaEventosPorCriterios(EventoCapacitacionDTO filtro, String tipoDatoFechas) {
		try {
			EventoCapEspecificacion fichaEsp = new EventoCapEspecificacion(filtro, tipoDatoFechas);
			List<TblEvento> res = eventoCapacitacionRepo.findAll(fichaEsp, sortByFechaActualizacionDesc());

			return modelMapper.map(res, tipoListaEvento);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}

	}

	private Sort sortByFechaActualizacionDesc() {
		return new Sort(Sort.Direction.DESC, "fechaActualizacion");
	}

	/**
	 *
	 * @param idPrograma
	 * @param idPlataforma
	 * @return
	 */
	@Override
	public List<EventoCapacitacionDTO> obtenerEventosConcluidos(Integer idPrograma, Integer idPlataforma) {
		try {
			List<TblEvento> res = eventoCapacitacionRepo.obtenerEventosConcluidos(idPrograma, idPlataforma);

			return modelMapper.map(res, tipoListaEvento);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public EventoCapacitacionDTO getEvento(Integer idEvento) {
		TblEvento tblEvento = eventoCapacitacionRepo.findOne(idEvento);

		if (ObjectUtils.isNotNull(tblEvento)) {
			EventoCapacitacionDTO eventoCapacitacionDTO = new EventoCapacitacionDTO();
			modelMapper.map(tblEvento, eventoCapacitacionDTO);

			return eventoCapacitacionDTO;
		} else {
			return null;
		}

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<EventoCapacitacionDTO> guardarBorrador(CapturaEventoCapacitacionDTO datos) {
		ResultadoDTO<EventoCapacitacionDTO> resultado = new ResultadoDTO<>();

		TblEvento evento = modelMapper.map(datos.getEvento(), TblEvento.class);
		TblEvento eventoRespuesta = eventoCapacitacionRepo.save(evento);

		limpiarResponsables(eventoRespuesta);
		almacenarResponsables(datos.getCoordinadores(), eventoRespuesta);
		almacenarResponsables(datos.getFacilitadores(), eventoRespuesta);
		almacenarResponsables(datos.getProductores(), eventoRespuesta);

		resultado.setDto(modelMapper.map(eventoRespuesta, EventoCapacitacionDTO.class));
		resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());

		return resultado;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean modificarEstatusEvento(Integer idEstatus, Integer idEvento) {
		try {
			eventoCapacitacionRepo.cambiarEstatusEvento(idEstatus, idEvento);
		} catch (Exception e) {
			logger.info("Ocurrio un error al modificar el estatus del evento." + e);
			return false;
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<EventoCapacitacionDTO> guardarEventoCapacitacion(CapturaEventoCapacitacionDTO datos)
			throws Exception {
		ResultadoDTO<EventoCapacitacionDTO> resultado = validarEventoCapacitacion(datos);
		if (resultado.esCorrecto()) {

			TblEvento evento = modelMapper.map(datos.getEvento(), TblEvento.class);
			TblEvento eventoRespuesta = eventoCapacitacionRepo.save(evento);
			almacenarRelacionEncuestaEvento(eventoRespuesta.getIdEvento(), datos, eventoRespuesta.getUsuarioModifico());
			procesarModalidad(datos, eventoRespuesta);

			limpiarResponsables(eventoRespuesta);

			List<PersonaResponsabilidadesDTO> responsables = new ArrayList<>();

			responsables.addAll(datos.getCoordinadores());
			responsables.addAll(datos.getFacilitadores());
			responsables.addAll(datos.getProductores());
			almacenarResponsables(responsables, eventoRespuesta);
			almacenarResponsablesLMS(responsables, eventoRespuesta);
			almacenarInfraestructura(datos, eventoRespuesta);

			resultado.setDto(modelMapper.map(eventoRespuesta, EventoCapacitacionDTO.class));
			resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());
		}

		return resultado;
	}

	private void almacenarRelacionEncuestaEvento(Integer idEvento, CapturaEventoCapacitacionDTO datos,
			Long usuarioModifico) {
		if (ObjectUtils.isNotNull(datos.getEncuesta())) {
			if (ObjectUtils.isNotNull(datos.getEncuesta().getId())) {
				relEncuestaEventoCapacitacionService.asignarEncuestaEvento(idEvento, datos.getEncuesta().getId(),
						usuarioModifico, Boolean.TRUE);
			}
		}

	}

	public Integer obtenerIdMoodle(TblEvento evento, int numeroUnidades) throws ErrorWS {
		FichaDescProgramaDTO ficha = fichaDescProgramaService.buscarPorId(evento.getIdPrograma());
		Curso curso = new Curso();
		curso.setShortname("EC-" + evento.getIdEvento());
		curso.setFullname(evento.getNombreEc());
		curso.setCategoryid(ficha.getIdCategoriaMdl());
		curso.setIdnumber(evento.getIdEvento().toString());
		curso.setStartdate(evento.getFechaInicial().getTime());
		curso.setSummary(evento.getObjetivoGeneralEc());
		curso.setNumsections(numeroUnidades);

		CursoWS cursoWS = new CursoWS(parametroWSMoodleService.buscarPorId(evento.getIdPlataformaLmsBorrador()));

		Integer resultado = cursoWS.crearCurso(curso);

		return resultado;
	}

	@Override
	public CapturaEventoCapacitacionDTO obtenerEvento(EventoCapacitacionDTO evento) {
		CapturaEventoCapacitacionDTO datos = new CapturaEventoCapacitacionDTO();
		datos.setEvento(evento);
		datos.setPrograma(evento.getFichaDescriptivaPrograma());
		datos.setIdPrograma(evento.getIdPrograma());

		List<RelReponsableProduccionEc> coordinadores = relReponsableProduccionEcRepo
				.getResponsableDelEvento(evento.getIdEvento(), ConstantesGestor.TIPO_RESPONSABILIDAD_COORDINADOR);
		List<RelReponsableProduccionEc> facilitadores = relReponsableProduccionEcRepo
				.getResponsableDelEvento(evento.getIdEvento(), ConstantesGestor.TIPO_RESPONSABILIDAD_FACILITADOR);
		List<RelReponsableProduccionEc> productores = relReponsableProduccionEcRepo
				.getResponsableDelEvento(evento.getIdEvento(), ConstantesGestor.TIPO_RESPONSABILIDAD_PRODUCTOR);

		for (RelReponsableProduccionEc coordinador : coordinadores) {
			PersonaResponsabilidadesDTO coordinadorDTO = modelMapper.map(coordinador.getPersonaResponsabilidad(),
					PersonaResponsabilidadesDTO.class);
			if (coordinador.getEsResponsablePrincipal()) {
				datos.setIdCoordinador(coordinador.getPersonaResponsabilidad().getId());
			}
			coordinadorDTO.setEsResponsablePrincipal(coordinador.getEsResponsablePrincipal());
			datos.getCoordinadores().add(coordinadorDTO);
		}

		for (RelReponsableProduccionEc facilitador : facilitadores) {
			datos.getFacilitadores()
					.add(modelMapper.map(facilitador.getPersonaResponsabilidad(), PersonaResponsabilidadesDTO.class));
		}

		for (RelReponsableProduccionEc productor : productores) {
			PersonaResponsabilidadesDTO productorDTO = modelMapper.map(productor.getPersonaResponsabilidad(),
					PersonaResponsabilidadesDTO.class);
			if (productor.getEsResponsablePrincipal().booleanValue()) {
				datos.setIdProductor(productor.getPersonaResponsabilidad().getId());
			}
			productorDTO.setEsResponsablePrincipal(productor.getEsResponsablePrincipal());
			datos.getProductores().add(productorDTO);
		}

		return datos;
	}

	private void procesarModalidad(CapturaEventoCapacitacionDTO datos, TblEvento evento) throws ErrorWS {

		if (datos.getEvento().getCatModalidadPlanPrograma().getId() == ConstantesGestor.MODALIDAD_LINEA
				|| ((datos.getEvento().getCatModalidadPlanPrograma().getId() == ConstantesGestor.MODALIDAD_MIXTO))) {

			if (evento.getIdClasificacionAva() == ConstantesGestor.CLASIFICACION_AVA_NUEVO) {
				crearEstructuraNueva(evento);
			}
			if (evento.getIdClasificacionAva() == ConstantesGestor.CLASIFICACION_AVA_EVENTO_ANTERIOR) {
				copiarEstructuraEventoAnterior(evento);
			}

		}
	}

	private void almacenarResponsablesLMS(List<PersonaResponsabilidadesDTO> responsables, TblEvento evento) {
		if (evento.getCatModalidadPlanPrograma().getId() == ConstantesGestor.MODALIDAD_LINEA
				|| evento.getCatModalidadPlanPrograma().getId() == ConstantesGestor.MODALIDAD_MIXTO) {

			List<Enrol> listaEnrol = new ArrayList<>();
			RolesWS rolesWS = new RolesWS(parametroWSMoodleService.buscarPorId(evento.getIdPlataformaLmsBorrador()));
			for (PersonaResponsabilidadesDTO responsable : responsables) {
				Integer idPersonaLms = relPersonaPlataformaMoodleService.obtenerIdMoodle(
						modelMapper.map(responsable.getTblPersona(), PersonaDTO.class),
						evento.getIdPlataformaLmsBorrador(), evento.getUsuarioModifico());
				if (!ObjectUtils.isNullOrCero(idPersonaLms)) {
					listaEnrol.add(generarEnrol(evento, idPersonaLms));
				}
			}
			try {
				rolesWS.enrolarProfesorSinPermisos(listaEnrol);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private Enrol generarEnrol(TblEvento evento, Integer idPersonaMoodle) {
		Enrol enrol = new Enrol();
		enrol.setUserId(idPersonaMoodle);
		enrol.setCourseId(evento.getIdCursoLmsBorrador());
		enrol.setTimestart((new Date()).getTime());
		return enrol;
	}

	// Descarta los elementos de la lista que tienen un id_unidad_didactica
	// repetido
	private List<RelEstructuraUnidadDidactica> obtenerListUnidDidactSinRepetir(List<RelEstructuraUnidadDidactica> relEstUnidadDidactica) {		
		Set<Integer> idsUnidadesDidacticas = new HashSet<>();
		
		List<RelEstructuraUnidadDidactica> relEstUnidadDidacticaSinRepetir = relEstUnidadDidactica
		.stream()
		.filter(e -> idsUnidadesDidacticas.add(e.getIdUnidadDidactica()))
		.collect(Collectors.toList());
		
		return relEstUnidadDidacticaSinRepetir;
	}

	private void crearEstructuraNueva(TblEvento evento) throws ErrorWS {
		List<RelEstructuraUnidadDidactica> estructuras = relEstructuraUDidacticaRepo
				.obtieneRelEstUnidadDidPorPrograma(evento.getIdPrograma());
		estructuras = obtenerListUnidDidactSinRepetir(estructuras);
		evento.setIdCursoLmsBorrador(obtenerIdMoodle(evento, estructuras.size()));
		eventoCapacitacionRepo.save(evento);
		logger.info("###################id_moodle " + evento.getIdCursoLmsBorrador());

		logger.info("####################### ALMACENAR AVA");
		TblAmbienteVirtualAprendizaje ava = new TblAmbienteVirtualAprendizaje();
		ava.setFechaRegistro(new Date());
		ava.setUsuarioModifico(evento.getUsuarioModifico());
		ava.setEventoCapacitacion(evento);
		ava.setPlataformaMoodle(new CatParametroWSMoodle());
		ava.getPlataformaMoodle().setIdParametroWSMoodle(evento.getIdPlataformaLmsBorrador());
		ava.setCatEstadoAva(new CatEstadoAva());
		ava.getCatEstadoAva().setId(ConstantesGestor.AVA_ESTADO_SOLICITUD);
		ava.setIdCursoLms(evento.getIdCursoLmsBorrador());
		ava.setUrlLms(parametroWSMoodleService.buscarPorId(evento.getIdPlataformaLmsBorrador()).getHost()
				+ "/course/view.php?id=" + ava.getIdCursoLms());
		// TODO cambiar por culumna en parametroWSMoodleService
		ambienteVirtualApRepo.save(ava);

		logger.info("####################### ALMACENAR UNIDADES DIDACTICAS");

		List<Seccion> secciones = new ArrayList<>();
		int secuencia = 1;
		
		for (RelEstructuraUnidadDidactica estructura : estructuras) {
			logger.info(estructura.getDetEstUnidadDidactica().getIdUnidadDidactica());
			RelUnidadOaAva unidad = new RelUnidadOaAva();
			unidad.setAmbienteVirtualAprendizaje(ava);
			unidad.setDetEstUnidadDidactica(estructura.getDetEstUnidadDidactica());
			unidad.setUsuarioModifico(evento.getUsuarioModifico());
			unidad.setFechaRegistro(new Date());
			unidad.setPorcentajeAvanceOa((byte) 0);
			relUnidadOaAvaRepo.save(unidad);
			secciones.add(generarSeccion(estructura, evento, secuencia));
			secuencia++;
		}
		almacenarSecciones(secciones, evento);
		almacenarCompetencias(evento);
	}

	private void almacenarSecciones(List<Seccion> secciones, TblEvento evento) throws ErrorWS {
		SeccionWS seccionWS = new SeccionWS(parametroWSMoodleService.buscarPorId(evento.getIdPlataformaLmsBorrador()));
		seccionWS.actualizarSeccion(secciones);
	}

	private void almacenarCompetencias(TblEvento evento) throws ErrorWS {
		CursoWS cursoWS = new CursoWS(parametroWSMoodleService.buscarPorId(evento.getIdPlataformaLmsBorrador()));
		List<String> competencias = new ArrayList<>();
		for (RelProgramaCompEspecifica competencia : relProgCompEspecificaRepo
				.obtenerCompEspPorIdPrograma(evento.getIdPrograma())) {
			competencias.add(competencia.getCatCompetenciaEspecifica().getNombre());
		}
		cursoWS.registrarCompetencias(evento.getIdCursoLmsBorrador(), competencias);
	}

	private Seccion generarSeccion(RelEstructuraUnidadDidactica estructura, TblEvento evento, int secuencia) {
		Seccion seccion = new Seccion();
		seccion.setId(secuencia);
		seccion.setCourse(evento.getIdCursoLmsBorrador());
		seccion.setName(estructura.getDetEstUnidadDidactica().getNombreUnidad());
		seccion.setSummary(estructura.getDetEstUnidadDidactica().getObjetivosEspecificos());
		return seccion;
	}

	private void copiarEstructuraEventoAnterior(TblEvento evento) {
		TblAmbienteVirtualAprendizaje avaAnterior = ambienteVirtualApRepo
				.obtenerAVAPorEvento(evento.getIdEventoCapacitacionAnterior());

		if (ObjectUtils.isNull(avaAnterior)) {
			throw new NullPointerException();
		} else {
			logger.info("####################### COPIAR AVA");
			TblAmbienteVirtualAprendizaje ava = new TblAmbienteVirtualAprendizaje();
			ava.setFechaRegistro(new Date());
			ava.setUsuarioModifico(evento.getUsuarioModifico());
			ava.setEventoCapacitacion(evento);
			ava.setPlataformaMoodle(avaAnterior.getPlataformaMoodle());
			ava.setCatEstadoAva(new CatEstadoAva());
			ava.getCatEstadoAva().setId(ConstantesGestor.AVA_ESTADO_CONTRUCCION);
			ava.setPorcentajeAvance(avaAnterior.getPorcentajeAvance());
			ava.setIdCursoLms(avaAnterior.getIdCursoLms());
			ambienteVirtualApRepo.save(ava);

			logger.info("####################### COPIAR UNIDADES DIDACTICAS");
			List<RelUnidadOaAva> unidadesAnteriores = relUnidadOaAvaRepo
					.obtenerUnidadesCompletasPorAva(avaAnterior.getId());
			for (RelUnidadOaAva unidadAnterior : unidadesAnteriores) {

				TblFichaDescriptivaObjetoAprendizaje foa = new TblFichaDescriptivaObjetoAprendizaje();
				if (ObjectUtils.isNotNull(unidadAnterior.getFichaDescriptivaObjetoAprendizaje())) {
					modelMapper.map(unidadAnterior.getFichaDescriptivaObjetoAprendizaje(), foa);
					foa.setFichaDescripcionOa(new TblFichaDescripcionObjetoAprendizaje());
					foa.setIdFoa(null);

					if (ObjectUtils.isNotNull(foa.getFichaDescripcionOa())) {
						modelMapper.map(unidadAnterior.getFichaDescriptivaObjetoAprendizaje().getFichaDescripcionOa(),
								foa.getFichaDescripcionOa());
						foa.getFichaDescripcionOa().setIdFoa(null);
					}
				} else {
					foa.setFichaDescripcionOa(new TblFichaDescripcionObjetoAprendizaje());
				}
				foa.setUsuarioModifico(ava.getUsuarioModifico());
				foa.setFechaRegistro(new Date());
				// foa.getFichaDescripcionOa().setFichaDescriptivaOa(foa);
				fichaDescriptivaOaRepo.save(foa);
				RelUnidadOaAva unidad = new RelUnidadOaAva();
				modelMapper.map(unidadAnterior, unidad);
				unidad.setId(null);
				unidad.setUsuarioModifico(ava.getUsuarioModifico());
				unidad.setFechaRegistro(new Date());
				unidad.setAmbienteVirtualAprendizaje(ava);
				unidad.setFichaDescriptivaObjetoAprendizaje(foa);
				relUnidadOaAvaRepo.save(unidad);

				List<TblRecursosOa> recursosAnteriores = recursosOaRepo.findTblRecursosOasByUnidadOaAva(unidadAnterior);
				for (TblRecursosOa recursoAnterior : recursosAnteriores) {
					TblRecursosOa recurso = new TblRecursosOa();
					modelMapper.map(recursoAnterior, recurso);
					recurso.setFechaRegistro(new Date());
					recurso.setUsuarioModifico(unidad.getUsuarioModifico());
					recurso.setUnidadOaAva(unidad);
					recurso.setIdUObjetoAprendizaje(unidad.getId());
					recursosOaRepo.save(recurso);
				}

				List<CatalogoComunDTO> clasificaciones = catClasificacionArchivoOaService
						.findAll(CatClasificacionArchivoOa.class);
				for (CatalogoComunDTO clasificacion : clasificaciones) {
					List<TblCargaArchivosOa> cargasArchivo = cargaArchivosOaRepo
							.findByUnidadOaAvaAndCatClasificacionArchivo(unidadAnterior,
									modelMapper.map(clasificacion, CatClasificacionArchivoOa.class));
					if (!ObjectUtils.isNullOrEmpty(cargasArchivo)) {
						TblCargaArchivosOa tblCargaArchivosOa = new TblCargaArchivosOa();
						modelMapper.map(cargasArchivo.get(ConstantesGestor.PRIMER_ELEMENTO), tblCargaArchivosOa);
						tblCargaArchivosOa.setId(null);
						tblCargaArchivosOa.setUnidadOaAva(unidad);
						tblCargaArchivosOa.setFechaRegistro(new Date());
						tblCargaArchivosOa.setUsuarioModifico(unidad.getUsuarioModifico());
						cargaArchivosOaRepo.save(tblCargaArchivosOa);
					}
				}

				List<RelReponsableProduccionOa> responsablesAnteriores = reponsableProduccionOaRepo
						.findByIdUnidadOa(unidadAnterior.getId());
				for (RelReponsableProduccionOa responsableAnterior : responsablesAnteriores) {
					RelReponsableProduccionOa responsable = new RelReponsableProduccionOa();
					modelMapper.map(responsableAnterior, responsable);
					responsable.setIdRelResponsableProduccionOa(null);
					responsable.setIdUnidadOa(unidad.getId());
					responsable.setUnidadOaAva(unidad);
					responsable.setUsuarioModifico(unidad.getUsuarioModifico());
					responsable.setFechaRegistro(new Date());
					reponsableProduccionOaRepo.save(responsable);
				}
			}
		}
	}

	private void limpiarResponsables(TblEvento evento) {
		List<RelReponsableProduccionEc> responsablesEliminar = relReponsableProduccionEcRepo
				.getResponsableDelEvento(evento.getIdEvento());
		relReponsableProduccionEcRepo.delete(responsablesEliminar);
	}

	private void almacenarResponsables(List<PersonaResponsabilidadesDTO> responsables, TblEvento evento) {

		for (PersonaResponsabilidadesDTO personaResponsabilidad : responsables) {
			RelReponsableProduccionEc entidad = new RelReponsableProduccionEc();
			entidad.setEventoCapacitacion(evento);
			RelPersonaResponsabilidades relPersonaResponsabilidades = new RelPersonaResponsabilidades();
			modelMapper.map(personaResponsabilidad, relPersonaResponsabilidades);
			entidad.setPersonaResponsabilidad(relPersonaResponsabilidades);
			entidad.setFechaRegistro(new Date());
			entidad.setUsuarioModifico(evento.getUsuarioModifico());
			entidad.setIdEvtCapacitacion(evento.getIdEvento());
			entidad.setIdReponsableProduccion(relPersonaResponsabilidades.getId());
			if (ObjectUtils.isNull(personaResponsabilidad.getEsResponsablePrincipal())) {
				entidad.setEsResponsablePrincipal(false);
			} else {
				entidad.setEsResponsablePrincipal(personaResponsabilidad.getEsResponsablePrincipal());
			}
			relReponsableProduccionEcRepo.save(entidad);
		}
	}

	private void almacenarInfraestructura(CapturaEventoCapacitacionDTO datos, TblEvento evento) {
		// limpiarInfraestructura();
		if (evento.getCatModalidadPlanPrograma().getId().equals(ConstantesGestor.MODALIDAD_PRESENCIAL)
				|| evento.getCatModalidadPlanPrograma().getId().equals(ConstantesGestor.MODALIDAD_MIXTO)) {
			if (ObjectUtils.isNotNull(datos.getTipoAreaSeleccionado())) {
				if (datos.getTipoAreaSeleccionado().equals(ConstantesGestor.TIPO_AREA_INTERNA)) {

					ResultadoDTO<SolicitudReservacionDTO> resultado = solicitudReservacionService
							.guardar(datos.getSolicitud());
					if (resultado.esCorrecto()) {
						datos.setSolicitud(resultado.getDto());
						almacenarReservaciones(datos, evento);
					} else {
						throw new NullPointerException();
					}
				}
			}

		}
	}

	private void almacenarReservaciones(CapturaEventoCapacitacionDTO datos, TblEvento evento) {
		persistePersonalizacionArea(datos);
		for (ReservacionEventoCapacitacionDTO reservacion : datos.getReservaciones()) {
			reservacion.setIdPersonalizacionArea(reservacion.getPersonalizacionAreaDTO().getIdPersonalizacion());

			reservacion.setTblEvento(modelMapper.map(evento, EventoCapacitacionDTO.class));
			ResultadoDTO<ReservacionEventoCapacitacionDTO> resultado = reservacionEventoCapacitacionService
					.guardar(reservacion);

			RelSolicitudEventoCapacitacionDTO relSolicitudEventoCapacitacionDTO = new RelSolicitudEventoCapacitacionDTO();
			relSolicitudEventoCapacitacionDTO.setFechaRegistro(new Date());
			relSolicitudEventoCapacitacionDTO.setIdReservacionEC(resultado.getDto().getIdReservacionEc());
			relSolicitudEventoCapacitacionDTO.setIdSolicitud(datos.getSolicitud().getIdSolicitud());
			relSolicitudEventoCapacitacionDTO.setUsuarioModifico(datos.getEvento().getUsuarioModifico());

			solicitudEventoCapacitacionService.guardar(relSolicitudEventoCapacitacionDTO);
		}
	}

	public void persistePersonalizacionArea(CapturaEventoCapacitacionDTO datos) {

		for (ReservacionEventoCapacitacionDTO reservacionEventoCapacitacionDTO : datos.getReservaciones()) {

			List<RelAreaRecursoDTO> relAreaRecurssoDTO = new ArrayList<>();
			relAreaRecurssoDTO.addAll(reservacionEventoCapacitacionDTO.getPersonalizacionAreaDTO().getRecTec());
			relAreaRecurssoDTO.addAll(reservacionEventoCapacitacionDTO.getPersonalizacionAreaDTO().getRecMob());

			ResultadoDTO<PersonalizacionAreaDTO> resultado = personalizacionAreaService
					.guardar(reservacionEventoCapacitacionDTO.getPersonalizacionAreaDTO());
			reservacionEventoCapacitacionDTO.setPersonalizacionAreaDTO(resultado.getDto());

			for (RelAreaRecursoDTO recurso : relAreaRecurssoDTO) {
				RelPersonalizacionRecursoDTO relPersonalizacionRecursoDTO = new RelPersonalizacionRecursoDTO();
				relPersonalizacionRecursoDTO.setActivo(true);
				relPersonalizacionRecursoDTO.setCantidad(recurso.getCantidadSeleccionada());
				relPersonalizacionRecursoDTO.setFechaRegistro(new Date());
				relPersonalizacionRecursoDTO.setIdPersonalizacionArea(
						reservacionEventoCapacitacionDTO.getPersonalizacionAreaDTO().getIdPersonalizacion());
				relPersonalizacionRecursoDTO.setIdRecurso(recurso.getCatRecursosInfraestructura().getIdRecurso());
				relPersonalizacionRecursoDTO.setUsuarioRegistro(datos.getEvento().getUsuarioModifico());

				personalizacionRecursoService.guardar(relPersonalizacionRecursoDTO);
			}
		}
	}

	private ResultadoDTO<EventoCapacitacionDTO> validarEventoCapacitacion(CapturaEventoCapacitacionDTO datos) {
		ResultadoDTO<EventoCapacitacionDTO> resultado = new ResultadoDTO<>();

		if (ObjectUtils.isNullOrEmpty(datos.getEvento().getNombreEc())) {
			resultado.setMensajeError(MensajesSistemaEnum.EC_NOMBRE_EVT_REQ);
		}
		if (ObjectUtils.isNullOrEmpty(datos.getEvento().getUrlImagen())) {
			resultado.setMensajeError(MensajesSistemaEnum.EC_IMAGEN_EVT_REQ);
		}
		if (ObjectUtils.isNull(datos.getEvento().getIdDirigido())
				|| ObjectUtils.isNull(TipoUsuarioEnum.getTipoUsuarioEnum(datos.getEvento().getIdDirigido()))) {
			resultado.setMensajeError(MensajesSistemaEnum.EC_DIRIGIDO_REQ);
		} else {
			if (TipoUsuarioEnum.getTipoUsuarioEnum(datos.getEvento().getIdDirigido())
					.getValor() == TipoUsuarioEnum.INTERNO.getValor()) {
				datos.getEvento().setIdDestinatario(null);
				datos.getEvento().setIdProgramaSocial(null);
				datos.getEvento().setIdEntidadFederativa(null);
				datos.getEvento().setIdMunicipio(null);
			}
			if (TipoUsuarioEnum.getTipoUsuarioEnum(datos.getEvento().getIdDirigido())
					.getValor() == TipoUsuarioEnum.EXTERNO.getValor()) {
				validarDestinatarios(datos, resultado);
			}
		}
		if (ObjectUtils.isNull(datos.getEvento().getFechaInicial())) {
			resultado.setMensajeError(MensajesSistemaEnum.EC_FECHA_INI_REQ);
		}
		if (ObjectUtils.isNull(datos.getEvento().getFechaFinal())) {
			resultado.setMensajeError(MensajesSistemaEnum.EC_FECHA_FIN_REQ);
		}
		if (ObjectUtils.isNull(datos.getEvento().getCategoriaEC())
				|| ObjectUtils.isNull(datos.getEvento().getCategoriaEC().getId())) {
			resultado.setMensajeError(MensajesSistemaEnum.EC_CATEGORIA_REQ);
		}

		validarModalidad(datos, resultado);
		if (datos.getCoordinadores().isEmpty()) {
			resultado.setMensajeError(MensajesSistemaEnum.EC_COORDINADOR_ACADEMICO_REQ);
		}

		validarEncuesta(datos, resultado);

		return resultado;
	}

	private void validarEncuesta(CapturaEventoCapacitacionDTO datos, ResultadoDTO<EventoCapacitacionDTO> resultado) {
		if (datos.getEvento().getAplicaEncuesta().booleanValue() == true) {
			if (ObjectUtils.isNull(datos.getEncuesta())) {
				resultado.setMensajeError(MensajesSistemaEnum.EC_ENCUESTA_REQ);
			}
		}
	}

	private void validarDestinatarios(CapturaEventoCapacitacionDTO datos,
			ResultadoDTO<EventoCapacitacionDTO> resultado) {
		if (ObjectUtils.isNotNull(datos.getEvento().getIdDestinatario())
				&& datos.getEvento().getIdDestinatario().getId() != ConstantesGestor.DESTINATARIO_PROGRAMA_SOCIAL) {
			datos.getEvento().setIdProgramaSocial(null);
			datos.getEvento().setIdEntidadFederativa(null);
			datos.getEvento().setIdMunicipio(null);
		} else if (ObjectUtils.isNull(datos.getEvento().getIdDestinatario())) {
			resultado.setMensajeError(MensajesSistemaEnum.EC_DESTINATARIO_REQ);
			datos.getEvento().setIdProgramaSocial(null);
			datos.getEvento().setIdEntidadFederativa(null);
			datos.getEvento().setIdMunicipio(null);
		} else {
			validarProgramaSocial(datos, resultado);
		}
	}

	private void validarProgramaSocial(CapturaEventoCapacitacionDTO datos,
			ResultadoDTO<EventoCapacitacionDTO> resultado) {
		if (ObjectUtils.isNotNull(datos.getEvento().getIdProgramaSocial())
				&& datos.getEvento().getIdProgramaSocial() != ConstantesGestor.PROGRAMA_SOCIAL_CAPTURA_CUIS) {
			datos.getEvento().setIdEntidadFederativa(null);
			datos.getEvento().setIdMunicipio(null);
		} else if (ObjectUtils.isNull(datos.getEvento().getIdProgramaSocial())) {
			resultado.setMensajeError(MensajesSistemaEnum.EC_PROGRAMA_SOCIAL_REQ);
			datos.getEvento().setIdEntidadFederativa(null);
			datos.getEvento().setIdMunicipio(null);
		} else {
			validarEstructuraTerritorial(datos, resultado);
		}
	}

	private void validarEstructuraTerritorial(CapturaEventoCapacitacionDTO datos,
			ResultadoDTO<EventoCapacitacionDTO> resultado) {
		if (ObjectUtils.isNull(datos.getEvento().getIdEntidadFederativa())) {
			resultado.setMensajeError(MensajesSistemaEnum.MUNICIPIOS_ENTIDAD_FED_REQ);
		}
		if (ObjectUtils.isNull(datos.getEvento().getIdMunicipio())) {
			resultado.setMensajeError(MensajesSistemaEnum.ASENTAMIENTOS_MUNICIPIO_REQ);
		}
	}

	private void validarModalidad(CapturaEventoCapacitacionDTO datos, ResultadoDTO<EventoCapacitacionDTO> resultado) {
		if (ObjectUtils.isNull(datos.getEvento().getCatModalidadPlanPrograma())
				|| ObjectUtils.isNull(datos.getEvento().getCatModalidadPlanPrograma().getId())) {
			resultado.setMensajeError(MensajesSistemaEnum.EC_MODALIDAD_REQ);
		} else {
			if (datos.getEvento().getCatModalidadPlanPrograma().getId() == ConstantesGestor.MODALIDAD_LINEA || ((datos
					.getEvento().getCatModalidadPlanPrograma().getId() == ConstantesGestor.MODALIDAD_MIXTO))) {
				validarAVA(datos, resultado);
			}

		}
	}

	private void validarAVA(CapturaEventoCapacitacionDTO datos, ResultadoDTO<EventoCapacitacionDTO> resultado) {
		if (ObjectUtils.isNull(datos.getEvento().getIdPlataformaLmsBorrador())) {
			resultado.setMensajeError(MensajesSistemaEnum.EC_PLATAFORMA_REQ);
		}
		if (ObjectUtils.isNull(datos.getEvento().getIdClasificacionAva())) {
			resultado.setMensajeError(MensajesSistemaEnum.EC_CLASIFICACION_AVA_REQ);
		} else {
			if (datos.getEvento().getIdClasificacionAva() == ConstantesGestor.CLASIFICACION_AVA_EVENTO_ANTERIOR) {
				validarEventoAnterior(datos, resultado);
			}
			if (datos.getEvento().getIdClasificacionAva() == ConstantesGestor.CLASIFICACION_AVA_CURSO_MOODLE) {
				validarCursoMoodle(datos, resultado);
			}
		}
	}

	private void validarEventoAnterior(CapturaEventoCapacitacionDTO datos,
			ResultadoDTO<EventoCapacitacionDTO> resultado) {
		if (ObjectUtils.isNull(datos.getEvento().getIdEventoCapacitacionAnterior())) {
			resultado.setMensajeError(MensajesSistemaEnum.EC_EVENTO_ANTERIOR_REQ);
		}
	}

	private void validarCursoMoodle(CapturaEventoCapacitacionDTO datos, ResultadoDTO<EventoCapacitacionDTO> resultado) {
		if (ObjectUtils.isNull(datos.getEvento().getIdCursoLmsBorrador())) {
			resultado.setMensajeError(MensajesSistemaEnum.EC_CURSO_MOODLE_REQ);
		}
	}

	public List<EventoCapacitacionDTO> consultaEventosPorTipoVisibilidad(boolean isPrivado) {

		Type listAux = new TypeToken<List<EventoCapacitacionDTO>>() {
		}.getType();
		List<TblEvento> eventos = eventoCapacitacionRepo.consultaEventoPorTipoVisibilidad(isPrivado);
		return modelMapper.map(eventos, listAux);
	}

	public List<EventoCapacitacionDTO> obtenerEventosPublicosEnEjec() {

		Type listAux = new TypeToken<List<EventoCapacitacionDTO>>() {
		}.getType();
		List<TblEvento> eventos = eventoCapacitacionRepo.obtenerEventosPublicosEnEjec();
		return modelMapper.map(eventos, listAux);
	}

	public List<EventoCapacitacionDTO> obtenerEventosPorPrograma(Integer idPrograma) {
		List<TblEvento> eventos = eventoCapacitacionRepo.obtenerEventosPorPrograma(idPrograma);
		Type listaEventosCapacitacion = new TypeToken<List<EventoCapacitacionDTO>>() {
		}.getType();

		return modelMapper.map(eventos, listaEventosCapacitacion);
	}

	@Override
	public void validarPersistencia(EventoCapacitacionDTO dto, ResultadoDTO<EventoCapacitacionDTO> resultado) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public void validarActualizacion(EventoCapacitacionDTO dto, ResultadoDTO<EventoCapacitacionDTO> resultado) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

	@Override
	public void validarEliminacion(EventoCapacitacionDTO dto, ResultadoDTO<EventoCapacitacionDTO> resultado) {
		throw new UnsupportedOperationException("Not supported yet."); // To
																		// change
																		// body
																		// of
																		// generated
																		// methods,
																		// choose
																		// Tools
																		// |
																		// Templates.
	}

}
