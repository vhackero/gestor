	package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tbl_grupos")
public class TblGrupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer idGrupo;

	private String clave;

	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="facha_actualizacion")
	private Date fachaActualizacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_registro", insertable = false, updatable=false)
	private Date fechaRegistro;

	private String nombre;

	@Column(name="usuario_modifico")
	private Long usuarioModifico;
	
	@Column(name="id_moodle")
	private Integer idMoodle;
	
	@Column(name="num_max_alumnos")
	private Integer numMaxAlumnos;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_facilitador")
	private RelPersonaResponsabilidades facilitador;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_evento")
	private TblEvento evento;
	
	@Column(name="acta_cerrada", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean actaCerrada;
	
	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer id) {
		this.idGrupo = id;
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

	public Date getFachaActualizacion() {
		return this.fachaActualizacion;
	}

	public void setFachaActualizacion(Date fachaActualizacion) {
		this.fachaActualizacion = fachaActualizacion;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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

	public Integer getIdMoodle() {
		return idMoodle;
	}

	public void setIdMoodle(Integer idMoodle) {
		this.idMoodle = idMoodle;
	}

	public Integer getNumMaxAlumnos() {
		return numMaxAlumnos;
	}

	public void setNumMaxAlumnos(Integer numMaxAlumnos) {
		this.numMaxAlumnos = numMaxAlumnos;
	}

	public RelPersonaResponsabilidades getFacilitador() {
		return facilitador;
	}

	public void setFacilitador(RelPersonaResponsabilidades facilitador) {
		this.facilitador = facilitador;
	}

	public TblEvento getEvento() {
		return evento;
	}

	public void setEvento(TblEvento evento) {
		this.evento = evento;
	}

	/**
	 * @return the actaCerrada
	 */
	public boolean isActaCerrada() {
		return actaCerrada;
	}

	/**
	 * @param actaCerrada the actaCerrada to set
	 */
	public void setActaCerrada(boolean actaCerrada) {
		this.actaCerrada = actaCerrada;
	}

	@Override
	public String toString() {
		return "TblGrupo [idGrupo=" + idGrupo + ", clave=" + clave + ", descripcion=" + descripcion
				+ ", fachaActualizacion=" + fachaActualizacion + ", fechaRegistro=" + fechaRegistro + ", nombre="
				+ nombre + ", usuarioModifico=" + usuarioModifico + ", idMoodle=" + idMoodle + ", numMaxAlumnos="
				+ numMaxAlumnos + ", facilitador=" + facilitador + ", evento=" + evento + ", actaCerrada=" + actaCerrada
				+ "]";
	}
	
	

}
