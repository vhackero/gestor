package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EscenarioCargaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tipoEscenario;
    private String nombre;
    private String descripcion;

    private Integer totalMaterias;
    private Integer totalCreditos;

    private String riesgoGeneral;
    private String beneficioPrincipal;

    private List<Long> unidadesIds;
    private List<String> razones;

    public EscenarioCargaDTO() {
        this.unidadesIds = new ArrayList<Long>();
        this.razones = new ArrayList<String>();
    }

    public String getTipoEscenario() {
        return tipoEscenario;
    }

    public void setTipoEscenario(String tipoEscenario) {
        this.tipoEscenario = tipoEscenario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getTotalMaterias() {
        return totalMaterias;
    }

    public void setTotalMaterias(Integer totalMaterias) {
        this.totalMaterias = totalMaterias;
    }

    public Integer getTotalCreditos() {
        return totalCreditos;
    }

    public void setTotalCreditos(Integer totalCreditos) {
        this.totalCreditos = totalCreditos;
    }

    public String getRiesgoGeneral() {
        return riesgoGeneral;
    }

    public void setRiesgoGeneral(String riesgoGeneral) {
        this.riesgoGeneral = riesgoGeneral;
    }

    public String getBeneficioPrincipal() {
        return beneficioPrincipal;
    }

    public void setBeneficioPrincipal(String beneficioPrincipal) {
        this.beneficioPrincipal = beneficioPrincipal;
    }

    public List<Long> getUnidadesIds() {
        return unidadesIds;
    }

    public void setUnidadesIds(List<Long> unidadesIds) {
        this.unidadesIds = unidadesIds;
    }

    public List<String> getRazones() {
        return razones;
    }

    public void setRazones(List<String> razones) {
        this.razones = razones;
    }
}
