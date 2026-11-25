package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

/** Resultado descriptivo RN-6.7; no autoriza ni ejecuta una reinscripcion. */
public enum EstadoReincorporacionBajaParcialV2 {
    NO_APLICA,
    REINCORPORACION_BAJA_PARCIAL_PROCEDE_EN_SOMBRA,
    REINCORPORACION_CON_PRIORIDAD_RECUPERACION,
    REINCORPORACION_CON_RUTA_COMPATIBLE,
    REINCORPORACION_SUJETA_RN61,
    REINCORPORACION_RESTRINGIDA_RN64,
    RESTRINGIDA_POR_SANCION,
    REQUIERE_REVISION,
    NO_DETERMINADO
}
