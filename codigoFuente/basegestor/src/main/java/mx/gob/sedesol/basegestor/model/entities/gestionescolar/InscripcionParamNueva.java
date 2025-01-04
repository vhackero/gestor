package mx.gob.sedesol.basegestor.model.entities.gestionescolar;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mx.gob.sedesol.basegestor.model.entities.planesyprogramas.TblPlan;

public class InscripcionParamNueva  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String nombreConvocatoria;
	
	private String planSeleccionada;
	
	private boolean  inscripcionExistente = false;
	
	private boolean  inscripcionOrdinaria = false;
	
	private boolean  planProgramaBoolean = false;
	
	private boolean  fechaMayor = false;
	
	public boolean getFechaMayor() {
		return fechaMayor;
	}

	public void setFechaMayor(boolean fechaMayor) {
		this.fechaMayor = fechaMayor;
	}

	public boolean getPlanProgramaBoolean() {
		return planProgramaBoolean;
	}

	public void setPlanProgramaBoolean(boolean planProgramaBoolean) {
		this.planProgramaBoolean = planProgramaBoolean;
	}

	List<InscripcionPlanesProgramas> planesProgramas;

	private List<Convocatoria> listaConvocatoria;
	
	List<TipoProceso> listaTipoProceso;
	
	List<TblPlan> listaPlanes;
	
	List<String> listaPlanesValor;
	
	List<String> listaProgramaValor;


	List<TipoProceso> listaNombres;
	
	private String convocatoriaSeleccionada;

	public String getConvocatoriaSeleccionada() {
		return convocatoriaSeleccionada;
	}
	
	public List<String> getListaProgramaValor() {
		return listaProgramaValor;
	}

	public void setListaProgramaValor(List<String> listaProgramaValor) {
		this.listaProgramaValor = listaProgramaValor;
	}

	public List<String> getListaPlanesValor() {
		return listaPlanesValor;
	}

	public void setListaPlanesValor(List<String> listaPlanesValor) {
		this.listaPlanesValor = listaPlanesValor;
	}

	public void setConvocatoriaSeleccionada(String convocatoriaSeleccionada) {
		this.convocatoriaSeleccionada = convocatoriaSeleccionada;
	}

	public List<TipoProceso> getListaTipoProceso() {
		return listaTipoProceso;
	}

	public void setListaTipoProceso(List<TipoProceso> listaTipoProceso) {
		this.listaTipoProceso = listaTipoProceso;
	}

	public List<TblPlan> getListaPlanes() {
		return listaPlanes;
	}

	public void setListaPlanes(List<TblPlan> listaPlanes) {
		this.listaPlanes = listaPlanes;
	}

	public List<TipoProceso> getListaNombres() {
		return listaNombres;
	}

	public void setListaNombres(List<TipoProceso> listaNombres) {
		this.listaNombres = listaNombres;
	}

	public List<InscripcionesTableroResumen> getListaTableResumen() {
		return listaTableResumen;
	}

	public void setListaTableResumen(List<InscripcionesTableroResumen> listaTableResumen) {
		this.listaTableResumen = listaTableResumen;
	}

	List<InscripcionesTableroResumen> listaTableResumen;
	
	public List<Convocatoria> getListaConvocatoria() {
		return listaConvocatoria;
	}

	public void setListaConvocatoria(List<Convocatoria> listaConvocatoria) {
		this.listaConvocatoria = listaConvocatoria;
	}

	private String tipoProceso;
	
	private String nombre;
	
	private String calveProceso;
	
	private String descripcion;
	
	private String semestre;

	private Date fechaInicio;
	
	private Date fechaFin;
	
	private String plan;
	
	private String programa;
	
	private String procesoSeleccionada;
	
	private String altaEstatus;
	
	private String perfil;
	
	public String getPerfil() {
		return perfil;
	}
	
	public List<InscripcionPlanesProgramas> getPlanesProgramas() {
		return planesProgramas;
	}

	public void setPlanesProgramas(List<InscripcionPlanesProgramas> planesProgramas) {
		this.planesProgramas = planesProgramas;
	}

	public boolean getInscripcionOrdinaria() {
		return inscripcionOrdinaria;
	}

	public void setInscripcionOrdinaria(boolean inscripcionOrdinaria) {
		this.inscripcionOrdinaria = inscripcionOrdinaria;
	}

	public boolean getInscripcionExistente() {
		return inscripcionExistente;
	}

	public void setInscripcionExistente(boolean inscripcionExistente) {
		this.inscripcionExistente = inscripcionExistente;
	}


	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getAltaEstatus() {
		return altaEstatus;
	}

	public void setAltaEstatus(String altaEstatus) {
		this.altaEstatus = altaEstatus;
	}

	public String getProcesoSeleccionada() {
		return procesoSeleccionada;
	}

	public void setProcesoSeleccionada(String procesoSeleccionada) {
		this.procesoSeleccionada = procesoSeleccionada;
	}

	public String getNombreConvocatoria() {
		return nombreConvocatoria;
	}

	public void setNombreConvocatoria(String nombreConvocatoria) {
		this.nombreConvocatoria = nombreConvocatoria;
	}

	public String getTipoProceso() {
		return tipoProceso;
	}

	public void setTipoProceso(String tipoProceso) {
		this.tipoProceso = tipoProceso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCalveProceso() {
		return calveProceso;
	}

	public void setCalveProceso(String calveProceso) {
		this.calveProceso = calveProceso;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getPlanSeleccionada() {
		return planSeleccionada;
	}

	public void setPlanSeleccionada(String planSeleccionada) {
		this.planSeleccionada = planSeleccionada;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	
}
