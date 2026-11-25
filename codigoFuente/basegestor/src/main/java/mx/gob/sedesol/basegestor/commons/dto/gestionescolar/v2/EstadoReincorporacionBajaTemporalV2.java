package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

/** Resultado descriptivo RN-6.6; no autoriza ni ejecuta una reincorporacion. */
public enum EstadoReincorporacionBajaTemporalV2 {
    NO_APLICA,
    BAJA_TEMPORAL_AUN_VIGENTE,
    REINCORPORACION_PROCEDE_EN_SOMBRA,
    REINCORPORACION_PROCEDE_CON_RUTA_REGULARIZACION,
    REINCORPORACION_SUJETA_RN61,
    REINCORPORACION_RESTRINGIDA_RN64,
    MAXIMO_BAJAS_TEMPORALES_EXCEDIDO,
    NO_PROCEDE_POR_PERMANENCIA,
    REQUIERE_REVISION,
    NO_DETERMINADO
}
