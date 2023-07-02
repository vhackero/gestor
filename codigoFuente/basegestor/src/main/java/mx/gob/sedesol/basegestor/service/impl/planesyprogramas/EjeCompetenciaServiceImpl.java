package mx.gob.sedesol.basegestor.service.impl.planesyprogramas;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelEjeCompetenciaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.RelEjeCompetencia;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.EjeCompetenciaRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.EjeCompetenciaService;

@Service("ejeCompetenciaService")
public class EjeCompetenciaServiceImpl extends ComunValidacionService<RelEjeCompetenciaDTO> implements EjeCompetenciaService {

    private static final Logger logger = Logger.getLogger(EjeCompetenciaServiceImpl.class);

    @Autowired
    private EjeCompetenciaRepo ejeCompetenciaRepo;

    private ModelMapper ejeCompMapper = new ModelMapper();

    @Override
    public List<RelEjeCompetenciaDTO> findAll() {
        return new ArrayList<>();
    }

    @Override
    public RelEjeCompetenciaDTO buscarPorId(Integer id) {
        return new RelEjeCompetenciaDTO();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<RelEjeCompetenciaDTO> guardar(RelEjeCompetenciaDTO dto) {

        ResultadoDTO<RelEjeCompetenciaDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                RelEjeCompetencia ejeComp = ejeCompMapper.map(dto, RelEjeCompetencia.class);
                ejeComp = ejeCompetenciaRepo.save(ejeComp);

                res = new ResultadoDTO<RelEjeCompetenciaDTO>();
                res.setDto(ejeCompMapper.map(ejeComp, RelEjeCompetenciaDTO.class));

            //GUSTAVO --guardarBitacoraEjeCompetencia(dto, String.valueOf(ejeComp.getIdCompetenciaEspecifica()));
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
            throw e;
        }
        return res;
    }

    @Override
    public ResultadoDTO<RelEjeCompetenciaDTO> actualizar(RelEjeCompetenciaDTO dto) {
        return new ResultadoDTO<>();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<RelEjeCompetenciaDTO> eliminar(RelEjeCompetenciaDTO dto) {

        ResultadoDTO<RelEjeCompetenciaDTO> res = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                RelEjeCompetencia ejeComp = ejeCompMapper.map(dto, RelEjeCompetencia.class);
                ejeCompetenciaRepo.delete(ejeComp);
                res = new ResultadoDTO<RelEjeCompetenciaDTO>();
                res.setDto(ejeCompMapper.map(ejeComp, RelEjeCompetenciaDTO.class));

            //GUSTAVO --guardarBitacoraEjeCompetencia(dto, String.valueOf(ejeComp.getIdCompetenciaEspecifica()));
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
        }
        return res;
    }

    @Override
    public ResultadoDTO<RelEjeCompetenciaDTO> sonDatosRequeridosValidos(TipoAccion accion, RelEjeCompetenciaDTO dto) {
        ResultadoDTO<RelEjeCompetenciaDTO> resultado = null;

        if (ObjectUtils.isNotNull(dto)) {
            resultado = new ResultadoDTO<RelEjeCompetenciaDTO>();

            switch (accion) {
                case PERSISTENCIA:

                    if (ObjectUtils.isNullOrEmpty(dto.getIdCompetenciaEspecifica())) {
                        resultado.setMensajeError(MensajesSistemaEnum.EJE_COMPETENCIA_ID_COMP_ESPECIFICA_REQ);
                    }

                    if (ObjectUtils.isNullOrEmpty(dto.getIdMallaCurricular())) {
                        resultado.setMensajeError(MensajesSistemaEnum.EJE_COMPETENCIA_ID_MALLA_CURR_REQ);
                    }
                    break;

                case ELIMINACION:
                    if (ObjectUtils.isNullOrEmpty(dto.getIdCompetenciaEspecifica())) {
                        resultado.setMensajeError(MensajesSistemaEnum.EJE_COMPETENCIA_ID_COMP_ESPECIFICA_REQ);
                    }

                    if (ObjectUtils.isNullOrEmpty(dto.getIdMallaCurricular())) {
                        resultado.setMensajeError(MensajesSistemaEnum.EJE_COMPETENCIA_ID_MALLA_CURR_REQ);
                    }
                    break;
            }
        }
        return resultado;
    }

    @Override
    public List<RelEjeCompetenciaDTO> obtenerCompetenciasEspecificasPorEje(Integer idEje) {

        try {
            Type listAux = new TypeToken<List<RelEjeCompetenciaDTO>>() {
            }.getType();
            List<RelEjeCompetencia> competencias = ejeCompetenciaRepo.obtieneCompetenciasEspecificasPorEje(idEje);

            return ejeCompMapper.map(competencias, listAux);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public List<RelEjeCompetenciaDTO> obtieneCompetenciasEspecificasNoAsignadas(Integer idEje) {

        try {
            Type listAux = new TypeToken<List<RelEjeCompetenciaDTO>>() {
            }.getType();
            List<RelEjeCompetencia> competencias = ejeCompetenciaRepo.obtieneCompetenciasEspecificasNoAsignadas(idEje);

            return ejeCompMapper.map(competencias, listAux);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new ArrayList<>();
    }



    @Override
    public void validarPersistencia(RelEjeCompetenciaDTO dto, ResultadoDTO<RelEjeCompetenciaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(RelEjeCompetenciaDTO dto, ResultadoDTO<RelEjeCompetenciaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(RelEjeCompetenciaDTO dto, ResultadoDTO<RelEjeCompetenciaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
