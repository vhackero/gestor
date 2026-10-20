package mx.gob.sedesol.basegestor.commons.dto.admin;

/** Clave y parámetros para mensajes administrables; no incorpora valores sensibles. */
public class ErrorAsignacionPlan extends IllegalArgumentException {
    private final Object[] parametros;
    public ErrorAsignacionPlan(String clave,Object... parametros){super(clave);this.parametros=parametros;}
    public Object[] getParametros(){return parametros;}
}
