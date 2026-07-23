package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.DiagnosticoAcademicoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadDecisionInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ClasificacionCasoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoAsistenteCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.CatMotivoRestriccionV2;
import mx.gob.sedesol.basegestor.model.entities.gestionescolar.v2.CatTipoCasoAcademicoV2;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.CatMotivoRestriccionV2Repo;
import mx.gob.sedesol.basegestor.model.repositories.gestionescolar.v2.CatTipoCasoAcademicoV2Repo;
import mx.gob.sedesol.basegestor.service.gestionescolar.AsistenteInscripcionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.ClasificadorCasoService;

@Service("clasificadorCasoService")
public class ClasificadorCasoServiceImpl implements ClasificadorCasoService {

    @Autowired
    private CatTipoCasoAcademicoV2Repo tipoCasoRepo;

    @Autowired
    private CatMotivoRestriccionV2Repo motivoRepo;

    @Autowired
    private AsistenteInscripcionService asistenteInscripcionService;

    @Override
    public ClasificacionCasoDTO clasificarCaso(ContextoAsistenteCurricularV2DTO contexto) {
        if (contexto != null && contexto.getCasoActual() != null && contexto.getCasoActual().getClasificacion() != null) {
            return contexto.getCasoActual().getClasificacion();
        }
        ClasificacionCasoDTO clasificacion = AsistenteCurricularV2Mapper.crearClasificacionVacia(contexto);

        boolean tienePersona = contexto != null && contexto.getIdPersonaObjetivo() != null;
        boolean tienePlan = contexto != null && contexto.getIdPlan() != null;
        boolean tienePerfil = contexto != null && tieneTexto(contexto.getPerfilConsulta());
        boolean tienePeriodoOperativo = contexto != null && tieneTexto(contexto.getPeriodoOperativo());
        boolean tieneOrigen = contexto != null && tieneTexto(contexto.getOrigenConsulta());
        boolean requiereGestor = contexto != null && Boolean.TRUE.equals(contexto.getVistaGestor());
        boolean tienePersonaConsulta = contexto != null && contexto.getIdPersonaConsulta() != null;
        String periodo = contexto != null ? contexto.getPeriodoOperativo() : null;
        String origen = contexto != null ? contexto.getOrigenConsulta() : null;
        AsistenteInscripcionContextoDTO contextoAsistido = obtenerContextoAsistidoSeguro(contexto);
        DiagnosticoAcademicoDTO diagnostico = contextoAsistido != null ? contextoAsistido.getDiagnosticoActual() : null;
        int noAcreditadas = valor(diagnostico != null ? diagnostico.getMateriasReprobadasActivas() : null);
        int omisiones = valor(diagnostico != null ? diagnostico.getMateriasPendientesNoInscritas() : null);
        int bloqueadas = valor(diagnostico != null ? diagnostico.getMateriasBloqueadasPorSeriacion() : null);
        boolean contextoAcademicoSuficiente = contextoAsistido != null
                && (noAcreditadas > 0 || omisiones > 0 || bloqueadas > 0
                        || (contextoAsistido.getUnidades() != null && !contextoAsistido.getUnidades().isEmpty()));

        if (!tienePersona || !tienePerfil || !tienePeriodoOperativo || !tieneOrigen
                || (!tienePlan && !contextoAcademicoSuficiente)
                || (requiereGestor && !tienePersonaConsulta)) {
            clasificacion.setTipoCaso(AsistenteCurricularV2Mapper
                    .toTipoCasoDto(tipoCasoRepo.findByClave("PENDIENTE_INFORMACION")));
            clasificacion.setMotivoPrincipal(AsistenteCurricularV2Mapper
                    .toMotivoDto(motivoRepo.findByClave("INFORMACION_INSUFICIENTE")));
            clasificacion.setConfianza(0.55D);
            clasificacion.setOperable(Boolean.FALSE);
            clasificacion.setRequiereIntervencionHumana(Boolean.TRUE);
            clasificacion.setEstatusSugerido("PENDIENTE_INFORMACION");
            clasificacion.getObservaciones().add("El expediente mínimo de la orientación está incompleto.");
            agregarFaltante(clasificacion, tienePersona, "Falta persona objetivo.");
            agregarFaltante(clasificacion, tienePlan, "Falta plan de estudios.");
            agregarFaltante(clasificacion, tienePerfil, "Falta perfil de consulta.");
            agregarFaltante(clasificacion, tienePeriodoOperativo, "Falta periodo operativo.");
            agregarFaltante(clasificacion, tieneOrigen, "Falta origen de consulta.");
            if (requiereGestor) {
                agregarFaltante(clasificacion, tienePersonaConsulta, "Falta persona que consulta en modo gestor.");
            }
            return clasificacion;
        }

        CatTipoCasoAcademicoV2 tipoCaso = null;
        CatMotivoRestriccionV2 motivo = null;
        boolean primerAnioIncompleto = contextoAsistido != null
                && contextoAsistido.getAnioObligatorioPendiente() != null
                && contextoAsistido.getAnioObligatorioPendiente().intValue() <= 1;
        boolean restriccionAvance = contextoAsistido != null
                && Boolean.TRUE.equals(contextoAsistido.getRestriccionCuatroOMasReprobadas());
        boolean soloOptativas = detectarSoloOptativasHabilitadas(contextoAsistido);
        boolean omisionDocumentada = detectarOmisionDocumentada(contextoAsistido);
        boolean mismoAnio = detectarContinuidadMismoAnio(contexto, contextoAsistido);

        if (noAcreditadas >= 5) {
            tipoCaso = tipoCasoRepo.findByClave("UNIDADES_NO_ACREDITADAS");
            motivo = motivoRepo.findByClave("ACUMULACION_NO_ACREDITADAS");
            clasificacion.setConfianza(0.98D);
            clasificacion.setEstatusSugerido("EN_ANALISIS");
            clasificacion.getObservaciones()
                    .add("La trayectoria muestra un rezago severo por acumulación alta de no acreditadas.");
        } else if (omisionDocumentada) {
            tipoCaso = tipoCasoRepo.findByClave("OMISION_DOCUMENTADA");
            motivo = motivoRepo.findByClave("OMISION_DOCUMENTADA_MOTIVO");
            clasificacion.setConfianza(0.92D);
            clasificacion.setEstatusSugerido("EN_ANALISIS");
            clasificacion.getObservaciones().add("La trayectoria sugiere una omisión documentable y no una no acreditación activa.");
        } else if (primerAnioIncompleto && "INSCRIPCION".equalsIgnoreCase(periodo)) {
            tipoCaso = tipoCasoRepo.findByClave("REINSCRIPCION_NO_APROBADA");
            motivo = motivoRepo.findByClave("PRIMER_ANIO_INCOMPLETO");
            clasificacion.setConfianza(0.95D);
            clasificacion.setEstatusSugerido("EN_ANALISIS");
            clasificacion.getObservaciones().add("El primer año permanece incompleto; no procede habilitar obligatorias de un año superior.");
        } else if (soloOptativas && "INSCRIPCION".equalsIgnoreCase(periodo)) {
            tipoCaso = tipoCasoRepo.findByClave(noAcreditadas > 0 ? "UNIDADES_NO_ACREDITADAS" : "REZAGO");
            motivo = motivoRepo.findByClave("SOLO_OPTATIVAS_HABILITADAS");
            clasificacion.setConfianza(0.90D);
            clasificacion.setEstatusSugerido("EN_ANALISIS");
            clasificacion.getObservaciones().add("La selección quedó limitada a optativas o pendientes compatibles con la trayectoria.");
        } else if (mismoAnio && "INSCRIPCION".equalsIgnoreCase(periodo)) {
            tipoCaso = tipoCasoRepo.findByClave("CONTINUIDAD_MISMO_ANIO");
            motivo = motivoRepo.findByClave("MISMO_ANIO_ACADEMICO");
            clasificacion.setConfianza(0.86D);
            clasificacion.setEstatusSugerido("EN_ANALISIS");
            clasificacion.getObservaciones().add("La solicitud corresponde al mismo año académico; la restricción no debe interpretarse como salto a otro año.");
        } else if (restriccionAvance || noAcreditadas >= 3) {
            tipoCaso = tipoCasoRepo.findByClave(noAcreditadas > 0 ? "UNIDADES_NO_ACREDITADAS" : "REZAGO");
            motivo = motivoRepo.findByClave("ACUMULACION_NO_ACREDITADAS");
            clasificacion.setConfianza(restriccionAvance ? 0.93D : 0.88D);
            clasificacion.setEstatusSugerido("EN_ANALISIS");
            clasificacion.getObservaciones().add("La clasificación se apoya en acumulación de no acreditadas y restricción de avance.");
        } else if (bloqueadas > 0) {
            tipoCaso = tipoCasoRepo.findByClave("SERIACION");
            motivo = motivoRepo.findByClave("BLOQUEO_SERIACION");
            clasificacion.setConfianza(0.89D);
            clasificacion.setEstatusSugerido("EN_ANALISIS");
            clasificacion.getObservaciones().add("Existen bloqueos por seriación que explican la limitación de selección.");
        } else if (omisiones > 0) {
            tipoCaso = tipoCasoRepo.findByClave("OMISION_REGISTRO");
            motivo = motivoRepo.findByClave("UD_NO_CURSADA");
            clasificacion.setConfianza(0.82D);
            clasificacion.setEstatusSugerido("EN_ANALISIS");
            clasificacion.getObservaciones().add("Predominan omisiones o unidades no inscritas sobre reprobación activa.");
        } else if ("MALLA".equalsIgnoreCase(origen) || "EXPEDIENTE".equalsIgnoreCase(origen)) {
            tipoCaso = tipoCasoRepo.findByClave("ACLARACION_AVANCE");
            motivo = motivoRepo.findByClave("INFORMACION_INSUFICIENTE");
            clasificacion.setConfianza(0.70D);
            clasificacion.setEstatusSugerido("EN_REVISION");
            clasificacion.setRequiereIntervencionHumana(Boolean.TRUE);
            clasificacion.getObservaciones().add("La orientación requiere revisión académica por provenir de una consulta explicativa.");
        } else if ("INSCRIPCION".equalsIgnoreCase(periodo)) {
            tipoCaso = tipoCasoRepo.findByClave("REINSCRIPCION_NO_APROBADA");
            motivo = motivoRepo.findByClave("UD_NO_CURSADA");
            clasificacion.setConfianza(0.80D);
            clasificacion.setEstatusSugerido("EN_ANALISIS");
            clasificacion.getObservaciones().add("Caso de inscripción/reinscripción abierto con información suficiente.");
        } else {
            tipoCaso = tipoCasoRepo.findByClave("REZAGO");
            motivo = motivoRepo.findByClave("UD_NO_ACREDITADA");
            clasificacion.setConfianza(0.78D);
            clasificacion.setEstatusSugerido("EN_ANALISIS");
        }

        clasificacion.setTipoCaso(AsistenteCurricularV2Mapper.toTipoCasoDto(tipoCaso));
        clasificacion.setMotivoPrincipal(AsistenteCurricularV2Mapper.toMotivoDto(motivo));
        clasificacion.setOperable(Boolean.TRUE);
        if (!Boolean.TRUE.equals(clasificacion.getRequiereIntervencionHumana())) {
            clasificacion.setRequiereIntervencionHumana(Boolean.FALSE);
        }
        clasificacion.getSubcausas().add("Periodo operativo: " + (periodo != null ? periodo : "SIN_DEFINIR"));
        clasificacion.getSubcausas().add("Origen de consulta: " + (origen != null ? origen : "SIN_DEFINIR"));
        clasificacion.getSubcausas().add("Perfil de consulta: " + contexto.getPerfilConsulta());
        if (requiereGestor) {
            clasificacion.getSubcausas().add("Caso abierto desde consulta de matrícula en modo gestor.");
        }
        if (contextoAsistido != null) {
            clasificacion.getSubcausas().add("Situación académica: "
                    + (tieneTexto(contextoAsistido.getSituacionAcademicaPeriodo())
                            ? contextoAsistido.getSituacionAcademicaPeriodo()
                            : "SIN_CLASIFICAR"));
            clasificacion.getSubcausas().add("No acreditadas detectadas: " + noAcreditadas);
            clasificacion.getSubcausas().add("No inscritas detectadas: " + omisiones);
            clasificacion.getSubcausas().add("Bloqueadas por seriación: " + bloqueadas);
            if (primerAnioIncompleto) {
                clasificacion.getSubcausas().add("El primer año académico permanece incompleto.");
            }
            if (soloOptativas) {
                clasificacion.getSubcausas().add("La selección disponible se limita a optativas o pendientes habilitadas.");
            }
            if (mismoAnio) {
                clasificacion.getSubcausas().add("La solicitud parece corresponder a continuidad dentro del mismo año académico.");
            }
            if (!tienePlan && contextoAcademicoSuficiente) {
                clasificacion.getSubcausas().add("Se clasificó con base en el diagnóstico académico aun sin idPlan persistido en el contexto.");
            }
        }
        return clasificacion;
    }

