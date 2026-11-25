package mx.gob.sedesol.basegestor.service.impl.admin;

import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.SQLException;

public class ImportacionFuenteErroresTest {
    @Test public void informaCampoObligatorioDesdeExcepcionAnidada() {
        String detalle=ImportacionUsuariosExternosService.describirError(new RuntimeException("Error de persistencia",
            new SQLException("Column 'sso_email' cannot be null", "23000",1048)));
        assertTrue(detalle.contains("obligatorio"));assertTrue(detalle.contains("sso_email"));
        assertTrue(detalle.contains("1048"));
    }
    @Test public void duplicadoNoExponeValorDeLaFila() {
        String detalle=ImportacionUsuariosExternosService.describirError(new RuntimeException(
            new SQLException("Duplicate entry 'Secreto2026' for key 'usuario_unico'", "23000",1062)));
        assertTrue(detalle.contains("duplicado"));assertTrue(detalle.contains("usuario_unico"));
        assertFalse(detalle.contains("Secreto2026"));
    }
    @Test public void informaCausaRaizYOmiteConsultasYCredenciales() {
        String detalle=ImportacionUsuariosExternosService.describirError(new RuntimeException("Wrapper",
            new IllegalStateException("Sin sesión; password=Secreto; insert into usuarios values ('OtroSecreto')")));
        assertTrue(detalle.contains("IllegalStateException"));assertTrue(detalle.contains("Sin sesión"));
        assertFalse(detalle.contains("Secreto"));assertFalse(detalle.contains("values"));
    }
}
