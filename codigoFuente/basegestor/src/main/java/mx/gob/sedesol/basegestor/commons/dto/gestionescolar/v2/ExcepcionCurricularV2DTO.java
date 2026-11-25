package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** Evidencia estructurada de una excepción evaluada únicamente en modo sombra. */
public class ExcepcionCurricularV2DTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private CodigoExcepcionCurricularV2 codigo;
    private TipoExcepcionCurricularV2 tipo;
    private CodigoReglaCurricularV2 reglaOrigen;
    private EstadoExcepcionCurricularV2 estado;
    private List<String> condicionesEvaluadas = new ArrayList<String>();
    private List<String> condicionesCumplidas = new ArrayList<String>();
    private List<String> condicionesNoCumplidas = new ArrayList<String>();
    private List<String> evidencias = new ArrayList<String>();
    private List<CodigoMotivoCurricularV2> motivos = new ArrayList<CodigoMotivoCurricularV2>();
    private List<Long> unidadesRelacionadas = new ArrayList<Long>();
    private String explicacionTecnica;
    private Date fechaEvaluacion;
    private String versionNormativa;

    public CodigoExcepcionCurricularV2 getCodigo() { return codigo; }
    public void setCodigo(CodigoExcepcionCurricularV2 codigo) { this.codigo = codigo; }
    public TipoExcepcionCurricularV2 getTipo() { return tipo; }
    public void setTipo(TipoExcepcionCurricularV2 tipo) { this.tipo = tipo; }
    public CodigoReglaCurricularV2 getReglaOrigen() { return reglaOrigen; }
    public void setReglaOrigen(CodigoReglaCurricularV2 reglaOrigen) { this.reglaOrigen = reglaOrigen; }
    public EstadoExcepcionCurricularV2 getEstado() { return estado; }
    public void setEstado(EstadoExcepcionCurricularV2 estado) { this.estado = estado; }
    public List<String> getCondicionesEvaluadas() { return condicionesEvaluadas; }
    public List<String> getCondicionesCumplidas() { return condicionesCumplidas; }
    public List<String> getCondicionesNoCumplidas() { return condicionesNoCumplidas; }
    public List<String> getEvidencias() { return evidencias; }
    public List<CodigoMotivoCurricularV2> getMotivos() { return motivos; }
    public List<Long> getUnidadesRelacionadas() { return unidadesRelacionadas; }
    public String getExplicacionTecnica() { return explicacionTecnica; }
    public void setExplicacionTecnica(String explicacionTecnica) { this.explicacionTecnica = explicacionTecnica; }
    public Date getFechaEvaluacion() { return fechaEvaluacion; }
    public void setFechaEvaluacion(Date fechaEvaluacion) { this.fechaEvaluacion = fechaEvaluacion; }
    public String getVersionNormativa() { return versionNormativa; }
    public void setVersionNormativa(String versionNormativa) { this.versionNormativa = versionNormativa; }
}
