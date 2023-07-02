package mx.gob.sedesol.basegestor.mongo.service;

import java.util.List;
import java.util.Map;

import mx.gob.sedesol.basegestor.commons.dto.admin.InformacionMensajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MensajeDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.MensajeResumenDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.NotificacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.PersonaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;

/**
 * Servicio para el manejo de las notificaciones
 * 
 * @author Carlos Lopez
 *
 */
public interface NotificacionService {
	
	/**
	 * Valida una notificacion, si es correcta la almacena
	 * 
	 * @param dto - notificacion
	 * @return
	 */
	ResultadoDTO<NotificacionDTO> guardar(NotificacionDTO dto);
	
	/**
	 * Almacena una notificacion sin realizar valildaciones
	 * @param dto
	 * @return
	 */
	ResultadoDTO<NotificacionDTO> guardarBorrador(NotificacionDTO dto);
	
	/**
	 * Cambia el estatus nuevo a falso
	 * con los siguientes filtros
	 * @param idNotificacion
	 * @param idPersona
	 * @return
	 */
	ResultadoDTO<NotificacionDTO> actualizarEstatusNuevo(String idNotificacion, long idPersona);
	
	/**
	 * cambia el estatus activo a falso
	 * con los siguientes filtros
	 * @param idNotificacion
	 * @param idPersona
	 * @return
	 */
	ResultadoDTO<NotificacionDTO> actualizarActivo(String idNotificacion, long idPersona);
	
	/**
	 * agregar una respuesta a la notificacion 
	 * y actualiza las banderas nuevo y activo a verdadero de los destinatarios
	 * @param notificacion
	 * @param dto
	 * @return
	 */
	ResultadoDTO<NotificacionDTO> agregarMensaje(NotificacionDTO notificacion, MensajeDTO dto);
	
	/**
	 * Obtiene una notificacion 
	 * @param id
	 * @param mapa - datos de las personas para llenar la notificacion
	 * @return
	 */
	NotificacionDTO obtenerNotificacion(String id, Map<Long, PersonaDTO> mapa, long idPersona);
	
	/**
	 * Obtiene las notificaciones enviadas por una persona
	 * @param idPersona
	 * @param enviado - ha sido enviada o es un borrador
	 * @param mapa - datos de las personas para llenar la notificacion
	 * @return
	 */
	List<NotificacionDTO> obtenerNotificacionesPorRemitente(Long idPersona, boolean enviado, Map<Long, PersonaDTO> mapa);
	
	/**
	 * obtiene lasnotificaciones recibidas por una persona
	 * @param idPersona
	 * @param activo - sis esta en la bandeja o ha sido borrada
	 * @param mapa - datos de las personas para llenar la notificacion
	 * @return
	 */
	List<NotificacionDTO> obtenerNotificacionesPorDestinatario(Long idPersona, boolean activo, Map<Long, PersonaDTO> mapa);
	
	/**
	 * obtiene el numero de mensajes nuevos que ha recibido una persona
	 * @param idPersona
	 * @return
	 */
	InformacionMensajeDTO obtenerNumeroMensajesNuevos(long idPersona);
	
	/**
	 * obtiene los tres primeros mensajes de una persona
	 * @param idPersona
	 * @return
	 */
	List<MensajeResumenDTO> obtenerPrimerosMensajes(Long idPersona);

}
