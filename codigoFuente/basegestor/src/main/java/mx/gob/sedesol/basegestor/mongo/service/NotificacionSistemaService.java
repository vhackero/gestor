package mx.gob.sedesol.basegestor.mongo.service;

import java.util.List;

import mx.gob.sedesol.basegestor.commons.dto.admin.NotificacionSistemaDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.ResultadoDTO;
import mx.gob.sedesol.basegestor.commons.dto.admin.VariableMensajeOperacionDTO;
import mx.gob.sedesol.basegestor.commons.utils.TipoNotificacionEnum;

/**
 * Servicio para el manejo de las notificaciones
 * del sistema
 * @author Carlos Lopez
 *
 */
public interface NotificacionSistemaService {
	
	/**
	 * toma la plantilla activa de esa operacion y reemplaza las variables,
	 * despues envia una notificacion al usuario
	 * @param claveOperacion
	 * @param idUsuarioDestino
	 * @param variables
	 * @return
	 */
	ResultadoDTO<NotificacionSistemaDTO> enviarNotificacion(String claveOperacion, long idUsuarioDestino, List<VariableMensajeOperacionDTO> variables);
	
	/**
	 * 
	 * @param claveOperacion
	 * @param idUsuarioDestino
	 * @param variables
	 * @return
	 */
	ResultadoDTO<NotificacionSistemaDTO> enviarNotificacionARolesEspecificos(String claveOperacion, List<Integer> roles, List<VariableMensajeOperacionDTO> variables);
	
	/**
	 * obtiene las notificaciones del sistema de un usuario
	 * @param idUsuario
	 * @param tipo
	 * @param activo
	 * @return
	 */
	List<NotificacionSistemaDTO> obtenerNotificaciones(long idUsuario, TipoNotificacionEnum tipo, Boolean activo);
	
	/**
	 * obtiene las tres primeras notificaciones del sistema de un usuario
	 * @param idUsuario
	 * @param tipo
	 * @param activo
	 * @return
	 */
	List<NotificacionSistemaDTO> obtenerPrimerasNotificaciones(long idUsuario, TipoNotificacionEnum tipo,
			Boolean activo);
	
	/**
	 * modifica la fecha de vizualizacion de una notificacion
	 * @param idUsuario
	 * @param idMensaje
	 * @return
	 */
	ResultadoDTO<NotificacionSistemaDTO> actualizarFechaVisualizacion(long idUsuario, String idMensaje);
	
	/**
	 * modificac el estatus de una notificacion
	 * @param idUsuario
	 * @param idMensaje
	 * @return
	 */
	ResultadoDTO<NotificacionSistemaDTO> actualizarEstatus(long idUsuario, String idMensaje);
	
	/**
	 * devuelve el numero de notificaciones nuevas de un usuario
	 * @param idUsuario
	 * @param tipo
	 * @param activo
	 * @return
	 */
	long obtenerNumeroNotificacionesNuevas(long idUsuario, TipoNotificacionEnum tipo, Boolean activo);

}
