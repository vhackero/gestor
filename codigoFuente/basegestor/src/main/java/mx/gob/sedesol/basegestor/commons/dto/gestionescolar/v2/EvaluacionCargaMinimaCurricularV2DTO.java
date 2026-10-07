package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Evaluación semestral de RN-4.1. Conserva candidatos, descartes y excepción
 * sin modificar oferta, seleccionabilidad ni carga del proceso vigente.
 */
public class EvaluacionCargaMinimaCurricularV2DTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean aplicaCargaMinimaNormativa;
    private Integer cargaMinimaNormativa;
    private Integer cargaMinimaEvaluada;
    private Integer cantidadObligatoriasElegibles = Integer.valueOf(0);
    private Integer cantidadOptativasElegibles = Integer.valueOf(0);
    private Integer cantidadTotalElegible = Integer.valueOf(0);
    private Boolean cargaMinimaAlcanzable;
    private Boolean excepcionCargaMinimaAplicable = Boolean.FALSE;
    private EstadoCargaMinimaCurricularV2 estado = EstadoCargaMinimaCurricularV2.NO_DETERMINADO;
    private List<Long> udsObligatoriasConsideradas = new ArrayList<Long>();
    private List<Long> udsOptativasConsideradas = new ArrayList<Long>();
    private List<String> udsDescartadas = new ArrayList<String>();
    private List<String> evidencias = new ArrayList<String>();
    private List<CodigoMotivoCurricularV2> motivos = new ArrayList<CodigoMotivoCurricularV2>();
    private List<CodigoReglaCurricularV2> reglasRelacionadas = new ArrayList<CodigoReglaCurricularV2>();

    public Boolean getAplicaCargaMinimaNormativa() { return aplicaCargaMinimaNormativa; }
    public void setAplicaCargaMinimaNormativa(Boolean valor) { aplicaCargaMinimaNormativa = valor; }
    public Integer getCargaMinimaNormativa() { return cargaMinimaNormativa; }
    public void setCargaMinimaNormativa(Integer valor) { cargaMinimaNormativa = valor; }
    public Integer getCargaMinimaEvaluada() { return cargaMinimaEvaluada; }
    public void setCargaMinimaEvaluada(Integer valor) { cargaMinimaEvaluada = valor; }
    public Integer getCantidadObligatoriasElegibles() { return cantidadObligatoriasElegibles; }
    public void setCantidadObligatoriasElegibles(Integer valor) { cantidadObligatoriasElegibles = valor; }
    public Integer getCantidadOptativasElegibles() { return cantidadOptativasElegibles; }
    public void setCantidadOptativasElegibles(Integer valor) { cantidadOptativasElegibles = valor; }
    public Integer getCantidadTotalElegible() { return cantidadTotalElegible; }
    public void setCantidadTotalElegible(Integer valor) { cantidadTotalElegible = valor; }
    public Boolean getCargaMinimaAlcanzable() { return cargaMinimaAlcanzable; }
    public void setCargaMinimaAlcanzable(Boolean valor) { cargaMinimaAlcanzable = valor; }
    public Boolean getExcepcionCargaMinimaAplicable() { return excepcionCargaMinimaAplicable; }
    public void setExcepcionCargaMinimaAplicable(Boolean valor) { excepcionCargaMinimaAplicable = valor; }
    public EstadoCargaMinimaCurricularV2 getEstado() { return estado; }
    public void setEstado(EstadoCargaMinimaCurricularV2 valor) { estado = valor; }
    public List<Long> getUdsObligatoriasConsideradas() { return udsObligatoriasConsideradas; }
    public List<Long> getUdsOptativasConsideradas() { return udsOptativasConsideradas; }
    public List<String> getUdsDescartadas() { return udsDescartadas; }
    public List<String> getEvidencias() { return evidencias; }
    public List<CodigoMotivoCurricularV2> getMotivos() { return motivos; }
    public List<CodigoReglaCurricularV2> getReglasRelacionadas() { return reglasRelacionadas; }
}
