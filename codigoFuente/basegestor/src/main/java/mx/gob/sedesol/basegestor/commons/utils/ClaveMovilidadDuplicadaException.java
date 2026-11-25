package mx.gob.sedesol.basegestor.commons.utils;

/** Conflicto de clave informativa; permite informar el programa dueño de la clave. */
public class ClaveMovilidadDuplicadaException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public ClaveMovilidadDuplicadaException(String mensaje) { super(mensaje); }
	public ClaveMovilidadDuplicadaException(String mensaje, Throwable causa) { super(mensaje, causa); }
}
