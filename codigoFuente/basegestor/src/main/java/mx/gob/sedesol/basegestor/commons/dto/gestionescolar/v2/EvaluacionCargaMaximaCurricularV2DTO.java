package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Propuesta explicativa de RN-4.2. Las listas no representan una selección
 * productiva y sólo permiten explicar el máximo aplicable en modo sombra.
 */
public class EvaluacionCargaMaximaCurricularV2DTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String situacionAcademicaUtilizada;
    private Integer cargaMaximaNormativa;
    private Integer cargaActualEvaluada = Integer.valueOf(0);
    private Integer cargaDisponible;
    private Integer cantidadPrioritarias = Integer.valueOf(0);
    private Integer cantidadCompatibles = Integer.valueOf(0);
    private Integer cantidadOptativas = Integer.valueOf(0);
    private Boolean excedeMaximo;
    private Boolean requiereRevisionPorEmpate = Boolean.FALSE;
    private EstadoCargaMaximaCurricularV2 estado = EstadoCargaMaximaCurricularV2.NO_DETERMINADO;
    private List<Long> udsPrioritarias = new ArrayList<Long>();
    private List<Long> udsIncluidasSombra = new ArrayList<Long>();
    private List<String> udsFueraPorMaximo = new ArrayList<String>();
    private List<String> udsDescartadas = new ArrayList<String>();
    private List<CodigoMotivoCurricularV2> motivos = new ArrayList<CodigoMotivoCurricularV2>();
    private List<CodigoReglaCurricularV2> reglasRelacionadas = new ArrayList<CodigoReglaCurricularV2>();
    private List<String> evidencias = new ArrayList<String>();

    public String getSituacionAcademicaUtilizada() { return situacionAcademicaUtilizada; }
    public void setSituacionAcademicaUtilizada(String valor) { situacionAcademicaUtilizada = valor; }
    public Integer getCargaMaximaNormativa() { return cargaMaximaNormativa; }
    public void setCargaMaximaNormativa(Integer valor) { cargaMaximaNormativa = valor; }
    public Integer getCargaActualEvaluada() { return cargaActualEvaluada; }
    public void setCargaActualEvaluada(Integer valor) { cargaActualEvaluada = valor; }
    public Integer getCargaDisponible() { return cargaDisponible; }
    public void setCargaDisponible(Integer valor) { cargaDisponible = valor; }
    public Integer getCantidadPrioritarias() { return cantidadPrioritarias; }
    public void setCantidadPrioritarias(Integer valor) { cantidadPrioritarias = valor; }
    public Integer getCantidadCompatibles() { return cantidadCompatibles; }
    public void setCantidadCompatibles(Integer valor) { cantidadCompatibles = valor; }
    public Integer getCantidadOptativas() { return cantidadOptativas; }
    public void setCantidadOptativas(Integer valor) { cantidadOptativas = valor; }
    public Boolean getExcedeMaximo() { return excedeMaximo; }
    public void setExcedeMaximo(Boolean valor) { excedeMaximo = valor; }
    public Boolean getRequiereRevisionPorEmpate() { return requiereRevisionPorEmpate; }
    public void setRequiereRevisionPorEmpate(Boolean valor) { requiereRevisionPorEmpate = valor; }
    public EstadoCargaMaximaCurricularV2 getEstado() { return estado; }
    public void setEstado(EstadoCargaMaximaCurricularV2 valor) { estado = valor; }
    public List<Long> getUdsPrioritarias() { return udsPrioritarias; }
    public List<Long> getUdsIncluidasSombra() { return udsIncluidasSombra; }
    public List<String> getUdsFueraPorMaximo() { return udsFueraPorMaximo; }
    public List<String> getUdsDescartadas() { return udsDescartadas; }
    public List<CodigoMotivoCurricularV2> getMotivos() { return motivos; }
    public List<CodigoReglaCurricularV2> getReglasRelacionadas() { return reglasRelacionadas; }
    public List<String> getEvidencias() { return evidencias; }
}
