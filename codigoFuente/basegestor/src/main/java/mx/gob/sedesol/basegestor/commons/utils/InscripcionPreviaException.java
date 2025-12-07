package mx.gob.sedesol.basegestor.commons.utils;

public class InscripcionPreviaException extends InscripcionException {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 3218425219266513839L;

	public InscripcionPreviaException() {
        super();
    }
    
    public InscripcionPreviaException(String mensaje) {
        super(mensaje);
    }
    
    public InscripcionPreviaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
    public InscripcionPreviaException(Throwable causa) {
        super(causa);
    }
}
