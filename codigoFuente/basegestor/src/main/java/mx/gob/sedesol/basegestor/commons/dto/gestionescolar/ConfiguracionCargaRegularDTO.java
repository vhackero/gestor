package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;

public class ConfiguracionCargaRegularDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idPlan;
	private String nombrePlan;
	private String nombreRegla;
	private String descripcionRegla;
	private Boolean activa;
	private Boolean restringirAvanceAnual;
	private Boolean permitirSemestreAdyacente;
	private Integer minimoObligatorias;
	private Integer maximoElectivasPorPeriodo;
	private Integer maximoOptativasPorBloque;
	private Integer optativasAprobadasParaOpcionales;
	private Boolean impedirClaveOptativaRepetida;
	private Integer porcentajeMinimoTramoFinal;
	private Integer semestreInicioTramoFinal;
	private Boolean validarRezagosSeriados;
	private Integer semestreDestinoRezagos;
	private Integer semestreInicialAntecedentes;
	private Integer semestreFinalAntecedentes;

	public Long getIdPlan() { return idPlan; }
	public void setIdPlan(Long idPlan) { this.idPlan = idPlan; }
	public String getNombrePlan() { return nombrePlan; }
	public void setNombrePlan(String nombrePlan) { this.nombrePlan = nombrePlan; }
	public String getNombreRegla() { return nombreRegla; }
	public void setNombreRegla(String nombreRegla) { this.nombreRegla = nombreRegla; }
	public String getDescripcionRegla() { return descripcionRegla; }
	public void setDescripcionRegla(String descripcionRegla) { this.descripcionRegla = descripcionRegla; }
	public Boolean getActiva() { return activa; }
	public void setActiva(Boolean activa) { this.activa = activa; }
	public Boolean getRestringirAvanceAnual() { return restringirAvanceAnual; }
	public void setRestringirAvanceAnual(Boolean valor) { this.restringirAvanceAnual = valor; }
	public Boolean getPermitirSemestreAdyacente() { return permitirSemestreAdyacente; }
	public void setPermitirSemestreAdyacente(Boolean valor) { this.permitirSemestreAdyacente = valor; }
	public Integer getMinimoObligatorias() { return minimoObligatorias; }
	public void setMinimoObligatorias(Integer valor) { this.minimoObligatorias = valor; }
	public Integer getMaximoElectivasPorPeriodo() { return maximoElectivasPorPeriodo; }
	public void setMaximoElectivasPorPeriodo(Integer valor) { this.maximoElectivasPorPeriodo = valor; }
	public Integer getMaximoOptativasPorBloque() { return maximoOptativasPorBloque; }
	public void setMaximoOptativasPorBloque(Integer valor) { this.maximoOptativasPorBloque = valor; }
	public Integer getOptativasAprobadasParaOpcionales() { return optativasAprobadasParaOpcionales; }
	public void setOptativasAprobadasParaOpcionales(Integer valor) { this.optativasAprobadasParaOpcionales = valor; }
	public Boolean getImpedirClaveOptativaRepetida() { return impedirClaveOptativaRepetida; }
	public void setImpedirClaveOptativaRepetida(Boolean valor) { this.impedirClaveOptativaRepetida = valor; }
	public Integer getPorcentajeMinimoTramoFinal() { return porcentajeMinimoTramoFinal; }
	public void setPorcentajeMinimoTramoFinal(Integer valor) { this.porcentajeMinimoTramoFinal = valor; }
	public Integer getSemestreInicioTramoFinal() { return semestreInicioTramoFinal; }
	public void setSemestreInicioTramoFinal(Integer valor) { this.semestreInicioTramoFinal = valor; }
	public Boolean getValidarRezagosSeriados() { return validarRezagosSeriados; }
	public void setValidarRezagosSeriados(Boolean valor) { this.validarRezagosSeriados = valor; }
	public Integer getSemestreDestinoRezagos() { return semestreDestinoRezagos; }
	public void setSemestreDestinoRezagos(Integer valor) { this.semestreDestinoRezagos = valor; }
	public Integer getSemestreInicialAntecedentes() { return semestreInicialAntecedentes; }
	public void setSemestreInicialAntecedentes(Integer valor) { this.semestreInicialAntecedentes = valor; }
	public Integer getSemestreFinalAntecedentes() { return semestreFinalAntecedentes; }
	public void setSemestreFinalAntecedentes(Integer valor) { this.semestreFinalAntecedentes = valor; }

	private Integer limiteReprobacionesPorMateria;
	public Integer getLimiteReprobacionesPorMateria() { return limiteReprobacionesPorMateria; }
	public void setLimiteReprobacionesPorMateria(Integer valor) { this.limiteReprobacionesPorMateria = valor; }

	private Integer semestreMinimoElectivas;
	public Integer getSemestreMinimoElectivas() { return semestreMinimoElectivas; }
	public void setSemestreMinimoElectivas(Integer valor) { this.semestreMinimoElectivas = valor; }

	private Integer primerSemestreOrigenElectivas;
	public Integer getPrimerSemestreOrigenElectivas() { return primerSemestreOrigenElectivas; }
	public void setPrimerSemestreOrigenElectivas(Integer valor) { this.primerSemestreOrigenElectivas = valor; }

	private Integer segundoSemestreOrigenElectivas;
	public Integer getSegundoSemestreOrigenElectivas() { return segundoSemestreOrigenElectivas; }
	public void setSegundoSemestreOrigenElectivas(Integer valor) { this.segundoSemestreOrigenElectivas = valor; }

	private Integer minimoOptativasPlanAsistente;
	public Integer getMinimoOptativasPlanAsistente() { return minimoOptativasPlanAsistente; }
	public void setMinimoOptativasPlanAsistente(Integer valor) { this.minimoOptativasPlanAsistente = valor; }
}
