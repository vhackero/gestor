package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.AccionOperativaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CasoAcademicoOperativoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DictamenCasoDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.RecomendadorResolucionOperativaService;

@Service("recomendadorResolucionOperativaService")
public class RecomendadorResolucionOperativaServiceImpl implements RecomendadorResolucionOperativaService {

    @Override
    public DictamenCasoDTO generarDictamen(CasoAcademicoOperativoDTO casoAcademico) throws InscripcionException {
        DictamenCasoDTO dictamen = new DictamenCasoDTO();
        AccionOperativaDTO accion = new AccionOperativaDTO();
        String tipoCaso = casoAcademico.getTipoCaso() != null ? casoAcademico.getTipoCaso().getClave() : null;
        String motivo = casoAcademico.getMotivoRestriccion() != null ? casoAcademico.getMotivoRestriccion().getClave() : null;
        String restriccion = casoAcademico.getDiagnostico() != null ? casoAcademico.getDiagnostico().getRestriccionDominante() : null;
        String riesgoActual = casoAcademico.getDiagnostico() != null ? casoAcademico.getDiagnostico().getRiesgoActual() : null;
        Integer totalNoAcreditadas = casoAcademico.getDiagnostico() != null ? casoAcademico.getDiagnostico().getTotalNoAcreditadas() : null;
        Integer totalBloqueadas = casoAcademico.getDiagnostico() != null ? casoAcademico.getDiagnostico().getTotalBloqueadas() : null;

        if (valor(totalNoAcreditadas) >= 5 || (valor(totalNoAcreditadas) >= 3 && contiene(riesgoActual, "ALTO"))) {
            accion.setClave("PRIORIZAR_REGULARIZACION");
            accion.setNombre("Priorizar regularización");
            dictamen.setDictamen("Alto riesgo por acumulación de no acreditadas");
            dictamen.setMensajeEstudiante("Tu trayectoria presenta un riesgo alto por acumulación de UD no acreditadas. En este momento la prioridad no es avanzar con más carga, sino regularizar las unidades que están afectando tu continuidad académica.");
            dictamen.setMensajeGestor("Enfatizar regularización intensiva, priorizar no acreditadas críticas y evitar prometer aperturas que contradigan la restricción académica activa.");
            dictamen.setRequiereEscalamiento(Boolean.FALSE);
        } else if (!Boolean.TRUE.equals(casoAcademico.getExpedienteCompleto())) {
            accion.setClave("SOLICITAR_INFORMACION");
            accion.setNombre("Solicitar información");
            dictamen.setDictamen("Pendiente de información");
            dictamen.setMensajeEstudiante("Tu caso requiere información adicional antes de emitir un dictamen definitivo.");
            dictamen.setMensajeGestor("Solicita evidencia mínima del expediente antes de continuar con la resolución.");
            dictamen.setRequiereEscalamiento(Boolean.FALSE);
        } else if (contiene(tipoCaso, "OMISION_DOCUMENTADA") || contiene(motivo, "OMISION_DOCUMENTADA")) {
            accion.setClave("APLICAR_AJUSTE_POR_OMISION");
            accion.setNombre("Aplicar ajuste por omisión");
            dictamen.setDictamen("Ajuste por omisión documentada");
            dictamen.setMensajeEstudiante("Tu caso puede atenderse como una omisión documentada, no como una no acreditación, siempre que la evidencia institucional ya esté validada.");
            dictamen.setMensajeGestor("Validar soporte documental y ejecutar el ajuste sin reclasificar el caso como no acreditación.");
            dictamen.setRequiereEscalamiento(Boolean.FALSE);
        } else if (contiene(tipoCaso, "REINSCRIPCION_NO_APROBADA")
                || contiene(motivo, "PRIMER_ANIO_INCOMPLETO")) {
            accion.setClave("MANTENER_RESTRICCION");
            accion.setNombre("Mantener restricción");
            dictamen.setDictamen("Primer año incompleto");
            dictamen.setMensajeEstudiante("Mientras el primer año permanezca incompleto por una UD no aprobada, no procede habilitar obligatorias de un año superior.");
            dictamen.setMensajeGestor("No habilitar manualmente obligatorias de un año superior; priorizar la UD pendiente y solo permitir selección compatible.");
            dictamen.setRequiereEscalamiento(Boolean.FALSE);
        } else if (contiene(motivo, "SOLO_OPTATIVAS") || (valor(totalNoAcreditadas) > 0 && "AVANCE_ANUAL".equalsIgnoreCase(restriccion))) {
            accion.setClave("REGISTRAR_SOLO_OPTATIVAS_HABILITADAS");
            accion.setNombre("Registrar solo optativas habilitadas");
            dictamen.setDictamen("Selección limitada por rezago");
            dictamen.setMensajeEstudiante("Tu trayectoria tiene rezago activo; en este período debes tomar solo las UD que el sistema habilite de forma compatible, aunque eso limite la selección a optativas o pendientes.");
            dictamen.setMensajeGestor("Mantener la restricción del sistema y no forzar desbloqueos manuales; documentar que la selección queda limitada por rezago, carga y pendientes.");
            dictamen.setRequiereEscalamiento(Boolean.FALSE);
        } else if (contiene(motivo, "MISMO_ANIO") || (contiene(restriccion, "AVANCE_ANUAL") && valor(totalNoAcreditadas) == 0)) {
            accion.setClave("PERMITIR_CONTINUIDAD_MISMO_ANIO");
            accion.setNombre("Permitir continuidad del mismo año");
            dictamen.setDictamen("Continuidad del mismo año académico");
            dictamen.setMensajeEstudiante("Tu caso puede analizarse como continuidad dentro del mismo año académico, pero sigue sujeto a carga, oferta y reglas de selección.");
            dictamen.setMensajeGestor("Distinguir continuidad del mismo año contra avance improcedente; permitir solo la selección que sea compatible con carga, oferta y trayectoria.");
            dictamen.setRequiereEscalamiento(Boolean.FALSE);
        } else if (valor(totalBloqueadas) > 0 && !Boolean.TRUE.equals(casoAcademico.getRequiereIntervencionHumana())) {
            accion.setClave("DAR_SEGUIMIENTO");
            accion.setNombre("Dar seguimiento");
            dictamen.setDictamen("Seguimiento con seriación activa");
            dictamen.setMensajeEstudiante("Hay UD bloqueadas por seriación; antes de pensar en otras opciones debes atender la unidad antecedente o la carga ya habilitada.");
            dictamen.setMensajeGestor("Explicar la seriación activa y documentar la UD antecedente que mantiene el bloqueo.");
            dictamen.setRequiereEscalamiento(Boolean.FALSE);
        } else if (Boolean.TRUE.equals(casoAcademico.getRequiereIntervencionHumana())) {
            accion.setClave("ESCALAR_REVISION");
            accion.setNombre("Escalar revisión");
            dictamen.setDictamen("Requiere revisión de segundo nivel");
            dictamen.setMensajeEstudiante("Tu caso será revisado por una instancia académica para confirmar la mejor ruta de atención.");
            dictamen.setMensajeGestor("Escalar el caso con evidencia y regla aplicada.");
            dictamen.setRequiereEscalamiento(Boolean.TRUE);
        } else if ("INSCRIPCION".equalsIgnoreCase(casoAcademico.getOrigenCaso())) {
            accion.setClave("VALIDAR_SELECCION");
            accion.setNombre("Validar selección");
            dictamen.setDictamen("Validación operativa inicial");
            dictamen.setMensajeEstudiante("Se generó una validación inicial para tu selección académica.");
            dictamen.setMensajeGestor("Revisar la selección, corroborar regla aplicada y documentar el resultado.");
            dictamen.setRequiereEscalamiento(Boolean.FALSE);
        } else {
            accion.setClave("DAR_SEGUIMIENTO");
            accion.setNombre("Dar seguimiento");
            dictamen.setDictamen("Seguimiento de primer nivel");
            dictamen.setMensajeEstudiante("Se generó una orientación inicial con base en tu contexto académico actual.");
            dictamen.setMensajeGestor("Revisar la causa principal, validar evidencia y documentar la atención brindada.");
            dictamen.setRequiereEscalamiento(Boolean.FALSE);
        }
        dictamen.setAccionOperativa(accion);
        return dictamen;
    }

    private boolean contiene(String valor, String patron) {
        return valor != null && patron != null && valor.toUpperCase().contains(patron.toUpperCase());
    }

    private int valor(Integer numero) {
        return numero != null ? numero.intValue() : 0;
    }
}
