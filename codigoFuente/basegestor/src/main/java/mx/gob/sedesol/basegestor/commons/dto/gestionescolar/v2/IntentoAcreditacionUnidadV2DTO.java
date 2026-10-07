package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

import java.io.Serializable;

/** Evidencia técnica de un intento; evita usar bloque/grupo como identidad por sí solos. */
public class IntentoAcreditacionUnidadV2DTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String idEjecucionTecnico;
    private Long idPeriodo;
    private Long idGrupo;
    private Long idParticipacion;
    private EstadoIntentoAcreditacionV2 estado;
    private String evidenciaFuente;
    public String getIdEjecucionTecnico() { return idEjecucionTecnico; }
    public void setIdEjecucionTecnico(String valor) { idEjecucionTecnico = valor; }
    public Long getIdPeriodo() { return idPeriodo; }
    public void setIdPeriodo(Long valor) { idPeriodo = valor; }
    public Long getIdGrupo() { return idGrupo; }
    public void setIdGrupo(Long valor) { idGrupo = valor; }
    public Long getIdParticipacion() { return idParticipacion; }
    public void setIdParticipacion(Long valor) { idParticipacion = valor; }
    public EstadoIntentoAcreditacionV2 getEstado() { return estado; }
    public void setEstado(EstadoIntentoAcreditacionV2 valor) { estado = valor; }
    public String getEvidenciaFuente() { return evidenciaFuente; }
    public void setEvidenciaFuente(String valor) { evidenciaFuente = valor; }
}
