package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DiagnosticoAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.MotivoDecisionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.PendientesPlanDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.RiesgoAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadDecisionInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.CasoUdRelacionadaDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoAsistenteCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DiagnosticoAcademicoOperativoDTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.TblCasoDiagnosticoV2Repo;
import mx.gob.sedesol.basegestor.service.gestionescolar.AsistenteInscripcionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.DiagnosticoAcademicoOperativoService;

@Service("diagnosticoAcademicoOperativoService")
public class DiagnosticoAcademicoOperativoServiceImpl implements DiagnosticoAcademicoOperativoService {

    @Autowired
    private TblCasoDiagnosticoV2Repo diagnosticoRepo;

    @Autowired
    private AsistenteInscripcionService asistenteInscripcionService;

    @Override
    @Transactional(readOnly = true)
    public DiagnosticoAcademicoOperativoDTO construirDiagnostico(ContextoAsistenteCurricularV2DTO contexto)
            throws InscripcionException {
        DiagnosticoAcademicoOperativoDTO diagnostico = null;
        if (contexto != null && contexto.getCasoActual() != null && contexto.getCasoActual().getDiagnostico() != null) {
            diagnostico = contexto.getCasoActual().getDiagnostico();
        } else if (contexto != null && contexto.getCasoActual() != null && contexto.getCasoActual().getId() != null) {
            diagnostico = AsistenteCurricularV2Mapper.toDiagnosticoDto(
                    diagnosticoRepo.findByIdCaso(contexto.getCasoActual().getId()));
        }
        if (diagnostico == null) {
            diagnostico = new DiagnosticoAcademicoOperativoDTO();
        }

        if (contexto == null || contexto.getIdPersonaObjetivo() == null) {
            completarDiagnosticoBase(diagnostico, contexto);
            diagnostico.getAlertasDiagnostico().add("No existe persona objetivo para construir diagnóstico enriquecido.");
            return diagnostico;
        }

        AsistenteInscripcionContextoDTO contextoAsistido = asistenteInscripcionService
                .obtenerContextoAsistido(contexto.getIdPersonaObjetivo());
        if (contextoAsistido == null) {
            completarDiagnosticoBase(diagnostico, contexto);
            diagnostico.getAlertasDiagnostico().add("No fue posible obtener el contexto asistido del estudiante.");
            return diagnostico;
        }

        enriquecerDesdeContextoAsistido(diagnostico, contexto, contextoAsistido);
        return diagnostico;
    }

