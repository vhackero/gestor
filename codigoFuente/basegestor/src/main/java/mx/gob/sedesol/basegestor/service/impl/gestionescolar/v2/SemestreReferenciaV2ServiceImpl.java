package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.PeriodoTrayectoriaV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.SemestreReferenciaV2DTO;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.SemestreReferenciaV2Service;

@Service("semestreReferenciaV2Service")
public class SemestreReferenciaV2ServiceImpl implements SemestreReferenciaV2Service {
    @Override
    public SemestreReferenciaV2DTO calcular(String periodoEvaluado, Integer ultimoSemestreMapa,
            List<PeriodoTrayectoriaV2DTO> periodos, String corte) {
        SemestreReferenciaV2DTO resultado = new SemestreReferenciaV2DTO();
        resultado.setFuente("INSCRIPCION_FORMAL_Y_EVENTOS");
        resultado.setCorte(corte);
        if (!esPeriodo(periodoEvaluado) || ultimoSemestreMapa == null || ultimoSemestreMapa.intValue() < 1 || periodos == null) {
            resultado.getPeriodosExcluidos().add("CONTEXTO:INSUFICIENTE");
            return resultado;
        }
        if (periodos.isEmpty()) {
            resultado.getPeriodosExcluidos().add("SIN_INSCRIPCION");
            resultado.setEstado("PARCIAL");
            resultado.setCalidadDato("NO_DISPONIBLE");
            return resultado;
        }
        List<PeriodoTrayectoriaV2DTO> ordenados = new ArrayList<PeriodoTrayectoriaV2DTO>(periodos);
        Collections.sort(ordenados, new Comparator<PeriodoTrayectoriaV2DTO>() { public int compare(PeriodoTrayectoriaV2DTO a, PeriodoTrayectoriaV2DTO b) { return valor(a).compareTo(valor(b)); } private String valor(PeriodoTrayectoriaV2DTO p) { return p == null || p.getPeriodo() == null ? "" : p.getPeriodo(); } });
        int contados = 0;
        boolean inferida = false;
        for (PeriodoTrayectoriaV2DTO periodo : ordenados) {
            if (periodo == null || !esPeriodo(periodo.getPeriodo()) || periodo.getPeriodo().compareTo(periodoEvaluado) >= 0) continue;
            if (Boolean.TRUE.equals(periodo.getBajaTemporalCompleta())) { resultado.getPeriodosExcluidos().add(periodo.getPeriodo() + ":BAJA_TEMPORAL"); continue; }
            if (Boolean.TRUE.equals(periodo.getCalificacionFinal()) || Boolean.TRUE.equals(periodo.getNpBajaParcial())) {
                resultado.getPeriodosContados().add(periodo.getPeriodo()); contados++; continue;
            }
            if (Boolean.TRUE.equals(periodo.getParticipacionGrupo())) {
                resultado.getPeriodosExcluidos().add(periodo.getPeriodo() + ":PARTICIPACION_SIN_CALIFICACION_FINAL");
                inferida = true;
                continue;
            }
            if (Boolean.TRUE.equals(periodo.getInscripcionFormal())) {
                resultado.getPeriodosExcluidos().add(periodo.getPeriodo() + ":INSCRIPCION_SIN_EVIDENCIA_DE_CURSAMIENTO");
                continue;
            }
            resultado.getPeriodosExcluidos().add(periodo.getPeriodo() + ":SIN_INSCRIPCION");
        }
        resultado.setValor(Integer.valueOf(Math.min(ultimoSemestreMapa.intValue(), 1 + contados)));
        resultado.setEstado(inferida ? "PARCIAL" : "DISPONIBLE");
        resultado.setCalidadDato(inferida ? "INFERIDA" : "VERIFICADA");
        return resultado;
    }
    private boolean esPeriodo(String valor) { return valor != null && valor.matches("\\d{4}-[12]"); }
}
