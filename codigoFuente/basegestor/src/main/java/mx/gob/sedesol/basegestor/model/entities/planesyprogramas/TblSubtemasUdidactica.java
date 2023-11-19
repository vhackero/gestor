package mx.gob.sedesol.basegestor.model.entities.planesyprogramas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tbl_subtemas_udidactica database table.
 * 
 */
@Entity
@Table(name="tbl_subtemas_udidactica")
@NamedQuery(name="TblSubtemasUdidactica.findAll", query="SELECT t FROM TblSubtemasUdidactica t")
public class TblSubtemasUdidactica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_subtema_udidactica")
	private Integer idSubtemaUdidactica;

	private Integer activo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_actualizacion", insertable = false, updatable=false)
	private Date fechaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	private Integer horas;

	private Integer minutos;

	private String nombre;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;

	//bi-directional many-to-one association to DetEstUnidadDidactica
	@ManyToOne
	@JoinColumn(name="id_unidad_didactica")
	private DetEstUnidadDidactica detEstUnidadDidactica;

	public TblSubtemasUdidactica() {
	}

	public Integer getIdSubtemaUdidactica() {
		return this.idSubtemaUdidactica;
	}

	public void setIdSubtemaUdidactica(Integer idSubtemaUdidactica) {
		this.idSubtemaUdidactica = idSubtemaUdidactica;
	}

	public Integer getActivo() {
		return this.activo;
	}

	public void setActivo(Integer activo) {
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

	public Integer getHoras() {
		return this.horas;
	}

	public void setHoras(Integer horas) {
		this.horas = horas;
	}

	public Integer getMinutos() {
		return this.minutos;
	}

	public void setMinutos(Integer minutos) {
		this.minutos = minutos;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public DetEstUnidadDidactica getDetEstUnidadDidactica() {
		return this.detEstUnidadDidactica;
	}

	public void setDetEstUnidadDidactica(DetEstUnidadDidactica detEstUnidadDidactica) {
		this.detEstUnidadDidactica = detEstUnidadDidactica;
	}

}