package mx.gob.sedesol.gestorweb.beans.administracion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import mx.gob.sedesol.basegestor.commons.constantes.ConstantesBitacora;

import org.modelmapper.ModelMapper;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import mx.gob.sedesol.basegestor.commons.constantes.ConstantesGestor;
import mx.gob.sedesol.basegestor.commons.dto.admin.ArchivoAdjuntoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.DestinatarioDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MensajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MensajeResumenDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.NotificacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.NotificacionSistemaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;
import mx.gob.sedesol.basegestor.commons.utils.TipoUsuarioEnum;
import mx.gob.sedesol.basegestor.mongo.service.NotificacionService;
import mx.gob.sedesol.basegestor.mongo.service.NotificacionSistemaService;
import mx.gob.sedesol.basegestor.service.ParametroSistemaService;
import mx.gob.sedesol.basegestor.service.admin.PersonaService;
import mx.gob.sedesol.gestorweb.beans.acceso.BaseBean;
import mx.gob.sedesol.gestorweb.commons.constantes.ConstantesGestorWeb;
import mx.gob.sedesol.gestorweb.commons.utils.BitacoraUtil;
import mx.gob.sedesol.gestorweb.commons.utils.GestorArchivos;
import mx.gob.sedesol.gestorweb.sistema.SistemaBean;

@SessionScoped
@ManagedBean(name = "notificacionesBean")
public class NotificacionesBean extends BaseBean {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{notificacionService}")
	private transient NotificacionService notificacionService;

	@ManagedProperty(value = "#{notificacionSistemaService}")
	private transient NotificacionSistemaService notificacionSistemaService;

	@ManagedProperty(value = "#{personaService}")
	private transient PersonaService personaService;

	@ManagedProperty(value = "#{sistema}")
	private SistemaBean sistema;

	@ManagedProperty(value = "#{parametroSistemaService}")
	private transient ParametroSistemaService parametroSistemaService;

	private transient ModelMapper modelMapper = new ModelMapper();

	private List<NotificacionDTO> recibidos;
	private List<NotificacionDTO> enviados;
	private List<NotificacionDTO> borradores;
	private List<NotificacionDTO> eliminados;
	private NotificacionDTO notificacion;
	private NotificacionDTO notificacionModificar;
	private MensajeDTO mensaje;

	private Map<Long, PersonaDTO> mapaPersonas;

	private List<PersonaDTO> personas;

	private PersonaDTO filtroPersona = new PersonaDTO();

	private DestinatarioDTO destinatarioDTO;

	private transient UploadedFile archivoCargar;

	private boolean enviado;

	private MensajeResumenDTO mensajeSeleccionado;

	private List<NotificacionSistemaDTO> notificacionesSistema;
	private NotificacionSistemaDTO notificacionSistema;

	@PostConstruct
	public void init() {
		mapaPersonas = new HashMap<>();
		for (PersonaDTO persona : personaService.findAll()) {
			mapaPersonas.put(persona.getIdPersona(), persona);
		}
		enviados = notificacionService.obtenerNotificacionesPorRemitente(getUsuarioEnSession().getIdPersona(), true,
				mapaPersonas);
		borradores = notificacionService.obtenerNotificacionesPorRemitente(getUsuarioEnSession().getIdPersona(), false,
				mapaPersonas);
		recibidos = notificacionService.obtenerNotificacionesPorDestinatario(getUsuarioEnSession().getIdPersona(), true,
				mapaPersonas);
		eliminados = notificacionService.obtenerNotificacionesPorDestinatario(getUsuarioEnSession().getIdPersona(),
				false, mapaPersonas);

	}

	public String cargarNotificacionesSistema() {
		notificacionesSistema = notificacionSistemaService.obtenerNotificaciones(getUsuarioEnSession().getIdPersona(),
				null, null);
		return ConstantesGestorWeb.NAVEGA_NOTIFICACIONES_SISTEMA;
	}

	public String nuevaNotificacion() {
		archivoCargar = null;
		notificacion = new NotificacionDTO(getUsuarioEnSession().getIdPersona());

		mensaje = new MensajeDTO(getUsuarioEnSession().getIdPersona());
		notificacion.getMensajes().add(mensaje);

		return ConstantesGestorWeb.NAVEGA_NOTIFICACION_EDICION;
	}

	public String iniciarListaPersonas() {
		personas = new ArrayList<>();
		return ConstantesGestorWeb.NAVEGA_NOTIFICACION_PERSONAS;
	}