    private void enriquecerDesdeContextoAsistido(DiagnosticoAcademicoOperativoDTO diagnostico,
            ContextoAsistenteCurricularV2DTO contexto, AsistenteInscripcionContextoDTO contextoAsistido) {
        DiagnosticoAcademicoDTO diagnosticoBase = contextoAsistido.getDiagnosticoActual();
        PendientesPlanDTO pendientes = contextoAsistido.getPendientesPlan();
        RiesgoAcademicoDTO riesgo = contextoAsistido.getRiesgos();

        int totalNoAcreditadas = 0;
        int totalOmisiones = 0;
        int totalBloqueadas = 0;
        int totalPendientesCriticas = 0;

        diagnostico.getDetallePorUd().clear();
        diagnostico.getReglasAplicadas().clear();
        diagnostico.getAlertasDiagnostico().clear();

        if (contextoAsistido.getUnidades() != null) {
            for (UnidadDecisionInscripcionDTO unidad : contextoAsistido.getUnidades()) {
                if (unidad == null) {
                    continue;
                }
                boolean noAcreditada = contiene(unidad.getEstatusHistorico(), "NO ACREDIT");
                boolean omision = contiene(unidad.getEstatusHistorico(), "NO INSCRITA")
                        || contiene(unidad.getEstatusHistorico(), "NO CURSADA");
                boolean bloqueada = Boolean.TRUE.equals(unidad.getBloqueada());
                boolean critica = Boolean.TRUE.equals(unidad.getPrioritaria()) || esMotivoBloqueante(unidad);

                if (noAcreditada) {
                    totalNoAcreditadas++;
                }
                if (omision) {
                    totalOmisiones++;
                }
                if (bloqueada) {
                    totalBloqueadas++;
                }
                if (critica) {
                    totalPendientesCriticas++;
                }

                diagnostico.getDetallePorUd().add(construirDetalleUd(unidad, noAcreditada, omision, bloqueada, critica));
            }
        }

        diagnostico.setSituacionAcademica(resolverSituacionAcademica(contextoAsistido, diagnosticoBase));
        diagnostico.setRiesgoActual(riesgo != null && tieneTexto(riesgo.getNivelRiesgo()) ? riesgo.getNivelRiesgo()
                : (contextoAsistido.getRestriccionCuatroOMasReprobadas() != null
                        && contextoAsistido.getRestriccionCuatroOMasReprobadas().booleanValue()) ? "ALTO" : "MEDIO");
        diagnostico.setRiesgoSiguientePeriodo(riesgo != null && tieneTexto(riesgo.getResumen())
                ? resumirRiesgoSiguientePeriodo(riesgo) : diagnostico.getRiesgoActual());
        diagnostico.setCierreAnual(Boolean.TRUE.equals(contextoAsistido.getCierreAnualCumplido()) ? "CUMPLIDO" : "PENDIENTE");
        diagnostico.setCargaViable(contextoAsistido.getCargaMaxima());
        diagnostico.setSeriacionActiva(Boolean.valueOf(totalBloqueadas > 0 || existeMensajeSeriacion(contextoAsistido)));
        diagnostico.setOfertaVigente(contextoAsistido.getInscripcionVigente());
        diagnostico.setDictamenPreliminar(resolverDictamenPreliminar(contexto, contextoAsistido, totalBloqueadas));

        diagnostico.setTotalNoAcreditadas(Integer.valueOf(totalNoAcreditadas));
        diagnostico.setTotalOmisiones(Integer.valueOf(totalOmisiones));
        diagnostico.setTotalBloqueadas(Integer.valueOf(totalBloqueadas));
        diagnostico.setTotalPendientesCriticas(Integer.valueOf(totalPendientesCriticas));
        diagnostico.setRestriccionDominante(resolverRestriccionDominante(contextoAsistido, totalBloqueadas, totalNoAcreditadas,
                totalOmisiones));
        diagnostico.setMotivoBloqueoPrincipal(resolverMotivoBloqueoPrincipal(contextoAsistido, totalBloqueadas));
        diagnostico.setComparativoAvanceRestricciones(construirComparativoAvance(contextoAsistido, totalBloqueadas));
        diagnostico.setInterpretacionOmisiones(construirInterpretacionOmisiones(totalOmisiones, totalNoAcreditadas));
        diagnostico.setResumenMotor(construirResumenMotorEnriquecido(contextoAsistido, diagnostico, pendientes));

        cargarReglasAplicadas(diagnostico, contextoAsistido, totalBloqueadas, totalNoAcreditadas, totalOmisiones);
        cargarAlertasDiagnostico(diagnostico, contextoAsistido, riesgo, totalBloqueadas);
    }

    private CasoUdRelacionadaDTO construirDetalleUd(UnidadDecisionInscripcionDTO unidad, boolean noAcreditada,
            boolean omision, boolean bloqueada, boolean critica) {
        CasoUdRelacionadaDTO detalle = new CasoUdRelacionadaDTO();
        detalle.setIdPrograma(unidad.getUdId());
        detalle.setClaveUd(unidad.getClave());
        detalle.setNombreUd(unidad.getNombre());
        detalle.setTipoUd(unidad.getTipoUd());
        detalle.setSemestre(unidad.getSemestre() != null ? String.valueOf(unidad.getSemestre()) : null);
        detalle.setBloque(unidad.getBloque() != null ? String.valueOf(unidad.getBloque()) : null);
        detalle.setEstatusDetectado(resolverEstatusDiagnosticoUd(noAcreditada, omision, bloqueada, unidad));
        detalle.setCritica(Boolean.valueOf(critica));
        return detalle;
    }

    private String resolverEstatusDiagnosticoUd(boolean noAcreditada, boolean omision, boolean bloqueada,
            UnidadDecisionInscripcionDTO unidad) {
        if (bloqueada) {
            return "BLOQUEADA";
        }
        if (noAcreditada) {
            return "NO_ACREDITADA";
        }
        if (omision) {
            return "OMISION";
        }
        if (unidad != null && tieneTexto(unidad.getEstatusPeriodo())) {
            return unidad.getEstatusPeriodo();
        }
        return unidad != null ? unidad.getEstatusHistorico() : null;
    }

