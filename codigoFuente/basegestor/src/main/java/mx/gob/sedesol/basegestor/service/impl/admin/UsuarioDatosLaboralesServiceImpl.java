package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.UsuarioDatosLaboralesDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.admin.RelUsuarioDatosLaborales;
import mx.gob.sedesol.basegestor.model.especificaciones.DatosLaboralesEspecificacion;
import mx.gob.sedesol.basegestor.model.repositories.admin.UsuarioDatosLaboralesRepo;
import mx.gob.sedesol.basegestor.service.admin.UsuarioDatosLaboralesService;

@Service("usuarioDatosLaboralesService")
public class UsuarioDatosLaboralesServiceImpl implements UsuarioDatosLaboralesService {

    private static final Logger logger = Logger.getLogger(UsuarioDatosLaboralesServiceImpl.class);

    @Autowired
    private UsuarioDatosLaboralesRepo usuarioDatosLaboralesRepo;

    private ModelMapper mapper = new ModelMapper();

    @Autowired
    private UsuarioDatosLaboralesRepo repo;

    Type tipoListaUsuarioDatosLaborales = new TypeToken<List<UsuarioDatosLaboralesDTO>>() {
    }.getType();

    @Override
    public List<UsuarioDatosLaboralesDTO> busquedaDatosLaboralesPorCriterios(UsuarioDatosLaboralesDTO criterios) {
        List<UsuarioDatosLaboralesDTO> lista = new ArrayList<>();
        try {
            if (ObjectUtils.isNotNull(criterios)) {

                List<RelUsuarioDatosLaborales> resultadoCriterios = usuarioDatosLaboralesRepo.findAll(new DatosLaboralesEspecificacion(criterios));

                lista = mapper.map(resultadoCriterios, tipoListaUsuarioDatosLaborales);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return lista;
    }

    @Override
    public List<UsuarioDatosLaboralesDTO> findAll() {
        List<UsuarioDatosLaboralesDTO> lista = new ArrayList<>();
        try {
            List<RelUsuarioDatosLaborales> resultadoCriterios = usuarioDatosLaboralesRepo.findAll();

            lista = mapper.map(resultadoCriterios, tipoListaUsuarioDatosLaborales);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return lista;
    }

    @Override
    public UsuarioDatosLaboralesDTO buscarPorId(Integer id) {
        RelUsuarioDatosLaborales entidad = repo.findOne(id.longValue());
        UsuarioDatosLaboralesDTO dto;
        if (ObjectUtils.isNull(entidad)) {
            dto = null;
        } else {
            dto = mapper.map(entidad, UsuarioDatosLaboralesDTO.class);
        }
        return dto;
    }

    @Override
    public ResultadoDTO<UsuarioDatosLaboralesDTO> guardar(UsuarioDatosLaboralesDTO dto) {
        return new ResultadoDTO<>();
    }

    @Override
    public ResultadoDTO<UsuarioDatosLaboralesDTO> actualizar(UsuarioDatosLaboralesDTO dto) {
        return new ResultadoDTO<>();
    }

    @Override
    public ResultadoDTO<UsuarioDatosLaboralesDTO> eliminar(UsuarioDatosLaboralesDTO dto) {
        return new ResultadoDTO<>();
    }

    @Override
    public ResultadoDTO<UsuarioDatosLaboralesDTO> sonDatosRequeridosValidos(TipoAccion accion,
            UsuarioDatosLaboralesDTO dto) {
        return new ResultadoDTO<>();
    }

    @Override
    public UsuarioDatosLaboralesDTO obtenerDatosLaboralesPorPersona(Long idPersona) {
        List<RelUsuarioDatosLaborales> datosLaborales = repo.obtenerDatosLaboralesPorPersona(idPersona);
        UsuarioDatosLaboralesDTO datosLaboralesDTO;
        if (ObjectUtils.isNullOrEmpty(datosLaborales)) {
            datosLaboralesDTO = null;
        } else {
            datosLaboralesDTO = mapper.map(datosLaborales.get(ConstantesGestor.PRIMER_ELEMENTO), UsuarioDatosLaboralesDTO.class);
        }
        return datosLaboralesDTO;
    }

    public UsuarioDatosLaboralesRepo getRepo() {
        return repo;
    }

    public void setRepo(UsuarioDatosLaboralesRepo repo) {
        this.repo = repo;
    }
}
