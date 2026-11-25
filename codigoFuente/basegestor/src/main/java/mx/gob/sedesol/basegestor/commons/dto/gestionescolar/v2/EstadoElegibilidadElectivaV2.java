package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

/** Resultado sombra de elegibilidad RN-7.2; no representa una inscripción. */
public enum EstadoElegibilidadElectivaV2 {
    NO_APLICA,
    ELEGIBLE_PARA_ELECTIVAS_EN_SOMBRA,
    NO_ELEGIBLE_POR_SEMESTRE,
    NO_ELEGIBLE_POR_MISMO_PE,
    ELEGIBLE_CON_RESTRICCIONES,
    RESTRINGIDA_POR_RN64,
    REQUIERE_REVISION,
    NO_DETERMINADO
}