    private String resolverSituacionAcademica(AsistenteInscripcionContextoDTO contextoAsistido,
            DiagnosticoAcademicoDTO diagnosticoBase) {
        if (tieneTexto(contextoAsistido.getSituacionAcademicaPeriodo())) {
            return contextoAsistido.getSituacionAcademicaPeriodo();
        }
        if (diagnosticoBase != null && diagnosticoBase.getRegular() != null) {
            return diagnosticoBase.getRegular().booleanValue() ? "REGULAR" : "IRREGULAR";
        }
        return "EN_ANALISIS";
    }

    private String resumirRiesgoSiguientePeriodo(RiesgoAcademicoDTO riesgo) {
        if (riesgo == null) {
            return null;
        }
        if (tieneTexto(riesgo.getNivelRiesgo())) {
            if (riesgo.getRiesgosMedianoPlazo() != null && !riesgo.getRiesgosMedianoPlazo().isEmpty()) {
                return riesgo.getNivelRiesgo().trim() + "_MEDIANO_PLAZO";
            }
            return riesgo.getNivelRiesgo().trim();
        }
        if (riesgo.getRiesgosMedianoPlazo() != null && !riesgo.getRiesgosMedianoPlazo().isEmpty()) {
            return resumirTextoCorto(riesgo.getRiesgosMedianoPlazo().get(0), 50);
        }
        return null;
    }

    private String resumirTextoCorto(String texto, int longitudMaxima) {
        if (!tieneTexto(texto)) {
            return texto;
        }
        String limpio = texto.trim().replaceAll("\\s+", " ");
        if (limpio.length() <= longitudMaxima) {
            return limpio;
        }
        return limpio.substring(0, Math.max(0, longitudMaxima - 3)) + "...";
    }

    private String resolverDictamenPreliminar(ContextoAsistenteCurricularV2DTO contexto,
            AsistenteInscripcionContextoDTO contextoAsistido, int totalBloqueadas) {
        if (Boolean.TRUE.equals(contextoAsistido.getRestriccionCuatroOMasReprobadas())) {
            return "RESTRICCION_AVANCE_ANUAL";
        }
        if (totalBloqueadas > 0) {
            return "BLOQUEO_POR_SERIACION";
        }
        if (contexto != null && "GESTOR".equalsIgnoreCase(contexto.getPerfilConsulta())) {
            return "REQUIERE_REVISION_OPERATIVA";
        }
        return Boolean.TRUE.equals(contextoAsistido.getInscripcionVigente()) ? "VALIDACION_ACADEMICA"
                : "SEGUIMIENTO_CURSAMIENTO";
    }

    private String resolverRestriccionDominante(AsistenteInscripcionContextoDTO contextoAsistido, int totalBloqueadas,
            int totalNoAcreditadas, int totalOmisiones) {
        if (Boolean.TRUE.equals(contextoAsistido.getRestriccionCuatroOMasReprobadas())) {
            return "AVANCE_ANUAL";
        }
        if (totalBloqueadas > 0) {
            return "SERIACION";
        }
        if (totalNoAcreditadas > totalOmisiones) {
            return "NO_ACREDITACION";
        }
        if (totalOmisiones > 0) {
            return "OMISION";
        }
        return "SIN_RESTRICCION_DOMINANTE";
    }

    private String resolverMotivoBloqueoPrincipal(AsistenteInscripcionContextoDTO contextoAsistido, int totalBloqueadas) {
        if (Boolean.TRUE.equals(contextoAsistido.getRestriccionCuatroOMasReprobadas())) {
            return "El estudiante rebasa la restricción de avance anual por acumulación de reprobadas.";
        }
        if (totalBloqueadas > 0) {
            return "Existen UD bloqueadas por seriación curricular pendiente.";
        }
        if (tieneTexto(contextoAsistido.getMensajeResumenPeriodo())) {
            return contextoAsistido.getMensajeResumenPeriodo();
        }
        return "No se detecta bloqueo dominante; el caso requiere revisión de trayectoria.";
    }

