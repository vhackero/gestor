package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.Date;

import mx.gob.sedesol.basegestor.commons.utils.ObjectUtils;

public class GrupoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idGrupo;
	private String clave;
	private String descripcion;
	private Date fachaActualizacion;
	private Date fechaRegistro;
	private int idEventoTemp;
	private String nombre;
	private Long usuarioModifico;
	private Integer idMoodle;
	private boolean actaCerrada;
	private EventoCapacitacionDTO evento;
	private PersonaResponsabilidadesDTO facilitador;
	private Integer numMaxAlumnos;
	private Integer numAlumnosMatriculados;

	public Integer getIdGrupo() {
		return this.idGrupo;
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

	/**
	 * @return the idEventoTemp
	 */
	public int getIdEventoTemp() {
		return idEventoTemp;
	}

	/**
	 * @param idEventoTemp
	 *            the idEventoTemp to set
	 */
	public void setIdEventoTemp(int idEventoTemp) {
		this.idEventoTemp = idEventoTemp;
	}

	public Long getUsuarioModifico() {
		return this.usuarioModifico;
	}

	public void setUsuarioModifico(Long usuarioModifico) {
		this.usuarioModifico = usuarioModifico;
	}

	public Integer getNumMaxAlumnos() {
		return numMaxAlumnos;
	}

	public Integer getNumAlumnosMatriculados() {
		return numAlumnosMatriculados;
	}

	public void setNumMaxAlumnos(Integer numMaxAlumnos) {
		this.numMaxAlumnos = numMaxAlumnos;
	}

	public void setNumAlumnosMatriculados(Integer numAlumnosMatriculados) {
		this.numAlumnosMatriculados = numAlumnosMatriculados;
	}

	public Integer getIdMoodle() {
		return idMoodle;
	}

	public void setIdMoodle(Integer idMoodle) {
		this.idMoodle = idMoodle;
	}

	public PersonaResponsabilidadesDTO getFacilitador() {
		return facilitador;
	}

	public void setFacilitador(PersonaResponsabilidadesDTO facilitador) {
		this.facilitador = facilitador;
	}

	public EventoCapacitacionDTO getEvento() {
		return evento;
	}

	public void setEvento(EventoCapacitacionDTO evento) {

		this.evento = evento;
		if (ObjectUtils.isNotNull(evento) && ObjectUtils.isNotNull(evento.getIdEvento())) {
			this.idEventoTemp = evento.getIdEvento();
		}
	}

	/**
	 * @return the actaCerrada
	 */
	public boolean isActaCerrada() {
		return actaCerrada;
	}

	/**
	 * @param actaCerrada
	 *            the actaCerrada to set
	 */
	public void setActaCerrada(boolean actaCerrada) {
		this.actaCerrada = actaCerrada;
	}

}
