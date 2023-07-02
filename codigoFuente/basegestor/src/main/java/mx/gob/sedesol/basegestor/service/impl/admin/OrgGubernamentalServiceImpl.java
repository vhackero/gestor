package mx.gob.sedesol.basegestor.service.impl.admin;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.OrgGubernamentalDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoAccion;
import mx.gob.sedesol.basegestor.model.entities.admin.TblOrganismoGubernamental;
import mx.gob.sedesol.basegestor.model.repositories.admin.OrgGubernamentalRepo;
import mx.gob.sedesol.basegestor.mongo.service.AdministradorBitacora;
import mx.gob.sedesol.basegestor.service.admin.ComunValidacionService;
import mx.gob.sedesol.basegestor.service.admin.OrgGubernamentalService;

@Service("orgGubernamentalService")
public class OrgGubernamentalServiceImpl extends ComunValidacionService<OrgGubernamentalDTO>
        implements OrgGubernamentalService {

    private Logger logger = Logger.getLogger(OrgGubernamentalServiceImpl.class);
    @Autowired
    private OrgGubernamentalRepo orgGubernamentalRepo;
    private ModelMapper orgGubMapper = new ModelMapper();

    @Override
    public List<OrgGubernamentalDTO> findAll() {
        Type lstAux = new TypeToken<List<OrgGubernamentalDTO>>() {
        }.getType();
        return orgGubMapper.map(orgGubernamentalRepo.findAll(), lstAux);
    }

    @Override
    public OrgGubernamentalDTO buscarPorId(Integer id) {
        List<TblOrganismoGubernamental> lstAux = orgGubernamentalRepo.buscarOrgGubernamentalPorId(id);

        if (!ObjectUtils.isNullOrEmpty(lstAux)) {
            TblOrganismoGubernamental entidad = lstAux.get(ConstantesGestor.PRIMER_ELEMENTO);

            return orgGubMapper.map(entidad, OrgGubernamentalDTO.class);
        }

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<OrgGubernamentalDTO> guardar(OrgGubernamentalDTO dto) {

        ResultadoDTO<OrgGubernamentalDTO> res = sonDatosRequeridosValidos(TipoAccion.PERSISTENCIA, dto);
        if (res.getResultado().getValor()) {

            try {
                TblOrganismoGubernamental orgEntidad = orgGubMapper.map(dto, TblOrganismoGubernamental.class);
                orgEntidad = orgGubernamentalRepo.save(orgEntidad);
                res = new ResultadoDTO<>();
                res.setDto(orgGubMapper.map(orgEntidad, OrgGubernamentalDTO.class));

            } catch (Exception e) {
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO);
                logger.error(e.getMessage(), e);
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<OrgGubernamentalDTO> actualizar(OrgGubernamentalDTO dto) {

        ResultadoDTO<OrgGubernamentalDTO> res = sonDatosRequeridosValidos(TipoAccion.ACTUALIZACION, dto);
        if (res.getResultado().getValor()) {

            try {

                TblOrganismoGubernamental orgEntidad = orgGubMapper.map(dto, TblOrganismoGubernamental.class);
                orgEntidad = orgGubernamentalRepo.saveAndFlush(orgEntidad);
                res = new ResultadoDTO<>();
                res.setDto(orgGubMapper.map(orgEntidad, OrgGubernamentalDTO.class));

                //GUSTAVO --guardarBitacoraOrgGubernamental(dto, String.valueOf(orgEntidad.getId()));

                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_EXITOSA.getId());
            } catch (Exception e) {
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ACTUALIZACION_FALLIDA);
                logger.error(e.getMessage(), e);
            }
        }
        return res;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultadoDTO<OrgGubernamentalDTO> eliminar(OrgGubernamentalDTO dto) {

        ResultadoDTO<OrgGubernamentalDTO> res = sonDatosRequeridosValidos(TipoAccion.ELIMINACION, dto);
        if (res.getResultado().getValor()) {

            try {

                TblOrganismoGubernamental orgEntidad = orgGubMapper.map(dto, TblOrganismoGubernamental.class);
                orgGubernamentalRepo.delete(orgEntidad);
                res = new ResultadoDTO<>();
                res.setDto(orgGubMapper.map(orgEntidad, OrgGubernamentalDTO.class));

                //GUSTAVO --guardarBitacoraOrgGubernamental(dto, String.valueOf(orgEntidad.getId()));

                res.agregaMensaje(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_EXITOSA.getId());
            } catch (Exception e) {
                res.setMensajeError(MensajesSistemaEnum.ADMIN_MSG_ELIMINACION_FALLIDA);
                logger.error(e.getMessage(), e);
            }
        }
        return res;

    }

    @Override
    public OrgGubernamentalDTO obtenerOrgGubernamentalPorId(Integer id) {
        List<TblOrganismoGubernamental> lstAux = orgGubernamentalRepo.buscarOrgGubernamentalPorId(id);

        if (!ObjectUtils.isNullOrEmpty(lstAux)) {
            TblOrganismoGubernamental entidad = lstAux.get(ConstantesGestor.PRIMER_ELEMENTO);

            return orgGubMapper.map(entidad, OrgGubernamentalDTO.class);
        }

        return null;
    }

    @Override
    public List<OrgGubernamentalDTO> obtenerOrgGubsPadres() {
        List<OrgGubernamentalDTO> lista;
        Type lstAux = new TypeToken<List<OrgGubernamentalDTO>>() {
        }.getType();
        List<TblOrganismoGubernamental> padres = orgGubernamentalRepo.obtenerOrgGubsPadres();
        if (ObjectUtils.isNotNull(padres)) {
            lista = orgGubMapper.map(padres, lstAux);
        } else {
            lista = new ArrayList<>();
        }
        return lista;
    }

    @Override
    public void validarEliminacion(OrgGubernamentalDTO dto, ResultadoDTO<OrgGubernamentalDTO> resultado) {
        if (ObjectUtils.isNull(dto.getId())) {
            resultado.setMensajeError(MensajesSistemaEnum.ORG_GUB_ID_REQ);
        }
    }

    @Override
    public void validarPersistencia(OrgGubernamentalDTO dto, ResultadoDTO<OrgGubernamentalDTO> resultado) {
        if (ObjectUtils.isNullOrEmpty(dto.getUsuarioModifico())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }

        if (ObjectUtils.isNull(dto.getFechaRegistro())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_REGISTRO_REQ);
        }
    }

    @Override
    public void validarActualizacion(OrgGubernamentalDTO dto, ResultadoDTO<OrgGubernamentalDTO> resultado) {
        validarEliminacion(dto, resultado);
        if (ObjectUtils.isNull(dto.getFechaActualizacion())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_FECHA_EDICION_REQ);
        }
        if (ObjectUtils.isNull(dto.getUsuarioModifico())) {
            resultado.setMensajeError(MensajesSistemaEnum.MSG_GENERAL_USUARIO_MODIFICO_REQ);
        }
    }


}
