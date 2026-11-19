package mx.gob.sedesol.basegestor.service.impl.gestionescolar.v2;

import org.springframework.stereotype.Service;

import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ConfiguracionEvaluacionCurricularV2DTO;
import mx.gob.sedesol.basegestor.commons.dto.gestionescolar.v2.ParametrosCurricularesV2DTO;
import mx.gob.sedesol.basegestor.service.gestionescolar.v2.ConfiguracionEvaluacionCurricularV2Service;

/**
 * Fuente temporal de configuración mientras la migración aditiva de FASE 0C no
 * sea aplicada. Los valores de sistema permiten verificación controlada sin
 * alterar catálogos existentes ni activar decisiones productivas.
 */
@Service("configuracionEvaluacionCurricularV2Service")
public class ConfiguracionEvaluacionCurricularV2ServiceImpl implements ConfiguracionEvaluacionCurricularV2Service {

    public static final String REGLAS_V2_VERSION = "RN-PE2024-2027-act-20261117";
    public static final String CTR01_PERIODO_INICIO = "2027-1";
    public static final Integer CTR01_SEMESTRE_OBJETIVO = Integer.valueOf(5);
    public static final String PERIODO_COMUNICADO_INSTITUCIONAL = "2027-1";
    public static final String TEXTO_COMUNICADO_INSTITUCIONAL = "Esta simulación aplica la normativa y reglas generales vigentes. En un proceso de Inscripción efectivo puede aplicar autorizaciones institucionales específicas. La inscripción efectiva puede diferir del resultado que se muestra aquí.";
    public static final String REFERENCIA_COMUNICADO_INSTITUCIONAL = "Decisiones_institucionales_motor_v2_RN_PE2024_2027.md v4, secciones A y C";
    public static final String DOCUMENTO_DECISIONES_PENDIENTES = "Decisiones_institucionales_motor_v2_RN_PE2024_2027.md v4";
    public static final String VIGENCIA_DECISIONES_PENDIENTES = "2026-11-18";

