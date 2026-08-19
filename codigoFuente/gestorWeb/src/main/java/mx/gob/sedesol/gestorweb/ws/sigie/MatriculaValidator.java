package mx.gob.sedesol.gestorweb.ws.sigie;

import java.util.Locale;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class MatriculaValidator {
    private static final Pattern FORMATO = Pattern.compile("^ES[0-9]{9}$", Pattern.CASE_INSENSITIVE);

    public boolean esValida(String matricula) {
        return matricula != null && matricula.length() == 11 && FORMATO.matcher(matricula).matches();
    }

    public String normalizar(String matricula) {
        return matricula == null ? null : matricula.toUpperCase(Locale.ROOT);
    }
}
