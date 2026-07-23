package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.Date;

public class DictamenCasoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private AccionOperativaDTO accionOperativa;
    private ViabilidadTecnicaDTO viabilidadTecnica;
    private String dictamen;
    private String mensajeEstudiante;
    private String mensajeGestor;
    private Boolean requiereEscalamiento;
    private String folioEscalamiento;
    private Long usuarioDictamino;
    private Date fechaDictamen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccionOperativaDTO getAccionOperativa() {
        return accionOperativa;
    }

    public void setAccionOperativa(AccionOperativaDTO accionOperativa) {
        this.accionOperativa = accionOperativa;
    }

    public ViabilidadTecnicaDTO getViabilidadTecnica() {
        return viabilidadTecnica;
    }

    public void setViabilidadTecnica(ViabilidadTecnicaDTO viabilidadTecnica) {
        this.viabilidadTecnica = viabilidadTecnica;
    }

    public String getDictamen() {
        return dictamen;
    }

    public void setDictamen(String dictamen) {
        this.dictamen = dictamen;
    }

    public String getMensajeEstudiante() {
        return mensajeEstudiante;
    }

    public void setMensajeEstudiante(String mensajeEstudiante) {
        this.mensajeEstudiante = mensajeEstudiante;
    }

    public String getMensajeGestor() {
        return mensajeGestor;
    }

    public void setMensajeGestor(String mensajeGestor) {
        this.mensajeGestor = mensajeGestor;
    }

    public Boolean getRequiereEscalamiento() {
        return requiereEscalamiento;
    }

    public void setRequiereEscalamiento(Boolean requiereEscalamiento) {
        this.requiereEscalamiento = requiereEscalamiento;
    }

    public String getFolioEscalamiento() {
        return folioEscalamiento;
    }

    public void setFolioEscalamiento(String folioEscalamiento) {
        this.folioEscalamiento = folioEscalamiento;
    }

    public Long getUsuarioDictamino() {
        return usuarioDictamino;
    }

    public void setUsuarioDictamino(Long usuarioDictamino) {
        this.usuarioDictamino = usuarioDictamino;
    }

    public Date getFechaDictamen() {
        return fechaDictamen;
    }

    public void setFechaDictamen(Date fechaDictamen) {
        this.fechaDictamen = fechaDictamen;
    }
}
