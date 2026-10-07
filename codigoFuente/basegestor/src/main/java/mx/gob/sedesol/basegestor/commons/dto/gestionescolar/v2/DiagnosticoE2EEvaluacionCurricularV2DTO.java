package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;

/** Salida técnica sin PII para validar una evaluación V2 real en DEV/QA. */
public class DiagnosticoE2EEvaluacionCurricularV2DTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idPersonaTecnico;
    private Long idEvaluacion;
    private String hashContexto;
    private Integer unidadesEvaluadas;
    private Integer reglasEvaluadas;
    private Integer excepcionesEvaluadas;
    private Boolean auditoriaReconstruida;
    private Boolean inmutabilidadVerificada;
    private String resultado;
    public Long getIdPersonaTecnico(){return idPersonaTecnico;} public void setIdPersonaTecnico(Long v){idPersonaTecnico=v;}
    public Long getIdEvaluacion(){return idEvaluacion;} public void setIdEvaluacion(Long v){idEvaluacion=v;}
    public String getHashContexto(){return hashContexto;} public void setHashContexto(String v){hashContexto=v;}
    public Integer getUnidadesEvaluadas(){return unidadesEvaluadas;} public void setUnidadesEvaluadas(Integer v){unidadesEvaluadas=v;}
    public Integer getReglasEvaluadas(){return reglasEvaluadas;} public void setReglasEvaluadas(Integer v){reglasEvaluadas=v;}
    public Integer getExcepcionesEvaluadas(){return excepcionesEvaluadas;} public void setExcepcionesEvaluadas(Integer v){excepcionesEvaluadas=v;}
    public Boolean getAuditoriaReconstruida(){return auditoriaReconstruida;} public void setAuditoriaReconstruida(Boolean v){auditoriaReconstruida=v;}
    public Boolean getInmutabilidadVerificada(){return inmutabilidadVerificada;} public void setInmutabilidadVerificada(Boolean v){inmutabilidadVerificada=v;}
    public String getResultado(){return resultado;} public void setResultado(String v){resultado=v;}
}