    private String construirComparativoAvance(AsistenteInscripcionContextoDTO contextoAsistido, int totalBloqueadas) {
        if (Boolean.TRUE.equals(contextoAsistido.getRestriccionCuatroOMasReprobadas())) {
            return "La restricción dominante es avance anual; prevalece sobre la recomendación ordinaria de carga.";
        }
        if (totalBloqueadas > 0) {
            return "La trayectoria permite avance parcial, pero la seriación limita la oferta disponible.";
        }
        if (Boolean.TRUE.equals(contextoAsistido.getInscripcionVigente())) {
            return "No se detecta restricción superior al avance anual; la limitante principal es la oferta vigente.";
        }
        return "Sin proceso de inscripción activo, el avance anual se interpreta sólo como referencia de seguimiento.";
    }

    private String construirInterpretacionOmisiones(int totalOmisiones, int totalNoAcreditadas) {
        if (totalOmisiones == 0 && totalNoAcreditadas == 0) {
            return "No se detectan omisiones ni no acreditaciones activas en el corte actual.";
        }
        if (totalOmisiones > totalNoAcreditadas) {
            return "Predominan omisiones de inscripción/no cursadas sobre no acreditaciones; la atención debe enfocarse en regularizar selección pendiente.";
        }
        if (totalNoAcreditadas > totalOmisiones) {
            return "Predominan no acreditaciones; la trayectoria está impactada principalmente por reprobación activa.";
        }
        return "Existe un equilibrio entre omisiones y no acreditaciones; el caso requiere revisar cada UD crítica.";
    }

    private String construirResumenMotorEnriquecido(AsistenteInscripcionContextoDTO contextoAsistido,
            DiagnosticoAcademicoOperativoDTO diagnostico, PendientesPlanDTO pendientes) {
        StringBuilder sb = new StringBuilder();
        sb.append("Situación ").append(diagnostico.getSituacionAcademica());
        sb.append(", restricción dominante ").append(diagnostico.getRestriccionDominante());
        sb.append(", ").append(diagnostico.getTotalNoAcreditadas()).append(" UD no acreditadas");
        sb.append(", ").append(diagnostico.getTotalOmisiones()).append(" omisiones");
        sb.append(", ").append(diagnostico.getTotalBloqueadas()).append(" bloqueadas por seriación");
        if (pendientes != null && pendientes.getCreditosFaltantes() != null) {
            sb.append(", créditos faltantes ").append(pendientes.getCreditosFaltantes());
        }
        if (tieneTexto(contextoAsistido.getMensajeResumenPeriodo())) {
            sb.append(". ").append(contextoAsistido.getMensajeResumenPeriodo());
        }
        return sb.toString();
    }

    private void cargarReglasAplicadas(DiagnosticoAcademicoOperativoDTO diagnostico,
            AsistenteInscripcionContextoDTO contextoAsistido, int totalBloqueadas, int totalNoAcreditadas,
            int totalOmisiones) {
        if (Boolean.TRUE.equals(contextoAsistido.getRestriccionCuatroOMasReprobadas())) {
            diagnostico.getReglasAplicadas().add("Restricción de avance anual por 4 o más reprobadas.");
        }
        if (totalBloqueadas > 0) {
            diagnostico.getReglasAplicadas().add("Aplicación de seriación curricular sobre UD bloqueadas.");
        }
        if (totalNoAcreditadas > 0) {
            diagnostico.getReglasAplicadas().add("Priorización de UD no acreditadas para regularización.");
        }
        if (totalOmisiones > 0) {
            diagnostico.getReglasAplicadas().add("Diferenciación entre omisión de inscripción y no acreditación.");
        }
        if (Boolean.TRUE.equals(contextoAsistido.getInscripcionVigente())) {
            diagnostico.getReglasAplicadas().add("Validación en periodo de inscripción/reinscripción vigente.");
        } else {
            diagnostico.getReglasAplicadas().add("Seguimiento de trayectoria en periodo de cursamiento.");
        }
    }

