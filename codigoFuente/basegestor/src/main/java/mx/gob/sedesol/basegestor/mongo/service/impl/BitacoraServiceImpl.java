package mx.gob.sedesol.basegestor.mongo.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ConexionMongo;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.mongo.model.Bitacora;
import mx.gob.sedesol.basegestor.mongo.repositories.BitacoraRepo;
import mx.gob.sedesol.basegestor.mongo.service.BitacoraEntradaSistemaService;
import mx.gob.sedesol.basegestor.mongo.service.BitacoraService;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import org.bson.Document;
import org.bson.conversions.Bson;

@Service("bitacoraService")
public class BitacoraServiceImpl implements BitacoraService {

	@Autowired
	private BitacoraRepo bitacoraRepo;

	@Autowired
	private ParametroSistemaService parametroSistemaService;

	private static final Logger logger = Logger.getLogger(BitacoraServiceImpl.class);

	private final ModelMapper modelMapper = new ModelMapper();

	private static final Type TIPO_LISTA_BITACORA = new TypeToken<List<BitacoraDTO>>() {
	}.getType();

	@Override
	public List<BitacoraDTO> obtenerBitacora(BitacoraDTO bitacoraFiltroDTO) {
		List<BitacoraDTO> listaBitacora = new ArrayList<>();

		bitacoraFiltroDTO.setFechaInicio(DateUtils.procesarFechaInicio(bitacoraFiltroDTO.getFechaInicio()));
		bitacoraFiltroDTO.setFechaFin(DateUtils.procesarFechaFin(bitacoraFiltroDTO.getFechaFin()));

		Bson query = generarCondiciones(bitacoraFiltroDTO);
		if (ConexionMongo.obtenerBaseDeDatos(parametroSistemaService) != null) {
			FindIterable<Document> bitacoras = ConexionMongo.obtenerBaseDeDatos(parametroSistemaService)
					.getCollection("Bitacoras").find(query);
			MongoCursor<Document> bitacorasIterator;
			bitacorasIterator = bitacoras.iterator();
			List<Bitacora> bitacorasTmp = new ArrayList<>();
			while (bitacorasIterator.hasNext()) {
				bitacorasTmp.add(convertirDocumentoABitacora(bitacorasIterator.next()));
			}
			if (ObjectUtils.isNotNull(bitacoras)) {
				listaBitacora = modelMapper.map(bitacorasTmp, TIPO_LISTA_BITACORA);
			}
		}
		return listaBitacora;
	}

	private Bitacora convertirDocumentoABitacora(Document doc) {
		Bitacora bitacora = new Bitacora();
		bitacora.setIdBitacora(doc.getObjectId("_id").toString());
		bitacora.setIdUsuario(doc.getString("idUsuario"));
		bitacora.setUsuario(doc.getString("usuario"));
		bitacora.setNombreCompleto(doc.getString("nombreCompleto"));
		bitacora.setClaveModulo(doc.getString("claveModulo"));
		bitacora.setModulo(doc.getString("modulo"));
		bitacora.setClaveComponente(doc.getString("claveComponente"));
		bitacora.setComponente(doc.getString("componente"));
		bitacora.setClaveFuncionalidad(doc.getString("claveFuncionalidad"));
		bitacora.setFuncionalidad(doc.getString("funcionalidad"));
		bitacora.setIdElementoAfectado(doc.getString("idElementoAfectado"));
		bitacora.setFechaRegistro(doc.getDate("fechaRegistro"));
		bitacora.setIp(doc.getString("ip"));
		bitacora.setNavegador(doc.getString("navegador"));
		bitacora.setTipoServicio(doc.getString("tipoServicio"));
		return bitacora;
	}

	/**
	 * Si viene el filtro de usuario obtiene el primer y ultimo ingreso al
	 * sistema de ese usuario y se agregan a la bitacora
	 *
	 * @param bitacoraFiltroDTO
	 * @param listaBitacora
	 */

	@Override
	public void guardarBitacora(final BitacoraDTO bitacoraDTO) {

		Bitacora bitacoraDocumento = modelMapper.map(bitacoraDTO, Bitacora.class);
		new Thread(() -> bitacoraRepo.save(bitacoraDocumento)).start();
		//logger.info("Bitacora:" + "Funcionalidad:" + bitacoraDTO.getFuncionalidad() + ". Id afectado: "
		//		+ bitacoraDTO.getIdElementoAfectado());

	}

	/**
	 * Procesa los filtros para generar un query
	 *
	 * @param filtros
	 * @return
	 */
	private BasicDBObject generarCondiciones(BitacoraDTO filtros) {

		BasicDBObject query = new BasicDBObject();
		if (ObjectUtils.isNotNull(filtros.getFechaInicio()) && ObjectUtils.isNotNull(filtros.getFechaFin())) {
			Date fechaInicio = filtros.getFechaInicio();
			Date fechaFin = filtros.getFechaFin();
			query.put("fechaRegistro", BasicDBObjectBuilder.start("$gte", fechaInicio).add("$lte", fechaFin).get());
		} else {
			if (ObjectUtils.isNotNull(filtros.getFechaInicio())) {
				Date fechaInicio = filtros.getFechaInicio();
				query.put("fechaRegistro", BasicDBObjectBuilder.start("$gte", fechaInicio).get());
			}
			if (ObjectUtils.isNotNull(filtros.getFechaFin())) {
				Date fechaFin = filtros.getFechaFin();
				query.put("fechaRegistro", BasicDBObjectBuilder.start("$lte", fechaFin).get());
			}
		}
		if (!ObjectUtils.isNullOrCero(filtros.getIdUsuario())) {
			query.put("idUsuario", filtros.getIdUsuario());
		}
		if (!ObjectUtils.isNullOrEmpty(filtros.getClaveModulo())) {
			query.put("claveModulo", filtros.getClaveModulo());
		}
		if (!ObjectUtils.isNullOrEmpty(filtros.getClaveComponente())) {
			query.put("claveComponente", filtros.getClaveComponente());
		}
		if (!ObjectUtils.isNullOrEmpty(filtros.getClaveFuncionalidad())) {
			query.put("claveFuncionalidad", filtros.getClaveFuncionalidad());
		}
		return query;
	}

}
