package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the tbl_usuarios_ftp database table.
 * 
 */
@Entity
@Table(name="tbl_usuarios_ftp")
@NamedQuery(name="TblUsuariosFtp.findAll", query="SELECT t FROM TblUsuariosFtp t")
public class TblUsuariosFtp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_usuario_ftp")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idUsuarioFtp;

	private Byte activo;

	private String clave;

	private String contrasenia;

	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	private Integer orden;

	private String usuario;

	@Column(name="usuario_modifico")
	private BigInteger usuarioModifico;

	//bi-directional many-to-one association to TblInformacionServidor
	@ManyToOne
	@JoinColumn(name="id_servidor")
	private TblInformacionServidor tblInformacionServidor;

	public TblUsuariosFtp() {
	}

	public Integer getIdUsuarioFtp() {
		return this.idUsuarioFtp;
	}

	public void setIdUsuarioFtp(Integer idUsuarioFtp) {
		this.idUsuarioFtp = idUsuarioFtp;
	}

	public Byte getActivo() {
		return this.activo;
	}

	public void setActivo(Byte activo) {
		this.activo = activo;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getContrasenia() {
		return this.contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public BigInteger getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(BigInteger usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public TblInformacionServidor getTblInformacionServidor() {
		return this.tblInformacionServidor;
	}

	public void setTblInformacionServidor(TblInformacionServidor tblInformacionServidor) {
		this.tblInformacionServidor = tblInformacionServidor;
	}

}