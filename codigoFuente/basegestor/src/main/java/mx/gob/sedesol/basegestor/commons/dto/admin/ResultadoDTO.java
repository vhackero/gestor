/**
 * 
 */
package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mx.gob.sedesol.basegestor.commons.utils.MensajesErrorEnum;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.ResultadoTransaccionEnum;

/**
 * @author PlanetMedia
 *
 */
public class ResultadoDTO<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private ResultadoTransaccionEnum resultado;
	private String mensaje;
	private List<String> mensajes;
	private T dto;
	private MensajesErrorEnum mensajeError;

	public ResultadoDTO() {
		resultado = ResultadoTransaccionEnum.EXITOSO;
	}

	public boolean esCorrecto() {
		boolean correcto;
		if (ObjectUtils.isNull(resultado)) {
			correcto = false;
		} else {
			correcto = resultado.getValor();
		}
		return correcto;
	}

	/**
	 * @return resultado
	 */
	public ResultadoTransaccionEnum getResultado() {
		return resultado;
	}

	/**
	 * @param resultado
	 *            set resultado
	 */
	public void setResultado(ResultadoTransaccionEnum resultado) {
		this.resultado = resultado;
	}

	/**
	 * @return mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje
	 *            set mensaje
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return mensajes
	 */
	public List<String> getMensajes() {
		return mensajes;
	}

	/**
	 * @param mensajes
	 *            set mensajes
	 */
	public void setMensajes(List<String> mensajes) {
		this.mensajes = mensajes;
	}

	/**
	 * 
	 * @param mensaje
	 */
	public void agregaMensaje(String mensaje) {
		if (this.mensajes == null)
			this.mensajes = new ArrayList<>();

		if (!ObjectUtils.isNullOrEmpty(mensaje))
			mensajes.add(mensaje);
	}

	public T getDto() {
		return dto;
	}

	public void setDto(T dto) {
		this.dto = dto;
	}

	public MensajesErrorEnum getMensajeError() {
		return mensajeError;
	}

	/**
	 * Setea el mensaje de error @deprecated Utilizar el metodo que utiliza el
	 * Enum de textos del Sistema
	 * 
	 * @param mensajeError
	 */
	// @Deprecated
	public void setMensajeError(MensajesErrorEnum mensajeError) {
		if (mensajeError != null) {
			this.resultado = ResultadoTransaccionEnum.FALLIDO;
			this.mensajeError = mensajeError;
			agregaMensaje(this.mensajeError.getMensaje());
		}
	}

	public void setMensajeError(MensajesSistemaEnum mensajeSistema) {
		if (mensajeSistema != null) {
			this.resultado = ResultadoTransaccionEnum.FALLIDO;
			agregaMensaje(mensajeSistema.getId());
		}
	}

	/**
	 * Setea el mensaje de error, agregando un mensaje adicional
	 * 
	 * @param mensajeError
	 * @param mensajeAdicional
	 */
	public void setMensajeError(MensajesErrorEnum mensajeError, String mensajeAdicional) {
		if (mensajeError != null) {
			this.resultado = ResultadoTransaccionEnum.FALLIDO;
			this.mensajeError = mensajeError;
			agregaMensaje(this.mensajeError.getMensaje().concat(" ").concat(mensajeAdicional));
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResultadoDTO [resultado=");
		builder.append(resultado);
		builder.append(", mensaje=");
		builder.append(mensaje);
		builder.append(", mensajes=");
		builder.append(mensajes);
		builder.append(", dto=");
		builder.append(dto);
		builder.append("]");
		return builder.toString();
	}

}
