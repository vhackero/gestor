package mx.gob.sedesol.basegestor.service.impl.admin;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.repositories.admin.CatalogoComunRepo;
import mx.gob.sedesol.basegestor.service.admin.CatalogoComunService;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;

@Primary
@Service("catalogoComunService")
public class CatalogoComunServiceImpl<A, ID extends Serializable> extends ComunValidacionService<CatalogoComunDTO> implements CatalogoComunService<A, ID> {

    private static final Logger logger = Logger.getLogger(CatalogoComunServiceImpl.class);

    @Autowired
    private CatalogoComunRepo<A, ID> catalogoComunRepo;

    private ModelMapper catComunMapper = new ModelMapper();

    @Override
    public List<CatalogoComunDTO> findAll(Class<A> classEntidad) {

        catalogoComunRepo.setEntityClass(classEntidad);
        List<A> lstaux = catalogoComunRepo.findAll();
        Type lstEnt = new TypeToken<List<CatalogoComunDTO>>() {
        }.getType();
        return catComunMapper.map(lstaux, lstEnt);

    }

    @Override
    public CatalogoComunDTO buscarPorId(ID id, Class<A> classEntidad) {
        logger.info("Buscar por id");
        catalogoComunRepo.setEntityClass(classEntidad);
        A entidad = catalogoComunRepo.findOne(id);
        CatalogoComunDTO cat;
        if (ObjectUtils.isNull(entidad)) {
            cat = null;
        } else {
            cat = catComunMapper.map(entidad, CatalogoComunDTO.class);
        }
        return cat;
    }

    /**
     *
     * @param catDto
     * @param classEntidad
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<CatalogoComunDTO> guardar(CatalogoComunDTO catDto, Class<A> classEntidad) throws Exception {

        ResultadoDTO<CatalogoComunDTO> resultado = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, catDto);
        if (resultado.getResultado().getValor()) {
            try {

                resultado = new ResultadoDTO<>();
                A entidad = catComunMapper.map(catDto, classEntidad);
                entidad = catalogoComunRepo.save(entidad);
                resultado.setDto(catComunMapper.map(entidad, CatalogoComunDTO.class));

                logger.info(resultado.getDto().getId().toString() + " "
                        + classEntidad.getSimpleName());

                //GUSTAVO --guardarBitacoraComun(catDto, String.valueOf(entidad));

                resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());
            } catch (Exception e) {
                resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
                logger.error(e.getMessage(), e);
            }
        }
        return resultado;
    }

    /**
     * @param catDto
     * @param classEntidad
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<CatalogoComunDTO> actualizar(CatalogoComunDTO catDto, Class<A> classEntidad) throws Exception {

        ResultadoDTO<CatalogoComunDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, catDto);
        if (resultado.getResultado().getValor()) {
            try {

                resultado = new ResultadoDTO<>();
                A entidad = catComunMapper.map(catDto, classEntidad);
                catalogoComunRepo.saveAndFlush(entidad);
                resultado.setDto(catComunMapper.map(entidad, CatalogoComunDTO.class));

                logger.info(resultado.getDto().getId().toString() + " "
                        + classEntidad.getSimpleName());

                //GUSTAVO --guardarBitacoraComun(catDto, String.valueOf(entidad));

                resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
            } catch (Exception e) {
                resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
                logger.error(e.getMessage(), e);
            }
        }
        return resultado;
    }

    /**
     * @param catDto
     * @param classEntidad
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<CatalogoComunDTO> eliminaLogicamente(CatalogoComunDTO catDto, Class<A> classEntidad)
            throws Exception {

        ResultadoDTO<CatalogoComunDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, catDto);
        if (resultado.getResultado().getValor()) {
            try {
                resultado = new ResultadoDTO<>();
                catDto.setActivo(ConstantesGestor.INACTIVO);
                A entidad = catComunMapper.map(catDto, classEntidad);
                catalogoComunRepo.saveAndFlush(entidad);
                resultado.setDto(catComunMapper.map(entidad, CatalogoComunDTO.class));

                logger.info(resultado.getDto().getId().toString() + " "
                        + classEntidad.getSimpleName());

                //GUSTAVO --guardarBitacoraComun(catDto, String.valueOf(entidad));

                resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
            } catch (Exception e) {
                resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
                logger.error(e.getMessage(), e);
            }
        }

        return resultado;
    }

    /**
     * @param catDto
     * @param classEntidad
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<CatalogoComunDTO> eliminar(CatalogoComunDTO catDto, Class<A> classEntidad) throws Exception {
        ResultadoDTO<CatalogoComunDTO> resultado = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, catDto);
        if (resultado.getResultado().getValor()) {
            try {

                resultado = new ResultadoDTO<>();
                A entidad = catalogoComunRepo.findOne((ID) catDto.getId());
                catalogoComunRepo.delete(entidad);
                resultado.setDto(catComunMapper.map(entidad, CatalogoComunDTO.class));

                logger.info(resultado.getDto().getId().toString() + " "
                        + classEntidad.getSimpleName());

                //GUSTAVO --guardarBitacoraComun(catDto, String.valueOf(entidad));

                resultado.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
            } catch (Exception e) {
                resultado.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
                logger.error(e.getMessage(), e);
            }
        }

        return resultado;
    }

    /**
     *
     */
    @Override
    public CatalogoComunDTO buscarRegistroPorNombre(String nombre, Class<A> classEntidad) {

        catalogoComunRepo.setEntityClass(classEntidad);
        A entidad;

        try {
            entidad = catalogoComunRepo.buscarRegistroPorNombre(nombre);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            entidad = null;
        }

        CatalogoComunDTO cat;
        if (ObjectUtils.isNull(entidad)) {
            cat = null;
        } else {
            cat = catComunMapper.map(entidad, CatalogoComunDTO.class);
        }
        return cat;
    }

