package mx.gob.sedesol.basegestor.service.impl.planesyprogramas;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.MallaCurricularDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblMallaCurricular;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.MallaCurricularRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.MallaCurricularService;

@Service("mallaCurricularService")
public class MallaCurricularServiceImpl extends ComunValidacionService<MallaCurricularDTO> implements MallaCurricularService {

    private static final Logger log = Logger.getLogger(MallaCurricularServiceImpl.class);

    @Autowired
    private MallaCurricularRepo mallaCurricularRepo;
    private ModelMapper mallaCurrMapper = new ModelMapper();

    /**
     * Metodo que regresa la lista de los planes registrados en
     * TblMallaCurricular
     */
    @Override
    public List<MallaCurricularDTO> findAll() {

        List<TblMallaCurricular> lstPlanes = mallaCurricularRepo.findAll();
        Type lstAux = new TypeToken<List<MallaCurricularDTO>>() {
        }.getType();

        return mallaCurrMapper.map(lstPlanes, lstAux);
    }

    /**
     * Servicio que busca un registro por identificador en la entidad
     * TblMallaCurricular
     */
    @Override
    public MallaCurricularDTO buscarPorId(Integer id) {

        TblMallaCurricular mallaCurr = mallaCurricularRepo.findOne(id);
        if (ObjectUtils.isNotNull(mallaCurr)) {
            return mallaCurrMapper.map(mallaCurr, MallaCurricularDTO.class);
        }

        return null;
    }

    public ResultadoDTO<MallaCurricularDTO> guardar(MallaCurricularDTO dto) {

        ResultadoDTO<MallaCurricularDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                TblMallaCurricular mallaCurr = mallaCurrMapper.map(dto, TblMallaCurricular.class);
                mallaCurr = mallaCurricularRepo.save(mallaCurr);

                res = new ResultadoDTO<MallaCurricularDTO>();
                res.setDto(mallaCurrMapper.map(mallaCurr, MallaCurricularDTO.class));
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res.setResultado(ResultadoTransaccionEnum.FALLIDO);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
            throw e;
        }
        return res;
    }

    @Override
    public ResultadoDTO<MallaCurricularDTO> actualizar(MallaCurricularDTO dto) {
        ResultadoDTO<MallaCurricularDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                TblMallaCurricular mallaCurr = mallaCurrMapper.map(dto, TblMallaCurricular.class);
                mallaCurr = mallaCurricularRepo.saveAndFlush(mallaCurr);
                res = new ResultadoDTO<MallaCurricularDTO>();
                res.setDto(mallaCurrMapper.map(mallaCurr, MallaCurricularDTO.class));
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
        }
        return res;
    }

    @Transactional
    public ResultadoDTO<MallaCurricularDTO> eliminar(MallaCurricularDTO dto) {
        ResultadoDTO<MallaCurricularDTO> res = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                TblMallaCurricular mallaCurr = mallaCurrMapper.map(dto, TblMallaCurricular.class);
                mallaCurricularRepo.delete(mallaCurr);
                res = new ResultadoDTO<MallaCurricularDTO>();
                res.setDto(mallaCurrMapper.map(mallaCurr, MallaCurricularDTO.class));

            //GUSTAVO --guardarBitacoraMalla(dto, String.valueOf(mallaCurr.getId()));
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
        }
        return res;
    }

    /**
     * Obtiene el registro de Malla curricular y sus objetos hijos por
     * identificador
     */
    public MallaCurricularDTO obtenerMallaCurricularPorId(Integer id) {
        List<TblMallaCurricular> lstAux = mallaCurricularRepo.buscarMallaCurricularPorId(id);

        if (!ObjectUtils.isNullOrEmpty(lstAux)) {
            TblMallaCurricular entidad = lstAux.get(ConstantesGestor.PRIMER_ELEMENTO);

            return mallaCurrMapper.map(entidad, MallaCurricularDTO.class);
        }

        return null;
    }

    public MallaCurricularDTO obtenerMallaCurricularPorIdPlan(Integer idPlan) {
        List<TblMallaCurricular> lstAux = mallaCurricularRepo.buscarMallaCurricularPorIdPlan(idPlan);

        if (!ObjectUtils.isNullOrEmpty(lstAux)) {
            TblMallaCurricular entidad = lstAux.get(ConstantesGestor.PRIMER_ELEMENTO);

            return mallaCurrMapper.map(entidad, MallaCurricularDTO.class);
        }

        return null;
    }

    /**
     *
     */
    public List<MallaCurricularDTO> obtieneMallasCurricularesDisponibles() {

        List<MallaCurricularDTO> mallas = null;
        List<TblMallaCurricular> lstAuxMallas = mallaCurricularRepo.findAll();

        if (!ObjectUtils.isNullOrEmpty(lstAuxMallas)) {
            mallas = new ArrayList<>();
            for (TblMallaCurricular obj : lstAuxMallas) {
                if (ObjectUtils.isNotNull(obj) && ObjectUtils.isNotNull(obj.getActivo())
                        && obj.getActivo().equals(ConstantesGestor.ACTIVO)) {
                    if (ObjectUtils.isNull(obj.getMallaCurricularPadre())) {
                        MallaCurricularDTO objPadre = new MallaCurricularDTO();
                        objPadre = mallaCurrMapper.map(obj, MallaCurricularDTO.class);
                        mallas.add(objPadre);
                    }

                }
            }
        }
        return mallas;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Integer getNuevoIdMallaCurricular() {
        return mallaCurricularRepo.getMaxIdMallaCurricular() + 1;
    }

    @Override
    public ResultadoDTO<MallaCurricularDTO> sonDatosRequeridosValidos(TipoAccion accion, MallaCurricularDTO dto) {

        ResultadoDTO<MallaCurricularDTO> resultado = null;

        if (ObjectUtils.isNotNull(dto)) {
            resultado = new ResultadoDTO<MallaCurricularDTO>();

            switch (accion) {
                case PERSISTENCIA:

                    if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MALLA_CURR_NOMBRE_REQ);
                    }

                    if (ObjectUtils.isNull(dto.getFechaRegistro())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
                    }
                    if (ObjectUtils.isNullOrEmpty(dto.getObjetoCurricular())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MALLA_CURR_TPO_OBJ_CURR_REQ);
                    }
                    break;

                case ELIMINACION:
                    if (ObjectUtils.isNull(dto.getId())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MALLA_CURR_ID_REQ);
                    }
                    break;

                case ACTUALIZACION:

                    if (ObjectUtils.isNull(dto.getId())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MALLA_CURR_ID_REQ);
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
    public void validarPersistencia(MallaCurricularDTO dto, ResultadoDTO<MallaCurricularDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(MallaCurricularDTO dto, ResultadoDTO<MallaCurricularDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(MallaCurricularDTO dto, ResultadoDTO<MallaCurricularDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
