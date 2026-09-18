package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

/** Configuración de la evaluación escalonada de estudiantes irregulares. */
public class ConfiguracionCargaIrregularDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idPlan;
	private String nombrePlan;
	private String nombreRegla;
	private String descripcionRegla;
	private Boolean activa;
	private Integer maxReprobadasAvanceAnual;
	private Integer minReprobadasMismoSemestre;
	private Integer minReprobadasRestringirOptativas;
	private Integer minOptativasReprobadasRestringir;
	private Integer minimoObligatorias;
	private Boolean permitirSemestreAdyacente;
	private Boolean permitirOptativasOtrosSemestresAvance;
	private Boolean restringirOptativasPorRezago;
	private Boolean marcarReprobadasObligatorias;
	private Boolean marcarReprobadasOptativas;
	private Boolean marcarObligatoriasSemestreRestringido;

	public Long getIdPlan() { return idPlan; }
	public void setIdPlan(Long valor) { this.idPlan = valor; }

	public String getNombrePlan() { return nombrePlan; }
	public void setNombrePlan(String valor) { this.nombrePlan = valor; }

	public String getNombreRegla() { return nombreRegla; }
	public void setNombreRegla(String valor) { this.nombreRegla = valor; }

	public String getDescripcionRegla() { return descripcionRegla; }
	public void setDescripcionRegla(String valor) { this.descripcionRegla = valor; }

	public Boolean getActiva() { return activa; }
	public void setActiva(Boolean valor) { this.activa = valor; }

	public Integer getMaxReprobadasAvanceAnual() { return maxReprobadasAvanceAnual; }
	public void setMaxReprobadasAvanceAnual(Integer valor) { this.maxReprobadasAvanceAnual = valor; }

	public Integer getMinReprobadasMismoSemestre() { return minReprobadasMismoSemestre; }
	public void setMinReprobadasMismoSemestre(Integer valor) { this.minReprobadasMismoSemestre = valor; }

	public Integer getMinReprobadasRestringirOptativas() { return minReprobadasRestringirOptativas; }
	public void setMinReprobadasRestringirOptativas(Integer valor) { this.minReprobadasRestringirOptativas = valor; }

	public Integer getMinOptativasReprobadasRestringir() { return minOptativasReprobadasRestringir; }
	public void setMinOptativasReprobadasRestringir(Integer valor) { this.minOptativasReprobadasRestringir = valor; }

	public Integer getMinimoObligatorias() { return minimoObligatorias; }
	public void setMinimoObligatorias(Integer valor) { this.minimoObligatorias = valor; }

	public Boolean getPermitirSemestreAdyacente() { return permitirSemestreAdyacente; }
	public void setPermitirSemestreAdyacente(Boolean valor) { this.permitirSemestreAdyacente = valor; }

	public Boolean getPermitirOptativasOtrosSemestresAvance() { return permitirOptativasOtrosSemestresAvance; }
	public void setPermitirOptativasOtrosSemestresAvance(Boolean valor) { this.permitirOptativasOtrosSemestresAvance = valor; }

	public Boolean getRestringirOptativasPorRezago() { return restringirOptativasPorRezago; }
	public void setRestringirOptativasPorRezago(Boolean valor) { this.restringirOptativasPorRezago = valor; }

	public Boolean getMarcarReprobadasObligatorias() { return marcarReprobadasObligatorias; }
	public void setMarcarReprobadasObligatorias(Boolean valor) { this.marcarReprobadasObligatorias = valor; }

	public Boolean getMarcarReprobadasOptativas() { return marcarReprobadasOptativas; }
	public void setMarcarReprobadasOptativas(Boolean valor) { this.marcarReprobadasOptativas = valor; }

	public Boolean getMarcarObligatoriasSemestreRestringido() { return marcarObligatoriasSemestreRestringido; }
	public void setMarcarObligatoriasSemestreRestringido(Boolean valor) { this.marcarObligatoriasSemestreRestringido = valor; }

	private Integer minReprobadasPriorizarAsistente;
	public Integer getMinReprobadasPriorizarAsistente() { return minReprobadasPriorizarAsistente; }
	public void setMinReprobadasPriorizarAsistente(Integer valor) { this.minReprobadasPriorizarAsistente = valor; }
}
