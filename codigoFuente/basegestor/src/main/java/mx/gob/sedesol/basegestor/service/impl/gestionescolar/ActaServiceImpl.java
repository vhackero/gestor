package mx.gob.sedesol.basegestor.service.impl.gestionescolar;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ActaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConsultaActaEventoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ActaDocumentoUtils;
import mx.gob.sedesol.basegestor.commons.utils.ConsultaActaUtils;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.Acta;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.IActaRepository;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.ActaService;

/**
 * ACTAS
 * 
 * @author ITTIVA
 * 
 */
@Service("actaService")
public class ActaServiceImpl extends ComunValidacionService<ActaDTO> implements ActaService {

	private static final Logger logger = Logger.getLogger(ActaServiceImpl.class);

	@Autowired
	private IActaRepository iCargaActaRepository;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultadoDTO<ActaDTO> guardar(ActaDTO dto) {
		ResultadoDTO<ActaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			try {
				bloquearGrupo(dto.getGrupo());
				if (!ActaDocumentoUtils.esPdfValido(dto.getBlob())) {
					throw new IllegalArgumentException("El acta debe ser un PDF válido, con páginas y sin contraseña.");
				}
				if (ObjectUtils.isNull(dto.getIdActa()) && ObjectUtils.isNotNull(dto.getGrupo())
						&& iCargaActaRepository.contarPorGrupo(dto.getGrupo()) > 0) {
					resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
					logger.warn("Ya existe un acta asociada al grupo " + dto.getGrupo());
					return resultado;
				}
				logger.info("EJECUTANDO QUERY");
				Acta entidad = new Acta();
				modelMapper.map(dto, entidad);
				iCargaActaRepository.save(entidad);

				logger.info("SAVE CORRECTO");
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

	@Transactional
	@Override
	public ResultadoDTO<ActaDTO> eliminar(ActaDTO dto) {

		ResultadoDTO<ActaDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
		if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
			try {
				logger.info("EJECUTANDO QUERY");
				Acta entidad = new Acta();
				modelMapper.map(dto, entidad);
				iCargaActaRepository.borraPorIdActa(entidad.getIdActa());

				logger.info(">>> ADMIN_MSG_ELIMINACION_EXITOSA");
				resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());

			} catch (Exception e) {
				resultado.setResultado(ResultadoTransaccionEnum.FALLIDO);
				resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
				logger.error(e.getMessage(), e);
				throw e;
			}
		}
		return resultado;
	}

	@Override
	public ActaDTO getActaByGrupoUser(int idGrupo, long idUser) {
		logger.info("EJECUTANDO QUERY descargaActa");
		List<Acta> optional = iCargaActaRepository.getActaByIdGrupoUser(idGrupo, idUser);

		if (optional.size() > 0) {
			return modelMapper.map(optional.get(0), ActaDTO.class);
		} else {
			return null;
		}

	}

	@Override
	public ActaDTO getActaByIdGrupo(int idGrupo) {
		List<Acta> optional = iCargaActaRepository.getActaByIdGrupo(idGrupo);

		if (optional.size() > 0) {
			return modelMapper.map(optional.get(0), ActaDTO.class);
		} else {
			return null;
		}

	}

	@Override
	public List<ActaDTO> getActasByIdGrupo(int idGrupo) {
		List<ActaDTO> documentos = new ArrayList<ActaDTO>();
		for (Object[] fila : iCargaActaRepository.consultarMetadatos(idGrupo)) { documentos.add(metadatos(fila)); }
		return documentos;
	}

	private ActaDTO metadatos(Object[] fila) {
		ActaDTO documento = new ActaDTO();
		documento.setIdActa(((Number) fila[0]).intValue());
		documento.setGrupo(((Number) fila[1]).intValue());
		documento.setFechaCierre((Date) fila[2]);
		documento.setUsuarioModifico(fila[3] == null ? null : ((Number) fila[3]).longValue());
		documento.setTamanioArchivo(((Number) fila[4]).longValue());
		documento.setEstructuraPdfValida(((Number) fila[5]).intValue() == 1);
		return documento;
	}

