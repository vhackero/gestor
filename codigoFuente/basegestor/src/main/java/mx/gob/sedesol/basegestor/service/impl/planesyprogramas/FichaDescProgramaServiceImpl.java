package mx.gob.sedesol.basegestor.service.impl.planesyprogramas;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.RelProgDuracionDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.RelProgramaCargaHoraria;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblFichaDescriptivaPrograma;
import mx.gob.sedesol.basegestor.model.especificaciones.FichaProgramaEspecificacion;
import mx.gob.sedesol.basegestor.model.repositories.planesyprogramas.FichaDescProgramaRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.planesyprogramas.FichaDescProgramaService;

@Service("fichaDescProgramaService")
public class FichaDescProgramaServiceImpl extends ComunValidacionService<FichaDescProgramaDTO> implements FichaDescProgramaService {

    private static final Logger logger = Logger.getLogger(FichaDescProgramaServiceImpl.class);

    @Autowired
    private FichaDescProgramaRepo fichaDescProgramaRepo;
    private ModelMapper fichaDescProgMapper = new ModelMapper();

    @Override
    public List<FichaDescProgramaDTO> findAll() {
        Type listAux = new TypeToken<List<FichaDescProgramaDTO>>() {
        }.getType();

        List<TblFichaDescriptivaPrograma> lista = fichaDescProgramaRepo.findAll();
        return fichaDescProgMapper.map(lista, listAux);
    }

    /**
     *
     */
    public List<FichaDescProgramaDTO> buscaProgramasPorCriterios(FichaDescProgramaDTO filtro) {

        try {
            //Type listAux = new TypeToken<List<FichaDescProgramaDTO>>(){}.getType();
            List<FichaDescProgramaDTO> list = new ArrayList<>();;
            FichaProgramaEspecificacion fichaEsp = new FichaProgramaEspecificacion(filtro);
            List<TblFichaDescriptivaPrograma> res = fichaDescProgramaRepo.findAll(fichaEsp);

            if (!ObjectUtils.isNullOrEmpty(res)) {
                for (TblFichaDescriptivaPrograma prog : res) {
                    FichaDescProgramaDTO fp = new FichaDescProgramaDTO();
                    fichaDescProgMapper.map(prog, fp);
                    list.add(fp);
                }
            }

            return list;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }

    }

