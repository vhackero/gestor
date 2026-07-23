package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticoAcademicoOperativoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String situacionAcademica;
    private String riesgoActual;
    private String riesgoSiguientePeriodo;
    private String cierreAnual;
    private Integer cargaViable;
    private Boolean seriacionActiva;
    private Boolean ofertaVigente;
    private String dictamenPreliminar;
    private String resumenMotor;
    private String restriccionDominante;
    private String motivoBloqueoPrincipal;
    private String comparativoAvanceRestricciones;
    private String interpretacionOmisiones;
    private Integer totalNoAcreditadas;
    private Integer totalOmisiones;
    private Integer totalBloqueadas;
    private Integer totalPendientesCriticas;
    private List<String> reglasAplicadas;
    private List<String> alertasDiagnostico;
    private List<CasoUdRelacionadaDTO> detallePorUd;

    public DiagnosticoAcademicoOperativoDTO() {
        this.reglasAplicadas = new ArrayList<String>();
        this.alertasDiagnostico = new ArrayList<String>();
        this.detallePorUd = new ArrayList<CasoUdRelacionadaDTO>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSituacionAcademica() {
        return situacionAcademica;
    }

    public void setSituacionAcademica(String situacionAcademica) {
        this.situacionAcademica = situacionAcademica;
    }

    public String getRiesgoActual() {
        return riesgoActual;
    }

    public void setRiesgoActual(String riesgoActual) {
        this.riesgoActual = riesgoActual;
    }

    public String getRiesgoSiguientePeriodo() {
        return riesgoSiguientePeriodo;
    }

    public void setRiesgoSiguientePeriodo(String riesgoSiguientePeriodo) {
        this.riesgoSiguientePeriodo = riesgoSiguientePeriodo;
    }

    public String getCierreAnual() {
        return cierreAnual;
    }

    public void setCierreAnual(String cierreAnual) {
        this.cierreAnual = cierreAnual;
    }

    public Integer getCargaViable() {
        return cargaViable;
    }

    public void setCargaViable(Integer cargaViable) {
        this.cargaViable = cargaViable;
    }

    public Boolean getSeriacionActiva() {
        return seriacionActiva;
    }

    public void setSeriacionActiva(Boolean seriacionActiva) {
        this.seriacionActiva = seriacionActiva;
    }

    public Boolean getOfertaVigente() {
        return ofertaVigente;
    }

    public void setOfertaVigente(Boolean ofertaVigente) {
        this.ofertaVigente = ofertaVigente;
    }

    public String getDictamenPreliminar() {
        return dictamenPreliminar;
    }

    public void setDictamenPreliminar(String dictamenPreliminar) {
        this.dictamenPreliminar = dictamenPreliminar;
    }

    public String getResumenMotor() {
        return resumenMotor;
    }

    public void setResumenMotor(String resumenMotor) {
        this.resumenMotor = resumenMotor;
    }

    public String getRestriccionDominante() {
        return restriccionDominante;
    }

    public void setRestriccionDominante(String restriccionDominante) {
        this.restriccionDominante = restriccionDominante;
    }

    public String getMotivoBloqueoPrincipal() {
        return motivoBloqueoPrincipal;
    }

    public void setMotivoBloqueoPrincipal(String motivoBloqueoPrincipal) {
        this.motivoBloqueoPrincipal = motivoBloqueoPrincipal;
    }

    public String getComparativoAvanceRestricciones() {
        return comparativoAvanceRestricciones;
    }

    public void setComparativoAvanceRestricciones(String comparativoAvanceRestricciones) {
        this.comparativoAvanceRestricciones = comparativoAvanceRestricciones;
    }

    public String getInterpretacionOmisiones() {
        return interpretacionOmisiones;
    }

    public void setInterpretacionOmisiones(String interpretacionOmisiones) {
        this.interpretacionOmisiones = interpretacionOmisiones;
    }

    public Integer getTotalNoAcreditadas() {
        return totalNoAcreditadas;
    }

    public void setTotalNoAcreditadas(Integer totalNoAcreditadas) {
        this.totalNoAcreditadas = totalNoAcreditadas;
    }

    public Integer getTotalOmisiones() {
        return totalOmisiones;
    }

    public void setTotalOmisiones(Integer totalOmisiones) {
        this.totalOmisiones = totalOmisiones;
    }

    public Integer getTotalBloqueadas() {
        return totalBloqueadas;
    }

    public void setTotalBloqueadas(Integer totalBloqueadas) {
        this.totalBloqueadas = totalBloqueadas;
    }

    public Integer getTotalPendientesCriticas() {
        return totalPendientesCriticas;
    }

    public void setTotalPendientesCriticas(Integer totalPendientesCriticas) {
        this.totalPendientesCriticas = totalPendientesCriticas;
    }

    public List<String> getReglasAplicadas() {
        return reglasAplicadas;
    }

    public void setReglasAplicadas(List<String> reglasAplicadas) {
        this.reglasAplicadas = reglasAplicadas;
    }

    public List<String> getAlertasDiagnostico() {
        return alertasDiagnostico;
    }

    public void setAlertasDiagnostico(List<String> alertasDiagnostico) {
        this.alertasDiagnostico = alertasDiagnostico;
    }

    public List<CasoUdRelacionadaDTO> getDetallePorUd() {
        return detallePorUd;
    }

    public void setDetallePorUd(List<CasoUdRelacionadaDTO> detallePorUd) {
        this.detallePorUd = detallePorUd;
    }
}
