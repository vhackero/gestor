package mx.gob.sedesol.basegestor.service.impl.gestion.aprendizaje;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroWSMoodleDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestion.aprendizaje.AmbienteVirtualAprendizajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.utils.EstadoEventoCapEnum;
import mx.gob.sedesol.basegestor.commons.utils.EstatusAmbienteVirtualAprendizajeEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.SemaforoEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.commons.utils.UtileriaBorrarArchivo;
import mx.gob.sedesol.basegestor.commons.utils.UtilidadadDescargaArchivoUrl;
import mx.gob.sedesol.basegestor.commons.utils.UtilidadesCargaArchivoFTP;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.CatEstadoAva;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblAmbienteVirtualAprendizaje;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblRespaldosAva;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblRutaRespaldo;
import mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje.TblUsuariosFtp;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.CatEstadoEventoCapacitacion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.RelReponsableProduccionEc;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.TblEvento;
import mx.gob.sedesol.basegestor.model.especificaciones.AmbienteVirtualApEspecificacion;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.AmbienteVirtualApRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.RespaldosAvaRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.RutaRespaldosRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestion.aprendizaje.UsuariosFtpRepo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.EventoCapacitacionRepo;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.basegestor.service.gestion.aprendizaje.AmbienteVirtualApService;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.CursoWS;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.client.LoginWS;
import mx.gob.sedesol.basegestor.ws.moodle.clientes.service.util.ErrorWS;

