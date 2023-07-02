package mx.gob.sedesol.gestorweb.beans.administracion.publico;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.ToggleEvent;

import mx.gob.sedesol.basegestor.commons.dto.admin.CorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ParametroSistemaDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ParametrosSistemaEnum;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.CorreoElectronicoService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;

@ViewScoped
@ManagedBean
public class ConfigCorreoBean extends BaseBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ConfigCorreoBean.class);

    private CorreoDTO datosConfig;
    private CorreoDTO datosCorreo;
    private String para;
    private String paraCC;
    private String password;

    @ManagedProperty(value = "#{correoElectronicoService}")
    private transient CorreoElectronicoService correoElectronicoService;

    @ManagedProperty(value = "#{parametroSistemaService}")
    private transient ParametroSistemaService parametroSistemaService;

    public ConfigCorreoBean() {
        para = StringUtils.EMPTY;
        paraCC = StringUtils.EMPTY;
        datosCorreo = new CorreoDTO();
        datosCorreo.setDestinatarios(new ArrayList<String>());
        datosCorreo.setDestinatariosCC(new ArrayList<String>());
    }

    @PostConstruct
    public void inicializaDatos() {
        datosConfig = correoElectronicoService.asignaParametrosConfigCorreo();
    }

    public void nuevoPara() {
        para = StringUtils.EMPTY;
    }

    public void nuevoParaCC() {
        paraCC = StringUtils.EMPTY;
    }

    public void enviarCorreo() {
        if (ObjectUtils.isNotNull(datosCorreo)) {

			if(ObjectUtils.isNullOrEmpty(datosCorreo.getHost())){
				datosCorreo.setHost(parametroSistemaService.obtenerParametro(ParametrosSistemaEnum.PS_CONFIG_CORREO_HOST.getClave()));
				datosCorreo.setPort(parametroSistemaService.obtenerParametro(ParametrosSistemaEnum.PS_CONFIG_CORREO_PUERTO.getClave()));
				datosCorreo.setUsuarioCorreo(parametroSistemaService.obtenerParametro(ParametrosSistemaEnum.PS_CONFIG_CORREO_CUENTA_ADMIN.getClave()));
				datosCorreo.setPasswordCorreo(parametroSistemaService.obtenerParametro(ParametrosSistemaEnum.PS_CONFIG_CORREO_CUENTA_PASSWORD.getClave()));
				
				logger.info("host: " + datosCorreo.getHost());
				logger.info("port: " + datosCorreo.getPort());
				logger.info("usuariocorreo: " + datosCorreo.getUsuarioCorreo());
				logger.info("password: " + datosCorreo.getPasswordCorreo());
			}
        	
            //datosCorreo.setAsunto(datosCorreo.getTitulo());
            
			datosCorreo.setRemitente(datosCorreo.getUsuarioCorreo());
            if (correoElectronicoService.enviaCorreoElectronico(datosCorreo)) {

                para = StringUtils.EMPTY;
                paraCC = StringUtils.EMPTY;
                datosCorreo = new CorreoDTO();

                agregarMsgInfo("Se envió el correo exitosamete.", null);

            } else {
                agregarMsgError("Ocurrió un error al eviar el correo", null);
            }
        }
    }

    public void onTogglePnlCorreo(ToggleEvent event) {
        datosCorreo = correoElectronicoService.asignaParametrosConfigCorreo();
        datosCorreo.setDestinatarios(new ArrayList<String>());
        datosCorreo.setDestinatariosCC(new ArrayList<String>());
    }

    /**
     *
     */
    public void guardaConfigCorreo() {
        try {

            if (validaDatosConfig()) {

                List<ParametroSistemaDTO> params = new ArrayList<>();
                params.add(this.getParametroSistemaActualizar(ParametrosSistemaEnum.PS_CONFIG_CORREO_HOST.getClave(), datosConfig.getHost()));
                params.add(this.getParametroSistemaActualizar(ParametrosSistemaEnum.PS_CONFIG_CORREO_PUERTO.getClave(), datosConfig.getPort()));
                params.add(this.getParametroSistemaActualizar(ParametrosSistemaEnum.PS_CONFIG_CORREO_CUENTA_ADMIN.getClave(), datosConfig.getUsuarioCorreo()));

                if (!datosConfig.getPasswordCorreo().isEmpty()) {
                    params.add(this.getParametroSistemaActualizar(ParametrosSistemaEnum.PS_CONFIG_CORREO_CUENTA_PASSWORD.getClave(), datosConfig.getPasswordCorreo()));
                }

                for (ParametroSistemaDTO pm : params) {
                    //GUSTAVO --BitacoraUtil.llenarBitacora(pm, getUsuarioEnSession(), ConstantesBitacora.PARAMETRO_SISTEMA_EDITAR, request);
                    parametroSistemaService.actualizar(pm);
                }

                agregarMsgInfo("Se guardaron los cambios realizados", null);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            agregarMsgError("Ocurrio un error", null);
        }
    }

    /**
     *
     * @param cveParam
     * @param valorParam
     * @return
     */
    private ParametroSistemaDTO getParametroSistemaActualizar(String cveParam, String valorParam) {

        Date fechaAct = new Date();
        ParametroSistemaDTO paramSistema = parametroSistemaService.obtieneParametroPorClave(cveParam);
        paramSistema.setValor(valorParam);
        paramSistema.setFechaActualizacion(fechaAct);
        paramSistema.setUsuarioModifico(ConstantesGestorWeb.ID_USUARIO_SYSTEM);

        return paramSistema;
    }

    /**
     *
     * @return
     */
    private boolean validaDatosConfig() {

        if (datosConfig.getHost().isEmpty()) {
            return false;
        }

        if (datosConfig.getPort().isEmpty()) {
            return false;
        }

        if (datosConfig.getUsuarioCorreo().isEmpty()) {
            return false;
        }

        return true;

    }

    /**
     * @return the datosConfig
     */
    public CorreoDTO getDatosConfig() {
        return datosConfig;
    }

    /**
     * @param datosConfig the datosConfig to set
     */
    public void setDatosConfig(CorreoDTO datosConfig) {
        this.datosConfig = datosConfig;
    }

    /**
     * @return the correoElectronicoService
     */
    public CorreoElectronicoService getCorreoElectronicoService() {
        return correoElectronicoService;
    }

    /**
     * @param correoElectronicoService the correoElectronicoService to set
     */
    public void setCorreoElectronicoService(CorreoElectronicoService correoElectronicoService) {
        this.correoElectronicoService = correoElectronicoService;
    }

    /**
     * @return the datosCorreo
     */
    public CorreoDTO getDatosCorreo() {
        return datosCorreo;
    }

    /**
     * @param datosCorreo the datosCorreo to set
     */
    public void setDatosCorreo(CorreoDTO datosCorreo) {
        this.datosCorreo = datosCorreo;
    }

    /**
     * @return the para
     */
    public String getPara() {
        return para;
    }

    /**
     * @param para the para to set
     */
    public void setPara(String para) {
        this.para = para;
    }

    /**
     * @return the paraCC
     */
    public String getParaCC() {
        return paraCC;
    }

    /**
     * @param paraCC the paraCC to set
     */
    public void setParaCC(String paraCC) {
        this.paraCC = paraCC;
    }

    /**
     * @return the parametroSistemaService
     */
    public ParametroSistemaService getParametroSistemaService() {
        return parametroSistemaService;
    }

    /**
     * @param parametroSistemaService the parametroSistemaService to set
     */
    public void setParametroSistemaService(ParametroSistemaService parametroSistemaService) {
        this.parametroSistemaService = parametroSistemaService;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
