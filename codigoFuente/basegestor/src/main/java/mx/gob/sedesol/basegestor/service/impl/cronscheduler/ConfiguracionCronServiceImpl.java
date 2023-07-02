package mx.gob.sedesol.basegestor.service.impl.cronscheduler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.cronscheduler.CronConfDTO;
import mx.gob.sedesol.basegestor.commons.dto.cronscheduler.ResultadoRespuestaCronDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.admin.TblCfgCron;
import mx.gob.sedesol.basegestor.model.repositories.cronscheduler.ConfiguracionCronRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.cronscheduler.ConfiguracionCronService;

@Service("configuracionCronService")
public class ConfiguracionCronServiceImpl extends ComunValidacionService<CronConfDTO>
		implements ConfiguracionCronService {

	private static final Logger logger = Logger.getLogger(ConfiguracionCronServiceImpl.class);

	private static final RestTemplate template = new RestTemplate();

	/**
	 * Inyeccion del repo de configuracion cron *
	 */
	@Autowired
	private ConfiguracionCronRepo configuracionCronRepo;

	/**
	 * Inyeccion de propiedades
	 *
	 *
	 */
	@Value("${cron.scheduler.ip}")
	private String ip;
	@Value("${cron.scheduler.puerto}")
	private String puerto;
	@Value("${cron.scheduler.web.contexto}")
	private String contextoWeb;
	@Value("${cron.scheduler.web.ws.contexto}")
	private String contextoWs;
	@Value("${cron.scheduler.web.ws.metodo.actualizaPatronEjecucion}")
	private String metodoWs;

	/**
	 * Model mapper
	 */
	private ModelMapper mapper = new ModelMapper();

	@Override
	public List<CronConfDTO> obtenerTareasProgramadas() {

		List<TblCfgCron> tblCfgCronList = configuracionCronRepo.findAll();
		Type objetoDTO = new TypeToken<List<CronConfDTO>>() {
		}.getType();
		return mapper.map(tblCfgCronList, objetoDTO);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResultadoDTO<CronConfDTO> guardar(CronConfDTO dto) {

		ResultadoDTO<CronConfDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			try {
				resultado = new ResultadoDTO<CronConfDTO>();

				TblCfgCron entidad = mapper.map(dto, TblCfgCron.class);
				TblCfgCron entidadRespuesta = configuracionCronRepo.save(entidad);
				resultado.setDto(mapper.map(entidadRespuesta, CronConfDTO.class));

				resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());

			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
				logger.error(e.getMessage(), e);
				throw e;
			}
		}

		return resultado;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ResultadoDTO<CronConfDTO> actualizar(CronConfDTO dto) {

		ResultadoDTO<CronConfDTO> rs = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
		if (ObjectUtils.isNotNull(rs) && rs.getResultado().getValor()) {
			try {

				TblCfgCron tblCfgCron = mapper.map(dto, TblCfgCron.class);

				tblCfgCron = configuracionCronRepo.saveAndFlush(tblCfgCron);
				rs = new ResultadoDTO<CronConfDTO>();

				rs.setDto(mapper.map(tblCfgCron, CronConfDTO.class));

				rs.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());

			} catch (Exception e) {
				rs.setResultado(ResultadoTransaccionEnum.FALLIDO);
				rs.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
				logger.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		}
		return rs;

	}

	@Override
	public CronConfDTO obtenerCronProClave(String clave) {
		CronConfDTO cronConfDTO = null;
		try {
			TblCfgCron tblCfgCron = configuracionCronRepo.obtenerCronProClave(clave);

			cronConfDTO = mapper.map(tblCfgCron, CronConfDTO.class);

		} catch (Exception e) {
			logger.error("Ocurrio un error al obtener la configuracion del cron el objeto CronConfDTO se enviara null");
			e.printStackTrace();

		}

		return cronConfDTO;
	}

	@Override
	public ResultadoRespuestaCronDTO actualizaPatronCronAvaWs(String claveCron) {
		ObjectMapper om = new ObjectMapper();
		ResultadoRespuestaCronDTO resultadoRespuestaCronDTO = null;
		String urlStr = "http://" + ip + ":" + puerto + "/" + contextoWeb + "/" + contextoWs + "/" + metodoWs + "/"
				+ claveCron;

		logger.info("La url generada es " + urlStr);

		try {
			String json = template.getForObject(urlStr, String.class);
			resultadoRespuestaCronDTO = om.readValue(json, ResultadoRespuestaCronDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Ocurrio un error al invocar el es ");

		}

		logger.info("El resultado es " + resultadoRespuestaCronDTO);

		return resultadoRespuestaCronDTO;
	}

	public ResultadoDTO<CronConfDTO> sonDatosRequeridosValidos(TipoAccion accion, CronConfDTO dto) {

		ResultadoDTO<CronConfDTO> resultado = null;

		if (ObjectUtils.isNotNull(dto)) {
			resultado = new ResultadoDTO<CronConfDTO>();

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
				if (ObjectUtils.isNull(dto.getIdCfgCron())) {
					resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_ID_REQ);
				}
				break;

			case ACTUALIZACION:

				if (ObjectUtils.isNull(dto.getIdCfgCron())) {
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
	public void validarPersistencia(CronConfDTO dto, ResultadoDTO<CronConfDTO> resultado) {
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
	public void validarActualizacion(CronConfDTO dto, ResultadoDTO<CronConfDTO> resultado) {
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
	public void validarEliminacion(CronConfDTO dto, ResultadoDTO<CronConfDTO> resultado) {
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
