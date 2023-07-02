package mx.gob.sedesol.basegestor.model.entities.gestionaprendizaje;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the tbl_respaldos_ava database table.
 * 
 */
@Entity
@Table(name="tbl_respaldos_ava")
@NamedQuery(name="TblRespaldosAva.findAll", query="SELECT t FROM TblRespaldosAva t")
public class TblRespaldosAva implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_respaldo_ava")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idRespaldoAva;

	private Boolean activo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion")
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="nombre_curso_con_datos")
	private String nombreCursoConDatos;

	@Column(name="nombre_curso_sin_datos")
	private String nombreCursoSinDatos;

	private Integer orden;

	@Column(name="usuario_modifico")
	private BigInteger usuarioModifico;
	
	@Column(name="url_curso_con_datos")
	private String urlCursoConDatos;

	@Column(name="url_curso_sin_datos")
	private String urlCursoSinDatos;


	//bi-directional many-to-one association to TblRutaRespaldo
	@ManyToOne
	@JoinColumn(name="id_ruta_respaldo")
	private TblRutaRespaldo tblRutaRespaldo;

	//bi-directional many-to-one association to TblAmbienteVirtualAprendizaje
	@ManyToOne
	@JoinColumn(name="id_ava")
	private TblAmbienteVirtualAprendizaje tblAmbienteVirtualAprendizaje;

	public TblRespaldosAva() {
	}

	public Integer getIdRespaldoAva() {
		return this.idRespaldoAva;
	}

	public void setIdRespaldoAva(Integer idRespaldoAva) {
		this.idRespaldoAva = idRespaldoAva;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
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

	public String getNombreCursoConDatos() {
		return this.nombreCursoConDatos;
	}

	public void setNombreCursoConDatos(String nombreCursoConDatos) {
		this.nombreCursoConDatos = nombreCursoConDatos;
	}

	public String getNombreCursoSinDatos() {
		return this.nombreCursoSinDatos;
	}

	public void setNombreCursoSinDatos(String nombreCursoSinDatos) {
		this.nombreCursoSinDatos = nombreCursoSinDatos;
	}

	public Integer getOrden() {
		return this.orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public BigInteger getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(BigInteger usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public TblRutaRespaldo getTblRutaRespaldo() {
		return this.tblRutaRespaldo;
	}

	public void setTblRutaRespaldo(TblRutaRespaldo tblRutaRespaldo) {
		this.tblRutaRespaldo = tblRutaRespaldo;
	}

	public TblAmbienteVirtualAprendizaje getTblAmbienteVirtualAprendizaje() {
		return this.tblAmbienteVirtualAprendizaje;
	}

	public void setTblAmbienteVirtualAprendizaje(TblAmbienteVirtualAprendizaje tblAmbienteVirtualAprendizaje) {
		this.tblAmbienteVirtualAprendizaje = tblAmbienteVirtualAprendizaje;
	}

	public String getUrlCursoConDatos() {
		return urlCursoConDatos;
	}

	public void setUrlCursoConDatos(String urlCursoConDatos) {
		this.urlCursoConDatos = urlCursoConDatos;
	}

	public String getUrlCursoSinDatos() {
		return urlCursoSinDatos;
	}

	public void setUrlCursoSinDatos(String urlCursoSinDatos) {
		this.urlCursoSinDatos = urlCursoSinDatos;
	}
	
	

}