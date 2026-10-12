package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

/** Resultado RN-9.3 en sombra; conserva restricciones concurrentes. */
public enum EstadoAvanceUltimosSemestresV2 {
    NO_APLICA,
    AVANCE_ULTIMOS_SEMESTRES_CUMPLE,
    RESTRINGIDO_POR_CREDITOS,
    RESTRINGIDO_POR_SERIACION,
    RESTRINGIDO_POR_RN64,
    RESTRINGIDO_POR_REQUISITO_PE,
    CUMPLE_CON_RESTRICCIONES_CONCURRENTES,
    REQUIERE_REVISION,
    NO_DETERMINADO
}
