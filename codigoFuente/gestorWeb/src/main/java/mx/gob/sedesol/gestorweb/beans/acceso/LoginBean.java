package mx.gob.sedesol.gestorweb.beans.acceso;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.admin.CorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaCorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ParametrosSistemaEnum;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.CorreoElectronicoService;
import mx.gob.sedesol.basegestor.service.admin.PersonaCorreoService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.dto.UsuarioSessionDTO;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@ViewScoped
@ManagedBean
public class LoginBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(LoginBean.class);

	private UsuarioSessionDTO usuarioSession;

	private PersonaCorreoDTO personaCorreoRecup;

	@ManagedProperty("#{appProperties}")
	private transient ResourceBundle appProperties;

	@ManagedProperty("#{personaService}")
	private transient PersonaService personaService;

	@ManagedProperty("#{personaCorreoService}")
	private transient PersonaCorreoService personaCorreoService;
	
	@ManagedProperty(value = "#{parametroSistemaService}")
	private transient ParametroSistemaService parametroSistemaService;
	
	@ManagedProperty(value = "#{correoElectronicoService}")
	private transient CorreoElectronicoService correoElectronicoService;

	@ManagedProperty("#{sistema}")
	private SistemaBean textosSistema;

	public LoginBean() {
		this.usuarioSession = getUsuarioEnSession();
		personaCorreoRecup = new PersonaCorreoDTO();
	}

	/**
	 * 
	 */
	public String regresaPantallaPrincipal() {
		return ConstantesGestorWeb.NAVEGA_INICIO;
	}

	/**
	 * 
	 */
	public void muestraErrores() {
		boolean error = false;
		if (ObjectUtils.isNotNull(getFacesContext().getExternalContext().getRequestParameterMap()
				.get(ConstantesGestorWeb.PARAM_ERROR_LOGIN)))
			error = Boolean.parseBoolean(getFacesContext().getExternalContext().getRequestParameterMap()
					.get(ConstantesGestorWeb.PARAM_ERROR_LOGIN));

		if (error) {
			String mensaje = (String) getRequest().getSession().getAttribute(ConstantesGestorWeb.ERRORES_LOGIN);
			if (!ObjectUtils.isNullOrEmpty(mensaje)) {
				agregarMsgError(mensaje, null);
				getRequest().getSession().removeAttribute(ConstantesGestorWeb.ERRORES_LOGIN);
			}
		}
	}

	/**
	 * 
	 */
	public void doLogOut() {
		FacesContext fContext = FacesContext.getCurrentInstance();
		ExternalContext extContext = fContext.getExternalContext();
		try {
			extContext.redirect(extContext.getRequestContextPath() + "/j_spring_security_logout");
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}

	}

	/**
	 * 
	 */
	public void recuperarContrasenia() {

		if (!ObjectUtils.isNullOrEmpty(personaCorreoRecup)
				&& !ObjectUtils.isNullOrEmpty(personaCorreoRecup.getCorreoElectronico())) {

			try {
				PersonaCorreoDTO personaCorreo = personaCorreoService
						.buscaPersonaCorreoElectronico(personaCorreoRecup.getCorreoElectronico());

				if (ObjectUtils.isNotNull(personaCorreo) && ObjectUtils.isNotNull(personaCorreo.getPersona())) {

					enviarCorreo(personaCorreo);

				} else {
					agregarMsgError(MensajesSistemaEnum.LOGIN_MSG_RECUP_PSWD_CORREO_INEXISTENTE.getId(), null,
							textosSistema);

				}
			} catch (Exception e1) {
				log.error(e1.getMessage(), e1);
			}

		}
	}

	private void enviarCorreo(PersonaCorreoDTO personaCorreo) {
		String token = getToken(10);
		
		StringBuilder rutaActivacion = new StringBuilder(ConstantesGestorWeb.SUFFIX_HTTP_S)
				.append(getFacesContext().getExternalContext().getRequestServerName()).append(":")
				.append(getFacesContext().getExternalContext().getRequestServerPort()).append("/")
				.append(getFacesContext().getExternalContext().getContextName())
				.append("/views/public/registro/recuperarContrasenia.jsf?token=").append(token)
				.append("&usuario=").append(personaCorreo.getPersona().getUsuario());


		CorreoDTO correoDto = correoElectronicoService.asignaParametrosConfigCorreo();
		correoDto.setTitulo(parametroSistemaService.obtenerParametro(ParametrosSistemaEnum.PS_TITULO_NOTIFICACION_CORREO.getClave()));
		correoDto.setAsunto(parametroSistemaService.obtenerParametro(ParametrosSistemaEnum.PS_ASUNTO_CORREO_RECUP_PASSWORD.getClave()));
		correoDto.setRemitente(parametroSistemaService.obtenerParametro(ParametrosSistemaEnum.PS_CONFIG_CORREO_CUENTA_ADMIN.getClave()));
		correoDto.setContenido(MessageFormat.format(
				textosSistema.obtenerTexto(MensajesSistemaEnum.LOGIN_MSG_RECUP_PSWD_EMAIL_ENVIADO.getId()), rutaActivacion));

		personaCorreo.getPersona().setToken(token);
		ResultadoDTO<PersonaDTO> res = personaService.enviaCorreoRecuperarContrasenia(personaCorreo,correoDto);
		
		if (ObjectUtils.isNotNull(res) && res.getResultado().getValor())
			agregarMsgInfo(MensajesSistemaEnum.LOGIN_MSG_RECUP_PSWD.getId(), null, textosSistema);
		else
			agregarMsgError(MensajesSistemaEnum.ADMIN_MSG_ERROR_GLOBAL.getId(), null, textosSistema);
	}

	public String navegaRegistro() {
		return ConstantesGestorWeb.NAVEGA_A_REGISTRO;
	}

	public UsuarioSessionDTO getUsuarioSession() {
		return usuarioSession;
	}

	public void setUsuarioSession(UsuarioSessionDTO usuarioSession) {
		this.usuarioSession = usuarioSession;
	}

	/**
	 * @return the personaCorreoRecup
	 */
	public PersonaCorreoDTO getPersonaCorreoRecup() {
		return personaCorreoRecup;
	}

	/**
	 * @param personaCorreoRecup
	 *            the personaCorreoRecup to set
	 */
	public void setPersonaCorreoRecup(PersonaCorreoDTO personaCorreoRecup) {
		this.personaCorreoRecup = personaCorreoRecup;
	}

	/**
	 * @return the appProperties
	 */
	public ResourceBundle getAppProperties() {
		return appProperties;
	}

	/**
	 * @param appProperties
	 *            the appProperties to set
	 */
	public void setAppProperties(ResourceBundle appProperties) {
		this.appProperties = appProperties;
	}

	/**
	 * @return the personaService
	 */
	public PersonaService getPersonaService() {
		return personaService;
	}

	/**
	 * @param personaService
	 *            the personaService to set
	 */
	public void setPersonaService(PersonaService personaService) {
		this.personaService = personaService;
	}

	/**
	 * @return the personaCorreoService
	 */
	public PersonaCorreoService getPersonaCorreoService() {
		return personaCorreoService;
	}

	/**
	 * @param personaCorreoService
	 *            the personaCorreoService to set
	 */
	public void setPersonaCorreoService(PersonaCorreoService personaCorreoService) {
		this.personaCorreoService = personaCorreoService;
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
