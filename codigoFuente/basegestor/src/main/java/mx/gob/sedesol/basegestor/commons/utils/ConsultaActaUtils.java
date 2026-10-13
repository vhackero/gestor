package mx.gob.sedesol.basegestor.commons.utils;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConsultaActaEventoDTO;

/** Convierte proyecciones escalares; nunca carga documentos para clasificar grupos. */
public final class ConsultaActaUtils {
	private ConsultaActaUtils() { }
	public static ConsultaActaEventoDTO desdeRegistro(Object[] fila, int inicio) {
		ConsultaActaEventoDTO estado = new ConsultaActaEventoDTO();
		estado.setIdGrupo(((Number) fila[inicio]).longValue());
		Object cierre = fila[inicio + 1];
		Boolean cerrado = null;
		if (cierre instanceof Boolean) { cerrado = (Boolean) cierre; }
		else if (cierre instanceof Number) {
			int valor = ((Number) cierre).intValue();
			if (valor == 0 || valor == 1) { cerrado = valor == 1; }
		}
		estado.setActaCerrada(cerrado);
		estado.setNumeroAlumnos(((Number) fila[inicio + 2]).intValue());
		estado.setAlumnosCalificados(((Number) fila[inicio + 3]).intValue());
		estado.setNumeroActas(((Number) fila[inicio + 4]).intValue());
		estado.setDocumentosInvalidos(((Number) fila[inicio + 5]).intValue());
		StringBuilder incidencias = new StringBuilder();
		for (EstatusConsultaActaEnum filtro : EstatusConsultaActaEnum.values()) {
			if (filtro != EstatusConsultaActaEnum.TODAS && filtro.incluye(estado)) {
				if (incidencias.length() > 0) { incidencias.append(" / "); }
				incidencias.append(filtro.getNombre());
			}
		}
		estado.setTextoEstatus(incidencias.toString());
		return estado;
	}
}
