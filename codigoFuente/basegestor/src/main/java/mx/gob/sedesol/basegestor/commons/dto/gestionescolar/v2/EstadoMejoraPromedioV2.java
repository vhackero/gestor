package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

/** Resultado sombra de una solicitud voluntaria de recursamiento por mejora. */
public enum EstadoMejoraPromedioV2 {
    ELEGIBLE_EN_SOMBRA,
    NO_ELEGIBLE,
    SIN_CUPO,
    LIMITE_MEJORA_AGOTADO,
    EGRESO_INICIADO,
    ESTUDIANTE_NO_ACTIVO,
    REQUIERE_REVISION,
    NO_DETERMINADO
}
