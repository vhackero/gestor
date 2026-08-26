package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class ConfiguracionCargaNuevoIngresoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idPlan;
	private String nombrePlan;
	private Long idPersona;
	private String username;
	private String nombreRegla;
	private String descripcionRegla;
	private Boolean activa;
	private Integer obligatoriasRequeridas;
	private Integer optativasRequeridas;
	private Boolean restringirPrimerSemestre;
	private Boolean mostrarSegundoSemestreSinOfertaPrimero;
	private Boolean autoseleccionarObligatorias;
	private Boolean bloquearObligatorias;
	private String ayudaObligatorias;
	private String ayudaOptativas;
	private String ayudaRestringirPrimerSemestre;
	private String ayudaMostrarSegundoSemestreSinOfertaPrimero;
	private String ayudaAutoseleccionarObligatorias;
	private String ayudaBloquearObligatorias;

	public Long getIdPlan() { return idPlan; }
	public void setIdPlan(Long idPlan) { this.idPlan = idPlan; }
	public String getNombrePlan() { return nombrePlan; }
	public void setNombrePlan(String nombrePlan) { this.nombrePlan = nombrePlan; }
	public Long getIdPersona() { return idPersona; }
	public void setIdPersona(Long idPersona) { this.idPersona = idPersona; }
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username == null ? null : username.trim(); }
	public String getNombreRegla() { return nombreRegla; }
	public void setNombreRegla(String nombreRegla) { this.nombreRegla = nombreRegla; }
	public String getDescripcionRegla() { return descripcionRegla; }
	public void setDescripcionRegla(String descripcionRegla) { this.descripcionRegla = descripcionRegla; }
	public Boolean getActiva() { return activa; }
	public void setActiva(Boolean activa) { this.activa = activa; }
	public Integer getObligatoriasRequeridas() { return obligatoriasRequeridas; }
	public void setObligatoriasRequeridas(Integer obligatoriasRequeridas) { this.obligatoriasRequeridas = obligatoriasRequeridas; }
	public Integer getOptativasRequeridas() { return optativasRequeridas; }
	public void setOptativasRequeridas(Integer optativasRequeridas) { this.optativasRequeridas = optativasRequeridas; }
	public Boolean getRestringirPrimerSemestre() { return restringirPrimerSemestre; }
	public void setRestringirPrimerSemestre(Boolean valor) { this.restringirPrimerSemestre = valor; }
	public Boolean getMostrarSegundoSemestreSinOfertaPrimero() { return mostrarSegundoSemestreSinOfertaPrimero; }
	public void setMostrarSegundoSemestreSinOfertaPrimero(Boolean valor) { this.mostrarSegundoSemestreSinOfertaPrimero = valor; }
	public Boolean getAutoseleccionarObligatorias() { return autoseleccionarObligatorias; }
	public void setAutoseleccionarObligatorias(Boolean autoseleccionarObligatorias) { this.autoseleccionarObligatorias = autoseleccionarObligatorias; }
	public Boolean getBloquearObligatorias() { return bloquearObligatorias; }
	public void setBloquearObligatorias(Boolean bloquearObligatorias) { this.bloquearObligatorias = bloquearObligatorias; }
	public String getAyudaObligatorias() { return ayudaObligatorias; }
	public void setAyudaObligatorias(String ayudaObligatorias) { this.ayudaObligatorias = ayudaObligatorias; }
	public String getAyudaOptativas() { return ayudaOptativas; }
	public void setAyudaOptativas(String ayudaOptativas) { this.ayudaOptativas = ayudaOptativas; }
	public String getAyudaRestringirPrimerSemestre() { return ayudaRestringirPrimerSemestre; }
	public void setAyudaRestringirPrimerSemestre(String valor) { this.ayudaRestringirPrimerSemestre = valor; }
	public String getAyudaMostrarSegundoSemestreSinOfertaPrimero() { return ayudaMostrarSegundoSemestreSinOfertaPrimero; }
	public void setAyudaMostrarSegundoSemestreSinOfertaPrimero(String valor) { this.ayudaMostrarSegundoSemestreSinOfertaPrimero = valor; }
	public String getAyudaAutoseleccionarObligatorias() { return ayudaAutoseleccionarObligatorias; }
	public void setAyudaAutoseleccionarObligatorias(String valor) { this.ayudaAutoseleccionarObligatorias = valor; }
	public String getAyudaBloquearObligatorias() { return ayudaBloquearObligatorias; }
	public void setAyudaBloquearObligatorias(String valor) { this.ayudaBloquearObligatorias = valor; }
}
