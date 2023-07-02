package mx.gob.sedesol.basegestor.service.impl.planesyprogramas;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.EstPersonalExternoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblEstPersonalExterno;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.EstPersonalExternoRepo;
import mx.gob.sedesol.basegestor.mongo.service.AdministradorBitacora;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.EstPersonalExternoService;

@Service("estPersonalExternoService")
public class EstPersonalExternoServiceImpl extends ComunValidacionService<EstPersonalExternoDTO> implements EstPersonalExternoService {

    private static final Logger logger = Logger.getLogger(EstPersonalExternoService.class);

    @Autowired
    private EstPersonalExternoRepo estPersonalExternoRepo;
    private ModelMapper personaExtMapper = new ModelMapper();

    @Override
    public List<EstPersonalExternoDTO> findAll() {
        Type lstAux = new TypeToken<List<EstPersonalExternoDTO>>() {
        }.getType();
        return personaExtMapper.map(estPersonalExternoRepo.findAll(), lstAux);
    }

    @Override
    public EstPersonalExternoDTO buscarPorId(Integer id) {

        return personaExtMapper.map(estPersonalExternoRepo.findOne(id), EstPersonalExternoDTO.class);
    }

    @Override
    public ResultadoDTO<EstPersonalExternoDTO> guardar(EstPersonalExternoDTO dto) {

        ResultadoDTO<EstPersonalExternoDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

            try {

                TblEstPersonalExterno estExterna = personaExtMapper.map(dto, TblEstPersonalExterno.class);
                estExterna = estPersonalExternoRepo.save(estExterna);
                res = new ResultadoDTO<EstPersonalExternoDTO>();
                res.setDto(personaExtMapper.map(estExterna, EstPersonalExternoDTO.class));

            //GUSTAVO --guardarBitacoraEstPersonal(dto, String.valueOf(estExterna.getId()));

            } catch (Exception e) {
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
        return res;
    }

    @Override
    public ResultadoDTO<EstPersonalExternoDTO> actualizar(EstPersonalExternoDTO dto) {

        ResultadoDTO<EstPersonalExternoDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

            try {

                TblEstPersonalExterno estExterna = personaExtMapper.map(dto, TblEstPersonalExterno.class);
                estExterna = estPersonalExternoRepo.saveAndFlush(estExterna);
                res = new ResultadoDTO<EstPersonalExternoDTO>();
                res.setDto(personaExtMapper.map(estExterna, EstPersonalExternoDTO.class));

            //GUSTAVO --guardarBitacoraEstPersonal(dto, String.valueOf(estExterna.getId()));

            } catch (Exception e) {
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
        return res;
    }

    @Override
    public ResultadoDTO<EstPersonalExternoDTO> eliminar(EstPersonalExternoDTO dto) {
        ResultadoDTO<EstPersonalExternoDTO> res = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

            try {

                TblEstPersonalExterno estExterna = personaExtMapper.map(dto, TblEstPersonalExterno.class);
                estPersonalExternoRepo.delete(estExterna);
                res = new ResultadoDTO<EstPersonalExternoDTO>();
                //res.setDto(orgGubMapper.map(orgEntidad, OrgGubernamentalDTO.class));
            //GUSTAVO --guardarBitacoraEstPersonal(dto, String.valueOf(estExterna.getId()));

            } catch (Exception e) {
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
        return res;
    }

    @Override
    public EstPersonalExternoDTO obtenerEstPersonalExternoPorId(Integer id) {
        List<TblEstPersonalExterno> lstAux = estPersonalExternoRepo.obtenerOrgGubernamentalPorId(id);

        if (!ObjectUtils.isNullOrEmpty(lstAux)) {
            TblEstPersonalExterno entidad = lstAux.get(ConstantesGestor.PRIMER_ELEMENTO);

            return personaExtMapper.map(entidad, EstPersonalExternoDTO.class);
        }

        return null;
    }

    @Override
    public List<EstPersonalExternoDTO> obtenerEstPersonalExtPadres() {

        Type lstAux = new TypeToken<List<EstPersonalExternoDTO>>() {
        }.getType();
        List<TblEstPersonalExterno> padres = estPersonalExternoRepo.obtenerEstPersonalExtPadres();
        if (ObjectUtils.isNotNull(padres)) {
            return personaExtMapper.map(padres, lstAux);
        }

        return null;
    }

    @Override
    public ResultadoDTO<EstPersonalExternoDTO> sonDatosRequeridosValidos(TipoAccion accion, EstPersonalExternoDTO dto) {

        ResultadoDTO<EstPersonalExternoDTO> resultado = null;

        if (ObjectUtils.isNotNull(dto)) {
            resultado = new ResultadoDTO<EstPersonalExternoDTO>();

            switch (accion) {
                case PERSISTENCIA:

                    if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
                    }

                    if (ObjectUtils.isNull(dto.getFechaRegistro())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
                    }

                    break;

                case ELIMINACION:
                    if (ObjectUtils.isNull(dto.getId())) {
                        resultado.setMensajeError(MensajesSistemaEnum.ORG_GUB_ID_REQ);
                    }
                    break;

                case ACTUALIZACION:

                    if (ObjectUtils.isNull(dto.getId())) {
                        resultado.setMensajeError(MensajesSistemaEnum.ORG_GUB_ID_REQ);
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
    public void validarPersistencia(EstPersonalExternoDTO dto, ResultadoDTO<EstPersonalExternoDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(EstPersonalExternoDTO dto, ResultadoDTO<EstPersonalExternoDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(EstPersonalExternoDTO dto, ResultadoDTO<EstPersonalExternoDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
