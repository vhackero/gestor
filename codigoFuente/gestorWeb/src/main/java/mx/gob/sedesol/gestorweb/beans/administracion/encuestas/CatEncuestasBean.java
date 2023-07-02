/**
 *
 */
package mx.gob.sedesol.gestorweb.beans.administracion.encuestas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.admin.CatalogoComunDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.CatEncuestasYEvaluacionesEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.model.entities.encuestas.CatEncuestaContexto;
import mx.gob.sedesol.basegestor.springinit.EncuestaServiceAdapter;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;

/**
 * @author jhcortes
 *
 */
@ViewScoped
@ManagedBean
public class CatEncuestasBean extends BaseBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(CatEncuestasBean.class);

    private CatalogoComunDTO catComunDTOGenerico;
    private String nombreCat;
    private String idCat;
    private Long idContexto;
    private boolean muestraCompReg;
    private boolean muestraCompTabla;
    private boolean renderCmpSelect;
    private CatEncuestasYEvaluacionesEnum catalogoSeleccionado;
    private List<CatalogoComunDTO> lstCatSelec;
    private List<CatalogoComunDTO> lstCatContexto;

    private CatEncuestasYEvaluacionesEnum[] catalogosEncuestasYEval;

    @ManagedProperty(value = "#{encuestaServiceAdapter}")
    private EncuestaServiceAdapter encuestaServiceAdapter;

    public CatEncuestasBean() {

    }

    @PostConstruct
    public void inicializaInstancia() {
        catalogosEncuestasYEval = CatEncuestasYEvaluacionesEnum.values();
    }

    @SuppressWarnings("unchecked")
    public void guardarRegCatalogo() {
        if (ObjectUtils.isNotNull(catalogoSeleccionado)
                && ObjectUtils.isNotNull(catComunDTOGenerico) && !ObjectUtils.isNullOrEmpty(catComunDTOGenerico.getNombre())) {
            try {

                ResultadoDTO<?> res = null;
                if (isMuestraCompTabla() && isMuestraCompReg()) {

                    catComunDTOGenerico.setFechaActualizacion(new Date());
                    catComunDTOGenerico.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
                    catComunDTOGenerico.setNombreUsuarioMod(getUsuarioEnSession().getUsuario());
                    //GUSTAVO --BitacoraUtil.llenarBitacora(catComunDTOGenerico, getUsuarioEnSession(), ConstantesBitacora.CATALOGO_EDITAR, request);
                    res = getEncuestaServiceAdapter().getCatalogoServiceByEncuestasEnum(catalogoSeleccionado)
                            .actualizar(catComunDTOGenerico, catalogoSeleccionado.getClassEntidad());

                } else {
                    catComunDTOGenerico.setFechaRegistro(new Date());
                    catComunDTOGenerico.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
                    catComunDTOGenerico.setNombreUsuarioMod(getUsuarioEnSession().getUsuario());
                    //GUSTAVO --BitacoraUtil.llenarBitacora(catComunDTOGenerico, getUsuarioEnSession(), ConstantesBitacora.CATALOGO_AGREGAR, request);
                    res = getEncuestaServiceAdapter().getCatalogoServiceByEncuestasEnum(catalogoSeleccionado)
                            .guardar(catComunDTOGenerico, catalogoSeleccionado.getClassEntidad());
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

    /**
     *
     * @param catalogo
     */
    public void generaComponenteReg(CatEncuestasYEvaluacionesEnum catalogo) {

        setMuestraCompTabla(Boolean.FALSE);

        if (ObjectUtils.isNotNull(catalogo)) {

            setMuestraCompReg(Boolean.TRUE);
            catalogoSeleccionado = catalogo;
            nombreCat = catalogo.getNombre();
            idCat = catalogo.getId();
            catComunDTOGenerico = new CatalogoComunDTO();
            requiereCatContexto(idCat);

        } else {
            setMuestraCompReg(Boolean.FALSE);
        }
    }

    /**
     *
     * @param idCat2
     */
    private void requiereCatContexto(String idCat2) {
        if (CatEncuestasYEvaluacionesEnum.CAT_ENCUESTAS_TIPO.getId().equals(idCat2)) {
            setRenderCmpSelect(Boolean.TRUE);
            obtenerCatContexto();
        } else {
            setRenderCmpSelect(Boolean.FALSE);
        }

    }

    @SuppressWarnings("unchecked")
    public void obtenerDatosCatSel(CatEncuestasYEvaluacionesEnum catalogo) {
        setMuestraCompReg(Boolean.FALSE);
        if (ObjectUtils.isNotNull(catalogo)) {
            setMuestraCompTabla(Boolean.TRUE);
            catalogoSeleccionado = catalogo;
            nombreCat = catalogo.getNombre();
            idCat = catalogo.getId();
            requiereCatContexto(idCat);
            lstCatSelec = getEncuestaServiceAdapter().getCatalogoServiceByEncuestasEnum(catalogoSeleccionado)
                    .findAll(catalogoSeleccionado.getClassEntidad());
        } else {
            setMuestraCompTabla(Boolean.FALSE);
            lstCatSelec = new ArrayList<CatalogoComunDTO>();
        }
    }

    @SuppressWarnings("unchecked")
    public void cambioEstatusCatSelec() {
        catComunDTOGenerico = null;
        String idRegCat = (String) getFacesContext().getExternalContext().getRequestParameterMap().get("idRegCat");
        if (ObjectUtils.isNotNull(catalogoSeleccionado) && !ObjectUtils.isNullOrEmpty(idRegCat)) {
            try {
                catComunDTOGenerico = getEncuestaServiceAdapter()
                        .getCatalogoServiceByEncuestasEnum(catalogoSeleccionado).
                        buscarPorId(idRegCat, catalogoSeleccionado.getClassEntidad());

                if (ObjectUtils.isNotNull(catComunDTOGenerico)) {

                    if (catComunDTOGenerico.getActivo().equals(ConstantesGestorWeb.ACTIVO)) {
                        catComunDTOGenerico.setActivo(ConstantesGestorWeb.INACTIVO);
                    } else {
                        catComunDTOGenerico.setActivo(ConstantesGestorWeb.ACTIVO);
                    }

                    catComunDTOGenerico.setFechaActualizacion(new Date());
                    catComunDTOGenerico.setUsuarioModifico(getUsuarioEnSession().getIdPersona());
                    catComunDTOGenerico.setNombreUsuarioMod(getUsuarioEnSession().getUsuario());
                    //GUSTAVO --BitacoraUtil.llenarBitacora(catComunDTOGenerico, getUsuarioEnSession(), ConstantesBitacora.CATALOGO_EDITAR, request);
                    ResultadoDTO<?> res = getEncuestaServiceAdapter().getCatalogoServiceByEncuestasEnum(catalogoSeleccionado)
                            .actualizar(catComunDTOGenerico, catalogoSeleccionado.getClassEntidad());

                    if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {

                        agregarMsgInfo(res.getMensaje(), null);

                    } else {

                        agregarMsgError(res.getMensaje(), null);
                        logger.error(res != null ? obtenerErroresDeServicio(res.getMensajes()) : null);
                    }

                }
            } catch (Exception e) {
                logger.error(e.getCause(), e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void obtenerCatContexto() {
        try {
            lstCatContexto = getEncuestaServiceAdapter()
                    .getCatalogoServiceByEncuestasEnum(CatEncuestasYEvaluacionesEnum.CAT_ENCUESTAS_CONTEXTO)
                    .findAll(CatEncuestaContexto.class);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * @return the catComunDTOGenerico
     */
    public CatalogoComunDTO getCatComunDTOGenerico() {
        return catComunDTOGenerico;
    }

    /**
     * @param catComunDTOGenerico the catComunDTOGenerico to set
     */
    public void setCatComunDTOGenerico(CatalogoComunDTO catComunDTOGenerico) {
        this.catComunDTOGenerico = catComunDTOGenerico;
    }

    /**
     * @return the lstCatSelec
     */
    public List<CatalogoComunDTO> getLstCatSelec() {
        return lstCatSelec;
    }

    /**
     * @param lstCatSelec the lstCatSelec to set
     */
    public void setLstCatSelec(List<CatalogoComunDTO> lstCatSelec) {
        this.lstCatSelec = lstCatSelec;
    }

    /**
     *
     * @param
     */
    public void editaRegCatSeleccionado() {
        if (ObjectUtils.isNotNull(catComunDTOGenerico)) {
            setMuestraCompReg(Boolean.TRUE);
            obtenerCatContexto();
        }

    }

    /**
     * @return the nombreCat
     */
    public String getNombreCat() {
        return nombreCat;
    }

    /**
     * @param nombreCat the nombreCat to set
     */
    public void setNombreCat(String nombreCat) {
        this.nombreCat = nombreCat;
    }

    /**
     * @return the idCat
     */
    public String getIdCat() {
        return idCat;
    }

    /**
     * @param idCat the idCat to set
     */
    public void setIdCat(String idCat) {
        this.idCat = idCat;
    }

    /**
     * @return the muestraCompReg
     */
    public boolean isMuestraCompReg() {
        return muestraCompReg;
    }

    /**
     * @param muestraCompReg the muestraCompReg to set
     */
    public void setMuestraCompReg(boolean muestraCompReg) {
        this.muestraCompReg = muestraCompReg;
    }

    /**
     * @return the catalogoSeleccionado
     */
    public CatEncuestasYEvaluacionesEnum getCatalogoSeleccionado() {
        return catalogoSeleccionado;
    }

    /**
     * @param catalogoSeleccionado the catalogoSeleccionado to set
     */
    public void setCatalogoSeleccionado(CatEncuestasYEvaluacionesEnum catalogoSeleccionado) {
        this.catalogoSeleccionado = catalogoSeleccionado;
    }

    /**
     * @return the catalogosEncuestasYEval
     */
    public CatEncuestasYEvaluacionesEnum[] getCatalogosEncuestasYEval() {
        return catalogosEncuestasYEval;
    }

    /**
     * @param catalogosEncuestasYEval the catalogosEncuestasYEval to set
     */
    public void setCatalogosEncuestasYEval(CatEncuestasYEvaluacionesEnum[] catalogosEncuestasYEval) {
        this.catalogosEncuestasYEval = catalogosEncuestasYEval;
    }

    /**
     * @return the springServiceAdapter
     */
    private EncuestaServiceAdapter getEncuestaServiceAdapter() {
        if (encuestaServiceAdapter == null) {
            encuestaServiceAdapter = new EncuestaServiceAdapter();
        }
        return encuestaServiceAdapter;
    }

    /**
     * @param
     */
    public void setEncuestaServiceAdapter(EncuestaServiceAdapter encuestaServiceAdapter) {
        this.encuestaServiceAdapter = encuestaServiceAdapter;
    }

    /**
     * @return the muestraCompTabla
     */
    public boolean isMuestraCompTabla() {
        return muestraCompTabla;
    }

    /**
     * @param muestraCompTabla the muestraCompTabla to set
     */
    public void setMuestraCompTabla(boolean muestraCompTabla) {
        this.muestraCompTabla = muestraCompTabla;
    }

    /**
     * @return the lstCatContexto
     */
    public List<CatalogoComunDTO> getLstCatContexto() {
        if (lstCatContexto == null) {
            lstCatContexto = new ArrayList<CatalogoComunDTO>();
        }
        return lstCatContexto;
    }

    /**
     * @param lstCatContexto the lstCatContexto to set
     */
    public void setLstCatContexto(List<CatalogoComunDTO> lstCatContexto) {
        this.lstCatContexto = lstCatContexto;
    }

    /**
     * @return the renderCmpSelect
     */
    public boolean isRenderCmpSelect() {
        return renderCmpSelect;
    }

    /**
     * @param renderCmpSelect the renderCmpSelect to set
     */
    public void setRenderCmpSelect(boolean renderCmpSelect) {
        this.renderCmpSelect = renderCmpSelect;
    }

    /**
     * @return the idContexto
     */
    public Long getIdContexto() {
        return idContexto;
    }

    /**
     * @param idContexto the idContexto to set
     */
    public void setIdContexto(Long idContexto) {
        this.idContexto = idContexto;
    }

}
