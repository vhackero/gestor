package mx.gob.sedesol.basegestor.model.entities.admin;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "rel_variables_mensaje_operacion")
public class RelVariableMensajeOperacion implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idVariableMensajeOperacion;
	private TblFuncionalidad funcionalidad;
	private String descripcion;
	private String clave;
	private Date fechaRegistro;
	private Date fechaActualizacion;
	private long usuarioModifico;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_variable_mensaje_operacion", unique = true, nullable = false)
	public Integer getIdVariableMensajeOperacion() {
		return this.idVariableMensajeOperacion;
	}

	public void setIdVariableMensajeOperacion(Integer idVariableMensajeOperacion) {
		this.idVariableMensajeOperacion = idVariableMensajeOperacion;
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

	@Column(name = "descripcion", nullable = false, length = 100)
	@NotNull
	@Length(max = 100)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "clave", nullable = false, length = 45)
	@NotNull
	@Length(max = 45)
	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
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

}
