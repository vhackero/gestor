package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

/** Resultado descriptivo RN-6.8 al cierre; no ejecuta promoción ni reinscripción. */
public enum EstadoActualizacionTrayectoriaRn68V2 {
    NO_APLICA_ESPECIFICO_NON_PAR,
    RECALCULO_REALIZADO_EN_SOMBRA,
    REGULARIZACION_RECONOCIDA_EN_SOMBRA,
    CONTINUIDAD_A_SEMESTRE_PAR_PROCEDE_EN_SOMBRA,
    CONTINUIDAD_CON_PRIORIDAD_REGULARIZACION,
    RESTRINGIDA_POR_RN64,
    REQUIERE_REVISION,
    NO_DETERMINADO
}