    @Override
    public ConfiguracionEvaluacionCurricularV2DTO obtenerConfiguracion() {
        ConfiguracionEvaluacionCurricularV2DTO configuracion = new ConfiguracionEvaluacionCurricularV2DTO();
        configuracion.setVersionReglas(obtenerPropiedad("REGLAS_V2_VERSION", REGLAS_V2_VERSION));
        configuracion.setEvaluacionV2Habilitada(obtenerBooleano("EVALUACION_V2_HABILITADA", true));
        configuracion.setModoSombra(obtenerBooleano("REGLAS_V2_MODO_SOMBRA", true));
        configuracion.setCtr01PeriodoInicio(obtenerPropiedad("CTR01_PERIODO_INICIO", CTR01_PERIODO_INICIO));
        configuracion.setCtr01SemestreObjetivo(obtenerEntero("CTR01_SEMESTRE_OBJETIVO", CTR01_SEMESTRE_OBJETIVO));
        ParametrosCurricularesV2DTO parametros = new ParametrosCurricularesV2DTO();
        parametros.setCalificacionMinimaAprobatoria(obtenerEntero("V2_CALIFICACION_MINIMA_APROBATORIA", Integer.valueOf(60)));
        parametros.setOportunidadesPorUd(obtenerEntero("V2_OPORTUNIDADES_POR_UD", Integer.valueOf(3)));
        parametros.setCargaMinima(obtenerEntero("V2_CARGA_MINIMA", Integer.valueOf(3)));
        parametros.setCargaMaxRegular(obtenerEntero("V2_CARGA_MAX_REGULAR", Integer.valueOf(8)));
        parametros.setCargaMaxIrregular(obtenerEntero("V2_CARGA_MAX_IRREGULAR", Integer.valueOf(6)));
        parametros.setUmbralRezagoSevero(obtenerEntero("V2_UMBRAL_REZAGO_SEVERO", Integer.valueOf(4)));
        parametros.setMaxSemestresBajaTemporal(obtenerEntero("V2_MAX_SEMESTRES_BAJA_TEMPORAL", Integer.valueOf(3)));
        parametros.setOptativasRequeridas(obtenerEntero("V2_OPTATIVAS_REQUERIDAS", Integer.valueOf(8)));
        parametros.setCtr01PeriodoInicio(configuracion.getCtr01PeriodoInicio());
        parametros.setCtr01Semestre(configuracion.getCtr01SemestreObjetivo());
        parametros.setDocumentoRespaldo("P2024-2027- Reglas de negocio_Octubre 2026_20261117.docx");
        parametros.setVigencia("2026-11-17");
        parametros.setAmbito(obtenerPropiedad("V2_CATALOGO_OPTATIVAS_AMBITO", "PE2024/2027"));
        parametros.setCatalogoOptativas(obtenerPropiedad("V2_CATALOGO_OPTATIVAS", "USTAPREN:1,RECVIRTU:1,PRINVEST:1,PGHRATRS:2,INAVIRTU:2,ANDHUMAN:2,BAIDOCUM:3,BAICAMPO:3,REACADEM:3,INARTIFI:3,PECRITIC:4,HIMEXICO:4,MECMUNDI:4"));
        parametros.setTratamientoCeroSinBaja(obtenerPropiedad("V2_Q6_CERO_SIN_BAJA", "REPROBACION"));
        parametros.setTratamientoRecursamientoQuinto(obtenerPropiedad("V2_Q5_RECURSAMIENTO_QUINTO", "AVANCE_CONDICIONADO"));
        parametros.setTratamientoRecursamientoQuintoCon64(obtenerPropiedad("V2_Q5_RECURSAMIENTO_QUINTO_CON_64", "REGULARIZACION_64"));
        parametros.setTratamientoOptativasFaltantesQuintoAOcho(obtenerPropiedad("V2_Q7_OPTATIVAS_FALTANTES_5_A_8", "BLOQUEAR"));
        parametros.setTratamientoBloqueOptativaBajaOSustituta(obtenerPropiedad("V2_Q11_BLOQUE_OPTATIVA_BAJA", "AMBOS_BLOQUES"));
        parametros.setTratamientoUdB2SeriadaCarga(obtenerPropiedad("V2_Q4_UD_B2_SERIADA_CARGA", "SOLO_MAX"));
        parametros.setDocumentoRespaldoDecisionesPendientes(obtenerPropiedad("V2_DECISIONES_PENDIENTES_DOCUMENTO", DOCUMENTO_DECISIONES_PENDIENTES));
        parametros.setVigenciaDecisionesPendientes(obtenerPropiedad("V2_DECISIONES_PENDIENTES_VIGENCIA", VIGENCIA_DECISIONES_PENDIENTES));
        parametros.setPeriodoComunicadoInstitucional(obtenerPropiedad("V2_COMUNICADO_INSTITUCIONAL_PERIODO", PERIODO_COMUNICADO_INSTITUCIONAL));
        parametros.setTextoComunicadoInstitucional(obtenerPropiedad("V2_COMUNICADO_INSTITUCIONAL_TEXTO", TEXTO_COMUNICADO_INSTITUCIONAL));
        parametros.setReferenciaComunicadoInstitucional(obtenerPropiedad("V2_COMUNICADO_INSTITUCIONAL_REFERENCIA", REFERENCIA_COMUNICADO_INSTITUCIONAL));
        parametros.setVigenciaComunicadoInstitucional(obtenerPropiedad("V2_COMUNICADO_INSTITUCIONAL_VIGENCIA", "2027-1"));
        configuracion.setParametrosCurriculares(parametros);
        return configuracion;
    }

    private String obtenerPropiedad(String nombre, String valorPredeterminado) {
        String valor = System.getProperty(nombre);
        return valor == null || valor.trim().isEmpty() ? valorPredeterminado : valor.trim();
    }

    private Boolean obtenerBooleano(String nombre, Boolean valorPredeterminado) {
        String valor = System.getProperty(nombre);
        return valor == null || valor.trim().isEmpty() ? valorPredeterminado : Boolean.valueOf(valor.trim());
    }

    private Integer obtenerEntero(String nombre, Integer valorPredeterminado) {
        String valor = System.getProperty(nombre);
        if (valor == null || valor.trim().isEmpty()) return valorPredeterminado;
        try {
            return Integer.valueOf(valor.trim());
        } catch (NumberFormatException e) {
            return valorPredeterminado;
        }
    }
}