	@Override
	@Transactional(propagation = org.springframework.transaction.annotation.Propagation.MANDATORY)
	public void bloquearGrupo(Integer idGrupo) {
		if (idGrupo == null || iCargaActaRepository.bloquearGrupo(idGrupo) == null) {
			throw new IllegalArgumentException("No fue posible identificar el grupo.");
		}
	}

	@Override
	public ConsultaActaEventoDTO consultarEstadoGrupo(Integer idGrupo) {
		List<Object[]> registros = iCargaActaRepository.consultarEstadoGrupo(idGrupo);
		if (registros.isEmpty()) { throw new IllegalArgumentException("El grupo ya no existe."); }
		return ConsultaActaUtils.desdeRegistro(registros.get(0), 0);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void retirarDocumento(Integer idActa, Integer idGrupo) {
		bloquearGrupo(idGrupo);
		buscarDocumentoDelGrupo(idActa, idGrupo);
		iCargaActaRepository.borraPorIdActa(idActa);
		iCargaActaRepository.flush();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void guardarDocumento(Integer idActa, Integer idGrupo, byte[] archivo, Long usuario) {
		bloquearGrupo(idGrupo);
		if (!ActaDocumentoUtils.esPdfValido(archivo)) {
			throw new IllegalArgumentException("Seleccione un PDF válido, con páginas y sin contraseña.");
		}
		Acta acta;
		if (idActa == null) {
			if (iCargaActaRepository.contarPorGrupo(idGrupo) > 0) {
				throw new IllegalArgumentException("Ya existe un documento. Seleccione el que desea reemplazar.");
			}
			acta = new Acta();
			acta.setGrupo(idGrupo);
		} else {
			buscarDocumentoDelGrupo(idActa, idGrupo);
			if (iCargaActaRepository.reemplazarDocumento(idActa, idGrupo, archivo, new Date(), usuario) != 1) {
				throw new IllegalArgumentException("El documento cambió. Actualice la consulta.");
			}
			return;
		}
		acta.setBlob(archivo);
		acta.setFechaCierre(new Date());
		acta.setUsuarioModifico(usuario);
		iCargaActaRepository.saveAndFlush(acta);
	}

	private ActaDTO buscarDocumentoDelGrupo(Integer idActa, Integer idGrupo) {
		List<Object[]> filas = idActa == null ? java.util.Collections.<Object[]>emptyList()
				: iCargaActaRepository.consultarMetadatosPorId(idActa);
		ActaDTO documento = filas.isEmpty() ? null : metadatos(filas.get(0));
		if (documento == null || !idGrupo.equals(documento.getGrupo())) {
			throw new IllegalArgumentException("El documento ya no existe o no pertenece al grupo seleccionado.");
		}
		return documento;
	}

	@Override
	public List<Long> consultarIdsAlumnos(Integer idGrupo) {
		List<Long> ids = new ArrayList<Long>();
		for (Number id : iCargaActaRepository.consultarIdsAlumnos(idGrupo)) { ids.add(id.longValue()); }
		return ids;
	}

	@Override
	public List<ActaDTO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActaDTO buscarPorId(Integer id) {
		List<Object[]> filas = iCargaActaRepository.consultarMetadatosPorId(id);
		if (filas.isEmpty()) { return null; }
		ActaDTO documento = metadatos(filas.get(0));
		if (documento.getTamanioArchivo() > 10485760L) {
			throw new IllegalArgumentException("El documento supera 10 MB. Reemplácelo por un PDF de hasta 10 MB.");
		}
		documento.setBlob(iCargaActaRepository.consultarArchivoAcotado(id));
		return documento;
	}

	@Override
	public ResultadoDTO<ActaDTO> actualizar(ActaDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validarPersistencia(ActaDTO dto, ResultadoDTO<ActaDTO> resultado) {
		// TODO Auto-generated method stub

	}

	@Override
	public void validarActualizacion(ActaDTO dto, ResultadoDTO<ActaDTO> resultado) {
		// TODO Auto-generated method stub

	}

	@Override
	public void validarEliminacion(ActaDTO dto, ResultadoDTO<ActaDTO> resultado) {
		// TODO Auto-generated method stub

	}

}
