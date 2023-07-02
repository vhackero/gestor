package mx.gob.sedesol.gestorweb.beans.administracion;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import mx.gob.sedesol.basegestor.commons.dto.admin.CorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MensajeOperacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaCorreoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.VariableMensajeOperacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.encuestas.EncuestaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.GrupoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PersonaResponsabilidadesDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RelGrupoParticipanteDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ReponsableProduccionEcDTO;
import mx.gob.sedesol.basegestor.commons.utils.DateUtils;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ParametrosSistemaEnum;
import mx.gob.sedesol.basegestor.mongo.service.NotificacionSistemaService;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.CorreoElectronicoService;
import mx.gob.sedesol.basegestor.service.admin.MensajeOperacionService;
import mx.gob.sedesol.basegestor.service.admin.PersonaCorreoService;
import mx.gob.sedesol.basegestor.service.gestionescolar.GrupoParticipanteService;
import mx.gob.sedesol.basegestor.service.gestionescolar.RelReponsableProduccionEcService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;

@ManagedBean
@SessionScoped
public class CorreoNotificacionBean extends BaseBean {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CorreoNotificacionBean.class);
	@ManagedProperty(value = "#{correoElectronicoService}")
	private CorreoElectronicoService correoElectronicoService;

	@ManagedProperty(value = "#{parametroSistemaService}")
	private ParametroSistemaService parametroSistemaService;

	@ManagedProperty(value = "#{notificacionSistemaService}")
	private NotificacionSistemaService notificacionSistemaService;

	@ManagedProperty("#{personaCorreoService}")
	private PersonaCorreoService personaCorreoService;

	@ManagedProperty("#{mensajeOperacionService}")
	private MensajeOperacionService mensajeOperacionService;

	@ManagedProperty("#{relReponsableProduccionEcService}")
	private RelReponsableProduccionEcService relReponsableProduccionEcService;

	@ManagedProperty("#{grupoParticipanteService}")
	private GrupoParticipanteService grupoParticipanteService;

	
	public void notificarUsuariosEncuestaPendiente(String claveNotificacion, String claveCorreo,List<RelGrupoParticipanteDTO> rgp,
			EncuestaDTO encuesta, EventoCapacitacionDTO evento) {
		for (RelGrupoParticipanteDTO relGrupoParticipanteDTO : rgp) {
			notificarUsuarioEncuestaPendiente(claveNotificacion, claveCorreo, relGrupoParticipanteDTO.getPersona(), encuesta, evento);
		}
		
	}
	
	
	public void notificarUsuarioEncuestaPendiente(String claveNotificacion, String claveCorreo, PersonaDTO persona,
			EncuestaDTO encuesta, EventoCapacitacionDTO evento) {
		enviarNotificacionEncuestaPendiente(claveNotificacion, persona, encuesta, evento);
		enviarCorreoEncuestaPendiente(claveCorreo, persona, encuesta, evento);
	}

	private void enviarNotificacionEncuestaPendiente(String claveNotificacion, PersonaDTO persona, EncuestaDTO encuesta,
			EventoCapacitacionDTO evento) {
		try {
			List<VariableMensajeOperacionDTO> variables = new ArrayList<>();

			variables.add(new VariableMensajeOperacionDTO("{0}", evento.getNombreEc()));
			variables.add(new VariableMensajeOperacionDTO("{1}", encuesta.getNombre()));
			notificacionSistemaService.enviarNotificacion(claveNotificacion, persona.getIdPersona(), variables);

		} catch (Exception e) {
			logger.info("Ocurrio un error al intentar enviar la notificacion de la encuesta pendiente.");
		}
	}

	private void enviarCorreoEncuestaPendiente(String claveCorreo, PersonaDTO persona, EncuestaDTO encuesta,
			EventoCapacitacionDTO evento) {
		try {
			PersonaCorreoDTO correoPersona = personaCorreoService.obtenerCorreoInstitucional(persona.getIdPersona());
			List<String> correosUsuarios = new ArrayList<>();
			correosUsuarios.add(correoPersona.getCorreoElectronico());

			String nombreEvento = evento.getNombreEc();
			String nombreEncuesta = encuesta.getNombre();

			MensajeOperacionDTO mensaje = getMensajeOperacionService()
					.obtenerMensajeActivoPorClaveFuncionalidad(claveCorreo);

			CorreoDTO correoDto = getCorreoElectronicoService().asignaParametrosConfigCorreo();

			correoDto.setTitulo(MessageFormat.format(mensaje.getTitulo(), nombreEvento, nombreEncuesta));
			correoDto.setAsunto(MessageFormat.format(mensaje.getTitulo(), nombreEvento, nombreEncuesta));
			correoDto.setDestinatarios(correosUsuarios);
			correoDto.setRemitente(getParametroSistemaService()
					.obtenerParametro(ParametrosSistemaEnum.PS_CONFIG_CORREO_CUENTA_ADMIN.getClave()));
			correoDto.setContenido(MessageFormat.format(mensaje.getMensaje(), nombreEvento, nombreEncuesta));
			correoElectronicoService.enviaCorreoElectronico(correoDto);

		} catch (Exception e) {
			logger.info("Ocurrio un error al intentar enviar el correo de la encuesta pendiente.");
		}
	}

	public void notificarUsuariosActaCerrada(String claveNotificacion, String claveCorreo, GrupoDTO grupo) {
		List<RelGrupoParticipanteDTO> listaRelacionGrupoParticipante = grupoParticipanteService
				.getParticipantesByGrupo(grupo.getIdGrupo());
		for (RelGrupoParticipanteDTO relacionGrupoParticipante : listaRelacionGrupoParticipante) {
			enviarNotificacionUsuarioActaCerrada(claveNotificacion, relacionGrupoParticipante.getPersona(), grupo);
			enviarCorreoUsuarioActaCerrada(claveCorreo, relacionGrupoParticipante.getPersona(), grupo);
		}
	}

	private void enviarNotificacionUsuarioActaCerrada(String claveNotificacion, PersonaDTO persona, GrupoDTO grupo) {
		try {
			List<VariableMensajeOperacionDTO> variables = new ArrayList<>();

			variables.add(new VariableMensajeOperacionDTO("{0}", grupo.getEvento().getNombreEc()));

			notificacionSistemaService.enviarNotificacion(claveNotificacion, persona.getIdPersona(), variables);

		} catch (Exception e) {
			logger.info("Ocurrio un error al intentar enviar la notificacion del cierre de acta.");
		}
	}

	private void enviarCorreoUsuarioActaCerrada(String claveCorreo, PersonaDTO persona, GrupoDTO grupo) {
		try {
			PersonaCorreoDTO correoPersona = personaCorreoService.obtenerCorreoInstitucional(persona.getIdPersona());
			List<String> correosUsuarios = new ArrayList<>();
			correosUsuarios.add(correoPersona.getCorreoElectronico());

			String nombreEvento = grupo.getEvento().getNombreEc();

			MensajeOperacionDTO mensaje = getMensajeOperacionService()
					.obtenerMensajeActivoPorClaveFuncionalidad(claveCorreo);

			CorreoDTO correoDto = getCorreoElectronicoService().asignaParametrosConfigCorreo();

			correoDto.setTitulo(MessageFormat.format(mensaje.getTitulo(), nombreEvento));
			correoDto.setAsunto(MessageFormat.format(mensaje.getTitulo(), nombreEvento));
			correoDto.setDestinatarios(correosUsuarios);
			correoDto.setRemitente(getParametroSistemaService()
					.obtenerParametro(ParametrosSistemaEnum.PS_CONFIG_CORREO_CUENTA_ADMIN.getClave()));
			correoDto.setContenido(MessageFormat.format(mensaje.getMensaje(), nombreEvento));
			correoElectronicoService.enviaCorreoElectronico(correoDto);

		} catch (Exception e) {
			logger.info("Ocurrio un error al intentar enviar el correo del cierre del acta");
		}
	}

	public void notificarUsuariosMatriculados(String claveNotificacion, String claveCorreo, List<PersonaDTO> personas,
			GrupoDTO grupo, EventoCapacitacionDTO evento) {
		for (PersonaDTO persona : personas) {
			notificarUsuarioMatriculado(claveNotificacion, claveCorreo, persona, grupo, evento);
		}
	}

	public void notificarUsuarioMatriculado(String claveNotificacion, String claveCorreo, PersonaDTO persona,
			GrupoDTO grupo, EventoCapacitacionDTO evento) {
		enviarNotificacionUsuarioMatriculado(claveNotificacion, persona, grupo, evento);
		enviarCorreoUsuarioMatriculado(claveCorreo, persona, grupo, evento);
	}

	private void enviarCorreoUsuarioMatriculado(String claveCorreo, PersonaDTO persona, GrupoDTO grupo,
			EventoCapacitacionDTO evento) {
		try {
			PersonaCorreoDTO correoPersona = personaCorreoService.obtenerCorreoInstitucional(persona.getIdPersona());
			List<String> correosUsuarios = new ArrayList<>();
			correosUsuarios.add(correoPersona.getCorreoElectronico());

			Integer idEvento = evento.getIdEvento();
			String nombreEvento = evento.getNombreEc();
			String modalidad = evento.getCatModalidadPlanPrograma().getNombre();
			String fechaInicio = DateUtils.convierteDateAString(evento.getFechaInicial(), DateUtils.FORMATO_FECHA);
			String fechaFin = DateUtils.convierteDateAString(evento.getFechaFinal(), DateUtils.FORMATO_FECHA);
			String nombreGrupo = grupo.getNombre();
			String nombreUsuario = persona.getUsuario();
			MensajeOperacionDTO mensaje = getMensajeOperacionService()
					.obtenerMensajeActivoPorClaveFuncionalidad(claveCorreo);
			CorreoDTO correoDto = getCorreoElectronicoService().asignaParametrosConfigCorreo();
			correoDto.setTitulo(MessageFormat.format(mensaje.getTitulo(), idEvento, nombreEvento, modalidad,
					fechaInicio, fechaFin, nombreGrupo, nombreUsuario));
			correoDto.setAsunto(MessageFormat.format(mensaje.getTitulo(), idEvento, nombreEvento, modalidad,
					fechaInicio, fechaFin, nombreGrupo, nombreUsuario));
			correoDto.setDestinatarios(correosUsuarios);
			correoDto.setRemitente(getParametroSistemaService()
					.obtenerParametro(ParametrosSistemaEnum.PS_CONFIG_CORREO_CUENTA_ADMIN.getClave()));
			correoDto.setContenido(MessageFormat.format(mensaje.getMensaje(), idEvento, nombreEvento, modalidad,
					fechaInicio, fechaFin, nombreGrupo, nombreUsuario));
			correoElectronicoService.enviaCorreoElectronico(correoDto);

		} catch (Exception e) {
			logger.info("Ocurrio un error al intentar enviar correos al usuario cuando se matriculo.");
		}
	}

	private void enviarNotificacionUsuarioMatriculado(String claveNotificacion, PersonaDTO persona, GrupoDTO grupo,
			EventoCapacitacionDTO evento) {
		try {
			List<VariableMensajeOperacionDTO> variables = new ArrayList<>();

			variables.add(new VariableMensajeOperacionDTO("{0}", String.valueOf(evento.getIdEvento())));

			variables.add(new VariableMensajeOperacionDTO("{1}", String.valueOf(evento.getNombreEc())));

			variables.add(new VariableMensajeOperacionDTO("{2}",
					String.valueOf(evento.getCatModalidadPlanPrograma().getNombre())));

			variables.add(new VariableMensajeOperacionDTO("{3}",
					DateUtils.convierteDateAString(evento.getFechaInicial(), DateUtils.FORMATO_FECHA)));

			variables.add(new VariableMensajeOperacionDTO("{4}",
					DateUtils.convierteDateAString(evento.getFechaFinal(), DateUtils.FORMATO_FECHA)));

			variables.add(new VariableMensajeOperacionDTO("{5}", grupo.getNombre()));

			variables.add(new VariableMensajeOperacionDTO("{6}", persona.getUsuario()));

			notificacionSistemaService.enviarNotificacion(claveNotificacion, persona.getIdPersona(), variables);

		} catch (Exception e) {
			logger.info("Ocurrio un error al intentar enviar la notificacion al usuario cuando se matriculo.");
		}
	}

	public void notificarColaboradorProduccion(String claveNotificacion, String claveCorreo, Long idColaborador,
			String nombreTema, EventoCapacitacionDTO evento) {

		enviarNotificacionColaboradorProduccion(claveNotificacion, idColaborador, nombreTema, evento);
		enviarCorreoColaboradorProduccion(claveCorreo, idColaborador, nombreTema, evento);

	}

	private void enviarNotificacionColaboradorProduccion(String claveNotificacion, Long idColaborador,
			String nombreTema, EventoCapacitacionDTO evento) {
		try {
			List<VariableMensajeOperacionDTO> variables = new ArrayList<>();

			variables.add(new VariableMensajeOperacionDTO("{0}", String.valueOf(evento.getIdEvento())));

			variables.add(new VariableMensajeOperacionDTO("{1}", String.valueOf(evento.getNombreEc())));

			variables.add(new VariableMensajeOperacionDTO("{2}",
					String.valueOf(evento.getCatModalidadPlanPrograma().getNombre())));

			variables.add(new VariableMensajeOperacionDTO("{3}",
					DateUtils.convierteDateAString(evento.getFechaInicial(), DateUtils.FORMATO_FECHA)));

			variables.add(new VariableMensajeOperacionDTO("{4}",
					DateUtils.convierteDateAString(evento.getFechaFinal(), DateUtils.FORMATO_FECHA)));

			variables.add(new VariableMensajeOperacionDTO("{5}", nombreTema));

			notificacionSistemaService.enviarNotificacion(claveNotificacion, idColaborador, variables);

		} catch (Exception e) {
			logger.info("Ocurrio un error al intentar enviar la notificacion al colaborador de produccion.");
		}
	}

	private void enviarCorreoColaboradorProduccion(String claveCorreo, Long idColaborador, String nombreTema,
			EventoCapacitacionDTO evento) {
		try {
			PersonaCorreoDTO correoPersona = personaCorreoService.obtenerCorreoInstitucional(idColaborador);
			List<String> correoColaborador = new ArrayList<>();
			correoColaborador.add(correoPersona.getCorreoElectronico());

			Integer idEvento = evento.getIdEvento();
			String nombreEvento = evento.getNombreEc();
			String modalidad = evento.getCatModalidadPlanPrograma().getNombre();
			String fechaInicio = DateUtils.convierteDateAString(evento.getFechaInicial(), DateUtils.FORMATO_FECHA);
			String fechaFin = DateUtils.convierteDateAString(evento.getFechaFinal(), DateUtils.FORMATO_FECHA);
			MensajeOperacionDTO mensaje = getMensajeOperacionService()
					.obtenerMensajeActivoPorClaveFuncionalidad(claveCorreo);
			CorreoDTO correoDto = getCorreoElectronicoService().asignaParametrosConfigCorreo();
			correoDto.setTitulo(MessageFormat.format(mensaje.getTitulo(), idEvento, nombreEvento, modalidad,
					fechaInicio, fechaFin, nombreTema));
			correoDto.setAsunto(MessageFormat.format(mensaje.getTitulo(), idEvento, nombreEvento, modalidad,
					fechaInicio, fechaFin, nombreTema));
			correoDto.setDestinatarios(correoColaborador);
			correoDto.setRemitente(getParametroSistemaService()
					.obtenerParametro(ParametrosSistemaEnum.PS_CONFIG_CORREO_CUENTA_ADMIN.getClave()));
			correoDto.setContenido(MessageFormat.format(mensaje.getMensaje(), idEvento, nombreEvento, modalidad,
					fechaInicio, fechaFin, nombreTema));
			correoElectronicoService.enviaCorreoElectronico(correoDto);

		} catch (Exception e) {
			logger.info("Ocurrio un error al intentar enviar correos a los responsables del evento");
		}

	}

	/**
	 * Envia una notificacion y un correo a los responsables del evento.
	 */
	public void notificarResponsablesEvento(String claveNotificacion, String claveCorreo,
			EventoCapacitacionDTO evento) {
		enviarNotificacionResponsablesDelEvento(claveNotificacion, evento);
		enviarCorreoResponsablesDelEvento(claveCorreo, evento);
	}

	private void enviarNotificacionResponsablesDelEvento(String claveNotificacion, EventoCapacitacionDTO evento) {
		try {
			List<Long> idsResponsables = obtenerIdsResponsablesDelEvento(evento.getIdEvento());
			if (!idsResponsables.isEmpty()) {

				List<VariableMensajeOperacionDTO> variables = new ArrayList<>();

				variables.add(new VariableMensajeOperacionDTO("{0}", String.valueOf(evento.getIdEvento())));

				variables.add(new VariableMensajeOperacionDTO("{1}", String.valueOf(evento.getNombreEc())));

				variables.add(new VariableMensajeOperacionDTO("{2}",
						String.valueOf(evento.getCatModalidadPlanPrograma().getNombre())));

				variables.add(new VariableMensajeOperacionDTO("{3}",
						DateUtils.convierteDateAString(evento.getFechaInicial(), DateUtils.FORMATO_FECHA)));

				variables.add(new VariableMensajeOperacionDTO("{4}",
						DateUtils.convierteDateAString(evento.getFechaFinal(), DateUtils.FORMATO_FECHA)));

				for (Long idResponsable : idsResponsables) {
					notificacionSistemaService.enviarNotificacion(claveNotificacion, idResponsable, variables);
				}
			}

		} catch (Exception e) {
			logger.info("Ocurrio un error al intentar enviar la notificacion a los responsables del evento.");
		}
	}

	private void enviarCorreoResponsablesDelEvento(String claveCorreo, EventoCapacitacionDTO evento) {
		try {
			List<String> correosResponsables = obtenerCorreosResponsablesDelEvento(evento.getIdEvento());
			if (!correosResponsables.isEmpty()) {
				Integer idEvento = evento.getIdEvento();
				String nombreEvento = evento.getNombreEc();
				String modalidad = evento.getCatModalidadPlanPrograma().getNombre();
				String fechaInicio = DateUtils.convierteDateAString(evento.getFechaInicial(), DateUtils.FORMATO_FECHA);
				String fechaFin = DateUtils.convierteDateAString(evento.getFechaFinal(), DateUtils.FORMATO_FECHA);
				MensajeOperacionDTO mensaje = getMensajeOperacionService()
						.obtenerMensajeActivoPorClaveFuncionalidad(claveCorreo);
				CorreoDTO correoDto = getCorreoElectronicoService().asignaParametrosConfigCorreo();
				correoDto.setTitulo(MessageFormat.format(mensaje.getTitulo(), idEvento, nombreEvento, modalidad,
						fechaInicio, fechaFin));
				correoDto.setAsunto(MessageFormat.format(mensaje.getTitulo(), idEvento, nombreEvento, modalidad,
						fechaInicio, fechaFin));
				correoDto.setDestinatarios(correosResponsables);
				correoDto.setRemitente(getParametroSistemaService()
						.obtenerParametro(ParametrosSistemaEnum.PS_CONFIG_CORREO_CUENTA_ADMIN.getClave()));
				correoDto.setContenido(MessageFormat.format(mensaje.getMensaje(), idEvento, nombreEvento, modalidad,
						fechaInicio, fechaFin));
				correoElectronicoService.enviaCorreoElectronico(correoDto);
			}
		} catch (Exception e) {
			logger.info("Ocurrio un error al intentar enviar correos a los responsables del evento");
		}

	}

	private List<String> obtenerCorreosResponsablesDelEvento(Integer idEvento) {
		List<PersonaResponsabilidadesDTO> responsables = obtenerResponsablesDelEvento(idEvento);
		List<String> correosResponsables = new ArrayList<>();
		if (!responsables.isEmpty()) {

			for (PersonaResponsabilidadesDTO responsable : responsables) {
				if (ObjectUtils.isNotNull(responsable.getTblPersona())) {
					PersonaCorreoDTO correoPersona = personaCorreoService
							.obtenerCorreoInstitucional(responsable.getTblPersona().getIdPersona());
					if (ObjectUtils.isNotNull(correoPersona)) {
						correosResponsables.add(correoPersona.getCorreoElectronico());
					}
				}
			}
		}
		return correosResponsables;
	}

	private List<PersonaResponsabilidadesDTO> obtenerResponsablesDelEvento(Integer idEvento) {

		List<ReponsableProduccionEcDTO> listaRelacionResponsableEvento = relReponsableProduccionEcService
				.getResponsableDelEvento(idEvento);

		List<PersonaResponsabilidadesDTO> responsables = new ArrayList<>();
		if (!listaRelacionResponsableEvento.isEmpty()) {
			for (ReponsableProduccionEcDTO reponsableProduccionEcDTO : listaRelacionResponsableEvento) {
				responsables.add(reponsableProduccionEcDTO.getPersonaResponsabilidad());
			}
		}

		return responsables;
	}

	private List<Long> obtenerIdsResponsablesDelEvento(Integer idEvento) {

		List<PersonaResponsabilidadesDTO> responsables = obtenerResponsablesDelEvento(idEvento);
		List<Long> idsResponsables = new ArrayList<>();
		if (!responsables.isEmpty()) {
			for (PersonaResponsabilidadesDTO responsable : responsables) {
				if (ObjectUtils.isNotNull(responsable.getTblPersona())) {
					if (ObjectUtils.isNotNull(responsable.getTblPersona().getIdPersona())) {
						idsResponsables.add(responsable.getTblPersona().getIdPersona());
					}
				}
			}
		}
		return idsResponsables;
	}

	/* Getters y Setters */

	public CorreoElectronicoService getCorreoElectronicoService() {
		return correoElectronicoService;
	}

	public void setCorreoElectronicoService(CorreoElectronicoService correoElectronicoService) {
		this.correoElectronicoService = correoElectronicoService;
	}

	public ParametroSistemaService getParametroSistemaService() {
		return parametroSistemaService;
	}

	public void setParametroSistemaService(ParametroSistemaService parametroSistemaService) {
		this.parametroSistemaService = parametroSistemaService;
	}

	public NotificacionSistemaService getNotificacionSistemaService() {
		return notificacionSistemaService;
	}

	public void setNotificacionSistemaService(NotificacionSistemaService notificacionSistemaService) {
		this.notificacionSistemaService = notificacionSistemaService;
	}

	public PersonaCorreoService getPersonaCorreoService() {
		return personaCorreoService;
	}

	public void setPersonaCorreoService(PersonaCorreoService personaCorreoService) {
		this.personaCorreoService = personaCorreoService;
	}

	public MensajeOperacionService getMensajeOperacionService() {
		return mensajeOperacionService;
	}

	public void setMensajeOperacionService(MensajeOperacionService mensajeOperacionService) {
		this.mensajeOperacionService = mensajeOperacionService;
	}

	public RelReponsableProduccionEcService getRelReponsableProduccionEcService() {
		return relReponsableProduccionEcService;
	}

	public void setRelReponsableProduccionEcService(RelReponsableProduccionEcService relReponsableProduccionEcService) {
		this.relReponsableProduccionEcService = relReponsableProduccionEcService;
	}

	public GrupoParticipanteService getGrupoParticipanteService() {
		return grupoParticipanteService;
	}

	public void setGrupoParticipanteService(GrupoParticipanteService grupoParticipanteService) {
		this.grupoParticipanteService = grupoParticipanteService;
	}

}