    /**
     *
     * @param accion
     * @param catDto
     * @return
     */
    @Override
    public ResultadoDTO<CatalogoComunDTO> sonDatosRequeridosValidos(TipoAccion accion, CatalogoComunDTO catDto) {
        ResultadoDTO<CatalogoComunDTO> res = new ResultadoDTO<>();

        if (ObjectUtils.isNotNull(catDto)) {

            switch (accion) {
                case PERSISTENCIA:

                    validarGuardar(catDto, res);

                    break;

                case ACTUALIZACION:

                    validarActualizacion(catDto, res);

                    break;

                case ELIMINACION:

                    validarEliminacion(catDto, res);
                    break;

            }
        } else {
            res.setResultado(ResultadoTransaccionEnum.FALLIDO);
        }

        return res;
    }

    private void validarGuardar(CatalogoComunDTO catDto, ResultadoDTO<CatalogoComunDTO> res) {
        if (ObjectUtils.isNullOrEmpty(catDto.getNombre())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_NOMBRE_REQ);
        }
        if (ObjectUtils.isNull(catDto.getFechaRegistro())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(catDto.getUsuarioModifico())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
    }

    @Override
    public void validarActualizacion(CatalogoComunDTO catDto, ResultadoDTO<CatalogoComunDTO> res) {
        if (ObjectUtils.isNullOrEmpty(catDto.getId())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_ID_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(catDto.getUsuarioModifico())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
        if (ObjectUtils.isNull(catDto.getFechaActualizacion())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
        }
    }

    @Override
    public void validarEliminacion(CatalogoComunDTO catDto, ResultadoDTO<CatalogoComunDTO> res) {
        if (ObjectUtils.isNullOrEmpty(catDto.getId())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_ID_REQ);
        }
        if (ObjectUtils.isNull(catDto.getFechaActualizacion())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
        }
    }

    private void guardarBitacoraComun(CatalogoComunDTO dto, String registro) {
        //Usar la clave/id del registro guardado
    	 //GUSTAVO --dto.getBitacoraDTO().setRegistro(registro);
        //Guarda la bitácora
        //GUSTAVO --guardarBitacora(dto.getBitacoraDTO());
    }

    @Override
    public void validarPersistencia(CatalogoComunDTO dto, ResultadoDTO<CatalogoComunDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
