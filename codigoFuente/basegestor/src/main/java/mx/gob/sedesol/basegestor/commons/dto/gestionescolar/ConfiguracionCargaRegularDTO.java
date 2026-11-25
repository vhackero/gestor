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
	private String semestreAplicacionAvanceAnual;
	public String getSemestreAplicacionAvanceAnual() { return semestreAplicacionAvanceAnual; }
	public void setSemestreAplicacionAvanceAnual(String valor) { semestreAplicacionAvanceAnual = valor; }
	public java.util.Set<Integer> getSemestresAplicacionAvanceAnual() {
		String mensaje = "Indica uno o más valores numéricos positivos separados por comas para el avance anual.";
		if (semestreAplicacionAvanceAnual == null || semestreAplicacionAvanceAnual.trim().isEmpty()
				|| semestreAplicacionAvanceAnual.length() > 1000) throw new IllegalArgumentException(mensaje);
		java.util.Set<Integer> valores = new java.util.LinkedHashSet<>();
		for (String parte : semestreAplicacionAvanceAnual.split(",", -1)) {
			String valor = parte.trim();
			if (!valor.matches("[0-9]+")) throw new IllegalArgumentException(mensaje);
			try {
				int numero = Integer.parseInt(valor);
				if (numero < 1) throw new IllegalArgumentException(mensaje);
				valores.add(numero);
			} catch (NumberFormatException e) { throw new IllegalArgumentException(mensaje, e); }
		}
		return java.util.Collections.unmodifiableSet(valores);
	}
	private Boolean permitirSemestreAdyacente;
	private Integer minimoObligatorias;
	private Integer maximoElectivasPorPeriodo;
	private Integer maximoOptativasPorBloque;
    private Boolean priorizarOptativasSemestreReferencia;
    private Boolean limitarOptativasOrdinariasPorPeriodo;

    public Boolean getPriorizarOptativasSemestreReferencia() {
        return priorizarOptativasSemestreReferencia;
    }

    public void setPriorizarOptativasSemestreReferencia(Boolean valor) {
        priorizarOptativasSemestreReferencia = valor;
    }
    private Integer maximoOptativasOrdinariasPorPeriodo;

    public Boolean getLimitarOptativasOrdinariasPorPeriodo() { return limitarOptativasOrdinariasPorPeriodo; }
    public void setLimitarOptativasOrdinariasPorPeriodo(Boolean valor) { limitarOptativasOrdinariasPorPeriodo = valor; }
    public Integer getMaximoOptativasOrdinariasPorPeriodo() { return maximoOptativasOrdinariasPorPeriodo; }
    public void setMaximoOptativasOrdinariasPorPeriodo(Integer valor) { maximoOptativasOrdinariasPorPeriodo = valor; }
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

	private Integer optativasRequeridasPlan;
	private String optativasSoloRequeridas;
	private String mensajeOptativaSoloRequerida;
	public Integer getOptativasRequeridasPlan() { return optativasRequeridasPlan; }
	public void setOptativasRequeridasPlan(Integer valor) { optativasRequeridasPlan=valor; }
	public String getOptativasSoloRequeridas() { return optativasSoloRequeridas; }
	public void setOptativasSoloRequeridas(String valor) { optativasSoloRequeridas=valor; }
	public String getMensajeOptativaSoloRequerida() { return mensajeOptativaSoloRequerida; }
	public void setMensajeOptativaSoloRequerida(String valor) { mensajeOptativaSoloRequerida=valor; }
	public boolean esOptativaSoloRequerida(String nombre) {
		if(optativasSoloRequeridas==null)throw new IllegalStateException("Falta OPTATIVAS_SOLO_REQUERIDAS.");
		String unidad=normalizarOptativa(nombre);
		for(String fragmento:optativasSoloRequeridas.split(";")) {
			String criterio=normalizarOptativa(fragmento);
			if(!criterio.isEmpty() && unidad.contains(criterio))return true;
		}
		return false;
	}
	public String avisoOptativaSoloRequerida(String nombre) {
		if(mensajeOptativaSoloRequerida==null)throw new IllegalStateException("Falta MENSAJE_OPTATIVA_SOLO_REQUERIDA.");
		return mensajeOptativaSoloRequerida.replace("{unidad}",java.util.Objects.toString(nombre,""));
	}
	private String normalizarOptativa(String nombre) {
		return java.text.Normalizer.normalize(java.util.Objects.toString(nombre,""),java.text.Normalizer.Form.NFD)
			.replaceAll("\\p{M}","").toLowerCase(java.util.Locale.ROOT).trim().replaceAll("\\s+"," ");
	}
	/** Alias de compatibilidad: el asistente usa el mismo umbral del plan. */
	@Deprecated
	public Integer getMinimoOptativasPlanAsistente() { return optativasRequeridasPlan; }
	public void setMinimoOptativasPlanAsistente(Integer valor) { this.optativasRequeridasPlan = valor; }
}
