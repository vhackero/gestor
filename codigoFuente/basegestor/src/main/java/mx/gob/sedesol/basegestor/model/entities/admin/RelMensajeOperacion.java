package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "rel_mensajes_operacion")
public class RelMensajeOperacion implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private long idMensajeOperacion;
	private TblFuncionalidad funcionalidad;
	private String titulo;
	private String mensaje;
	private Date fechaRegistro;
	private Date fechaActualizacion;
	private long usuarioModifico;
	private boolean activo;
	private int tipo;

	@Id
	@Column(name = "id_mensaje_operacion", unique = true, nullable = false)
	public long getIdMensajeOperacion() {
		return this.idMensajeOperacion;
	}

	public void setIdMensajeOperacion(long idMensajeOperacion) {
		this.idMensajeOperacion = idMensajeOperacion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_funcionalidad", nullable = false)
	@NotNull
	public TblFuncionalidad getFuncionalidad() {
		return funcionalidad;
	}
	public void setFuncionalidad(TblFuncionalidad funcionalidad) {
		this.funcionalidad = funcionalidad;
	}

	@Column(name = "titulo", nullable = false, length = 100)
	@NotNull
	@Length(max = 100)
	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Column(name = "mensaje", nullable = false, length = 4000)
	@NotNull
	@Length(max = 4000)
	public String getMensaje() {
		return this.mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_registro", nullable = false, length = 19)
	@NotNull
	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", length = 19)
	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	@Column(name = "usuario_modifico", nullable = false)
	public long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	@Column(name = "activo", nullable = false)
	public boolean isActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@Column(name = "tipo")
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

}
