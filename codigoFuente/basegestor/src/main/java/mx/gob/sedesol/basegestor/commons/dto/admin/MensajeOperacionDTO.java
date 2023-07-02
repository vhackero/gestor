package mx.gob.sedesol.basegestor.commons.dto.admin;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import mx.gob.sedesol.basegestor.commons.dto.ComunDTO;
import mx.gob.sedesol.basegestor.commons.utils.MensajesSistemaEnum;
import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;
import mx.gob.sedesol.basegestor.commons.utils.TipoNotificacionEnum;

public class MensajeOperacionDTO extends ComunDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long idMensajeOperacion;
	private FuncionalidadDTO funcionalidad;
	
	@Length(max=100)
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String titulo;
	
	@Length(max=4000)
	@NotEmpty(message=MensajesSistemaEnum.Constantes.GESTOR_WEB_GENERAL_DATO_REQUERIDO)
	private String mensaje;
	
	private boolean activo;
	private int tipo;
	private TipoNotificacionEnum tipoEnum;
	
	public MensajeOperacionDTO() {
		setFechaRegistro(new Date());
		this.activo = false;
	}
	
	public MensajeOperacionDTO(Long usuarioModifico, FuncionalidadDTO funcionalidad) {
		setUsuarioModifico(usuarioModifico);
		setFechaRegistro(new Date());
		this.activo = false;
		this.funcionalidad = funcionalidad;
	}
	
	public long getIdMensajeOperacion() {
		return idMensajeOperacion;
	}
	public void setIdMensajeOperacion(long idMensajeOperacion) {
		this.idMensajeOperacion = idMensajeOperacion;
	}
	public FuncionalidadDTO getFuncionalidad() {
		return funcionalidad;
	}
	public void setFuncionalidad(FuncionalidadDTO funcionalidad) {
		this.funcionalidad = funcionalidad;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		if (ObjectUtils.isNull(titulo)) {
			this.titulo = null;
		} else {
			this.titulo = titulo.trim();
		}
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		if (ObjectUtils.isNull(mensaje)) {
			this.mensaje = null;
		} else {
			this.mensaje = mensaje.trim();
		}
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
		this.tipoEnum = TipoNotificacionEnum.geTipoNotificacionEnum(tipo);
	}

	public TipoNotificacionEnum getTipoEnum() {
		return tipoEnum;
	}

	public void setTipoEnum(TipoNotificacionEnum tipoEnum) {
		this.tipoEnum = tipoEnum;
	}
}