    private AsistenteInscripcionContextoDTO obtenerContextoAsistidoSeguro(ContextoAsistenteCurricularV2DTO contexto) {
        if (contexto == null || contexto.getIdPersonaObjetivo() == null) {
            return null;
        }
        try {
            return asistenteInscripcionService.obtenerContextoAsistido(contexto.getIdPersonaObjetivo());
        } catch (InscripcionException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private boolean detectarSoloOptativasHabilitadas(AsistenteInscripcionContextoDTO contextoAsistido) {
        if (contextoAsistido == null || contextoAsistido.getUnidades() == null || contextoAsistido.getUnidades().isEmpty()) {
            return false;
        }
        int habilitadas = 0;
        int optativas = 0;
        for (UnidadDecisionInscripcionDTO unidad : contextoAsistido.getUnidades()) {
            if (unidad == null) {
                continue;
            }
            if (Boolean.TRUE.equals(unidad.getOfertada()) || Boolean.TRUE.equals(unidad.getSeleccionable())) {
                habilitadas++;
                if (unidad.getTipoUd() != null && unidad.getTipoUd().toUpperCase().contains("OPTAT")) {
                    optativas++;
                }
            }
        }
        return habilitadas > 0 && optativas == habilitadas;
    }

    private boolean detectarOmisionDocumentada(AsistenteInscripcionContextoDTO contextoAsistido) {
        if (contextoAsistido == null) {
            return false;
        }
        String resumen = contextoAsistido.getMensajeResumenPeriodo();
        if (contiene(resumen, "omisión documentada") || contiene(resumen, "omision documentada")) {
            return true;
        }
        if (contextoAsistido.getPendientesCriticos() != null) {
            for (String pendiente : contextoAsistido.getPendientesCriticos()) {
                if (contiene(pendiente, "omisión documentada") || contiene(pendiente, "omision documentada")) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean detectarContinuidadMismoAnio(ContextoAsistenteCurricularV2DTO contexto,
            AsistenteInscripcionContextoDTO contextoAsistido) {
        if (contexto == null || contextoAsistido == null || !"INSCRIPCION".equalsIgnoreCase(contexto.getPeriodoOperativo())) {
            return false;
        }
        if (Boolean.TRUE.equals(contextoAsistido.getRestriccionCuatroOMasReprobadas())) {
            return true;
        }
        String resumen = contextoAsistido.getMensajeResumenPeriodo();
        if (contiene(resumen, "mismo primer año académico") || contiene(resumen, "mismo primer anio academico")
                || contiene(resumen, "mismo año académico") || contiene(resumen, "mismo anio academico")) {
            return true;
        }
        return contextoAsistido.getAnioObligatorioPendiente() != null
                && contextoAsistido.getAnioObligatorioPendiente().intValue() == 1;
    }

    private void agregarFaltante(ClasificacionCasoDTO clasificacion, boolean disponible, String mensaje) {
        if (!disponible) {
            clasificacion.getObservaciones().add(mensaje);
        }
    }

    private boolean contiene(String valor, String patron) {
        return valor != null && patron != null && valor.toUpperCase().contains(patron.toUpperCase());
    }

    private int valor(Integer numero) {
        return numero != null ? numero.intValue() : 0;
    }

    private boolean tieneTexto(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }
}
