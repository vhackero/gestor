package mx.gob.sedesol.basegestor.commons.utils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** Formato compartido del parametro de bajas que se representan como NP. */
public final class TiposBajaNp {
    public static final String VALOR_COMPATIBILIDAD = "1,2";
    public static final String MENSAJE_FORMATO =
            "TIPOS_BAJA_NP_ACTA requiere IDs enteros positivos separados por comas; por ejemplo: 1,2,7,8.";

    private TiposBajaNp() {
    }

    public static List<Integer> parsear(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(MENSAJE_FORMATO);
        }
        Set<Integer> ids = new LinkedHashSet<>();
        for (String elemento : valor.split(",", -1)) {
            String id = elemento.trim();
            if (!id.matches("[0-9]+")) {
                throw new IllegalArgumentException(MENSAJE_FORMATO);
            }
            try {
                int numero = Integer.parseInt(id);
                if (numero <= 0) {
                    throw new IllegalArgumentException(MENSAJE_FORMATO);
                }
                ids.add(numero);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(MENSAJE_FORMATO, e);
            }
        }
        return new ArrayList<>(ids);
    }
}