	public TipoUsuarioEnum[] getTiposUsuarios() {
		return TipoUsuarioEnum.values();
	}

	public void fileUploadListener(FileUploadEvent e) {
		this.archivoCargar = e.getFile();
		almacenarArchivoAdjunto();
	}

	public void buscarPersonas() {
		List<PersonaDTO> personasDescartadas = new ArrayList<>();
		personas = personaService.busquedaPorCriterios(filtroPersona);
		for (PersonaDTO elementoPersona : personas) {
			identificarPersonasDescartadas(personasDescartadas, elementoPersona);
		}
		personas.removeAll(personasDescartadas);
	}

	private void identificarPersonasDescartadas(List<PersonaDTO> personasDescartadas, PersonaDTO elementoPersona) {
		if (elementoPersona.getIdPersona() == getUsuarioEnSession().getIdPersona()) {
			personasDescartadas.add(elementoPersona);
		} else {
			for (DestinatarioDTO elementoDestinatario : notificacion.getDestinatarios()) {
				if (elementoDestinatario.getIdPersona() == elementoPersona.getIdPersona()) {
					personasDescartadas.add(elementoPersona);
					break;
				}
			}
		}
	}

	public String agregarDestinatarios() {
		if (!personas.isEmpty()) {
			for (PersonaDTO persona : personas) {
				if (persona.isSeleccionado()) {
					notificacion.getDestinatarios().add(new DestinatarioDTO(persona));
				}
			}
			return ConstantesGestorWeb.NAVEGA_NOTIFICACION_EDICION;
		}else{
			agregarFlashMessage("No se seleccionaron destinatarios.", null,
					FacesMessage.SEVERITY_INFO);
			return null;
		}
		
	}

	public void eliminarDestinatario() {
		notificacion.getDestinatarios().remove(destinatarioDTO);
	}

	public String cargarNotificacion() {
		archivoCargar = null;
		notificacion = new NotificacionDTO();
		modelMapper.map(notificacionModificar, notificacion);
		mensaje = notificacion.getMensajes().get(0);
		notificacion.setUsuarioModifico(getUsuarioEnSession().getIdPersona());

		return ConstantesGestorWeb.NAVEGA_NOTIFICACION_EDICION;
	}

