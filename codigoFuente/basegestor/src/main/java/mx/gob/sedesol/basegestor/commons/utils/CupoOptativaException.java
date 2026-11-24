package mx.gob.sedesol.basegestor.commons.utils;

public class CupoOptativaException extends IllegalArgumentException {
    private static final long serialVersionUID = 1L;
    private final boolean faltaConfiguracion;

    public CupoOptativaException(String mensaje, boolean faltaConfiguracion) {
        super(mensaje);
        this.faltaConfiguracion = faltaConfiguracion;
    }

    public String getMomentoEvaluacion() {
        return faltaConfiguracion ? "CUPO_OPTATIVAS_SIN_CONFIGURAR" : "FALTA_CUPO_OPTATIVAS";
    }
}