    /**
     * Metodo que busca programas con los datos necesarios {id, nombre,
     * modalidad, nivel, tipocompetencia, eje, estatus, fechaActualizacion}
     * (**Se pueden agregar mas*)
     *
     * @param filtro
     * @return
     */
    public List<FichaDescProgramaDTO> buscaProgramasPorCriteriosDatosBasicos(FichaDescProgramaDTO filtro) {
        List<FichaDescProgramaDTO> lista = new ArrayList<>();
        try {
            FichaProgramaEspecificacion fichaEsp = new FichaProgramaEspecificacion(filtro);
            List<TblFichaDescriptivaPrograma> res = fichaDescProgramaRepo.findAll(fichaEsp);
            for (TblFichaDescriptivaPrograma programa : res) {
                FichaDescProgramaDTO dto = new FichaDescProgramaDTO();
                dto.setIdPrograma(programa.getIdPrograma());
                dto.setNombreTentativo(programa.getNombreTentativo());
                dto.setCatModalidad(fichaDescProgMapper.map(programa.getCatModalidad(), CatalogoComunDTO.class));
                dto.setCatNivelEnsenanzaPrograma(
                        fichaDescProgMapper.map(programa.getCatNivelConocimiento(), CatalogoComunDTO.class));
                dto.setTipoCompetencia(programa.getTipoCompetencia());
                dto.setEjeCapacitacion(programa.getEjeCapacitacion());
                dto.setCatStatusPrograma(
                        fichaDescProgMapper.map(programa.getCatStatusPrograma(), CatalogoComunDTO.class));
                dto.setFechaActualizacion(programa.getFechaActualizacion());
                dto.setCalificacionMinAprobatoria(programa.getCalificacionMinAprobatoria());
                dto.setRelProgramaDuracion(new ArrayList<>());
                for (RelProgramaCargaHoraria carga : programa.getRelProgramaDuracion()) {
                    RelProgDuracionDTO programaDuracion = new RelProgDuracionDTO();
                    programaDuracion.setHoras(programaDuracion.getHoras());
                    programaDuracion.setMinutos(programaDuracion.getMinutos());
                    dto.getRelProgramaDuracion().add(programaDuracion);
                }
                lista.add(dto);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            lista = new ArrayList<>();
        }
        return lista;
    }

    /**
     *
     */
    public List<FichaDescProgramaDTO> consultaUltimosProgramas() {

        Pageable topTen = new PageRequest(0, 10);
        List<TblFichaDescriptivaPrograma> res = fichaDescProgramaRepo.consultaUltimosProgramas(topTen);
        //Type listAux = new TypeToken<List<FichaDescProgramaDTO>>(){}.getType();
        List<FichaDescProgramaDTO> list = new ArrayList<>();
        if (!ObjectUtils.isNullOrEmpty(res)) {
            for (TblFichaDescriptivaPrograma prog : res) {
                FichaDescProgramaDTO fp = new FichaDescProgramaDTO();
                fichaDescProgMapper.map(prog, fp);
                list.add(fp);
            }
        }

        return list;
    }

    /**
     *
     */
    public List<FichaDescProgramaDTO> consultaProgramasPorEstatus(Integer idEstatus) {

        List<FichaDescProgramaDTO> list = null;
        List<TblFichaDescriptivaPrograma> res = fichaDescProgramaRepo.consultaProgramasPorEstatus(idEstatus);
        if (!ObjectUtils.isNullOrEmpty(res)) {
            list = new ArrayList<>();
            for (TblFichaDescriptivaPrograma prog : res) {
                FichaDescProgramaDTO fp = new FichaDescProgramaDTO();
                fichaDescProgMapper.map(prog, fp);
                list.add(fp);
            }
        }

        return list;
    }

    /**
     * Se utiliza Java Reflection para asignar nulo las relaciones (Many to One)
     * con la tabla principal ya que de no hacerlo se cicla el objeto mapper.
     *
     */
    @Override
    public FichaDescProgramaDTO buscarPorId(Integer id) {

        TblFichaDescriptivaPrograma res = fichaDescProgramaRepo.findOne(id);
        try {
            Method[] metodos = res.getClass().getDeclaredMethods();
            for (Method m : metodos) {
                Class<?> typeClass = m.getReturnType();
                if (typeClass.equals(java.util.List.class)) {
                    List<?> dataList = (List<?>) m.invoke(res);
                    if (dataList != null && !dataList.isEmpty()) {
                        for (Object obj : dataList) {
                            Field[] fields = obj.getClass().getDeclaredFields();
                            for (Field f : fields) {
                                if (f.getType().equals(TblFichaDescriptivaPrograma.class)) {
                                    f.setAccessible(true);
                                    f.set(obj, null);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return fichaDescProgMapper.map(res, FichaDescProgramaDTO.class);
    }

    @Override
    public ResultadoDTO<FichaDescProgramaDTO> guardar(FichaDescProgramaDTO dto) {
        ResultadoDTO<FichaDescProgramaDTO> rs = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (ObjectUtils.isNotNull(rs) && rs.getResultado().getValor()) {
            try {

                TblFichaDescriptivaPrograma entityForSave = new TblFichaDescriptivaPrograma();
                //TblFichaDescriptivaPrograma programa = fichaDescProgMapper.map(dto, entityForSave);
                fichaDescProgMapper.map(dto, entityForSave);
                entityForSave = fichaDescProgramaRepo.save(entityForSave);

                rs = new ResultadoDTO<FichaDescProgramaDTO>();
                FichaDescProgramaDTO dtoSvd = new FichaDescProgramaDTO();
                fichaDescProgMapper.map(entityForSave, dtoSvd);
                rs.setDto(dtoSvd);


                //GUSTAVO --guardarBitacoraFicha(dto, String.valueOf(entityForSave.getIdPrograma()));

            } catch (Exception e) {
                rs.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
        return rs;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<FichaDescProgramaDTO> actualizar(FichaDescProgramaDTO dto) {
        ResultadoDTO<FichaDescProgramaDTO> rs = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        if (ObjectUtils.isNotNull(rs) && rs.getResultado().getValor()) {
            try {

                TblFichaDescriptivaPrograma programa = new TblFichaDescriptivaPrograma();

                fichaDescProgMapper.map(dto, programa);

                if (ObjectUtils.isNotNull(programa.getOrganismoGubernamental()) && ObjectUtils.isNull(programa.getOrganismoGubernamental().getId())) {
                    programa.setOrganismoGubernamental(null);
                }

                programa = fichaDescProgramaRepo.saveAndFlush(programa);
                rs = new ResultadoDTO<FichaDescProgramaDTO>();
                FichaDescProgramaDTO programaSaved = new FichaDescProgramaDTO();

                FichaDescProgramaDTO progAntDto = null;
                if (ObjectUtils.isNotNull(programa.getProgramaAntecedente())) {
                    TblFichaDescriptivaPrograma progAnteced = programa.getProgramaAntecedente();
                    progAntDto = new FichaDescProgramaDTO();
                    fichaDescProgMapper.map(progAnteced, progAntDto);
                }

                fichaDescProgMapper.map(programa, programaSaved);
                programaSaved.setProgramaAntecedente(progAntDto);
                rs.setDto(programaSaved);

                //GUSTAVO --guardarBitacoraFicha(dto, String.valueOf(programa.getIdPrograma()));


            } catch (Exception e) {
                rs.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
                logger.error(e.getMessage(), e);
            }
        }
        return rs;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<FichaDescProgramaDTO> eliminar(FichaDescProgramaDTO dto) {
        ResultadoDTO<FichaDescProgramaDTO> rs = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (ObjectUtils.isNotNull(rs) && rs.getResultado().getValor()) {
            try {

                fichaDescProgramaRepo.delete(dto.getIdPrograma());
                rs = new ResultadoDTO<FichaDescProgramaDTO>();

                //GUSTAVO --guardarBitacoraFicha(dto, String.valueOf(dto.getIdPrograma()));


            } catch (Exception e) {
                rs.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
                logger.error(e.getMessage(), e);
            }
        }
        return rs;
    }

    @Override
    public ResultadoDTO<FichaDescProgramaDTO> sonDatosRequeridosValidos(TipoAccion accion, FichaDescProgramaDTO dto) {

        ResultadoDTO<FichaDescProgramaDTO> resultado = null;

        if (ObjectUtils.isNotNull(dto)) {
            resultado = new ResultadoDTO<FichaDescProgramaDTO>();

            switch (accion) {
                case PERSISTENCIA:

                    if (ObjectUtils.isNullOrEmpty(dto.getFechaRegistro())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
                    }
                    if (ObjectUtils.isNull(dto.getUsuarioModifico())) {
                        resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
                    }
                    break;

                case ELIMINACION:
                    if (ObjectUtils.isNull(dto.getIdPrograma())) {
                        resultado.setMensajeError(MensajesSistemaEnum.FICHA_DESC_PROG_ID_REQ);
                    }
                    break;

                case ACTUALIZACION:

                    if (ObjectUtils.isNull(dto.getIdPrograma())) {
                        resultado.setMensajeError(MensajesSistemaEnum.FICHA_DESC_PROG_ID_REQ);
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
    public List<FichaDescProgramaDTO> consultarProgramasPorTCompYEjeCap(Integer tipoCompetencia,
            Integer ejeCapacitacion, Integer idEstatusPrograma) {

        //Type listAux = new TypeToken<List<FichaDescProgramaDTO>>(){}.getType();
        List<TblFichaDescriptivaPrograma> res = fichaDescProgramaRepo.consultarProgramasPorTCompYEjeCap(tipoCompetencia, ejeCapacitacion, idEstatusPrograma);

        List<FichaDescProgramaDTO> list = null;

        if (!ObjectUtils.isNullOrEmpty(res)) {
            list = new ArrayList<>();
            for (TblFichaDescriptivaPrograma prog : res) {
                FichaDescProgramaDTO fp = new FichaDescProgramaDTO();
                fichaDescProgMapper.map(prog, fp);
                list.add(fp);
            }
        }

        return list;
    }

    /**
     *
     */
    public Integer totalProgramasActivosByEstatus(Integer idEstatus) {
        return fichaDescProgramaRepo.totalProgramasActivosByEstatus(idEstatus);
    }

    public Integer totalProgramas() {
        return fichaDescProgramaRepo.totalProgramas();
    }

    @Override
    public void eliminaRelProgPersonalExtPorIdPrograma(Integer idPrograma) {
        fichaDescProgramaRepo.eliminaRelProgPersonalExtPorIdPrograma(idPrograma);
    }

    @Override
    public void eliminaRelProgResponsablesIdPrograma(Integer idPrograma) {
        fichaDescProgramaRepo.eliminaRelProgResponsablesIdPrograma(idPrograma);
    }

    @Override
    public void eliminaRelProgAutorIdPrograma(Integer idPrograma) {
        fichaDescProgramaRepo.eliminaRelProgAutorIdPrograma(idPrograma);
    }

    @Override
    public void eliminaRelProgCompEspecificasIdPrograma(Integer idPrograma) {
        fichaDescProgramaRepo.eliminaRelProgCompEspecificasIdPrograma(idPrograma);
    }

    @Override
    public void eliminaRelProgDuracionIdPrograma(Integer idPrograma) {
        fichaDescProgramaRepo.eliminaRelProgDuracionIdPrograma(idPrograma);
    }

    @Override
    public Integer totalProgramasByTipo(Integer tipo) {
        return fichaDescProgramaRepo.totalProgramasByTipo(tipo);
    }

    @Override
    public Integer totalModalidadPrograma(Integer idModalidad) {
        return fichaDescProgramaRepo.totalModalidadPrograma(idModalidad);
    }

    @Override
    public Integer totalNivelEnsenanzaById(Integer idNivelEnsenanza) {
        return fichaDescProgramaRepo.totalNivelEnsenanzaById(idNivelEnsenanza);
    }

    @Override
    public Integer totalNivelConocimientoById(Integer idNivelConocimiento) {
        return fichaDescProgramaRepo.totalNivelConocimientoById(idNivelConocimiento);
    }

    @Override
    public Integer totalTipoEventoById(Integer idEvento) {
        return fichaDescProgramaRepo.totalTipoEventoById(idEvento);
    }

    @Override
    public void validarPersistencia(FichaDescProgramaDTO dto, ResultadoDTO<FichaDescProgramaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarActualizacion(FichaDescProgramaDTO dto, ResultadoDTO<FichaDescProgramaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void validarEliminacion(FichaDescProgramaDTO dto, ResultadoDTO<FichaDescProgramaDTO> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
