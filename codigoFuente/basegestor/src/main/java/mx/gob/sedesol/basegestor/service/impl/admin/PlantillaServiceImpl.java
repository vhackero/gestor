package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.PlantillaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.commons.utils.TipoDocumentoEnum;
import mx.gob.sedesol.basegestor.model.entities.admin.TblPlantilla;
import mx.gob.sedesol.basegestor.model.repositories.admin.PlantillaRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.PlantillaService;

@Service("plantillaService")
public class PlantillaServiceImpl extends ComunValidacionService<PlantillaDTO> implements PlantillaService {

    private static final Logger logger = Logger.getLogger(PlantillaServiceImpl.class);

    @Autowired
    private PlantillaRepo plantillaRepo;

    private ModelMapper plantillaMaper = new ModelMapper();

    Type tipoListaPlantilla = new TypeToken<List<PlantillaDTO>>() {
    }.getType();

    @Override
    public String obtenerFondoDocumentoPorTipoDocumento(TipoDocumentoEnum tipoDocumento) {
        List<TblPlantilla> lstPlantillas = plantillaRepo.obtenerPlantillasPorTipoDocumentoEstatus(tipoDocumento.getValor(), true);
        if (lstPlantillas.isEmpty()) {
            return null;
        } else {
            return lstPlantillas.get(ConstantesGestor.PRIMER_ELEMENTO).getImagenFondo();
        }
    }

    @Override
    public PlantillaDTO obtenerPlantillaPorTipoDocumento(TipoDocumentoEnum tipoDocumento) {
        List<TblPlantilla> lstPlantillas = plantillaRepo.obtenerPlantillasPorTipoDocumentoEstatus(tipoDocumento.getValor(), true);
        if (lstPlantillas.isEmpty()) {
            return null;
        } else {
            return plantillaMaper.map(lstPlantillas.get(ConstantesGestor.PRIMER_ELEMENTO), PlantillaDTO.class);
        }
    }

    @Override
    public List<PlantillaDTO> findAll() {

        List<TblPlantilla> lstPlantillas = plantillaRepo.findAll();

        return plantillaMaper.map(lstPlantillas, tipoListaPlantilla);
    }

    @Override
    public List<PlantillaDTO> obtenerPlantillasPorTipoDocumento(Integer tipoDocumento) {
        List<TblPlantilla> lstPlantillas = plantillaRepo.obtenerPlantillasPorTipoDocumento(tipoDocumento);
        return plantillaMaper.map(lstPlantillas, tipoListaPlantilla);
    }

    @Override
    public PlantillaDTO buscarPorId(Long id) {
        PlantillaDTO plantillaDTO;
        TblPlantilla entidad = plantillaRepo.findOne(id);
        if (ObjectUtils.isNull(entidad)) {
            plantillaDTO = null;
        } else {
            plantillaDTO = plantillaMaper.map(entidad, PlantillaDTO.class);
        }
        return plantillaDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultadoDTO<PlantillaDTO> guardar(PlantillaDTO plantillaDto) {

        ResultadoDTO<PlantillaDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, plantillaDto);
        if (res.getResultado().getValor()) {
            try {
                res = new ResultadoDTO<>();
                TblPlantilla entidadPersistir = plantillaMaper.map(plantillaDto, TblPlantilla.class);
                entidadPersistir = plantillaRepo.save(entidadPersistir);
                res.setDto(plantillaMaper.map(entidadPersistir, PlantillaDTO.class));

                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
                res.setResultado(ResultadoTransaccionEnum.FALLIDO);
            }
        }
        return res;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultadoDTO<PlantillaDTO> actualizar(PlantillaDTO plantillaDto) {

        ResultadoDTO<PlantillaDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, plantillaDto);
        if (res.getResultado().getValor()) {
            try {

                res = new ResultadoDTO<>();
                TblPlantilla entidadAct = plantillaMaper.map(plantillaDto, TblPlantilla.class);
                entidadAct = plantillaRepo.saveAndFlush(entidadAct);
                res.setDto(plantillaMaper.map(entidadAct, PlantillaDTO.class));

                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
                res.setResultado(ResultadoTransaccionEnum.FALLIDO);
            }
        }
        return res;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultadoDTO<PlantillaDTO> eliminar(PlantillaDTO plantillaDto) {

        ResultadoDTO<PlantillaDTO> res = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, plantillaDto);
        if (res.getResultado().getValor()) {
            try {
                res = new ResultadoDTO<>();
                TblPlantilla plantilla = plantillaMaper.map(plantillaDto, TblPlantilla.class);
                plantillaRepo.delete(plantilla);
        //GUSTAVO --guardarBitacora(plantillaDto.getBitacoraDTO(), String.valueOf(plantilla.getIdPlantilla()));
                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
            }
        }

        return res;
    }

    @Override
    public void validarPersistencia(PlantillaDTO plantillaDto, ResultadoDTO<PlantillaDTO> res) {
        if (ObjectUtils.isNullOrEmpty(plantillaDto.getNombre())) {
            res.setMensajeError(MensajesSistemaEnum.PLANTILLA_DOC_NOMBRE_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(plantillaDto.getUsuarioCreo())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
    }

    @Override
    public void validarActualizacion(PlantillaDTO plantillaDto, ResultadoDTO<PlantillaDTO> res) {
        if (ObjectUtils.isNullOrEmpty(plantillaDto.getNombre())) {
            res.setMensajeError(MensajesSistemaEnum.PLANTILLA_DOC_NOMBRE_REQ);
        }
        if (ObjectUtils.isNullOrCero(plantillaDto.getUsuarioModifico())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
        if (ObjectUtils.isNull(plantillaDto.getFechaActualizacion())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
        }
    }

    @Override
    public void validarEliminacion(PlantillaDTO plantillaDto, ResultadoDTO<PlantillaDTO> res) {
        if (ObjectUtils.isNull(plantillaDto.getFechaActualizacion())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(plantillaDto.getUsuarioModifico())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
    }
}