    private void cargarAlertasDiagnostico(DiagnosticoAcademicoOperativoDTO diagnostico,
            AsistenteInscripcionContextoDTO contextoAsistido, RiesgoAcademicoDTO riesgo, int totalBloqueadas) {
        if (Boolean.FALSE.equals(contextoAsistido.getCierreAnualCumplido())) {
            diagnostico.getAlertasDiagnostico().add("El cierre anual no está cumplido; validar arrastre de pendientes.");
        }
        if (tieneTexto(contextoAsistido.getContextoBase() != null ? contextoAsistido.getContextoBase().getMensajeSeriacion() : null)) {
            diagnostico.getAlertasDiagnostico().add(contextoAsistido.getContextoBase().getMensajeSeriacion());
        }
        if (tieneTexto(contextoAsistido.getContextoBase() != null
                ? contextoAsistido.getContextoBase().getMensajeLimiteReprobacionesAlcanzado() : null)) {
            diagnostico.getAlertasDiagnostico()
                    .add(contextoAsistido.getContextoBase().getMensajeLimiteReprobacionesAlcanzado());
        }
        if (riesgo != null && riesgo.getRiesgosInmediatos() != null) {
            diagnostico.getAlertasDiagnostico().addAll(riesgo.getRiesgosInmediatos());
        }
        if (contextoAsistido.getPendientesCriticos() != null) {
            diagnostico.getAlertasDiagnostico().addAll(contextoAsistido.getPendientesCriticos());
        }
        if (totalBloqueadas > 0) {
            diagnostico.getAlertasDiagnostico().add("Se detectaron " + totalBloqueadas
                    + " UD bloqueadas que condicionan la trayectoria inmediata.");
        }
    }

    private boolean esMotivoBloqueante(UnidadDecisionInscripcionDTO unidad) {
        if (unidad == null || unidad.getMotivos() == null) {
            return false;
        }
        for (MotivoDecisionDTO motivo : unidad.getMotivos()) {
            if (motivo != null && Boolean.TRUE.equals(motivo.getBloqueante())) {
                return true;
            }
        }
        return false;
    }

    private boolean existeMensajeSeriacion(AsistenteInscripcionContextoDTO contextoAsistido) {
        return contextoAsistido != null && contextoAsistido.getContextoBase() != null
                && tieneTexto(contextoAsistido.getContextoBase().getMensajeSeriacion());
    }

    private boolean contiene(String valor, String patron) {
        return valor != null && patron != null && valor.toUpperCase().contains(patron.toUpperCase());
    }

    private boolean tieneTexto(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }

    private void completarDiagnosticoBase(DiagnosticoAcademicoOperativoDTO diagnostico,
            ContextoAsistenteCurricularV2DTO contexto) {
        diagnostico.setSituacionAcademica("EN_ANALISIS");
        diagnostico.setRiesgoActual(contexto != null && "INSCRIPCION".equalsIgnoreCase(contexto.getPeriodoOperativo())
                ? "MEDIO" : "BAJO");
        diagnostico.setRiesgoSiguientePeriodo(
                contexto != null && "CURSAMIENTO".equalsIgnoreCase(contexto.getPeriodoOperativo()) ? "MEDIO" : "BAJO");
        diagnostico.setCierreAnual(contexto != null && "INSCRIPCION".equalsIgnoreCase(contexto.getPeriodoOperativo())
                ? "VALIDAR" : "SEGUIMIENTO");
        diagnostico.setCargaViable(contexto != null && contexto.getIdPlan() != null ? Integer.valueOf(1) : Integer.valueOf(0));
        diagnostico.setSeriacionActiva(Boolean.FALSE);
        diagnostico.setOfertaVigente(Boolean.valueOf(contexto != null && "INSCRIPCION".equalsIgnoreCase(contexto.getPeriodoOperativo())));
        diagnostico.setDictamenPreliminar(contexto != null && "GESTOR".equalsIgnoreCase(contexto.getPerfilConsulta())
                ? "REQUIERE_REVISION_OPERATIVA" : "ORIENTACION_INICIAL");
        diagnostico.setRestriccionDominante("SIN_RESTRICCION_DOMINANTE");
        diagnostico.setMotivoBloqueoPrincipal("No existe contexto suficiente para construir un diagnóstico enriquecido.");
        diagnostico.setComparativoAvanceRestricciones("No fue posible comparar avance anual con otras restricciones.");
        diagnostico.setInterpretacionOmisiones("Sin datos suficientes para distinguir omisión y no acreditación.");
        diagnostico.setResumenMotor(construirResumenBase(contexto));
    }

    private String construirResumenBase(ContextoAsistenteCurricularV2DTO contexto) {
        String perfil = contexto != null ? contexto.getPerfilConsulta() : "SIN_PERFIL";
        String periodo = contexto != null ? contexto.getPeriodoOperativo() : "SIN_PERIODO";
        String origen = contexto != null ? contexto.getOrigenConsulta() : "SIN_ORIGEN";
        return "Diagnóstico inicial generado para perfil " + perfil
                + ", periodo " + periodo + " y origen " + origen + ".";
    }
}
