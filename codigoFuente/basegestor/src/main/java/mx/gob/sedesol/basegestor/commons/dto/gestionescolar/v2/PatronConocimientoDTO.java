package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PatronConocimientoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private TipoCasoAcademicoDTO tipoCaso;
    private String nombrePatron;
    private String descripcion;
    private Double confianzaBase;
    private Boolean activo;
    private List<CriterioOperativoDTO> criterios;
    private List<AccionOperativaDTO> acciones;
    private List<MensajeInstitucionalContextualDTO> mensajes;

    public PatronConocimientoDTO() {
        this.criterios = new ArrayList<CriterioOperativoDTO>();
        this.acciones = new ArrayList<AccionOperativaDTO>();
        this.mensajes = new ArrayList<MensajeInstitucionalContextualDTO>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoCasoAcademicoDTO getTipoCaso() {
        return tipoCaso;
    }

    public void setTipoCaso(TipoCasoAcademicoDTO tipoCaso) {
        this.tipoCaso = tipoCaso;
    }

    public String getNombrePatron() {
        return nombrePatron;
    }

    public void setNombrePatron(String nombrePatron) {
        this.nombrePatron = nombrePatron;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getConfianzaBase() {
        return confianzaBase;
    }

    public void setConfianzaBase(Double confianzaBase) {
        this.confianzaBase = confianzaBase;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<CriterioOperativoDTO> getCriterios() {
        return criterios;
    }

    public void setCriterios(List<CriterioOperativoDTO> criterios) {
        this.criterios = criterios;
    }

    public List<AccionOperativaDTO> getAcciones() {
        return acciones;
    }

    public void setAcciones(List<AccionOperativaDTO> acciones) {
        this.acciones = acciones;
    }

    public List<MensajeInstitucionalContextualDTO> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<MensajeInstitucionalContextualDTO> mensajes) {
        this.mensajes = mensajes;
    }
}
