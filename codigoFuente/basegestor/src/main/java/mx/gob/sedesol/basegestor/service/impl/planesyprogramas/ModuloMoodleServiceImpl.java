package mx.gob.sedesol.basegestor.service.impl.planesyprogramas;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.ModuloMoodleDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.MdlModulo;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.ModuloMoodleRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.ModuloMoodleService;

@Service("catModuloMoodleService")
public class ModuloMoodleServiceImpl extends ComunValidacionService<ModuloMoodleDTO> implements ModuloMoodleService {

    private static final Logger logger = Logger.getLogger(ModuloMoodleServiceImpl.class);

    @Autowired
    private ModuloMoodleRepo moduloMoodleRepo;

    private ModelMapper modulosMdlMapper = new ModelMapper();

    @Override
    public List<ModuloMoodleDTO> findAll() {
        Type lstAux = new TypeToken<List<ModuloMoodleDTO>>() {
        }.getType();
        return modulosMdlMapper.map(moduloMoodleRepo.findAll(), lstAux);
    }

    @Override
    public ModuloMoodleDTO buscarPorId(Integer id) {
        return modulosMdlMapper.map(moduloMoodleRepo.findOne(id), ModuloMoodleDTO.class);
    }

    @Override
    public List<ModuloMoodleDTO> buscarPorTipoDeModulo(Integer tipoModulo) {
        Type lstAux = new TypeToken<List<ModuloMoodleDTO>>() {
        }.getType();
        return modulosMdlMapper.map(moduloMoodleRepo.obtieneModulosPorTipo(tipoModulo), lstAux);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<ModuloMoodleDTO> guardar(ModuloMoodleDTO dto) {

        ResultadoDTO<ModuloMoodleDTO> r = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (ObjectUtils.isNotNull(r) && r.getResultado().getValor()) {

            try {
                MdlModulo estTem = modulosMdlMapper.map(dto, MdlModulo.class);
                estTem = moduloMoodleRepo.save(estTem);
                r = new ResultadoDTO<ModuloMoodleDTO>();
                r.setDto(modulosMdlMapper.map(estTem, ModuloMoodleDTO.class));
            //GUSTAVO --guardarBitacoraModulo(dto, String.valueOf(estTem.getId()));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                r.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
                throw e;
            }
        }
        return r;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<ModuloMoodleDTO> actualizar(ModuloMoodleDTO dto) {

        ResultadoDTO<ModuloMoodleDTO> r = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        if (ObjectUtils.isNotNull(r) && r.getResultado().getValor()) {
            try {
                MdlModulo estTem = modulosMdlMapper.map(dto, MdlModulo.class);
                estTem = moduloMoodleRepo.saveAndFlush(estTem);
                r = new ResultadoDTO<ModuloMoodleDTO>();
                r.setDto(modulosMdlMapper.map(estTem, ModuloMoodleDTO.class));
            //GUSTAVO --guardarBitacoraModulo(dto, String.valueOf(estTem.getId()));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                r.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
                throw e;
            }
        }
        return r;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<ModuloMoodleDTO> eliminar(ModuloMoodleDTO dto) {
        ResultadoDTO<ModuloMoodleDTO> r = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (ObjectUtils.isNotNull(r) && r.getResultado().getValor()) {

            try {
                MdlModulo estTem = modulosMdlMapper.map(dto, MdlModulo.class);
                moduloMoodleRepo.delete(estTem);
                r = new ResultadoDTO<ModuloMoodleDTO>();
            //GUSTAVO --guardarBitacoraModulo(dto, String.valueOf(estTem.getId()));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                r.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
                throw e;
            }
        }
        return r;
    }

    @Override
    public ResultadoDTO<ModuloMoodleDTO> sonDatosRequeridosValidos(TipoAccion accion, ModuloMoodleDTO dto) {

        ResultadoDTO<ModuloMoodleDTO> resultado = null;

        if (ObjectUtils.isNotNull(dto)) {
            resultado = new ResultadoDTO<ModuloMoodleDTO>();

            switch (accion) {
                case PERSISTENCIA:

                    if (ObjectUtils.isNullOrEmpty(dto.getNombre())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MDL_MODULOS_NOMBRE_REQ);
                    }

                    break;

                case ELIMINACION:
                    if (ObjectUtils.isNull(dto.getId())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MDL_MODULOS_ID_REQ);
                    }
                    break;

                case ACTUALIZACION:

                    if (ObjectUtils.isNull(dto.getId())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MDL_MODULOS_ID_REQ);
                    }
                    break;
            }
        }
        return resultado;
    }

    @Override
    public void validarPersistencia(ModuloMoodleDTO dto, ResultadoDTO<ModuloMoodleDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(ModuloMoodleDTO dto, ResultadoDTO<ModuloMoodleDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(ModuloMoodleDTO dto, ResultadoDTO<ModuloMoodleDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
