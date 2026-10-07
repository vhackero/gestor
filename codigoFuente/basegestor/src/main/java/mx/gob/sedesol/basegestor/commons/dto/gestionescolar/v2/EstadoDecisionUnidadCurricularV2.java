package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

/**
 * Estados descriptivos de una decisión V2 en sombra. No sustituyen la
 * seleccionabilidad ni los bloqueos del proceso vigente.
 */
public enum EstadoDecisionUnidadCurricularV2 {
    HABILITADA,
    BLOQUEADA,
    PRIORITARIA,
    REQUIERE_REVISION
}
