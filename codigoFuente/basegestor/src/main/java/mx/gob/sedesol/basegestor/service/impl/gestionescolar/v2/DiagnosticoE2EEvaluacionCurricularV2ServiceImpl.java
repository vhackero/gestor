package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.AsistenteInscripcionContextoDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.UnidadDecisionInscripcionDTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ConfiguracionEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ContextoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.DiagnosticoE2EEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ResultadoEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.utils.InscripcionException;
import mx.gob.sedesol.basegestor.service.gestionescolar.AsistenteInscripcionService;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.ConfiguracionEvaluacionCurricularV2Service;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.ContextoEvaluacionCurricularV2Adapter;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.DiagnosticoE2EEvaluacionCurricularV2Service;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.EvaluacionCurricularV2Service;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.TrazabilidadEvaluacionCurricularV2Service;

/** No expone endpoint público: sólo puede invocarse con propiedad DEV/QA explícita. */
@Service("diagnosticoE2EEvaluacionCurricularV2Service")
public class DiagnosticoE2EEvaluacionCurricularV2ServiceImpl implements DiagnosticoE2EEvaluacionCurricularV2Service {
    private static final String PROPIEDAD_HABILITADO = "sigie.v2.diagnostico.e2e.habilitado";
    private static final String PROPIEDAD_AMBIENTE = "sigie.v2.diagnostico.e2e.ambiente";
    @Autowired private AsistenteInscripcionService asistenteInscripcionService;
    @Autowired private ConfiguracionEvaluacionCurricularV2Service configuracionService;
    @Autowired private ContextoEvaluacionCurricularV2Adapter adaptador;
    @Autowired private EvaluacionCurricularV2Service evaluador;
    @Autowired private TrazabilidadEvaluacionCurricularV2Service trazabilidad;

    @Override
    @Transactional(readOnly = true)
    public DiagnosticoE2EEvaluacionCurricularV2DTO ejecutar(Long idPersona) throws InscripcionException {
        if (!Boolean.parseBoolean(System.getProperty(PROPIEDAD_HABILITADO, "false")) || !ambienteDiagnosticoPermitido()) {
            throw new InscripcionException("El diagnóstico E2E V2 está deshabilitado para este ambiente.");
        }
        ConfiguracionEvaluacionCurricularV2DTO cfg = configuracionService.obtenerConfiguracion();
        if (!Boolean.TRUE.equals(cfg.getEvaluacionV2Habilitada()) || !Boolean.TRUE.equals(cfg.getModoSombra())) {
            throw new InscripcionException("El diagnóstico requiere V2 habilitada exclusivamente en modo sombra.");
        }
        AsistenteInscripcionContextoDTO origen = asistenteInscripcionService.obtenerContextoAsistido(idPersona);
        if (origen == null) {
            throw new InscripcionException("No existe contexto SIGIE para el expediente técnico solicitado.");
        }
        String antes = huellaOrigen(origen);
        ContextoEvaluacionCurricularV2DTO contexto = adaptador.construirDesdeAsistente(origen);
        contexto.setVersionReglas(cfg.getVersionReglas());
        ResultadoEvaluacionCurricularV2DTO resultado = evaluador.evaluarSombra(contexto);
        Long idEvaluacion = trazabilidad.registrarSombra(contexto, resultado);
        String despues = huellaOrigen(origen);
        DiagnosticoE2EEvaluacionCurricularV2DTO dto = new DiagnosticoE2EEvaluacionCurricularV2DTO();
        dto.setIdPersonaTecnico(contexto.getIdPersona()); dto.setIdEvaluacion(idEvaluacion);
        dto.setHashContexto(trazabilidad.obtenerHashContexto(idEvaluacion)); dto.setUnidadesEvaluadas(Integer.valueOf(resultado.getDecisiones().size()));
        dto.setReglasEvaluadas(Integer.valueOf(resultado.getReglasEvaluadas().size())); dto.setExcepcionesEvaluadas(Integer.valueOf(resultado.getExcepcionesEvaluadas().size()));
        dto.setAuditoriaReconstruida(Boolean.valueOf(idEvaluacion != null && dto.getHashContexto() != null));
        dto.setInmutabilidadVerificada(Boolean.valueOf(antes.equals(despues))); dto.setEvaluacionSombra(resultado);
        dto.setResultado("SOMBRA_SIN_EFECTOS_PRODUCTIVOS");
        return dto;
    }

    private String huellaOrigen(AsistenteInscripcionContextoDTO contexto) {
        StringBuilder resultado = new StringBuilder();
        if (contexto != null && contexto.getUnidades() != null) for (UnidadDecisionInscripcionDTO unidad : contexto.getUnidades()) {
            if (unidad == null) continue;
            resultado.append(unidad.getUdId()).append('|').append(unidad.getOfertada()).append('|')
                    .append(unidad.getSeleccionable()).append('|').append(unidad.getBloqueada()).append(';');
        }
        return resultado.toString();
    }

    /** La habilitación debe acompañarse de una declaración explícita de DEV o QA. */
    private boolean ambienteDiagnosticoPermitido() {
        String ambiente = System.getProperty(PROPIEDAD_AMBIENTE, "").trim();
        return "DEV".equalsIgnoreCase(ambiente) || "QA".equalsIgnoreCase(ambiente);
    }
}
