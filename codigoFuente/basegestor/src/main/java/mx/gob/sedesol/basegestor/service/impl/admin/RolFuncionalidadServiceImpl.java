package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.admin.BitacoraDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.FuncionalidadDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.RolFuncionalidadDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.admin.CatRol;
import mx.gob.sedesol.basegestor.model.entities.admin.RelRolFuncionalidad;
import mx.gob.sedesol.basegestor.model.entities.admin.TblFuncionalidad;
import mx.gob.sedesol.basegestor.model.repositories.admin.RolFuncionalidadRepo;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.FuncionalidadService;
import mx.gob.sedesol.basegestor.service.admin.RolFuncionalidadService;
import mx.gob.sedesol.basegestor.service.admin.RoleService;

@Service("rolFuncionalidadService")
public class RolFuncionalidadServiceImpl extends ComunValidacionService<RolFuncionalidadDTO>
        implements RolFuncionalidadService {

    private static final Logger logger = Logger.getLogger(RolFuncionalidadServiceImpl.class);

    @Autowired
    private RolFuncionalidadRepo rolFuncionalidadRepo;
    @Autowired
    private FuncionalidadService funcionalidadService;
    @Autowired
    private RoleService roleService;

    private ModelMapper rolFuncMapper = new ModelMapper();

    private Type tipoListaRolFuncionalidad = new TypeToken<List<RolFuncionalidadDTO>>() {
    }.getType();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<RolFuncionalidadDTO> guardar(RolFuncionalidadDTO rolFuncionalidad) {

        ResultadoDTO<RolFuncionalidadDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, rolFuncionalidad);
        if (res.getResultado().getValor()) {
            try {
                res = new ResultadoDTO<>();
                RelRolFuncionalidad relRolFunc = rolFuncMapper.map(rolFuncionalidad, RelRolFuncionalidad.class);
                relRolFunc = rolFuncionalidadRepo.save(relRolFunc);
                res.setDto(rolFuncMapper.map(relRolFunc, RolFuncionalidadDTO.class));


        //GUSTAVO --guardarBitacora(rolFuncionalidad.getBitacoraDTO(), String.valueOf(relRolFunc.getIdRolFuncionalidad()));

                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId());
            } catch (Exception e) {
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
                logger.error(e.getMessage(), e);
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<RolFuncionalidadDTO> eliminar(RolFuncionalidadDTO rolFuncionalidad) {
        ResultadoDTO<RolFuncionalidadDTO> res = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, rolFuncionalidad);
        if (res.getResultado().getValor()) {
            try {
                res = new ResultadoDTO<>();
                RelRolFuncionalidad entidad = rolFuncMapper.map(rolFuncionalidad, RelRolFuncionalidad.class);
                entidad = rolFuncionalidadRepo.saveAndFlush(entidad);
                res.setDto(rolFuncMapper.map(entidad, RolFuncionalidadDTO.class));


        //GUSTAVO --guardarBitacora(rolFuncionalidad.getBitacoraDTO(), String.valueOf(entidad.getIdRolFuncionalidad()));

                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
            } catch (Exception e) {
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
                logger.error(e.getMessage(), e);
            }
        }
        return res;
    }

    @Override
    public List<RolFuncionalidadDTO> findAll() {
        return rolFuncMapper.map(rolFuncionalidadRepo.findAll(), tipoListaRolFuncionalidad);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<RolFuncionalidadDTO> actualizar(RolFuncionalidadDTO rolFuncionalidad) {
        ResultadoDTO<RolFuncionalidadDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, rolFuncionalidad);

        if (res.getResultado().getValor()) {
            RelRolFuncionalidad entActual = rolFuncionalidadRepo.findOne(rolFuncionalidad.getIdRolFuncionalidad());
            if (ObjectUtils.isNotNull(entActual)) {

                if (!entActual.getFuncionalidad().getIdFuncionalidad()
                        .equals(rolFuncionalidad.getFuncionalidadDTO().getIdFuncionalidad())) {
                    res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_IDENTIFICADORES_DIFERENTES);
                    return res;
                }

                if (!entActual.getRol().getIdRol().equals(rolFuncionalidad.getRol().getIdRol())) {
                    res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_IDENTIFICADORES_DIFERENTES);
                    return res;
                }

                try {
                    res = new ResultadoDTO<>();
                    entActual = rolFuncMapper.map(rolFuncionalidad, RelRolFuncionalidad.class);
                    entActual = rolFuncionalidadRepo.saveAndFlush(entActual);
                    res.setDto(rolFuncMapper.map(entActual, RolFuncionalidadDTO.class));


            //GUSTAVO --guardarBitacora(rolFuncionalidad.getBitacoraDTO(), String.valueOf(entActual.getIdRolFuncionalidad()));

                    res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
                } catch (Exception e) {
                    res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
                    logger.error(e.getMessage(), e);
                }

            } else {
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
            }
        }
        return res;
    }

    @Override
    public RolFuncionalidadDTO buscarPorId(Long id) {
        RolFuncionalidadDTO rolFuncionalidadDTO;
        RelRolFuncionalidad rolFunc = rolFuncionalidadRepo.findOne(id);
        if (ObjectUtils.isNull(rolFunc)) {
            rolFuncionalidadDTO = null;
        } else {
            rolFuncionalidadDTO = rolFuncMapper.map(rolFunc, RolFuncionalidadDTO.class);
        }
        return rolFuncionalidadDTO;
    }

    @Override
    public void validarPersistencia(RolFuncionalidadDTO rolFuncionalidad, ResultadoDTO<RolFuncionalidadDTO> res) {
        if (ObjectUtils.isNull(rolFuncionalidad.getRol()) || ObjectUtils.isNull(rolFuncionalidad.getRol().getIdRol())) {
            res.setMensajeError(MensajesSistemaEnum.ROL_FUNCIONALIDAD_ID_ROL_REQ);
        }
        if (ObjectUtils.isNull(rolFuncionalidad.getFuncionalidadDTO())
                || ObjectUtils.isNull(rolFuncionalidad.getFuncionalidadDTO().getIdFuncionalidad())) {
            res.setMensajeError(MensajesSistemaEnum.ROL_FUNCIONALIDAD_ID_FUNCIONALIDAD_REQ);
        }
        if (ObjectUtils.isNull(rolFuncionalidad.getFechaRegistro())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
        }
        if (ObjectUtils.isNull(rolFuncionalidad.getUsuarioModifico())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
    }

    @Override
    public void validarActualizacion(RolFuncionalidadDTO rolFuncionalidad, ResultadoDTO<RolFuncionalidadDTO> res) {
        if (ObjectUtils.isNull(rolFuncionalidad.getRol()) || ObjectUtils.isNull(rolFuncionalidad.getRol().getIdRol())) {
            res.setMensajeError(MensajesSistemaEnum.ROL_FUNCIONALIDAD_ID_ROL_REQ);
        }
        if (ObjectUtils.isNull(rolFuncionalidad.getFuncionalidadDTO())
                || ObjectUtils.isNull(rolFuncionalidad.getFuncionalidadDTO().getIdFuncionalidad())) {
            res.setMensajeError(MensajesSistemaEnum.ROL_FUNCIONALIDAD_ID_FUNCIONALIDAD_REQ);
        }
        if (ObjectUtils.isNull(rolFuncionalidad.getFechaActualizacion())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
        }
        if (ObjectUtils.isNull(rolFuncionalidad.getUsuarioModifico())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
    }

    @Override
    public void validarEliminacion(RolFuncionalidadDTO rolFuncionalidad, ResultadoDTO<RolFuncionalidadDTO> res) {
        if (ObjectUtils.isNull(rolFuncionalidad.getRol()) || ObjectUtils.isNull(rolFuncionalidad.getRol().getIdRol())) {
            res.setMensajeError(MensajesSistemaEnum.ROL_FUNCIONALIDAD_ID_ROL_REQ);
        }
        if (ObjectUtils.isNull(rolFuncionalidad.getFuncionalidadDTO())
                || ObjectUtils.isNull(rolFuncionalidad.getFuncionalidadDTO().getIdFuncionalidad())) {
            res.setMensajeError(MensajesSistemaEnum.ROL_FUNCIONALIDAD_ID_FUNCIONALIDAD_REQ);
        }
        if (ObjectUtils.isNull(rolFuncionalidad.getActivo())) {
            res.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_ESTA_ACTIVO_REQ);
        }
    }

    @Override
    public List<RolFuncionalidadDTO> obtenerArbolFuncionalidadesRol(int idRol) {
        List<RolFuncionalidadDTO> listaArbol = new ArrayList<>();
        Map<Long, RolFuncionalidadDTO> mapaArbol = new HashMap<>();
        Map<Long, Long> mapaFuncionalidadesRol = obtenerMapaFuncionalidadesRol(idRol);
        RolDTO rol = roleService.buscarPorId(idRol);

        for (FuncionalidadDTO dto : funcionalidadService.findAll()) {
            RolFuncionalidadDTO rolFuncionalidadDTO = new RolFuncionalidadDTO();
            rolFuncionalidadDTO.setFuncionalidades(new ArrayList<>());
            rolFuncionalidadDTO.setRol(rol);
            rolFuncionalidadDTO.setFuncionalidadDTO(dto);
            if (ObjectUtils.isNull(dto.getFuncionalidadPadre())) {
                rolFuncionalidadDTO.setSeleccionado(mapaFuncionalidadesRol.containsKey(dto.getIdFuncionalidad()));
                listaArbol.add(rolFuncionalidadDTO);
            } else {
                RolFuncionalidadDTO funcionalidadPadre = mapaArbol
                        .get(dto.getFuncionalidadPadre().getIdFuncionalidad());
                if (funcionalidadPadre.isSeleccionado()) {
                    rolFuncionalidadDTO.setSeleccionado(true);
                } else {
                    rolFuncionalidadDTO.setSeleccionado(mapaFuncionalidadesRol.containsKey(dto.getIdFuncionalidad()));
                }
                funcionalidadPadre.getFuncionalidades().add(rolFuncionalidadDTO);
            }
            mapaArbol.put(dto.getIdFuncionalidad(), rolFuncionalidadDTO);
        }
        return listaArbol;
    }

    @Override
    public ResultadoDTO<RolFuncionalidadDTO> guardarArbolFuncionalidadesRol(List<RolFuncionalidadDTO> arbol, int idRol,
            Long idUsuario) {
        ResultadoDTO<RolFuncionalidadDTO> resultado = new ResultadoDTO<>();
        Map<Long, Long> mapaFuncionalidadesRol = obtenerMapaFuncionalidadesRol(idRol);
        procesarListaFuncionalidades(arbol);

        Map<Long, FuncionalidadDTO> listaGuardar = new HashMap<>();

        llenarMapaFuncionalidadesSeleccionadas(listaGuardar, arbol, false);

        for (Map.Entry<Long, FuncionalidadDTO> funcionalidad : listaGuardar.entrySet()) {
            logger.info("MARCADO GUARDAR " + funcionalidad.getValue().getClave());
            if (!mapaFuncionalidadesRol.containsKey(funcionalidad.getKey())) {
                logger.info("GUARDAR " + funcionalidad.getValue().getClave());
                //Envie la bitacora nula para evitar problemas.
                //guardarFuncionalidadRol(funcionalidad.getKey(), idRol, idUsuario, funcionalidad.getValue().getBitacoraDTO());
                guardarFuncionalidadRol(funcionalidad.getKey(), idRol, idUsuario, null);
            }
        }

        for (Map.Entry<Long, Long> funcionalidad : mapaFuncionalidadesRol.entrySet()) {
            logger.info("FUNCIONALIDAD ACTUAL " + funcionalidad.getValue());
            if (!listaGuardar.containsKey(funcionalidad.getKey())) {
                logger.info("ELIMINAR " + funcionalidad.getValue());
                //Envie la bitacora nula para evitar problemas
                //eliminarFuncionalidadRol(funcionalidad.getValue(), funcionalidad.getKey(), idRol, idUsuario, arbol.get(0).getBitacoraDTO());
                eliminarFuncionalidadRol(funcionalidad.getValue(), funcionalidad.getKey(), idRol, idUsuario, null);
            }
        }
        return resultado;
    }

    @Override
    public Map<String, String> obtenerMapaFuncionalidadesPorRol(int idRol) {
        Map<String, String> mapaFuncionalidadesRol = new HashMap<>();

        for (RelRolFuncionalidad entidad : rolFuncionalidadRepo.obtenerFuncionalidadesRol(idRol)) {
            logger.info(entidad.getFuncionalidad().getClave());
            mapaFuncionalidadesRol.put(entidad.getFuncionalidad().getClave(), entidad.getFuncionalidad().getClave());
        }
        return mapaFuncionalidadesRol;
    }

    @Override
    public void procesarListaFuncionalidades(List<RolFuncionalidadDTO> arbol) {
        for (RolFuncionalidadDTO dto : arbol) {
            validarTodosHijosSeleccionados(dto);
        }
    }

    private void guardarFuncionalidadRol(long idFuncionalidad, int idRol, Long idUsuario, BitacoraDTO bitacora) {
        RelRolFuncionalidad entidad = new RelRolFuncionalidad();
        entidad.setFechaRegistro(new Date());
        entidad.setUsuarioModifico(idUsuario);
        entidad.setFuncionalidad(new TblFuncionalidad());
        entidad.setRol(new CatRol());
        entidad.getFuncionalidad().setIdFuncionalidad(idFuncionalidad);
        entidad.getRol().setIdRol(idRol);

        rolFuncionalidadRepo.save(entidad);

//GUSTAVO --guardarBitacora(bitacora, String.valueOf(entidad.getIdRolFuncionalidad()));

    }

    private void eliminarFuncionalidadRol(long id, long idFuncionalidad, int idRol, Long idUsuario, BitacoraDTO bitacora) {
        rolFuncionalidadRepo.delete(id);

      //GUSTAVO --bitacora.setFuncion(ConstantesBitacora.ROL_FUNC_ELIMINAR);
//GUSTAVO --guardarBitacora(bitacora, String.valueOf(id));

    }

    private void llenarMapaFuncionalidadesSeleccionadas(Map<Long, FuncionalidadDTO> mapa,
            List<RolFuncionalidadDTO> lista, boolean padreSeleccionado) {
        for (RolFuncionalidadDTO dto : lista) {
            logger.info("---" + dto.getFuncionalidadDTO().getClave() + " " + dto.isSeleccionado());
            if (dto.isSeleccionado() && !padreSeleccionado) {
                mapa.put(dto.getFuncionalidadDTO().getIdFuncionalidad(), dto.getFuncionalidadDTO());
            }
            llenarMapaFuncionalidadesSeleccionadas(mapa, dto.getFuncionalidades(), dto.isSeleccionado());
        }
    }

    private boolean validarTodosHijosSeleccionados(RolFuncionalidadDTO dto) {
        boolean seleccionado = true;
        if (!dto.getFuncionalidades().isEmpty()) {
            for (RolFuncionalidadDTO hijo : dto.getFuncionalidades()) {
                if (!hijo.getFuncionalidades().isEmpty()) {
                    hijo.setSeleccionado(validarTodosHijosSeleccionados(hijo));
                }
                if (!hijo.isSeleccionado()) {
                    seleccionado = false;
                }
            }
            dto.setSeleccionado(seleccionado);
        }
        return seleccionado;
    }

    /**
     * Obtiene las funcionalidades que tiene un rol y las carga en un mapa en
     * base a su id
     *
     * @param idRol
     * @return
     */
    private Map<Long, Long> obtenerMapaFuncionalidadesRol(int idRol) {
        Map<Long, Long> mapaFuncionalidadesRol = new HashMap<>();

        for (RelRolFuncionalidad entidad : rolFuncionalidadRepo.obtenerFuncionalidadesRol(idRol)) {
            mapaFuncionalidadesRol.put(entidad.getFuncionalidad().getIdFuncionalidad(),
                    entidad.getIdRolFuncionalidad());
        }
        return mapaFuncionalidadesRol;
    }

}
