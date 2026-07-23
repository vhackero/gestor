package mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbl_caso_diagnostico")
@NamedQuery(name = "TblCasoDiagnosticoV2.findAll", query = "SELECT t FROM TblCasoDiagnosticoV2 t")
public class TblCasoDiagnosticoV2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_caso")
    private Long idCaso;

    @Column(name = "situacion_academica")
    private String situacionAcademica;

    @Column(name = "riesgo_actual")
    private String riesgoActual;

    @Column(name = "riesgo_siguiente_periodo")
    private String riesgoSiguientePeriodo;

    @Column(name = "cierre_anual")
    private String cierreAnual;

    @Column(name = "carga_viable")
    private Integer cargaViable;

    @Column(name = "seriacion_activa")
    private Boolean seriacionActiva;

    @Column(name = "oferta_vigente")
    private Boolean ofertaVigente;

    @Column(name = "dictamen_preliminar")
    private String dictamenPreliminar;

    @Column(name = "resumen_motor")
    private String resumenMotor;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_registro", insertable = false, updatable = false)
    private Date fechaRegistro;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_actualizacion", insertable = false, updatable = false)
    private Date fechaActualizacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(Long idCaso) {
        this.idCaso = idCaso;
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

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
