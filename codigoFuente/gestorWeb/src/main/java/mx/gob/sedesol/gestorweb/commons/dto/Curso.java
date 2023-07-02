package mx.gob.sedesol.gestorweb.commons.dto;

import java.util.Date;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.EventoCapacitacionDTO;
import mx.gob.sedesol.basegestor.commons.dto.planesyprogramas.FichaDescProgramaDTO;
import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.CatModalidadPlanPrograma;

public class Curso {
	
	private Integer id;
	private String imagen;
	private String nombreCurso;
	private String creadoPor;
	private Date publicado;
	private String descripcion;
	private String areaConocimiento;
	private String nivel;
	private Integer valoracionCurso;
	private String duracion;
	private String impartidoPor;
	private Boolean nuevo;
	private Boolean proximamente;
	private String	correoRespCordinadorAcad;
	private FichaDescProgramaDTO programa;
	private CatModalidadPlanPrograma modalidad;
	private EventoCapacitacionDTO evento;
	public Curso(){
		
	}
	
	public Curso(Integer id, String imagen, String nombreCurso, String creadoPor, Date publicado, String descripcion, String areaConocimiento, String nivel, Integer valoracionCurso, String duracion, String impartidoPor, Boolean nuevo, Boolean proximamente){
		this.id =  id;
		this.imagen = imagen;
		this.nombreCurso = nombreCurso;
		this.creadoPor = creadoPor;
		this.publicado = publicado;
		this.descripcion = descripcion;
		this.areaConocimiento = areaConocimiento;
		this.nivel = nivel;
		this.valoracionCurso = valoracionCurso;
		this.duracion = duracion;
		this.impartidoPor = impartidoPor;
		this.nuevo = nuevo;
		this.proximamente = proximamente;
	}
	
	public Curso(String correoRespCordinadorAcad, String imagen, String nombreCurso, String descripcion,  String nivel, Integer valoracionCurso, String duracion){
		this.correoRespCordinadorAcad = correoRespCordinadorAcad;
		this.imagen = imagen;
		this.nombreCurso = nombreCurso;
		this.descripcion = descripcion;
		this.nivel = nivel;
		this.valoracionCurso = valoracionCurso;
		this.duracion = duracion;
	}
	
	public Curso(Integer idCurso, String correoRespCordinadorAcad, String imagen, String nombreCurso, String descripcion,  String nivel, Integer valoracionCurso, String duracion){
		this.id = idCurso;
		this.correoRespCordinadorAcad = correoRespCordinadorAcad;
		this.imagen = imagen;
		this.nombreCurso = nombreCurso;
		this.descripcion = descripcion;
		this.nivel = nivel;
		this.valoracionCurso = valoracionCurso;
		this.duracion = duracion;
	}
	
	public boolean hayCioncidencia(String filtro){
		filtro = filtro.toLowerCase();
		return (this.nombreCurso!=null && this.nombreCurso.toLowerCase().contains(filtro))
				|| (this.descripcion!=null && this.descripcion.toLowerCase().contains(filtro))
				|| (this.areaConocimiento!=null && this.areaConocimiento.toLowerCase().contains(filtro))
				|| (this.nivel!=null && this.nivel.toLowerCase().contains(filtro))
				|| (this.duracion!=null && this.duracion.toLowerCase().contains(filtro));
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public String getNombreCurso() {
		return nombreCurso;
	}
	public void setNombreCurso(String nombreCurso) {
		this.nombreCurso = nombreCurso;
	}
	public String getCreadoPor() {
		return creadoPor;
	}
	public void setCreadoPor(String creadoPor) {
		this.creadoPor = creadoPor;
	}
	public Date getPublicado() {
		return publicado;
	}
	public void setPublicado(Date publicado) {
		this.publicado = publicado;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getAreaConocimiento() {
		return areaConocimiento;
	}
	public void setAreaConocimiento(String areaConocimiento) {
		this.areaConocimiento = areaConocimiento;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public Integer getValoracionCurso() {
		return valoracionCurso;
	}
	public void setValoracionCurso(Integer valoracionCurso) {
		this.valoracionCurso = valoracionCurso;
	}
	public String getDuracion() {
		return duracion;
	}
	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}
	public String getImpartidoPor() {
		return impartidoPor;
	}
	public void setImpartidoPor(String impartidoPor) {
		this.impartidoPor = impartidoPor;
	}
	public Boolean getNuevo() {
		return nuevo;
	}
	public void setNuevo(Boolean nuevo) {
		this.nuevo = nuevo;
	}
	public Boolean getProximamente() {
		return proximamente;
	}
	public void setProximamente(Boolean proximamente) {
		this.proximamente = proximamente;
	}

	/**
	 * @return the correoRespCordinadorAcad
	 */
	public String getCorreoRespCordinadorAcad() {
		return correoRespCordinadorAcad;
	}

	/**
	 * @param correoRespCordinadorAcad the correoRespCordinadorAcad to set
	 */
	public void setCorreoRespCordinadorAcad(String correoRespCordinadorAcad) {
		this.correoRespCordinadorAcad = correoRespCordinadorAcad;
	}

	public FichaDescProgramaDTO getPrograma() {
		return programa;
	}

	public void setPrograma(FichaDescProgramaDTO programa) {
		this.programa = programa;
	}

	public CatModalidadPlanPrograma getModalidad() {
		return modalidad;
	}

	public void setModalidad(CatModalidadPlanPrograma modalidad) {
		this.modalidad = modalidad;
	}

	public EventoCapacitacionDTO getEvento() {
		return evento;
	}

	public void setEvento(EventoCapacitacionDTO evento) {
		this.evento = evento;
	}
	
	
}
