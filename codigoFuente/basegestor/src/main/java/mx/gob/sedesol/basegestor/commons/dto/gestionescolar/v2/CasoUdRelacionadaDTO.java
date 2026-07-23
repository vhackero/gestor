package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;

public class CasoUdRelacionadaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long idPrograma;
    private String claveUd;
    private String nombreUd;
    private String tipoUd;
    private String bloque;
    private String semestre;
    private String estatusDetectado;
    private Boolean critica;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Long idPrograma) {
        this.idPrograma = idPrograma;
    }

    public String getClaveUd() {
        return claveUd;
    }

    public void setClaveUd(String claveUd) {
        this.claveUd = claveUd;
    }

    public String getNombreUd() {
        return nombreUd;
    }

    public void setNombreUd(String nombreUd) {
        this.nombreUd = nombreUd;
    }

    public String getTipoUd() {
        return tipoUd;
    }

    public void setTipoUd(String tipoUd) {
        this.tipoUd = tipoUd;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getEstatusDetectado() {
        return estatusDetectado;
    }

    public void setEstatusDetectado(String estatusDetectado) {
        this.estatusDetectado = estatusDetectado;
    }

    public Boolean getCritica() {
        return critica;
    }

    public void setCritica(Boolean critica) {
        this.critica = critica;
    }
}
