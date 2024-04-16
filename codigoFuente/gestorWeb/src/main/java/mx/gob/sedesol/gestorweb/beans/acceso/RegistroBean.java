package mx.gob.sedesol.gestorweb.beans.acceso;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import mx.gob.sedesol.basegestor.commons.dto.ConverterDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.CorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaCorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.TipoCorreoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ParametrosSistemaEnum;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.CorreoElectronicoService;
import mx.gob.sedesol.basegestor.service.admin.PersonaCorreoService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.basegestor.service.admin.TiposCorreoService;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@ViewScoped
@ManagedBean(name = "registroBean")
public class RegistroBean extends BaseBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(RegistroBean.class);

    @ManagedProperty(value = "#{personaService}")
    private transient PersonaService personaService;

    @ManagedProperty(value = "#{tiposCorreoService}")
    private transient TiposCorreoService tipoCorreoService;

    @ManagedProperty(value = "#{personaCorreoService}")
    private transient PersonaCorreoService personaCorreoService;

    @ManagedProperty("#{msgGestorWeb}")
    private transient ResourceBundle msgGestorWeb;

    @ManagedProperty("#{appProperties}")
    private transient ResourceBundle appProperties;

    @ManagedProperty("#{sistema}")
    private SistemaBean textosSistema;

    @ManagedProperty(value = "#{parametroSistemaService}")
    private transient ParametroSistemaService parametroSistemaService;

    @ManagedProperty(value = "#{correoElectronicoService}")
    private transient CorreoElectronicoService correoElectronicoService;

    private PersonaDTO persona;
    private PersonaCorreoDTO personaCorreo;
    private String contrasenia;
    private String contraseniaConfirm;
    private TipoCorreoDTO tipoCorreo;
    private List<TipoCorreoDTO> catTiposCorreo;

    private String msgStatusActivacion;

    private boolean botonLoginActivo;
    private String paramToken;
    private String paramUsuario;
    private boolean panelRecupContrasenia;

    /**
     * Constructor
     */
    public RegistroBean() {
        this.iniciaObjetos();

    }

    /**
     * Metodo de persistencia de persona
     */
    public void registrarPersona() {
        logger.info("Registrando persona.");
        ResultadoDTO<PersonaDTO> resultadoPeticion;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (ObjectUtils.isNotNull(persona) && ObjectUtils.isNotNull(personaCorreo)
                && !validaExisteUsuario(personaCorreo.getCorreoElectronico())) {
            try {

                // Encriptacion de Cadena
                PersonaDTO usuariosystem = personaService
                        .obtienePersonaPorNombreUsuario(ConstantesGestorWeb.USUARIO_SYSTEM);
                persona.setUsuarioModifico(usuariosystem.getIdPersona());
                persona.setFechaRegistro(new Date());
                persona.setToken(getToken(8));
                persona.setPersonaCorreos(new ArrayList<>());
                persona.setContrasenia(encoder.encode(persona.getContrasenia()));

                tipoCorreo = tipoCorreoService.buscarPorId(2);

                personaCorreo.setTipoCorreo(tipoCorreo);
                personaCorreo.setActivo(ConstantesGestorWeb.ACTIVO);
                personaCorreo.setFechaRegistro(new Date());
                personaCorreo.setUsuarioModifico(usuariosystem.getIdPersona());
                persona.getPersonaCorreos().add(personaCorreo);
                persona.setUsuario(personaCorreo.getCorreoElectronico());

                StringBuilder rutaActivacion = new StringBuilder(ConstantesGestorWeb.SUFFIX_HTTP_S)
                        .append(getFacesContext().getExternalContext().getRequestServerName()).append(":")
                        .append(getFacesContext().getExternalContext().getRequestServerPort()).append("/")
                        .append(getFacesContext().getExternalContext().getContextName())
                        .append("/views/public/registro/confirmarRegistro.jsf?token=").append(persona.getToken())
                        .append("&usuario=").append(persona.getUsuario());

                CorreoDTO correoDto = correoElectronicoService.asignaParametrosConfigCorreo();

                correoDto.setTitulo(parametroSistemaService.obtenerParametro(ParametrosSistemaEnum.PS_TITULO_NOTIFICACION_CORREO.getClave()));
                correoDto.setAsunto(parametroSistemaService.obtenerParametro(ParametrosSistemaEnum.PS_ASUNTO_CORREO_ACTIVACION_CTA.getClave()));
                correoDto.setRemitente(parametroSistemaService.obtenerParametro(ParametrosSistemaEnum.PS_CONFIG_CORREO_CUENTA_ADMIN.getClave()));
                correoDto.setContenido(MessageFormat.format(
                        textosSistema.obtenerTexto(MensajesSistemaEnum.LOGIN_REGISTRO_CUENTA_POR_EMAIL.getId()),
                        rutaActivacion));

                resultadoPeticion = personaService.guardaPersonaRolAlumno(persona, correoDto);

                pintarMensajes(resultadoPeticion);

            } catch (Exception e) {
                agregarMsgError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO.getId(), null, textosSistema);
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void pintarMensajes(ResultadoDTO<PersonaDTO> resultadoPeticion) {
        if (ObjectUtils.isNotNull(resultadoPeticion) && resultadoPeticion.getResultado().getValor()) {
            ConverterDTO<PersonaDTO> converterPersona = new ConverterDTO<>(resultadoPeticion.getDto(),
                    PersonaDTO.class);

            logger.info(converterPersona.getObjetoDTOFromObject().toString());

            agregarMsgInfo(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_EXITOSO.getId(), null, textosSistema);

        } else {
            if (ObjectUtils.isNotNull(resultadoPeticion)) {
                agregarMsgError(obtenerErroresDeServicio(resultadoPeticion.getMensajes()), null);
            }
        }
    }

    /**
     *
     * @param usuario
     * @return
     */
    private boolean validaExisteUsuario(String usuario) {
        try {
            PersonaCorreoDTO personaCorreoExiste = personaCorreoService.buscaPersonaCorreoElectronico(usuario);
            if (ObjectUtils.isNotNull(personaCorreoExiste)) {
                return true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return true;
        }

        return false;
    }

    /**
     *
     */
    public void confirmaRegistroWeb() {
        paramToken = getFacesContext().getExternalContext().getRequestParameterMap()
                .get(ConstantesGestorWeb.PARAM_TOKEN);
        paramUsuario = getFacesContext().getExternalContext().getRequestParameterMap()
                .get(ConstantesGestorWeb.PARAM_USUARIO);

        if (!ObjectUtils.isNullOrEmpty(paramToken) && !ObjectUtils.isNullOrEmpty(paramUsuario)) {
            try {
                ResultadoDTO<PersonaDTO> resultado = personaService.activaUsuarioWeb(paramUsuario, paramToken);
                if (ObjectUtils.isNotNull(resultado) && resultado.getResultado().getValor()) {
                    msgStatusActivacion = MessageFormat.format(
                            textosSistema.obtenerTexto(MensajesSistemaEnum.LOGIN_REGISTRO_CUENTA_EMAIL_ENVIADO.getId()),
                            paramUsuario);
                    setBotonLoginActivo(Boolean.TRUE);
                } else {
                    msgStatusActivacion = resultado.getMensajes().get(ConstantesGestorWeb.INDICE_INICIAL);
                }

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                agregarMsgError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO.getId(), null, textosSistema);
            }
        }
    }

    public void validaTokenUsuario() {

        paramToken = getFacesContext().getExternalContext().getRequestParameterMap()
                .get(ConstantesGestorWeb.PARAM_TOKEN);
        paramUsuario = getFacesContext().getExternalContext().getRequestParameterMap()
                .get(ConstantesGestorWeb.PARAM_USUARIO);

        if (!isPanelRecupContrasenia()) {
            if (ObjectUtils.isNull(paramToken) || ObjectUtils.isNull(paramUsuario)) {
                return;
            }

            persona = personaService.obtienePersonaPorNombreUsuario(paramUsuario);

            if (ObjectUtils.isNotNull(persona) && !ObjectUtils.isNullOrEmpty(persona.getToken())
                    && persona.getToken().equals(paramToken)) {
                setPanelRecupContrasenia(Boolean.TRUE);
            } else {
                setPanelRecupContrasenia(Boolean.FALSE);
                agregarMsgError(MensajesSistemaEnum.RECUP_PSWD_MSG_TOKEN_INVALIDO.getId(), null, textosSistema);
            }
        }
    }

    /**
     *
     */
    public void confirmaRecuperacionContrasenias() {

        if (ObjectUtils.isNullOrEmpty(contrasenia) && ObjectUtils.isNullOrEmpty(contraseniaConfirm)) {
            return;
        }

        try {
            if (contrasenia.equals(contraseniaConfirm)) {

                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                persona.setContrasenia(encoder.encode(contrasenia));
                persona.setToken(null);
                PersonaDTO usuariosystem = personaService
                        .obtienePersonaPorNombreUsuario(ConstantesGestorWeb.USUARIO_SYSTEM);
                persona.setUsuarioModifico(usuariosystem.getIdPersona());
                persona.setFechaActualizacion(new Date());

                //GUSTAVO --BitacoraUtil.llenarBitacora(persona, getUsuarioEnSession(), ConstantesBitacora.PERSONA_AGREGAR, request);


                ResultadoDTO<PersonaDTO> res = personaService.actualizar(persona);
                if (ObjectUtils.isNotNull(res) && res.getResultado().getValor()) {
                    setBotonLoginActivo(Boolean.TRUE);
                    agregarMsgInfo(MensajesSistemaEnum.RECUP_PSWD_MSG_ACTUALIZACION_CORRECTA.getId(), null,
                            textosSistema);
                } else {
                    agregarMsgError(MensajesSistemaEnum.ADMIN_MSG_GUARDADO_FALLIDO.getId(), null, textosSistema);
                }
            } else {
                agregarMsgError(MensajesSistemaEnum.RECUP_PSWD_MSG_CAMPOS_SIN_COINCIDENCIA.getId(), null,
                        textosSistema);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     *
     * @return
     */
    public void navegaLogin() {
        try {
            getFacesContext().getExternalContext().redirect("../login/login.jsf");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     *
     */
    @SuppressWarnings("unchecked")
    public void iniciaObjetos() {
        persona = new PersonaDTO();
        personaCorreo = new PersonaCorreoDTO();
        catTiposCorreo = (List<TipoCorreoDTO>) getSession().getServletContext()
                .getAttribute(ConstantesGestorWeb.CAT_TIPOS_CORREO);
    }

    /**
     * @return the persona
     */
    public PersonaDTO getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(PersonaDTO persona) {
        this.persona = persona;
    }

    /**
     * @return the personaService
     */
    public PersonaService getPersonaService() {
        return personaService;
    }

    /**
     * @param personaService the personaService to set
     */
    public void setPersonaService(PersonaService personaService) {
        this.personaService = personaService;
    }

    /**
     * @return the msgGestorWeb
     */
    public ResourceBundle getMsgGestorWeb() {
        return msgGestorWeb;
    }

    /**
     * @param msgGestorWeb the msgGestorWeb to set
     */
    public void setMsgGestorWeb(ResourceBundle msgGestorWeb) {
        this.msgGestorWeb = msgGestorWeb;
    }

    /**
     * @return the appProperties
     */
    public ResourceBundle getAppProperties() {
        return appProperties;
    }

    /**
     * @param appProperties the appProperties to set
     */
    public void setAppProperties(ResourceBundle appProperties) {
        this.appProperties = appProperties;
    }

    /**
     * @return the personaCorreo
     */
    public PersonaCorreoDTO getPersonaCorreo() {
        return personaCorreo;
    }

    /**
     * @param personaCorreo the personaCorreo to set
     */
    public void setPersonaCorreo(PersonaCorreoDTO personaCorreo) {
        this.personaCorreo = personaCorreo;
    }

    /**
     * @return the tipoCorreo
     */
    public TipoCorreoDTO getTipoCorreo() {
        return tipoCorreo;
    }

    /**
     * @param tipoCorreo the tipoCorreo to set
     */
    public void setTipoCorreo(TipoCorreoDTO tipoCorreo) {
        this.tipoCorreo = tipoCorreo;
    }

    /**
     * @return the tipoCorreoService
     */
    public TiposCorreoService getTipoCorreoService() {
        return tipoCorreoService;
    }

    /**
     * @param tipoCorreoService the tipoCorreoService to set
     */
    public void setTipoCorreoService(TiposCorreoService tipoCorreoService) {
        this.tipoCorreoService = tipoCorreoService;
    }

    /**
     * @return the msgStatusActivacion
     */
    public String getMsgStatusActivacion() {
        return msgStatusActivacion;
    }

    /**
     * @param msgStatusActivacion the msgStatusActivacion to set
     */
    public void setMsgStatusActivacion(String msgStatusActivacion) {
        this.msgStatusActivacion = msgStatusActivacion;
    }

    /**
     * @return the catTiposCorreo
     */
    public List<TipoCorreoDTO> getCatTiposCorreo() {
        return catTiposCorreo;
    }

    /**
     * @param catTiposCorreo the catTiposCorreo to set
     */
    public void setCatTiposCorreo(List<TipoCorreoDTO> catTiposCorreo) {
        this.catTiposCorreo = catTiposCorreo;
    }

    /**
     * @return the botonLoginActivo
     */
    public boolean isBotonLoginActivo() {
        return botonLoginActivo;
    }

    /**
     * @param botonLoginActivo the botonLoginActivo to set
     */
    public void setBotonLoginActivo(boolean botonLoginActivo) {
        this.botonLoginActivo = botonLoginActivo;
    }

    /**
     * @return the personaCorreoService
     */
    public PersonaCorreoService getPersonaCorreoService() {
        return personaCorreoService;
    }

    /**
     * @param personaCorreoService the personaCorreoService to set
     */
    public void setPersonaCorreoService(PersonaCorreoService personaCorreoService) {
        this.personaCorreoService = personaCorreoService;
    }

    /**
     * @return the contrasenia
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * @param contrasenia the contrasenia to set
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /**
     * @return the contraseniaConfirm
     */
    public String getContraseniaConfirm() {
        return contraseniaConfirm;
    }

    /**
     * @param contraseniaConfirm the contraseniaConfirm to set
     */
    public void setContraseniaConfirm(String contraseniaConfirm) {
        this.contraseniaConfirm = contraseniaConfirm;
    }

    /**
     * @return the paramToken
     */
    public String getParamToken() {
        return paramToken;
    }

    /**
     * @param paramToken the paramToken to set
     */
    public void setParamToken(String paramToken) {
        this.paramToken = paramToken;
    }

    /**
     * @return the paramUsuario
     */
    public String getParamUsuario() {
        return paramUsuario;
    }

    /**
     * @param paramUsuario the paramUsuario to set
     */
    public void setParamUsuario(String paramUsuario) {
        this.paramUsuario = paramUsuario;
    }

    /**
     * @return the panelRecupContrasenia
     */
    public boolean isPanelRecupContrasenia() {
        return panelRecupContrasenia;
    }

    /**
     * @param panelRecupContrasenia the panelRecupContrasenia to set
     */
    public void setPanelRecupContrasenia(boolean panelRecupContrasenia) {
        this.panelRecupContrasenia = panelRecupContrasenia;
    }

    public SistemaBean getTextosSistema() {
        return textosSistema;
    }

    public void setTextosSistema(SistemaBean textosSistema) {
        this.textosSistema = textosSistema;
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

}
