package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;



/**
 * The persistent class for the tbl_ruta_respaldos database table.
 * 
 */
@Entity
@Table(name="tbl_ruta_respaldos")
@NamedQuery(name="TblRutaRespaldo.findAll", query="SELECT t FROM TblRutaRespaldo t")
public class TblRutaRespaldo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_ruta_respaldo")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idRutaRespaldo;

	private Byte activo;

	private String clave;

	private String descripcion;

	@Column(name="directorio_ruta")
	private String directorioRuta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	private Integer orden;

	@Column(name="tipo_ruta")
	private Integer tipoRuta;

	@Column(name="usuario_modifico")
	private BigInteger usuarioModifico;


	//bi-directional many-to-one association to TblInformacionServidor
	@ManyToOne
	@JoinColumn(name="id_servidor")
	private TblInformacionServidor tblInformacionServidor;

	public TblRutaRespaldo() {
	}

	public Integer getIdRutaRespaldo() {
		return this.idRutaRespaldo;
	}

	public void setIdRutaRespaldo(Integer idRutaRespaldo) {
		this.idRutaRespaldo = idRutaRespaldo;
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

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDirectorioRuta() {
		return this.directorioRuta;
	}

	public void setDirectorioRuta(String directorioRuta) {
		this.directorioRuta = directorioRuta;
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

	public Integer getTipoRuta() {
		return this.tipoRuta;
	}

	public void setTipoRuta(Integer tipoRuta) {
		this.tipoRuta = tipoRuta;
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