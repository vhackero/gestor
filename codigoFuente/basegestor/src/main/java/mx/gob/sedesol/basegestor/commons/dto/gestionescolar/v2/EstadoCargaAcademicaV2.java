package mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2;

/** Estado integrado de carga, construido a partir de RN-4.1 y RN-4.2. */
public enum EstadoCargaAcademicaV2 {
    VALIDA,
    VALIDA_CON_EXCEPCION_MINIMO,
    INCOMPLETA_POR_OFERTA,
    INCOMPLETA_POR_CUPO,
    RESTRINGIDA,
    REQUIERE_REVISION,
    NO_DETERMINADA
}
