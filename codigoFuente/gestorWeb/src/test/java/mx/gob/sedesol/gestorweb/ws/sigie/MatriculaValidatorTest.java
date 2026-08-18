package mx.gob.sedesol.gestorweb.ws.sigie;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class MatriculaValidatorTest {
    private final MatriculaValidator validator = new MatriculaValidator();

    @Test public void aceptaFormatoInstitucional() { assertTrue(validator.esValida("ES241100064")); }
    @Test public void rechazaVacia() { assertFalse(validator.esValida("")); }
    @Test public void rechazaNula() { assertFalse(validator.esValida(null)); }
    @Test public void rechazaDemasiadoLarga() { assertFalse(validator.esValida("ES2411000640")); }
    @Test public void rechazaCaracteresInvalidos() { assertFalse(validator.esValida("ES24110A064")); }
    @Test public void noNormalizaMinusculas() { assertFalse(validator.esValida("es241100064")); }
}
