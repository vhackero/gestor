package mx.gob.sedesol.basegestor.model.entities.admin;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * The persistent class for the tbl_funcionalidades database table.
 * 
 */
@Entity
@Table(name = "tbl_funcionalidades")
@NamedQuery(name = "TblFuncionalidad.findAll", query = "SELECT t FROM TblFuncionalidad t")
public class TblFuncionalidad implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idFuncionalidad;
	private String clave;
	private String descripcion;
	private Long usuarioModifico;
	private Date fechaRegistro;
	private Date fechaActualizacion;
	private boolean activo;
	private TblFuncionalidad funcionalidadPadre;

	public TblFuncionalidad() {
		funcionalidadPadre = null;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_funcionalidad", unique = true, nullable = false)
	public Long getIdFuncionalidad() {
		return this.idFuncionalidad;
	}

	public void setIdFuncionalidad(Long idFuncionalidad) {
		this.idFuncionalidad = idFuncionalidad;
	}

	@Column(name = "clave", unique = true, nullable = false, length = 100)
	@NotNull
	@Length(max = 100)
	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	@Column(name = "descripcion", length = 250)
	@Length(max = 250)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "usuario_modifico")
	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
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

	@Column(name = "activo", nullable = false)
	public boolean isActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_funcionalidad_padre")
	public TblFuncionalidad getFuncionalidadPadre() {
		return funcionalidadPadre;
	}

	public void setFuncionalidadPadre(TblFuncionalidad funcionalidadPadre) {
		this.funcionalidadPadre = funcionalidadPadre;
	}

}