	public String guardarBorrador() {
		ResultadoDTO<NotificacionDTO> resultado = notificacionService.guardarBorrador(notificacion);
		if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
			init();
			agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
		} else {
			agregarMsgError(resultado.getMensajes(), null, sistema);
			return ConstantesGestorWeb.NAVEGA_NOTIFICACION_EDICION;
		}
		return ConstantesGestorWeb.NAVEGA_NOTIFICACIONES;
	}

	public String enviarNotificacion() {
		ResultadoDTO<NotificacionDTO> resultado = notificacionService.guardar(notificacion);

		if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
			init();
			agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
			return ConstantesGestorWeb.NAVEGA_NOTIFICACIONES;
		} else {
			agregarMsgError(resultado.getMensajes(), null, sistema);
			return ConstantesGestorWeb.NAVEGA_NOTIFICACION_EDICION;
		}

	}

	public String detalleNotificacion() {
		notificacion = new NotificacionDTO();
		modelMapper.map(notificacionModificar, notificacion);
		for (DestinatarioDTO dto : notificacion.getDestinatarios()) {
			if (dto.getIdPersona() == getUsuarioEnSession().getIdPersona() && dto.isNuevo()) {
				notificacionService.actualizarEstatusNuevo(notificacion.getId(), getUsuarioEnSession().getIdPersona());
				break;
			}
		}
		enviado = false;
		mensaje = new MensajeDTO();
		return ConstantesGestorWeb.NAVEGA_NOTIFICACION_DETALLE;
	}

	public String detalleMensajeSeleccionado() {
		for (NotificacionDTO notificacionRecibida : recibidos) {
			if (notificacionRecibida.getId().equals(mensajeSeleccionado.getId())) {
				notificacionModificar = notificacionRecibida;
				return detalleNotificacion();
			}
		}
		return null;
	}

	public String cargarNuevoMensaje() {
		for (DestinatarioDTO dto : notificacion.getDestinatarios()) {
			if (dto.getIdPersona() == getUsuarioEnSession().getIdPersona()) {
				dto.setNuevo(false);
			} else {
				dto.setNuevo(true);
			}
			dto.setActivo(true);
		}
		archivoCargar = null;
		mensaje = new MensajeDTO(mapaPersonas.get(getUsuarioEnSession().getIdPersona()));
		return ConstantesGestorWeb.NAVEGA_NOTIFICACION_RESPONDER;
	}

	public String enviarMensaje() {
		// GUSTAVO --BitacoraUtil.llenarBitacora(notificacion,
		// getUsuarioEnSession(), ConstantesBitacora.NOTIFICACION_AGREGAR,
		// request);
		ResultadoDTO<NotificacionDTO> resultado = notificacionService.agregarMensaje(notificacion, mensaje);
		if (resultado.getResultado() == ResultadoTransaccionEnum.EXITOSO) {
			agregarMsgInfo(resultado.getMensajes().get(0), null, sistema);
			notificacion = notificacionService.obtenerNotificacion(notificacion.getId(), mapaPersonas,
					getUsuarioEnSession().getIdPersona());
			actualizarMensajes();
		} else {
			agregarMsgError(resultado.getMensajes(), null, sistema);
			return ConstantesGestorWeb.NAVEGA_NOTIFICACION_RESPONDER;
		}
		return ConstantesGestorWeb.NAVEGA_NOTIFICACION_DETALLE;
	}

	private void almacenarArchivoAdjunto() {
		if (ObjectUtils.isNotNull(archivoCargar) && !ObjectUtils.isNullOrEmpty(archivoCargar.getFileName())) {
			ArchivoAdjuntoDTO archivoAdjunto = new ArchivoAdjuntoDTO(archivoCargar.getFileName(),
					archivoCargar.getContentType());

			StringBuilder rutaAlmacenamiento = new StringBuilder(
					parametroSistemaService.obtenerParametroConRutaCompleta(ConstantesGestor.PARAMETRO_RUTA_ADJUNTOS));
			rutaAlmacenamiento.append(notificacion.getRutaArchivos());
			rutaAlmacenamiento.append("/");

			String rutaArchivo = rutaAlmacenamiento.toString() + archivoAdjunto.getRuta();

			if (GestorArchivos.crearCarpeta(rutaAlmacenamiento.toString()).getResultado().getValor() && GestorArchivos
					.almacenarArchivo(rutaArchivo, archivoCargar.getContents()).getResultado().getValor()) {
				mensaje.setArchivo(archivoAdjunto);
			}
		}
	}

	private void actualizarMensajes() {
		actualizarLista(recibidos, notificacion);
		actualizarLista(eliminados, notificacion);
		actualizarLista(enviados, notificacion);
	}

	private void actualizarLista(List<NotificacionDTO> notificaciones, NotificacionDTO dto) {
		for (NotificacionDTO notificacionDTO : notificaciones) {
			if (notificacionDTO.getId().equals(dto.getId())) {
				notificaciones.remove(notificacionDTO);
				notificaciones.add(0, dto);
				break;
			}
		}
	}

	public String detalleEnviado() {
		enviado = true;
		mensaje = notificacion.getMensajes().get(ConstantesGestor.PRIMER_ELEMENTO);
		return ConstantesGestorWeb.NAVEGA_NOTIFICACION_DETALLE;
	}

	public void eliminarNotificacion() {
		// GUSTAVO --BitacoraUtil.llenarBitacora(notificacion,
		// getUsuarioEnSession(), ConstantesBitacora.NOTIFICACION_EDITAR,
		// request);
		notificacionService.actualizarActivo(notificacion.getId(), getUsuarioEnSession().getIdPersona());
		init();
	}

	public StreamedContent getArchivoDescargar() throws IOException {
		StringBuilder rutaArchivo = new StringBuilder(
				parametroSistemaService.obtenerParametroConRutaCompleta(ConstantesGestor.PARAMETRO_RUTA_ADJUNTOS));
		rutaArchivo.append(notificacion.getRutaArchivos());
		rutaArchivo.append("/");
		rutaArchivo.append(mensaje.getArchivo().getRuta());

		File archivo = new File(rutaArchivo.toString());
		if (archivo.canRead()) {
			return new DefaultStreamedContent(new FileInputStream(archivo), mensaje.getArchivo().getTipo(),
					mensaje.getArchivo().getNombre());
		} else {
			return null;
		}
	}

	public int getNumeroMensajes() {
		return notificacionService.obtenerNumeroMensajesNuevos(getUsuarioEnSession().getIdPersona())
				.getNumeroMensajes();
	}

	public long getNumeroNotificaciones() {
		return notificacionSistemaService.obtenerNumeroNotificacionesNuevas(getUsuarioEnSession().getIdPersona(), null,
				true);
	}

	public List<MensajeResumenDTO> getPrimerosMensajes() {
		return notificacionService.obtenerPrimerosMensajes(getUsuarioEnSession().getIdPersona());
	}

	public List<NotificacionSistemaDTO> getPrimerasNotificaciones() {
		return notificacionSistemaService.obtenerPrimerasNotificaciones(getUsuarioEnSession().getIdPersona(), null,
				null);
	}

	public void actualizarEstatusNotificacion() {
		notificacionSistemaService.actualizarFechaVisualizacion(getUsuarioEnSession().getIdPersona(),
				notificacionSistema.getIdNotificacion());
		notificacionesSistema = notificacionSistemaService.obtenerNotificaciones(getUsuarioEnSession().getIdPersona(),
				null, null);
	}

	/* INICIO DE GETS Y SETS */
	public NotificacionService getNotificacionService() {
		return notificacionService;
	}

	public void setNotificacionService(NotificacionService notificacionService) {
		this.notificacionService = notificacionService;
	}

	public List<NotificacionDTO> getRecibidos() {
		return recibidos;
	}

	public void setRecibidos(List<NotificacionDTO> recibidos) {
		this.recibidos = recibidos;
	}

	public List<NotificacionDTO> getEnviados() {
		return enviados;
	}

	public void setEnviados(List<NotificacionDTO> enviados) {
		this.enviados = enviados;
	}

	public List<NotificacionDTO> getBorradores() {
		return borradores;
	}

	public void setBorradores(List<NotificacionDTO> borradores) {
		this.borradores = borradores;
	}

	public List<NotificacionDTO> getEliminados() {
		return eliminados;
	}

	public void setEliminados(List<NotificacionDTO> eliminados) {
		this.eliminados = eliminados;
	}

	public NotificacionDTO getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(NotificacionDTO notificacion) {
		this.notificacion = notificacion;
	}

	public NotificacionDTO getNotificacionModificar() {
		return notificacionModificar;
	}

	public void setNotificacionModificar(NotificacionDTO notificacionModificar) {
		this.notificacionModificar = notificacionModificar;
	}

	public PersonaService getPersonaService() {
		return personaService;
	}

	public void setPersonaService(PersonaService personaService) {
		this.personaService = personaService;
	}

	public List<PersonaDTO> getPersonas() {
		return personas;
	}

	public void setPersonas(List<PersonaDTO> personas) {
		this.personas = personas;
	}

	public PersonaDTO getFiltroPersona() {
		return filtroPersona;
	}

	public void setFiltroPersona(PersonaDTO filtroPersona) {
		this.filtroPersona = filtroPersona;
	}

	public DestinatarioDTO getDestinatarioDTO() {
		return destinatarioDTO;
	}

	public void setDestinatarioDTO(DestinatarioDTO destinatarioDTO) {
		this.destinatarioDTO = destinatarioDTO;
	}

	public boolean isEnviado() {
		return enviado;
	}

	public void setEnviado(boolean enviado) {
		this.enviado = enviado;
	}

	public MensajeDTO getMensaje() {
		return mensaje;
	}

	public void setMensaje(MensajeDTO mensaje) {
		this.mensaje = mensaje;
	}

	public UploadedFile getArchivoCargar() {
		return archivoCargar;
	}

	public void setArchivoCargar(UploadedFile archivoCargar) {
		this.archivoCargar = archivoCargar;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}

	public MensajeResumenDTO getMensajeSeleccionado() {
		return mensajeSeleccionado;
	}

	public void setMensajeSeleccionado(MensajeResumenDTO mensajeSeleccionado) {
		this.mensajeSeleccionado = mensajeSeleccionado;
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

	public List<NotificacionSistemaDTO> getNotificacionesSistema() {
		return notificacionesSistema;
	}

	public void setNotificacionesSistema(List<NotificacionSistemaDTO> notificacionesSistema) {
		this.notificacionesSistema = notificacionesSistema;
	}

	public NotificacionSistemaDTO getNotificacionSistema() {
		return notificacionSistema;
	}

	public void setNotificacionSistema(NotificacionSistemaDTO notificacionSistema) {
		this.notificacionSistema = notificacionSistema;
	}

}