@Service("ambienteVirtualApService")
public class AmbienteVirtualApServiceImpl extends ComunValidacionService<AmbienteVirtualAprendizajeDTO>
		implements AmbienteVirtualApService {

	/**
	 * clave de respaldo servidor
	 */
	private static final String CLAVE_RESPALDO_SERVIDOR = "2c52ee57-247d-4751-919b-9029d41f852b";
	/**
	 * clave respaldo temporal
	 */
	private static final String CLAVE_RESPALDO_TEMPORAL = "f5bf9f05-334f-4d40-b2e8-d48c0a8b54b3";
	/**
	 * clave usuario ftp
	 */
	private static final String CLAVE_USUARIO_FTP = "ca34b858-fbee-4da0-b278-67858bc55cd2";

	/**
	 * Variable logger
	 */
	private static final Logger logger = Logger.getLogger(AmbienteVirtualApServiceImpl.class);

	/**
	 * Inyeccion de ambiente virtual Repo
	 */
	@Autowired
	private AmbienteVirtualApRepo ambienteVirtualApRepo;

	/**
	 * Inyeccion de persona
	 */
	@Autowired
	private PersonaService personaService;

	/**
	 * Inyeccion de repo de evento de capacitacion
	 */
	@Autowired
	private EventoCapacitacionRepo eventoCapacitacionRepo;

	/**
	 * Inyeccion del servicio de catalogo comun para cat estado evento
	 * capacitacion
	 */
	@Autowired
	private CatalogoComunService<CatEstadoEventoCapacitacion, Integer> catalogoEstadoEventoCapacitacionService;

	/**
	 * Inyeccion del servicio de catalogo comun para cat estado ava service
	 */
	@Autowired
	private CatalogoComunService<CatEstadoAva, Integer> catEstadoAvaService;

	/**
	 * Inyeccion de UsuariosFtpRepo
	 */
	@Autowired
	private UsuariosFtpRepo usuariosFtpRepo;

	/**
	 * Inyeccion de RespaldosAvaRepo
	 */
	@Autowired
	private RespaldosAvaRepo respaldosAvaRepo;

	/**
	 * Inyeccion de RutaRespaldosRepo
	 */
	@Autowired
	private RutaRespaldosRepo rutaRespaldosRepo;

	/**
	 * Model mapper
	 */
	private ModelMapper ambienteVirtualApMapper = new ModelMapper();

	private TblRutaRespaldo rutaRespaldoTemporal;

	private TblRutaRespaldo rutaRespaldoServidor;

	private TblUsuariosFtp tblUsuariosFtp;

	@PostConstruct
	private void init() {

		rutaRespaldoTemporal = rutaRespaldosRepo.findByClave(CLAVE_RESPALDO_TEMPORAL);

		rutaRespaldoServidor = rutaRespaldosRepo.findByClave(CLAVE_RESPALDO_SERVIDOR);

		tblUsuariosFtp = usuariosFtpRepo.findByClave(CLAVE_USUARIO_FTP);

	}

	/**
	 * Buequeda de los AVAs por estatus y por el evento de capacitacion que este
	 * en cierta modalidad y estatus
	 */
	@Override
	public List<AmbienteVirtualAprendizajeDTO> consultarAvaPorEstatusYEventoCapacitacion(Integer estatusAva,
			List<Integer> modalidadEvtCapList, Integer estatusEvtCap) {
		List<TblAmbienteVirtualAprendizaje> tblAmbienteVirtualAprendizajeList = ambienteVirtualApRepo
				.consultarAvaPorEstatusYEventoCapacitacion(estatusAva, modalidadEvtCapList, estatusEvtCap);

		logger.info("El tamanio de la lista retornada es " + tblAmbienteVirtualAprendizajeList.size());

		List<AmbienteVirtualAprendizajeDTO> resList = new ArrayList<>();
		for(TblAmbienteVirtualAprendizaje ava : tblAmbienteVirtualAprendizajeList){
			try {
				resList.add(ambienteVirtualApMapper.map(ava, AmbienteVirtualAprendizajeDTO.class));
			}catch(Exception ex){
				logger.error("Ocurrio un error en el ava con id "+ava.getId());
			}
		}
		
		return resList;
	}

	public List<AmbienteVirtualAprendizajeDTO> consultarAvasPorTipoCompetenciaEjeCapacitacion(
			List<Integer> modalidadEvtCapList, List<Integer> estatusEvtCap, Integer idTipoCom,
			Integer idEjeCapacitacion) {

		List<TblAmbienteVirtualAprendizaje> tblAmbienteVirtualAprendizajeList = ambienteVirtualApRepo
				.consultarAvasPorTipoCompetenciaEjeCapacitacion(modalidadEvtCapList, estatusEvtCap, idTipoCom,
						idEjeCapacitacion);

		logger.info("El tamanio de la lista retornada es " + tblAmbienteVirtualAprendizajeList.size());
		
		List<AmbienteVirtualAprendizajeDTO> resList = new ArrayList<>();
		for(TblAmbienteVirtualAprendizaje ava : tblAmbienteVirtualAprendizajeList){
			try {
				resList.add(ambienteVirtualApMapper.map(ava, AmbienteVirtualAprendizajeDTO.class));
			}catch(Exception ex){
				logger.error("Ocurrio un error en el ava con id "+ava.getId());
			}
		}
		
		return resList;
	}
	
	@Override
	public List<AmbienteVirtualAprendizajeDTO> consultarAvasPorEjeCapacitacion(
			List<Integer> modalidadEvtCapList, List<Integer> estatusEvtCap, Integer idEjeCapacitacion) {

		List<TblAmbienteVirtualAprendizaje> tblAmbienteVirtualAprendizajeList = ambienteVirtualApRepo
				.consultarAvasPorEjeCapacitacion(modalidadEvtCapList, estatusEvtCap, idEjeCapacitacion);

		logger.info("El tamanio de la lista retornada es " + tblAmbienteVirtualAprendizajeList.size());

		List<AmbienteVirtualAprendizajeDTO> resList = new ArrayList<>();
		for(TblAmbienteVirtualAprendizaje ava : tblAmbienteVirtualAprendizajeList){
			try {
				resList.add(ambienteVirtualApMapper.map(ava, AmbienteVirtualAprendizajeDTO.class));
			}catch(Exception ex){
				logger.error("Ocurrio un error en el ava con id "+ava.getId());
			}
		}
		
		return resList;
	}

	public List<AmbienteVirtualAprendizajeDTO> busquedaDeAvasConCriterios(EventoCapacitacionDTO filtro,
			String tipoDatoFechas) {
		List<AmbienteVirtualAprendizajeDTO> ambienteVirtualAprendizajeDTOList = null;
		try {

			AmbienteVirtualApEspecificacion avaEsp = new AmbienteVirtualApEspecificacion(filtro, tipoDatoFechas);
			List<TblAmbienteVirtualAprendizaje> res = ambienteVirtualApRepo.findAll(avaEsp,
					sortByFechaActualizacionDesc());

			Type objetoDTO = new TypeToken<List<AmbienteVirtualAprendizajeDTO>>() {
			}.getType();

			ambienteVirtualAprendizajeDTOList = ambienteVirtualApMapper.map(res, objetoDTO);
			logger.info("El tamanio de la lista retornada es " + ambienteVirtualAprendizajeDTOList.size());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ocurrio un error en la busqueda AVAs por criterios");

		}
		return ambienteVirtualAprendizajeDTOList;
	}

	@Override
	public AmbienteVirtualAprendizajeDTO findAvaById(Integer idAva) {

		TblAmbienteVirtualAprendizaje ambienteVirtualAprendizaje = ambienteVirtualApRepo.findById(idAva);
		// ambienteVirtualAprendizaje.setPersonaResponsabilidades(null);
		ambienteVirtualAprendizaje.getEventoCapacitacion().setAmbienteVirtualAprendizajes(null);

		if (!ObjectUtils
				.isNullOrEmpty(ambienteVirtualAprendizaje.getEventoCapacitacion().getReponsableProduccionEcs())) {
			for (RelReponsableProduccionEc relReponsableProduccionEc : ambienteVirtualAprendizaje
					.getEventoCapacitacion().getReponsableProduccionEcs()) {
				relReponsableProduccionEc.setEventoCapacitacion(null);
				if (ObjectUtils.isNotNull(relReponsableProduccionEc.getPersonaResponsabilidad())) {
					relReponsableProduccionEc.getPersonaResponsabilidad().setRelReponsableProduccionEcs(null);
					relReponsableProduccionEc.getPersonaResponsabilidad().setRelReponsableProduccionOas(null);
					if (ObjectUtils.isNotNull(relReponsableProduccionEc.getPersonaResponsabilidad().getTblPersona())) {
						relReponsableProduccionEc.getPersonaResponsabilidad().getTblPersona()
								.setDomiciliosPersonas(null);
						relReponsableProduccionEc.getPersonaResponsabilidad().getTblPersona().setPersonaCorreos(null);
						relReponsableProduccionEc.getPersonaResponsabilidad().getTblPersona().setRelPersonaRoles(null);
						relReponsableProduccionEc.getPersonaResponsabilidad().getTblPersona()
								.setRelPersonaTelefonos(null);
					}
				}
			}
		}

		return ambienteVirtualApMapper.map(ambienteVirtualAprendizaje, AmbienteVirtualAprendizajeDTO.class);

	}

	@Override
	public void save(AmbienteVirtualAprendizajeDTO ambienteVirtualAprendizajeDTO) {
		TblAmbienteVirtualAprendizaje tblAmbienteVirtualAprendizaje = new TblAmbienteVirtualAprendizaje();

		ambienteVirtualApMapper.map(ambienteVirtualAprendizajeDTO, tblAmbienteVirtualAprendizaje);

		ambienteVirtualApRepo.save(tblAmbienteVirtualAprendizaje);
	}

	private Sort sortByFechaActualizacionDesc() {
		return new Sort(Sort.Direction.DESC, "fechaActualizacion");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<AmbienteVirtualAprendizajeDTO> actualizar(AmbienteVirtualAprendizajeDTO dto) {
		ResultadoDTO<AmbienteVirtualAprendizajeDTO> rs = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
		if (ObjectUtils.isNotNull(rs) && rs.getResultado().getValor()) {
			try {

				TblAmbienteVirtualAprendizaje tblAmbienteVirtualAprendizaje = ambienteVirtualApMapper.map(dto,
						TblAmbienteVirtualAprendizaje.class);

				tblAmbienteVirtualAprendizaje = ambienteVirtualApRepo.saveAndFlush(tblAmbienteVirtualAprendizaje);
				rs = new ResultadoDTO<AmbienteVirtualAprendizajeDTO>();

				AmbienteVirtualAprendizajeDTO avaDTO = ambienteVirtualApMapper.map(tblAmbienteVirtualAprendizaje,
						AmbienteVirtualAprendizajeDTO.class);
				rs.setDto(avaDTO);

				rs.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());

			} catch (Exception e) {
				rs.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
				logger.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		}
		return rs;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<AmbienteVirtualAprendizajeDTO> activaAvaEjecutaEventoCapacitacion(
			AmbienteVirtualAprendizajeDTO ava, Long idPersona, CatalogoComunDTO estatusAvaActivo,
			CatalogoComunDTO edoEvtCapEnEjecucion) {
		ResultadoDTO<AmbienteVirtualAprendizajeDTO> resultado = null;

		try {

			ava.setActivo(Boolean.TRUE);
			ava.setUsuarioModifico(idPersona);
			ava.setFechaActualizacion(new Date());
			ava.setCatEstadoAva(estatusAvaActivo);

			resultado = this.actualizar(ava);

			logger.info("El resultado de actualizar el AVA es: " + resultado.esCorrecto());

			EventoCapacitacionDTO eventoDTO = resultado.getDto().getEventoCapacitacion();
			eventoDTO.setCatEstadoEventoCapacitacion(edoEvtCapEnEjecucion);

			TblEvento tblEvento = new TblEvento();
			ambienteVirtualApMapper.map(eventoDTO, tblEvento);

			eventoCapacitacionRepo.saveAndFlush(tblEvento);

		} catch (Exception e) {
			resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
			logger.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return resultado;
	}

	@Override
	public ResultadoDTO<AmbienteVirtualAprendizajeDTO> sonDatosRequeridosValidos(TipoAccion accion,
			AmbienteVirtualAprendizajeDTO dto) {

		ResultadoDTO<AmbienteVirtualAprendizajeDTO> resultado = null;

		if (ObjectUtils.isNotNull(dto)) {
			resultado = new ResultadoDTO<AmbienteVirtualAprendizajeDTO>();

			switch (accion) {
			case PERSISTENCIA:

				if (ObjectUtils.isNull(dto.getFechaRegistro())) {
					resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
				}
				if (ObjectUtils.isNull(dto.getUsuarioModifico())) {
					resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
				}
				break;

			case ELIMINACION:
				if (ObjectUtils.isNull(dto.getId())) {
					resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_ID_REQ);
				}
				break;

			case ACTUALIZACION:

				if (ObjectUtils.isNull(dto.getId())) {
					resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_ID_REQ);
				}
				if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
					resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
				}
				if (ObjectUtils.isNull(dto.getUsuarioModifico())) {
					resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
				}
				break;
			}
		}
		return resultado;
	}

	@Override
	public AmbienteVirtualAprendizajeDTO obtenerAVAPorEvento(Integer idEvento) {
		TblAmbienteVirtualAprendizaje entidad = ambienteVirtualApRepo.obtenerAVAPorIdEvento(idEvento);
		AmbienteVirtualAprendizajeDTO dto;
		if (ObjectUtils.isNull(entidad)) {
			dto = null;
		} else {
			dto = ambienteVirtualApMapper.map(entidad, AmbienteVirtualAprendizajeDTO.class);
		}
		return dto;
	}

	public List<AmbienteVirtualAprendizajeDTO> obtenerAvasPorIdEventos(List<Integer> idEventos) {

		List<TblAmbienteVirtualAprendizaje> tblAmbienteVirtualAprendizajeList = ambienteVirtualApRepo
				.obtenerAVAsPorIdEventos(idEventos);

		List<AmbienteVirtualAprendizajeDTO> resList = new ArrayList<>();
		for(TblAmbienteVirtualAprendizaje ava : tblAmbienteVirtualAprendizajeList){
			try {
				resList.add(ambienteVirtualApMapper.map(ava, AmbienteVirtualAprendizajeDTO.class));
			}catch(Exception ex){
				logger.error("Ocurrio un error en el ava con id "+ava.getId());
			}
		}
		
		return resList;
	}

	public String asignaColorSemaro(AmbienteVirtualAprendizajeDTO ava) {
		Long diferencia = null;
		Date fechaActual = new Date();
		Date fechaInicialEventoCap = ava.getEventoCapacitacion().getFechaInicial();
		String resultante = SemaforoEnum.SEMAFORO_GRIS.getColorSemaforo();
		Byte porcentajeDeAvance = ObjectUtils.isNull(ava.getPorcentajeAvance()) ? 0 : ava.getPorcentajeAvance();
		diferencia = obtenerDiferenciaDeDias(fechaInicialEventoCap, fechaActual);

		if (diferencia > 5) {
			resultante = SemaforoEnum.SEMAFORO_AMARILLO.getColorSemaforo();
		}
		if (diferencia < 5) {
			resultante = SemaforoEnum.SEMAFORO_ROJO.getColorSemaforo();
		}
		if (diferencia < 0 && ConstantesGestor.NUMERO_CIEN.intValue() == porcentajeDeAvance.intValue()) {
			resultante = SemaforoEnum.SEMAFORO_VERDE.getColorSemaforo();

		}

		return resultante;
	}

	private Long obtenerDiferenciaDeDias(Date fechaInicial, Date fechaFinal) {
		Long diferencia = null;

		diferencia = (fechaInicial.getTime() - fechaFinal.getTime()) / ConstantesGestor.MILISEGUNDOS_POR_DIA;

		return diferencia;
	}

	@Transactional
	@Override
	public ResultadoDTO<AmbienteVirtualAprendizajeDTO> activaAvas(Date fechaVencimiento, Boolean estatusActual) {

		ResultadoDTO<AmbienteVirtualAprendizajeDTO> resultado = new ResultadoDTO<AmbienteVirtualAprendizajeDTO>();

		try {

			/**
			 * Obtiene catalogos*
			 */
			CatEstadoEventoCapacitacion catEstadoEventoCapacitacion = new CatEstadoEventoCapacitacion();
			ambienteVirtualApMapper.map(catalogoEstadoEventoCapacitacionService
					.buscarPorId(EstadoEventoCapEnum.EN_EJECUCION.getId(), CatEstadoEventoCapacitacion.class),
					catEstadoEventoCapacitacion);

			CatEstadoAva catEstadoAva = new CatEstadoAva();
			ambienteVirtualApMapper.map(catEstadoAvaService.buscarPorId(
					EstatusAmbienteVirtualAprendizajeEnum.ACTIVO.getId(), CatEstadoAva.class), catEstadoAva);

			/**
			 * 1.-Obtiene los ava a actualizar
			 */
			List<TblAmbienteVirtualAprendizaje> list = ambienteVirtualApRepo.obtenerAvasPorActivar(fechaVencimiento,
					estatusActual, EstadoEventoCapEnum.CALENDARIZADO.getId(),
					EstatusAmbienteVirtualAprendizajeEnum.EN_CONSTRUCCION.getId());

			if (!ObjectUtils.isNullOrEmpty(list)) {

				/**
				 * 2.- Hace un update de todos los regitros actualizados
				 */
				list.forEach(tblAmbienteVirtualAprendizaje -> {

					tblAmbienteVirtualAprendizaje.setActivo(Boolean.TRUE);
					tblAmbienteVirtualAprendizaje.setUsuarioModifico(0L);
					tblAmbienteVirtualAprendizaje.setFechaActualizacion(new Date());
					tblAmbienteVirtualAprendizaje.setCatEstadoAva(catEstadoAva);

					TblEvento tblEvento = tblAmbienteVirtualAprendizaje.getEventoCapacitacion();
					tblEvento.setCatEstadoEventoCapacitacion(catEstadoEventoCapacitacion);

					ambienteVirtualApRepo.saveAndFlush(tblAmbienteVirtualAprendizaje);

					eventoCapacitacionRepo.saveAndFlush(tblEvento);

					// GUSTAVO --guardarBitacoraAmbienteVirtual(dto,
					// String.valueOf(tblAmbienteVirtualAprendizaje.getId()));

				});
				resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
			} else {
				logger.info("Sin registros para actualizar");
			}
		} catch (Exception e) {
			resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
			logger.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return resultado;
	}

	public Boolean respaldarCurso(AmbienteVirtualAprendizajeDTO ava, Long usuarioModifico) {

		CursoWS cursoWS = new CursoWS(ava.getPlataformaMoodle());
		Integer idCurso = ava.getIdCursoLms();
		Boolean esOperacionCorrecta = Boolean.TRUE;
		String urlCursoSinDatos = null;
		String urlCursoConDatos = null;
		String ubicacionArchivoCursoSinDatos = null;
		String ubicacionArchivoCursoConDatos = null;

		logger.info("Inicia la operacion para respaldar curso");

		/**
		 * 1.-Se obtiene la Url para descargar el curso sin datos*
		 */
		logger.info("1.-Se obtiene la Url para descargar el curso sin datos");
		urlCursoSinDatos = this.obtenerUrlCursoLms(cursoWS, idCurso, Boolean.TRUE);

		/**
		 * 1.2.-Se obtiene la Url para descargar el curso con datos*
		 */
		logger.info("1.2.-Se obtiene la Url para descargar el curso con datos");
		urlCursoConDatos = this.obtenerUrlCursoLms(cursoWS, idCurso, Boolean.FALSE);

		if (ObjectUtils.isNull(urlCursoSinDatos) || ObjectUtils.isNull(urlCursoConDatos)) {
			logger.error("No se encontro una url valida");
			esOperacionCorrecta = Boolean.FALSE;
		}

		/**
		 * 2.-Descargamos el curso apartir de la url obtenida *
		 */
		if (esOperacionCorrecta) {
			logger.info("2.-Descargamos el curso apartir de la url obtenida");

			logger.info("2.2-Descarga curso sin datos");
			ubicacionArchivoCursoSinDatos = this.descargaArchivoApartirDeUrl(urlCursoSinDatos);

			logger.info("2.2.1-Descarga curso sin datos resultado es " + esOperacionCorrecta);

			logger.info("2.3-Descarga curso con datos");
			ubicacionArchivoCursoConDatos = this.descargaArchivoApartirDeUrl(urlCursoConDatos);
			logger.info("2.3.1-Descarga curso con datos resultado es " + esOperacionCorrecta);

			if (ObjectUtils.isNull(ubicacionArchivoCursoSinDatos)
					|| ObjectUtils.isNull(ubicacionArchivoCursoConDatos)) {
				logger.error("No se Puedo descargar el curso Lms");
				esOperacionCorrecta = Boolean.FALSE;
			}

		}

		/**
		 * 3.-Movemos el curso descargado a a el servidor de respaldo*
		 */
		if (esOperacionCorrecta) {

			logger.info("3.-Movemos el curso a el servidor de respaldos");

			logger.info("3.1-Movemos el curso sin datos a el servidor de respaldos");

			esOperacionCorrecta = respaldarCursoEnServidor(ubicacionArchivoCursoSinDatos);
			logger.info("El resultado del respaldo del curso sin datos en el servidor es :" + esOperacionCorrecta);

			logger.info("3.2-Movemos el curso sin datos a el servidor de respaldos");

			esOperacionCorrecta = respaldarCursoEnServidor(ubicacionArchivoCursoConDatos);

			logger.info("El resultado del respaldo del curso Con datos en el servidor es :" + esOperacionCorrecta);

			if (!esOperacionCorrecta) {
				logger.info(
						"El respaldo de los cursos en el servidor no fue exitoso borrar cursos de la carpeta temporal");
				Boolean esBorradoCorrecto = Boolean.FALSE;
				esBorradoCorrecto = UtileriaBorrarArchivo.borraArchivo(ubicacionArchivoCursoConDatos);
				esBorradoCorrecto = UtileriaBorrarArchivo.borraArchivo(ubicacionArchivoCursoSinDatos);

				logger.info("El borrado de los archivos fue :" + esBorradoCorrecto);

			}

		}

		/**
		 * 4.-Persistir la informacion de los archivos respaldados en las tablas
		 * *
		 */
		if (esOperacionCorrecta) {
			logger.info("4.-Persistir la informacion de los archivos respaldados en las tablas");

			TblAmbienteVirtualAprendizaje tblAmbienteVirtualAprendizaje = ambienteVirtualApMapper.map(ava,
					TblAmbienteVirtualAprendizaje.class);

			String nombreCursoSinDatos = this.obtenerNombreArchivoApartirDeRutaRelativa(ubicacionArchivoCursoSinDatos);

			String nombreCursoConDatos = this.obtenerNombreArchivoApartirDeRutaRelativa(ubicacionArchivoCursoConDatos);

			TblRespaldosAva tblRespaldosAva = armaObjetoTblRespaldosAva(usuarioModifico, nombreCursoConDatos,
					nombreCursoSinDatos, tblAmbienteVirtualAprendizaje, urlCursoConDatos, urlCursoSinDatos);

			ResultadoDTO<AmbienteVirtualAprendizajeDTO> resultado = this.guardaInformacionRespaldoAva(tblRespaldosAva,
					ava, usuarioModifico);

			logger.info("El resultado de persistir el respaldo ava es " + resultado.esCorrecto());

			if (!resultado.esCorrecto()) {
				esOperacionCorrecta = Boolean.FALSE;

			}

		}

		/**
		 * 5.Borrar el curso LMS*
		 */
		if (esOperacionCorrecta) {
			logger.info("5.-Borrar el curso LMS");
			esOperacionCorrecta = this.eliminarCursoLms(cursoWS, idCurso);

			logger.info("El resultado de la operacion de borrar curso del LMS es " + esOperacionCorrecta);

		}

		logger.info("El resultado de la operacion fue de archivo ava es  :" + esOperacionCorrecta);

		return esOperacionCorrecta;

	}

	private TblRespaldosAva armaObjetoTblRespaldosAva(Long usuarioModifico, String nombreCursoConDatos,
			String nombreCursoSinDatos, TblAmbienteVirtualAprendizaje tblAva, String urlCursoConDatos,
			String urlCursoSinDatos) {

		TblRespaldosAva tblRespaldosAva = new TblRespaldosAva();

		tblRespaldosAva.setUrlCursoConDatos(urlCursoConDatos);
		tblRespaldosAva.setUrlCursoSinDatos(urlCursoSinDatos);
		tblRespaldosAva.setActivo(Boolean.TRUE);
		tblRespaldosAva.setFechaActualizacion(new Date());
		tblRespaldosAva.setFechaRegistro(new Date());
		tblRespaldosAva.setUsuarioModifico(BigInteger.valueOf(usuarioModifico));
		tblRespaldosAva.setNombreCursoConDatos(nombreCursoConDatos);
		tblRespaldosAva.setNombreCursoSinDatos(nombreCursoSinDatos);
		tblRespaldosAva.setTblAmbienteVirtualAprendizaje(tblAva);
		tblRespaldosAva.setTblRutaRespaldo(rutaRespaldoServidor);

		return tblRespaldosAva;
	}

	@Transactional(rollbackFor = Exception.class)
	private ResultadoDTO<AmbienteVirtualAprendizajeDTO> guardaInformacionRespaldoAva(TblRespaldosAva tblRespaldosAva,
			AmbienteVirtualAprendizajeDTO ava, Long usuarioModifico) {
		ResultadoDTO<AmbienteVirtualAprendizajeDTO> rs = new ResultadoDTO<AmbienteVirtualAprendizajeDTO>();

		try {
			tblRespaldosAva = respaldosAvaRepo.save(tblRespaldosAva);
			rs = new ResultadoDTO<AmbienteVirtualAprendizajeDTO>();

			ava.setUsuarioModifico(usuarioModifico);
			ava.setFechaActualizacion(new Date());
			ava.setEsAvaArchivado(Boolean.TRUE);
			ResultadoDTO<AmbienteVirtualAprendizajeDTO> resultadoDTO = actualizar(ava);
			logger.info("El resultado de la transaccion de actualizacion de ava Ava archivado fue : "
					+ resultadoDTO.getResultado());

		} catch (Exception e) {
			rs.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
			logger.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return rs;

	}

	private Boolean respaldarCursoEnServidor(String rutaArchivo) {

		Boolean esOperacionExitosa = Boolean.FALSE;
		String servidor = tblUsuariosFtp.getTblInformacionServidor().getIp();
		Integer puerto = 21;
		String user = tblUsuariosFtp.getUsuario();
		String pass = tblUsuariosFtp.getContrasenia();

		String rutaRemota = rutaRespaldoServidor.getDirectorioRuta();

		String nombreArchivo = this.obtenerNombreArchivoApartirDeRutaRelativa(rutaArchivo);

		if (ObjectUtils.isNotNull(nombreArchivo) && nombreArchivo != "") {

			rutaRemota = rutaRemota.concat(nombreArchivo);

			logger.info("Ruta origen archivo :" + rutaArchivo);
			logger.info("Nombre archivo  :" + nombreArchivo);
			logger.info("Rutaremota Archivo   :" + rutaRespaldoServidor);

			UtilidadesCargaArchivoFTP utilidadesCargaArchivoFTP = new UtilidadesCargaArchivoFTP(servidor, puerto, user,
					pass);

			try {
				logger.info("Incia la carga del archivo");
				utilidadesCargaArchivoFTP.cargarArchivo(rutaArchivo, rutaRemota);
				esOperacionExitosa = Boolean.TRUE;
				logger.info("Termina la carga del archivo");
			} catch (IOException e) {
				logger.info(
						"Ocurrio un error en la carga del archivo : " + rutaArchivo + " Hacia la ruta :" + rutaRemota);
				e.printStackTrace();

			}
		}

		return esOperacionExitosa;
	}

	private String obtenerNombreArchivoApartirDeRutaRelativa(String rutaArchivo) {
		String nombreArchivo = null;

		if (ObjectUtils.isNotNull(rutaArchivo) && (rutaArchivo.length() > 0)) {
			String[] cadenaSplitArray = rutaArchivo.split("/");
			logger.info("Tamnio de la cadena : " + cadenaSplitArray.length);
			nombreArchivo = cadenaSplitArray[cadenaSplitArray.length - 1];
		}
		logger.info("nombre del archivo es " + nombreArchivo);

		return nombreArchivo;
	}

	private String descargaArchivoApartirDeUrl(String urlCurso) {

		String rutaTemporal = rutaRespaldoTemporal.getDirectorioRuta();

		UUID uniqueKey = UUID.randomUUID();
		String nombreDelArchivo = uniqueKey.toString();
		StringBuilder ubicacionDelArchvivo = new StringBuilder(rutaTemporal);
		ubicacionDelArchvivo.append(nombreDelArchivo);
		Boolean esDescargaCorrecta = Boolean.FALSE;

		esDescargaCorrecta = UtilidadadDescargaArchivoUrl.descargaArchivoApartirDeUrl(urlCurso,
				ubicacionDelArchvivo.toString());

		if (!esDescargaCorrecta) {
			return null;
		}

		return ubicacionDelArchvivo.toString();

	}

	private Boolean eliminarCursoLms(CursoWS cursoWS, Integer idCurso) {
		Boolean borradoCorrecto = Boolean.FALSE;
		try {
			logger.info("Procedera a borrar el curso del lsm con id" + idCurso);
			cursoWS.borrarCurso(idCurso);
			logger.info("Borro el curso del lsm con exito");
			borradoCorrecto = Boolean.TRUE;
		} catch (ErrorWS e) {
			logger.error("Ocurrio un error al borrar el curso lms");
			e.printStackTrace();
		}

		return borradoCorrecto;
	}

	private String obtenerUrlCursoLms(CursoWS cursoWS, Integer idCurso, Boolean esCursoSinDatos) {

		String urlObtenida = null;

		if (esCursoSinDatos) {

			try {
				logger.info("Obtendra la url del curso Sin datos");
				urlObtenida = cursoWS.respaldoSinDatos(idCurso);
			} catch (ErrorWS e) {
				logger.error("Ocurrio un error en la optencion de la url del curso lms sin datos.");
				e.printStackTrace();
			}

		} else {

			try {
				logger.info("Obtendra la url del curso Con datos");
				urlObtenida = cursoWS.respaldoConConDatos(idCurso);
			} catch (ErrorWS e) {
				logger.error("Ocurrio un error en la optencion de la url del curso lms con datos.");
				e.printStackTrace();
			}
		}

		logger.info("La url es de curson sin datos ? (" + esCursoSinDatos + ") La url obtenida es : " + urlObtenida);

		return urlObtenida;
	}

	@Override
	public String obtenerUrlCursoLms(Long idPersona, Integer idCurso, ParametroWSMoodleDTO parametroWSMoodleDTO) {

		String url = null;

		PersonaDTO personaDTO = personaService.buscarPorId(idPersona);

		LoginWS loginWS = new LoginWS(parametroWSMoodleDTO);

		try {
			url = loginWS.generarAccesoMoodle(personaDTO.getUsuario(), personaDTO.getContrasenia(), idCurso);
		} catch (Exception e) {
			url = parametroWSMoodleDTO.getHost();
			logger.error("Ocurrio un error al obtener la url para ingresar a el curso, se tomara la url por defatult :"
					+ parametroWSMoodleDTO.getHost());
		}

		return url;

	}

	@Override
	public void validarPersistencia(AmbienteVirtualAprendizajeDTO dto,
			ResultadoDTO<AmbienteVirtualAprendizajeDTO> resultado) {
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
	public void validarActualizacion(AmbienteVirtualAprendizajeDTO dto,
			ResultadoDTO<AmbienteVirtualAprendizajeDTO> resultado) {
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
	public void validarEliminacion(AmbienteVirtualAprendizajeDTO dto,
			ResultadoDTO<AmbienteVirtualAprendizajeDTO> resultado) {
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
