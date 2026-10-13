package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class ConsultaActaEventoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idPlan;
	private String plan;
	private String semestre;
	private String bloque;
	private Long idPrograma;
	private String programa;
	private Long idEvento;
	private String evento;
	private Long idGrupo;
	private String grupo;
	private Integer estatus;
	private String textoEstatus;
	private Boolean actaCerrada;
	private int numeroActas;
	private int numeroAlumnos;
	private int alumnosCalificados;
	private int documentosInvalidos;

	public Boolean getActaCerrada() { return actaCerrada; }
	public void setActaCerrada(Boolean actaCerrada) { this.actaCerrada = actaCerrada; }
	public int getNumeroActas() { return numeroActas; }
	public void setNumeroActas(int numeroActas) { this.numeroActas = numeroActas; }
	public int getNumeroAlumnos() { return numeroAlumnos; }
	public void setNumeroAlumnos(int numeroAlumnos) { this.numeroAlumnos = numeroAlumnos; }
	public int getAlumnosCalificados() { return alumnosCalificados; }
	public void setAlumnosCalificados(int alumnosCalificados) { this.alumnosCalificados = alumnosCalificados; }
	public int getDocumentosInvalidos() { return documentosInvalidos; }
	public void setDocumentosInvalidos(int documentosInvalidos) { this.documentosInvalidos = documentosInvalidos; }
	public int getCalificacionesPendientes() { return numeroAlumnos - alumnosCalificados; }
	public String getEstadoGrupo() {
		return actaCerrada == null ? "Sin definir" : (actaCerrada ? "Cerrado" : "Abierto");
	}

	public Long getIdPlan() {
		return idPlan;
	}

	public void setIdPlan(Long idPlan) {
		this.idPlan = idPlan;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getSemestre() {
		return semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public Long getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Long idPrograma) {
		this.idPrograma = idPrograma;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public String getTextoEstatus() {
		return textoEstatus;
	}

	public void setTextoEstatus(String textoEstatus) {
		this.textoEstatus = textoEstatus;
	}
}
