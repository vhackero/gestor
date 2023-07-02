package mx.gob.sedesol.basegestor.model.entities.admin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "cat_parametros_sistema")
public class CatParametroSistema implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String clave;
	private String valor;
	private Date fechaRegistro;
	private Date fechaActualizacion;
	private Long usuarioModifico;

	public CatParametroSistema() {
	}

	public CatParametroSistema(String clave, String valor) {
		this.clave = clave;
		this.valor = valor;
	}

	@Id
	@Column(name = "clave", unique = true, nullable = false)
	@NotNull
	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	@Column(name = "valor", nullable = false)
	@NotNull
	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
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
	@Column(name = "fecha_actualizacion", length = 19)
	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	@Column(name = "usuario_modifico")
	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

}
