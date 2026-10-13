package mx.gob.sedesol.basegestor.commons.utils;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.ConsultaActaEventoDTO;

/** Filtros de consulta. Las incidencias pueden coexistir en un mismo grupo. */
public enum EstatusConsultaActaEnum {
	TODAS(-1, "Todos los casos"),
	ABIERTA(0, "Abierta"),
	CERRADA(1, "Cerrada"),
	CIERRE_INCOMPLETO(2, "Cierre incompleto"),
	DOCUMENTOS_MULTIPLES(3, "Más de un documento cargado"),
	CERRADA_SIN_DOCUMENTO(4, "Cerrada sin documento"),
	ABIERTA_CON_DOCUMENTO(5, "Abierta con documento"),
	SIN_ALUMNOS(6, "Sin alumnos evaluables"),
	DOCUMENTO_INVALIDO(7, "Documento vacío o inválido"),
	CIERRE_SIN_DEFINIR(8, "Estado de cierre sin definir");

	private final int id;
	private final String nombre;

	EstatusConsultaActaEnum(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public int getId() { return id; }
	public String getNombre() { return nombre; }

	public boolean incluye(ConsultaActaEventoDTO grupo) {
		boolean cerrado = Boolean.TRUE.equals(grupo.getActaCerrada());
		boolean abierto = Boolean.FALSE.equals(grupo.getActaCerrada());
		switch (this) {
		case TODAS: return true;
		case ABIERTA: return abierto && grupo.getNumeroActas() == 0;
		case CERRADA: return cerrado && grupo.getNumeroActas() == 1
				&& grupo.getDocumentosInvalidos() == 0 && grupo.getNumeroAlumnos() > 0
				&& grupo.getCalificacionesPendientes() == 0;
		case CIERRE_INCOMPLETO: return cerrado && grupo.getCalificacionesPendientes() > 0;
		case DOCUMENTOS_MULTIPLES: return grupo.getNumeroActas() > 1;
		case CERRADA_SIN_DOCUMENTO: return cerrado && grupo.getNumeroActas() == 0;
		case ABIERTA_CON_DOCUMENTO: return abierto && grupo.getNumeroActas() > 0;
		case SIN_ALUMNOS: return grupo.getNumeroAlumnos() == 0;
		case DOCUMENTO_INVALIDO: return grupo.getDocumentosInvalidos() > 0;
		case CIERRE_SIN_DEFINIR: return grupo.getActaCerrada() == null;
		default: return false;
		}
	}

	public static EstatusConsultaActaEnum porId(Integer id) {
		for (EstatusConsultaActaEnum estatus : values()) {
			if (id != null && estatus.id == id.intValue()) { return estatus; }
		}
		throw new IllegalArgumentException("El estatus de consulta de actas no es válido.");
	}
}
