package mx.gob.sedesol.gestorweb.beans.gestionescolar;

import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenciaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.service.gestionescolar.CatalogoAsistenciaService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import org.apache.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.Date;
import java.util.List;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;

/**
 * Created by jhcortes on 2/02/17.
 */
@SessionScoped
@ManagedBean
public class CatAsistenciaBean extends BaseBean {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(CatAsistenciaBean.class);

    private AsistenciaDTO asistenciaDTO;
    private List<AsistenciaDTO> lstAsistenciaDTO;
    private String nombreCat;
    private Integer idCat;
    private boolean muestraCompReg;
    private boolean muestraCompTabla;

    @ManagedProperty(value = "#{catalogoAsistenciaService}")
    private CatalogoAsistenciaService catalogoAsistenciaService;

    public CatAsistenciaBean() {
    }

    @SuppressWarnings("unchecked")
    public void guardarRegCatalogo() {
        if (ObjectUtils.isNotNull(asistenciaDTO) && !ObjectUtils.isNullOrEmpty(asistenciaDTO.getNombre())) {
            try {

                ResultadoDTO<AsistenciaDTO> res = null;
                if (isMuestraCompTabla() && isMuestraCompReg()) {

                    asistenciaDTO.setFechaActualizacion(new Date());
                    asistenciaDTO.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
                    asistenciaDTO.setNombreUsuarioMod(getUsuarioEnSession().getUsuario());
                    //GUSTAVO --BitacoraUtil.llenarBitacora(asistenciaDTO, getUsuarioEnSession(), ConstantesBitacora.CATALOGO_EDITAR, request);
                    res = catalogoAsistenciaService.actualizar(asistenciaDTO);

                } else {
                    asistenciaDTO.setFechaRegistro(new Date());
                    asistenciaDTO.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
                    asistenciaDTO.setNombreUsuarioMod(getUsuarioEnSession().getUsuario());
                    //GUSTAVO --BitacoraUtil.llenarBitacora(asistenciaDTO, getUsuarioEnSession(), ConstantesBitacora.CATALOGO_AGREGAR, request);
                    res = catalogoAsistenciaService.guardar(asistenciaDTO);
                }

                if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
                    agregarMsgInfo("Se guardo el registro exitosamente.", null);
                } else {
                    agregarMsgError("Ocurrio un error", null);
                    logger.error(res != null ? obtenerErroresDeServicio(res.getMensajes()) : null);
                }

            } catch (Exception e) {
                logger.error(e.getCause(), e);
            }

        }
    }

    @SuppressWarnings("unchecked")
    public void obtenerDatosCatSel() {
        setMuestraCompReg(Boolean.FALSE);
        setMuestraCompTabla(Boolean.TRUE);
        lstAsistenciaDTO = catalogoAsistenciaService.findAll();
    }

    @SuppressWarnings("unchecked")
    public void cambioEstatusCatSelec() {
        asistenciaDTO = null;
        String idRegCat = (String) getFacesContext().getExternalContext().getRequestParameterMap().get("idRegCat");
        if (ObjectUtils.isNotNull(asistenciaDTO) && !ObjectUtils.isNullOrEmpty(idRegCat)) {

            try {
                asistenciaDTO = catalogoAsistenciaService.buscarPorId(Integer.valueOf(idRegCat));

                if (ObjectUtils.isNotNull(asistenciaDTO)) {

                    if (asistenciaDTO.getActivo().equals(ConstantesGestorWeb.ACTIVO)) {
                        asistenciaDTO.setActivo(ConstantesGestorWeb.INACTIVO);
                    } else {
                        asistenciaDTO.setActivo(ConstantesGestorWeb.ACTIVO);
                    }

                    asistenciaDTO.setFechaActualizacion(new Date());
                    asistenciaDTO.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
                    asistenciaDTO.setNombreUsuarioMod(getUsuarioEnSession().getUsuario());
                    //GUSTAVO --BitacoraUtil.llenarBitacora(asistenciaDTO, getUsuarioEnSession(), ConstantesBitacora.CATALOGO_EDITAR, request);
                    ResultadoDTO<AsistenciaDTO> res = catalogoAsistenciaService.actualizar(asistenciaDTO);

                    if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
                        agregarMsgInfo("Se actualizo el registro exitosamente.", null);
                    } else {
                        agregarMsgError("Ocurrio un error", null);
                        logger.error(res != null ? obtenerErroresDeServicio(res.getMensajes()) : null);
                    }

                }
            } catch (Exception e) {
                logger.error(e.getCause(), e);
            }
        }
    }

    /**
     *
     */
    public void generaComponenteReg() {
        setMuestraCompTabla(Boolean.FALSE);
        setMuestraCompReg(Boolean.TRUE);
        asistenciaDTO = new AsistenciaDTO();
    }

    /**
     *
     */
    public void editaRegCatSeleccionado() {
        if (ObjectUtils.isNotNull(asistenciaDTO)) {
            setMuestraCompReg(Boolean.TRUE);
        }

    }

    public static Logger getLogger() {
        return logger;
    }

    public AsistenciaDTO getAsistenciaDTO() {
        return asistenciaDTO;
    }

    public void setAsistenciaDTO(AsistenciaDTO asistenciaDTO) {
        this.asistenciaDTO = asistenciaDTO;
    }

    public List<AsistenciaDTO> getLstAsistenciaDTO() {
        return lstAsistenciaDTO;
    }

    public void setLstAsistenciaDTO(List<AsistenciaDTO> lstAsistenciaDTO) {
        this.lstAsistenciaDTO = lstAsistenciaDTO;
    }

    public String getNombreCat() {
        return nombreCat;
    }

    public void setNombreCat(String nombreCat) {
        this.nombreCat = nombreCat;
    }

    public Integer getIdCat() {
        return idCat;
    }

    public void setIdCat(Integer idCat) {
        this.idCat = idCat;
    }

    public boolean isMuestraCompReg() {
        return muestraCompReg;
    }

    public void setMuestraCompReg(boolean muestraCompReg) {
        this.muestraCompReg = muestraCompReg;
    }

    public boolean isMuestraCompTabla() {
        return muestraCompTabla;
    }

    public void setMuestraCompTabla(boolean muestraCompTabla) {
        this.muestraCompTabla = muestraCompTabla;
    }

    public CatalogoAsistenciaService getCatalogoAsistenciaService() {
        return catalogoAsistenciaService;
    }

    public void setCatalogoAsistenciaService(CatalogoAsistenciaService catalogoAsistenciaService) {
        this.catalogoAsistenciaService = catalogoAsistenciaService;
    }
}
