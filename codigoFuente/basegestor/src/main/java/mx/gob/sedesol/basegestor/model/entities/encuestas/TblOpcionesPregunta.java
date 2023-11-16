package mx.gob.sedesol.basegestor.model.entities.encuestas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the tbl_opciones_pregunta database table.
 * 
 */
@Entity
@Table(name="tbl_opciones_pregunta")
@NamedQuery(name="TblOpcionesPregunta.findAll", query="SELECT t FROM TblOpcionesPregunta t")
public class TblOpcionesPregunta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_opcion")
	private int idOpcion;

	private byte activo;

	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Column(name="txt_opcion")
	private String txtOpcion;

	@Column(name="usuario_modifico")
	private BigInteger usuarioModifico;

	//bi-directional many-to-one association to TblEncuesta
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_encuesta")
	private TblEncuesta tblEncuesta;

	public TblOpcionesPregunta() {
	}

	public int getIdOpcion() {
		return this.idOpcion;
	}

	public void setIdOpcion(int idOpcion) {
		this.idOpcion = idOpcion;
	}

	public byte getActivo() {
		return this.activo;
	}

	public void setActivo(byte activo) {
		this.activo = activo;
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

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getTxtOpcion() {
		return this.txtOpcion;
	}

	public void setTxtOpcion(String txtOpcion) {
		this.txtOpcion = txtOpcion;
	}

	public BigInteger getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(BigInteger usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public TblEncuesta getTblEncuesta() {
		return this.tblEncuesta;
	}

	public void setTblEncuesta(TblEncuesta tblEncuesta) {
		this.tblEncuesta = tblEncuesta;
	}

}