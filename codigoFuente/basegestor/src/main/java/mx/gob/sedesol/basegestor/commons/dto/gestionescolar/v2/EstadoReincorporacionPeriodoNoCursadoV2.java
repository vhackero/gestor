package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

/** Resultado descriptivo RN-6.5; no autoriza ni ejecuta una reinscripcion. */
public enum EstadoReincorporacionPeriodoNoCursadoV2 {
    NO_APLICA,
    REINCORPORACION_PROCEDE_EN_SOMBRA,
    REINCORPORACION_PROCEDE_CON_RUTA_REGULARIZACION,
    REINCORPORACION_SUJETA_RN61,
    REINCORPORACION_RESTRINGIDA_POR_RN64,
    NO_PROCEDE_POR_INACTIVIDAD,
    REQUIERE_REVISION,
    NO_DETERMINADO
}
