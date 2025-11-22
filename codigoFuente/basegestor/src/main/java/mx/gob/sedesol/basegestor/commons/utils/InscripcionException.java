package mx.gob.sedesol.basegestor.commons.utils;

public class InscripcionException extends RuntimeException {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 3218425219266513839L;

	public InscripcionException() {
        super();
    }
    
    public InscripcionException(String mensaje) {
        super(mensaje);
    }
    
    public InscripcionException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
    public InscripcionException(Throwable causa) {
        super(causa);
    }
}
