package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;

public class CasoSimilarDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long idCasoRelacion;
    private String folioCasoRelacion;
    private Double puntajeSimilitud;
    private String motivoRelacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCasoRelacion() {
        return idCasoRelacion;
    }

    public void setIdCasoRelacion(Long idCasoRelacion) {
        this.idCasoRelacion = idCasoRelacion;
    }

    public String getFolioCasoRelacion() {
        return folioCasoRelacion;
    }

    public void setFolioCasoRelacion(String folioCasoRelacion) {
        this.folioCasoRelacion = folioCasoRelacion;
    }

    public Double getPuntajeSimilitud() {
        return puntajeSimilitud;
    }

    public void setPuntajeSimilitud(Double puntajeSimilitud) {
        this.puntajeSimilitud = puntajeSimilitud;
    }

    public String getMotivoRelacion() {
        return motivoRelacion;
    }

    public void setMotivoRelacion(String motivoRelacion) {
        this.motivoRelacion = motivoRelacion;
    }
}
