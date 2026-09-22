package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.AccionOperativaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CasoAcademicoOperativoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DictamenCasoDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.CatAccionOperativaV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.CatViabilidadTecnicaV2;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.CatAccionOperativaV2Repo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.CatViabilidadTecnicaV2Repo;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.RecomendadorResolucionOperativaService;

@Service("recomendadorResolucionOperativaService")
public class RecomendadorResolucionOperativaServiceImpl implements RecomendadorResolucionOperativaService {

    @Autowired
    private CatAccionOperativaV2Repo accionRepo;

    @Autowired
    private CatViabilidadTecnicaV2Repo viabilidadRepo;

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

        resolverPorTipo(dictamen, accion, tipoCaso, motivo, restriccion, totalNoAcreditadas, totalBloqueadas,
                casoAcademico.getExpedienteCompleto(), casoAcademico.getRequiereIntervencionHumana());
        CatAccionOperativaV2 accionCatalogo = accionRepo.findByClave(accion.getClave());
        if (accionCatalogo == null) {
            throw new InscripcionException("No existe la acción operativa configurada: " + accion.getClave());
        }
        dictamen.setAccionOperativa(AsistenteCurricularV2Mapper.toAccionDto(accionCatalogo));

        if (casoAcademico.getViabilidadTecnica() != null
                && casoAcademico.getViabilidadTecnica().getId() != null) {
            dictamen.setViabilidadTecnica(casoAcademico.getViabilidadTecnica());
        } else {
            CatViabilidadTecnicaV2 viabilidad = viabilidadRepo.findByClave("VIABLE");
            if (viabilidad == null) {
                throw new InscripcionException("No existe la viabilidad técnica configurada: VIABLE");
            }
            dictamen.setViabilidadTecnica(AsistenteCurricularV2Mapper.toViabilidadDto(viabilidad));
        }
        return dictamen;
    }

    private boolean contiene(String valor, String patron) {
        return valor != null && patron != null && valor.toUpperCase().contains(patron.toUpperCase());
    }

    private int valor(Integer numero) {
        return numero != null ? numero.intValue() : 0;
    }

    /** El tipo clasificado es la decisión principal; los contadores sólo la contextualizan. */
    private void resolverPorTipo(DictamenCasoDTO dictamen, AccionOperativaDTO accion, String tipo, String motivo,
            String restriccion, Integer noAcreditadas, Integer bloqueadas, Boolean expedienteCompleto,
            Boolean requiereIntervencion) {
        if (!Boolean.TRUE.equals(expedienteCompleto) || "PENDIENTE_INFORMACION".equals(tipo)) {
            configurar(dictamen, accion, "SOLICITAR_INFORMACION", "Pendiente de información",
                    "Tu caso requiere información adicional antes de emitir un dictamen definitivo.",
                    "Solicita evidencia mínima del expediente antes de continuar con la resolución.", false);
        } else if (valor(bloqueadas) > 0) {
            configurar(dictamen, accion, "MANTENER_RESTRICCION", "Seguimiento con seriación activa",
                    "Hay unidades didácticas bloqueadas por seriación; debes acreditar primero la unidad antecedente para habilitar las subsecuentes.",
                    "Documentar la unidad antecedente pendiente y mantener la restricción curricular.", false);
        } else if ("SERIACION".equals(tipo) && valor(noAcreditadas) == 0) {
            // La clasificación almacenada puede provenir de una consulta anterior. La
            // restricción sólo se conserva si la evaluación actual todavía reporta UD
            // bloqueadas; una acreditación posterior del antecedente debe liberar el
            // avance sin exigir una intervención manual.
            configurar(dictamen, accion, "DAR_SEGUIMIENTO", "Seriación sin bloqueo activo",
                    "El antecedente de seriación ya está acreditado o no genera bloqueos vigentes. Puedes continuar con las unidades habilitadas.",
                    "Conservar la trazabilidad del caso y confirmar que la oferta actual no contiene bloqueos activos.", false);
        } else if ("UNIDADES_NO_ACREDITADAS".equals(tipo) || "REZAGO".equals(tipo)) {
            configurar(dictamen, accion, "PRIORIZAR_REGULARIZACION", "Regularización de unidades pendientes",
                    "Presentas " + valor(noAcreditadas) + " unidad(es) no acreditada(s). Debes priorizar su regularización cuando exista oferta.",
                    "Priorizar las unidades no acreditadas y no habilitar una carga que contradiga la restricción vigente.", false);
        } else if ("OMISION_DOCUMENTADA".equals(tipo)) {
            configurar(dictamen, accion, "APLICAR_AJUSTE_POR_OMISION", "Ajuste por omisión documentada",
                    "Tu caso puede atenderse como una omisión documentada, una vez validada la evidencia institucional.",
                    "Validar la evidencia y aplicar el ajuste sin clasificarlo como no acreditación.", false);
        } else if ("OMISION_REGISTRO".equals(tipo)) {
            configurar(dictamen, accion, "VALIDAR_SELECCION", "Regularización de omisión de registro",
                    "Tienes unidades didácticas no cursadas que requieren revisión de oferta y selección para regularizar tu trayectoria.",
                    "Verificar oferta, motivo de omisión y selección compatible antes de confirmar el movimiento.", false);
        } else if ("OFERTA_NO_DISPONIBLE".equals(tipo)) {
            configurar(dictamen, accion, "DAR_SEGUIMIENTO", "Oferta académica no disponible",
                    "No existe oferta vigente suficiente para resolver tu selección en este periodo; consulta las alternativas disponibles.",
                    "Registrar la incidencia de oferta y acompañar al estudiante con una alternativa compatible.", false);
        } else if ("CARGA_LIMITADA".equals(tipo)) {
            configurar(dictamen, accion, "VALIDAR_SELECCION", "Carga académica limitada",
                    "Tu selección debe ajustarse a la carga máxima permitida y a las unidades prioritarias de tu trayectoria.",
                    "Validar créditos, unidades prioritarias y límites de carga antes de confirmar.", false);
        } else if ("AVANCE_ANUAL".equals(tipo) || "REINSCRIPCION_NO_APROBADA".equals(tipo)) {
            configurar(dictamen, accion, "MANTENER_RESTRICCION", "Restricción de avance académico",
                    "Tu avance está condicionado por unidades obligatorias pendientes; primero debes atender la carga compatible habilitada.",
                    "Mantener la restricción de avance y evitar desbloqueos manuales incompatibles.", false);
        } else if ("CONTINUIDAD_MISMO_ANIO".equals(tipo)) {
            configurar(dictamen, accion, "PERMITIR_CONTINUIDAD_MISMO_ANIO", "Continuidad del mismo año académico",
                    "Puedes continuar dentro del mismo año académico, sujeto a la oferta y a las reglas de carga aplicables.",
                    "Distinguir continuidad de avance improcedente y validar la selección compatible.", false);
        } else if ("EQUIVALENCIA_PE".equals(tipo)) {
            configurar(dictamen, accion, "RECONOCER_EQUIVALENCIA_PE", "Equivalencia académica acreditada",
                    "Las unidades didácticas ya acreditadas por equivalencia no deben volver a formar parte de tu oferta.",
                    "Validar la equivalencia por nombre normalizado y conservar fuera de la oferta las UD ya acreditadas.", false);
        } else if ("CONFIGURACION_PLAN".equals(tipo)) {
            configurar(dictamen, accion, "REVISAR_CONFIGURACION_PLAN", "Revisión de configuración del plan",
                    "La situación requiere revisar la configuración académica aplicable antes de continuar.",
                    "Verificar umbrales, marcado, excepciones y vigencia de las reglas del plan sin alterar el historial del estudiante.", true);
        } else if ("ESTADO_INSCRIPCION".equals(tipo)) {
            configurar(dictamen, accion, "VERIFICAR_INSCRIPCION_PREVIA", "Validación del estado de inscripción",
                    "Antes de continuar, el sistema debe confirmar que no exista una inscripción previa vigente.",
                    "Revalidar la oferta y proteger la operación contra registros duplicados o concurrentes.", false);
        } else if ("ACLARACION_AVANCE".equals(tipo) || Boolean.TRUE.equals(requiereIntervencion)) {
            configurar(dictamen, accion, "ESCALAR_REVISION", "Revisión de avance académico",
                    "Tu trayectoria requiere una revisión académica para confirmar la ruta de atención aplicable.",
                    "Escalar el caso con la evidencia y la regla que originó la revisión.", true);
        } else {
            configurar(dictamen, accion, "DAR_SEGUIMIENTO", "Trayectoria académica regular",
                    "Tu trayectoria no presenta pendientes que limiten el avance actual. Mantén la acreditación de tu carga vigente.",
                    "Dar seguimiento a la trayectoria y orientar sobre la siguiente carga académica.", false);
        }
    }

    private void configurar(DictamenCasoDTO dictamen, AccionOperativaDTO accion, String clave, String titulo,
            String estudiante, String gestor, boolean escalar) {
        accion.setClave(clave);
        dictamen.setDictamen(titulo);
        dictamen.setMensajeEstudiante(estudiante);
        dictamen.setMensajeGestor(gestor);
        dictamen.setRequiereEscalamiento(Boolean.valueOf(escalar));
    }
}
