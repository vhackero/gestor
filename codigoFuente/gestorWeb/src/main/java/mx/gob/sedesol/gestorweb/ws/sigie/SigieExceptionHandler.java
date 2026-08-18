package mx.gob.sedesol.gestorweb.ws.sigie;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = EstudianteSigieController.class)
public class SigieExceptionHandler {
    private static final Logger LOG = Logger.getLogger(SigieExceptionHandler.class);

    @ExceptionHandler(SigieApiException.class)
    public ResponseEntity<SigieErrorResponse> funcional(SigieApiException error, HttpServletRequest request) {
        return new ResponseEntity<SigieErrorResponse>(new SigieErrorResponse(error.getCodigo(), error.getMessage(), folio(request)),
                error.getStatus());
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<SigieErrorResponse> persistencia(PersistenceException error, HttpServletRequest request) {
        String folio = folio(request);
        LOG.error("folio=" + folio + " resultado=SERVICIO_NO_DISPONIBLE", error);
        return new ResponseEntity<SigieErrorResponse>(new SigieErrorResponse("SERVICIO_NO_DISPONIBLE",
                "El servicio no está disponible temporalmente.", folio), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SigieErrorResponse> interno(Exception error, HttpServletRequest request) {
        String folio = folio(request);
        LOG.error("folio=" + folio + " resultado=ERROR_INTERNO", error);
        return new ResponseEntity<SigieErrorResponse>(new SigieErrorResponse("ERROR_INTERNO",
                "Ocurrió un error interno al procesar la solicitud.", folio), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String folio(HttpServletRequest request) { return SigieRequestContext.preparar(request); }
}
