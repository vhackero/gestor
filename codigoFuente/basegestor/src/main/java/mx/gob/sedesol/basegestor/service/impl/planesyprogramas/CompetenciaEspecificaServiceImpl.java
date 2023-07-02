package mx.gob.sedesol.basegestor.service.impl.planesyprogramas;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.CompetenciaEspecificaDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatCompetenciaEspecifica;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.CompetenciaEspecificaRepo;
import mx.gob.sedesol.basegestor.mongo.service.AdministradorBitacora;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.CompetenciaEspecificaService;

@Service("competenciaEspecificaService")
public class CompetenciaEspecificaServiceImpl extends ComunValidacionService<CompetenciaEspecificaDTO> implements CompetenciaEspecificaService {

    private static final Logger logger = Logger.getLogger(CompetenciaEspecificaServiceImpl.class);

    @Autowired
    private CompetenciaEspecificaRepo competenciaEspecificaRepo;

    private ModelMapper competenciaEspMapper = new ModelMapper();

    @Override
    public List<CompetenciaEspecificaDTO> findAll() {
        Type lstType = new TypeToken<List<CompetenciaEspecificaDTO>>() {
        }.getType();
        return competenciaEspMapper.map(competenciaEspecificaRepo.findAll(), lstType);
    }

    @Override
    public boolean estaVacio(Integer id, String nombre) {
        List<CatCompetenciaEspecifica> lista = competenciaEspecificaRepo.estaVacio(id, nombre);
        return lista.isEmpty();
    }

    @Override
    public CompetenciaEspecificaDTO buscarPorId(Integer id) {
        return competenciaEspMapper.map(competenciaEspecificaRepo.findOne(id), CompetenciaEspecificaDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<CompetenciaEspecificaDTO> guardar(CompetenciaEspecificaDTO dto) {

        ResultadoDTO<CompetenciaEspecificaDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                CatCompetenciaEspecifica compEsp = competenciaEspMapper.map(dto, CatCompetenciaEspecifica.class);
                competenciaEspecificaRepo.save(compEsp);

                res = new ResultadoDTO<CompetenciaEspecificaDTO>();
                res.setDto(competenciaEspMapper.map(compEsp, CompetenciaEspecificaDTO.class));

            //GUSTAVO --guardarBitacoraCompetencia(dto, String.valueOf(compEsp.getId()));
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
            throw e;
        }
        return res;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<CompetenciaEspecificaDTO> actualizar(CompetenciaEspecificaDTO dto) {

        ResultadoDTO<CompetenciaEspecificaDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                CatCompetenciaEspecifica compEsp = competenciaEspMapper.map(dto, CatCompetenciaEspecifica.class);
                competenciaEspecificaRepo.save(compEsp);

                res = new ResultadoDTO<CompetenciaEspecificaDTO>();
                res.setDto(competenciaEspMapper.map(compEsp, CompetenciaEspecificaDTO.class));

            //GUSTAVO --guardarBitacoraCompetencia(dto, String.valueOf(compEsp.getId()));
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
            throw e;
        }
        return res;
    }

    @Override
    public ResultadoDTO<CompetenciaEspecificaDTO> eliminar(CompetenciaEspecificaDTO dto) {
        ResultadoDTO<CompetenciaEspecificaDTO> res = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        try {
            if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                CatCompetenciaEspecifica compEsp = competenciaEspMapper.map(dto, CatCompetenciaEspecifica.class);
                competenciaEspecificaRepo.delete(compEsp);
                res = new ResultadoDTO<CompetenciaEspecificaDTO>();
                res.setDto(competenciaEspMapper.map(compEsp, CompetenciaEspecificaDTO.class));

            //GUSTAVO --guardarBitacoraCompetencia(dto, String.valueOf(compEsp.getId()));
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
        }
        return res;
    }

    @Override
    public ResultadoDTO<CompetenciaEspecificaDTO> sonDatosRequeridosValidos(TipoAccion accion,
            CompetenciaEspecificaDTO dto) {
        ResultadoDTO<CompetenciaEspecificaDTO> res = null;

        if (ObjectUtils.isNotNull(dto)) {
            res = new ResultadoDTO<CompetenciaEspecificaDTO>();

            switch (accion) {
                case PERSISTENCIA:

                    if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
                        res.setMensajeError(MensajesSistemaEnum.COMPETENCIA_ESPECIFICA_NOMBRE_REQ);
                    }
                    break;
                case ELIMINACION:

                    if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
                        res.setMensajeError(MensajesSistemaEnum.COMPETENCIA_ESPECIFICA_NOMBRE_REQ);
                    }
                    break;
            }
        }
        return res;
    }

    @Override
    public void validarPersistencia(CompetenciaEspecificaDTO dto, ResultadoDTO<CompetenciaEspecificaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(CompetenciaEspecificaDTO dto, ResultadoDTO<CompetenciaEspecificaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(CompetenciaEspecificaDTO dto, ResultadoDTO<CompetenciaEspecificaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
