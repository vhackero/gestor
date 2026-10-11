package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Contexto de sólo lectura para comparar el motor vigente con la evaluación V2.
 * No representa una solicitud de inscripción ni conserva una selección del usuario.
 */
public class ContextoEvaluacionCurricularV2DTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idPersona;
    private Long idPlan;
    private Long idPeriodo;
    private String periodoNormativo;
    private Long idProcesoInscripcion;
    private Integer semestreObjetivo;
    private String bloqueObjetivo;
    private String perfilConsulta;
    private String origenConsulta;
    private String versionReglas;
    private String huellaOferta;
    private String situacionAcademicaOrigen;
    private Integer totalReprobadasOrigen;
    private Integer cargaMinimaOrigen;
    private Integer cargaMaximaOrigen;
    private Boolean ctr01ProcedenciaFutura = Boolean.FALSE;
    private Boolean bajaTemporalRegistrada = Boolean.FALSE;
    /* Datos de actividad sólo se llenan cuando la fuente canónica los acredita. */
    private Boolean bajaTemporalTotalRegistrada = Boolean.FALSE;
    private Boolean bajaParcialRegistrada = Boolean.FALSE;
    private Boolean reincorporacionRegistrada = Boolean.FALSE;
    private Boolean bajaDefinitivaRegistrada = Boolean.FALSE;
    private Boolean actividadAcademicaComprobada = Boolean.FALSE;
    private Boolean seriacionComprobada = Boolean.TRUE;
    private Boolean limiteInactividadAlcanzado = Boolean.FALSE;
    private Boolean datosActividadContradictorios = Boolean.FALSE;
    private Boolean permanenciaDentroLimiteComprobada = Boolean.FALSE;
    private Boolean limitePermanenciaExcedido = Boolean.FALSE;
    private Boolean dictaminacionEgresoConocida = Boolean.FALSE;
    private Boolean dictaminacionEgresoIniciada = Boolean.FALSE;
    private Integer oportunidadesMejoraUtilizadas;
    private Integer periodosBajaTemporalAutorizados;
    private Integer periodosBajaTemporalTranscurridos;
    private List<String> periodosActividadConsiderados = new ArrayList<String>();
    private List<String> evidenciasActividad = new ArrayList<String>();
    private Boolean avanceCondicionado;
    /** Sólo se llena cuando una fuente canónica acredita el dato disciplinario. */
    private Boolean sancionImpedimentoConocida;
    private Boolean sancionImpedimentoRegistrada;
    /** Compatibilidad curricular del plan acreditada por fuente canónica; null preserva incertidumbre. */
    private Boolean estructuraProgramaEducativoComprobada;
    private Boolean restriccionPeNormativaConocida;
    private Boolean restriccionPeNormativaImpeditiva;
    private String codigoRestriccionPeNormativa;
    private String fuenteRestriccionPeNormativa;
    private Boolean admitido;
    private Boolean inscripcionInicialRealizada;
    private String periodoAdmision;
    private String periodoIncorporacion;
    /** Evidencia de trayectoria requerida por RN-6.5; null conserva incertidumbre. */
    private Boolean haCursadoUnoOMasPeriodos;
    private Boolean noCursoPeriodoInmediatoSiguiente;
    private Boolean causaInterrupcionConocida;
    private Boolean limiteInactividadComprobado;
    private String causaInterrupcion;
    private String ultimoPeriodoCursado;
    private String primerPeriodoOmitido;
    private List<String> periodosOmitidos = new ArrayList<String>();
    private String periodoReincorporacion;
    private TipoProcesoReincorporacionV2 tipoProcesoReincorporacion = TipoProcesoReincorporacionV2.NO_DETERMINADO;
    private Date fechaEvaluacion;
    private List<UnidadEvaluacionCurricularV2DTO> unidades = new ArrayList<UnidadEvaluacionCurricularV2DTO>();

    public Long getIdPersona() { return idPersona; }
    public void setIdPersona(Long idPersona) { this.idPersona = idPersona; }
    public Long getIdPlan() { return idPlan; }
    public void setIdPlan(Long idPlan) { this.idPlan = idPlan; }
    public Long getIdPeriodo() { return idPeriodo; }
    public void setIdPeriodo(Long idPeriodo) { this.idPeriodo = idPeriodo; }
    public String getPeriodoNormativo() { return periodoNormativo; }
    public void setPeriodoNormativo(String valor) { periodoNormativo = valor; }
    public Long getIdProcesoInscripcion() { return idProcesoInscripcion; }
    public void setIdProcesoInscripcion(Long idProcesoInscripcion) { this.idProcesoInscripcion = idProcesoInscripcion; }
    public Integer getSemestreObjetivo() { return semestreObjetivo; }
    public void setSemestreObjetivo(Integer semestreObjetivo) { this.semestreObjetivo = semestreObjetivo; }
    public String getBloqueObjetivo() { return bloqueObjetivo; }
    public void setBloqueObjetivo(String bloqueObjetivo) { this.bloqueObjetivo = bloqueObjetivo; }
    public String getPerfilConsulta() { return perfilConsulta; }
    public void setPerfilConsulta(String perfilConsulta) { this.perfilConsulta = perfilConsulta; }
    public String getOrigenConsulta() { return origenConsulta; }
    public void setOrigenConsulta(String origenConsulta) { this.origenConsulta = origenConsulta; }
    public String getVersionReglas() { return versionReglas; }
    public void setVersionReglas(String versionReglas) { this.versionReglas = versionReglas; }
    public String getHuellaOferta() { return huellaOferta; }
    public void setHuellaOferta(String huellaOferta) { this.huellaOferta = huellaOferta; }
    public String getSituacionAcademicaOrigen() { return situacionAcademicaOrigen; }
    public void setSituacionAcademicaOrigen(String situacionAcademicaOrigen) { this.situacionAcademicaOrigen = situacionAcademicaOrigen; }
    public Integer getTotalReprobadasOrigen() { return totalReprobadasOrigen; }
    public void setTotalReprobadasOrigen(Integer totalReprobadasOrigen) { this.totalReprobadasOrigen = totalReprobadasOrigen; }
    public Integer getCargaMinimaOrigen() { return cargaMinimaOrigen; }
    public void setCargaMinimaOrigen(Integer cargaMinimaOrigen) { this.cargaMinimaOrigen = cargaMinimaOrigen; }
    public Integer getCargaMaximaOrigen() { return cargaMaximaOrigen; }
    public void setCargaMaximaOrigen(Integer cargaMaximaOrigen) { this.cargaMaximaOrigen = cargaMaximaOrigen; }
    public Boolean getCtr01ProcedenciaFutura() { return ctr01ProcedenciaFutura; }
    public void setCtr01ProcedenciaFutura(Boolean ctr01ProcedenciaFutura) { this.ctr01ProcedenciaFutura = ctr01ProcedenciaFutura; }
    public Boolean getBajaTemporalRegistrada() { return bajaTemporalRegistrada; }
    public void setBajaTemporalRegistrada(Boolean bajaTemporalRegistrada) { this.bajaTemporalRegistrada = bajaTemporalRegistrada; }
    public Boolean getBajaTemporalTotalRegistrada() { return bajaTemporalTotalRegistrada; }
    public void setBajaTemporalTotalRegistrada(Boolean valor) { bajaTemporalTotalRegistrada = valor; }
    public Boolean getBajaParcialRegistrada() { return bajaParcialRegistrada; }
    public void setBajaParcialRegistrada(Boolean valor) { bajaParcialRegistrada = valor; }
    public Boolean getReincorporacionRegistrada() { return reincorporacionRegistrada; }
    public void setReincorporacionRegistrada(Boolean valor) { reincorporacionRegistrada = valor; }
    public Boolean getBajaDefinitivaRegistrada() { return bajaDefinitivaRegistrada; }
    public void setBajaDefinitivaRegistrada(Boolean valor) { bajaDefinitivaRegistrada = valor; }
    public Boolean getActividadAcademicaComprobada() { return actividadAcademicaComprobada; }
    public void setActividadAcademicaComprobada(Boolean valor) { actividadAcademicaComprobada = valor; }
    public Boolean getSeriacionComprobada() { return seriacionComprobada; }
    public void setSeriacionComprobada(Boolean valor) { seriacionComprobada = valor; }
    public Boolean getLimiteInactividadAlcanzado() { return limiteInactividadAlcanzado; }
    public void setLimiteInactividadAlcanzado(Boolean valor) { limiteInactividadAlcanzado = valor; }
    public Boolean getDatosActividadContradictorios() { return datosActividadContradictorios; }
    public void setDatosActividadContradictorios(Boolean valor) { datosActividadContradictorios = valor; }
    public Boolean getPermanenciaDentroLimiteComprobada() { return permanenciaDentroLimiteComprobada; }
    public void setPermanenciaDentroLimiteComprobada(Boolean valor) { permanenciaDentroLimiteComprobada = valor; }
    public Boolean getLimitePermanenciaExcedido() { return limitePermanenciaExcedido; }
    public void setLimitePermanenciaExcedido(Boolean valor) { limitePermanenciaExcedido = valor; }
    public Boolean getDictaminacionEgresoConocida() { return dictaminacionEgresoConocida; }
    public void setDictaminacionEgresoConocida(Boolean valor) { dictaminacionEgresoConocida = valor; }
    public Boolean getDictaminacionEgresoIniciada() { return dictaminacionEgresoIniciada; }
    public void setDictaminacionEgresoIniciada(Boolean valor) { dictaminacionEgresoIniciada = valor; }
    public Integer getOportunidadesMejoraUtilizadas() { return oportunidadesMejoraUtilizadas; }
    public void setOportunidadesMejoraUtilizadas(Integer valor) { oportunidadesMejoraUtilizadas = valor; }
    public Integer getPeriodosBajaTemporalAutorizados() { return periodosBajaTemporalAutorizados; }
    public void setPeriodosBajaTemporalAutorizados(Integer valor) { periodosBajaTemporalAutorizados = valor; }
    public Integer getPeriodosBajaTemporalTranscurridos() { return periodosBajaTemporalTranscurridos; }
    public void setPeriodosBajaTemporalTranscurridos(Integer valor) { periodosBajaTemporalTranscurridos = valor; }
    public List<String> getPeriodosActividadConsiderados() { return periodosActividadConsiderados; }
    public void setPeriodosActividadConsiderados(List<String> valores) { periodosActividadConsiderados = valores == null ? new ArrayList<String>() : valores; }
    public List<String> getEvidenciasActividad() { return evidenciasActividad; }
    public void setEvidenciasActividad(List<String> valores) { evidenciasActividad = valores == null ? new ArrayList<String>() : valores; }
    public Boolean getAvanceCondicionado() { return avanceCondicionado; }
    public void setAvanceCondicionado(Boolean avanceCondicionado) { this.avanceCondicionado = avanceCondicionado; }
    public Boolean getSancionImpedimentoConocida() { return sancionImpedimentoConocida; }
    public void setSancionImpedimentoConocida(Boolean valor) { sancionImpedimentoConocida = valor; }
    public Boolean getSancionImpedimentoRegistrada() { return sancionImpedimentoRegistrada; }
    public void setSancionImpedimentoRegistrada(Boolean valor) { sancionImpedimentoRegistrada = valor; }
    public Boolean getEstructuraProgramaEducativoComprobada() { return estructuraProgramaEducativoComprobada; }
    public void setEstructuraProgramaEducativoComprobada(Boolean valor) { estructuraProgramaEducativoComprobada = valor; }
    public Boolean getRestriccionPeNormativaConocida() { return restriccionPeNormativaConocida; }
    public void setRestriccionPeNormativaConocida(Boolean valor) { restriccionPeNormativaConocida = valor; }
    public Boolean getRestriccionPeNormativaImpeditiva() { return restriccionPeNormativaImpeditiva; }
    public void setRestriccionPeNormativaImpeditiva(Boolean valor) { restriccionPeNormativaImpeditiva = valor; }
    public String getCodigoRestriccionPeNormativa() { return codigoRestriccionPeNormativa; }
    public void setCodigoRestriccionPeNormativa(String valor) { codigoRestriccionPeNormativa = valor; }
    public String getFuenteRestriccionPeNormativa() { return fuenteRestriccionPeNormativa; }
    public void setFuenteRestriccionPeNormativa(String valor) { fuenteRestriccionPeNormativa = valor; }
    public Boolean getAdmitido() { return admitido; } public void setAdmitido(Boolean valor) { admitido = valor; }
    public Boolean getInscripcionInicialRealizada() { return inscripcionInicialRealizada; } public void setInscripcionInicialRealizada(Boolean valor) { inscripcionInicialRealizada = valor; }
    public String getPeriodoAdmision() { return periodoAdmision; } public void setPeriodoAdmision(String valor) { periodoAdmision = valor; }
    public String getPeriodoIncorporacion() { return periodoIncorporacion; } public void setPeriodoIncorporacion(String valor) { periodoIncorporacion = valor; }
    public Boolean getHaCursadoUnoOMasPeriodos() { return haCursadoUnoOMasPeriodos; }
    public void setHaCursadoUnoOMasPeriodos(Boolean valor) { haCursadoUnoOMasPeriodos = valor; }
    public Boolean getNoCursoPeriodoInmediatoSiguiente() { return noCursoPeriodoInmediatoSiguiente; }
    public void setNoCursoPeriodoInmediatoSiguiente(Boolean valor) { noCursoPeriodoInmediatoSiguiente = valor; }
    public Boolean getCausaInterrupcionConocida() { return causaInterrupcionConocida; }
    public void setCausaInterrupcionConocida(Boolean valor) { causaInterrupcionConocida = valor; }
    public Boolean getLimiteInactividadComprobado() { return limiteInactividadComprobado; }
    public void setLimiteInactividadComprobado(Boolean valor) { limiteInactividadComprobado = valor; }
    public String getCausaInterrupcion() { return causaInterrupcion; }
    public void setCausaInterrupcion(String valor) { causaInterrupcion = valor; }
    public String getUltimoPeriodoCursado() { return ultimoPeriodoCursado; }
    public void setUltimoPeriodoCursado(String valor) { ultimoPeriodoCursado = valor; }
    public String getPrimerPeriodoOmitido() { return primerPeriodoOmitido; }
    public void setPrimerPeriodoOmitido(String valor) { primerPeriodoOmitido = valor; }
    public List<String> getPeriodosOmitidos() { return periodosOmitidos; }
    public void setPeriodosOmitidos(List<String> valores) { periodosOmitidos = valores == null ? new ArrayList<String>() : valores; }
    public String getPeriodoReincorporacion() { return periodoReincorporacion; }
    public void setPeriodoReincorporacion(String valor) { periodoReincorporacion = valor; }
    public TipoProcesoReincorporacionV2 getTipoProcesoReincorporacion() { return tipoProcesoReincorporacion; }
    public void setTipoProcesoReincorporacion(TipoProcesoReincorporacionV2 valor) { tipoProcesoReincorporacion = valor == null ? TipoProcesoReincorporacionV2.NO_DETERMINADO : valor; }
    public Date getFechaEvaluacion() { return fechaEvaluacion; }
    public void setFechaEvaluacion(Date fechaEvaluacion) { this.fechaEvaluacion = fechaEvaluacion; }
    public List<UnidadEvaluacionCurricularV2DTO> getUnidades() { return unidades; }
    public void setUnidades(List<UnidadEvaluacionCurricularV2DTO> unidades) {
        this.unidades = unidades == null ? new ArrayList<UnidadEvaluacionCurricularV2DTO>() : unidades;
    }
}
