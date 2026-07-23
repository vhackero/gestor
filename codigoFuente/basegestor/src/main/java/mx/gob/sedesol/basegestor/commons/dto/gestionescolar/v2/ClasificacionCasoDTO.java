package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClasificacionCasoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private TipoCasoAcademicoDTO tipoCaso;
    private MotivoRestriccionDTO motivoPrincipal;
    private Double confianza;
    private Boolean requiereIntervencionHumana;
    private Boolean operable;
    private String estatusSugerido;
    private List<String> subcausas;
    private List<String> observaciones;

    public ClasificacionCasoDTO() {
        this.subcausas = new ArrayList<String>();
        this.observaciones = new ArrayList<String>();
    }

    public TipoCasoAcademicoDTO getTipoCaso() {
        return tipoCaso;
    }

    public void setTipoCaso(TipoCasoAcademicoDTO tipoCaso) {
        this.tipoCaso = tipoCaso;
    }

    public MotivoRestriccionDTO getMotivoPrincipal() {
        return motivoPrincipal;
    }

    public void setMotivoPrincipal(MotivoRestriccionDTO motivoPrincipal) {
        this.motivoPrincipal = motivoPrincipal;
    }

    public Double getConfianza() {
        return confianza;
    }

    public void setConfianza(Double confianza) {
        this.confianza = confianza;
    }

    public Boolean getRequiereIntervencionHumana() {
        return requiereIntervencionHumana;
    }

    public void setRequiereIntervencionHumana(Boolean requiereIntervencionHumana) {
        this.requiereIntervencionHumana = requiereIntervencionHumana;
    }

    public Boolean getOperable() {
        return operable;
    }

    public void setOperable(Boolean operable) {
        this.operable = operable;
    }

    public String getEstatusSugerido() {
        return estatusSugerido;
    }

    public void setEstatusSugerido(String estatusSugerido) {
        this.estatusSugerido = estatusSugerido;
    }

    public List<String> getSubcausas() {
        return subcausas;
    }

    public void setSubcausas(List<String> subcausas) {
        this.subcausas = subcausas;
    }

    public List<String> getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(List<String> observaciones) {
        this.observaciones = observaciones;
    }
}
