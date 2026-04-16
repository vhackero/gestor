package mx.gob.sedesol.basegestor.commons.dto.gestionescolar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticoAcademicoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean regular;
    private Integer materiasEnCurso;
    private Integer materiasReprobadasActivas;
    private Integer materiasConBaja;
    private Integer materiasPendientesNoInscritas;
    private Integer materiasCriticas;
    private Integer materiasBloqueadasPorSeriacion;
    private String resumen;
    private List<String> materiasEnCursoDetalle;
    private List<String> alertas;

    public DiagnosticoAcademicoDTO() {
        this.materiasEnCursoDetalle = new ArrayList<String>();
        this.alertas = new ArrayList<String>();
    }

    public Boolean getRegular() {
        return regular;
    }

    public void setRegular(Boolean regular) {
        this.regular = regular;
    }

    public Integer getMateriasEnCurso() {
        return materiasEnCurso;
    }

    public void setMateriasEnCurso(Integer materiasEnCurso) {
        this.materiasEnCurso = materiasEnCurso;
    }

    public Integer getMateriasReprobadasActivas() {
        return materiasReprobadasActivas;
    }

    public void setMateriasReprobadasActivas(Integer materiasReprobadasActivas) {
        this.materiasReprobadasActivas = materiasReprobadasActivas;
    }

    public Integer getMateriasConBaja() {
        return materiasConBaja;
    }

    public void setMateriasConBaja(Integer materiasConBaja) {
        this.materiasConBaja = materiasConBaja;
    }

    public Integer getMateriasPendientesNoInscritas() {
        return materiasPendientesNoInscritas;
    }

    public void setMateriasPendientesNoInscritas(Integer materiasPendientesNoInscritas) {
        this.materiasPendientesNoInscritas = materiasPendientesNoInscritas;
    }

    public Integer getMateriasCriticas() {
        return materiasCriticas;
    }

    public void setMateriasCriticas(Integer materiasCriticas) {
        this.materiasCriticas = materiasCriticas;
    }

    public Integer getMateriasBloqueadasPorSeriacion() {
        return materiasBloqueadasPorSeriacion;
    }

    public void setMateriasBloqueadasPorSeriacion(Integer materiasBloqueadasPorSeriacion) {
        this.materiasBloqueadasPorSeriacion = materiasBloqueadasPorSeriacion;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public List<String> getMateriasEnCursoDetalle() {
        return materiasEnCursoDetalle;
    }

    public void setMateriasEnCursoDetalle(List<String> materiasEnCursoDetalle) {
        this.materiasEnCursoDetalle = materiasEnCursoDetalle;
    }

    public List<String> getAlertas() {
        return alertas;
    }

    public void setAlertas(List<String> alertas) {
        this.alertas = alertas;
    }
}
