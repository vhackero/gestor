package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

/** Resultado descriptivo de RN-1.4; no bloquea una UD productiva. */
public enum EstadoLimiteIntentosV2 {
    DISPONIBLE,
    /** Aún existe una oportunidad posterior, sin cursamiento vigente. */
    ULTIMA_DISPONIBLE,
    /** La última oportunidad está inscrita y pendiente de resultado. */
    ULTIMA_EN_CURSO,
    AGOTADA,
    /* Alias de compatibilidad para resultados históricos anteriores. */
    ULTIMA_OPORTUNIDAD,
    LIMITE_AGOTADO,
    REQUIERE_REVISION,
    NO_DETERMINADO,
    ACREDITADA_SIN_OPORTUNIDAD_REQUERIDA
}
