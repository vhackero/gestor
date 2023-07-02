package mx.gob.sedesol.basegestor.service.impl.logisticainfraestructura;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.logisticainfraestructura.RecursosInfraestructuraDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.logisticainfraestructura.CatRecursosInfraestructura;
import mx.gob.sedesol.basegestor.model.repositories.logisticainfraestructura.RecursosInfraestructuraRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.logisticainfraestructura.RecursosInfraestructuraService;

@Service("recursosInfraestructuraService")
public class RecursosInfraestructuraServiceImpl extends ComunValidacionService<RecursosInfraestructuraDTO> implements RecursosInfraestructuraService {

    private static final long serialVersionUID = -438928934704941440L;

    private static final Logger logger = Logger.getLogger(RecursosInfraestructuraServiceImpl.class);

    @Autowired
    private RecursosInfraestructuraRepo recursosInfraestructuraRepo;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public List<RecursosInfraestructuraDTO> obtieneRecursosPorTipoDeRecurso(Integer idTipoRecurso) {
        try {
            List<CatRecursosInfraestructura> recursos = recursosInfraestructuraRepo
                    .obtieneRecursosPorTipoDeRecurso(idTipoRecurso);
            List<RecursosInfraestructuraDTO> listR = new ArrayList<>();
            for (CatRecursosInfraestructura pr : recursos) {
                pr.setRelAreaRecursos(null);
                RecursosInfraestructuraDTO rDTO = new RecursosInfraestructuraDTO();
                mapper.map(pr, rDTO);
                listR.add(rDTO);
            }
            return listR;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    @Override
    public List<RecursosInfraestructuraDTO> findAll() {
        List<CatRecursosInfraestructura> recursos = recursosInfraestructuraRepo.findAll();
        List<RecursosInfraestructuraDTO> listR = new ArrayList<>();
        for (CatRecursosInfraestructura pr : recursos) {
            pr.setRelAreaRecursos(null);
            RecursosInfraestructuraDTO rDTO = new RecursosInfraestructuraDTO();
            mapper.map(pr, rDTO);
            listR.add(rDTO);
        }
        return listR;
    }

    @Override
    public RecursosInfraestructuraDTO buscarPorId(Integer id) {
        return new RecursosInfraestructuraDTO();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<RecursosInfraestructuraDTO> guardar(RecursosInfraestructuraDTO dto) {
        ResultadoDTO<RecursosInfraestructuraDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                CatRecursosInfraestructura recursos = mapper.map(dto, CatRecursosInfraestructura.class);
                recursos = recursosInfraestructuraRepo.save(recursos);
                res.setDto(mapper.map(recursos, RecursosInfraestructuraDTO.class));
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
        }
        return res;

    }

    @Override
    public ResultadoDTO<RecursosInfraestructuraDTO> actualizar(RecursosInfraestructuraDTO dto) {
        return new ResultadoDTO<>();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<RecursosInfraestructuraDTO> eliminar(RecursosInfraestructuraDTO dto) {
        ResultadoDTO<RecursosInfraestructuraDTO> res = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
            	
                CatRecursosInfraestructura recInf = mapper.map(dto, CatRecursosInfraestructura.class);
                recursosInfraestructuraRepo.delete(recInf);
                res = new ResultadoDTO<>();
                res.setDto(mapper.map(recInf, RecursosInfraestructuraDTO.class));
            }else{
            	res.setResultado(ResultadoTransaccionEnum.FALLIDO);
            	res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setResultado(ResultadoTransaccionEnum.FALLIDO);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
        }
        return res;
    }

    @Override
    public ResultadoDTO<RecursosInfraestructuraDTO> sonDatosRequeridosValidos(TipoAccion accion,
            RecursosInfraestructuraDTO dto) {
        ResultadoDTO<RecursosInfraestructuraDTO> resultado = null;

        if (ObjectUtils.isNotNull(dto)) {
            resultado = new ResultadoDTO<>();

            switch (accion) {
                case PERSISTENCIA:
                    validarPersistencia(dto, resultado);
                    break;

                case ELIMINACION:
                    validarEliminacion(dto, resultado);
                    break;

                default:
                    resultado.setMensajeError(MensajesSistemaEnum.LOGISTICA_INFRAESTRUCTURA_MSG_OPERACION_REQ);
                    break;
            }
        }
        return resultado;
    }

    @Override
    public void validarPersistencia(RecursosInfraestructuraDTO dto,
            ResultadoDTO<RecursosInfraestructuraDTO> resultado) {
        if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
            resultado.setMensajeError(MensajesSistemaEnum.RECURSOS_INFRAESTRUCTURA_NOMBRE_REQ);
        }
        if (ObjectUtils.isNullOrEmpty(dto.getCatTipoRecurso())) {
            resultado.setMensajeError(MensajesSistemaEnum.RECURSOS_INFRAESTRUCTURA_ID_TIPO_RECURSO_REQ);
        }
    }

    @Override
    public void validarEliminacion(RecursosInfraestructuraDTO dto,
            ResultadoDTO<RecursosInfraestructuraDTO> resultado) {
        if (ObjectUtils.isNullOrEmpty(dto.getIdRecurso())) {
            resultado.setMensajeError(MensajesSistemaEnum.RECURSOS_INFRAESTRUCTURA_ID_REQ);
        }
    }

    public RecursosInfraestructuraRepo getRecursosInfraestructuraRepo() {
        return recursosInfraestructuraRepo;
    }

    public void setRecursosInfraestructuraRepo(RecursosInfraestructuraRepo recursosInfraestructuraRepo) {
        this.recursosInfraestructuraRepo = recursosInfraestructuraRepo;
    }

    @Override
    public void validarActualizacion(RecursosInfraestructuraDTO dto, ResultadoDTO<RecursosInfraestructuraDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
